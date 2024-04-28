package com.polytech.touristroots.service;

import static com.polytech.touristroots.domain.enums.Gender.FEMALE;
import static com.polytech.touristroots.domain.enums.Gender.MALE;

import com.polytech.touristroots.domain.enums.Gender;

public class GenderConverter {
    public static String getStringValueByGender(Gender gender){
        switch (gender){
            case MALE:
                return "Мужской";
            case FEMALE:
                return "Женский";
        }
        return "Мужской";
    }

    public static Gender getGenderByString(String gender){
        switch (gender){
            case "Мужской":
                return MALE;
            case "Женский":
                return FEMALE;
        }
        return MALE;
    }
}
