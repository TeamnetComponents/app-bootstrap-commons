package ro.teamnet.bootstrap.domain.util;

import ro.teamnet.bootstrap.domain.Account;

public class AccountAndResponseBody {

    private String infoAboutAccount;
    private Account account;

    public AccountAndResponseBody(String infoAboutAccount, Account account) {
        this.infoAboutAccount = infoAboutAccount;
        this.account = account;
    }

    public String getInfoAboutAccount() {
        return infoAboutAccount;
    }

    public void setInfoAboutAccount(String infoAboutAccount) {
        this.infoAboutAccount = infoAboutAccount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
