package net.touchsf.appclinica.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import net.touchsf.appclinica.R;
import net.touchsf.appclinica.database.Database;
import net.touchsf.appclinica.database.entity.User;
import net.touchsf.appclinica.preference.AppPrefs;
import net.touchsf.appclinica.ui.base.BaseActivity;
import net.touchsf.appclinica.util.AlertDialogs;
import net.touchsf.appclinica.util.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.spDocumentType) AppCompatSpinner spDocumentType;
    @BindView(R.id.spCivilState) AppCompatSpinner spCivilState;
    @BindView(R.id.etDocumentNumber) EditText etDocumentNumber;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.etRepeatPassword) EditText etRepeatPassword;
    @BindView(R.id.etNames) EditText etNames;
    @BindView(R.id.etFirstLastName) EditText etFirstLastName;
    @BindView(R.id.etSecondLastName) EditText etSecondLastName;

    private Context context;
    private AppPrefs appPrefs;

    @Override
    protected void setUp() {
        context = this;
        appPrefs = new AppPrefs(context);
        setUpDocumentType();
        setUpCivilState();
    }

    @OnClick(R.id.btnRegister)
    void register() {
        String documentType = spDocumentType.getSelectedItem().toString();
        String documentNumber = etDocumentNumber.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String names = etNames.getText().toString().trim();
        String firstLastName = etFirstLastName.getText().toString().trim();
        String secondLastName = etSecondLastName.getText().toString().trim();
        String civilState = spCivilState.getSelectedItem().toString();

        if (!documentNumber.isEmpty() && !password.isEmpty() && !names.isEmpty() && !firstLastName.isEmpty()
                && !secondLastName.isEmpty()) {

            if (password.equals(etRepeatPassword.getText().toString().trim())) {

                if (getUserByDocumentTypeAndNumber(documentType, documentNumber) == null) {
                    User user = new User();
                    user.setDocumentType(documentType);
                    user.setDocumentNumber(documentNumber);
                    user.setNames(names);
                    user.setFirstLastName(firstLastName);
                    user.setSecondLastName(secondLastName);
                    user.setPassword(password);
                    user.setCivilState(civilState);
                    Database.getDatabase(context).userDao().insert(user);

                    appPrefs.setLogged(true);
                    appPrefs.setUserId(Database.getDatabase(context).userDao().findByDocumentTypeAndNumber(documentType, documentNumber).getUid());
                    openMainActivity();
                } else {
                    AlertDialogs.showMessage(context, R.string.user_already_exists);

                }

            } else {
                AlertDialogs.showMessage(context, R.string.password_does_not_match);

            }

        }
    }

    private void openMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private User getUserByDocumentTypeAndNumber(String documentType, String documentNumber) {
        return Database.getDatabase(context).userDao().findByDocumentTypeAndNumber(documentType, documentNumber);
    }

    @OnClick(R.id.tvCancel)
    void cancelRegister() {
        startActivity(new Intent(context, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        cancelRegister();
    }

    private void setUpCivilState() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, Constants.CIVIL_STATE);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCivilState.setAdapter(adapter);
    }

    private void setUpDocumentType() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, Constants.DOCUMENT_TYPES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDocumentType.setAdapter(adapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }
}
