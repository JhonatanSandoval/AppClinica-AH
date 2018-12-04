package net.touchsf.appclinica.ui.content;

import net.touchsf.appclinica.R;
import net.touchsf.appclinica.ui.base.BaseFragment;

public class RecordsFragment extends BaseFragment {

    public static RecordsFragment newInstance() {
        return new RecordsFragment();
    }

    @Override
    protected void setUp() {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_records;
    }
}
