package Models;

public class Size {
    private String size;
    private String detail;
    private Integer cals;
    private Double price;

    public Size(){}
    public Size(String size,String detail, Integer cals, Double price){
        this.size = size;
        this.detail = detail;
        this.cals = cals;
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getCals() {
        return cals;
    }

    public void setCals(Integer cals) {
        this.cals = cals;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
