package eu.vk.trackerapp.ui;

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
import eu.vk.trackerapp.ui.model.Workout;
import eu.vk.trackerapp.ui.storage.DatabaseProvider;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;

import static java.util.Objects.nonNull;

public class TrainingFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnWorkoutInteractionListener mListener;
    private @NonNull Disposable subscription;

    public TrainingFragment() {
    }

    @SuppressWarnings("unused")
    public static TrainingFragment newInstance(int columnCount) {
        TrainingFragment fragment = new TrainingFragment();
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
        View view = inflater.inflate(R.layout.fragment_training_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new TrainingViewAdapter(getWorkouts(), mListener));
            subscription = ListUpdateTracker.getInstance().getUpdateTracker()
                    .subscribe(bool -> recyclerView.setAdapter(new TrainingViewAdapter(getWorkouts(), mListener)));
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnWorkoutInteractionListener) {
            mListener = (OnWorkoutInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnItemInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        if (nonNull(subscription))
            subscription.dispose();
    }

    private List<Workout> getWorkouts() {
        return DatabaseProvider.getInstance()
                .workoutDao()
                .queryAll();
    }

    public interface OnWorkoutInteractionListener {
        void apply(Workout item);
    }
}
