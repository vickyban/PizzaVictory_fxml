package DB;

import App.Controllers.LocationController;
import App.MainApp;
import Models.Address;
import javafx.concurrent.Task;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StoreLocation extends Task<Void> {
    private String postcode;
    private ArrayList<Address> fullAddresses;
    private boolean Switch;
    private boolean success;


    public StoreLocation(String postcode, boolean Switch){
        this.postcode = postcode;
        this.Switch = Switch;
    }

    @Override
    protected Void call() throws Exception {
        System.out.println("Fetching api");
        this.find();
        return null;
    }
    @Override
    protected void failed(){
        System.out.println("failed");
    }
    @Override
    protected void succeeded(){
        System.out.println("Done fetching");
        if(success){
            MainApp.locationController.setMap(postcode);
            MainApp.locationController.updateAddressList(fullAddresses);
            System.out.println("Result: " + fullAddresses.toString() );
        }else{
            MainApp.locationController.setNoFound("We are unable to find stores in your area\nPlease try using a different address");
        }

        if(Switch)
            MainApp.switchToLocation();
        else
            MainApp.locationController.doneLoading();
    }

    private void find() throws Exception{
        fullAddresses = new ArrayList<>();
        try {
            String latlng = getLocation(postcode).get("latLng");
            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/xml?" +
                    "location=" + latlng +
                    "&radius=3000" +
                    "&type=restaurant&keyword=Pizzahut" +
                    "&key=" + System.getenv("API_KEY");
            String response = sendRequest(url);
            ArrayList<String> semiAddresses = XMLParser.getNearbyStoreAddresses(response);
            System.out.println(semiAddresses);
            Pattern p = Pattern.compile("\\s|#");
            for(String address : semiAddresses){
                System.out.println("Before match");
                Matcher m = p.matcher(address);
                address = m.replaceAll( "+");
                String tmp = getLocation(address).get("formatted_address");
                System.out.println(tmp);
                fullAddresses.add(new Address(tmp));
            }
            success = true;
        } catch (Exception e) {     // No result
            System.out.println(e.getMessage());
        }

    }

    private static HashMap<String, String> getLocation(String postalcode) throws Exception {
        String url = "https://maps.googleapis.com/maps/api/geocode/xml" +
                "?address=" + postalcode +
                "&key=" + System.getenv("API_KEY");
        String response = sendRequest(url);
        HashMap<String,String> location = XMLParser.getLocation(response);
        return location;
    }

    private static String sendRequest(String url) throws Exception {
        URL obj = new URL(url);
        HttpsURLConnection com = (HttpsURLConnection) obj.openConnection();
        com.setRequestMethod("GET");
        com.setRequestProperty("User-Agent", "Chrome/67.0.3396.99");
        int responseCode = com.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(com.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append((inputLine));
            }
            return response.toString();

        }else{
            throw new Exception("No result");
        }
    }


    // nearby address   https://maps.googleapis.com/maps/api/place/nearbysearch/json/location=lat,lng&radius=1500&type=restaurant&keyword=pizzahut&key=KEY
    // find address     https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=YOUR_API_KEY

}
