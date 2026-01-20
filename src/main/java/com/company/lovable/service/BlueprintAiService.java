package com.company.lovable.service;


import com.company.lovable.blueprint.AppBlueprint;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
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
                    .entity(AppBlueprint.class)
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
