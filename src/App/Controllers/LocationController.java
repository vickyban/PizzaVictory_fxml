package App.Controllers;

import App.MainApp;
import DB.StoreLocation;
import Models.Address;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocationController  implements Initializable {
    private ObservableList<Address> addressList = null;

    @FXML private Button btnHome;
    @FXML private Label err_msg;
    @FXML private TextField txtpcode;
    @FXML private Button btnfind;
    @FXML private ListView<Address> listViewAddresses;
    @FXML private StackPane loading;

    @FXML private WebView googleMap;
    private WebEngine engine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        engine = googleMap.getEngine();
        URL url = this.getClass().getResource("../GoogleMap.html");
        engine.load(url.toString());
        loading.setVisible(false);
        listViewAddresses.getSelectionModel().selectedItemProperty().addListener(e -> {
            Address selectedAddress = listViewAddresses.getSelectionModel().getSelectedItem();
            MainApp.selectedStoreLocation = selectedAddress;
            MainApp.selectedAddress.set(selectedAddress.toString());
            MainApp.switchScene(MainApp.menuView);
        });
    }

    public void updateAddressList(ArrayList<Address> list){
        this.addressList = FXCollections.observableArrayList(list);
        listViewAddresses.setItems(addressList);
    }

    public void onFind(){
        String postcode = txtpcode.getText();
        if (postcode.length() <= 0) {
            setErrorMsg("Please enter postal code");
            txtpcode.requestFocus();
            return;
        }
        String validpostcode = validatePostcode(postcode);
        if ( validpostcode == null) {
            setErrorMsg("Invalid postal code");
            txtpcode.requestFocus();
            return;
        }
        resetField();
        startLoading();
        fetchApi(validpostcode);
    }

    private void fetchApi(String postcode){
        Task<Void> task = new StoreLocation(postcode, false);
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
    public void setMap(String postcode){
        engine.executeScript("document.find(\"" + postcode + "\")");
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
        setErrorMsg("");
    }
    public void setErrorMsg(String msg){
        err_msg.setText(msg);
    }
    public void setNoFound(String err){
        setErrorMsg(err);
        engine.executeScript("document.resetMap()");
        if(addressList != null)
            addressList.clear();
    }
    private void startLoading(){
        loading.setVisible(true);
    }
    public void doneLoading(){
        loading.setVisible(false);
    }
    @FXML void switchHome(){
        MainApp.switchScene(MainApp.homeView);
    }
}
