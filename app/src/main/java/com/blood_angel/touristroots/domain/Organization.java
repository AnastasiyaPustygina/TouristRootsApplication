package com.blood_angel.touristroots.domain;


import com.blood_angel.touristroots.domain.enums.OrganizationStatus;

import java.util.List;


public class Organization {
    private long id;
    private String fullname;
    private String shortname;
    private String website;
    private String contacts;
    private String information;
    private OrganizationStatus status;
    private String managerName;
    private List<Image> documents;
    private Image image;

}
