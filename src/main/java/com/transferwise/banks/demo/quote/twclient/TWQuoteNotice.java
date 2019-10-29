package com.transferwise.banks.demo.quote.twclient;

class TWQuoteNotice {

    private String text;
    private String link;
    private String type;

    public TWQuoteNotice() {
    }

    public TWQuoteNotice(String text, String link, String type) {
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
