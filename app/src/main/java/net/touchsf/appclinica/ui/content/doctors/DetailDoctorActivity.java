package net.touchsf.appclinica.ui.content.doctors;

import android.widget.EditText;

import net.touchsf.appclinica.R;
import net.touchsf.appclinica.model.DetailDoctor;
import net.touchsf.appclinica.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailDoctorActivity extends BaseActivity {

    @BindView(R.id.etDoctor) EditText etDoctor;
    @BindView(R.id.etSpeciality) EditText etSpeciality;

    @Override
    protected void setUp() {
        if (getIntent() != null) {
            DetailDoctor detailDoctor = (DetailDoctor) getIntent().getSerializableExtra("doctor");
            if (detailDoctor != null) {
                etDoctor.setText(detailDoctor.getDoctor());
                etSpeciality.setText(detailDoctor.getSpeciality());
            }
        }
    }

    @OnClick(R.id.ivBack)
    void back() {
        finish();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_detail_doctor;
    }
}
