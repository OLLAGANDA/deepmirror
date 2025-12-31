package com.deepmirror.server.dto;

public class FeedbackRequestDTO {

    private String senderName;
    private String email;
    private String content;

    // 기본 생성자
    public FeedbackRequestDTO() {
    }

    // 생성자
    public FeedbackRequestDTO(String senderName, String email, String content) {
        this.senderName = senderName;
        this.email = email;
        this.content = content;
    }

    // Getters
    public String getSenderName() {
        return senderName;
    }

    public String getEmail() {
        return email;
    }

    public String getContent() {
        return content;
    }

    // Setters
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
