package App.Controllers;

import App.MainApp;
import DB.PizzaRecipe;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeController implements Initializable{

    @FXML private Label err_msg;
    @FXML private TextField txtpcode;
    @FXML private StackPane popup;
    @FXML private StackPane loading;
    @FXML private Button btnstart;
    @FXML private Button btnfind;
    @FXML private Button btnclose;
    @FXML private Button btnMenu;

    @FXML private Label lbdate;
    @FXML private Label lbSpecial1;
    @FXML private Label lbSpecial2;
    @FXML private ImageView imgSpecial1;
    @FXML private ImageView imgSpecial2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setSpecialDeal();
        popup.setVisible(false);
        loading.setVisible(false);
    }

    public void openPopup(){
        popup.setVisible(true);
    }
    public void closePopup(){
        resetField();
        popup.setVisible(false);
    }
    public void startLoading(){
        loading.setVisible(true);
    }
    public void doneLoading(){
        loading.setVisible(false);
    }


    @FXML public void onFind() {
        String postcode = txtpcode.getText();
        if (postcode.length() <= 0) {
            err_msg.setText("Please enter postal code");
            txtpcode.requestFocus();
            return;
        }
        String validpostcode = validatePostcode(postcode);
        if ( validpostcode == null) {
            err_msg.setText("Invalid postal code");
            txtpcode.requestFocus();
            return;
        }
        resetField();
        closePopup();
        startLoading();
        MainApp.findStore(validpostcode);
    }

    private String validatePostcode(String postcode){
        Pattern p = Pattern.compile("([a-zA-Z]\\d[a-zA-Z])\\s*(\\d[a-zA-Z]\\d)");
        Matcher m = p.matcher(postcode);
        if(m.matches()){
            return m.group(1) + m.group(2);
        }
        return null;
    }
    private void resetField(){
        txtpcode.clear();
        txtpcode.getStyleClass().remove("alert-danger");
        err_msg.setText("");
    }
    @FXML void switchMenu(){
        if(MainApp.selectedStoreLocation != null){
            MainApp.switchScene(MainApp.menuView);
        }else{
            openPopup();
        }
    }
    private void setSpecialDeal(){
        lbdate.setText(MainApp.todaySpecial.toString() + " SPECIALS");
        PizzaRecipe recipe1 = MainApp.todaySpecial.getFirstRecipe();
        PizzaRecipe recipe2 = MainApp.todaySpecial.getSecondRecipe();
        lbSpecial1.setText(recipe1.getName());
        lbSpecial2.setText(recipe2.getName());
        imgSpecial1.setImage(new Image(recipe1.getImg()[1]));
        imgSpecial2.setImage(new Image(recipe2.getImg()[1]));
    }
}
