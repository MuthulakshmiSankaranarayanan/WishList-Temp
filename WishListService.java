package com.teqtron.wishlist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    @Autowired
    private WishListRepository wishListRepository;    

    // Body of the request is saved in the wishlist Table
    public WishListModel addWishList(WishListModel wishListModel) {
        Optional<WishListModel> response = wishListRepository.findById(wishListModel.getWishListId());
        if (response.isPresent())
            return null;
        else
            return wishListRepository.save(wishListModel);
    }

    /*
     * Given the user ID, list of item IDs are retrieved from the wishlist table.
     * Product details for the list of item IDS 
     * are retrieved from an API and the response is converted to String 
     * and the String is returned.
     */
    public String getWishLists(int userId) throws IOException {
        
         List<WishListModel> wishes = new ArrayList<>();
         StringBuilder items = new StringBuilder();

         //Fetching all the wishlisted items for the given user and adding it to a list
         wishes = wishListRepository.findByWishListIdUserId(userId);

         //If there is no wishlisted item for that user , it returns null
         if (wishes.size() == 0)
            return null;

         // If there are some items ,
         // those items are added to a String builder wrapping it with double quotes and separating by comma
         for( WishListModel wish : wishes){
            String itemNumber = wish.getWishListId().getItemNumber();
            items.append("\"");
            items.append(itemNumber);
            items.append("\",");
         }
         items.deleteCharAt(items.length()-1);
         
         // Forming a String to hold the request body of HttpPost method
         // appending it with the String of items built.
         String requestBody = "{\"catalog\": {"
         +"\"@xmlns\": \"http://xmlns.oracle.com/apps/per/rest/catalog/GET_CATALOG_ITEMS\","
         +"\"RESTHeader\": {"
         +"\"xmlns\": \"http://xmlns.oracle.com/apps/per/rest/catalog/header\","
         +"\"Responsibility\":\"US_HRMS_MANAGER\","
         +"\"RespApplication\":\"PER\","
         +"\"SecurityGroup\":\"STANDARD\","
         +"\"NLSLanguage\":\"AMERICAN\","
         +"\"Org_Id\" :\"204\""
         +"},"
         +"\"InputParameters\": {"
         +"\"P_ITEMS\": {"
         +"\"ITEM_LIST\":["+items
         +"]} } } }";

         // Basic Authorization credentials required to call the API, and it is encoded for security.
         String usernameColonPassword = "sysadmin:sysadmin";
         String basicAuthPayload = "Basic " + Base64.getEncoder().encodeToString(usernameColonPassword.getBytes());

         // try with resources statement. A resource is an object that must be closed after the program is finished with it
         try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            // Build HttpPostRequest with URL, RequestBody, headers and Basic Authorization
            HttpPost request = new HttpPost("http://e3a9ff5.online-server.cloud:8007/webservices/rest/price/GET_CATALOG_ITEMS/");
            request.setEntity(new StringEntity(requestBody));
            request.setHeader("Content-Type","application/json");
            request.setHeader("Authorization",basicAuthPayload);

            //Execution of the request 
            HttpResponse response = client.execute(request);
           
            //Below lines of code receives the response and change the response object to String
            BufferedReader bufReader = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));     
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = bufReader.readLine()) != null) {
                builder.append(line);
                builder.append(System.lineSeparator());
            }            
            return builder.toString();
         }
         catch(Exception e){
             return null;
         }
    }

    // Given the user ID and Item ID, this method deletes the record in the wishlist table.
    public String deleteWishList(int userId, String itemNumber) {
        Optional<WishListModel> resultsRows = wishListRepository.findById(new WishListId(userId, itemNumber));
        if (resultsRows.isPresent())
             wishListRepository.deleteById(new WishListId(userId, itemNumber));
        else 
            return null;
        return "Deleted";
    }    
}