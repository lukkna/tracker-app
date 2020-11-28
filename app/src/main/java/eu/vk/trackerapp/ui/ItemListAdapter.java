package eu.vk.trackerapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.List;

import eu.vk.trackerapp.R;
import eu.vk.trackerapp.ui.ItemFragment.OnListFragmentInteractionListener;
import eu.vk.trackerapp.ui.model.Item;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm");
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
        holder.mItem = mValues.get(position);
        holder.tvItemTime.setText(mValues.get(position).dateTime.format(TIME_FORMATTER));
        holder.tvItemName.setText(mValues.get(position).title.replace("+", ": "));

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onListFragmentInteraction(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvItemTime;
        public final TextView tvItemName;
        public Item mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvItemTime = (TextView) view.findViewById(R.id.tv_item_time);
            tvItemName = (TextView) view.findViewById(R.id.tv_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvItemName.getText() + "'";
        }
    }
}
