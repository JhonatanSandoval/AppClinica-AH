package net.touchsf.appclinica.ui.adapter;

import android.widget.Spinner;

public class GeneralSpinnerAdapter extends NothingSelectedSpinnerAdapter<String> {

    private String[] items;

    public GeneralSpinnerAdapter(Spinner spinner) {
        super(spinner);
    }

    public void update(String[] items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    protected String getNothingSelectedText(String nothingSelectedDataItem) {
        return "";
    }

    @Override
    protected String getNothingSelectedText() {
        return "";
    }

    @Override
    protected String getDataItemText(int position) {
        return items[position];
    }

    @Override
    protected int getDataItemCount() {
        return items != null ? items.length : 0;
    }

    @Override
    protected String getDataItem(int position) {
        return items[position];
    }
}
