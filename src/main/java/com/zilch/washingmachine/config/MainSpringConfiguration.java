package com.zilch.washingmachine.config;

import java.time.Clock;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(JpaConfiguration.class)
@ComponentScan
@EnableAutoConfiguration
public class MainSpringConfiguration {
    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

}
