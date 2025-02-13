package io.github.devgrgur.adresarlevak.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPIConfig()
    {
        Info info = new Info()
                .title("Adresar Levak API")
                .description("View all available REST API endpoints!")
                .version("1.0.0");

        OpenAPI openAPI = new OpenAPI();
        openAPI.setInfo(info);

        return openAPI;
    }

}
