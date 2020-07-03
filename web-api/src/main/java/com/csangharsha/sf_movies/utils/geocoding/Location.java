package com.csangharsha.sf_movies.utils.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

    private String street;

    @JsonProperty("adminArea5")
    private String city;

    @JsonProperty("adminArea3")
    private String state;

    @JsonProperty("adminArea1")
    private String country;

    @JsonProperty("latLng")
    private LatLng latLng;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LatLng {
        private Double lat;
        private Double lng;
    }

}
