package com.deepmirror.server.dto;

import com.deepmirror.server.domain.Result;

public class ResultCreateRequest {

    private String clientIp;
    private int openness;
    private int conscientiousness;
    private int extraversion;
    private int agreeableness;
    private int neuroticism;

    // 기본 생성자
    public ResultCreateRequest() {
    }

    // 전체 생성자
    public ResultCreateRequest(String clientIp, int openness, int conscientiousness,
                               int extraversion, int agreeableness, int neuroticism) {
        this.clientIp = clientIp;
        this.openness = openness;
        this.conscientiousness = conscientiousness;
        this.extraversion = extraversion;
        this.agreeableness = agreeableness;
        this.neuroticism = neuroticism;
    }

    // Getters
    public String getClientIp() {
        return clientIp;
    }

    public int getOpenness() {
        return openness;
    }

    public int getConscientiousness() {
        return conscientiousness;
    }

    public int getExtraversion() {
        return extraversion;
    }

    public int getAgreeableness() {
        return agreeableness;
    }

    public int getNeuroticism() {
        return neuroticism;
    }

    // Setters (JSON 역직렬화를 위해 필요)
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public void setOpenness(int openness) {
        this.openness = openness;
    }

    public void setConscientiousness(int conscientiousness) {
        this.conscientiousness = conscientiousness;
    }

    public void setExtraversion(int extraversion) {
        this.extraversion = extraversion;
    }

    public void setAgreeableness(int agreeableness) {
        this.agreeableness = agreeableness;
    }

    public void setNeuroticism(int neuroticism) {
        this.neuroticism = neuroticism;
    }

    // DTO -> Entity 변환
    public Result toEntity() {
        return new Result(
                this.clientIp,
                this.openness,
                this.conscientiousness,
                this.extraversion,
                this.agreeableness,
                this.neuroticism,
                null  // aiAnalysis는 아직 생성 전
        );
    }
}
