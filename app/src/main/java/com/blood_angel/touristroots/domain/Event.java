package com.blood_angel.touristroots.domain;


import com.blood_angel.touristroots.domain.enums.Category;
import com.blood_angel.touristroots.domain.enums.StatusEvent;

import java.security.Timestamp;
import java.util.List;

public class Event {
        private long id;
        private String name;
        private int cost;
        private int currentCount;
        private int maxCount;
        private String place;
        private Category category;
        private Timestamp dateStart;
        private Timestamp dateEnd;
        private String requirement;
        private String information;
        private StatusEvent status;
        private List<Image> images;
}
