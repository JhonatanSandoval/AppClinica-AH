package net.touchsf.appclinica.ui.content;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.touchsf.appclinica.R;
import net.touchsf.appclinica.database.Database;
import net.touchsf.appclinica.database.entity.Date;
import net.touchsf.appclinica.preference.AppPrefs;
import net.touchsf.appclinica.ui.adapter.DatesAdapter;
import net.touchsf.appclinica.ui.base.BaseFragment;
import net.touchsf.appclinica.ui.content.dates.AddDateActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DatesFragment extends BaseFragment {

    public static DatesFragment newInstance() {
        return new DatesFragment();
    }

    @BindView(R.id.rvDates) RecyclerView rvDates;
    @BindView(R.id.tvNoDates) TextView tvNoDates;

    private DatesAdapter adapter;
    private Context context;
    private AppPrefs appPrefs;

    @Override
    protected void setUp() {
        context = getActivity();
        appPrefs = new AppPrefs(context);

        configureRecyclerview();
        loadDates();
    }

    private void configureRecyclerview() {
        rvDates.setLayoutManager(new LinearLayoutManager(context));

        adapter = new DatesAdapter();
        rvDates.setAdapter(adapter);
    }

    private void loadDates() {
        if (!getDatesFromuser().isEmpty()) {
            tvNoDates.setVisibility(View.GONE);
            rvDates.setVisibility(View.VISIBLE);
            adapter.setDates(getDatesFromuser());
        } else {
            tvNoDates.setVisibility(View.VISIBLE);
            rvDates.setVisibility(View.GONE);
        }
    }

    @NonNull
    private List<Date> getDatesFromuser() {
        return Database.getDatabase(context).dateDao().getDatesFromuser(appPrefs.getUsetId());
    }

    @OnClick(R.id.fabAddDate)
    void addDate() {
        startActivityForResult(new Intent(context, AddDateActivity.class), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            loadDates();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_dates;
    }

}
