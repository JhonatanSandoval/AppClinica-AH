package net.touchsf.appclinica.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import net.touchsf.appclinica.R;
import net.touchsf.appclinica.database.Database;
import net.touchsf.appclinica.database.entity.History;
import net.touchsf.appclinica.database.entity.User;
import net.touchsf.appclinica.preference.AppPrefs;
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
    private AppPrefs appPrefs;

    @Override
    protected void setUp() {
        context = this;
        appPrefs = new AppPrefs(context);
        validateInitialUsers();
        setUpDocumentType();
        checkIfUserIsLogged();
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
                String password = etPassword.getText().toString().trim();
                if (!password.isEmpty()) {
                    if (password.equals(user.getPassword())) {
                        appPrefs.setLogged(true);
                        appPrefs.setUserId(user.getUid());
                        openMainActivity();
                    } else {
                        AlertDialogs.showMessage(context, R.string.invalid_password);
                    }
                }
            } else {
                AlertDialogs.showMessage(context, R.string.no_user_found);
            }
        } else {
            etDocumentNumber.setError(getString(R.string.field_must_be_filled));
        }
    }

    private void openMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
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

    private void checkIfUserIsLogged() {
        if (appPrefs.isLogged() && appPrefs.getUsetId() != 0) {
            openMainActivity();
        }
    }

    private void insertAllInitialUsers() {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setDocumentType(Constants.DOCUMENT_TYPES[0]);
        user.setDocumentNumber("10082626");
        user.setPassword("123");
        user.setNames("Miguel Dario");
        user.setFirstLastName("Armey");
        user.setSecondLastName("Tejada");
        user.setCivilState(Constants.CIVIL_STATE[0]);
        user.setBirthday("01/12/1978");
        user.setPlaceBirth("Cañete, Perú");
        users.add(user);
        Database.getDatabase(context).userDao().insertAll(users);

        History history = new History();
        history.setUser_id(1);
        history.setDate("05/06/2018");
        history.setSpeciality("Medicina general");
        history.setDiagnostic("Crisis asmática");
        history.setDoctor("José Ramírez peña");
        Database.getDatabase(context).historyDao().insertDate(history);
    }

}
