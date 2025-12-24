package com.deepmirror.server.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("DeepMirror API")
                .version("v1.0")
                .description("성격 분석 서비스 API 문서");

        return new OpenAPI()
                .info(info);
    }
}
