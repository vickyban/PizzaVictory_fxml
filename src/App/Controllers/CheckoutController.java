package App.Controllers;

import App.MainApp;
import Models.Address;
import Models.Pizza;
import Models.Topping;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class CheckoutController implements Initializable{
    private IntegerBinding numPizzaOrdered = Bindings.size(MainApp.pizzas);
    @FXML private Label storeAddress;
    @FXML private Label err_msg;
    @FXML private Button cart;

    @FXML private Label lbSubTotal;
    @FXML private Label lbTax;
    @FXML private Label lbTotal;
    Double subtotal;
    Double tax;
    Double total;

    @FXML private Button btnDelete;
    @FXML private Button btnEdit;
    @FXML private Button btnCheckout;
    @FXML private StackPane confirmationPane;
    @FXML private Label lbConfirmationN;
    @FXML private Label lbPickupAddress;

    @FXML private TableView<Pizza> tableView;
    @FXML private TableColumn<Pizza, String> colName;
    @FXML private TableColumn<Pizza, String> colDetail;
    @FXML private TableColumn<Pizza, String> colPrice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clearErrMsg();
        confirmationPane.setVisible(false);
        storeAddress.textProperty().bind(MainApp.selectedAddress);
        numPizzaOrdered.addListener(e -> {
            cart.setText(numPizzaOrdered.getValue().toString());
            updateSummary();
            if(numPizzaOrdered.getValue() == 0){
                disableEditTable(true);
                return;
            }
            clearErrMsg();
            disableEditTable(false);
        });
        disableEditTable(true);
        setTableView();
        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> err_msg.setText(""));
    }

    private void setTableView(){
        colName.setCellValueFactory(cell-> new SimpleStringProperty(cell.getValue().getName()));
        colDetail.setCellValueFactory(cell-> new SimpleStringProperty(cell.getValue().toString()));
        colPrice.setCellValueFactory(cell-> Bindings.format("$%.2f", new SimpleDoubleProperty(cell.getValue().getPrice())));
        colName.setStyle("-fx-alignment: TOP_CENTER;");
        colPrice.setStyle("-fx-alignment: TOP_CENTER;");
        tableView.setItems(MainApp.pizzas);
    }

    @FXML void switchHome(ActionEvent event) {
        MainApp.switchScene(MainApp.homeView);
    }
    @FXML void switchMenu(ActionEvent event){
        MainApp.switchScene(MainApp.menuView);
    }

    @FXML void onDelete(){
        if(!MainApp.pizzas.remove(tableView.getSelectionModel().getSelectedItem()));
            err_msg.setText("No Item is selected");
    }

    @FXML void onEdit(){
        Pizza selectedPizza = tableView.getSelectionModel().getSelectedItem();
        if(selectedPizza == null){
            err_msg.setText("No Item is selected");
            return;
        }
        MainApp.orderingController.loadEditPizza(selectedPizza);
        MainApp.switchScene(MainApp.orderingView);
    }

    @FXML void onCheckout(){
        if(numPizzaOrdered.getValue() == 0 ){
            err_msg.setText("No Item in cart");
            return;
        }
        try{
            File file = new File("receipt.txt");
            if(!file.exists())
                file.createNewFile();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            Address address = MainApp.selectedStoreLocation;
            String cityProvince = address.getCity() + ", " + address.getProvince();
            int n1 = (30 - address.getStreet().length())/2;
            String spaces1 = new String(new char[n1]).replace("\0", " ");
            int n2 = (30 - cityProvince.length())/2;
            String spaces2 = new String(new char[n2]).replace("\0", " ");

            bw.newLine();
            bw.write("        Pizza Victoria");
            bw.newLine();
            bw.write(spaces1 + address.getStreet());
            bw.newLine();
            bw.write(spaces2 + cityProvince);
            bw.newLine();
            bw.write("           "+address.getPostalcode());
            bw.newLine();
            bw.write("================================");
            bw.newLine();

            for(Pizza pizza : MainApp.pizzas) {
                int nspaces = 25 - pizza.getName().length();
                String spaces = new String(new char[nspaces]).replace("\0", " ");

                bw.write(pizza.getName() + spaces + String.format("$%.2f",pizza.getPrice()));
                bw.newLine();
                bw.write(pizza.getSize().getSize() + " " + pizza.getCrust().getName());
                bw.newLine();
                for (Topping e : pizza.getToppings()){
                    bw.write(" >" + e.getName());
                    bw.newLine();
                }
                bw.newLine();
                bw.newLine();
            }
            bw.write("================================");
            bw.newLine();
            bw.write("Subtotal                 $" +  String.format("%.2f",this.subtotal));
            bw.newLine();
            bw.write("Taxes(13%)               $" + String.format("%.2f",this.tax));
            bw.newLine();
            bw.write("Total                    $" + String.format("%.2f",this.total));
            bw.newLine();
            bw.close();

            MainApp.pizzas.clear();
            displayConfirmationPane();
        }catch (IOException err){
            System.out.println(err);
        }
    }

    public void refreshTable(){
        tableView.refresh();
    }

    private void clearErrMsg(){
        err_msg.setText("");
    }

    private void disableEditTable(boolean disable){
        btnEdit.setDisable(disable);
        btnDelete.setDisable(disable);
    }

    public void updateSummary(){
        this.subtotal = MainApp.pizzas.stream().mapToDouble(pizza -> pizza.getPrice()).reduce(0.0,(sum, next) -> sum + next);
        this.tax = subtotal * .13;
        this.total = subtotal + tax;
        lbSubTotal.setText(String.format("$%.2f",subtotal));
        lbTax.setText(String.format("$%.2f",tax));
        lbTotal.setText(String.format("$%.2f",total));
    }
    private void displayConfirmationPane(){
        int confirmationNum = generateRandomN();
        lbConfirmationN.setText(""+confirmationNum);
        lbPickupAddress.setText(MainApp.selectedAddress.getValue());
        confirmationPane.setVisible(true);
    }
    private int generateRandomN(){
        Random random = new Random();
        return 100000000 + random.nextInt(900000000);
    }
    @FXML void onFinish(){
        MainApp.selectedStoreLocation = null;
        MainApp.switchScene(MainApp.homeView);
        confirmationPane.setVisible(false);
    }
}
