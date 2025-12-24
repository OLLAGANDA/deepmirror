package com.deepmirror.server.service;

import com.deepmirror.server.domain.Result;
import com.deepmirror.server.dto.ResultCreateRequest;
import com.deepmirror.server.dto.ResultResponse;
import com.deepmirror.server.repository.ResultRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ResultService {

    private final ResultRepository resultRepository;
    private final GeminiService geminiService;

    // 생성자 주입 (Lombok 없이)
    public ResultService(ResultRepository resultRepository, GeminiService geminiService) {
        this.resultRepository = resultRepository;
        this.geminiService = geminiService;
    }

    /**
     * 성격 분석 결과 생성 및 저장
     * @param request 클라이언트가 보낸 설문 결과
     * @return 저장된 결과 (DTO)
     */
    public ResultResponse createResult(ResultCreateRequest request) {
        // DTO -> Entity 변환
        Result result = request.toEntity();

        // AI 분석 수행
        String aiAnalysis = analyzePersonality(
                request.getOpenness(),
                request.getConscientiousness(),
                request.getExtraversion(),
                request.getAgreeableness(),
                request.getNeuroticism()
        );

        // AI 분석 결과 저장
        result.setAiAnalysis(aiAnalysis);

        // 저장
        Result savedResult = resultRepository.save(result);

        // Entity -> DTO 변환 후 반환
        return ResultResponse.fromEntity(savedResult);
    }

    /**
     * AI를 통한 성격 분석
     * @param openness 개방성 점수
     * @param conscientiousness 성실성 점수
     * @param extraversion 외향성 점수
     * @param agreeableness 친화성 점수
     * @param neuroticism 신경성 점수
     * @return AI 분석 결과 텍스트
     */
    private String analyzePersonality(int openness, int conscientiousness,
                                      int extraversion, int agreeableness, int neuroticism) {
        String systemPrompt = "너는 심리학 전문가야. Big Five 성격 이론에 기반하여 사용자의 성격을 분석하고, 실용적이고 긍정적인 피드백을 제공해.";

        String userMessage = String.format(
                """
                다음은 Big Five 성격 검사 결과입니다. 각 항목은 0~100 사이의 점수입니다:

                - 개방성 (Openness): %d
                - 성실성 (Conscientiousness): %d
                - 외향성 (Extraversion): %d
                - 친화성 (Agreeableness): %d
                - 신경성 (Neuroticism): %d

                이 점수를 바탕으로 사용자의 성격을 종합적으로 분석하고, 각 특성이 일상생활과 대인관계에 어떤 영향을 미치는지 설명해주세요.
                200자 이내로 간결하게 작성해주세요.
                """,
                openness, conscientiousness, extraversion, agreeableness, neuroticism
        );

        return geminiService.generateContent(systemPrompt, userMessage);
    }
}
