package net.touchsf.appclinica.ui.content;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.touchsf.appclinica.R;
import net.touchsf.appclinica.database.entity.Date;
import net.touchsf.appclinica.ui.AddDateActivity;
import net.touchsf.appclinica.ui.adapter.DatesAdapter;
import net.touchsf.appclinica.ui.base.BaseFragment;
import net.touchsf.appclinica.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

public class DatesFragment extends BaseFragment {

    public static DatesFragment newInstance() {
        return new DatesFragment();
    }

    @BindView(R.id.rvDates) RecyclerView rvDates;

    private DatesAdapter adapter;
    private Context context;

    @Override
    protected void setUp() {
        context = getActivity();

        configureRecyclerview();
        loadDates();
    }

    private void configureRecyclerview() {
        rvDates.setLayoutManager(new LinearLayoutManager(context));

        adapter = new DatesAdapter();
        rvDates.setAdapter(adapter);
    }

    private void loadDates() {
        adapter.setDates(getUserDates());
    }

    private List<Date> getUserDates() {
        List<Date> dates = new ArrayList<>();

        Date date = new Date();
        date.setSpeciality(Constants.SPECIALITY_TYPES[getRandomNumber(1, 20)]);
        date.setDoctor(Constants.DOCTORS[getRandomNumber(0, 4)]);
        date.setDate("06/12/2018");
        date.setTime("08:00am - 08:30am");
        dates.add(date);

        date = new Date();
        date.setSpeciality(Constants.SPECIALITY_TYPES[getRandomNumber(1, 20)]);
        date.setDoctor(Constants.DOCTORS[getRandomNumber(0, 4)]);
        date.setDate("08/12/2018");
        date.setTime("03:30pm - 04:00pm");
        dates.add(date);

        return dates;
    }

    private int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
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
