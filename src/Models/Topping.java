package Models;

public class Topping {
    private String name;
    private Integer cals;
    private Double price;

    public Topping(){}
    public Topping(String name, Integer cals, Double price){
        this.name = name;
        this.cals = cals;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCals() {
        return cals;
    }

    public void setCals(Integer cals) {
        this.cals = cals;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
