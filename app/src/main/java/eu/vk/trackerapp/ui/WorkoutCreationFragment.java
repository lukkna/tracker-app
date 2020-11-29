package eu.vk.trackerapp.ui;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import static java.lang.String.format;
import static java.util.Objects.nonNull;

public class WorkoutCreationFragment extends DialogFragment {
    private static final String[] WORKOUT_TYPES = new String[]{"Krūtinė", "Rankos", "Kojos", "Nugara", "Kardio"};
    private Item item;
    private TimePickerDialog picker;
    private RadioButton rbRepeatEveryDay;
    private RadioButton rbRepeatEveryWeek;
    private MaterialButton btSave;

    public WorkoutCreationFragment(Item item) {
        this.item = item;
    }

    public WorkoutCreationFragment() {
    }

    @SuppressLint({"DefaultLocale", "ClickableViewAccessibility"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workout_creation, container, false);
        AutoCompleteTextView acWorkoutType = root.findViewById(R.id.actv_workout_type);
        AutoCompleteTextView acTime = root.findViewById(R.id.actv_time);
        rbRepeatEveryDay = root.findViewById(R.id.radio_button_1);
        rbRepeatEveryWeek = root.findViewById(R.id.radio_button_2);
        btSave = root.findViewById(R.id.bt_save);
        btSave.setOnClickListener(v -> {
            if (nonNull(this.item)) {
                item.date = LocalDate.now().toString();
                item.startTime = acTime.getText().toString();
                item.priority = 0;
                item.title = "Treniruotė+" + acWorkoutType.getText().toString();
                item.everyDay = rbRepeatEveryDay.isChecked();
                item.everyWeek = rbRepeatEveryWeek.isChecked();
                DatabaseProvider.getInstance()
                        .itemDao()
                        .update(item);
            } else {
                item = new Item(
                        LocalDate.now().toString(),
                        acTime.getText().toString(),
                        null,
                        0,
                        "Treniruotė+" + acWorkoutType.getText().toString(),
                        rbRepeatEveryDay.isChecked(),
                        rbRepeatEveryWeek.isChecked(),
                        false
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

        acTime.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            picker = new TimePickerDialog(requireActivity(),
                    (tp, sHour, sMinute) -> acTime.setText(format("%02d:%02d", sHour, sMinute)), hour, minutes, true);
            picker.show();
        });
        acWorkoutType.setAdapter(new ArrayAdapter<>(
                requireActivity(),
                R.layout.support_simple_spinner_dropdown_item,
                WORKOUT_TYPES
        ));
        if (nonNull(item)) {
            acTime.setText(item.startTime);
            acWorkoutType.setText(item.title.substring(item.title.lastIndexOf('+') + 1));
            if (item.everyDay)
                rbRepeatEveryDay.setChecked(true);
            else if (item.everyWeek)
                rbRepeatEveryWeek.setChecked(true);
        }
        return root;
    }
}
