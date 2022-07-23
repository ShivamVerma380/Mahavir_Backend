package com.brewingjava.mahavir.entities.product;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        private String productId;
        
        private String productName;

        private String productHighlights;
        
        private String productImage1;
        
        private String productImage2;
        
        private String productImage3;
        
        private String productImage4;
        
        private String productImage5;
        
        private String productPrice;
        
        private String productVideoLink;
        
        private String category;

        private HashMap<String,String> subCategoryMap;  // SubCat, SubSubCat
        
        private String OfferPrice; 


        private HashMap<String,HashMap<String,String>> productInformation;

        private HashMap<String,String> variants;				

        private HashMap<String,ArrayList<String>> variantTypes;

        private ArrayList<String> defaultVariant;

        private FreeItem freeItem;

        private ArrayList<ProductDescription> productDescriptions;

        private HashMap<String,String> filtercriterias;

        private double averageRating = 0;




        public ProductDetail() {
        }

        

        public ProductDetail(String modelNumber, String productId, String productName, String productHighlights,
                String productImage1, String productImage2, String productImage3, String productImage4,
                String productImage5, String productPrice, String productVideoLink, String category,
                HashMap<String, String> subCategoryMap, String offerPrice,
                HashMap<String, HashMap<String, String>> productInformation, HashMap<String, String> variants,
                HashMap<String, ArrayList<String>> variantTypes, ArrayList<String> defaultVariant, FreeItem freeItem,
                ArrayList<ProductDescription> productDescriptions, HashMap<String, String> filtercriterias,
                double averageRating) {
            this.modelNumber = modelNumber;
            this.productId = productId;
            this.productName = productName;
            this.productHighlights = productHighlights;
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
            this.productInformation = productInformation;
            this.variants = variants;
            this.variantTypes = variantTypes;
            this.defaultVariant = defaultVariant;
            this.freeItem = freeItem;
            this.productDescriptions = productDescriptions;
            this.filtercriterias = filtercriterias;
            this.averageRating = averageRating;
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


        public String getProductHighlights() {
            return productHighlights;
        }


        public void setProductHighlights(String productHighlights) {
            this.productHighlights = productHighlights;
        }


        public String getProductImage1() {
            return productImage1;
        }


        public void setProductImage1(String productImage1) {
            this.productImage1 = productImage1;
        }


        public String getProductImage2() {
            return productImage2;
        }


        public void setProductImage2(String productImage2) {
            this.productImage2 = productImage2;
        }


        public String getProductImage3() {
            return productImage3;
        }


        public void setProductImage3(String productImage3) {
            this.productImage3 = productImage3;
        }


        public String getProductImage4() {
            return productImage4;
        }


        public void setProductImage4(String productImage4) {
            this.productImage4 = productImage4;
        }


        public String getProductImage5() {
            return productImage5;
        }


        public void setProductImage5(String productImage5) {
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


        public HashMap<String, HashMap<String, String>> getProductInformation() {
            return productInformation;
        }


        public void setProductInformation(HashMap<String, HashMap<String, String>> productInformation) {
            this.productInformation = productInformation;
        }


        

        public FreeItem getFreeItem() {
            return freeItem;
        }


        public void setFreeItem(FreeItem freeItem) {
            this.freeItem = freeItem;
        }


        public ArrayList<ProductDescription> getProductDescriptions() {
            return productDescriptions;
        }


        public void setProductDescriptions(ArrayList<ProductDescription> productDescriptions) {
            this.productDescriptions = productDescriptions;
        }


        public HashMap<String, String> getFiltercriterias() {
            return filtercriterias;
        }


        public void setFiltercriterias(HashMap<String, String> filtercriterias) {
            this.filtercriterias = filtercriterias;
        }

        public HashMap<String,String> getVariants() {
            return variants;
        }

        public void setVariants(HashMap<String,String> variants) {
            this.variants = variants;
        }

        public HashMap<String, ArrayList<String>> getVariantTypes() {
            return variantTypes;
        }

        public void setVariantTypes(HashMap<String, ArrayList<String>> variantTypes) {
            this.variantTypes = variantTypes;
        }


        public ArrayList<String> getDefaultVariant() {
            return defaultVariant;
        }


        public void setDefaultVariant(ArrayList<String> defaultVariant) {
            this.defaultVariant = defaultVariant;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }



        public double getAverageRating() {
            return averageRating;
        }



        public void setAverageRating(double averageRating) {
            this.averageRating = averageRating;
        }



        @Override
        public String toString() {
            return "ProductDetail [OfferPrice=" + OfferPrice + ", averageRating=" + averageRating + ", category="
                    + category + ", defaultVariant=" + defaultVariant + ", filtercriterias=" + filtercriterias
                    + ", freeItem=" + freeItem + ", modelNumber=" + modelNumber + ", productDescriptions="
                    + productDescriptions + ", productHighlights=" + productHighlights + ", productId=" + productId
                    + ", productImage1=" + productImage1 + ", productImage2=" + productImage2 + ", productImage3="
                    + productImage3 + ", productImage4=" + productImage4 + ", productImage5=" + productImage5
                    + ", productInformation=" + productInformation + ", productName=" + productName + ", productPrice="
                    + productPrice + ", productVideoLink=" + productVideoLink + ", subCategoryMap=" + subCategoryMap
                    + ", variantTypes=" + variantTypes + ", variants=" + variants + "]";
        }

        

        


        
}
