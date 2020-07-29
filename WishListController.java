package com.teqtron.wishlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WishListController {

    @Autowired
    private WishListService wishListService;
    
    /*  HTTP GET Request
        This method will be called when the user wants to see all the items under his
        wish list category. User ID is passed as a parameter to this method.       
    */
    @RequestMapping("/user/{userId}/item")
    public @ResponseBody ResponseEntity<?> getWishLists(@PathVariable int userId) {
        
        String StringOfProducts = "";
        try{
            StringOfProducts = wishListService.getWishLists(userId);
        }
        catch(Exception e){
            return new ResponseEntity<String>("Error!", HttpStatus.INTERNAL_SERVER_ERROR);
        }        
        if(StringOfProducts == null)
            return new ResponseEntity<String>("User does not exist", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<String>(StringOfProducts, HttpStatus.OK);

    }

    /* 
        This method will be called when the user wants to delete a item from the list of
        wish lists. User ID and Item ID are passed as  parameters to this method.
        DELETE service
    */
    @RequestMapping(method = RequestMethod.DELETE, value = "/user/{userId}/item/{itemNumber}")
    public ResponseEntity<String> deleteWishList(@PathVariable int userId, @PathVariable String itemNumber){        
        String result = null;
        try{
            result = wishListService.deleteWishList(userId, itemNumber);    
         }
         catch(Exception e){             
            return new ResponseEntity<String>("Internal server error!",HttpStatus.INTERNAL_SERVER_ERROR); 
         }
         if(result == null)
            return new ResponseEntity<String>("No records deleted!",HttpStatus.BAD_REQUEST);
         else
             return new ResponseEntity<String>("Record was successfully deleted!",HttpStatus.OK); 
    }

    /* 
        This method will be called when the user wants to add a new item to his 
        wish lists. Request body is the input to this method.
        POST service
    */
    @RequestMapping(method = RequestMethod.POST, value = "/item")
    public ResponseEntity<String> addWishList(@RequestBody WishListModel wishListModel) {
    
    WishListModel responseWishListModel ;    
        try{
            responseWishListModel = wishListService.addWishList(wishListModel);
        }
        catch(Exception e){
            return new ResponseEntity<String>("Error adding record to collection.",HttpStatus.INTERNAL_SERVER_ERROR); 
        }    
        if(responseWishListModel != null)  
            return new ResponseEntity<String>("Record was successfully added to collection.",HttpStatus.CREATED);       
        else              
            return new ResponseEntity<String>("Cannot add a duplicate Entry.",HttpStatus.BAD_REQUEST);      
    }
}