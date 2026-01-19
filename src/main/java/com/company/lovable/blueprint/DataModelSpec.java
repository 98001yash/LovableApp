package com.company.lovable.blueprint;


import lombok.Data;

@Data
public class DataModelSpec {

    private String name;
    private String description;
    private List<FieldSpec> fields;
}
