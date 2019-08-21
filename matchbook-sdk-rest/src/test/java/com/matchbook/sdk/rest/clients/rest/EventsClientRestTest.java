package com.matchbook.sdk.rest.clients.rest;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.matchbook.sdk.core.StreamObserver;
import com.matchbook.sdk.core.exceptions.MatchbookSDKException;
import com.matchbook.sdk.rest.EventsClientRest;
import com.matchbook.sdk.rest.MatchbookSDKClientRestTest;
import com.matchbook.sdk.rest.dtos.events.Event;
import com.matchbook.sdk.rest.dtos.events.EventRequest;
import com.matchbook.sdk.rest.dtos.events.EventsRequest;
import com.matchbook.sdk.rest.dtos.events.Market;
import com.matchbook.sdk.rest.dtos.events.MarketRequest;
import com.matchbook.sdk.rest.dtos.events.MarketsRequest;
import com.matchbook.sdk.rest.dtos.events.Runner;
import com.matchbook.sdk.rest.dtos.events.RunnerRequest;
import com.matchbook.sdk.rest.dtos.events.RunnersRequest;
import com.matchbook.sdk.rest.dtos.events.Sport;
import com.matchbook.sdk.rest.dtos.events.SportsRequest;
import org.junit.Before;
import org.junit.Test;

public class EventsClientRestTest extends MatchbookSDKClientRestTest {

    private EventsClientRest unit;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        this.unit = new EventsClientRest(connectionManager);
    }

    @Test
    public void successfulGetSportsTest() throws InterruptedException {
        wireMockServer.stubFor(get(urlPathEqualTo("/edge/rest/lookups/sports"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("matchbook/getSportsSuccessfulResponse.json")));

        final CountDownLatch countDownLatch = new CountDownLatch(6);

        SportsRequest sportsRequest = new SportsRequest.Builder().build();

        unit.getSports(sportsRequest, new StreamObserver<Sport>() {

            @Override
            public void onNext(Sport sport) {
                assertThat(sport.getId()).isNotNull();
                assertThat(sport.getName()).isNotEmpty();
                countDownLatch.countDown();
            }

            @Override
            public void onError(MatchbookSDKException e) {
                fail();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });

        boolean await = countDownLatch.await(5, TimeUnit.SECONDS);
        assertThat(await).isTrue();
    }

    @Test
    public void successfulGetEventTest() throws InterruptedException {
        wireMockServer.stubFor(get(urlPathEqualTo("/edge/rest/events/395729780570010"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("matchbook/getEventSuccessfulResponse.json")));

        final CountDownLatch countDownLatch = new CountDownLatch(2);
        EventRequest eventRequest = new EventRequest.Builder(395729780570010L).build();

        unit.getEvent(eventRequest, new StreamObserver<Event>() {

            @Override
            public void onNext(Event event) {
                assertThat(event.getId()).isNotNull();
                countDownLatch.countDown();
            }

            @Override
            public void onError(MatchbookSDKException e) {
                fail(e.getMessage());
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });

        boolean await = countDownLatch.await(5, TimeUnit.SECONDS);
        assertThat(await).isTrue();
    }

    @Test
    public void successfulGetEventsTest() throws InterruptedException {
        wireMockServer.stubFor(get(urlPathEqualTo("/edge/rest/events"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("matchbook/getEventsSuccessfulResponse.json")));

        final CountDownLatch countDownLatch = new CountDownLatch(2);
        EventsRequest eventsRequest = new EventsRequest.Builder().build();

        unit.getEvents(eventsRequest, new StreamObserver<Event>() {

            @Override
            public void onNext(Event event) {
                assertThat(event.getId()).isNotNull();
                countDownLatch.countDown();
            }

            @Override
            public void onError(MatchbookSDKException e) {
                fail();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });

        boolean await = countDownLatch.await(5, TimeUnit.SECONDS);
        assertThat(await).isTrue();
    }

    @Test
    public void successfulGetMarketTest() throws InterruptedException {
        String testUrl = "/edge/rest/events/395729780570010/markets/395729860260010";
        wireMockServer.stubFor(get(urlPathEqualTo(testUrl))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("matchbook/getMarketSuccessfulResponse.json")));


        final CountDownLatch countDownLatch = new CountDownLatch(2);
        MarketRequest marketRequest = new MarketRequest
                .Builder(395729860260010L, 395729780570010L)
                .build();

        unit.getMarket(marketRequest, new StreamObserver<Market>() {

            @Override
            public void onNext(Market market) {
                assertThat(market.getId()).isNotNull();
                assertThat(market.getEventId()).isNotNull();
                countDownLatch.countDown();
            }

            @Override
            public void onError(MatchbookSDKException e) {
                fail();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });

        boolean await = countDownLatch.await(5, TimeUnit.SECONDS);
        assertThat(await).isTrue();
    }

    @Test
    public void successfulGetMarketsTest() throws InterruptedException {
        String testUrl = "/edge/rest/events/395729780570010/markets";
        wireMockServer.stubFor(get(urlPathEqualTo(testUrl))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("matchbook/getMarketsSuccessfulResponse.json")));

        final CountDownLatch countDownLatch = new CountDownLatch(9);

        MarketsRequest marketsRequest = new MarketsRequest.Builder(395729780570010L).build();

        unit.getMarkets(marketsRequest, new StreamObserver<Market>() {

            @Override
            public void onNext(Market market) {
                assertThat(market.getId()).isNotNull();
                countDownLatch.countDown();
            }

            @Override
            public void onError(MatchbookSDKException e) {
                fail();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });

        boolean await = countDownLatch.await(5, TimeUnit.SECONDS);
        assertThat(await).isTrue();
    }

    @Test
    public void successfulGetRunnerTest() throws InterruptedException {
        String testUrl = "/edge/rest/events/395729780570010/markets/395729860260010/runners/395729860800010";
        wireMockServer.stubFor(get(urlPathEqualTo(testUrl))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("matchbook/getRunnerSuccessfulResponse.json")));


        final CountDownLatch countDownLatch = new CountDownLatch(2);

        RunnerRequest runnerRequest = new RunnerRequest
                .Builder(395729780570010L, 395729860260010L, 395729860800010L)
                .build();

        unit.getRunner(runnerRequest, new StreamObserver<Runner>() {

            @Override
            public void onNext(Runner runner) {
                assertThat(runner.getId()).isNotNull();
                assertThat(runner.getEventId()).isNotNull();
                assertThat(runner.getMarketId()).isNotNull();
                countDownLatch.countDown();
            }

            @Override
            public void onError(MatchbookSDKException e) {
                fail();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });

        boolean await = countDownLatch.await(5, TimeUnit.SECONDS);
        assertThat(await).isTrue();
    }

    @Test
    public void successfulGetRunnersTest() throws InterruptedException {
        String testUrl = "/edge/rest/events/395729780570010/markets/395729860260010/runners";
        wireMockServer.stubFor(get(urlPathEqualTo(testUrl))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("matchbook/getRunnersSuccessfulResponse.json")));

        final CountDownLatch countDownLatch = new CountDownLatch(4);

        RunnersRequest runnersRequest = new RunnersRequest
                .Builder(395729780570010L, 395729860260010L)
                .build();

        unit.getRunners(runnersRequest, new StreamObserver<Runner>() {

            @Override
            public void onNext(Runner runner) {
                assertThat(runner.getId()).isNotNull();
                countDownLatch.countDown();
            }

            @Override
            public void onError(MatchbookSDKException e) {
                fail();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });

        boolean await = countDownLatch.await(5, TimeUnit.SECONDS);
        assertThat(await).isTrue();
    }

}
