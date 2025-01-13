package com.api.marvel.context;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Marvel API")
                        .version("0.1")
                        .description("Marvel Challenge API")
                        .termsOfService("https://swagger.io/terms/")
                        .license(new License().name("GPLv3").url("https://free.software.org")));
    }
}
