package net.touchsf.appclinica.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import net.touchsf.appclinica.R;
import net.touchsf.appclinica.ui.base.BaseActivity;
import net.touchsf.appclinica.util.Constants;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.spDocumentType) AppCompatSpinner spDocumentType;
    @BindView(R.id.spCivilState) AppCompatSpinner spCivilState;
    @BindView(R.id.etDocumentNumber) EditText etDocumentNumber;
    @BindView(R.id.etPassword) EditText etPassword;

    private Context context;

    @Override
    protected void setUp() {
        context = this;
        setUpDocumentType();
        setUpCivilState();
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
