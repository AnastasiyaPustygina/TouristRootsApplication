package com.polytech.touristroots.rest;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.polytech.touristroots.fragment.ProfileEditFragment;
import com.polytech.touristroots.rest.mapper.TouristMapper;
import com.polytech.touristroots.R;
import com.polytech.touristroots.domain.Tourist;

import org.json.JSONObject;
public class AppApiVolley implements AppApi{
    private final Context context;
    private final Fragment fragment;
    public static final String BASE_URL = "http://192.168.1.35:8083";

    public AppApiVolley(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public void login(String email, String pass) {
        String url = BASE_URL + "/tourist/login?email=" + email + "&" + "password=" + pass;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("TOURIST", response.toString());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("tourist", TouristMapper.touristFromJson(response));
                        NavHostFragment.findNavController(fragment)
                                .navigate(R.id.action_signInFragment_to_profileFragment, bundle);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Неправильный email или пароль",
                                Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            context.deleteSharedPreferences("remember");
                        }
                        Log.e("AUTHORIZE", error.toString());
                    }
                }
        );
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue referenceQueue = Volley.newRequestQueue(context);
        referenceQueue.add(jsonObjectRequest);
    }

    @Override
    public void updateTourist(Tourist tourist) {
        String url = BASE_URL + "/tourist/update";
        JSONObject params = TouristMapper.touristToJsonObject(tourist);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("API_TEST_UPDATE_TOURIST", response.toString());
                Tourist tourist1 = TouristMapper.touristFromJson(response);
                Bundle bundle = new Bundle();
                bundle.putSerializable("tourist", tourist1);
                if(fragment.getClass().equals(ProfileEditFragment.class))
                    NavHostFragment.findNavController(fragment).navigate(
                            R.id.action_profileEditFragment_to_profileFragment, bundle);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Не удалось сохранить изображение",
                        Toast.LENGTH_SHORT).show();
                Log.e("API_TEST_UPDATE_TOURIST", error.toString());
            }
        }
        );
        RequestQueue referenceQueue = Volley.newRequestQueue(context);
        referenceQueue.add(jsonObjectRequest);
    }

    @Override
    public void sendCode(String email, Bundle bundle){
        String url = BASE_URL + "/emailcode/insert?email=" + email;
        StringRequest jsonObjectRequest = new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("API_TEST_ADD_EMAIL_CODE", response);
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.action_signUpFragment_to_confirmEmailFragment, bundle);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Ошибка отправки кода",
                        Toast.LENGTH_SHORT).show();
                Log.e("API_ADD_EMAIL_CODE", error.toString());
            }
        }
        );
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue referenceQueue = Volley.newRequestQueue(context);
        referenceQueue.add(jsonObjectRequest);
    }

    @Override
    public void updateEmailCode(String email){
        String url = BASE_URL + "/emailcode/update?email=" + email;
        StringRequest jsonObjectRequest = new StringRequest(
                Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("API_TEST_UPDATE_CODE", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Ошибка переотправки кода",
                        Toast.LENGTH_SHORT).show();
                Log.e("API_UPDATE_EMAIL_CODE", error.toString());
            }
        });
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue referenceQueue = Volley.newRequestQueue(context);
        referenceQueue.add(jsonObjectRequest);
    }

    @Override
    public void compareCode(String email, String code, Tourist tourist){
        String url = BASE_URL + "/emailcode/compare?email=" + email + "&code=" + code;
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("API_TEST_COMPARE_CODE", response.toString());
                if(response.equals("true")) {
                    register(tourist);
                    NavHostFragment.findNavController(fragment)
                            .navigate(R.id.action_confirmEmailFragment_to_signInFragment);
                }
                else Toast.makeText(context, "Неверный код", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Ошибка проверки кода",
                        Toast.LENGTH_SHORT).show();
                Log.e("API_COMPARE_EMAIL_CODE", error.toString());
            }
        }
        );
        RequestQueue referenceQueue = Volley.newRequestQueue(context);
        referenceQueue.add(stringRequest);
    }

    @Override
    public void register(Tourist tourist) {
        String url = BASE_URL + "/tourist/register";

        JSONObject params = TouristMapper.touristToJsonObject(tourist);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("API_TEST_ADD_TOURIST", response.toString());
                Bundle bundle = new Bundle();
                bundle.putSerializable("tourist", tourist);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Этот email уже зарегистрирован",
                        Toast.LENGTH_SHORT).show();
                Log.e("API_VOLLEY_ADD_TOURIST", error.toString());
            }
        }
        );
        RequestQueue referenceQueue = Volley.newRequestQueue(context);
        referenceQueue.add(jsonObjectRequest);
    }
}
