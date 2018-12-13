package net.touchsf.appclinica.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatSpinner;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import net.touchsf.appclinica.R;
import net.touchsf.appclinica.database.Database;
import net.touchsf.appclinica.database.entity.User;
import net.touchsf.appclinica.preference.AppPrefs;
import net.touchsf.appclinica.ui.adapter.GeneralSpinnerAdapter;
import net.touchsf.appclinica.ui.base.BaseActivity;
import net.touchsf.appclinica.util.AlertDialogs;
import net.touchsf.appclinica.util.Constants;

import java.util.Calendar;

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
    @BindView(R.id.etBirthday) EditText etBirthday;
    @BindView(R.id.etBirthPlace) EditText etBirthPlace;

    private Context context;
    private AppPrefs appPrefs;

    @Override
    protected void setUp() {
        context = this;
        appPrefs = new AppPrefs(context);
        setUpDocumentType();
        setUpCivilState();
        setUpBirthday();
    }

    @OnClick(R.id.ivBack)
    void back() {
        openMainActivity();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpBirthday() {
        etBirthday.setOnTouchListener((view, motionEvent) -> {
            if (MotionEvent.ACTION_UP == motionEvent.getAction()) {
                openDatePickerDialog();
            }
            return true;
        });
    }

    private Calendar calendar = Calendar.getInstance();
    private int year = calendar.get(Calendar.YEAR);
    private int month = calendar.get(Calendar.MONTH);
    private int day = calendar.get(Calendar.DAY_OF_MONTH);

    private void openDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year, month, dayOfMonth) -> {
            etBirthday.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
        }, year, month, day);
        datePickerDialog.show();
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
        String birthday = etBirthday.getText().toString().trim();
        String birthPlace = etBirthPlace.getText().toString().trim();

        if (!documentNumber.isEmpty() && !password.isEmpty() && !names.isEmpty() && !firstLastName.isEmpty()
                && !secondLastName.isEmpty() && !birthday.isEmpty() && !birthPlace.isEmpty()) {

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
                    user.setBirthday(birthday);
                    user.setPlaceBirth(birthPlace);
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
        GeneralSpinnerAdapter adapter = new GeneralSpinnerAdapter(spCivilState);
        adapter.setNothingSelectedDataItem("");
        adapter.update(Constants.CIVIL_STATE);
        adapter.setSelection(-1);
        spCivilState.setAdapter(adapter);
    }

    private void setUpDocumentType() {
        GeneralSpinnerAdapter adapter = new GeneralSpinnerAdapter(spDocumentType);
        adapter.setNothingSelectedDataItem("");
        adapter.update(Constants.DOCUMENT_TYPES);
        adapter.setSelection(-1);
        spDocumentType.setAdapter(adapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }
}
