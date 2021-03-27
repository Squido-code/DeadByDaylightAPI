package com.guillermo.DeadByDaylightAPI.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConf {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Dead by Daylight API")
                        .description("Informacion sobre el juego Dead by Daylight")
                        .contact(new Contact()
                                .name("Guillermo Suarez")
                                .email("squidocode@hotmail.es")
                                .url("api.deadbydaylight.es"))
                        .version("1.0"));
    }
}
