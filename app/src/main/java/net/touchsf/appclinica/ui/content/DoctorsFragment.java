package net.touchsf.appclinica.ui.content;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.touchsf.appclinica.R;
import net.touchsf.appclinica.database.Database;
import net.touchsf.appclinica.database.entity.Date;
import net.touchsf.appclinica.model.DetailDoctor;
import net.touchsf.appclinica.preference.AppPrefs;
import net.touchsf.appclinica.ui.adapter.DoctorAdapter;
import net.touchsf.appclinica.ui.base.BaseFragment;
import net.touchsf.appclinica.ui.content.doctors.DetailDoctorActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class DoctorsFragment extends BaseFragment implements DoctorAdapter.DoctorClickListener {

    public static DoctorsFragment newInstance() {
        return new DoctorsFragment();
    }

    @BindView(R.id.rvDoctors) RecyclerView rvDoctors;
    @BindView(R.id.tvNoDoctors) TextView tvNoDoctors;

    private Context context;
    private AppPrefs appPrefs;

    private DoctorAdapter adapter;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadDoctors();
        }
    }

    private void loadDoctors() {
        rvDoctors.setLayoutManager(new GridLayoutManager(context, 2));
        rvDoctors.setAdapter(adapter);

        if (!getDoctors().isEmpty()) {
            rvDoctors.setVisibility(View.VISIBLE);
            tvNoDoctors.setVisibility(View.GONE);

            adapter.setDoctors(getDoctors());

        } else {
            rvDoctors.setVisibility(View.GONE);
            tvNoDoctors.setVisibility(View.VISIBLE);

        }

    }

    private List<DetailDoctor> getDoctors() {
        List<DetailDoctor> doctors = new ArrayList<>();
        List<Date> dates = Database.getDatabase(context).dateDao().getDatesFromuser(appPrefs.getUsetId());
        if (!dates.isEmpty()) {
            for (Date date : dates) {
                if (!Arrays.asList(doctors).contains(date.getDoctor())) {
                    doctors.add(new DetailDoctor(date.getDoctor(), date.getSpeciality(), ""));
                }
            }
        }
        return doctors;
    }

    @Override
    protected void setUp() {
        context = getActivity();
        appPrefs = new AppPrefs(context);

        adapter = new DoctorAdapter();
        adapter.setClickListener(this);
        adapter.setContext(context);
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_doctors;
    }

    @Override
    public void click(DetailDoctor detailDoctor) {
        Intent intent = new Intent(getActivity(), DetailDoctorActivity.class);
        intent.putExtra("doctor", detailDoctor);
        startActivity(intent);
    }
}
