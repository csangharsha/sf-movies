package com.csangharsha.sf_movies.utils.geocoding;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
public class GeoCodingUtils {

    private static final String GEOCODING_URI = "http://open.mapquestapi.com/geocoding/v1/address";

    private GeoCoding getGeoCoding(String address, String key) {
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(GEOCODING_URI)
                .queryParam("location", address)
                .queryParam("key", key);
        URI uri = builder.build().toUri();

        log.info("Calling geocoding api with: " + uri);
        return restTemplate.getForObject(uri, GeoCoding.class);
    }

    public static GeoCoding getGeoCodingForLoc(String address, String key) {
        GeoCodingUtils geoCodingUtils = new GeoCodingUtils();
        return geoCodingUtils.getGeoCoding(address, key);
    }

}