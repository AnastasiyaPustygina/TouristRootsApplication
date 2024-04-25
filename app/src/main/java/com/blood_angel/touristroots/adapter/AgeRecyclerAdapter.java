package com.blood_angel.touristroots.adapter;


import static com.blood_angel.touristroots.domain.enums.AgePeriod.AGE_18_24;
import static com.blood_angel.touristroots.domain.enums.AgePeriod.AGE_25_31;
import static com.blood_angel.touristroots.domain.enums.AgePeriod.AGE_32_38;
import static com.blood_angel.touristroots.domain.enums.AgePeriod.AGE_39_45;
import static com.blood_angel.touristroots.domain.enums.AgePeriod.AGE_46_52;
import static com.blood_angel.touristroots.domain.enums.AgePeriod.AGE_53;
import static com.blood_angel.touristroots.service.AgePeriodConverter.getAgePeriodByString;
import static com.blood_angel.touristroots.service.AgePeriodConverter.getStringValueByAgePeriod;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.blood_angel.touristroots.R;
import com.blood_angel.touristroots.domain.enums.AgePeriod;

import java.util.Arrays;
import java.util.List;

public class AgeRecyclerAdapter extends RecyclerView.Adapter<AgeRecyclerAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    private AppCompatButton currentAgeElementButton;

    private final List<AgePeriod> ages = Arrays.asList(AgePeriod.values());

    public AgeRecyclerAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.age_element, parent, false);
        return new AgeRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AgePeriod age = ages.get(position);
        if(age.equals(AGE_18_24)) {
            currentAgeElementButton = holder.button;
            holder.button.setTextColor(Color.WHITE);
            holder.button.setBackgroundDrawable(context.getDrawable(R.drawable.bg_fab_full));
        }
        holder.button.setText(getStringValueByAgePeriod(age));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                currentAgeElementButton.setBackgroundDrawable(context.getDrawable(R.drawable.bg_fab_void));
                currentAgeElementButton.setTextColor(context.getColor(R.color.green));
                currentAgeElementButton = holder.button;
                holder.button.setTextColor(Color.WHITE);
                holder.button.setBackgroundDrawable(context.getDrawable(R.drawable.bg_fab_full));
            }
        });
    }

    @Override
    public int getItemCount() {
        return ages.size();
    }
    public AgePeriod getCurrentAgeElement(){
        return getAgePeriodByString(currentAgeElementButton.getText().toString());
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatButton button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.bt_ageElement);
        }
    }
}