package DB;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

public class XMLParser {
    public static HashMap<String, String> getLocation(String xml) throws IOException, SAXException, ParserConfigurationException {
        // Build DOM
        Document document = getDocument(xml);
        // Get Elemets from DOM
        Element geocodeResponse = (Element)document.getElementsByTagName("GeocodeResponse").item(0);
        String formatted_address = geocodeResponse.getElementsByTagName("formatted_address").item(0).getTextContent();
        Element location = (Element) geocodeResponse.getElementsByTagName("location").item(0);
        String lat = location.getElementsByTagName("lat").item(0).getTextContent();
        String lng = location.getElementsByTagName("lng").item(0).getTextContent();

        HashMap<String, String> res = new HashMap<>();
        res.put("formatted_address", formatted_address);
        res.put("latLng", String.format("%s,%s",lat,lng));
        return res;
    }

    public static ArrayList<String> getNearbyStoreAddresses(String xml) throws IOException, SAXException, ParserConfigurationException {
        Document document = getDocument(xml);

        Element placeSearchResponse = (Element) document.getElementsByTagName("PlaceSearchResponse").item(0);
        NodeList results = placeSearchResponse.getElementsByTagName("result");
        ArrayList<String> addresses = new ArrayList<>();
        for(int index = 0 ; index < results.getLength(); index++){
            Element result = (Element)results.item(index);
            String address = result.getElementsByTagName("vicinity").item(0).getTextContent();
            addresses.add(address);
        }
        //System.out.println(addresses);
        return addresses;
    }

    private static Document getDocument(String docStr) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource source = new InputSource(new StringReader(docStr));
        // Document
        return builder.parse(source);
    }
}
