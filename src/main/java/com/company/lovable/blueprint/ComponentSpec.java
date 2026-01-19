package com.company.lovable.blueprint;


import lombok.Data;
import org.hibernate.type.ComponentType;

import java.util.List;

@Data
public class ComponentSpec {


    private String name;

    private ComponentType type;
    private List<String> usedInPages;
    private String responsibility;
}
