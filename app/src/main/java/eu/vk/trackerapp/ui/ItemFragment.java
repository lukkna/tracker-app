package eu.vk.trackerapp.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import eu.vk.trackerapp.ListUpdateTracker;
import eu.vk.trackerapp.R;
import eu.vk.trackerapp.ui.model.Item;
import eu.vk.trackerapp.ui.storage.DatabaseProvider;
import io.reactivex.rxjava3.disposables.Disposable;

import static eu.vk.trackerapp.ui.CurrentDateHolder.CURRENT_DATE;
import static eu.vk.trackerapp.ui.CurrentDateHolder.CURRENT_DATE_STRING;
import static java.lang.String.format;
import static java.util.Objects.nonNull;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnItemInteractionListener}
 * interface.
 */
public class ItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnItemInteractionListener mListener;
    private RecyclerView view;
    private Disposable subscription;
    private DatePickerDialog picker;
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
    int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
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

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_item_list, container, false);
        view = root.findViewById(R.id.list);
        AutoCompleteTextView acDate = root.findViewById(R.id.actv_date);
        LocalDate now = LocalDate.now();
        acDate.setText(now.toString());
        CURRENT_DATE_STRING = format("%s-%02d-%02d", now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        CURRENT_DATE = LocalDate.parse(CURRENT_DATE_STRING);
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            view.setLayoutManager(new LinearLayoutManager(context));
        } else {
            view.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        view.setAdapter(new ItemListAdapter(getItems(), mListener));
        subscription = ListUpdateTracker.getInstance().getUpdateTracker()
                .subscribe(bool -> view.setAdapter(new ItemListAdapter(getItems(), mListener)));

        picker = new DatePickerDialog(
                requireActivity(),
                (v, year, month, dayOfMonth) -> {
                    CURRENT_DATE_STRING = format("%s-%02d-%02d", year, month + 1, dayOfMonth);
                    CURRENT_DATE = LocalDate.parse(CURRENT_DATE_STRING);
                    acDate.setText(CURRENT_DATE_STRING);
                    currentYear = year;
                    currentMonth = month;
                    currentDay = dayOfMonth;
                    this.view.setAdapter(new ItemListAdapter(getItems(), mListener));
                },
                currentYear,
                currentMonth,
                currentDay
        );

        acDate.setOnClickListener(view -> picker.show());
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @SuppressLint("DefaultLocale")
    private List<Item> getItems() {
        return DatabaseProvider.getInstance()
                .itemDao()
                .queryByDate(CURRENT_DATE_STRING, getTodayPeriodValue());
    }

    private int getTodayPeriodValue() {
        if (CURRENT_DATE.getDayOfWeek().getValue() == 6 || CURRENT_DATE.getDayOfWeek().getValue() == 7)
            return 2;
        return 1;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemInteractionListener) {
            mListener = (OnItemInteractionListener) context;
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

    public interface OnItemInteractionListener {
        void apply(Item item);
    }
}
