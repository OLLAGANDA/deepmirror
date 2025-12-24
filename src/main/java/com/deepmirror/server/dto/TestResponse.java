package com.deepmirror.server.dto;

import java.time.LocalDateTime;

public class TestResponse {
    private String status;
    private String receivedName;
    private String receivedMessage;
    private LocalDateTime timestamp;

    public TestResponse() {
    }

    public TestResponse(String status, String receivedName, String receivedMessage) {
        this.status = status;
        this.receivedName = receivedName;
        this.receivedMessage = receivedMessage;
        this.timestamp = LocalDateTime.now();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReceivedName() {
        return receivedName;
    }

    public void setReceivedName(String receivedName) {
        this.receivedName = receivedName;
    }

    public String getReceivedMessage() {
        return receivedMessage;
    }

    public void setReceivedMessage(String receivedMessage) {
        this.receivedMessage = receivedMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
