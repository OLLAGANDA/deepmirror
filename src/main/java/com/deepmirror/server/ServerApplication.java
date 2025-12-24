package com.deepmirror.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
	org.springframework.ai.autoconfigure.vertexai.gemini.VertexAiGeminiAutoConfiguration.class,
	org.springframework.ai.autoconfigure.chat.client.ChatClientAutoConfiguration.class
})
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

}
