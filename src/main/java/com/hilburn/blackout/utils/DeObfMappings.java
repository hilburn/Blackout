package com.hilburn.blackout.utils;

import com.hilburn.blackout.helpers.ReflectionHandler;

public enum DeObfMappings
{
    shapedOutput("recipeOutput", "field_77575_e"),
    shapedInput("recipeItems", "field_77574_d"),
    shapelessOutput("recipeOutput", "field_77580_a"),
    shapelessInput("recipeItems", "field_77579_b");

    private String deObfName;
    private String obfName;

    private DeObfMappings(String deObfName, String obfName)
    {
        this.deObfName = deObfName;
        this.obfName = obfName;
    }

    public String getFieldName()
    {
        return ReflectionHandler.isObf ? obfName : deObfName;
    }
}
