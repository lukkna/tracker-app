package eu.vk.trackerapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import eu.vk.trackerapp.ListUpdateTracker;
import eu.vk.trackerapp.R;
import eu.vk.trackerapp.ui.model.Measures;
import eu.vk.trackerapp.ui.storage.DatabaseProvider;
import io.reactivex.rxjava3.disposables.Disposable;

import static java.util.Objects.nonNull;

public class MeasuresFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnMeasuresInteractionListener mListener;
    private Disposable subscription;

    public MeasuresFragment() {
    }

    @SuppressWarnings("unused")
    public static MeasuresFragment newInstance(int columnCount) {
        MeasuresFragment fragment = new MeasuresFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_measures_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MeasuresListAdapter(getMeasures(), mListener, requireContext()));

            subscription = ListUpdateTracker.getInstance().getUpdateTracker()
                    .subscribe(bool -> recyclerView.setAdapter(new MeasuresListAdapter(getMeasures(), mListener, requireContext())));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMeasuresInteractionListener) {
            mListener = (OnMeasuresInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMeasuresInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        if (nonNull(subscription))
            subscription.dispose();
    }

    @SuppressLint("DefaultLocale")
    private List<Measures> getMeasures() {
        return DatabaseProvider.getInstance()
                .measuresDao()
                .queryAll();
    }

    public interface OnMeasuresInteractionListener {
        void apply(Measures measures);
    }
}
