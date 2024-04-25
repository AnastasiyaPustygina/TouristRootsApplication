package com.blood_angel.touristroots.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.blood_angel.touristroots.R;
import com.blood_angel.touristroots.domain.Tourist;
import com.blood_angel.touristroots.service.AgePeriodConverter;
import com.blood_angel.touristroots.service.GenderConverter;
import com.google.android.material.appbar.MaterialToolbar;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        Tourist tourist = (Tourist) getArguments().getSerializable("tourist");
        TextView tvName = view.findViewById(R.id.tv_profile_name);
        TextView tvEmail = view.findViewById(R.id.tv_profile_email);
        TextView tvAge = view.findViewById(R.id.tv_profile_age);
        TextView tvGender = view.findViewById(R.id.tv_profile_gender);
        TextView tvInfo = view.findViewById(R.id.tv_profile_info);
        AppCompatButton btMyEntries = view.findViewById(R.id.bt_profile_myEntries);
        MaterialToolbar toolbar = view.findViewById(R.id.mt_profile);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfileFragment.this)
                        .navigate(R.id.action_profileFragment_to_signInFragment);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    getContext().deleteSharedPreferences("remember");
                }
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.prAb_edit){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("tourist", tourist);
                    NavHostFragment.findNavController(ProfileFragment.this).navigate(
                            R.id.action_profileFragment_to_profileEditFragment, bundle);
                }
                return false;
            }
        });
        tvName.setText(tourist.getName());
        tvEmail.setText(tourist.getEmail());
        tvAge.setText(AgePeriodConverter.getStringValueByAgePeriod(tourist.getAgePeriod()));
        tvGender.setText(GenderConverter.getStringValueByGender(tourist.getGender()));
        tvInfo.setText(tourist.getInformation());

        return view;
    }
}
