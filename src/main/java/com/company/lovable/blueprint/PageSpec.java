package com.company.lovable.blueprint;


import lombok.Data;

@Data
public class PageSpec {

    private String name;
    private String route;
    private String purpose;
    private boolean authRequired;
}
