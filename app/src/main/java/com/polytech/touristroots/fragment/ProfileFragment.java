package com.polytech.touristroots.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;
import com.polytech.touristroots.R;
import com.polytech.touristroots.domain.Image;
import com.polytech.touristroots.domain.Tourist;
import com.polytech.touristroots.rest.AppApiVolley;
import com.polytech.touristroots.service.AgePeriodConverter;
import com.polytech.touristroots.service.GenderConverter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class ProfileFragment extends Fragment {
    private ImageView ivAva;
    private FirebaseStorage storage;
    private Tourist tourist;
    public final static String pathToFirebase = "gs://grounded-style-420715.appspot.com";
    public static final String defaultImage = "gs://grounded-style-420715.appspot.com/images/f0d82bb4-03d0-4c9c-b216-a94e5156401b";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        tourist = (Tourist) getArguments().getSerializable("tourist");
        TextView tvName = view.findViewById(R.id.tv_profile_name);
        TextView tvEmail = view.findViewById(R.id.tv_profile_email);
        TextView tvAge = view.findViewById(R.id.tv_profile_age);
        TextView tvGender = view.findViewById(R.id.tv_profile_gender);
        TextView tvInfo = view.findViewById(R.id.tv_profile_info);
        AppCompatButton btMyEntries = view.findViewById(R.id.bt_profile_myEntries);
        FloatingActionButton btChangeImage = view.findViewById(R.id.bt_profile_updateAva);
        ivAva = view.findViewById(R.id.iv_profile_ava);
        MaterialToolbar toolbar = view.findViewById(R.id.mt_profile);
        FirebaseStorage storage = FirebaseStorage.getInstance(pathToFirebase);
        StorageReference storageReference = storage.getReference(tourist.getImage().getPath());
        Glide.with(getContext()).load(storageReference).into(ivAva);
        btChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSomeActivityForResult();
            }
        });
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
    public void openSomeActivityForResult() {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        someActivityResultLauncher.launch(intent);
    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    if (result.getResultCode() == Activity.RESULT_OK
                            && data != null && data.getData() != null) {
                        Uri uri = data.getData();
                        ivAva.setImageURI(uri);
                        uploadImage(uri);
                    }
                }
            });
    private void uploadImage(Uri uri)
    {
        if (uri != null) {
            if(storage == null) storage = FirebaseStorage.getInstance(pathToFirebase);
            StorageReference ref
                    = storage.getReference("/images")
                    .child(UUID.randomUUID().toString());
            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            tourist.setImage(new Image(0, ref.getPath()));
//                            updateAva();
                            new AppApiVolley(getContext(), ProfileFragment.this).updateTourist(tourist);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Не удалось загрузить изображение", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
