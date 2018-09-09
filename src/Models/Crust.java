package Models;

public class Crust {
    private String name;
    private String description;
    private Integer cals;
    private Double price;

    public Crust(){}
    public Crust(String name, String description,Integer cals, Double price){
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
