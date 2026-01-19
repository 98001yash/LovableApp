package com.company.lovable.blueprint;


import lombok.Data;

import java.util.List;

@Data
public class DataModelSpec {

    private String name;
    private String description;
    private List<FieldSpec> fields;
}
