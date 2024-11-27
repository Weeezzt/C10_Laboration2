package org.example.c10_laboration2.location;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public record AddressRoot(@JsonProperty("place_id") long placeId,
                          String licence,
                          @JsonProperty("osm_type") String osmType,
                          @JsonProperty("osm_id") long osmId,
                          String lat,
                          String lon,
                          @JsonProperty("display_name") String displayName,
                          Address address,
                          List<String> boundingbox) implements Serializable {}
