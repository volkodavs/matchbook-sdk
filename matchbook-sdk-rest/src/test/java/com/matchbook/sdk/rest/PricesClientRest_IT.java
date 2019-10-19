package com.matchbook.sdk.rest;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import com.matchbook.sdk.rest.dtos.prices.Price;
import com.matchbook.sdk.rest.dtos.prices.PricesRequest;

import org.junit.jupiter.api.Test;

public class PricesClientRest_IT extends MatchbookSDKClientRest_IT<PricesClientRest> {

    @Override
    protected PricesClientRest newClientRest(ConnectionManager connectionManager) {
        return new PricesClientRest(connectionManager);
    }

    @Test
    void getPricesTest() {
        String url = "/edge/rest/events/395729780570010/markets/395729860260010/runners/395729860800010/prices";
        wireMockServer.stubFor(get(urlPathEqualTo(url))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("matchbook/prices/getPricesSuccessfulResponse.json")));

        PricesRequest pricesRequest = new PricesRequest
                .Builder(395729780570010L, 395729860260010L, 395729860800010L)
                .build();
        ResponseStreamObserver<Price> streamObserver = new SuccessfulResponseStreamObserver<>(2, this::verifyPrice);
        clientRest.getPrices(pricesRequest, streamObserver);
        streamObserver.waitTermination();

        wireMockServer.verify(getRequestedFor(urlPathEqualTo(url))
                .withCookie("mb-client-type", equalTo("mb-sdk")));
    }

    private void verifyPrice(Price price) {
        assertThat(price).isNotNull();
        assertThat(price.getOddsType()).isNotNull();
        assertThat(price.getOdds()).isNotNull();
    }

}
