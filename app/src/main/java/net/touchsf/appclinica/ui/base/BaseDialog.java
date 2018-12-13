package net.touchsf.appclinica.ui.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseDialog extends Dialog {

    private Context mContext;
    private Activity mActivity;

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, Activity activity) {
        super(context);
        mContext = context;
        mActivity = activity;
    }

    public Activity getActivity() {
        return mActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getDialogLayout());
        ButterKnife.bind(this);
        setUpWidth();
        setUp();
    }

    private void closeDialog() {
        this.dismiss();
    }

    private void setUpWidth() {
        if (getWindow().getAttributes() != null) {
            ViewGroup.LayoutParams params = getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        }
    }

    protected abstract void setUp();

    protected abstract int getDialogLayout();

}
