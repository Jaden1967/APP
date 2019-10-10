package com.vaadin.example;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.upload.UploadI18N;
import com.vaadin.flow.router.Route;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

@Route
public class MainView extends VerticalLayout {

    // TODO:
    // private static ArrayList<String> continentColourList = new ArrayList<>();
    // 存储大陆和国家的数据
    private ArrayList<ArrayList<String>> continentsData = new ArrayList<>();
    private ArrayList<ArrayList<String>> countriesData = new ArrayList<>();

    // 存储是否包含这个大陆或者国家
    private HashMap<String, Integer> continentsMap = new HashMap<>();
    private HashMap<String, String> countriesMap = new HashMap<>();

    private TextArea continents = new TextArea(); // (2)
    private TextArea countries = new TextArea(); // (2)
    private TextArea outputLog = new TextArea(); // (2)
    private TextField commandLine = new TextField(); // (2)

    private void init() {
        continents.setValue("continents shows here\n");
        continents.setLabel("Continents");
        continents.setReadOnly(true);
        continents.setWidth("350px");
        continents.getStyle().set("maxHeight", "300px");
        continents.getStyle().set("minHeight", "200px");

        countries.setValue("countries shows here\n");
        countries.setLabel("Countries");
        countries.setReadOnly(true);
        countries.setWidth("350px");
        countries.getStyle().set("maxHeight", "300px");
        countries.getStyle().set("minHeight", "200px");

        outputLog.setValue("output shows here");
        outputLog.setLabel("Output Log");
        outputLog.setReadOnly(true);
        outputLog.setWidth("600px");
        outputLog.getStyle().set("maxHeight", "400px");
        outputLog.getStyle().set("minHeight", "300px");

        commandLine.setPlaceholder("Command Line");
        commandLine.setWidth("600px");
    }

    public MainView() {
        init();

        // TODO:
        Button runButton = new Button("Run", event -> {
            String temp = commandLine.getValue();
            String[] tempArr = temp.split(" ");

            if (tempArr.length == 4) {
                // editcontinent
                if (tempArr[0].equals("editcontinent")) {
                    if (tempArr[1].equals("-add")) {
                        // TODO closed
                        addContinent(tempArr[2], tempArr[3]);
                    }
                    else {
                        // TODO closed
                        // invalid input alert
                        invalidInputAlert();
                    }
                } 
                // editcountry
                else if (tempArr[0].equals("editcountry")) {
                    // TODO closed
                    if (tempArr[1].equals("-add")) {
                        // TODO closed
                        addCountry(tempArr[2], tempArr[3]);
                    }
                    else {
                        // TODO closed
                        // invalid input alert
                        invalidInputAlert();
                    }
                }
                else {
                    // TODO closed
                    // invalid input alert
                    invalidInputAlert();
                }
            } 
            else if (tempArr.length == 3) {
                // editcontinent
                if (tempArr[0].equals("editcontinent")) {
                    if (tempArr[1].equals("-remove")) {
                        // TODO
                        removeContinent(tempArr[2]);
                    } else {
                        // TODO closed
                        // invalid input alert
                        invalidInputAlert();
                    }
                } 
                // editcountry
                else if (tempArr[0].equals("editcountry")) {
                    if (tempArr[1].equals("-remove")) {
                        // TODO closed
                        removeCountry(tempArr[2]);
                    } else {
                        // TODO closed
                        // invalid input alert
                        invalidInputAlert();
                    }
                }
                else {
                    // TODO closed
                    // invalid input alert
                    invalidInputAlert();
                }
            }
            else if (tempArr.length == 2) {
                // TODO:
                // map operations combined with few buttons
                // savemap
                if (tempArr[0].equals("savemap")) {
                    // TODO closed
                    saveMap(tempArr[1]);
                } 
                // editmap
                else if (tempArr[0].equals("editmap")) {
                    // TODO:                 
                }
                else {
                    // TODO closed
                    // invalid input alert
                    invalidInputAlert();
                }
            } 
            else if (tempArr.length == 1) {
                // TODO:
                
                
            }
            else {
                // TODO:
                // invalid input alert
                Dialog dialog = new Dialog();
                dialog.add(new Label("Invalid Input!\nClose me with the esc-key or an outside click"));

                dialog.setWidth("400px");
                dialog.setHeight("150px");

                dialog.open();
            }

        });
        runButton.addClickShortcut(Key.ENTER);

        // TODO:
        // a save map button

        

        add( // (5)
                new H1("Map Editor"), 
                new HorizontalLayout(
                    continents, 
                    countries
                    ),
                new HorizontalLayout(
                    commandLine, 
                    runButton
                    ), 
                outputLog);
    }

    private void addContinent(String continentname, String continentvalue) {
        if (!continentsMap.containsKey(continentname)){
            ArrayList<String> tempAL = new ArrayList<>();
            tempAL.add(continentname);
            tempAL.add(continentvalue);
            tempAL.add("color");
            continentsData.add(tempAL);
            continentsMap.put(continentname, Integer.valueOf(continentvalue));
        }
        else {
            Dialog dialog = new Dialog();
            dialog.add(new Label("Already exist continent, please remove it first. \nClose me with the esc-key or an outside click"));
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }

        updateContinents();
    }

    private void removeContinent(String continentname) {
        if (continentsMap.containsKey(continentname)) {
            continentsMap.remove(continentname);
            // TODO:
            // fix bugs here!!!
            for (int i = 0; i < continentsData.size(); i++ ) {
                if (continentsData.get(i).get(0).equals(continentname)) {
                    continentsData.remove(i);
                }
            }
        }
        else {
            // TODO:
            // invalid
            Dialog dialog = new Dialog();
            dialog.add(new Label("No such continent, please create it first. \nClose me with the esc-key or an outside click"));
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }

        updateContinents();
    }

    private void updateContinents(){
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
        }
        else {
            continents.setValue("continents shows here\n");
        }
    }

    private void addCountry(String countryname, String continentname) {
        if (!countriesMap.containsKey(countryname)){
            ArrayList<String> tempAL = new ArrayList<>();
            tempAL.add(countryname);
            tempAL.add(continentname);
            tempAL.add("coordinates");
            countriesData.add(tempAL);
            countriesMap.put(countryname, continentname);
        }
        else {
            Dialog dialog = new Dialog();
            dialog.add(new Label("Already exist country, please remove it first. \nClose me with the esc-key or an outside click"));
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }

        updateCountries();
    }

    private void removeCountry(String countryname) {
        if (countriesMap.containsKey(countryname)) {
            countriesMap.remove(countryname);
            // TODO:
            // fix bugs here!!!country
            for (ArrayList<String> arrList : countriesData) {
                if (arrList.get(0).equals(countryname)) {
                    countriesData.remove(arrList);
                }
            }
        }
        else {
            // TODO:
            // invalid
            Dialog dialog = new Dialog();
            dialog.add(new Label("No such country, please create it first. \nClose me with the esc-key or an outside click"));
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }

        updateCountries();
    }

    private void updateCountries(){
        if (countriesData.size() > 0) {
            StringBuilder strBuilder = new StringBuilder();
            for (ArrayList<String> arrList : countriesData) {
                String countryStr = "";
                for (String strList : arrList) {
                    countryStr += (strList + " ");
                }
                strBuilder.append(countryStr + "\n");
            }
            countries.setValue(strBuilder.toString());
        }
        else {
            countries.setValue("countries shows here\n");
        }
    }

    public void saveMap(String fileName) {
        try {
            String path = "maps/" + fileName + ".txt";
            // TODO:
            // encoding
            StringBuilder strBuilder = new StringBuilder();

            strBuilder.append("[map_info]\n");

            strBuilder.append("This is a Risk Game Map\n");
            strBuilder.append("made by Yilun Sun and his dev groupmates\n");

            strBuilder.append("\n[continents]\n");
            

            for (ArrayList<String> arrList : continentsData) {
                String continentStr = "";
                for (String strList : arrList) {
                    continentStr += (strList + " ");
                }
                strBuilder.append(continentStr + "\n");
            }

            strBuilder.append("\n[countries]\n");

            for (ArrayList<String> arrList : countriesData) {
                String counutryStr = "";
                for (String strList : arrList) {
                    counutryStr += (strList + " ");
                }
                strBuilder.append(counutryStr + "\n");
            }

            String content = strBuilder.toString();

            // Java 11 , default StandardCharsets.UTF_8
            Files.writeString(Paths.get(path), content);

            outputLog.setValue(outputLog.getValue() + "\n" + "Map saved to " + path);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void invalidInputAlert(){
        Dialog dialog = new Dialog();
        dialog.add(new Label("Invalid Input Command!"));
        dialog.setWidth("300px");
        dialog.setHeight("150px");
        dialog.open();
    }
}
