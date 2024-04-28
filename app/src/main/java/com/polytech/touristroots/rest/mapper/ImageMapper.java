package com.polytech.touristroots.rest.mapper;

import com.polytech.touristroots.domain.Image;

import org.json.JSONException;
import org.json.JSONObject;

public class ImageMapper {
    public static Image imageFromJson(JSONObject jsonObject) {
        Image image = null;
        try {
            image = new Image(jsonObject.getLong("id"),
                    jsonObject.getString("path"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return image;
    }
    public static JSONObject ImageToJsonObject(Image Image){
        JSONObject params = new JSONObject();
        try {
            params.put("id", Image.getId());
            params.put("path", Image.getPath());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }
}
