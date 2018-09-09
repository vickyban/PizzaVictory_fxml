package App.Controllers;

import App.MainApp;
import DB.Database;
import DB.PizzaRecipe;
import Models.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class OrderingController implements Initializable{
    // Edit pizza
    private boolean isEdit;
    private Pizza editingPizza;

    // Data
    private PizzaRecipe recipe;
    private static ObservableList<Size> sizeInfo;
    private static ObservableList<Crust> crustInfo;
    private static ObservableList<Sauce> sauceInfo;
    private static ObservableList<Topping> toppingInfo;

    private IntegerBinding numPizzaOrdered = Bindings.size(MainApp.pizzas);
    private SimpleIntegerProperty cal = new SimpleIntegerProperty(0);
    private SimpleDoubleProperty price = new SimpleDoubleProperty(0.0);

    //selectingItem group
    ToggleGroup sizeGroup;
    ToggleGroup crustGroup;
    ToggleGroup sauceGroup;
    private static ObservableList<CheckBox> toppingGroup;
    private static ObservableList<CheckBox> selectedToppingGroup = FXCollections.observableArrayList();

    @FXML private TitledPane sizePane;
    @FXML private TitledPane crustPane;
    @FXML private TitledPane saucePane;
    @FXML private TitledPane toppingPane;


    @FXML private Button btnAdd;
    @FXML private Button cart;

    @FXML private Label err_msg;
    @FXML private Label storeAddress;
    @FXML private ImageView pizzaImg;
    @FXML private Label pizzaName;
    @FXML private Label lbCal;
    @FXML private Label lbPrice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        storeAddress.textProperty().bind(MainApp.selectedAddress);
        numPizzaOrdered.addListener((observable, oldValue, newValue) -> cart.setText(newValue.toString()));
        lbPrice.textProperty().bind(price.asString("$%.02f"));
        lbCal.textProperty().bind(cal.asString("%d Cals/slice"));

        sizeInfo = Database.getSizeInfo();
        crustInfo = Database.getCrustInfo();
        sauceInfo = Database.getSauceInfo();
        toppingInfo = Database.getToppingInfo();

        getSizeElements();
        getCrustElements();
        getSauceElements();
        getToppingElements();

        sizeGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
                Size size = (Size) newToggle.getUserData();
                sizePane.setText("Size: " + size.getSize());
                price.setValue(price.getValue() + size.getPrice());
                cal.setValue(cal.getValue() + size.getCals());
                if(oldToggle != null) {
                    price.setValue(price.getValue() - ((Size) oldToggle.getUserData()).getPrice());
                    cal.setValue(cal.getValue() - ((Size)oldToggle.getUserData()).getCals());
                }
        });
        crustGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle)-> {
            Crust crust =  (Crust) newToggle.getUserData();
            crustPane.setText("Crust: " + crust.getName());
            price.setValue(price.getValue() + crust.getPrice());
            cal.setValue(cal.getValue() + crust.getCals());
            if(oldToggle != null) {
                price.setValue(price.getValue() - ((Crust) oldToggle.getUserData()).getPrice());
                cal.setValue(cal.getValue() - ((Crust)oldToggle.getUserData()).getCals());
            }
        });
        sauceGroup.selectedToggleProperty().addListener(e -> {
            String text ="Sauce: " + ((Sauce) sauceGroup.getSelectedToggle().getUserData()).getName();
            saucePane.setText(text);
        });

    }

    @FXML void addPizza(){
        if(selectedToppingGroup.size() == 0){
            err_msg.setText("Please Select at least one topping");
            return;
        }
        Size size = (Size) sizeGroup.getSelectedToggle().getUserData();
        Crust crust = (Crust) crustGroup.getSelectedToggle().getUserData();
        Sauce sauce = (Sauce) sauceGroup.getSelectedToggle().getUserData();
        List<Topping> toppings = selectedToppingGroup.stream().map(cb -> (Topping) cb.getUserData()).collect(Collectors.toList());
        cleanToppingSelection();
        if(isEdit){
            editingPizza.setSize(size);
            editingPizza.setCrust(crust);
            editingPizza.setSauce(sauce);
            editingPizza.setToppings(toppings);
            editingPizza.setPrice();
            MainApp.checkoutController.refreshTable();
            MainApp.checkoutController.updateSummary();

            isEdit = false;
            btnAdd.setText("Add");
        } else {
            Pizza pizza = new Pizza(recipe, size, crust, sauce, toppings);
            MainApp.pizzas.add(pizza);
        }
    }

    public void loadPage(PizzaRecipe recipe){
        isEdit = false;

        this.recipe = recipe;
        pizzaImg.setImage(new Image(recipe.getImg()[1]));
        pizzaName.setText(recipe.getName());
        btnAdd.setText("Add");
        cleanToppingSelection();
        closeTab();
        if(recipe.getToppings().length != 0) {
            List<String> toppings = Arrays.asList(recipe.getToppings());
            for(CheckBox cb : toppingGroup){
                Topping data = (Topping) cb.getUserData();
                if(toppings.contains(data.getName()))
                    cb.setSelected(true);
            }
        }
        sizeGroup.getToggles().get(0).setSelected(true);
        crustGroup.getToggles().get(0).setSelected(true);
        sauceGroup.getToggles().get(0).setSelected(true);
    }
    public void loadEditPizza(Pizza pizza){
        isEdit = true;
        editingPizza = pizza;

        pizzaImg.setImage(new Image(pizza.getRecipe().getImg()[1]));
        pizzaName.setText(pizza.getName());
        btnAdd.setText("Update");

        cleanToppingSelection();
        sizeGroup.getToggles().stream().filter(size -> (size.getUserData()) == pizza.getSize()).forEach(size -> size.setSelected(true));
        crustGroup.getToggles().stream().filter(crust -> (crust.getUserData()) == pizza.getCrust()).forEach(crust -> crust.setSelected(true));
        sauceGroup.getToggles().stream().filter(sauce -> (sauce.getUserData()) == pizza.getSauce()).forEach(sauce -> sauce.setSelected(true));
        toppingGroup.stream().filter(topping -> pizza.getToppings().contains( topping.getUserData())).forEach(topping -> topping.setSelected(true));
    }

    private void getSizeElements(){
        sizeGroup = new ToggleGroup();
        HBox box = new HBox();
        box.setSpacing(14);
        sizeInfo.forEach(size -> {
            VBox vb = new VBox();
            RadioButton rb = new RadioButton(size.getSize());
            rb.setUserData(size);
            rb.setToggleGroup(sizeGroup);
            Label detail = new Label( "       "+size.getDetail());
            vb.getChildren().addAll(rb,detail);
            box.getChildren().add(vb);
        } );
        sizePane.setContent(box);
    }
    private void getCrustElements(){
        crustGroup = new ToggleGroup();
        HBox box = new HBox();
        box.setSpacing(14);
        crustInfo.forEach(crust -> {
            VBox vb = new VBox();
            RadioButton rb = new RadioButton(crust.getName());
            rb.setUserData(crust);
            rb.setToggleGroup(crustGroup);
            Label detail = new Label( "       "+crust.getDescription());
            vb.getChildren().addAll(rb,detail);
            box.getChildren().add(vb);
        } );
        crustPane.setContent(box);
    }
    private void getSauceElements(){
        sauceGroup = new ToggleGroup();
        HBox box = new HBox();
        box.setSpacing(14);
        sauceInfo.forEach(sauce -> {
            VBox vb = new VBox();
            RadioButton rb = new RadioButton(sauce.getName());
            rb.setUserData(sauce);
            rb.setToggleGroup(sauceGroup);
            vb.getChildren().addAll(rb);
            box.getChildren().add(vb);
        } );
        saucePane.setContent(box);
    }
    private void getToppingElements(){
        toppingGroup = FXCollections.observableArrayList();
        ArrayList<VBox> boxes = new ArrayList<>();
        toppingInfo.forEach(topping -> {
            CheckBox cb = new CheckBox(topping.getName());
            cb.setUserData(topping);
            toppingGroup.add(cb);
            cb.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
                if(isSelected){
                    selectedToppingGroup.add(cb);
                    price.setValue(price.getValue() + ((Topping)cb.getUserData()).getPrice());
                    cal.setValue(cal.getValue() + ((Topping)cb.getUserData()).getCals());}
                else{
                    selectedToppingGroup.remove(cb);
                    price.setValue(price.getValue() - ((Topping)cb.getUserData()).getPrice());
                    cal.setValue(cal.getValue() - ((Topping)cb.getUserData()).getCals());}
            });
            Label lb = new Label(topping.getCals() + " Cals/slice");
            VBox box = new VBox();
            box.getChildren().addAll(cb,lb);
            boxes.add(box);
        });
        renderTopping(boxes);
    }
    private void renderTopping(ArrayList<VBox> boxes){
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        for(int i = 0; i< boxes.size(); i++){
            grid.add(boxes.get(i), i%4, i/4);
        }
        toppingPane.setContent(grid);
    }

    private void cleanToppingSelection(){
        err_msg.setText("");
        toppingGroup.stream().filter(cb -> cb.isSelected()).forEach(cb-> cb.setSelected(false));
    }
    private void closeTab(){
        sizePane.setExpanded(false);
        crustPane.setExpanded(false);
        saucePane.setExpanded(false);
        toppingPane.setExpanded(false);
    }

    @FXML void switchHome(){
        MainApp.switchScene(MainApp.homeView);
    }
    @FXML void switchMenu(){
        MainApp.switchScene(MainApp.menuView);
    }
    @FXML void switchCheckout(){
        MainApp.switchScene(MainApp.checkoutView);
    }

}
