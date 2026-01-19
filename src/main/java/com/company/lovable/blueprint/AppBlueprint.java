package com.company.lovable.blueprint;


import lombok.Data;

import java.util.List;

@Data
public class AppBlueprint {

    private MetaInfo meta;
    private List<ComponentSpec> components;

    private List<PageSpec> pages;

    private List<ApiSpec> apis;

    private List<DataModelSpec> dataModels;
}
