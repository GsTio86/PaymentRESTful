package me.gt86;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Server {

    public static void main(String[] args) {
        SpringApplication.run(Server.class,args);
        System.out.println("Server is running...");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
