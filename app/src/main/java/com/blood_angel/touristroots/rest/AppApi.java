package com.blood_angel.touristroots.rest;

import android.os.Bundle;

import com.blood_angel.touristroots.domain.Tourist;

public interface AppApi {

    void login(String email, String pass);

    void sendCode(String email, Bundle bundle);

    void updateEmailCode(String email);

    void compareCode(String email, String code, Tourist tourist);

    void register(Tourist tourist);
}
