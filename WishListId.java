package com.teqtron.wishlist;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/*
    Embeddable class     
    1. must be public
    2. must override hashCode method
    3. must override equals method
    4. must implement Serializable interface
    5. must have no-arg constructor
*/
@Embeddable
public class WishListId implements Serializable{

    //The class which implements Serializable interface must have the final field serialVersionUID by default
    private static final long serialVersionUID = 1L;

    // These two columns together form the primary key for the wishlist table
    @Column(name = "userid")
    private int userId;

    @Column(name = "item_number")
    private String itemNumber;
    
    public WishListId(){
    }

    public WishListId(int userId, String itemNumber){
        this.userId = userId;
        this.itemNumber = itemNumber;
    }    

    public void setUserId(int userId){
        this.userId = userId;
    }
    public void setItemNumber(String itemNumber){
        this.itemNumber = itemNumber;
    }

    public int getUserId(){
        return userId;
    }
    public String getItemNumber(){
        return itemNumber;
    }

    @Override
    public int hashCode() 
    {
        return itemNumber.hashCode() + userId;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WishListId wishListObj = (WishListId) o;

        if (!itemNumber.equals(wishListObj.itemNumber)) return false;
        return (userId == wishListObj.userId);

    }
}