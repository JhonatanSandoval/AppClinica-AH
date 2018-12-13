package net.touchsf.appclinica.ui.content.histories;

import android.support.v7.widget.Toolbar;

import net.touchsf.appclinica.R;
import net.touchsf.appclinica.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class HistoryDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void setUp() {
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.ivBack)
    void back() {
        finish();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_history_detail;
    }

}
