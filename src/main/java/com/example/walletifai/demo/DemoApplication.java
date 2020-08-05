package com.example.walletifai.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        DefaultUriBuilderFactory builderFactory = new DefaultUriBuilderFactory();
        builderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri("http://localhost:8080")
                .uriTemplateHandler(builderFactory)
                .build();
        SpringApplication.run(DemoApplication.class, args);
    }

}
