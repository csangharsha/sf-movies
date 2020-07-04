package com.csangharsha.sf_movies.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@Profile("dev")
public class SwaggerConfig {

    @Value("${app.basePackage}")
    private String basePackage;

    @Value("${app.basePath}")
    private String basePath;

    @Bean
    public Docket sfMoviesAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(regex(basePath))
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("SF-Movies REST API")
                .description("\"REST API for SF-Movies in San Francisco\"")
                .version("1.0.0")
                .license("API License")
                .licenseUrl("https://csangharsha.github.io/")
                .contact(new Contact("Sangharsha Chaulagain", "https://csangharsha.github.io/", "sangharsha.chaulagain@gmail.com"))
                .termsOfServiceUrl("https://google.com")
                .build();
    }

}
