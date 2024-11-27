package org.example.c10_laboration2.location;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record Address(
        String road,
        String suburb,
        String county,
        String city,
        String state,
        @JsonProperty("ISO3166-2-lvl4") String iSO3166_2_lvl4,
        String postcode,
        String country,
        @JsonProperty("country_code") String countryCode
) implements Serializable {}
