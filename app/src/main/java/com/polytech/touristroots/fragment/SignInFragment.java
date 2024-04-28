package com.polytech.touristroots.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.polytech.touristroots.R;
import com.polytech.touristroots.rest.AppApiVolley;

public class SignInFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.authorization_fragment, container, false);
        AppCompatButton btSign = view.findViewById(R.id.bt_auth_signIn);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(
                "remember", 0);
        TextView tvRegister = view.findViewById(R.id.tv_auth_signUp);
        EditText etEmail = view.findViewById(R.id.et_auth_email);
        EditText etPassword = view.findViewById(R.id.et_auth_password);
        CheckBox chRemember = view.findViewById(R.id.cb_auth_rememberMe);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SignInFragment.this).navigate(R.id.action_signInFragment_to_signUpFragment);
            }
        });
        btSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                if(chRemember.isChecked()){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.apply();
                }
                if(email.isEmpty() || password.isEmpty())
                    Toast.makeText(getContext(), "Введите email и пароль", Toast.LENGTH_SHORT).show();
                else
                    new AppApiVolley(getContext(), SignInFragment.this).login(email, password);
            }
        });
        if(!sharedPreferences.getString("email", "not").equals("not")) {
            etEmail.setText(sharedPreferences.getString("email", "not"));
            etPassword.setText(sharedPreferences.getString("password", "not"));
            btSign.performClick();
        }
        return view;
    }
}
