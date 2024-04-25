package com.blood_angel.touristroots.domain;

import com.blood_angel.touristroots.domain.enums.NotificationStatus;

public class NotificationMessage {
    private long id;
    private NotificationStatus status;
    private long chatId;
    private Organization organization;

}
