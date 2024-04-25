package com.blood_angel.touristroots.service;

import static com.blood_angel.touristroots.domain.enums.AgePeriod.AGE_18_24;
import static com.blood_angel.touristroots.domain.enums.AgePeriod.AGE_25_31;
import static com.blood_angel.touristroots.domain.enums.AgePeriod.AGE_32_38;
import static com.blood_angel.touristroots.domain.enums.AgePeriod.AGE_39_45;
import static com.blood_angel.touristroots.domain.enums.AgePeriod.AGE_46_52;
import static com.blood_angel.touristroots.domain.enums.AgePeriod.AGE_53;

import com.blood_angel.touristroots.domain.enums.AgePeriod;

public class AgePeriodConverter {
    public static String getStringValueByAgePeriod(AgePeriod agePeriod){
        switch (agePeriod){
            case AGE_18_24:
                return "18-24";
            case AGE_25_31:
                return "25-31";
            case AGE_32_38:
                return "32-38";
            case AGE_39_45:
                return "39-45";
            case AGE_46_52:
                return "46-52";
            case AGE_53:
                return "53+";
        }
        return "18-24";
    }

    public static AgePeriod getAgePeriodByString(String agePeriod){
        switch (agePeriod){
            case "18-24":
                return AGE_18_24;
            case "25-31":
                return AGE_25_31;
            case "32-38":
                return AGE_32_38;
            case "39-45":
                return AGE_39_45;
            case "46-52":
                return AGE_46_52;
            case "53+":
                return AGE_53;
        }
        return AGE_18_24;
    }
}
