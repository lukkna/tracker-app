package eu.vk.trackerapp.ui;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;

import java.time.LocalDate;
import java.util.Calendar;

import eu.vk.trackerapp.ListUpdateTracker;
import eu.vk.trackerapp.R;
import eu.vk.trackerapp.ui.model.Item;
import eu.vk.trackerapp.ui.storage.DatabaseProvider;

import static eu.vk.trackerapp.ui.CurrentDateHolder.CURRENT_DATE;
import static java.lang.String.format;
import static java.util.Objects.nonNull;

public class SleepCreationFragment extends DialogFragment {
    private Item item;
    private TimePickerDialog picker;
    private RadioButton rbCompleted;
    private MaterialButton btSave;

    public SleepCreationFragment(Item item) {
        this.item = item;
    }

    public SleepCreationFragment() {
    }

    @SuppressLint({"DefaultLocale", "ClickableViewAccessibility"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sleep_creation, container, false);
        AutoCompleteTextView acTimeStart = root.findViewById(R.id.actv_time_start);
        AutoCompleteTextView acTimeEnd = root.findViewById(R.id.actv_time_end);

        rbCompleted = root.findViewById(R.id.radio_button_1);

        btSave = root.findViewById(R.id.bt_save);
        btSave.setOnClickListener(v -> {
            if (nonNull(this.item)) {
                item.date = CURRENT_DATE;
                item.startTime = acTimeStart.getText().toString();
                item.priority = 0;
                item.title = "Miegas";
                item.everyDay = true;
                item.everyWeek = false;
                item.completed = rbCompleted.isChecked();
                DatabaseProvider.getInstance()
                        .itemDao()
                        .update(item);
            } else {
                item = new Item(
                        CURRENT_DATE,
                        acTimeStart.getText().toString(),
                        acTimeEnd.getText().toString(),
                        0,
                        "Miegas",
                        true,
                        false,
                        rbCompleted.isChecked()
                );
                DatabaseProvider.getInstance()
                        .itemDao()
                        .insertAll(item);
            }
            dismiss();
            ListUpdateTracker.getInstance()
                    .getUpdateTracker()
                    .onNext(true);
        });

        acTimeStart.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            picker = new TimePickerDialog(requireActivity(),
                    (tp, sHour, sMinute) -> acTimeStart.setText(format("%02d:%02d", sHour, sMinute)), hour, minutes, true);
            picker.show();
        });

        acTimeEnd.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            picker = new TimePickerDialog(requireActivity(),
                    (tp, sHour, sMinute) -> acTimeStart.setText(format("%02d:%02d", sHour, sMinute)), hour, minutes, true);
            picker.show();
        });

        if (nonNull(item)) {
            acTimeStart.setText(item.startTime);
            acTimeEnd.setText(item.endTime);
            if (item.completed)
                rbCompleted.setChecked(true);
        }

        return root;
    }
}
