package com.vaadin.example;

import java.util.ArrayList;
import java.util.HashMap;

public interface SaveMapInterface {
    public void saveToMap(String fileName, int map_width, int map_height, ArrayList<ArrayList<String>> continentsData, ArrayList<ArrayList<String>> countriesData, ArrayList<ArrayList<String>> neighborsData, HashMap<String, Integer> neighborsMap);
}