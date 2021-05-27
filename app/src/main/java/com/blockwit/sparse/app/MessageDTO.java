package com.blockwit.sparse.app;

public class MessageDTO {

    private MessageProviderType providerType;

    private Long date;

    private String from;

    private String to;

    private String text;

    public MessageDTO(MessageProviderType providerType, Long date, String from, String to, String text) {
        this.providerType = providerType;
        this.date = date;
        this.from = from;
        this.to = to;
        this.text = text;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
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
