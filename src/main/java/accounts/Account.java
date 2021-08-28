package accounts;

public class Account {
    private String account;
    private String username;
    private String password;
    private String additionalInformation;

    public Account() {
    }

    public Account(String account, String username, String password, String additionalInformation) {
        this.account = account;
        this.username = username;
        this.password = password;
        this.additionalInformation = additionalInformation;
    }



    public String getAccount() {
        return account;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    @Override
    public String toString() {
        return "Current account details:\n" +
                "\naccount -> " + account +
                "\nusername -> " + username +
                "\npassword -> " + password +
                "\nadditional information -> " + additionalInformation;
    }
}
