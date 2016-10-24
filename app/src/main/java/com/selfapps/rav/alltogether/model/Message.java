package com.selfapps.rav.alltogether.model;

/**
 * Json example:
 * "GroupMessage":{
 *    "Sender":"<name>",
 *    "SenderId":"<id",
 *    "Text":"<text>",
 *    "Date":<timestamp long>
 *},
 */
public class Message {
    String id;
    String senderId;
    String senderName;
    String text;
    long sendDate;

    public Message() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getSendDate() {
        return sendDate;
    }

    public void setSendDate(long sendDate) {
        this.sendDate = sendDate;
    }
}
