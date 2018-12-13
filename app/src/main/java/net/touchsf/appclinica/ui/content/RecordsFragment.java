package net.touchsf.appclinica.ui.content;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.touchsf.appclinica.R;
import net.touchsf.appclinica.database.Database;
import net.touchsf.appclinica.database.entity.History;
import net.touchsf.appclinica.preference.AppPrefs;
import net.touchsf.appclinica.ui.adapter.HistoriesAdapter;
import net.touchsf.appclinica.ui.base.BaseFragment;
import net.touchsf.appclinica.ui.content.histories.HistoryDetailActivity;

import java.util.List;

import butterknife.BindView;

public class RecordsFragment extends BaseFragment implements HistoriesAdapter.HistoryClickListener {

    public static RecordsFragment newInstance() {
        return new RecordsFragment();
    }

    @BindView(R.id.rvHistories) RecyclerView rvHistories;
    @BindView(R.id.tvNoHistories) TextView tvNoHistories;

    private HistoriesAdapter adapter;
    private Context context;
    private AppPrefs appPrefs;

    @Override
    protected void setUp() {
        context = getActivity();
        appPrefs = new AppPrefs(context);
        configureRecyclerview();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadHistories();
        }
    }

    private void loadHistories() {
        if (!getHistoriesFromDb().isEmpty()) {
            rvHistories.setVisibility(View.VISIBLE);
            tvNoHistories.setVisibility(View.GONE);

            adapter.setHistories(getHistoriesFromDb());
        } else {
            rvHistories.setVisibility(View.GONE);
            tvNoHistories.setVisibility(View.VISIBLE);

        }
    }

    private List<History> getHistoriesFromDb() {
        return Database.getDatabase(context).historyDao().getFromuser(appPrefs.getUsetId());
    }

    @Override
    public void objClick(History history) {
        getActivity().startActivity(new Intent(getActivity(), HistoryDetailActivity.class));
    }

    private void configureRecyclerview() {
        rvHistories.setLayoutManager(new LinearLayoutManager(context));

        adapter = new HistoriesAdapter();
        adapter.setClickListener(this);
        rvHistories.setAdapter(adapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_records;
    }
}
