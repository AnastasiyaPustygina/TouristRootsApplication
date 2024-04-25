package com.blood_angel.touristroots.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.blood_angel.touristroots.R;
import com.blood_angel.touristroots.domain.Tourist;

public class ProfileEditFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_edit_fragment, container, false);
        ImageView ivBack = view.findViewById(R.id.iv_editProfile_back);
        Tourist tourist = (Tourist) getArguments().getSerializable("tourist");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("tourist", tourist);
                NavHostFragment.findNavController(ProfileEditFragment.this).navigate(
                        R.id.action_profileEditFragment_to_profileFragment, bundle);
            }
        });
        return view;
    }
}
