package org.example.c10_laboration2.location;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.example.c10_laboration2.category.CategoryRepository;
import org.example.c10_laboration2.category.CategoryService;
import org.example.c10_laboration2.category.entity.Category;
import org.example.c10_laboration2.category.exceptionMappers.CategoryNotFoundExceptionMapper;
import org.example.c10_laboration2.location.dto.LocationDto;
import org.example.c10_laboration2.location.entity.Location;
import org.example.c10_laboration2.location.exceptionMappers.CategoryIdNotFoundExceptionMapper;
import org.example.c10_laboration2.location.exceptionMappers.LocationBadRequestExceptionMapper;
import org.example.c10_laboration2.location.exceptionMappers.LocationNotFoundExceptionMapper;
import org.example.c10_laboration2.location.valueobject.LocationStatus;
import org.example.c10_laboration2.user.UserRepository;
import org.example.c10_laboration2.user.entity.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
@Service
public class LocationService {

    private final CategoryService categoryService;
    RestClient restClient;

    UserRepository userRepository;
    CategoryRepository categoryRepository;
    LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository,
                           CategoryRepository categoryRepository,
                           RestClient restClient,
                           UserRepository userRepository, CategoryService categoryService){
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
        this.restClient = restClient;
        this.userRepository = userRepository;
        this.categoryService = categoryService;
    }

    public List<LocationDto> getPublicLocations(){
        return locationRepository.findAll().stream()
                .filter(location -> location.getStatus() == LocationStatus.PUBLIC)
                .map(LocationDto::fromLocation)
                .toList();
    }

    public List<LocationDto> getLocationsById(int id){
        List<LocationDto> location =  locationRepository.findById(id).stream()
                .map(LocationDto::fromLocation)
                .toList();
        if (location.isEmpty()) {
            throw new LocationNotFoundExceptionMapper(id);
        }
        return location;



    }

    @Retryable(maxAttempts = 2)
    public String convertCoordinatesToAddress(float lon, float lat) {
        log.info("Requesting address for coordinates: " + lon + ", " + lat);
        AddressRoot address = restClient
                .get()
                .uri("https://geocode.maps.co/reverse?lat=" + lat + "&lon=" + lon + "&api_key=674364b4c7bf9148352548roh043dc7")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        return address.displayName();
    }

    //Works with exception handling
    @Transactional
    public int addLocation(LocationDto locationDto){

        Location location = new Location();
        location.setName(locationDto.name());
        location.setDescription(locationDto.description());
        location.setStatus(LocationStatus.valueOf(locationDto.status()));
        location.setCoordinate(locationDto.coordinate());

        if (locationRepository.findByName(locationDto.name()).isPresent()) {
            throw new LocationBadRequestExceptionMapper(locationDto.name());
        }

        User user = userRepository.findById(locationDto.userId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );

        location.setUser(user);

        Category category = categoryRepository.findById(locationDto.categoryId()).orElseThrow(
                () -> new CategoryIdNotFoundExceptionMapper(locationDto.categoryId())
        );

        location.setCategory(category);
        location = locationRepository.save(location);
        return location.getId();
    }

    //work without exception handling
    public List<LocationDto> getPublicLocationByCategory(String category){
        List<LocationDto> locations =  locationRepository.findAll().stream()
                .filter(location -> location.getStatus() == LocationStatus.PUBLIC && location.getCategory().getName().equals(category) )
                .map(LocationDto::fromLocation)
                .toList();

        if (locations.isEmpty()) {
            throw new CategoryNotFoundExceptionMapper(category);
        }
        return locations;
    }

    //works with exception handling
    public List<LocationDto> getAllLocationsByUser(int userId) {
        List<Location> locations = locationRepository.findByUserId(userId);
        if (locations.isEmpty())
            {
                throw new LocationNotFoundExceptionMapper(userId);
            }
            return locations.stream()
                    .map(LocationDto::fromLocation)
                    .toList();
    }

   //works with exception handling
    public LocationDto updateLocation(int id, LocationDto locationDto) {
            Location location = locationRepository.findById(id)
                    .orElseThrow(() -> new LocationNotFoundExceptionMapper(id));
            Optional.of(locationDto.userId()).ifPresent(userId -> {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID cannot be updated");
            });
            Optional.ofNullable(locationDto.name()).ifPresent(location::setName);
            Optional.ofNullable(locationDto.description()).ifPresent(location::setDescription);
            Optional.ofNullable(locationDto.status()).ifPresent(status -> location.setStatus(LocationStatus.valueOf(status)));
            Optional.of(locationDto.categoryId())
                    .filter(categoryId -> categoryId != 0)
                    .ifPresent(categoryId ->
                            location.setCategory(categoryRepository.findById(categoryId)
                                    .orElseThrow(() -> new CategoryIdNotFoundExceptionMapper(categoryId)))
                    );

            Location updatedLocation = locationRepository.save(location);

            return LocationDto.fromLocation(updatedLocation);
    }

    //works with exception handling
    public List<LocationDto> getLocationsInRadius(float lon, float lat, double radius) {

        if (lat < -90 || lat > 90 || lon < -180 || lon > 180) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid latitude or longitude");
        }

        String string = "Point(" + lat + " " + lon + ")";
        return locationRepository.findByRadius(string, radius)
                .stream()
                .map(LocationDto::fromLocation)
                .toList();
    }
}


