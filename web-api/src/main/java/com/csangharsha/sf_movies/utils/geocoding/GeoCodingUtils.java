package com.csangharsha.sf_movies.utils.geocoding;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
public class GeoCodingUtils {

    private static final String GEOCODING_URI = "https://nominatim.openstreetmap.org/search";

    private GeoCoding[] getGeoCoding(String address, String key) {
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(GEOCODING_URI)
                .queryParam("q", address)
                .queryParam("format", "json");
        URI uri = builder.build().toUri();

        log.info("Calling geocoding api with: " + uri);
        return restTemplate.getForObject(uri, GeoCoding[].class);
    }

    public static GeoCoding getGeoCodingForLoc(String address, String key) {
        GeoCodingUtils geoCodingUtils = new GeoCodingUtils();
        GeoCoding[] geoCodings = geoCodingUtils.getGeoCoding(address, key);
        if(geoCodings.length == 0) {
            return null;
        }else {
            return geoCodings[0];
        }
    }

}