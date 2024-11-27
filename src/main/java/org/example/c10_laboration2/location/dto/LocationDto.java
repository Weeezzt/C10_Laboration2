package org.example.c10_laboration2.location.dto;

import org.example.c10_laboration2.location.valueobject.LocationStatus;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.example.c10_laboration2.location.entity.Location;

public record LocationDto(String name, String status, String description, Point<G2D> coordinate, int userId, int categoryId) {

    public static LocationDto fromLocation(Location location){
        return new LocationDto(
                location.getName(),
                location.getStatus().name(),
                location.getDescription(),
                location.getCoordinate(),
                location.getUser().getId(),
                location.getCategory().getId()
        );
    }

    public static LocationStatus toStatus(String status) {
        return LocationStatus.valueOf(status.toUpperCase());
    }
}
