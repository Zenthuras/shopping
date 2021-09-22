package cz.uhk.fim.pro2.shopping.controller;

import cz.uhk.fim.pro2.shopping.model.*;
import cz.uhk.fim.pro2.shopping.utils.DataGenerator;
import cz.uhk.fim.pro2.shopping.utils.FileUtils;
import cz.uhk.fim.pro2.shopping.utils.XPathGenerator;
import cz.uhk.fim.pro2.shopping.utils.parser.CSVParser;
import cz.uhk.fim.pro2.shopping.utils.parser.JAXBParser;
import cz.uhk.fim.pro2.shopping.utils.parser.JSONParser;
import jakarta.xml.bind.JAXBException;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Trida predstavujici controller pro praci s UI
 * - implementuje rozhrani Initializable pro inicializaci
 * jednotlivych komponent, aby je bylo mozne v kodu pouzit
 */
public class MainController implements Initializable {

    /*
    Komponenty uzivatelskeho prostredi:
    - datove typy odpovidaji typu komponenty
    - nazev promenne odpovida nazvu fx:id v .fxml souboru/prostredi SceneBuilderu
    - pro propojeni s .fxml slouzi anotace @FXML
     */
    @FXML
    private Label lblDisplayName;
    @FXML
    private Label lblBudget;
    @FXML
    private Label lblAge;
    @FXML
    private Label lblPersonalId;
    @FXML
    private Label lblWeight;
    @FXML
    private Label lblPrice;
    @FXML
    private Label lblUser;
    @FXML
    private Label lblTotalItems;
    @FXML
    private Label lblTotalPrice;
    @FXML
    private Label lblBudgetCart;
    @FXML
    private DatePicker dtBirthDate;
    @FXML
    private CheckBox checkboxRightRace;
    @FXML
    private Slider sldWeight;
    @FXML
    private TableView<Child> offerTable;
    @FXML
    private TableView<Child> cartTable;
    @FXML
    private TableColumn<Child, String> colId;
    @FXML
    private TableColumn<Child, String> colName;
    @FXML
    private TableColumn<Child, Integer> colAge;
    @FXML
    private TableColumn<Child, GenderType> colGender;
    @FXML
    private TableColumn<Child, String> colPrice;
    @FXML
    private TableColumn<Child, String> colId2;
    @FXML
    private TableColumn<Child, String> colName2;
    @FXML
    private TableColumn<Child, Integer> colAge2;
    @FXML
    private TableColumn<Child, GenderType> colGender2;
    @FXML
    private TableColumn<Child, String> colPrice2;
    @FXML
    private Tab tabDetail;
    @FXML
    private ImageView childImage;
    @FXML
    private ImageView genderImage;
    @FXML
    private Button btnLoadMarketplace;
    @FXML
    private Button btnAddChildToCart;
    @FXML
    private Button btnSaveOffers;
    @FXML
    private Button btnMakeOrder;
    @FXML
    private Button btnClearFilters;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnLoadMarketplaceXPath;
    @FXML
    private ChoiceBox filterGender;
    @FXML
    private TextField filterAgeMin;
    @FXML
    private TextField filterAgeMax;
    @FXML
    private TextField filterPriceMin;
    @FXML
    private TextField filterPriceMax;


    // reference na nakupni kosik
    private ShoppingCart cart;
    // reference na marketplace
    private Marketplace marketplace;
    // reference na aktualni zobrazene dite
    private Child currentChild;
    // reference na zákazníka
    private Customer customer;

    private ObservableList<Child> offerList;
    private List<Child> childList;
    private ObservableList<Child> childListTemp = FXCollections.observableArrayList(); // seznam dětí v košíku

    public MainController() {
    }

    /**
     * Metoda pro inicializaci nakupniho kosiku
     */
    private void initCart() {
        this.cart = new ShoppingCart();
        this.cart.setVat(0.21);
    }

    /**
     * Initializacni metoda pro vytvoreni noveho marketplacu
     */
    private void initMarketplace() throws IOException {
        this.marketplace = new Marketplace();
    }

    private void initCustomer() {
        customer = new Customer(123, this.cart, "Josef Fritzl", "password", "e-mail", null, 30000.0);
    }

    /**
     * Inicializacni metoda pro tabulku nabidek
     */
    private void initTableView() {
        // nastaveni sloupcu pro zobrazeni spravne hodnoty a korektniho datoveho typu
        colId.setCellValueFactory(cellData -> cellData.getValue().getPersonalIdProperty());
        colName.setCellValueFactory(cellData -> cellData.getValue().getDisplayNameProperty());
        colAge.setCellValueFactory(cellData -> cellData.getValue().getAgeProperty().asObject());
        colGender.setCellValueFactory(cellData -> cellData.getValue().getGenderProperty());
        colPrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty());

        colId2.setCellValueFactory(cellData -> cellData.getValue().getPersonalIdProperty());
        colName2.setCellValueFactory(cellData -> cellData.getValue().getDisplayNameProperty());
        colAge2.setCellValueFactory(cellData -> cellData.getValue().getAgeProperty().asObject());
        colGender2.setCellValueFactory(cellData -> cellData.getValue().getGenderProperty());
        colPrice2.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty());

        // nastaveni listu prvku tabulce
        offerTable.setItems(this.marketplace.getOfferList());
        cartTable.setItems(FXCollections.observableArrayList(this.cart.getChildList()));

        // listener pro zjisteni, ktery prvek tabulky uzivatel oznacil a naplneni aktualni reference na vybrane dite
        offerTable.getSelectionModel().getSelectedCells().addListener(new ListChangeListener<TablePosition>() {
            @Override
            public void onChanged(Change<? extends TablePosition> change) {
                TableView.TableViewSelectionModel selectionModel = offerTable.getSelectionModel();
                ObservableList selectedCells = selectionModel.getSelectedCells();
                TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                currentChild = marketplace.getOfferList().get(tablePosition.getRow());
                System.out.println(currentChild);
                try {
                    updateUi();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Metoda, ktera inicializuje uzivatelske rozhrani
     * - inicializuje nastaveni tabulky nabidek
     * - pro kartu detailu - znepristupnuje editaci pole s datem narozeni, slideru s vahou a checkboxem se spravnou rasou
     * - po startu aplikace pred vyberem nejakeho ditete je karta detailu vypnuta
     */
    private void initUi() {
        initTableView();
        initComponents();

    }

    private void initComponents() {
        lblBudget.setText(String.valueOf(customer.getBudget()));
        lblBudgetCart.setText(String.valueOf(customer.getBudget()));
        lblTotalPrice.setText(String.valueOf(this.cart.calculateTotal()));
        lblTotalItems.setText(String.valueOf(this.cart.getChildCount()));
        lblUser.setText(customer.getUsername());
        dtBirthDate.setDisable(true);
        sldWeight.setDisable(true);
        checkboxRightRace.setDisable(true);
        tabDetail.setDisable(true);

        filterGender.setValue("OBĚ");
        filterGender.getItems().add("MUŽ");
        filterGender.getItems().add("ŽENA");
        filterGender.getItems().add("OBĚ");

        //inicializace tlačítek
        btnLoadMarketplace.setOnAction(e -> {
            try {
                loadMarketplace();
            } catch (IOException | XPathExpressionException | SAXException | ParserConfigurationException | ParseException ioException) {
                ioException.printStackTrace();
            }
        });

        btnLoadMarketplaceXPath.setOnAction(e -> {
            try {
                loadMarketplaceXPath();
            } catch (SAXException saxException) {
                saxException.printStackTrace();
            } catch (ParserConfigurationException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            } catch (XPathExpressionException xPathExpressionException) {
                xPathExpressionException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        btnAddChildToCart.setOnAction(e -> addChildToShoppingCart());
        btnSaveOffers.setOnAction(e -> {
            try {
                saveOffers();
            } catch (JAXBException jaxbException) {
                jaxbException.printStackTrace();
            }
        });
        btnClearFilters.setOnAction(e -> clearFilters());
        btnDelete.setOnAction(e -> deleteChildFromShoppingCart());
        btnMakeOrder.setOnAction(e -> makeOrder());

        ChangeListener<String> listener = ((observable, oldValue, newValue) -> {
            filter();
            updateCart();
        });

        //listenery pro změnu v komponentách
        filterGender.getSelectionModel().selectedItemProperty().addListener(listener);
        filterPriceMin.textProperty().addListener(listener);
        filterPriceMax.textProperty().addListener(listener);
        filterAgeMin.textProperty().addListener(listener);
        filterAgeMax.textProperty().addListener(listener);

    }

    private void updateCart() {
        lblBudget.setText(String.valueOf(customer.getBudget()));
        lblBudgetCart.setText(String.valueOf(customer.getBudget()));
        lblTotalPrice.setText(String.valueOf(this.cart.calculateTotal()));
        lblTotalItems.setText(String.valueOf(this.cart.getChildCount()));
        lblUser.setText(customer.getUsername());
    }


    /**
     * OnClick Listener tlacitka "Pridat do kosiku"
     */
    public void addChildToShoppingCart() {
        Child child = currentChild;
        childListTemp.add(child);
        this.cart.addChild(child);
        cartTable.setItems(childListTemp);
        marketplace.removeOffer(currentChild);
        offerTable.refresh();
        cartTable.refresh();
        updateCart();
    }

    public void deleteChildFromShoppingCart() {
        Child child = currentChild;
        this.marketplace.addOffer(currentChild);
        offerTable.setItems(this.marketplace.getOfferList());
        this.cart.removeChild(currentChild);
        childListTemp.remove(currentChild);
        cartTable.setItems(childListTemp);
        offerTable.refresh();
        cartTable.refresh();
    }

    /**
     * OnClick Listener tlacitka "Nacteni marketu"
     */
    public void loadMarketplace() throws IOException, XPathExpressionException, SAXException, ParserConfigurationException, ParseException {
        DataGenerator generator = new DataGenerator();
        ObservableList<Child> newList = generator.generateOffers(30);
        offerTable.setItems(newList);
        offerTable.refresh();
    }

    public void loadMarketplaceXPath() throws SAXException, ParserConfigurationException, ParseException, XPathExpressionException, IOException {
        ObservableList<Child> xPathList = XPathGenerator.generate();
        offerTable.setItems(xPathList);
        offerTable.refresh();
    }

    /**
     * OnClick Listener tlacitka "Ulozeni nabidky"
     */
    public void saveOffers() throws JAXBException {
        List<Child> objects = new ArrayList<>();
        for (Child child : this.marketplace.getOfferList()) {
            objects.add(child);
        }
        JSONParser.generateJson("deti", objects);

        JAXBParser.marshal(this.marketplace.getOfferList());
    }

    private void makeOrder() {
        if (customer.getBudget() >= cart.calculateTotal()) {
            customer.setBudget(customer.getBudget() - cart.calculateTotal());
            CSVParser.generateCsv("order", this.cart.getChildList());
            childListTemp.clear();
            childList = new ArrayList<>(childListTemp);
            cartTable.setItems(childListTemp);
            cartTable.refresh();
            updateCart();
        }
    }

    /**
     * OnClick Listener tlacitka "Vymazani filtru"
     */
    public void clearFilters() {
        filterGender.setValue("OBĚ");
        filterPriceMin.setText("0");
        filterPriceMax.setText("50000");
        filterAgeMin.setText("1");
        filterAgeMax.setText("15");
        offerTable.refresh();
    }

    public void filter() {
        String genderValue = (String) filterGender.getValue();
        String minPriceText = filterPriceMin.getText();
        String maxPriceText = filterPriceMax.getText();
        String minAgeText = filterAgeMin.getText();
        String maxAgeText = filterAgeMax.getText();

        if (!minPriceText.equals("") && !maxPriceText.equals("") && !minAgeText.equals("") && !maxAgeText.equals("") && !genderValue.equals("")) {

            try {
                double minPrice = Double.parseDouble(minPriceText);
                double maxPrice = Double.parseDouble(maxPriceText);
                int minAge = Integer.parseInt(minAgeText);
                int maxAge = Integer.parseInt(maxAgeText);

                if (genderValue.equals("MUŽ")) {
                    List<Child> filteredList = this.marketplace.getOfferList().stream()
                            .filter(child ->
                                    child.getPrice() >= minPrice &&
                                            child.getPrice() <= maxPrice &&
                                            child.getAge() >= minAge &&
                                            child.getAge() <= maxAge &&
                                            child.getGender() == GenderType.MALE
                            )
                            .collect(Collectors.toList());
                    ObservableList<Child> newList = FXCollections.observableArrayList(filteredList);

                    offerTable.setItems(newList);
                    offerTable.refresh();
                }

                if (genderValue.equals("ŽENA")) {
                    List<Child> filteredList = this.marketplace.getOfferList().stream()
                            .filter(child ->
                                    child.getPrice() >= minPrice &&
                                            child.getPrice() <= maxPrice &&
                                            child.getAge() >= minAge &&
                                            child.getAge() <= maxAge &&
                                            child.getGender() == GenderType.FEMALE
                            )
                            .collect(Collectors.toList());
                    ObservableList<Child> newList = FXCollections.observableArrayList(filteredList);

                    offerTable.setItems(newList);
                    offerTable.refresh();
                }

                if (genderValue.equals("OBĚ")) {
                    List<Child> filteredList = this.marketplace.getOfferList().stream()
                            .filter(child ->
                                    child.getPrice() >= minPrice &&
                                            child.getPrice() <= maxPrice &&
                                            child.getAge() >= minAge &&
                                            child.getAge() <= maxAge
                            )
                            .collect(Collectors.toList());
                    ObservableList<Child> newList = FXCollections.observableArrayList(filteredList);

                    offerTable.setItems(newList);
                    offerTable.refresh();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Metoda pro aktualizaci uzivatelskeho prostredi
     */
    private void updateUi() throws FileNotFoundException {
        tabDetail.setDisable(false);
        lblDisplayName.setText(this.currentChild.getDisplayName());
        lblPersonalId.setText(String.format("(%s)", this.currentChild.getPersonalId()));
        lblPrice.setText(String.format("%.2f", this.currentChild.getPrice()));
        lblAge.setText(String.valueOf(this.currentChild.getAge()));
        lblWeight.setText(String.format("%.1f kg", this.currentChild.getWeight()));
        dtBirthDate.setValue(this.currentChild.getBirthDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        sldWeight.setValue(this.currentChild.getWeight());
        checkboxRightRace.setSelected(!this.currentChild.isRace());
        childImage.setImage(this.currentChild.getAvatar());

        Image gender;
        if (this.currentChild.getGender() == GenderType.MALE) {
            gender = FileUtils.loadImage("gender/boy.png");
        } else {
            gender = FileUtils.loadImage("gender/girl.png");
        }
        genderImage.setImage(gender);
    }


    // TODO [assignment_final] implementace UI kosiku:
    //  UI bude obsahovat:
    //  - tabulku se vsemi prvky v kosiku (v tabulce budou vsechny atributy, ktere),
    //  - celkovy pocet prvku v kosiku,
    //  - celkovy soucet (bez DPH a s DPH),
    //  - ovladaci prvky pro: odebrani vybraneho prvku z kosiku (po odebrani se prvek vrati zpet do nabidek) a dokonceni objednavky (pokud bude celkovy soucet s DPH vetsi nez dostupny budget, nedovolte dokoncit objednavku)
    //  Dokoncena objednavka se ulozi do souboru, kosik se vyprazdni, zakaznikovi se odecte castka od jeho budgetu.
    //      Soubor s dokoncenou objednavkou: nazev si vyberte, format CSV
    //      - do toho stejneho souboru uvedte (v uvodu) informace o objednavce - id a username zakaznika, celkovy soucet bez a s DPH

    /**
     * Metoda, ktera je volana pri inicializaci UI/kontroleru
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCart();
        try {
            initMarketplace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initCustomer();
        initUi();
    }
}
