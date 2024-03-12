package by.ycovich.manager.config;

import by.ycovich.manager.client.RestClientProductClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {
    @Bean
    public RestClientProductClient productClient(
            @Value("${selpo.services.catalog.uri:http://localhost:8081}") String catalogBaseUri) {
        return new RestClientProductClient(RestClient.builder()
                .baseUrl(catalogBaseUri )
                .build());
    };
}
