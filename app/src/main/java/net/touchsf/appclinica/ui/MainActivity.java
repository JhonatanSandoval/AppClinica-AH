package net.touchsf.appclinica.ui;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import net.touchsf.appclinica.R;
import net.touchsf.appclinica.ui.adapter.ContentPagerAdapter;
import net.touchsf.appclinica.ui.base.BaseActivity;
import net.touchsf.appclinica.ui.content.DatesFragment;
import net.touchsf.appclinica.ui.content.DoctorsFragment;
import net.touchsf.appclinica.ui.content.RecordsFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tbOptions) TabLayout tbOptions;
    @BindView(R.id.vpContent) ViewPager vpContent;

    private ContentPagerAdapter contentPagerAdapter;

    @Override
    protected void setUp() {
        configureTabLayout();
    }

    private void configureTabLayout() {
        contentPagerAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        contentPagerAdapter.addPage(DatesFragment.newInstance(), getString(R.string.my_dates));
        contentPagerAdapter.addPage(DoctorsFragment.newInstance(), getString(R.string.my_doctors));
        contentPagerAdapter.addPage(RecordsFragment.newInstance(), getString(R.string.my_records));

        vpContent.setAdapter(contentPagerAdapter);
        tbOptions.setupWithViewPager(vpContent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = contentPagerAdapter.getFragments().get(vpContent.getCurrentItem());
        fragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }
}
