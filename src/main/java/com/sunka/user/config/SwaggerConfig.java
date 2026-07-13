package com.sunka.user.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "User API",
        version = "1.0",
        description = "API documentation for User service"
    ),
    security = @SecurityRequirement(name = "bearerAuth") // ✅ apply globally
)
@SecurityScheme(
    name = "bearerAuth",                // must match SecurityRequirement name
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"                // optional, just for UI display
)
public class SwaggerConfig {
}
