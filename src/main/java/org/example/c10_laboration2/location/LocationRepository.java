package org.example.c10_laboration2.location;

import org.example.c10_laboration2.location.entity.Location;
import org.example.c10_laboration2.location.exceptionMappers.LocationNotFoundExceptionMapper;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface LocationRepository extends ListCrudRepository<Location, Integer> {
    Optional<Location>  findByName(String name);

    List<Location> findByUserId(Integer id);

    @Query(value = """

            SELECT *
        FROM location l
        WHERE ST_Distance_Sphere(l.coordinate, ST_GeomFromText(:point, 4326)) / 1000 <= :radius
        """, nativeQuery = true)
    List<Location> findByRadius(@Param("point") String point,
                                @Param("radius") double radius);

    }


