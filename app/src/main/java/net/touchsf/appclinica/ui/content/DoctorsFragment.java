package net.touchsf.appclinica.ui.content;

import android.content.Context;
import android.content.Intent;

import net.touchsf.appclinica.R;
import net.touchsf.appclinica.ui.AddDateActivity;
import net.touchsf.appclinica.ui.base.BaseFragment;

import butterknife.OnClick;

public class DoctorsFragment extends BaseFragment {

    public static DoctorsFragment newInstance() {
        return new DoctorsFragment();
    }

    private Context context;

    @Override
    protected void setUp() {
        context = getActivity();
    }



    @Override
    protected int getLayout() {
        return R.layout.fragment_doctors;
    }
}
