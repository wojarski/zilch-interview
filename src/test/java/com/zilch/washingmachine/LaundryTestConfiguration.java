package com.zilch.washingmachine;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class LaundryTestConfiguration {
    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }
}
