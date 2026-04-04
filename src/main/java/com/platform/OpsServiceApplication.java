package com.platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class OpsServiceApplication {

    private static final Logger log = LoggerFactory.getLogger(OpsServiceApplication.class);

    public static void main(String[] args) {
        log.info("Starting OpsService application...");
        SpringApplication.run(OpsServiceApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        log.info("OpsService application started successfully and is ready to serve traffic");
    }

    @EventListener(ContextClosedEvent.class)
    public void onShutdown() {
        log.info("OpsService application shutdown initiated");
        log.info("OpsService application shutdown completed");
    }
}