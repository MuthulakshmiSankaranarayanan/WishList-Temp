package com.teqtron.wishlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.ws.rs.core.Response;


@Service
public class WishListService {

    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private ProductRepository productRepository;

    // Body of the request is saved in the wishlist Table
    public WishListModel addWishList(WishListModel wishListModel) {
       Optional<WishListModel> response =  wishListRepository.findById(wishListModel.getWishListId());
       if(response.isPresent())
            return null;
       else
            return wishListRepository.save(wishListModel);
    }

    /*
        Given the user ID, list of item IDs are retrived from the wishlist table.
        For each item ID, description-partType-productName fields are retrived from the products table
        and added to the arraylist and finally the arraylist is returned.
        
    */
    public List<ProductModel> getWishLists(int userId) {
        List<ProductModel> products = new ArrayList<>();
        List<WishListModel> wishes = new ArrayList<>();

        wishes = wishListRepository.findByWishListIdUserId(userId);

         for( WishListModel wish : wishes){
            String itemNumber = wish.getWishListId().getItemNumber();
            Optional<ProductModel> singleProduct =  productRepository.findById(itemNumber);
            try{
                 products.add(singleProduct.get());
            }
            catch(Exception e){
                Response.status(404, "No records");
                return null;
            }
        }
        return products;        
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

    public Optional<WishListModel> getSingleWishList(int userId, String itemNumber) {
		return wishListRepository.findById(new WishListId(userId, itemNumber));
	}
    
}