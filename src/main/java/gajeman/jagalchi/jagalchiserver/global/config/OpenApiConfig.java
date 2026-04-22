package gajeman.jagalchi.jagalchiserver.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Jagalchi User API")
                        .version("v1.0.0")
                        .description("유저/인증 API"))
                .servers(List.of(new Server().url("/").description("Gateway Server")));
    }
}
