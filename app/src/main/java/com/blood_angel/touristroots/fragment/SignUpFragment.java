package com.blood_angel.touristroots.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.blood_angel.touristroots.R;
import com.blood_angel.touristroots.adapter.AgeRecyclerAdapter;
import com.blood_angel.touristroots.domain.Tourist;
import com.blood_angel.touristroots.domain.enums.AgePeriod;
import com.blood_angel.touristroots.domain.enums.Gender;
import com.blood_angel.touristroots.rest.AppApiVolley;

public class SignUpFragment extends Fragment {

    private Gender gender = Gender.MALE;
    private AgePeriod age = AgePeriod.AGE_18_24;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration_fragment, container, false);
        AppCompatButton btSignUp = view.findViewById(R.id.bt_register_signUp);
        AppCompatButton btGenderMale = view.findViewById(R.id.bt_register_male);
        AppCompatButton btGenderFemale = view.findViewById(R.id.bt_register_female);
        EditText etName = view.findViewById(R.id.et_register_name);
        EditText etEmail = view.findViewById(R.id.et_register_email);
        EditText etInfo = view.findViewById(R.id.et_register_info);
        EditText etPass = view.findViewById(R.id.et_register_pass);
        EditText etPassConfirm = view.findViewById(R.id.et_register_passConfirm);
        RecyclerView rvAges = view.findViewById(R.id.rv_register_ages);
        AgeRecyclerAdapter adapter = new AgeRecyclerAdapter(getContext());
        rvAges.setAdapter(adapter);
        btGenderMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btGenderMale.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btGenderMale.setBackgroundDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.bg_fab_full));
                        btGenderMale.setTextColor(Color.WHITE);
                        btGenderFemale.setBackgroundDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.bg_fab_void));
                        btGenderFemale.setTextColor(getResources().getColor(R.color.green));
                        gender = Gender.MALE;
                    }
                });
            }
        });
        btGenderFemale.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btGenderFemale.setBackgroundDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.bg_fab_full));
                        btGenderFemale.setTextColor(Color.WHITE);
                        btGenderMale.setBackgroundDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.bg_fab_void));
                        btGenderMale.setTextColor(getResources().getColor(R.color.green));
                        gender = Gender.FEMALE;
            }
        });
        ImageView ivBack = view.findViewById(R.id.iv_register_back);
        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String info = etInfo.getText().toString();
                String pass = etPass.getText().toString();
                age = adapter.getCurrentAgeElement();
                String passConfirm = etPassConfirm.getText().toString();
                if(!email.contains("@"))
                    Toast.makeText(getContext(), "Введите корректный email", Toast.LENGTH_SHORT).show();
                else if (!pass.equals(passConfirm)) {
                    Toast.makeText(getContext(), "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                }
                else {
                    Tourist tourist = new Tourist(0, name, email, gender, age, info, pass);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("tourist", tourist);
                    new AppApiVolley(getContext(), SignUpFragment.this).sendCode(tourist.getEmail(), bundle);
                }
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SignUpFragment.this).navigate(R.id.action_signUpFragment_to_signInFragment);
            }
        });
        return view;
    }
}
