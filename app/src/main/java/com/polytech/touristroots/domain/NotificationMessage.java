package com.polytech.touristroots.domain;

import com.polytech.touristroots.domain.enums.NotificationStatus;

public class NotificationMessage {
    private long id;
    private NotificationStatus status;
    private long chatId;
    private Organization organization;

}
