package org.hibernate.auction.model;

/**
 * Created by IntelliJ IDEA.
 * User: plightbo
 * Date: Apr 11, 2005
 */
public class Email {
    String username;
    String domain;

    public Email() {
    }

    public Email(String username, String domain) {
        this.username = username;
        this.domain = domain;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String toString() {
        return username + '@' + domain;
    }

    public static Email parse(String email) {
        Email e = new Email();
        int at = email.indexOf('@');
        if (at == -1) {
            throw new IllegalArgumentException("Invalid email address");
        }

        e.setUsername(email.substring(0, at));
        e.setDomain(email.substring(at + 1));

        return e;
    }
}
