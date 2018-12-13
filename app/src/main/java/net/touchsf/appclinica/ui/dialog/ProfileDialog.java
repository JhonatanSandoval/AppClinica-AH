package net.touchsf.appclinica.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.EditText;

import net.touchsf.appclinica.R;
import net.touchsf.appclinica.database.AppDatabase;
import net.touchsf.appclinica.database.Database;
import net.touchsf.appclinica.database.entity.User;
import net.touchsf.appclinica.preference.AppPrefs;
import net.touchsf.appclinica.ui.base.BaseDialog;
import net.touchsf.appclinica.util.MainUtil;

import java.util.Date;

import butterknife.BindView;

public class ProfileDialog extends BaseDialog {

    private AppDatabase database;
    private AppPrefs appPrefs;

    public ProfileDialog(@NonNull Context context) {
        super(context);
    }

    @BindView(R.id.etHistory) EditText etHistory;
    @BindView(R.id.etPatient) EditText etPatient;
    @BindView(R.id.etAge) EditText etAge;
    @BindView(R.id.etPlaceBirth) EditText etPlaceBirth;

    @Override
    protected void setUp() {
        database = Database.getDatabase(getContext());
        appPrefs = new AppPrefs(getContext());

        loadUserDetails();
    }

    private void loadUserDetails() {
        User currentUser = database.userDao().findByUserId(appPrefs.getUsetId());
        etHistory.setText(currentUser.getDocumentNumber());

        String patient = currentUser.getNames() + " " + currentUser.getFirstLastName() + " " + currentUser.getSecondLastName();
        etPatient.setText(patient);

        etPlaceBirth.setText(currentUser.getPlaceBirth());
        etAge.setText(MainUtil.getEdad(new Date(currentUser.getBirthday())));
    }

    @Override
    protected int getDialogLayout() {
        return R.layout.dialog_profile;
    }

}
