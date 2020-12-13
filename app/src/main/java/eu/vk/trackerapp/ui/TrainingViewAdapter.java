package eu.vk.trackerapp.ui;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import eu.vk.trackerapp.R;
import eu.vk.trackerapp.ui.TrainingFragment.OnWorkoutInteractionListener;
import eu.vk.trackerapp.ui.model.Workout;

public class TrainingViewAdapter extends RecyclerView.Adapter<TrainingViewAdapter.ViewHolder> {
    private final List<Workout> mValues;
    private final OnWorkoutInteractionListener mListener;

    public TrainingViewAdapter(List<Workout> items, OnWorkoutInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_training, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (position == 0) {
            holder.tvDate.setText("Data");
            holder.tvDate.setTypeface(Typeface.DEFAULT_BOLD);
            holder.tvDate.setPadding(0, 0, 140, 0);
            holder.tvWorkoutName.setText("Pavadinimas");
            holder.tvWorkoutName.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            holder.workout = mValues.get(position - 1);
            holder.tvDate.setText(mValues.get(position - 1).date);
            holder.tvWorkoutName.setText(mValues.get(position - 1).name);

            holder.mView.setOnClickListener(v -> {
                if (null != mListener)
                    mListener.apply(holder.workout);
            });
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvDate;
        public final TextView tvWorkoutName;
        public Workout workout;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvDate = view.findViewById(R.id.tv_date);
            tvWorkoutName = view.findViewById(R.id.tv_workout_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvWorkoutName.getText() + "'";
        }
    }
}
