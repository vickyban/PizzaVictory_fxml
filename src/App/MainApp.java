package App;

import App.Controllers.*;

import DB.DailySpecial;
import DB.StoreLocation;
import Models.*;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainApp extends Application {
    public static DailySpecial todaySpecial;

    public static ObservableList<Pizza> pizzas = FXCollections.observableArrayList();
    public static Address selectedStoreLocation;
    public static StringProperty selectedAddress = new SimpleStringProperty("-");


    //App.Controllers
    //public static DataTableController dataTableController;
    public static HomeController homeController;
    public static LocationController locationController;
    public static MenuController menuController;
    public static OrderingController orderingController;
    public static CheckoutController checkoutController;

    // Stage
    public static Stage mainStage = new Stage();
    public static Scene mainScene;

    // Parent rootNode
    public static Parent homeView;
    public static Parent locationView;
    public static Parent menuView;
    public static Parent orderingView;
    public static Parent checkoutView;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Calendar calendar = new GregorianCalendar();
        int today = calendar.get(Calendar.DAY_OF_WEEK ) - 1  ;
        todaySpecial = DailySpecial.values()[today];
        System.out.println(todaySpecial);


        //FXMLLoader f0 = new FXMLLoader(getClass().getResource("App.Pages/DataTable.fxml"));
        FXMLLoader f1 = new FXMLLoader(getClass().getResource("Pages/Home.fxml"));
        FXMLLoader f2 = new FXMLLoader(getClass().getResource("Pages/Location.fxml"));
        FXMLLoader f3 = new FXMLLoader(getClass().getResource("Pages/Menu.fxml"));
        FXMLLoader f4 = new FXMLLoader(getClass().getResource("Pages/Ordering.fxml"));
        FXMLLoader f5 = new FXMLLoader(getClass().getResource("Pages/Checkout.fxml"));

        //Parent dataTable = f0.load();
        homeView = f1.load();
        locationView = f2.load();
        menuView = f3.load();
        orderingView = f4.load();
        checkoutView = f5.load();

        //dataTableController = f0.getController();
        homeController = f1.getController();
        locationController = f2.getController();
        menuController = f3.getController();
        orderingController = f4.getController();
        checkoutController = f5.getController();

        mainScene = new Scene(homeView);

        mainStage.setScene(mainScene);
        mainStage.setTitle("Pizza Victoria Home");
        mainStage.show();

    }

    public static void findStore(String postcode){
        Task<Void> task = new StoreLocation(postcode, true);
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
    public static void switchToLocation(){
        homeController.doneLoading();
        switchScene(locationView);
    }

    public static void switchScene(Parent rootNode){
        mainScene.setRoot(rootNode);
    }

    public static void main(String[] args){launch(args);}
}
