package com.company.lovable.controller;


import com.company.lovable.blueprint.AppBlueprint;
import com.company.lovable.dtos.BlueprintRequest;
import com.company.lovable.service.BlueprintAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blueprint")
@RequiredArgsConstructor
public class BlueprintController {

    private final BlueprintAiService blueprintAiService;


    // generate the application blueprint using spring AI

    @PostMapping
    public AppBlueprint generateBlueprint(
            @RequestBody BlueprintRequest request
    ){
        return blueprintAiService.generateBlueprint(request.getPrompt());
    }
}
