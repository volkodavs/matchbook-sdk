package com.matchbook.sdk.rest.readers.user;

import com.matchbook.sdk.core.exceptions.MatchbookSDKParsingException;
import com.matchbook.sdk.core.utils.VisibleForTesting;
import com.matchbook.sdk.rest.dtos.user.Account;
import com.matchbook.sdk.rest.dtos.user.Login;
import com.matchbook.sdk.rest.readers.ResponseReader;

public class LoginReader extends ResponseReader<Login> {

    private final AccountReader accountReader;

    public LoginReader() {
        super();
        accountReader = new AccountReader();
    }

    @VisibleForTesting
    LoginReader(AccountReader accountReader) {
        this.accountReader = accountReader;
    }

    @Override
    protected Login readItem() throws MatchbookSDKParsingException {
        final Login login = new Login();
        while (!parser.isEndOfObject()) {
            parser.moveToNextValue();
            String fieldName = parser.getFieldName();
            if ("session-token".equals(fieldName)) {
                login.setSessionToken(parser.getString());
            } else if ("user-id".equals(fieldName)) {
                login.setUserId(parser.getLong());
            } else if ("account".equals(fieldName)) {
                Account account = readAccount();
                login.setAccount(account);
            }
            parser.moveToNextToken();
        }
        return login;
    }

    private Account readAccount() {
        accountReader.init(parser);
        return accountReader.readFullResponse();
    }

}
