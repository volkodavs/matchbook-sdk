package com.matchbook.sdk.core.clients.rest.dtos.events;

import com.matchbook.sdk.core.clients.rest.dtos.prices.PageablePricesRequest;
import com.matchbook.sdk.core.clients.rest.dtos.prices.PageablePricesRequestBuilder;

import java.util.Set;

public class MarketsRequest extends PageablePricesRequest {

    private final Long eventId;
    private final Set<MarketType> types;
    private final Set<MarketStatus> statuses;
    private final boolean includePrices;

    private MarketsRequest(MarketsRequest.Builder builder) {
        super(builder);

        this.eventId = builder.eventId;
        this.types = builder.types;
        this.statuses = builder.statuses;
        this.includePrices = builder.includePrices;
    }

    public Long getEventId() {
        return eventId;
    }

    public Set<MarketType> getTypes() {
        return types;
    }

    public Set<MarketStatus> getStatuses() {
        return statuses;
    }

    public boolean includePrices() {
        return includePrices;
    }

    public static class Builder extends PageablePricesRequestBuilder {

        private final Long eventId;
        private Set<MarketType> types;
        private Set<MarketStatus> statuses;
        private boolean includePrices;

        public Builder(Long eventId) {
            this.eventId = eventId;
            includePrices = false;
        }

        public Builder types(Set<MarketType> types) {
            this.types = types;
            return this;
        }

        public Builder statuses(Set<MarketStatus> statuses) {
            this.statuses = statuses;
            return this;
        }

        public Builder includePrices(boolean includePrices) {
            this.includePrices = includePrices;
            return this;
        }

        public MarketsRequest build() {
            return new MarketsRequest(this);
        }
    }

}
