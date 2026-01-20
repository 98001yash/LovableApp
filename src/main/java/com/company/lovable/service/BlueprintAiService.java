package com.company.lovable.service;


import com.company.lovable.blueprint.AppBlueprint;
import com.company.lovable.exceptions.ai.AiOutputInvalidException;
import com.company.lovable.exceptions.ai.AiPolicyViolationException;
import com.company.lovable.exceptions.ai.AiProviderUnavailableException;
import com.company.lovable.exceptions.ai.AiRateLimitException;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlueprintAiService {

    private final ChatClient chatClient;

    /**
     * Core AI entry point
     * converts a user idea into an AppBlueprint using spring AI
     *
     */

    public AppBlueprint generateBlueprint(String userPrompt){
        try{
            return chatClient
                    .prompt()
                    .system(buildSystemPrompt())
                    .user(userPrompt)
                    .call()
                    .entity(AppBlueprint.class);
        }
        /*
          ------ AI output/ Mapping errors ------
          Happens when:
            Json is malformed
            fields are missing
            Enum values do not match
         */

        catch (IllegalArgumentException ex) {
            throw new AiOutputInvalidException(
                    "AI returned an invalid application blueprint. Please refine your prompt."
            );
        }

        ///------------OPENAI PROVIDER ERRORS ----------------
        catch (OpenAiApi.OpenAiApiException ex) {

            int statusCode = ex.getStatusCode();

            if (statusCode == 429) {
                throw new AiRateLimitException(
                        "AI rate limit exceeded. Please try again later."
                );
            }

            if (statusCode == 400 || statusCode == 403) {
                throw new AiPolicyViolationException(
                        "Your request was rejected by AI safety policies."
                );
            }

            throw new AiProviderUnavailableException(
                    "AI service is temporarily unavailable.",
                    ex
            );
        }
        /*
         * -------- NETWORK / TIMEOUT / UNKNOWN --------
         */
        catch (Exception ex) {
            throw new AiProviderUnavailableException(
                    "Unexpected AI failure occurred.",
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
