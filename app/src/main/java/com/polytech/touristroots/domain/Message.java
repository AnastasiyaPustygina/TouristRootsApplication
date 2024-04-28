package com.polytech.touristroots.domain;


import com.polytech.touristroots.domain.enums.Sender;
import com.polytech.touristroots.domain.enums.StatusMessage;

import java.security.Timestamp;

public class Message {
    private long id;
    private StatusMessage status;
    private String text;
    private Timestamp datetime;
    private Sender sender;
}
