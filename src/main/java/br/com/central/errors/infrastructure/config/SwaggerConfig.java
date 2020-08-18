package br.com.central.errors.infrastructure.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Collections.singletonList;

@Configuration
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfig {

    @Value("${app.version:0.0.1}")
    private String appVersion;

    @Bean
    Docket getDocketApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .useDefaultResponseMessages(false)
                .securitySchemes(singletonList(apiKey()))
                .securityContexts(singletonList(securityContext()))
                .enableUrlTemplating(false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API - Central Errors")
                .description("Codenation Final Challenge - Central Errors")
                .license("MIT License")
                .licenseUrl("https://github.com/acelera-codenation/central-errors/blob/master/LICENSE")
                .version(appVersion)
                .contact(new Contact("Samuel Santos", "https://www.linkedin.com/in/samuelssantos", ""))
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("Bearer", "Authorization", "header");
    }


    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(securePaths())
                .build();
    }

    private Predicate<String> securePaths() {
        return PathSelectors.regex("/api/events.*").or(PathSelectors.regex("/user/resetpassword.*"));
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("ADMIN", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(
                new SecurityReference("Bearer", authorizationScopes));
    }
}