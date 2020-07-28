package com.teqtron.wishlist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository <WishListModel, WishListId>{

	public List<WishListModel> findByWishListIdUserId(int userId);

}