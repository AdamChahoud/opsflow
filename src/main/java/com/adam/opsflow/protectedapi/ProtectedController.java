package com.adam.opsflow.protectedapi;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class ProtectedController {
    @GetMapping("/protected")
    public Map<String, Object> protectedEndpoint(Authentication auth) {
        return Map.of("message", "You are authenticated",
                "principal", auth.getPrincipal(),
                "authorities", auth.getAuthorities()
        );
    }
}
