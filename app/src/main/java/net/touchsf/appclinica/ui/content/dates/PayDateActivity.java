package net.touchsf.appclinica.ui.content.dates;

import android.app.Activity;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.touchsf.appclinica.R;
import net.touchsf.appclinica.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class PayDateActivity extends BaseActivity {

    private static final int CARD_NUMBER_TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
    private static final int CARD_NUMBER_TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
    private static final int CARD_NUMBER_DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
    private static final int CARD_NUMBER_DIVIDER_POSITION = CARD_NUMBER_DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
    private static final char CARD_NUMBER_DIVIDER = ' ';

    private static final int CARD_DATE_TOTAL_SYMBOLS = 5; // size of pattern MM/YY
    private static final int CARD_DATE_TOTAL_DIGITS = 4; // max numbers of digits in pattern: MM + YY
    private static final int CARD_DATE_DIVIDER_MODULO = 3; // means divider position is every 3rd symbol beginning with 1
    private static final int CARD_DATE_DIVIDER_POSITION = CARD_DATE_DIVIDER_MODULO - 1; // means divider position is every 2nd symbol beginning with 0
    private static final char CARD_DATE_DIVIDER = '/';

    private static final int CARD_CVC_TOTAL_SYMBOLS = 3;

    @BindView(R.id.tvTotalPrice) TextView tvTotalPrice;
    @BindView(R.id.cardNumberEditText) EditText cardNumberEditText;
    @BindView(R.id.cardDateEditText) EditText cardDateEditText;
    @BindView(R.id.cardCVCEditText) EditText cardCVCEditText;

    @BindView(R.id.btnConfirmPay) Button btnConfirmPay;

    @Override
    protected void setUp() {
        validateEmptyFields();
        getPriceToPay();
    }

    private void getPriceToPay() {
        double price = getIntent().getDoubleExtra("price", 50.00);
        tvTotalPrice.setText("S/ " + price);
    }

    @OnClick(R.id.btnConfirmPay)
    void confirmPay() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    private void validateEmptyFields() {
        String cardNumber = cardNumberEditText.getText().toString().trim();
        String cardDate = cardDateEditText.getText().toString().trim();
        String cardCvc = cardCVCEditText.getText().toString().trim();

        boolean state = !cardNumber.isEmpty() && !cardDate.isEmpty() && !cardCvc.isEmpty();
        btnConfirmPay.setFocusable(state);
        btnConfirmPay.setClickable(state);
        btnConfirmPay.setEnabled(state);

        btnConfirmPay.setAlpha(state ? 1.0f : 0.5f);
    }

    @OnClick(R.id.tvCancel)
    void cancel() {
        finish();
    }

    @OnTextChanged(value = R.id.cardNumberEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    protected void onCardNumberTextChanged(Editable s) {
        if (!isInputCorrect(s, CARD_NUMBER_TOTAL_SYMBOLS, CARD_NUMBER_DIVIDER_MODULO, CARD_NUMBER_DIVIDER)) {
            s.replace(0, s.length(), concatString(getDigitArray(s, CARD_NUMBER_TOTAL_DIGITS), CARD_NUMBER_DIVIDER_POSITION, CARD_NUMBER_DIVIDER));
        }
        if (!s.toString().isEmpty()) {
            validateEmptyFields();
        }
    }

    @OnTextChanged(value = R.id.cardDateEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    protected void onCardDateTextChanged(Editable s) {
        if (!isInputCorrect(s, CARD_DATE_TOTAL_SYMBOLS, CARD_DATE_DIVIDER_MODULO, CARD_DATE_DIVIDER)) {
            s.replace(0, s.length(), concatString(getDigitArray(s, CARD_DATE_TOTAL_DIGITS), CARD_DATE_DIVIDER_POSITION, CARD_DATE_DIVIDER));
        }
        if (!s.toString().isEmpty()) {
            validateEmptyFields();
        }
    }

    @OnTextChanged(value = R.id.cardCVCEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    protected void onCardCVCTextChanged(Editable s) {
        if (s.length() > CARD_CVC_TOTAL_SYMBOLS) {
            s.delete(CARD_CVC_TOTAL_SYMBOLS, s.length());
        }
        if (!s.toString().isEmpty()) {
            validateEmptyFields();
        }
    }

    private boolean isInputCorrect(Editable s, int size, int dividerPosition, char divider) {
        boolean isCorrect = s.length() <= size;
        for (int i = 0; i < s.length(); i++) {
            if (i > 0 && (i + 1) % dividerPosition == 0) {
                isCorrect &= divider == s.charAt(i);
            } else {
                isCorrect &= Character.isDigit(s.charAt(i));
            }
        }
        return isCorrect;
    }

    private String concatString(char[] digits, int dividerPosition, char divider) {
        final StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < digits.length; i++) {
            if (digits[i] != 0) {
                formatted.append(digits[i]);
                if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                    formatted.append(divider);
                }
            }
        }

        return formatted.toString();
    }

    private char[] getDigitArray(final Editable s, final int size) {
        char[] digits = new char[size];
        int index = 0;
        for (int i = 0; i < s.length() && index < size; i++) {
            char current = s.charAt(i);
            if (Character.isDigit(current)) {
                digits[index] = current;
                index++;
            }
        }
        return digits;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_pay_date;
    }

}
