package com.matchbook.sdk.core.clients.rest;

import com.fasterxml.jackson.databind.ObjectReader;
import com.matchbook.sdk.core.StreamObserver;
import com.matchbook.sdk.core.clients.rest.dtos.events.SportsResponse;
import com.matchbook.sdk.core.clients.rest.dtos.prices.Price;
import com.matchbook.sdk.core.clients.rest.dtos.prices.PricesRequest;
import com.matchbook.sdk.core.configs.ClientConnectionManager;

public class PricesRestClientImpl extends AbstractRestClient implements PricesRestClient {

    private final ObjectReader pricesResponseReader;

    public PricesRestClientImpl(ClientConnectionManager clientConnectionManager) {
        super(clientConnectionManager);

        this.pricesResponseReader = clientConnectionManager.getMapper().readerFor(SportsResponse.class);
    }

    @Override
    public void getPrices(PricesRequest pricesRequest, StreamObserver<Price> pricesObserver) {
        String url = clientConnectionManager.getClientConfig().buildUrl(pricesRequest.resourcePath());
        getRequest(url, pricesRequest.parameters(), pricesObserver, pricesResponseReader);
    }

}
