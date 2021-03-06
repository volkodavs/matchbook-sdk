package com.matchbook.sdk.rest.configs;

import com.matchbook.sdk.core.exceptions.MatchbookSDKParsingException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;

public interface Parser extends AutoCloseable {

    boolean hasCurrentToken();

    boolean isEndOfObject();

    boolean isEndOfArray();

    void moveToNextToken() throws MatchbookSDKParsingException;

    void moveToNextValue() throws MatchbookSDKParsingException;

    void skipChildren() throws MatchbookSDKParsingException;

    String getFieldName() throws MatchbookSDKParsingException;

    Boolean getBoolean() throws MatchbookSDKParsingException;

    String getString() throws MatchbookSDKParsingException;

    Integer getInteger() throws MatchbookSDKParsingException;

    Long getLong() throws MatchbookSDKParsingException;

    Double getDouble() throws MatchbookSDKParsingException;

    BigDecimal getDecimal() throws MatchbookSDKParsingException;

    Instant getInstant() throws MatchbookSDKParsingException;

    <T extends Enum<T>> T getEnum(Class<T> enumClass) throws MatchbookSDKParsingException;

    @Override
    void close() throws IOException;

}
