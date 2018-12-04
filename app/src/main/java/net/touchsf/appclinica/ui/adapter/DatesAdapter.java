package net.touchsf.appclinica.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.touchsf.appclinica.R;
import net.touchsf.appclinica.database.entity.Date;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DatesAdapter extends RecyclerView.Adapter<DatesAdapter.DateHolder> {

    private List<Date> dates = new ArrayList<>();

    public void setDates(List<Date> dates) {
        this.dates = dates;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DateHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DateHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_date, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DateHolder dateHolder, int position) {
        dateHolder.bind(dates.get(position));
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    class DateHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvDoctor) TextView tvDoctor;
        @BindView(R.id.tvSpeciality) TextView tvSpeciality;
        @BindView(R.id.tvDate) TextView tvDate;
        @BindView(R.id.tvTime) TextView tvTime;

        public DateHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Date objDate) {
            tvDoctor.setText(objDate.getDoctor());
            tvSpeciality.setText(objDate.getSpeciality());
            tvDate.setText(objDate.getDate());
            tvTime.setText(objDate.getTime());
        }
    }

}
