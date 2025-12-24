package com.deepmirror.server.controller;

import com.deepmirror.server.dto.ResultCreateRequest;
import com.deepmirror.server.dto.ResultResponse;
import com.deepmirror.server.service.ResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/results")
public class ResultController {

    private final ResultService resultService;

    // 생성자 주입 (Lombok 없이)
    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    /**
     * 성격 분석 결과 저장
     * POST /api/v1/results
     */
    @PostMapping
    public ResponseEntity<ResultResponse> createResult(@RequestBody ResultCreateRequest request) {
        ResultResponse response = resultService.createResult(request);
        return ResponseEntity.ok(response);
    }
}
