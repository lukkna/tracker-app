package eu.vk.trackerapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.List;

import eu.vk.trackerapp.R;
import eu.vk.trackerapp.ui.model.Exercise;
import eu.vk.trackerapp.ui.model.Item;
import eu.vk.trackerapp.ui.model.Workout;

import static android.graphics.Typeface.DEFAULT_BOLD;

//public class ExerciseListAdapter extends ArrayAdapter<Exercise> {
//    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
//    private final List<Item> mValues;
//    private final ItemFragment.OnItemInteractionListener mListener;
//
//    public ExerciseListAdapter(Context context, @LayoutRes int resource, List<Workout> items, ItemFragment.OnItemInteractionListener listener) {
//        super(context, resource);
//        mValues = items;
//        mListener = listener;
//    }
//
//
//    @Override
//    public ItemListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.fragment_item, parent, false);
//        return new ItemListAdapter.ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(final ItemListAdapter.ViewHolder holder, int position) {
//        if (position == 0) {
//            holder.tvItemTimeFrom.setVisibility(View.GONE);
//            holder.tvItemTimeTo.setVisibility(View.GONE);
//            holder.tvDash.setVisibility(View.GONE);
//            holder.tvItemName.setText("Įrašas");
//            holder.tvItemName.setTypeface(DEFAULT_BOLD);
//            holder.tvPriority.setText("Prioritetas");
//            holder.tvPriority.setTypeface(DEFAULT_BOLD);
//        } else {
//            holder.mItem = mValues.get(position - 1);
//            holder.tvItemTimeFrom.setText(holder.mItem.startDateTime.format(TIME_FORMATTER));
//            holder.tvItemTimeTo.setText(holder.mItem.endDateTime.format(TIME_FORMATTER));
//            holder.tvItemName.setText(holder.mItem.title.replace("+", ": "));
//            holder.tvPriority.setText(holder.mItem.priority == 0 ? "" : String.valueOf(holder.mItem.priority));
//
//            holder.mView.setOnClickListener(v -> {
//                if (null != mListener) {
//                    mListener.apply(holder.mItem);
//                }
//            });
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return mValues.size() + 1;
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        final View mView;
//        final TextView tvItemTimeFrom;
//        final TextView tvItemTimeTo;
//        final TextView tvItemName;
//        final TextView tvPriority;
//        final TextView tvDash;
//        Item mItem;
//
//        ViewHolder(View view) {
//            super(view);
//            mView = view;
//            tvItemTimeFrom = view.findViewById(R.id.tv_item_time_from);
//            tvItemTimeTo = view.findViewById(R.id.tv_item_time_to);
//            tvItemName = view.findViewById(R.id.tv_name);
//            tvPriority = view.findViewById(R.id.tv_priority);
//            tvDash = view.findViewById(R.id.tv_dash);
//        }
//
//        @Override
//        public String toString() {
//            return super.toString() + " '" + tvItemName.getText() + "'";
//        }
//    }
//}
