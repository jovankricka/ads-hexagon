package com.theannotatedhexagon;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdsGatewayApplication {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        SpringApplication.run(AdsGatewayApplication.class, args);
    }

}
