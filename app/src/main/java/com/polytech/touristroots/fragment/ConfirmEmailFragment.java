package com.polytech.touristroots.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.polytech.touristroots.R;
import com.polytech.touristroots.domain.Tourist;
import com.polytech.touristroots.rest.AppApiVolley;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfirmEmailFragment extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.confirm_email_fragment, container, false);
        AppCompatButton btConfirm = view.findViewById(R.id.bt_confirmEmail_signIn);
        ImageView ivBack = view.findViewById(R.id.iv_confirmEmail_back);
        Tourist tourist = (Tourist) getArguments().getSerializable("tourist");
        TextView tvEmail = view.findViewById(R.id.tv_confirmEmail_emailValue);
        TextView tvSendAgain = view.findViewById(R.id.tv_confirmEmail_sendAgain);
        tvEmail.setText("Email: " + tourist.getEmail());
        List<EditText> editTexts = Arrays.asList(view.findViewById(R.id.et_confirmEmail_1),
                view.findViewById(R.id.et_confirmEmail_2),view.findViewById(R.id.et_confirmEmail_3),
                view.findViewById(R.id.et_confirmEmail_4),view.findViewById(R.id.et_confirmEmail_5),
                view.findViewById(R.id.et_confirmEmail_6));
        tvSendAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AppApiVolley(getContext(), ConfirmEmailFragment.this)
                        .updateEmailCode(tourist.getEmail());
            }
        });
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Character> code = new ArrayList<>();
                editTexts.stream().forEach(e -> code.add(e.getText().charAt(0)));
                String codeString = code.toString()
                        .substring(1, 3 * code.size() - 1)
                        .replaceAll(", ", "");
                new AppApiVolley(getContext(), ConfirmEmailFragment.this)
                        .compareCode(tourist.getEmail(), codeString, tourist);
                }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ConfirmEmailFragment.this).navigate(R.id.action_confirmEmailFragment_to_signUpFragment);
            }
        });
        return view;
    }
}

