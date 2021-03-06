package com.matchbook.sdk.rest.dtos.events;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.matchbook.sdk.rest.dtos.prices.BasePricesRequestTest;
import com.matchbook.sdk.rest.dtos.prices.Currency;
import com.matchbook.sdk.rest.dtos.prices.ExchangeType;
import com.matchbook.sdk.rest.dtos.prices.OddsType;
import com.matchbook.sdk.rest.dtos.prices.PriceMode;
import com.matchbook.sdk.rest.dtos.prices.Side;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RunnerRequestTest extends BasePricesRequestTest<RunnerRequest> {

    private Long eventId;
    private Long marketId;
    private Long runnerId;

    @Override
    @BeforeEach
    protected void setUp() {
        eventId = 395729780570010L;
        marketId = 395729860260010L;
        runnerId = 410499974510009L;

        super.setUp();
    }

    @Override
    protected RunnerRequest newPricesRequest(OddsType oddsType, ExchangeType exchangeType, Side side,
            Currency currency, BigDecimal minimumLiquidity, PriceMode priceMode) {
        return new RunnerRequest.Builder(eventId, marketId, runnerId)
                .includePrices(true)
                .oddsType(oddsType)
                .exchangeType(exchangeType)
                .side(side)
                .currency(currency)
                .minimumLiquidity(minimumLiquidity)
                .priceMode(priceMode)
                .build();
    }

    @Override
    protected RunnerRequest newEmptyRequest() {
        return new RunnerRequest.Builder(eventId, marketId, runnerId).build();
    }

    @Test
    @DisplayName("Check event ID")
    void eventIdTest() {
        assertThat(unit.getEventId()).isEqualTo(eventId)
                .isEqualTo(emptyUnit.getEventId());
    }

    @Test
    @DisplayName("Check market ID")
    void marketIdTest() {
        assertThat(unit.getMarketId()).isEqualTo(marketId)
                .isEqualTo(emptyUnit.getMarketId());
    }

    @Test
    @DisplayName("Check runner ID")
    void runnerIdTest() {
        assertThat(unit.getRunnerId()).isEqualTo(runnerId)
                .isEqualTo(emptyUnit.getRunnerId());
    }

    @Test
    @DisplayName("Check include prices")
    void includePricesTest() {
        assertThat(unit.includePrices()).isTrue();
        assertThat(emptyUnit.includePrices()).isFalse();
    }

    @Test
    @DisplayName("Verify resource path")
    void resourcePathTest() {
        String path = unit.resourcePath();
        assertThat(path).isEqualTo("events/" + eventId + "/markets/" + marketId + "/runners/" + runnerId);
    }

    @Test
    @DisplayName("Check query parameters")
    void parametersTest() {
        assertThat(unit.parameters())
                .extractingFromEntries(Map.Entry::getKey, Map.Entry::getValue)
                .contains(tuple("include-prices", "true"));

        assertThat(emptyUnit.parameters()).doesNotContainKeys("include-prices");
    }

}
