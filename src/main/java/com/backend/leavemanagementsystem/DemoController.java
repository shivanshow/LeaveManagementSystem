package com.backend.leavemanagementsystem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/check")
public class DemoController {

    @GetMapping("/v1")
    public Map<String, String> checkApi() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "API is working!");
        return response;
    }
}
