package net.touchsf.appclinica.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.touchsf.appclinica.R;
import net.touchsf.appclinica.database.entity.History;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoriesAdapter extends RecyclerView.Adapter<HistoriesAdapter.HistoryHolder> {

    private List<History> histories = new ArrayList<>();
    private HistoryClickListener clickListener;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public void setHistories(List<History> histories) {
        this.histories = histories;
        notifyDataSetChanged();
    }

    public void setClickListener(HistoryClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public HistoriesAdapter.HistoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HistoryHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_history, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoriesAdapter.HistoryHolder viewHolder, int position) {
        History objHistory = histories.get(position);
        //viewHolder.tvAttentionDate.setText(objHistory.getDate());
        viewHolder.tvAttentionDate.setText(sdf.format(new Date()));
        viewHolder.tvDiagnostic.setText(objHistory.getDiagnostic());
        viewHolder.tvDoctor.setText(objHistory.getDoctor());
        viewHolder.tvSpeciality.setText(objHistory.getSpeciality());

        viewHolder.llContent.setOnClickListener(view -> {
            clickListener.objClick(objHistory);
        });
    }

    public interface HistoryClickListener {
        void objClick(History history);
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    class HistoryHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.llContent) LinearLayout llContent;
        @BindView(R.id.tvAttentionDate) TextView tvAttentionDate;
        @BindView(R.id.tvDoctor) TextView tvDoctor;
        @BindView(R.id.tvSpeciality) TextView tvSpeciality;
        @BindView(R.id.tvDiagnostic) TextView tvDiagnostic;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
