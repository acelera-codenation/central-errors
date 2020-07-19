package br.com.codenation.errors_center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ErrorsCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErrorsCenterApplication.class, args);
    }

}
