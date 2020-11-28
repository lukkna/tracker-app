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

import java.time.LocalDate;
import java.util.Calendar;

import eu.vk.trackerapp.MainActivity;
import eu.vk.trackerapp.R;
import eu.vk.trackerapp.ui.model.Item;
import eu.vk.trackerapp.ui.storage.DatabaseProvider;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

public class CreationFragment extends DialogFragment {
    private static final String[] MEAL_TYPES = new String[]{"Paprastas", "Prieš sportą", "Po sporto"};
    private Item item;
    private TimePickerDialog picker;
    private RadioButton rbRepeatEveryDay;
    private RadioButton rbRepeatEveryWeek;
    private RadioGroup rgRepeat;
    private MaterialButton btSave;

    public CreationFragment(Item item) {
        this.item = item;
    }

    public CreationFragment() {
    }

    @SuppressLint({"DefaultLocale", "ClickableViewAccessibility"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_creation, container, false);
        AutoCompleteTextView acMealType = root.findViewById(R.id.actv_meal_type);
        AutoCompleteTextView acTime = root.findViewById(R.id.actv_time);
        rbRepeatEveryDay = root.findViewById(R.id.radio_button_1);
        rbRepeatEveryWeek = root.findViewById(R.id.radio_button_2);
        rgRepeat = root.findViewById(R.id.rg_repeat);
        btSave = root.findViewById(R.id.bt_save);
        btSave.setOnClickListener(v -> {
            if (nonNull(this.item)) {
                item.date = LocalDate.now().toString();
                item.time = acTime.getText().toString();
                item.priority = 0;
                item.title = "Valgis+" + acMealType.getText().toString();
                item.everyDay = rbRepeatEveryDay.isChecked();
                item.everyWeek = rbRepeatEveryWeek.isChecked();
                DatabaseProvider.getInstance()
                        .itemDao()
                        .update(item);
            } else {
                item = new Item(
                        LocalDate.now().toString(),
                        acTime.getText().toString(),
                        0,
                        "Valgis+" + acMealType.getText().toString(),
                        rbRepeatEveryDay.isChecked(),
                        rbRepeatEveryWeek.isChecked()
                );
                DatabaseProvider.getInstance()
                        .itemDao()
                        .insertAll(item);
            }
            dismiss();
        });

        acTime.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            picker = new TimePickerDialog(requireActivity(),
                    (tp, sHour, sMinute) -> acTime.setText(format("%02d:%02d", sHour, sMinute)), hour, minutes, true);
            picker.show();
        });
        acMealType.setAdapter(new ArrayAdapter<>(
                requireActivity(),
                R.layout.support_simple_spinner_dropdown_item,
                MEAL_TYPES
        ));
        return root;
    }
}
