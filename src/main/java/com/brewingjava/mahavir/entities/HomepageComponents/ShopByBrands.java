package com.brewingjava.mahavir.entities.HomepageComponents;

import java.util.ArrayList;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection = "shop_by_brands")
public class ShopByBrands {
    
    @Id
    private String BrandName;

    private Binary brandLogo;

    private ArrayList<BrandOfferPoster> brandOfferPosters;

    private ArrayList<BrandCategory> brandCategories;

    private ArrayList<String> videoLinks;


    public ShopByBrands() {
    }

    public ShopByBrands(String brandName, Binary brandLogo) {
        this.BrandName = brandName;
        this.brandLogo = brandLogo;
    }

    public ShopByBrands(String brandName, Binary brandLogo, ArrayList<BrandOfferPoster> brandOfferPosters,
            ArrayList<BrandCategory> brandCategories, ArrayList<String> videoLinks) {
        BrandName = brandName;
        this.brandLogo = brandLogo;
        this.brandOfferPosters = brandOfferPosters;
        this.brandCategories = brandCategories;
        this.videoLinks = videoLinks;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public Binary getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(Binary brandLogo) {
        this.brandLogo = brandLogo;
    }

    public ArrayList<BrandOfferPoster> getBrandOfferPosters() {
        return brandOfferPosters;
    }

    public void setBrandOfferPosters(ArrayList<BrandOfferPoster> brandOfferPosters) {
        this.brandOfferPosters = brandOfferPosters;
    }

    public ArrayList<BrandCategory> getBrandCategories() {
        return brandCategories;
    }

    public void setBrandCategories(ArrayList<BrandCategory> brandCategories) {
        this.brandCategories = brandCategories;
    }

    public ArrayList<String> getVideoLinks() {
        return videoLinks;
    }

    public void setVideoLinks(ArrayList<String> videoLinks) {
        this.videoLinks = videoLinks;
    }

    @Override
    public String toString() {
        return "ShopByBrands [BrandName=" + BrandName + ", brandCategories=" + brandCategories + ", brandLogo="
                + brandLogo + ", brandOfferPosters=" + brandOfferPosters + ", videoLinks=" + videoLinks + "]";
    }

    

}
