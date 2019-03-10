package com.matchbook.sdk.core.clients.rest.dtos.events;

import com.matchbook.sdk.core.clients.rest.dtos.prices.BasePricesRequest;
import com.matchbook.sdk.core.clients.rest.dtos.prices.BasePricesRequestBuilder;

public class MarketRequest extends BasePricesRequest {

    private final Long marketId;
    private final Long eventId;
    private final boolean includePrices;

    private MarketRequest(MarketRequest.Builder builder) {
        super(builder);

        this.marketId = builder.marketId;
        this.eventId = builder.eventId;
        this.includePrices = builder.includePrices;
    }

    public Long getId() {
        return marketId;
    }

    public Long getEventId() {
        return eventId;
    }

    public boolean includePrices() {
        return includePrices;
    }

    public static class Builder extends BasePricesRequestBuilder {

        private final Long marketId;
        private final Long eventId;
        private boolean includePrices;

        public Builder(Long marketId, Long eventId) {
            this.eventId = eventId;
            this.marketId = marketId;
            includePrices = false;
        }

        public Builder includePrices(boolean includePrices) {
            this.includePrices = includePrices;
            return this;
        }

        public MarketRequest build() {
            return new MarketRequest(this);
        }
    }

}
