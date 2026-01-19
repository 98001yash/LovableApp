package com.company.lovable.blueprint;



import lombok.Data;

@Data
public class ApiSpec {


    private String name;
    private HttpMethod method;
    private String endpoint;
    private String purpose;
    private boolean authRequired;
}
