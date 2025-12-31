package com.deepmirror.server.controller;

import com.deepmirror.server.domain.Feedback;
import com.deepmirror.server.dto.FeedbackRequestDTO;
import com.deepmirror.server.repository.FeedbackRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
@Tag(name = "Feedback", description = "사용자 건의 및 문의 API")
public class FeedbackController {

    private final FeedbackRepository feedbackRepository;

    // 생성자 주입
    public FeedbackController(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @PostMapping
    @Operation(summary = "건의사항 제출", description = "사용자의 건의사항 및 문의를 저장합니다.")
    public ResponseEntity<String> submitFeedback(@RequestBody FeedbackRequestDTO request) {
        // DTO → Entity 변환
        Feedback feedback = new Feedback(
            request.getSenderName(),
            request.getEmail(),
            request.getContent()
        );

        // DB 저장
        feedbackRepository.save(feedback);

        return ResponseEntity.ok("소중한 의견 감사합니다!");
    }
}
