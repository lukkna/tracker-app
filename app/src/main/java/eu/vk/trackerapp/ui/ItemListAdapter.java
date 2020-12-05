package eu.vk.trackerapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.List;

import eu.vk.trackerapp.R;
import eu.vk.trackerapp.ui.ItemFragment.OnListFragmentInteractionListener;
import eu.vk.trackerapp.ui.model.Item;

import static android.graphics.Typeface.DEFAULT_BOLD;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private final List<Item> mValues;
    private final OnListFragmentInteractionListener mListener;

    public ItemListAdapter(List<Item> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (position == 0) {
            holder.tvItemTime.setText("Laikas");
            holder.tvItemTime.setTypeface(DEFAULT_BOLD);
            holder.tvItemName.setText("Įvykis");
            holder.tvItemName.setTypeface(DEFAULT_BOLD);
            holder.tvPriority.setText("Prioritetas");
            holder.tvPriority.setTypeface(DEFAULT_BOLD);
        } else {
            holder.mItem = mValues.get(position - 1);
            holder.tvItemTime.setText(holder.mItem.dateTime.format(TIME_FORMATTER));
            holder.tvItemName.setText(holder.mItem.title.replace("+", ": "));
            holder.tvPriority.setText(holder.mItem.priority == 0 ? "" : String.valueOf(holder.mItem.priority));

            holder.mView.setOnClickListener(v -> {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView tvItemTime;
        final TextView tvItemName;
        final TextView tvPriority;
        Item mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            tvItemTime = view.findViewById(R.id.tv_item_time);
            tvItemName = view.findViewById(R.id.tv_name);
            tvPriority = view.findViewById(R.id.tv_priority);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvItemName.getText() + "'";
        }
    }
}
