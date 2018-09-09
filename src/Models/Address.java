package Models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Address {
    private String name;
    private String street;
    private String postalcode;
    private String province;
    private String city;
    public Address(String fullAddress){
//        String[] parts = fullAddress.trim().split(", ");
//        String subparts = parts[2];
        Pattern p = Pattern.compile("(.*), (.*), (.{1,2}) (.*),.*");
        Matcher m = p.matcher(fullAddress);
        m.matches();
        this.name = "Pizza Victoria";
        this.street = m.group(1);
        this.city = m.group(2);
        this.province = m.group(3);
        this.postalcode = m.group(4);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString(){
        return String.format("%s, %s, %s %s", street, city, province, postalcode);
    }

}
