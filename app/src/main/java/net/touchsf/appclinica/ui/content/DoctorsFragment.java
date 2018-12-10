package net.touchsf.appclinica.ui.content;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.touchsf.appclinica.R;
import net.touchsf.appclinica.database.Database;
import net.touchsf.appclinica.database.entity.Date;
import net.touchsf.appclinica.preference.AppPrefs;
import net.touchsf.appclinica.ui.adapter.DoctorAdapter;
import net.touchsf.appclinica.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class DoctorsFragment extends BaseFragment {

    public static DoctorsFragment newInstance() {
        return new DoctorsFragment();
    }

    @BindView(R.id.rvDoctors) RecyclerView rvDoctors;

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
        adapter.setDoctors(getDoctors());
    }

    private List<String> getDoctors() {
        List<String> doctors = new ArrayList<>();
        List<Date> dates = Database.getDatabase(context).dateDao().getDatesFromuser(appPrefs.getUsetId());
        if (!dates.isEmpty()) {
            for (Date date : dates) {
                if (!Arrays.asList(doctors).contains(date.getDoctor())) {
                    doctors.add(date.getDoctor());
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
        adapter.setContext(context);
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_doctors;
    }
}
