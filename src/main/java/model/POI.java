package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class POI {

    private String name;
    private String address;
    private String photo_reference;
    private String link;

    public POI(String link) {
        this.link = link;
        try {
            this.parserPOI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parserPOI() throws IOException {


        String[] detailsLink = link.split("\\/");
        String placeName = detailsLink[5].trim();

        URL urlID = new URL("https://maps.googleapis.com/maps/api/place/findplacefromtext/" +
                "json?input=/" + placeName + "&inputtype=textquery&fields=place_id&key=AIzaSyCFHuvSLicFOEbrNAMgRkOL0HPbVKNLqhU");
        URLConnection connID = urlID.openConnection();
        BufferedReader rdID = new BufferedReader(new InputStreamReader(connID.getInputStream()));
        String lineID;
        StringBuilder resultID = new StringBuilder();
        while ((lineID = rdID.readLine()) != null) {
            resultID.append(lineID);
        }
        String ausgabeplaceID = resultID.toString();
        String placeIDshorted = ausgabeplaceID.
                replaceAll("\"", "").
                replaceAll(":", "").
                replaceAll("\\}", "").
                replaceAll("\\]", "").
                replaceAll("\\[", "");


        String[] splittedID = placeIDshorted.split("\\{");
        String[] statusPlaceID = splittedID[2].split(",");
        String status = statusPlaceID[1].replaceAll("status", "").trim();
        String placeID = statusPlaceID[0].replaceAll("place_id", "").trim();
        rdID.close();


        URL url = new URL("https://maps.googleapis.com/maps/api/place/details/json?" +
                "fields=name%2Cformatted_address%2Cphoto" +
                "&place_id=" + placeID +
                "&key=AIzaSyCFHuvSLicFOEbrNAMgRkOL0HPbVKNLqhU");
        URLConnection conn = url.openConnection();
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();

        String responsePOI = result.toString();
        String[] detailsPOI = responsePOI.split("\\{");

        String addressName = detailsPOI[2];
        String address = addressName.substring(addressName.indexOf(":") + 1, addressName.indexOf("name") - 2).replaceAll("\"", "").trim();
        String name = addressName.substring(addressName.indexOf("name"), addressName.indexOf("photos")).
                replaceAll("\"", "").
                replace("name :", "").trim();

        String detailsphotoID = detailsPOI[3];
        String photo_reference = detailsphotoID.substring(detailsphotoID.
                        indexOf("photo_reference"), detailsphotoID.indexOf("width")).
                replaceAll("\"", "").
                replaceAll("photo_reference :", "").
                replaceAll(",", "").trim();

        this.name = name;
        this.address = address;
        this.photo_reference = photo_reference;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoto_reference() {
        return photo_reference;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

}
