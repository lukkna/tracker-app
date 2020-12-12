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
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;

import java.util.Calendar;

import eu.vk.trackerapp.ListUpdateTracker;
import eu.vk.trackerapp.R;
import eu.vk.trackerapp.ui.model.Item;
import eu.vk.trackerapp.ui.storage.DatabaseProvider;

import static eu.vk.trackerapp.ui.CurrentDateHolder.CURRENT_DATE;
import static eu.vk.trackerapp.ui.CurrentDateHolder.CURRENT_DATE_STRING;
import static java.lang.String.format;
import static java.util.Objects.nonNull;

public class MealCreationFragment extends DialogFragment {
    private static final String[] MEAL_TYPES = new String[]{"Paprastas", "Prieš sportą", "Po sporto"};
    private Item item;
    private RadioButton rbRepeatEveryDay;
    private RadioButton rbRepeatEveryWeek;
    private RadioGroup rgRepeat;
    private MaterialButton btSave;

    public MealCreationFragment(Item item) {
        this.item = item;
    }

    public MealCreationFragment() {
    }

    @SuppressLint({"DefaultLocale", "ClickableViewAccessibility"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_meal_creation, container, false);
        AutoCompleteTextView acMealType = root.findViewById(R.id.actv_meal_type);
        AutoCompleteTextView acTimeFrom = root.findViewById(R.id.actv_time_from);
        AutoCompleteTextView acTimeTo = root.findViewById(R.id.actv_time_to);
        rbRepeatEveryDay = root.findViewById(R.id.radio_button_1);
        rbRepeatEveryWeek = root.findViewById(R.id.radio_button_2);
        rgRepeat = root.findViewById(R.id.rg_repeat);
        btSave = root.findViewById(R.id.bt_save);

        btSave.setOnClickListener(v -> {
            if (nonNull(this.item)) {
                item.date = CURRENT_DATE_STRING;
                item.startTime = acTimeFrom.getText().toString();
                item.endTime = acTimeTo.getText().toString();
                item.priority = 0;
                item.title = "Valgis+" + acMealType.getText().toString();
                item.everyDay = rbRepeatEveryDay.isChecked();
                item.everyWeek = rbRepeatEveryWeek.isChecked();
                item.weekDay = CURRENT_DATE.getDayOfWeek().getValue();
                DatabaseProvider.getInstance()
                        .itemDao()
                        .update(item);
            } else {
                item = new Item(
                        CURRENT_DATE_STRING,
                        acTimeFrom.getText().toString(),
                        acTimeTo.getText().toString(),
                        0,
                        "Valgis+" + acMealType.getText().toString(),
                        rbRepeatEveryDay.isChecked(),
                        rbRepeatEveryWeek.isChecked(),
                        CURRENT_DATE.getDayOfWeek().getValue(),
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

        acTimeFrom.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            TimePickerDialog picker = new TimePickerDialog(requireActivity(),
                    (tp, sHour, sMinute) -> acTimeFrom.setText(format("%02d:%02d", sHour, sMinute)), hour, minutes, true);
            picker.show();
        });
        acTimeTo.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            TimePickerDialog picker = new TimePickerDialog(requireActivity(),
                    (tp, sHour, sMinute) -> acTimeTo.setText(format("%02d:%02d", sHour, sMinute)), hour, minutes, true);
            picker.show();
        });
        acMealType.setAdapter(new ArrayAdapter<>(
                requireActivity(),
                R.layout.support_simple_spinner_dropdown_item,
                MEAL_TYPES
        ));

        if (nonNull(item)) {
            acTimeFrom.setText(item.startTime);
            acTimeTo.setText(item.endTime);
            acMealType.setText(item.title.substring(item.title.lastIndexOf('+') + 1), false);
            if (item.everyDay)
                rbRepeatEveryDay.setChecked(true);
            else if (item.everyWeek)
                rbRepeatEveryWeek.setChecked(true);
        }

        return root;
    }
}
