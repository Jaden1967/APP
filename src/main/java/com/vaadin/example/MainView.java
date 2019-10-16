package com.vaadin.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

    // TODO:
    // private static ArrayList<String> continentColourList = new ArrayList<>();
    // 存储大陆和国家的数据
    private ArrayList<ArrayList<String>> continentsData = new ArrayList<>();
    private ArrayList<ArrayList<String>> countriesData = new ArrayList<>();
    private ArrayList<ArrayList<String>> neighborsData = new ArrayList<>();

    // 存储是否包含这个大陆或者国家
    private HashMap<String, Integer> continentsMap = new HashMap<>();
    private HashMap<String, String> countriesMap = new HashMap<>();
    private HashMap<String, Integer> neighborsMap = new HashMap<>();

    private TextArea continents = new TextArea(); // (2)
    private TextArea countries = new TextArea(); // (2)
    private TextArea neighbors = new TextArea();
    private TextArea outputLog = new TextArea(); // (2)
    private TextField commandLine = new TextField(); // (2)
    // private MemoryBuffer buffer = new MemoryBuffer();
    // private Upload upload = new Upload(buffer);

    private String x_coor = "";
    private String y_coor = "";

    private int map_width = 0;
    private int map_height = 0;

    // if value == true, this color could be used in a new continent
    private HashMap<String, Boolean> colorAvailable = new HashMap<>();

    private void init() {

        // continents color set
        colorAvailable.put("yellow", true);
        colorAvailable.put("red", true);
        colorAvailable.put("blue", true);
        colorAvailable.put("orange", true);
        colorAvailable.put("magenta", true);
        colorAvailable.put("green", true);
        colorAvailable.put("pink", true);

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

        neighbors.setValue("neighbors shows here\n");
        neighbors.setLabel("Neighbors");
        neighbors.setReadOnly(true);
        neighbors.setWidth("350px");
        neighbors.getStyle().set("maxHeight", "300px");
        neighbors.getStyle().set("minHeight", "200px");

        String defaultOutput =  "editcontinent -add Asia 10\n"
                            +   "editcontinent -remove Asia\n"
                            +   "editcountry -add China Asia\n"
                            +   "editcountry -remove China\n"
                            +   "editneighbor -add China India\n"
                            +   "editneighbor -remove China India\n"
                            +   "editmap risk\n"
                            +   "savemap map_1\n"
                            +   "validatemap\n";
        outputLog.setValue("output shows here\n" + defaultOutput);
        outputLog.setLabel("Output Log");
        outputLog.setReadOnly(true);
        outputLog.setWidth("600px");
        outputLog.getStyle().set("maxHeight", "400px");
        outputLog.getStyle().set("minHeight", "300px");

        commandLine.setPlaceholder("Command Line");
        commandLine.setWidth("600px");
        commandLine.setClearButtonVisible(true);
        commandLine.setErrorMessage("invalid");
    }

    public void addOutputLog(String addtext){
        outputLog.setValue(outputLog.getValue() + addtext + "\n");
    }

    public void coordinatesDialog(String countryname, String continentname) {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);
        
        TextField coordinatesText = new TextField(); // (2)
        coordinatesText.setPlaceholder("input coordinates seperate with space");
        coordinatesText.setWidth("300px");
        coordinatesText.setClearButtonVisible(true);

        
        Button confirmButton = new Button("Confirm", event -> {
            x_coor = coordinatesText.getValue().split(" ")[0];
            y_coor = coordinatesText.getValue().split(" ")[1];
            dialog.close();
            addCountry(countryname, continentname, Integer.valueOf(x_coor), Integer.valueOf(y_coor));
        });   
        confirmButton.addClickShortcut(Key.ENTER);
        // TODO:
        // invalid coordinates

        dialog.add(coordinatesText, confirmButton);
        dialog.open();
        coordinatesText.focus();
    }

    public void setMapSize() {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);
        
        TextField mapSizeText = new TextField(); // (2)
        mapSizeText.setPlaceholder("input map width and height seperate with space");
        mapSizeText.setWidth("400px");
        mapSizeText.setClearButtonVisible(true);

        
        Button confirmButton = new Button("Confirm", event -> {
            map_width = Integer.valueOf(mapSizeText.getValue().split(" ")[0]);
            map_height = Integer.valueOf(mapSizeText.getValue().split(" ")[1]);
            dialog.close();
            addOutputLog("set map width to: " + map_width + "; set map height to: " + map_height);
        });   
        confirmButton.addClickShortcut(Key.ENTER);

        dialog.add(mapSizeText, confirmButton);
        dialog.open();
        mapSizeText.focus();
    }

    public void testCases() {
        // ArrayList<String> tempAL = new ArrayList<>();
        // tempAL.add("China");
        // tempAL.add("India");
        // countriesMap.add(tempAL);
        // countriesMap.put(continentname, Integer.valueOf(continentvalue));
    }

    public MainView() {
        init();

        testCases();

        setMapSize();
        commandLine.addKeyDownListener(event -> {
			commandLine.setInvalid(false);
        });
        
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
                        // invalidInputAlert();
                        commandLine.setInvalid(true);
                    }
                } 
                // editcountry
                else if (tempArr[0].equals("editcountry")) {
                    // TODO closed
                    if (tempArr[1].equals("-add")) {
                        // open dialog for the input of coordinates
                        coordinatesDialog(tempArr[2], tempArr[3]);
                        
                        // TODO closed
                        // addCountry(tempArr[2], tempArr[3], Integer.valueOf(x_coor), Integer.valueOf(y_coor));
                    }
                    else {
                        // TODO closed
                        // invalid input alert
                        invalidInputAlert();
                    }
                }
                // editneighbor
                else if (tempArr[0].equals("editneighbor")) {
                    // TODO:
                    if (tempArr[1].equals("-add")) {
                        addNeighbor(tempArr[2], tempArr[3]);
                    }
                    else if (tempArr[1].equals("-remove")) {
                        removeNeighbor(tempArr[2], tempArr[3]);
                    }
                    else {
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
                // edit map
                else if (tempArr[0].equals("editmap")) {
                    // TODO:
                    editMap(tempArr[1]);               
                }
                else {
                    // TODO closed
                    // invalid input alert
                    invalidInputAlert();
                }
            } 
            else if (tempArr.length == 1) {
                // TODO:
                // map operations
                // show map
                if (tempArr[0].equals("showmap")) {
                    // TODO closed
                    // showmap();
                } 
                // validate map
                else if (tempArr[0].equals("validatemap")) {
                    // TODO:
                    validateMap();               
                }
                else {
                    // TODO closed
                    // invalid input alert
                    invalidInputAlert();
                }
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


        // upload map
        // upload.addSucceededListener(event -> {
        //     Component component = createComponent(event.getMIMEType(),
        //             event.getFileName(), buffer.getInputStream());
        //     showOutput(event.getFileName(), component, output);
        // });
        

        add( // (5)
                new H1("Map Editor"), 
                new HorizontalLayout(
                    continents, 
                    countries,
                    neighbors
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

            String tempColor = "";
            for (String color_name : colorAvailable.keySet()) {
                if (colorAvailable.get(color_name)) {
                    colorAvailable.put(color_name, false);
                    tempColor = color_name;
                    break;
                }
            }
            if (tempColor.equals("")) {
                addOutputLog("Insufficient available color");
                createAlert("Insufficient available color");
                tempColor = "defaultColor";
            }

            tempAL.add(tempColor);
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

    private void addCountry(String countryname, String continentname, int x_coordinates, int y_coordinates) {
        if (!countriesMap.containsKey(countryname)){
            if (continentsMap.containsKey(continentname)) {
                ArrayList<String> tempAL = new ArrayList<>();
                tempAL.add(countryname);
                tempAL.add(continentname);
                tempAL.add(x_coordinates + "");
                tempAL.add(y_coordinates + "");
                countriesData.add(tempAL);
                countriesMap.put(countryname, continentname);
            }
            else {
                Dialog dialog = new Dialog();
                dialog.add(new Label("No such continent, please create it first. \nClose me with the esc-key or an outside click"));
                dialog.setWidth("400px");
                dialog.setHeight("150px");
                dialog.open();
            }
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

    private void addNeighbor(String countryname, String neighborCountryname) {
        if (countriesMap.containsKey(countryname) && countriesMap.containsKey(neighborCountryname)){
            boolean hasCountry = false;
            boolean hasNeighborCountry = false;
            for (ArrayList<String> arrList : neighborsData) {
                if (arrList.get(0).equals(countryname)) {
                    hasCountry = true;
                    if (!arrList.contains(neighborCountryname)) {
                        arrList.add(neighborCountryname);
                    }
                }
                if (arrList.get(0).equals(neighborCountryname)) {
                    hasNeighborCountry = true;
                    if (!arrList.contains(countryname)) {
                        arrList.add(countryname);
                    }
                }
            }
            if (!hasCountry) {
                ArrayList<String> tempAL = new ArrayList<>();
                tempAL.add(countryname);
                tempAL.add(neighborCountryname);
                neighborsData.add(tempAL);
            }
            if (!hasNeighborCountry) {
                ArrayList<String> tempAL = new ArrayList<>();
                tempAL.add(neighborCountryname);
                tempAL.add(countryname);
                neighborsData.add(tempAL);
            }
        }
        else {
            Dialog dialog = new Dialog();
            dialog.add(new Label("No such countries exist, please add it first. \nClose me with the esc-key or an outside click"));
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }

        updateNeighbors();
    }

    private void removeNeighbor(String countryname, String neighborCountryname) {
        boolean hasCountry = false;
        boolean hasNeighborCountry = false;
        // TODO: 
        // bug need to fix!
        if (neighborsData.size() == 0) {
            invalidInputAlert();
            return;
        }

        for (ArrayList<String> arrList : neighborsData) {
            if (arrList.get(0).equals(countryname)) {
                for (String neighbor_name : arrList.subList(1, arrList.size())) {
                    if (neighbor_name.equals(neighborCountryname)) {
                        arrList.remove(neighborCountryname);
                        if (arrList.size() == 1) {
                            neighborsData.remove(arrList);
                        }
                        hasCountry = true;
                        updateNeighbors();
                    }
                }
            }
            // else if (arrList.get(0).equals(neighborCountryname)) {
            //     for (String neighbor_name : arrList) {
            //         if (neighbor_name.equals(countryname)) {
            //             arrList.remove(countryname);
            //             if (arrList.size() == 1) {
            //                 neighborsData.remove(arrList);
            //             }
            //             hasNeighborCountry = true;
            //             updateNeighbors();
            //         }
            //     }
            // }
        }
        // TODO:
        for (ArrayList<String> arrList : neighborsData) {
            if (arrList.get(0).equals(neighborCountryname)) {
                for (String neighbor_name : arrList) {
                    if (neighbor_name.equals(countryname)) {
                        arrList.remove(countryname);
                        if (arrList.size() == 1) {
                            neighborsData.remove(arrList);
                        }
                        hasNeighborCountry = true;
                        updateNeighbors();
                    }
                }
            }
        }

        // if (!hasCountry || !hasNeighborCountry) {
        if (!hasCountry || !hasNeighborCountry) {
            // invalid
            Dialog dialog = new Dialog();
            dialog.add(new Label("No such country, please create it first. \nClose me with the esc-key or an outside click"));
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }
    }

    private void updateNeighbors(){
        if (neighborsData.size() > 0) {
            StringBuilder strBuilder = new StringBuilder();
            for (ArrayList<String> arrList : neighborsData) {
                String neighborStr = "";
                for (String strList : arrList) {
                    neighborStr += (strList + " ");
                }
                strBuilder.append(neighborStr + "\n");
            }
            neighbors.setValue(strBuilder.toString());
        }
        else {
            neighbors.setValue("neighbors shows here\n");
        }
    }

    public void saveMap(String fileName) {
        if (!validateMap()) {
            createAlert("Save Map Failed!");
            return;
        }


        try {
            

            String path = "map/" + fileName + ".map";
            // TODO:
            // encoding
            StringBuilder strBuilder = new StringBuilder();

            strBuilder.append("; Yilun Sun and his dev groupmates\n");
            strBuilder.append("; Risk Map\n\n");

            strBuilder.append("; Risk Game Map\n");
            // TODO:
            strBuilder.append("; Dimensions: 677 x 425 Pixels\n");
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
                for (String strList : arrList) {
                    counutryStr += (strList + " ");
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
                    if (neighborList.get(0).equals(currCountryName)) {
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

            addOutputLog("Map saved to " + path);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO:
    public void editMap(String fileName) {
        String path = "map/" + fileName + ".map";
        File file = new File(path);

        if (!file.exists()) {
            invalidInputAlert();
        }

        // Java 11 , default StandardCharsets.UTF_8
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        // reset all data
        continentsData.clear();
        countriesData.clear();
        neighborsData.clear();

        continentsMap.clear();
        countriesMap.clear();

        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
    
            String line = "";

            boolean continentPhase = false;
            boolean countryPhase = false;
            boolean borderPhase = false;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("")) {
                    continentPhase = false;
                    countryPhase = false;
                    borderPhase = false;
                }

                // TODO:
                // System.out.println(line);
                if (continentPhase){
                    String[] tempStr = line.split(" ");
                    ArrayList<String> tempAL = new ArrayList<>();
                    tempAL.add(tempStr[0]);
                    tempAL.add(tempStr[1]);
                    tempAL.add(tempStr[2]);
                    continentsData.add(tempAL);
                    continentsMap.put(tempStr[0], Integer.valueOf(tempStr[1]));
                    updateContinents();
                }
                else if (countryPhase) {
                    String[] tempStr = line.split(" ");
                    ArrayList<String> tempAL = new ArrayList<>();
                    tempAL.add(tempStr[1]);
                    String continent_name = continentsData.get(Integer.valueOf(tempStr[2]) - 1).get(0);
                    tempAL.add(continent_name);
                    tempAL.add(tempStr[3]);
                    tempAL.add(tempStr[4]);
                    countriesData.add(tempAL);
                    countriesMap.put(tempStr[1], continent_name);
                    updateCountries();
                }
                else if (borderPhase) {
                    String[] tempStr = line.split(" ");
                    ArrayList<String> tempAL = new ArrayList<>();

                    String country_name = countriesData.get(Integer.valueOf(tempStr[0]) - 1).get(0);
                    tempAL.add(country_name);

                    for (int i = 1; i < tempStr.length; i++) {
                        String tempName = countriesData.get(Integer.valueOf(tempStr[i]) - 1).get(0);
                        tempAL.add(tempName);
                    }
                    neighborsData.add(tempAL);

                    updateNeighbors();
                }


                if (line.equals("[continents]")){
                    continentPhase = true;
                }
                else if (line.equals("[countries]")){
                    countryPhase = true;
                }
                else if (line.equals("[borders]")){
                    borderPhase = true;
                }
                else if (line.length() > 20) {
                    if (line.substring(0, 13).equals("; Dimensions:")) {
                        String[] temp = line.split(" ");
                        map_width = Integer.valueOf(temp[2]);
                        map_height = Integer.valueOf(temp[4]);
                        addOutputLog("Load game map width: " + map_width + "\nLoad game map height: " + map_height);
                    }
                }

                // addOutputLog(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            }
    
            if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            }
        }
        // Files.read(Paths.get(path), content);

        addOutputLog("Map loaded from: " + path);
    }

    /** Validation of map construction
     * bind to command 'validatemap'
     * @param isValidated if is true, map validation success; if is false, validation failed
     * add log to output block
     */
    public boolean validateMap() {
        boolean isValidated = true;

        // check if all contries are in existing continents
        for (ArrayList<String> arrList : countriesData) {
            // arrList.get(1) represents the continent in which the country are located
            if (!continentsMap.containsKey(arrList.get(1))) {
                addOutputLog("Continent " + arrList.get(1) + " in country data is not exist!");
                isValidated = false;
            }
        }

        for (ArrayList<String> arrList : neighborsData) {
            for (String country_name : arrList) {
                if (!countriesMap.containsKey(country_name)) {
                    addOutputLog("Country " + country_name + " in neighbor data is not exist!");
                    isValidated = false;
                }
            }
        }

        if (isValidated) {
            Dialog dialog = new Dialog();
            dialog.add(new Label("Map successful Validated!"));
            dialog.setWidth("300px");
            dialog.setHeight("150px");
            dialog.open();
        }
        else {
            Dialog dialog = new Dialog();
            dialog.add(new Label("Map Validation Failed!"));
            dialog.setWidth("300px");
            dialog.setHeight("150px");
            dialog.open();
        }

        return isValidated;
    }

    /** Create Invalid Input Alert Dialog
     * dialog can be closed with the esc-key or an outside click
     */
    private void invalidInputAlert(){
        Dialog dialog = new Dialog();
        dialog.add(new Label("Invalid Input Command!"));
        dialog.setWidth("300px");
        dialog.setHeight("150px");
        dialog.open();
        // InvalidInputAlert inputAlert = new InvalidInputAlert();
        // inputAlert.alert();
    }

    /** Create Alert Dialog
     * @param alertStr alert text will appear on the Dialog
     * dialog can be closed with the esc-key or an outside click
     */
    private void createAlert(String alertStr){
        Dialog dialog = new Dialog();
        dialog.add(new Label(alertStr));
        dialog.setWidth("300px");
        dialog.setHeight("150px");
        dialog.open();
    }

    // TODO:
    // user edit continent color

    // TODO:
    // output log save to .txt file, for each simple line
}
