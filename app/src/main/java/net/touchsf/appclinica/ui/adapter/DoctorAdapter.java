package net.touchsf.appclinica.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.touchsf.appclinica.R;
import net.touchsf.taxitel.cliente.util.CircleTransform;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorHolder> {

    private Context context;
    private List<String> doctors = new ArrayList<>();

    public void setDoctors(List<String> doctors) {
        this.doctors = doctors;
        notifyDataSetChanged();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DoctorHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DoctorHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_doctor, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorHolder doctorHolder, int position) {
        doctorHolder.tvDoctorName.setText(doctors.get(position));
        Picasso.with(context).load(R.drawable.doctor_avatar_2).transform(new CircleTransform()).into(doctorHolder.ivDoctorImage);
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    class DoctorHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivDoctorImage) ImageView ivDoctorImage;
        @BindView(R.id.tvDoctorName) TextView tvDoctorName;

        public DoctorHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
