package com.blockwit.sparse.app;

public class MessageDTO {

    private MessageProviderType providerType;

    private String from;

    private String to;

    private String text;

    public MessageDTO(MessageProviderType providerType, String from, String to, String text) {
        this.providerType = providerType;
        this.from = from;
        this.to = to;
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MessageProviderType getProviderType() {
        return providerType;
    }

    public void setProviderType(MessageProviderType providerType) {
        this.providerType = providerType;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
