package com.transferwise.banks.demo.quote;

public class QuoteNotice {

    private String text;
    private String link;
    private String type;

    public QuoteNotice() {
    }

    public QuoteNotice(String text, String link, String type) {
        this.text = text;
        this.link = link;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public String getLink() {
        return link;
    }

    public String getType() {
        return type;
    }
}
