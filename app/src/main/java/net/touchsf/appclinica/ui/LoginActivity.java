package net.touchsf.appclinica.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import net.touchsf.appclinica.R;
import net.touchsf.appclinica.database.Database;
import net.touchsf.appclinica.database.entity.User;
import net.touchsf.appclinica.ui.base.BaseActivity;
import net.touchsf.appclinica.util.AlertDialogs;
import net.touchsf.appclinica.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.spDocumentType) AppCompatSpinner spDocumentType;
    @BindView(R.id.etDocumentNumber) EditText etDocumentNumber;
    @BindView(R.id.etPassword) EditText etPassword;

    private Context context;

    @Override
    protected void setUp() {
        context = this;
        validateInitialUsers();
        setUpDocumentType();
    }

    @OnItemSelected(R.id.spDocumentType)
    void documentTypeItemSelected(AppCompatSpinner spinner, int position) {
        etDocumentNumber.setText("");
        etPassword.setText("");
    }

    @OnClick(R.id.btnLogin)
    void login() {
        String documentType = spDocumentType.getSelectedItem().toString();
        String documentNumber = etDocumentNumber.getText().toString().trim();
        if (!documentType.isEmpty() && !documentNumber.isEmpty()) {
            User user = getUserByDocumentTypeAndNumber(documentType, documentNumber);
            if (user != null) {

            } else {
                AlertDialogs.showMessage(context, R.string.no_user_found);
            }
        } else {
            etDocumentNumber.setError(getString(R.string.field_must_be_filled));
        }
    }

    private User getUserByDocumentTypeAndNumber(String documentType, String documentNumber) {
        return Database.getDatabase(context).userDao().findByDocumentTypeAndNumber(documentType, documentNumber);
    }

    @OnClick(R.id.tvRegister)
    void register() {
        startActivity(new Intent(context, RegisterActivity.class));
        finish();
    }

    private void setUpDocumentType() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, Constants.DOCUMENT_TYPES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDocumentType.setAdapter(adapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    private void validateInitialUsers() {
        List<User> users = Database.getDatabase(context).userDao().getAll();
        if (users == null || users.isEmpty()) {
            insertAllInitialUsers();
        }
    }

    private void insertAllInitialUsers() {
        List<User> users = new ArrayList<>();

        User user = new User();
        user.setDocumentType(Constants.DOCUMENT_TYPES[0]);
        user.setDocumentNumber("73123125");
        user.setPassword("123");
        user.setNames("Jhonatan Joel");
        user.setFirstLastName("Sandoval");
        user.setSecondLastName("Bazán");
        user.setCivilState(Constants.CIVIL_STATE[0]);
        users.add(user);

        Database.getDatabase(context).userDao().insertAll(users);
    }

}
