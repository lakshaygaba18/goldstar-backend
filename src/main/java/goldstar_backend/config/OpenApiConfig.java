package goldstar_backend.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI goldStarOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("GoldStar API")
                                .description("""
                                        GoldStar is an AI-powered virtual try-on platform
                                        for boutiques and fashion stores.

                                        Features:
                                        • Authentication
                                        • Worker Management
                                        • Customer Management
                                        • Garment Management
                                        • Virtual Try-On
                                        • QR Based Try-On
                                        • Subscription & Tokens
                                        • Dashboard Analytics
                                        """)
                                .version("v1.0.0")
                                .contact(
                                        new Contact()
                                                .name("Lakshay Gaba")
                                                .email("your-email@example.com")
                                )
                                .license(
                                        new License()
                                                .name("Private")
                                )
                )
                .externalDocs(
                        new ExternalDocumentation()
                                .description("GoldStar Documentation")
                );
    }
}