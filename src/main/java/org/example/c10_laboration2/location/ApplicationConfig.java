package org.example.c10_laboration2.location;

import org.geolatte.geom.json.GeolatteGeomModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestClient;
import org.springframework.cache.annotation.EnableCaching;


@Configuration
@EnableRetry
@EnableCaching
public class ApplicationConfig {
    @Bean
    public GeolatteGeomModule geolatteGeomModule() {
        return new GeolatteGeomModule();
    }

    @Bean
    RestClient restClient(){return RestClient.create();}
}
