package com.brewingjava.mahavir.entities.offers;

import org.springframework.stereotype.Component;

@Component
public class OfferPricePercentage {
    
    public String offerPrice;

    public String offerPercentage;

    public OfferPricePercentage() {
    }

    public OfferPricePercentage(String offerPrice, String offerPercentage) {
        this.offerPrice = offerPrice;
        this.offerPercentage = offerPercentage;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getOfferPercentage() {
        return offerPercentage;
    }

    public void setOfferPercentage(String offerPercentage) {
        this.offerPercentage = offerPercentage;
    }

    @Override
    public String toString() {
        return "OfferPricePercentage [offerPercentage=" + offerPercentage + ", offerPrice=" + offerPrice + "]";
    }
    
}
