package com.grove.tfb_backend.feedback;

public class Feedback {
    private String sender;
    private String topic;
    private String feedback;






    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                ", sender='" + sender + '\'' +
                ", topic='" + topic + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
