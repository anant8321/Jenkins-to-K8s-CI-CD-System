package com.platform.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OpsController {

    private static final Logger log = LoggerFactory.getLogger(OpsController.class);

    @Value("${app.name}")
    private String appName;

    @Value("${app.environment}")
    private String appEnvironment;

    @Value("${app.version}")
    private String appVersion;

    @GetMapping(value = "/health", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> health() {
        log.info("Health check requested");
        return ResponseEntity.ok("OK");
    }

    @GetMapping(value = "/ready", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> ready() {
        log.info("Readiness check requested");
        return ResponseEntity.ok("READY");
    }

    @GetMapping(value = "/version", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> version() {
        log.info("Version endpoint requested - returning version: {}", appVersion);
        return ResponseEntity.ok(appVersion);
    }

    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> info() {
        log.info("Info endpoint requested");
        
        Map<String, String> info = new HashMap<>();
        info.put("name", appName);
        info.put("environment", appEnvironment);
        info.put("version", appVersion);
        
        return ResponseEntity.ok(info);
    }
}