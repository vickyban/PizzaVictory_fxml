package Models;

import DB.PizzaRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Pizza {
    private PizzaRecipe recipe;
    private String name;
    private Size size;
    private Crust crust;
    private Sauce sauce;
    private List<Topping> toppings;
    private Double price;

    public Pizza(PizzaRecipe name, Size size, Crust crust,Sauce sauce, List<Topping> toppings){
        this.recipe = name;
        this.name = this.recipe.getName();
        this.size = size;
        this.crust = crust;
        this.sauce = sauce;
        this.toppings = toppings;
        setPrice();
    }
    public void setPrice(){
        Double toppingSum = toppings.stream().mapToDouble(i -> i.getPrice()).reduce(0.0, (sum, i) -> sum+i);
        this.price = size.getPrice() + crust.getPrice() + toppingSum;
    }
    public PizzaRecipe getRecipe(){return recipe;}

    public String getName() {
        return name;
    }

    public Size getSize() {
        return size;
    }

    public Crust getCrust() {
        return crust;
    }

    public Sauce getSauce(){ return sauce; }

    public List<Topping> getToppings() {
        return toppings;
    }

    public Double getPrice(){ return price; }

    public void setRecipe(PizzaRecipe recipe){ this.recipe = recipe; }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void setCrust(Crust crust) {
        this.crust = crust;
    }

    public void setSauce(Sauce sauce) {
        this.sauce = sauce;
    }

    public void setToppings(List<Topping> toppings) {
        this.toppings = toppings;
    }

    @Override
    public String toString(){
        String toppingText = toppings.stream().map(i ->">" + i.getName()).collect(Collectors.joining("\n"));
        return  name + "\n" +
                size.getSize() + " " + crust.getName() + "\n>" +
                sauce.getName() + "\n" +
                toppingText;
    }

}
