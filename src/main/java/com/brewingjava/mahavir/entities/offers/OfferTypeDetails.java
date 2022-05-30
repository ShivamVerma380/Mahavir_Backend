package com.brewingjava.mahavir.entities.offers;

import org.springframework.stereotype.Component;

@Component
public class OfferTypeDetails {
    
    public String offerType;

    public String offerValue;

    public OfferTypeDetails() {
    }

    public OfferTypeDetails(String offerType, String offerValue) {
        this.offerType = offerType;
        this.offerValue = offerValue;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public String getOfferValue() {
        return offerValue;
    }

    public void setOfferValue(String offerValue) {
        this.offerValue = offerValue;
    }

    @Override
    public String toString() {
        return "OfferPricePercentage [offerType=" + offerType + ", offerValue=" + offerValue + "]";
    }

}
