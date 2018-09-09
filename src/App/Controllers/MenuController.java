package App.Controllers;

import App.MainApp;
import DB.DailySpecial;
import DB.PizzaRecipe;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    private DailySpecial todaySpecial;
    private IntegerBinding numPizzaOrdered = Bindings.size(MainApp.pizzas);

    @FXML private Label storeAddress;
    @FXML private Button cart;
    @FXML private Button ownPizza;

    @FXML private ImageView imgSpecial1;
    @FXML private ImageView imgSpecial2;
    @FXML private Button btnSpecial1;
    @FXML private Button btnSpecial2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        storeAddress.textProperty().bind(MainApp.selectedAddress);
        setTodaySpecial();
        numPizzaOrdered.addListener(((observable, oldValue, newValue) -> cart.setText(newValue.toString())));
    }
    private void setTodaySpecial(){
        PizzaRecipe recipe1 = MainApp.todaySpecial.getFirstRecipe();
        PizzaRecipe recipe2 = MainApp.todaySpecial.getSecondRecipe();
        ownPizza.setUserData(PizzaRecipe.OWN);
        imgSpecial1.setImage(new Image(recipe1.getImg()[0]));
        imgSpecial2.setImage(new Image(recipe2.getImg()[0]));

        btnSpecial1.setText(formatName(recipe1.getName()));
        btnSpecial2.setText(formatName(recipe2.getName()));
        btnSpecial1.setUserData(recipe1);
        btnSpecial2.setUserData(recipe2);
    }
    private String formatName(String name){
        int len = name.length();
        String spaces = new String(new char[30 - len]).replace("\0"," ");
        return name + spaces;
    }
    @FXML void switchToOrder(Event e){
        Button btn = (Button) e.getSource();
        MainApp.orderingController.loadPage((PizzaRecipe) btn.getUserData());
        MainApp.switchScene(MainApp.orderingView);
    }
    @FXML void switchHome(){
        MainApp.switchScene(MainApp.homeView);
    }
    @FXML void switchCheckout(){
        MainApp.switchScene(MainApp.checkoutView);
    }

}
