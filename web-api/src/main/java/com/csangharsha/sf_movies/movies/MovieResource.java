package com.csangharsha.sf_movies.movies;

import com.csangharsha.sf_movies.utils.Constant;
import com.csangharsha.sf_movies.utils.geocoding.GeoCoding;
import com.csangharsha.sf_movies.utils.geocoding.GeoCodingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(Constant.BASE_URL + MovieResource.BASE_URL)
public class MovieResource {

    public static final String BASE_URL = "/movies";

    private final MovieService movieService;

    private final MovieMapper movieMapper;

    @Autowired
    private Environment env;

    @PostMapping
    public ResponseEntity<MovieDto> create(@RequestBody MovieDto dto) throws URISyntaxException {
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        if( StringUtils.isEmpty(dto.getLocations()) ) {
            return ResponseEntity.badRequest().build();
        }

        log.info(dto.toString());
        Movie entity = movieMapper.toEntity(dto);

        if(entity.getLat() == null || entity.getLon() == null) {
            GeoCoding geoCoding = GeoCodingUtils.getGeoCodingForLoc(StringUtils.join(entity.getLocations(), ", ", Constant.LOCATION), env.getProperty("apiKey"));
            if(geoCoding.getGeoCodingResults().length > 0 && geoCoding.getGeoCodingResults()[0].getLocations().size() > 0) {
                entity.setLat(geoCoding.getGeoCodingResults()[0].getLocations().get(0).getLatLng().getLat());
                entity.setLon(geoCoding.getGeoCodingResults()[0].getLocations().get(0).getLatLng().getLng());
            }else {
                return ResponseEntity.badRequest().build();
            }
        }

        entity = movieService.save(entity);
        MovieDto newDto = movieMapper.toDto(entity);
        return ResponseEntity.created(new URI(BASE_URL + "/" + newDto.getId())).body(newDto);
    }

    @GetMapping
    public ResponseEntity<Iterable<MovieDto>> findAll(@RequestParam(name = "page", required = false) Integer page,
                                               @RequestParam(name = "size", required = false) Integer pageSize) {
        List<MovieDto> dtos;
        if (page != null && pageSize != null) {
            dtos = movieMapper.toDto(movieService.findAll(PageRequest.of(page, pageSize)).getContent());
            return ResponseEntity.ok().body(dtos);
        }
        dtos = movieMapper.toDto(movieService.findAll());
        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> get(@PathVariable String id) {
        Optional<Movie> result = movieService.findOne(id);
        return result.map(r -> ResponseEntity.ok().body(movieMapper.toDto(r))).
                orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> update(@RequestBody MovieDto dto, @PathVariable String id) {
        if (dto.getId() == null || !dto.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }

        if( StringUtils.isEmpty(dto.getLocations()) ) {
            return ResponseEntity.badRequest().build();
        }

        if(dto.getLat() == null || dto.getLon() == null) {
            return ResponseEntity.badRequest().build();
        }

        Movie entity = movieMapper.toEntity(dto);
        entity = movieService.update(entity);
        MovieDto newDto = movieMapper.toDto(entity);
        return ResponseEntity.ok().body(newDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        movieService.delete(id);
        return ResponseEntity.ok().build();
    }

}
