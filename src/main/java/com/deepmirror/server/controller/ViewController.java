package com.deepmirror.server.controller;

import com.deepmirror.server.dto.ResultResponse;
import com.deepmirror.server.service.ResultService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
public class ViewController {

    private final ResultService resultService;
    private final ObjectMapper objectMapper;

    // 생성자 주입 (Lombok 금지)
    public ViewController(ResultService resultService, ObjectMapper objectMapper) {
        this.resultService = resultService;
        this.objectMapper = objectMapper;
    }

    /**
     * 메인 설문 화면
     * @return index.html 템플릿
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 결과 조회 화면
     * @param id 결과 ID
     * @param model Thymeleaf 모델
     * @return result.html 템플릿
     */
    @GetMapping("/results/{id}")
    public String getResultsView(@PathVariable UUID id, Model model) {
        Optional<ResultResponse> result = resultService.getResult(id);

        if (result.isPresent()) {
            ResultResponse resultData = result.get();
            model.addAttribute("result", resultData);

            // detailScores JSON String을 Map으로 파싱
            Map<String, Integer> detailMap = new HashMap<>();
            String detailScoresJson = resultData.getDetailScores();

            if (detailScoresJson != null && !detailScoresJson.isEmpty()) {
                try {
                    detailMap = objectMapper.readValue(
                            detailScoresJson,
                            new TypeReference<Map<String, Integer>>() {}
                    );
                } catch (Exception e) {
                    System.err.println("Failed to parse detailScores: " + e.getMessage());
                    // 파싱 실패 시 빈 Map 사용
                }
            }

            model.addAttribute("detailMap", detailMap);
            return "result";
        } else {
            // 결과가 없을 경우 메인으로 리다이렉트
            return "redirect:/";
        }
    }
}
