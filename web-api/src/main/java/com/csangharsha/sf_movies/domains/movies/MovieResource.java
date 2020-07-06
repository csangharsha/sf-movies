package com.csangharsha.sf_movies.domains.movies;

import com.csangharsha.sf_movies.exceptions.BadRequestException;
import com.csangharsha.sf_movies.exceptions.ResourceNotFoundException;
import com.csangharsha.sf_movies.utils.Constant;
import com.csangharsha.sf_movies.utils.geocoding.GeoCoding;
import com.csangharsha.sf_movies.utils.geocoding.GeoCodingUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static com.csangharsha.sf_movies.domains.movies.MovieSequenceIdGenerator.ID_SEPARATOR_DEFAULT;

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

    @Autowired
    private RestTemplate restTemplate;


    @ApiOperation(
            value = "Create Movie Records",
            notes = "Create a Movie that was filmed in San Francisco",
            response = MovieDto.class
    )
    @PostMapping
    public ResponseEntity<MovieDto> create(
            @ApiParam(value = "Movie Atrributes that needs to be inserted. Dont send id attribute. Also locations is required and should not be null.", required = true)
            @RequestBody MovieDto dto
    ) throws URISyntaxException {
        if (dto.getId() != null) {
            throw new BadRequestException("Id must be null to create movie.");
        }

        Movie entity = cleanseAndSaveMovie(dto);
        if(entity == null) {
            throw new BadRequestException("Location cannot be found in the map.");
        }

        MovieDto newDto = movieMapper.toDto(entity);
        return ResponseEntity.created(new URI(BASE_URL + "/" + newDto.getId())).body(newDto);
    }


    @ApiOperation(
            value = "Get All Movie Records",
            notes = "Get All the Movie that was filmed in San Francisco",
            response = List.class
    )
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


    @ApiOperation(
            value = "Get Movie Records By Id",
            notes = "Get Movie using unique Identifier",
            response = MovieDto.class
    )
    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> get(@PathVariable String id) {
        Optional<Movie> result = movieService.findOne(id);
        return result.map(r -> ResponseEntity.ok().body(movieMapper.toDto(r))).
                orElseThrow(() -> new ResourceNotFoundException(String.format("Movie with id %1$s not found", id)));
    }


    @ApiOperation(
            value = "Search Movie Using title",
            notes = "Search Movie Using title",
            response = List.class
    )
    @GetMapping("/search")
    public ResponseEntity<List<MovieDto>> searchByMovieTitle(@RequestParam("keyword") String keyword){
        return ResponseEntity.ok(movieMapper.toDto(movieService.search(keyword)));
    }


    @ApiOperation(
            value = "Update Movie Records By Id",
            notes = "Update Movie using unique Identifier",
            response = MovieDto.class
    )
    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> update(@RequestBody MovieDto dto, @PathVariable String id) {
        if (dto.getId() == null || !dto.getId().equals(id)) {
            throw new BadRequestException("ID is either null or doesn't match with url's.");
        }

        if( StringUtils.isEmpty(dto.getLocations()) ) {
            throw new BadRequestException("Location cannot be null or empty.");
        }

        if(dto.getLat() == null || dto.getLon() == null) {
            throw new BadRequestException("Latitude and Longitude cannot be null.");
        }

        Movie entity = movieMapper.toEntity(dto);
        entity = movieService.update(entity);
        MovieDto newDto = movieMapper.toDto(entity);
        return ResponseEntity.ok().body(newDto);
    }


    @ApiOperation(
            value = "Delete Movie Records By Id",
            notes = "Delete Movie using unique Identifier",
            response = MovieDto.class
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        movieService.delete(id);
        return ResponseEntity.ok().build();
    }


    @ApiOperation(
            value = "Import Movie Records",
            notes = "Import Movie Records from \"https://data.sfgov.org/resource/yitu-d5am.json\""
    )
    @PostMapping("/import")
    public ResponseEntity<Void> importData() {
        String url = "https://data.sfgov.org/resource/yitu-d5am.json";

        ResponseEntity<MovieDto[]> response = restTemplate.getForEntity(url, MovieDto[].class);

        MovieDto[] dtos = response.getBody();

        for(MovieDto dto: dtos) {
            String format = "%1$s_%2$s";
            String id = String.format(format,
                    StringUtils.replace(
                            dto.getTitle(), " ", ID_SEPARATOR_DEFAULT
                    ),
                    StringUtils.replace(
                            StringUtils.removeEnd(dto.getLocations(), "."), " ", ID_SEPARATOR_DEFAULT
                    )
            );

            if(movieService.existsById(id)) {
                continue;
            }

            cleanseAndSaveMovie(dto);
        }

        return ResponseEntity.ok().build();
    }


    public Movie cleanseAndSaveMovie(MovieDto dto) {
        if( StringUtils.isEmpty(dto.getLocations()) ) {
            return null;
        }

        log.info(dto.toString());
        Movie entity = movieMapper.toEntity(dto);

        if(entity.getLat() == null || entity.getLon() == null) {
            GeoCoding geoCoding = GeoCodingUtils.getGeoCodingForLoc(StringUtils.join(entity.getLocations(), ", ", Constant.LOCATION), env.getProperty("apiKey"));
            if(geoCoding.getGeoCodingResults().length > 0 && geoCoding.getGeoCodingResults()[0].getLocations().size() > 0) {
                entity.setLat(geoCoding.getGeoCodingResults()[0].getLocations().get(0).getLatLng().getLat());
                entity.setLon(geoCoding.getGeoCodingResults()[0].getLocations().get(0).getLatLng().getLng());
            }else {
                return null;
            }
        }

        entity = movieService.save(entity);

        return entity;
    }

}
