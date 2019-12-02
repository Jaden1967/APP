package com.vaadin.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class SaveMap {
    public void saveToMap(String fileName, int map_width, int map_height, ArrayList<ArrayList<String>> continentsData, ArrayList<ArrayList<String>> countriesData, ArrayList<ArrayList<String>> neighborsData, HashMap<String, Integer> neighborsMap) {
        try {

            String path = "map/" + fileName + ".map";
            // TODO:
            // encoding
            StringBuilder strBuilder = new StringBuilder();

            strBuilder.append("; Yilun Sun and his dev groupmates\n");
            strBuilder.append("; Risk Map\n\n");

            strBuilder.append("; Risk Game Map\n");
            // TODO:
            strBuilder.append("; Dimensions: " + map_width + " x " + map_height + " Pixels\n");
            strBuilder.append("; name Risk Map\n");

            strBuilder.append("\n[files]\n");

            strBuilder.append("\n[continents]\n");

            for (ArrayList<String> arrList : continentsData) {
                String continentStr = "";
                for (String strList : arrList) {
                    continentStr += (strList + " ");
                }
                strBuilder.append(continentStr + "\n");
            }

            strBuilder.append("\n[countries]\n");

            int countryIndex = 1;
            for (ArrayList<String> arrList : countriesData) {
                String counutryStr = countryIndex + " ";
                for (int i = 0; i < arrList.size(); i++) {
                    if (i == 1) {
                        int index = 1;
                        // int ind = continentsMap.keySet.indexOf(arrList.get(1));
                        for (ArrayList<String> contList : continentsData) {
                            if (!contList.get(0).equals(arrList.get(1))) {
                                index++;
                            } else {
                                break;
                            }
                        }
                        counutryStr += (index + " ");
                    } else {
                        counutryStr += (arrList.get(i) + " ");
                    }
                }
                strBuilder.append(counutryStr + "\n");
                neighborsMap.put(arrList.get(0), countryIndex);
                countryIndex++;
            }

            // TODO:
            strBuilder.append("\n[borders]\n");
            for (ArrayList<String> arrList : countriesData) {
                String currCountryName = arrList.get(0);
                for (ArrayList<String> neighborList : neighborsData) {
                    String neighborStr = "";
                    if (neighborList.get(0).equals(currCountryName) && neighborList.size() > 1) {
                        for (String country_name : neighborList) {
                            neighborStr = neighborStr + neighborsMap.get(country_name) + " ";
                        }
                        strBuilder.append(neighborStr.substring(0, neighborStr.length() - 1) + "\n");
                    }

                }
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