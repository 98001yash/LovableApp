package com.company.lovable.blueprint;


import com.azure.core.http.HttpMethod;
import lombok.Data;

@Data
public class ApiSpec {


    private String name;
    private HttpMethod method;
    private String endpoint;
    private String purpose;
    private boolean authRequired;
}
