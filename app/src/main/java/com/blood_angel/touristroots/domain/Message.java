package com.blood_angel.touristroots.domain;


import com.blood_angel.touristroots.domain.enums.Sender;
import com.blood_angel.touristroots.domain.enums.StatusMessage;

import java.security.Timestamp;

public class Message {
    private long id;
    private StatusMessage status;
    private String text;
    private Timestamp datetime;
    private Sender sender;
}
