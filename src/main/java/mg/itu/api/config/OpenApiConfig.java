package mg.itu.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
@OpenAPIDefinition(info = @Info(
        title = "REST API", version = "1.0",
        description = "OPEN API documentation",
        contact = @Contact(name = "API Auth")),
        // security = {@SecurityRequirement(name = "bearerToken")},
        servers = {
            @Server(
                description = "Local ENV",
                url = "http://localhost:8080"
            )
        }
)
// @SecuritySchemes({
//         @SecurityScheme(name = "bearerToken", type = SecuritySchemeType.HTTP, 
//                         scheme = "bearer", bearerFormat = "JWT")
// })
public class OpenApiConfig {

    
}