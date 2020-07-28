package com.teqtron.wishlist;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table ( name = "products")
public class ProductModel {
    // This is the primary key column of products table
    @Id
    @Column(name = "itemNumber")
    private String itemNumber;

    @Column(name = "partType")
    private String partType;

    @Column(name = "description")
    private String description;

    @Column(name = "productName")
    private String productName;

    public ProductModel(){
    }
    
    public String getItemNumber() {
        return itemNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPartType() {
        return partType;
    }

    public void setPartType(String partType) {
        this.partType = partType;
    }
    
    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    
    //private String revision;
    // private String lifecyclePhase;
    //private String shortDesc;
    // private String organization;
    // public String getOrganization() {
    //     return organization;
    // }

    // public void setOrganization(String organization) {
    //     this.organization = organization;
    // }

    // public String getShortDesc() {
    //     return shortDesc;
    // }

    // public void setShortDesc(String shortDesc) {
    //     this.shortDesc = shortDesc;
    // }

    // public String getLifecyclePhase() {
    //     return lifecyclePhase;
    // }

    // public void setLifecyclePhase(String lifecyclePhase) {
    //     this.lifecyclePhase = lifecyclePhase;
    // }

    // public String getRevision() {
    //     return revision;
    // }

    // public void setRevision(String revision) {
    //     this.revision = revision;
    // }


}
