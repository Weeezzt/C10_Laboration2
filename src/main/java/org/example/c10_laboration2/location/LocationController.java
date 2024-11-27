package org.example.c10_laboration2.location;

import org.example.c10_laboration2.location.dto.LocationDto;
import org.example.c10_laboration2.location.exceptionMappers.LocationNotFoundExceptionMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class LocationController {

    LocationService locationService;

    public LocationController(LocationService locationService){
        this.locationService = locationService;
    }

    //works
    @GetMapping("/locations")
    public List<LocationDto> getAllLocations(){
        return locationService.getPublicLocations();
    }


    //works with exception handling
    @GetMapping("/locations/{id}")
    public List<LocationDto> getLocationsById(@PathVariable int id){

            List<LocationDto> location = locationService.getLocationsById(id);
            return location;
    }

    //works with exception handling
    @PostMapping("/locations")
    public ResponseEntity<Void> createLocations(@RequestBody LocationDto locationDto) {
        int id = locationService.addLocation(locationDto);
        return ResponseEntity.created(URI.create("locations/" + id)).build();
    }


    @GetMapping("/locations/user/{id}")
    public List<LocationDto> allUserLocations(@PathVariable int id){
            return locationService.getAllLocationsByUser(id);
    }

    @GetMapping("/locations/address/{longitude}/{latitude}")
    public String convertCoordinatesToAddress(@PathVariable float longitude, @PathVariable float latitude) {
        return locationService.convertCoordinatesToAddress(longitude, latitude);
    }

    //works with exception handling
    @GetMapping("/locations/category/{category}")
    public List<LocationDto> getPublicLocationByCategory(@PathVariable String category){
        return locationService.getPublicLocationByCategory(category);
    }

    //works
    @GetMapping("/locations/{longitude}/{latitude}/{radius}")
    public List<LocationDto> getLocationsWithinRadius(@PathVariable float longitude, @PathVariable float latitude, @PathVariable double radius){
        return locationService.getLocationsInRadius(longitude, latitude, radius);
    }

    //works with exception handling
    @PutMapping("/locations/{id}")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable int id, @RequestBody LocationDto locationDto) {
        LocationDto updatedLocation = locationService.updateLocation(id, locationDto);
        return ResponseEntity.ok(updatedLocation);
    }
}
