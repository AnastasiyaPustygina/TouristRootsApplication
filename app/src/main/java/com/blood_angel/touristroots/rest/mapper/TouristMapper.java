package com.blood_angel.touristroots.rest.mapper;

import com.blood_angel.touristroots.domain.Tourist;
import com.blood_angel.touristroots.domain.enums.AgePeriod;
import com.blood_angel.touristroots.domain.enums.Gender;

import org.json.JSONException;
import org.json.JSONObject;

public class TouristMapper {
    public static Tourist touristFromJson(JSONObject jsonObject) {
        Tourist tourist = null;
        try {
            tourist = new Tourist(jsonObject.getLong("id"),
                    jsonObject.getString("name"),
                    jsonObject.getString("email"),
                    Gender.valueOf(jsonObject.getString("gender")),
                    AgePeriod.valueOf(jsonObject.getString("agePeriod")),
                    jsonObject.getString("information"),
                    jsonObject.getString("password"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tourist;
    }
    public static JSONObject touristToJsonObject(Tourist tourist){
        JSONObject params = new JSONObject();
        try {
            params.put("id", tourist.getId());
            params.put("name", tourist.getName());
            params.put("email", tourist.getEmail());
            params.put("gender", tourist.getGender().name());
            params.put("agePeriod", tourist.getAgePeriod().name());
            params.put("information", tourist.getInformation());
            params.put("password", tourist.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }
}