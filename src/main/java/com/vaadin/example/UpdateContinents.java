package com.vaadin.example;

import java.util.ArrayList;

import com.vaadin.flow.component.textfield.TextArea;

public class UpdateContinents{
    public void updateContinents(ArrayList<ArrayList<String>> continentsData, TextArea continents) {

        if (continentsData.size() > 0) {
            StringBuilder strBuilder = new StringBuilder();
            for (ArrayList<String> arrList : continentsData) {
                String continentStr = "";
                for (String strList : arrList) {
                    continentStr += (strList + " ");
                }
                strBuilder.append(continentStr + "\n");
            }
            continents.setValue(strBuilder.toString());
        } else {
            continents.setValue("continents shows here\n");
        }
    }
}