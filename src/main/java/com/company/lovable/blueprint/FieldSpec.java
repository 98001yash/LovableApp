package com.company.lovable.blueprint;


import lombok.Data;

@Data
public class FieldSpec {

    private String name;
    private String type;
    private boolean required;
}
