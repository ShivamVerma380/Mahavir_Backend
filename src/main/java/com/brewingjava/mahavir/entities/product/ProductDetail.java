package com.brewingjava.mahavir.entities.product;



import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products_details")
public class ProductDetail {
    
        
        //private List<String> modelNumber; //can keep this in inventory management database?

        @Id
        private String modelNumber;
        
        private String productDescription;
        
        private Binary productImage1;
        
        private Binary productImage2;
        
        private Binary productImage3;
        
        private Binary productImage4;
        
        private Binary productImage5;
        
        private String productPrice;
        
        private String productVideoLink;
        
        private Binary productVideo;

        private String category;

        private String subCategory;

        private String subSubCategory;



        
        public ProductDetail() {
        
        }



        public ProductDetail(String modelNumber, String productDescription, Binary productImage1, Binary productImage2,
                Binary productImage3, Binary productImage4, Binary productImage5, String productPrice,
                String productVideoLink, Binary productVideo) {
            this.modelNumber = modelNumber;
            this.productDescription = productDescription;
            this.productImage1 = productImage1;
            this.productImage2 = productImage2;
            this.productImage3 = productImage3;
            this.productImage4 = productImage4;
            this.productImage5 = productImage5;
            this.productPrice = productPrice;
            this.productVideoLink = productVideoLink;
            this.productVideo = productVideo;
        }



        public String getmodelNumber() {
            return modelNumber;
        }



        public void setmodelNumber(String modelNumber) {
            this.modelNumber = modelNumber;
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



        public Binary getProductVideo() {
            return productVideo;
        }



        public void setProductVideo(Binary productVideo) {
            this.productVideo = productVideo;
        }



        public String getCategory() {
            return category;
        }



        public void setCategory(String category) {
            this.category = category;
        }



        public String getSubCategory() {
            return subCategory;
        }



        public void setSubCategory(String subCategory) {
            this.subCategory = subCategory;
        }



        public String getSubSubCategory() {
            return subSubCategory;
        }



        public void setSubSubCategory(String subSubCategory) {
            this.subSubCategory = subSubCategory;
        }



        @Override
        public String toString() {
            return "ProductDetail [category=" + category + ", productDescription=" + productDescription
                    + ", productImage1=" + productImage1 + ", productImage2=" + productImage2 + ", productImage3="
                    + productImage3 + ", productImage4=" + productImage4 + ", productImage5=" + productImage5
                    + ", modelNumber=" + modelNumber + ", productPrice=" + productPrice + ", productVideo="
                    + productVideo + ", productVideoLink=" + productVideoLink + ", subCategory=" + subCategory
                    + ", subSubCategory=" + subSubCategory + "]";
        }


        


        

        
        
        
}
