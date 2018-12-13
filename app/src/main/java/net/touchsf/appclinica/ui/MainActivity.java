package net.touchsf.appclinica.ui;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;

import net.touchsf.appclinica.R;
import net.touchsf.appclinica.preference.AppPrefs;
import net.touchsf.appclinica.ui.adapter.ContentPagerAdapter;
import net.touchsf.appclinica.ui.base.BaseActivity;
import net.touchsf.appclinica.ui.content.DatesFragment;
import net.touchsf.appclinica.ui.content.DoctorsFragment;
import net.touchsf.appclinica.ui.content.RecordsFragment;
import net.touchsf.appclinica.ui.dialog.ProfileDialog;
import net.touchsf.appclinica.util.AlertDialogs;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tbOptions) TabLayout tbOptions;
    @BindView(R.id.vpContent) ViewPager vpContent;

    private ContentPagerAdapter contentPagerAdapter;
    private AppPrefs appPrefs;

    @Override
    protected void setUp() {
        appPrefs = new AppPrefs(this);
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

    @OnClick(R.id.ivProfile)
    void profile() {
        AlertDialogs.openDialog(new ProfileDialog(this));
    }

    @OnClick(R.id.ivLogout)
    void logout() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage(R.string.are_you_sure_logout)
                .setPositiveButton(R.string.yes_logout, (dialogInterface, i) -> {
                    appPrefs.setLogged(false);
                    appPrefs.setUserId(0);
                    openLoginActivity();
                })
                .setNegativeButton(R.string.no, null)
                .create();
        alertDialog.show();
    }

    private void openLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
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
