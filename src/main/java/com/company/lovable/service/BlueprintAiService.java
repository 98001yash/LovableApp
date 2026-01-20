package com.company.lovable.service;



import com.company.lovable.blueprint.AppBlueprint;
import com.company.lovable.exceptions.ai.AiOutputInvalidException;
import com.company.lovable.exceptions.ai.AiPolicyViolationException;
import com.company.lovable.exceptions.ai.AiProviderUnavailableException;
import com.company.lovable.exceptions.ai.AiRateLimitException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class BlueprintAiService {

    private final ChatClient chatClient;

    public BlueprintAiService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public AppBlueprint generateBlueprint(String userPrompt) {

        try {
            return chatClient
                    .prompt()
                    .system(buildSystemPrompt())
                    .user(userPrompt)
                    .call()
                    .entity(AppBlueprint.class);

        }
        /*
         * -------- INVALID AI OUTPUT / MAPPING --------
         */
        catch (IllegalArgumentException ex) {
            throw new AiOutputInvalidException(
                    "AI returned an invalid application blueprint. Please refine your prompt."
            );
        }
        /*
         * -------- AI PROVIDER / NETWORK / RATE LIMIT --------
         */
        catch (RuntimeException ex) {

            String message = ex.getMessage() != null ? ex.getMessage().toLowerCase() : "";

            if (message.contains("rate limit") || message.contains("429")) {
                throw new AiRateLimitException(
                        "AI rate limit exceeded. Please try again later."
                );
            }

            if (message.contains("policy") || message.contains("safety")) {
                throw new AiPolicyViolationException(
                        "Your request was rejected by AI safety policies."
                );
            }

            throw new AiProviderUnavailableException(
                    "AI service is temporarily unavailable.",
                    ex
            );
        }
    }

    private String buildSystemPrompt() {
        return """
        You are an expert software architect.

        Convert the user's idea into an application blueprint.

        STRICT RULES:
        - Return ONLY valid JSON
        - Do NOT include explanations or markdown
        - Follow this structure EXACTLY

        {
          "meta": {
            "name": "string",
            "description": "string",
            "appType": "string",
            "targetUsers": "string"
          },
          "pages": [
            {
              "name": "string",
              "route": "string",
              "purpose": "string",
              "authRequired": boolean
            }
          ],
          "components": [
            {
              "name": "string",
              "type": "FORM | LIST | CARD | MODAL | NAV",
              "usedInPages": ["string"],
              "responsibility": "string"
            }
          ],
          "apis": [
            {
              "name": "string",
              "method": "GET | POST | PUT | DELETE",
              "endpoint": "string",
              "purpose": "string",
              "authRequired": boolean
            }
          ],
          "dataModels": [
            {
              "name": "string",
              "description": "string",
              "fields": [
                {
                  "name": "string",
                  "type": "string",
                  "required": boolean
                }
              ]
            }
          ]
        }
        """;
    }
}
