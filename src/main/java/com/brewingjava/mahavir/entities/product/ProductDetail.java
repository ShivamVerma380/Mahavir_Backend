package com.brewingjava.mahavir.entities.product;



import java.util.ArrayList;
import java.util.HashMap;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.brewingjava.mahavir.entities.categories.ProductInformationItem;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Document(collection = "products_details")
public class ProductDetail {
        
        //private List<String> modelNumber; //can keep this in inventory management database?
        @Id
        private String modelNumber;
        
        private String productName;

        private String productDescription;
        
        private Binary productImage1;
        
        private Binary productImage2;
        
        private Binary productImage3;
        
        private Binary productImage4;
        
        private Binary productImage5;
        
        private String productPrice;
        
        private String productVideoLink;
        
        private String category;

        private HashMap<String,String> subCategoryMap;  // SubCat, SubSubCat
        
        private String OfferPrice; 


        private ArrayList<String> items;
        private ArrayList<HashMap<String,String>> subItems;

        public ProductDetail() {

        }

        public ProductDetail(String modelNumber, String productName, String productDescription, Binary productImage1,
                Binary productImage2, Binary productImage3, Binary productImage4, Binary productImage5,
                String productPrice, String productVideoLink, String category,
                HashMap<String, String> subCategoryMap, String offerPrice, ArrayList<String> items,
                ArrayList<HashMap<String, String>> subItems) {
            this.modelNumber = modelNumber;
            this.productName = productName;
            this.productDescription = productDescription;
            this.productImage1 = productImage1;
            this.productImage2 = productImage2;
            this.productImage3 = productImage3;
            this.productImage4 = productImage4;
            this.productImage5 = productImage5;
            this.productPrice = productPrice;
            this.productVideoLink = productVideoLink;
            this.category = category;
            this.subCategoryMap = subCategoryMap;
            OfferPrice = offerPrice;
            this.items = items;
            this.subItems = subItems;
        }

        public String getModelNumber() {
            return modelNumber;
        }

        public void setModelNumber(String modelNumber) {
            this.modelNumber = modelNumber;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductDescription() {
            return productDescription;
        }

        public void setProductDescription(String productDescription) {
            this.productDescription = productDescription;
        }

        public Binary getProductImage1() {
            return productImage1;
        }

        public void setProductImage1(Binary productImage1) {
            this.productImage1 = productImage1;
        }

        public Binary getProductImage2() {
            return productImage2;
        }


        public void setProductImage2(Binary productImage2) {
            this.productImage2 = productImage2;
        }

        public Binary getProductImage3() {
            return productImage3;
        }

        public void setProductImage3(Binary productImage3) {
            this.productImage3 = productImage3;
        }

        public Binary getProductImage4() {
            return productImage4;
        }

        public void setProductImage4(Binary productImage4) {
            this.productImage4 = productImage4;
        }

        public Binary getProductImage5() {
            return productImage5;
        }

        public void setProductImage5(Binary productImage5) {
            this.productImage5 = productImage5;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public String getProductVideoLink() {
            return productVideoLink;
        }

        public void setProductVideoLink(String productVideoLink) {
            this.productVideoLink = productVideoLink;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public HashMap<String, String> getSubCategoryMap() {
            return subCategoryMap;
        }

        public void setSubCategoryMap(HashMap<String, String> subCategoryMap) {
            this.subCategoryMap = subCategoryMap;
        }

        public String getOfferPrice() {
            return OfferPrice;
        }

        public void setOfferPrice(String offerPrice) {
            OfferPrice = offerPrice;
        }
        public ArrayList<String> getItems() {
            return items;
        }

        public void setItems(ArrayList<String> items) {
            this.items = items;
        }

        public ArrayList<HashMap<String, String>> getSubItems() {
            return subItems;
        }

        public void setSubItems(ArrayList<HashMap<String, String>> subItems) {
            this.subItems = subItems;
        }



        @Override
        public String toString() {
            return "ProductDetail [OfferPrice=" + OfferPrice + ", category=" + category + ", items=" + items
                    + ", modelNumber=" + modelNumber + ", productDescription=" + productDescription + ", productImage1="
                    + productImage1 + ", productImage2=" + productImage2 + ", productImage3=" + productImage3
                    + ", productImage4=" + productImage4 + ", productImage5=" + productImage5 + ", productName="
                    + productName + ", productPrice=" + productPrice + ", productVideoLink=" + productVideoLink
                    + ", subCategoryMap=" + subCategoryMap + ", subItems=" + subItems + "]";
        }

        

        
}
