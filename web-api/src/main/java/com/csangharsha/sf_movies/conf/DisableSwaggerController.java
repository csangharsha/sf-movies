package com.csangharsha.sf_movies.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Profile({"!dev"})
@RestController
@Slf4j
public class DisableSwaggerController {

//    @RequestMapping(value = "/swagger-ui.html", method = RequestMethod.GET)
//    public ResponseEntity<Void> getSwagger() throws IOException {
//        return ResponseEntity.notFound().build();
//    }

}
