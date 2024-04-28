package com.polytech.touristroots.rest;

import android.os.Bundle;

import com.polytech.touristroots.domain.Tourist;

public interface AppApi {

    void login(String email, String pass);
    void updateTourist(Tourist tourist);

    void sendCode(String email, Bundle bundle);

    void updateEmailCode(String email);

    void compareCode(String email, String code, Tourist tourist);

    void register(Tourist tourist);
}
