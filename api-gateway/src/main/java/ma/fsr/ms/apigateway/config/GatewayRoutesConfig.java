package ma.fsr.ms.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("patient", r -> r.path("/api/patients/**")
                        .filters(f -> f.rewritePath("/api/patients(?<segment>/?.*)",
                                "/internal/api/v1/patients${segment}"))
                        .uri("http://localhost:8082"))

                .route("medecin", r -> r.path("/api/medecins/**")
                        .filters(f -> f.rewritePath("/api/medecins(?<segment>/?.*)",
                                "/internal/api/v1/medecins${segment}"))
                        .uri("http://localhost:8083"))

                .route("rendezvous", r -> r.path("/api/rendezvous/**")
                        .filters(f -> f.rewritePath("/api/rendezvous(?<segment>/?.*)",
                                "/internal/api/v1/rendezvous${segment}"))
                        .uri("http://localhost:8084"))

                .build();
    }
}
