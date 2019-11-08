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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;

import org.vaadin.pekkam.Canvas;
import org.vaadin.pekkam.CanvasRenderingContext2D;

@Route
public class MainView extends VerticalLayout {

    /**
     *
     */
    private static final long serialVersionUID = 1125267260411033039L;
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

    private TextArea mapSize = new TextArea();

    private Tab tab1 = new Tab("Output Log");
    private Tab tab2 = new Tab(new Icon(VaadinIcon.GLOBE));
    private Tab tab3 = new Tab("Tab three");
    Tabs tabs = new Tabs(tab1, tab2, tab3);

    private Div page1 = new Div();
    private Div page2 = new Div();
    private Div page3 = new Div();
    Div pages = new Div(page1, page2, page3);

    // private Image image = new Image("https://dummyimage.com/600x400/000/fff",
    // "DummyImage");

    // private MemoryBuffer buffer = new MemoryBuffer();
    // private Upload upload = new Upload(buffer);

    private String x_coor = "";
    private String y_coor = "";

    private int map_width = 0;
    private int map_height = 0;

    // private boolean hasMapSize = false;

    // if value == true, this color could be used in a new continent
    private HashMap<String, Boolean> colorAvailable = new HashMap<>();

    private Canvas canvas = new Canvas(2000, 1000);
    private CanvasRenderingContext2D ctx = canvas.getContext();
    private Button runButton;
    private MenuBar menuBar = new MenuBar();

    private ArrayList<String> map_list = new ArrayList<>();

    // private void updateMapList() {
    // map_list = getFile();
    // }

    private void init() {
        // add all maps in map folder to map_list
        map_list.addAll(getFile());

        String[] continent_data_list = { "Asia", "Europe", "North-America", "South-America", "Africa", "Oceania" };
        String[] country_data_list = { "Afghanistan", "Alaska", "Alberta", "Argentina", "Brazil", "Central-America",
                "China", "Congo", "East-Africa", "Eastern-United-States", "Egypt", "Great-Britain", "Greenland",
                "Iceland", "India", "Indonesia", "Madagascar", "North-Africa", "North-West-Territory", "Northern-Europe", "Ontario", "Peru",
                "Quebec", "Scandinavia", "Siam", "Siberia", "South-Africa", "Southern-Europe", "Ukraine", "Ural", "Venezuela",
                "Western-Europe", "Western-United-States" };

        MenuItem editcontinent_menu = menuBar.addItem("editcontinent");
        MenuItem editcountry_menu = menuBar.addItem("editcountry");
        MenuItem editneighbor_menu = menuBar.addItem("editneighbor");
        MenuItem map_menu = menuBar.addItem("map operations");
        MenuItem data_menu = menuBar.addItem("data");

        // menuBar.addItem("Sign Out", e -> selected.setText("Sign Out"));

        SubMenu editcontinent_submenu = editcontinent_menu.getSubMenu();
        editcontinent_submenu.addItem("-add", e -> commandLine.setValue("editcontinent -add"));
        editcontinent_submenu.addItem("-remove", e -> commandLine.setValue("editcontinent -remove"));

        SubMenu editcountry_submenu = editcountry_menu.getSubMenu();
        editcountry_submenu.addItem("-add", e -> commandLine.setValue("editcountry -add"));
        editcountry_submenu.addItem("-remove", e -> commandLine.setValue("editcountry -remove"));

        SubMenu editneighbor_submenu = editneighbor_menu.getSubMenu();
        editneighbor_submenu.addItem("-add", e -> commandLine.setValue("editneighbor -add"));
        editneighbor_submenu.addItem("-remove", e -> commandLine.setValue("editneighbor -remove"));

        SubMenu map_submenu = map_menu.getSubMenu();
        map_submenu.addItem("showmap", e -> showmap());
        map_submenu.addItem("validatemap", e -> validateMap());
        map_submenu.addItem("savemap", e -> commandLine.setValue("savemap"));

        MenuItem editmap_submenu = map_submenu.addItem("editmap");
        SubMenu editmap_data_submenu = editmap_submenu.getSubMenu();
        for (String map_name : map_list) {
            editmap_data_submenu.addItem(map_name, e -> editMap(map_name));
        }

        SubMenu data_submenu = data_menu.getSubMenu();
        MenuItem continent_data_menu = data_submenu.addItem("continent list");
        MenuItem country_data_menu = data_submenu.addItem("country list");
        // MenuItem integer_data_menu = data_submenu.addItem("integer list");

        SubMenu continent_data_submenu = continent_data_menu.getSubMenu();
        for (String continent_data_name : continent_data_list) {
            continent_data_submenu.addItem(continent_data_name,
                    e -> commandLine.setValue(commandLine.getValue() + " " + continent_data_name));
        }
        SubMenu country_data_submenu = country_data_menu.getSubMenu();
        for (String country_data_name : country_data_list) {
            country_data_submenu.addItem(country_data_name,
                    e -> commandLine.setValue(commandLine.getValue() + " " + country_data_name));
        }
        // SubMenu integer_data_menu = continent_data_menu.getSubMenu();
        // for {
        // continent_data_submenu.addItem(continent_data_name, e ->
        // commandLine.setValue(commandLine.getValue() + " " + continent_data_name));
        // }

        // TODO:

        // SubMenu country_data_list_submenu = country_data_list.getSubMenu();
        // MenuItem continent_data_list = country_data_list_submenu.addItem("continent
        // list");
        // MenuItem country_data_list = country_data_list_submenu.addItem("country
        // list");

        // page1.setText("Page#1");

        // page2.setText("Page#2");
        // page2.setVisible(false);

        // page3.setText("Page#3");
        // page3.setVisible(false);

        // Map<Tab, Component> tabsToPages = new HashMap<>();
        // tabsToPages.put(tab1, outputLog);
        // tabsToPages.put(tab2, new VerticalLayout(mapSize, canvas));
        // tabsToPages.put(tab3, page3);

        // Set<Component> pagesShown = Stream.of(page1).collect(Collectors.toSet());
        mapSize.setVisible(false);
        mapSize.setReadOnly(true);
        mapSize.setMaxHeight("200px");
        mapSize.setMaxWidth("200px");
        canvas.setVisible(false);
        outputLog.setVisible(true);

        tabs.addSelectedChangeListener(event -> {
            if (tabs.getSelectedTab() == tab1) {
                mapSize.setVisible(false);
                canvas.setVisible(false);
                outputLog.setVisible(true);
                continents.setVisible(true);
                countries.setVisible(true);
                neighbors.setVisible(true);
                commandLine.setVisible(true);
                runButton.setVisible(true);
                menuBar.setVisible(true);
            } else if (tabs.getSelectedTab() == tab2) {
                mapSize.setVisible(true);
                canvas.setVisible(true);
                outputLog.setVisible(false);
                continents.setVisible(false);
                countries.setVisible(false);
                neighbors.setVisible(false);
                commandLine.setVisible(false);
                runButton.setVisible(false);
                menuBar.setVisible(false);
            } else if (tabs.getSelectedTab() == tab3) {
                mapSize.setVisible(false);
                canvas.setVisible(false);
                outputLog.setVisible(false);
                continents.setVisible(false);
                countries.setVisible(false);
                neighbors.setVisible(false);
                commandLine.setVisible(false);
                runButton.setVisible(false);
                menuBar.setVisible(false);
            }
        });

        tabs.setSelectedTab(tab1);
        tab3.add(outputLog);

        // Draw an image located in src/main/webapp/resources:
        // ctx.drawImage("https://dummyimage.com/600x400/FCFAF2/000000&text=+", 0, 0);

        // TODO:
        // ctx.setStrokeStyle("#373C38");
        // ctx.strokeRect(1, 1, 699, 399);

        // for (int i = 0; i < 6; i++) {
        // for (int j = 0; j < 6; j++) {
        // ctx.setFillStyle(String.format("rgb(%s, %s, 0)", i * 50, j * 50));
        // ctx.fillRect(j * 25, i * 25, 25, 25);
        // }
        // }
        // ctx.setFillStyle(String.format("rgb(%s, %s, %s)", 100, 111, 99));
        // ctx.fillRect(200, 200, 100, 100);

        // // Draw a red line from point (10,10) to (100,100):
        // ctx.setStrokeStyle("red");
        // ctx.beginPath();
        // ctx.moveTo(10, 10);
        // ctx.lineTo(100, 100);
        // ctx.closePath();
        // ctx.stroke();

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
        countries.setWidth("500px");
        countries.getStyle().set("maxHeight", "300px");
        countries.getStyle().set("minHeight", "200px");

        neighbors.setValue("neighbors shows here\n");
        neighbors.setLabel("Neighbors");
        neighbors.setReadOnly(true);
        neighbors.setWidth("800px");
        neighbors.getStyle().set("maxHeight", "300px");
        neighbors.getStyle().set("minHeight", "200px");

        String defaultOutput = "editcontinent -add Asia 10\n" + "editcontinent -remove Asia\n"
                + "editcountry -add China Asia\n" + "editcountry -add India Asia\n" + "editcountry -remove China\n"
                + "editneighbor -add China India\n" + "editneighbor -remove China India\n"
                + "editneighbor -remove India China\n" + "editmap risk\n" + "savemap map_1\n" + "validatemap\n"
                + "editneighbor -remove Siam Indonesia\n" + "editneighbor -add Siam Indonesia\n" + "showmap\n";
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

    private ArrayList<String> getFile() {
        String path = "D:/Projects/GitProjects/APPmapeditor/map"; // 要遍历的路径
        File file = new File(path); // 获取其file对象
        File[] fs = file.listFiles(); // 遍历path下的文件和目录，放在File数组中
        ArrayList<String> file_list = new ArrayList<>();
        for (File f : fs) { // 遍历File[]数组
            if (!f.isDirectory()) { // 若非目录(即文件)，则加入list
                // TODO:
                String temp = f.getName();
                if (temp.substring(temp.length() - 4).equals(".map")) {
                    file_list.add(temp.substring(0, temp.length() - 4));
                }
            }
        }
        addOutputLog("Find maps in map folder: " + file_list);
        return file_list;
    }

    public void addOutputLog(String addtext) {
        outputLog.setValue(outputLog.getValue() + addtext + "\n");
        Notification notification = new Notification(addtext, 3000, Position.TOP_START);
        notification.open();
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

            if (x_coor.matches("\\d+") && y_coor.matches("\\d+")) {
                if (Integer.valueOf(x_coor) > 0 && Integer.valueOf(x_coor) < map_width && Integer.valueOf(y_coor) > 0
                        && Integer.valueOf(y_coor) < map_width) {
                    addCountry(countryname, continentname, Integer.valueOf(x_coor), Integer.valueOf(y_coor));
                } else {
                    createAlert("Wrong coordiantes. You can not place a country outside the map.");
                }
            } else {
                createAlert("Country coordiantes should be integers!");
            }

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

            if ((mapSizeText.getValue().split(" ")[0].matches("\\d+")
                    && Integer.valueOf(mapSizeText.getValue().split(" ")[0]) > 0)
                    && (mapSizeText.getValue().split(" ")[1].matches("\\d+")
                            && Integer.valueOf(mapSizeText.getValue().split(" ")[1]) > 0)) {
                addOutputLog("set map width to: " + map_width + "; set map height to: " + map_height);
                mapSize.setValue("map width: " + map_width + "\nmap height: " + map_height);

                // TODO:
                canvas.setMaxHeight(String.valueOf(map_height));
                canvas.setMinHeight(String.valueOf(map_height));
                canvas.setMaxWidth(String.valueOf(map_width));
                canvas.setMinWidth(String.valueOf(map_width));
                ctx.setStrokeStyle("#373C38");
                ctx.strokeRect(2, 2, map_width - 2, map_height - 2);
            } else {
                createAlert("Invalid map size.");
                setMapSize();
            }
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

        // testCases();

        setMapSize();

        commandLine.addKeyDownListener(event -> {
            commandLine.setInvalid(false);
        });

        // TODO:
        runButton = new Button("Run", event -> {
            String temp = commandLine.getValue();
            String[] tempArr = temp.split(" ");

            if (tempArr.length == 4) {
                // editcontinent
                if (tempArr[0].equals("editcontinent")) {
                    if (tempArr[1].equals("-add")) {
                        // TODO closed
                        if (tempArr[3].matches("\\d+") && Integer.valueOf(tempArr[3]) > 0)
                            addContinent(tempArr[2], tempArr[3]);
                        else {
                            createAlert("Continent value is not an positive integer!");
                        }
                    } else {
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
                        // addCountry(tempArr[2], tempArr[3], Integer.valueOf(x_coor),
                        // Integer.valueOf(y_coor));
                    } else {
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
                    } else if (tempArr[1].equals("-remove")) {
                        removeNeighbor(tempArr[2], tempArr[3]);
                    } else {
                        // invalid input alert
                        invalidInputAlert();
                    }
                } else {
                    // TODO closed
                    // invalid input alert
                    invalidInputAlert();
                }
            } else if (tempArr.length == 3) {
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
                } else {
                    // TODO closed
                    // invalid input alert
                    invalidInputAlert();
                }
            } else if (tempArr.length == 2) {
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
                } else {
                    // TODO closed
                    // invalid input alert
                    invalidInputAlert();
                }
            } else if (tempArr.length == 1) {
                // TODO:
                // map operations
                // show map
                if (tempArr[0].equals("showmap")) {
                    // TODO:
                    showmap();
                }

                // validate map
                else if (tempArr[0].equals("validatemap")) {
                    // TODO:
                    validateMap();
                } else {
                    // TODO closed
                    // invalid input alert
                    invalidInputAlert();
                }
            } else {
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

        Button toggleThemeButton = new Button("Toggle dark theme", click -> {
            ThemeList themeList = UI.getCurrent().getElement().getThemeList(); //

            if (themeList.contains(Lumo.DARK)) { //
                themeList.remove(Lumo.DARK);
            } else {
                themeList.add(Lumo.DARK);
            }
        });

        // TODO:
        // a save map button

        // upload map
        // upload.addSucceededListener(event -> {
        // Component component = createComponent(event.getMIMEType(),
        // event.getFileName(), buffer.getInputStream());
        // showOutput(event.getFileName(), component, output);
        // });

        add( // (5)
             // new H1("Map Editor"),
             // new HorizontalLayout(
             // new VerticalLayout(
             // new HorizontalLayout(continents, countries, neighbors),
             // new HorizontalLayout(commandLine, runButton)
             // ),
             // new VerticalLayout(
             // tabs,
             // new HorizontalLayout(outputLog, new HorizontalLayout(mapSize, canvas))
             // )
             // )
                new H1("Map Editor"), new HorizontalLayout(tabs, toggleThemeButton),
                new VerticalLayout(new HorizontalLayout(continents, countries, neighbors), menuBar,
                        new HorizontalLayout(commandLine, runButton), outputLog),
                new HorizontalLayout(mapSize, canvas));
        // new HorizontalLayout(continents, countries, neighbors),
        // new HorizontalLayout(commandLine, runButton),

    }

    private void addContinent(String continentname, String continentvalue) {
        if (!continentsMap.containsKey(continentname)) {
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
        } else {
            Dialog dialog = new Dialog();
            dialog.add(new Label(
                    "Already exist continent, please remove it first. \nClose me with the esc-key or an outside click"));
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
            for (int i = 0; i < continentsData.size(); i++) {
                if (continentsData.get(i).get(0).equals(continentname)) {
                    continentsData.remove(i);
                }
            }

            ArrayList<String> removed_countries = new ArrayList<>();
            // delete countries belong to this continent
            for (int i = countriesData.size() - 1; i >= 0; i--) {
                if (countriesData.get(i).get(1).equals(continentname)) {
                    removed_countries.add(countriesData.get(i).get(0));
                    countriesData.remove(i);
                }
            }

            // delete neighbors data related to removed countries
            for (ArrayList<String> neighbor_names : neighborsData) {
                if (removed_countries.contains(neighbor_names.get(0))) {
                    String temp_curr_country = neighbor_names.get(0);
                    neighbor_names.clear();
                    neighbor_names.add(temp_curr_country);
                }
                if (neighbor_names.size() > 1) {
                    for (int j = neighbor_names.size() - 1; j >= 1; j--) {
                        if (removed_countries.contains(neighbor_names.get(j))) {
                            neighbor_names.remove(j);
                        }
                    }
                }
            }

        } else {
            // TODO:
            // invalid
            Dialog dialog = new Dialog();
            dialog.add(new Label(
                    "No such continent, please create it first. \nClose me with the esc-key or an outside click"));
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }

        updateContinents();
        updateCountries();
        updateNeighbors();
    }

    private void updateContinents() {
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

    private void addCountry(String countryname, String continentname, int x_coordinates, int y_coordinates) {
        if (!countriesMap.containsKey(countryname)) {
            if (continentsMap.containsKey(continentname)) {
                ArrayList<String> tempAL = new ArrayList<>();
                tempAL.add(countryname);
                tempAL.add(continentname);
                tempAL.add(x_coordinates + "");
                tempAL.add(y_coordinates + "");
                countriesData.add(tempAL);
                countriesMap.put(countryname, continentname);
            } else {
                Dialog dialog = new Dialog();
                dialog.add(new Label(
                        "No such continent, please create it first. \nClose me with the esc-key or an outside click"));
                dialog.setWidth("400px");
                dialog.setHeight("150px");
                dialog.open();
            }
        } else {
            Dialog dialog = new Dialog();
            dialog.add(new Label(
                    "Already exist country, please remove it first. \nClose me with the esc-key or an outside click"));
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

            ArrayList<String> removed_countries = new ArrayList<>();
            // fix bugs here!!!country
            for (ArrayList<String> arrList : countriesData) {
                if (arrList.get(0).equals(countryname)) {
                    removed_countries.add(arrList.get(0));
                    countriesData.remove(arrList);
                    // break;
                }
            }
            
            // delete neighbors data related to removed countries
            for (ArrayList<String> neighbor_names : neighborsData) {
                if (removed_countries.contains(neighbor_names.get(0))) {
                    String temp_curr_country = neighbor_names.get(0);
                    neighbor_names.clear();
                    neighbor_names.add(temp_curr_country);
                }
                if (neighbor_names.size() > 1) {
                    for (int j = neighbor_names.size() - 1; j >= 1; j--) {
                        if (removed_countries.contains(neighbor_names.get(j))) {
                            neighbor_names.remove(j);
                        }
                    }
                }
            }
        } else {
            // TODO:
            // invalid
            Dialog dialog = new Dialog();
            dialog.add(new Label(
                    "No such country, please create it first. \nClose me with the esc-key or an outside click"));
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }

        updateCountries();
    }

    private void updateCountries() {
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
        } else {
            countries.setValue("countries shows here\n");
        }
    }

    private void addNeighbor(String countryname, String neighborCountryname) {
        if (countriesMap.containsKey(countryname) && countriesMap.containsKey(neighborCountryname)) {
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
        } else {
            Dialog dialog = new Dialog();
            dialog.add(new Label(
                    "No such countries exist, please add it first. \nClose me with the esc-key or an outside click"));
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
            if (arrList != null && arrList.size() > 1) {
                if (arrList.get(0).equals(countryname)) {
                    for (int neighbor_index = 1; neighbor_index < arrList.size(); neighbor_index++) {
                        if (arrList.get(neighbor_index).equals(neighborCountryname)) {
                            arrList.remove(neighbor_index);
                            // if (arrList.size() == 1) {
                            // neighborsData.remove(arrList);
                            // }
                            hasNeighborCountry = true;
                            updateNeighbors();
                        }
                    }
                }
            }
        }

        // addOutputLog(neighborsData.toString());

        // TODO:
        // for (ArrayList<String> arrList : neighborsData) {
        // if (arrList != null) {
        // if (arrList.get(0).equals(neighborCountryname)) {
        // for (int neighbor_index = 1; neighbor_index < arrList.size();
        // neighbor_index++) {
        // if (arrList.get(neighbor_index).equals(countryname)) {
        // arrList.remove(neighbor_index);
        // if (arrList.size() == 1) {
        // neighborsData.remove(arrList);
        // }
        // hasNeighborCountry = true;
        // updateNeighbors();
        // }
        // }
        // }
        // }

        // }

        // TODO:
        for (int arrListIndex = 0; arrListIndex < neighborsData.size(); arrListIndex++) {
            if (neighborsData.get(arrListIndex) != null && neighborsData.get(arrListIndex).size() > 1) {
                if (neighborsData.get(arrListIndex).get(0).equals(neighborCountryname)) {
                    for (int neighbor_index = 1; neighbor_index < neighborsData.get(arrListIndex)
                            .size(); neighbor_index++) {
                        if (neighborsData.get(arrListIndex).get(neighbor_index).equals(countryname)) {
                            neighborsData.get(arrListIndex).remove(neighbor_index);
                            // if (neighborsData.get(arrListIndex).size() == 1) {
                            // addOutputLog("arrList Size3:" + neighborsData.get(arrListIndex).size());
                            // neighborsData.remove(arrListIndex);
                            // }
                            hasCountry = true;
                            updateNeighbors();
                        }
                    }
                }
            }
        }

        // if (!hasCountry || !hasNeighborCountry) {
        if (!hasCountry || !hasNeighborCountry) {
            // invalid
            Dialog dialog = new Dialog();
            dialog.add(new Label(
                    "No such country, please create it first. \nClose me with the esc-key or an outside click"));
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }
    }

    private void updateNeighbors() {
        if (neighborsData.size() > 0) {
            StringBuilder strBuilder = new StringBuilder();
            for (ArrayList<String> arrList : neighborsData) {
                String neighborStr = "";
                // TODO:
                if (arrList.size() > 1) {
                    for (String strList : arrList) {
                        neighborStr += (strList + " ");
                    }
                    strBuilder.append(neighborStr + "\n");
                }

            }
            neighbors.setValue(strBuilder.toString());
        }
        if (neighbors.getValue().equals("")) {
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

            addOutputLog("Map saved to " + path);
        } catch (IOException e) {
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
                if (continentPhase) {
                    String[] tempStr = line.split(" ");
                    ArrayList<String> tempAL = new ArrayList<>();
                    tempAL.add(tempStr[0]);
                    tempAL.add(tempStr[1]);
                    tempAL.add(tempStr[2]);
                    continentsData.add(tempAL);
                    continentsMap.put(tempStr[0], Integer.valueOf(tempStr[1]));
                    updateContinents();
                } else if (countryPhase) {
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
                } else if (borderPhase) {
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

                if (line.equals("[continents]")) {
                    continentPhase = true;
                } else if (line.equals("[countries]")) {
                    countryPhase = true;
                } else if (line.equals("[borders]")) {
                    borderPhase = true;
                } else if (line.length() > 20) {
                    if (line.substring(0, 13).equals("; Dimensions:")) {
                        String[] temp = line.split(" ");
                        map_width = Integer.valueOf(temp[2]);
                        map_height = Integer.valueOf(temp[4]);
                        addOutputLog("Load game map width: " + map_width + "\nLoad game map height: " + map_height);
                        mapSize.setValue("map width: " + map_width + "\nmap height: " + map_height);
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

    public void showmap() {
        drawMap();

        tabs.setSelectedTab(tab2);
    }

    // DrawMap
    public void drawMap() {
        canvas.setMaxHeight(String.valueOf(map_height * 2));
        canvas.setMinHeight(String.valueOf(map_height * 2));
        canvas.setMaxWidth(String.valueOf(map_width * 2));
        canvas.setMinWidth(String.valueOf(map_width * 2));
        ctx.setStrokeStyle("#348498");
        ctx.clearRect(0, 0, 2000, 2000);
        ctx.strokeRect(2, 2, map_width * 2 - 2, map_height * 2 - 2);

        // neighbors line
        for (ArrayList<String> neighborList : neighborsData) {
            String currCountryName = neighborList.get(0);
            // * 2
            int coordinate_x = Integer.valueOf(getCountryCoordinates(currCountryName).split(",")[0]) * 2;
            int coordinate_y = Integer.valueOf(getCountryCoordinates(currCountryName).split(",")[1]) * 2;
            for (int i = 1; i < neighborList.size(); i++) {
                // * 2
                int neighbor_x = Integer.valueOf(getCountryCoordinates(neighborList.get(i)).split(",")[0]) * 2;
                int neighbor_y = Integer.valueOf(getCountryCoordinates(neighborList.get(i)).split(",")[1]) * 2;
                ctx.setStrokeStyle("#ff502f");
                ctx.beginPath();
                ctx.moveTo(coordinate_x, coordinate_y);
                ctx.lineTo(neighbor_x, neighbor_y);
                ctx.closePath();
                ctx.stroke();
            }
        }

        // countries
        for (ArrayList<String> arrList : countriesData) {
            String countryName = arrList.get(0);
            String continentName = arrList.get(1);
            // * 2
            int coordinate_x = Integer.valueOf(arrList.get(2)) * 2;
            int coordinate_y = Integer.valueOf(arrList.get(3)) * 2;
            ctx.setFillStyle("#348498");
            ctx.fillRect(coordinate_x - 10, coordinate_y - 10, 20, 20);
            ctx.setFillStyle("#5f6769");
            ctx.fillText(countryName, coordinate_x - 30, coordinate_y - 15);
        }
    }

    public String getCountryCoordinates(String countryName) {
        for (ArrayList<String> countryList : countriesData) {
            if (countryList.get(0).equals(countryName)) {
                return countryList.get(2) + "," + countryList.get(3);
            }
        }
        return "1,1";
    }

    // TODO:
    /**
     * Validation of map construction bind to command 'validatemap'
     * 
     * param isValidated if is true, map validation success; if is false, validation
     * failed add log to output block
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

        // TODO:
        for (ArrayList<String> continentArrayList : continentsData) {
            String continent_name = continentArrayList.get(0);

            ArrayList<String> can_reach_country_set = new ArrayList<>();
            ArrayList<String> continent_country_list = new ArrayList<>();
            for (ArrayList<String> countryArrayList : countriesData) {
                if (countryArrayList.get(1).equals(continent_name)) {
                    continent_country_list.add(countryArrayList.get(0));
                }
            }

            if (continent_name.equals("South-America")) {
                addOutputLog(continent_country_list.toString());
            }

            Queue<String> country_openList = new LinkedList<>();
            country_openList.add(continent_country_list.get(0));
            can_reach_country_set.add(continent_country_list.get(0));

            while (country_openList.size() != 0) {
                String curr_country = country_openList.peek();
                for (ArrayList<String> neighbor_data : neighborsData) {
                    // TODO:
                    if (neighbor_data.get(0).equals(curr_country) && neighbor_data.size() > 1) {
                        for (String neighbor_name : neighbor_data) {
                            if (!can_reach_country_set.contains(neighbor_name)
                                    && continent_country_list.contains(neighbor_name)) {
                                country_openList.add(neighbor_name);
                                can_reach_country_set.add(neighbor_name);
                            }
                        }
                    }
                }
                country_openList.poll();
            }
            if (can_reach_country_set.size() != continent_country_list.size()) {
                addOutputLog("can_reach_country_set: " + can_reach_country_set.toString());
                addOutputLog("continent_country_list: " + continent_country_list.toString());
                // createAlert(neighborsData.toString());
                // createAlert(can_reach_country_set.toString());
                isValidated = false;
            }
        }

        int country_num = countriesMap.size();
        HashSet<String> country_set = new HashSet<>();
        Queue<String> openList = new LinkedList<>();
        // Queue<String> closedList = new LinkedList<>();
        openList.add(neighborsData.get(0).get(0));
        country_set.add(neighborsData.get(0).get(0));

        while (openList.size() != 0) {
            String curr_country = openList.peek();
            for (ArrayList<String> neighbor_data : neighborsData) {
                // if (neighbor_data.get(0) == curr_country) {
                // for (String neighbor_name : neighbor_data) {
                // if (!country_set.contains(neighbor_name)) {
                // openList.add(neighbor_name);
                // country_set.add(neighbor_name);
                // }
                // }
                // }
                if (neighbor_data.contains(curr_country)) {
                    for (String neighbor_name : neighbor_data) {
                        if (!country_set.contains(neighbor_name)) {
                            openList.add(neighbor_name);
                            country_set.add(neighbor_name);
                        }
                    }
                }
            }
            openList.poll();
        }
        if (country_set.size() != country_num) {
            addOutputLog("0" + country_set.size());
            // createAlert(neighborsData.toString());
            // createAlert(country_set.toString());
            isValidated = false;
        }

        if (isValidated) {
            Dialog dialog = new Dialog();
            dialog.add(new Label("Map successful Validated!"));
            dialog.setWidth("300px");
            dialog.setHeight("150px");
            dialog.open();
        } else {
            Dialog dialog = new Dialog();
            dialog.add(new Label("Map Validation Failed!"));
            dialog.setWidth("300px");
            dialog.setHeight("150px");
            dialog.open();
        }

        return isValidated;
    }

    /**
     * Create Invalid Input Alert Dialog dialog can be closed with the esc-key or an
     * outside click
     */
    private void invalidInputAlert() {
        Dialog dialog = new Dialog();
        dialog.add(new Label("Invalid Input Command!"));
        dialog.setWidth("300px");
        dialog.setHeight("150px");
        dialog.open();
        // InvalidInputAlert inputAlert = new InvalidInputAlert();
        // inputAlert.alert();
    }

    /**
     * Create Alert Dialog
     * 
     * @param alertStr alert text will appear on the Dialog dialog can be closed
     *                 with the esc-key or an outside click
     */
    private void createAlert(String alertStr) {
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
