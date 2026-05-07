package de.trinimon.dictionary.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * Find documentation at:
 * <a href="http://127.0.0.1:8001/v3/api-docs">OpenAPI</a>
 * <a href="http://localhost:8001/swagger-ui/index.html">Swagger</a>
 **/
@OpenAPIDefinition(
        info = @Info(title = "Trinimon Dictionaries", version = "1.0", description = "Translation Service"),
        servers = {
                @Server(url = "http://localhost:8001", description = "Local application")
        })
@Configuration
public class OpenApiConfiguration {
}

