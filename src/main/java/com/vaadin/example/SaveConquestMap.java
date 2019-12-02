package com.vaadin.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class SaveConquestMap {
    public void saveToMap(String fileName, int map_width, int map_height, ArrayList<ArrayList<String>> continentsData, ArrayList<ArrayList<String>> countriesData, ArrayList<ArrayList<String>> neighborsData, HashMap<String, Integer> neighborsMap) {
        try {

            String path = "map/" + fileName + ".map";
            // TODO:
            // encoding
            StringBuilder strBuilder = new StringBuilder();

            strBuilder.append("author=???\n");
            strBuilder.append("image=???.bmp\n");
            strBuilder.append("wrap=no\n");
            strBuilder.append("scroll=none\n");
            strBuilder.append("warn=yes\n");

            strBuilder.append("\n[Continents]\n");

            for (ArrayList<String> arrList : continentsData) {
                String continentStr = "";
                continentStr = arrList.get(0) + "=" + arrList.get(1);
                strBuilder.append(continentStr + "\n");
            }

            strBuilder.append("\n[Territories]\n");

            for (ArrayList<String> neighborList : neighborsData) {
                String countryName = neighborList.get(0);

                String territoryStr = countryName;

                for (ArrayList<String> countryrList : countriesData) {
                    if (countryrList.get(0).equals(countryName)) {
                        territoryStr += "," + countryrList.get(2);
                        territoryStr += "," + countryrList.get(3);
                        territoryStr += "," + countryrList.get(1);
                        break;
                    }
                }

                for (int i = 1; i < neighborList.size(); i++) {
                    territoryStr += "," + neighborList.get(i);
                }

                strBuilder.append(territoryStr + "\n");
            }
            
            String content = strBuilder.toString();

            // Java 11 , default StandardCharsets.UTF_8
            Files.writeString(Paths.get(path), content);

            // addOutputLog("Map saved to " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}