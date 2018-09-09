package DB;

import Models.Crust;
import Models.Sauce;
import Models.Size;
import Models.Topping;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {
    public static ObservableList<Size> getSizeInfo(){
        return FXCollections.observableArrayList(
                new Size("9\" Small", "6 slices, 190 Cals/slice",0,10.0),
                new Size("12\" Medium", "8 slices, 210-320 Cals/slice",10, 12.0),
                new Size("14\" Large", "12 slices, 200-240 Cals/slice",20,15.0)
        );
    }
    public static ObservableList<Crust> getCrustInfo(){
        return FXCollections.observableArrayList(
                new Crust("Thin and Crispy" ,"120 Cals/slice",120, 1.0 ),
                new Crust("Stuffed Crust","160 Cals/slice",160, 3.75 ),
                new Crust("Pan Pizza","130 Cals/slice",130, 2.0 )
        );
    }
    public static ObservableList<Sauce> getSauceInfo(){
        return FXCollections.observableArrayList(
                new Sauce("Tomato Sauce", "TS"),
                new Sauce("Beef Gravy", "BG"),
                new Sauce("BBQ Sauce", "BS"),
                new Sauce("Old World Tomato Sauce", "OTS")
        );
    }
    public static ObservableList<Topping> getToppingInfo(){
        return FXCollections.observableArrayList(
                new Topping("Pepperoni", 20, 0.99),
                new Topping("Bacon Crumble", 25, 0.99),
                new Topping("Bacon", 130, 1.99),
                new Topping("Ham", 10, 1.5),
                new Topping("Italian Sausage", 40, 2.99),
                new Topping("Mild Sausage", 20, 1.5),
                new Topping("Beef", 20, 1.5),
                new Topping("Grilled Chicken", 10, 2.0),
                new Topping("Mushroom",  5, 2.0),
                new Topping("Black Olives", 10, 0.99),
                new Topping("Green Peppers", 5, 1.5),
                new Topping("Red Onions", 5, 0.50),
                new Topping("Pineapples", 10, 1.5),
                new Topping("Marinated tomatoes", 5, 2.5),
                new Topping("Parmesan", 10, 0.99),
                new Topping("Feta Cheese", 25, 1.99)
        );
    }
}
