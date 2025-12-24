package com.deepmirror.server.controller;

import com.deepmirror.server.dto.TestRequest;
import com.deepmirror.server.dto.TestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    // GET 요청 테스트
    @GetMapping("/hello")
    public ResponseEntity<Map<String, String>> hello() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello from DeepMirror API!");
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    // GET 요청 - 파라미터 포함
    @GetMapping("/greet")
    public ResponseEntity<Map<String, String>> greet(@RequestParam(defaultValue = "Guest") String name) {
        Map<String, String> response = new HashMap<>();
        response.put("greeting", "안녕하세요, " + name + "님!");
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    // POST 요청 테스트
    @PostMapping("/echo")
    public ResponseEntity<TestResponse> echo(@RequestBody TestRequest request) {
        TestResponse response = new TestResponse(
            "success",
            request.getName(),
            request.getMessage()
        );
        return ResponseEntity.ok(response);
    }

    // POST 요청 - 간단한 처리 로직 포함
    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> process(@RequestBody TestRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "processed");
        response.put("original", request);
        response.put("processed", Map.of(
            "upperName", request.getName().toUpperCase(),
            "messageLength", request.getMessage().length(),
            "reversed", new StringBuilder(request.getMessage()).reverse().toString()
        ));
        return ResponseEntity.ok(response);
    }
}
