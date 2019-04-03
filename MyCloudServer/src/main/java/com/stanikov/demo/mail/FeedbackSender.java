package com.stanikov.demo.mail;

public interface FeedbackSender {
    void sendFeedback(String from, String name, String feedback);
}
