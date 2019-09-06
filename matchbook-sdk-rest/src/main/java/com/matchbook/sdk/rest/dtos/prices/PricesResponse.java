package com.matchbook.sdk.rest.dtos.prices;

import java.util.List;

import com.matchbook.sdk.rest.dtos.PageableResponse;

public class PricesResponse extends PageableResponse<Price> {

    private List<Price> prices;

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    @Override
    public List<Price> getContent() {
        return prices;
    }

    @Override
    public String toString() {
        return PricesResponse.class.getSimpleName() + " {" +
                "prices=" + prices +
                "}";
    }

}
