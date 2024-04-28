package com.polytech.touristroots.fragment;

import static com.polytech.touristroots.fragment.ProfileFragment.defaultImage;
import static com.polytech.touristroots.fragment.ProfileFragment.pathToFirebase;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.polytech.touristroots.R;
import com.polytech.touristroots.adapter.AgeRecyclerAdapter;
import com.polytech.touristroots.domain.Image;
import com.polytech.touristroots.domain.Tourist;
import com.polytech.touristroots.domain.enums.AgePeriod;
import com.polytech.touristroots.domain.enums.Gender;
import com.polytech.touristroots.rest.AppApiVolley;

import java.util.UUID;

public class ProfileEditFragment extends Fragment {

    private Gender gender;
    private FirebaseStorage storage;
    private ImageView ivAva;
    private Tourist tourist;

    private Uri imageUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_edit_fragment, container, false);

        tourist = (Tourist) getArguments().getSerializable("tourist");
        ImageView ivBack = view.findViewById(R.id.iv_editProfile_back);
        ivAva = view.findViewById(R.id.iv_editProfile_ava);
        EditText etName = view.findViewById(R.id.et_editProfile_name);
        EditText etEmail = view.findViewById(R.id.et_editProfile_email);
        EditText etInfo = view.findViewById(R.id.et_editProfile_info);
        AppCompatButton btGenderMale = view.findViewById(R.id.bt_editProfile_male);
        AppCompatButton btGenderFemale = view.findViewById(R.id.bt_editProfile_female);
        AppCompatButton btSave = view.findViewById(R.id.bt_editProfile_editProfile);
        FloatingActionButton btChangeImage = view.findViewById(R.id.bt_editProfile_changeImage);
        storage = FirebaseStorage.getInstance(pathToFirebase);
        StorageReference storageReference = storage.getReference(tourist.getImage().getPath());
        Glide.with(getContext()).load(storageReference).into(ivAva);
        etEmail.setText(tourist.getEmail());
        etInfo.setText(tourist.getInformation());
        etName.setText(tourist.getName());
        btChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSomeActivityForResult();
            }
        });
        gender = tourist.getGender();
        RecyclerView rvAges = view.findViewById(R.id.rv_editProfile_ages);
        AgeRecyclerAdapter adapter = new AgeRecyclerAdapter(getContext(), tourist.getAgePeriod());
        rvAges.setAdapter(adapter);
        FirebaseStorage storage = FirebaseStorage.getInstance(pathToFirebase);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tourist = new Tourist(tourist.getId(), etName.getText().toString(),
                        etEmail.getText().toString(), gender, adapter.getCurrentAgeElement(), etInfo.getText().toString(),
                        tourist.getPassword(), tourist.getImage());
                if(imageUri != null) uploadTouristWithImage(imageUri);
                else new AppApiVolley(getContext(), ProfileEditFragment.this).updateTourist(tourist);
            }
        });
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
        if(tourist.getGender().equals(Gender.MALE)) btGenderMale.performClick();
        else btGenderFemale.performClick();
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

    public void openSomeActivityForResult() {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        someActivityResultLauncher.launch(intent);
    }
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
                        imageUri = uri;
                    }
                }
            });
    private void uploadTouristWithImage(Uri uri)
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
                            updateAva();
                            new AppApiVolley(getContext(), ProfileEditFragment.this).updateTourist(tourist);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Не удалось сохранить изменения", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    public void updateAva(){
        try{
            Glide.with(getContext()).load(tourist.getImage().getPath()).into(ivAva);
        }catch (Exception e){
            Glide.with(getContext()).load(defaultImage).into(ivAva);
        }
    }
}
