package com.teqtron.wishlist;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "wishlist")
public class WishListModel {

    //Composite primary key is embedded here
    @EmbeddedId
    private WishListId wishListId;

    public WishListModel() {
    }

    public WishListModel(WishListId wishListId) {
        this.wishListId = wishListId;
    }

    public void setWishListId(WishListId wishListId){
        this.wishListId = wishListId;
    }

    public WishListId getWishListId(){
        return wishListId;
    }

}