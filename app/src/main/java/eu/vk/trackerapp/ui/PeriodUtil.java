package eu.vk.trackerapp.ui;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.HashMap;
import java.util.Map;

import eu.vk.trackerapp.R;

public class PeriodUtil {
    private static final Map<String, Integer> PERIOD_TYPES = new HashMap<>();

    static {
        PERIOD_TYPES.put("Nekartoti", 0);
        PERIOD_TYPES.put("Darbo dienomis", 1);
        PERIOD_TYPES.put("Savaitgaliais", 2);
    }

    public static int getPeriodIntValue(AutoCompleteTextView view) {
        return PERIOD_TYPES.getOrDefault(view.getText().toString(), 0);
    }

    public static String getPeriodStringValue(int period) {
        return PERIOD_TYPES.entrySet().stream()
                .filter(entry -> entry.getValue() == period)
                .map(Map.Entry::getKey)
                .findAny()
                .orElse("Nekartoti");
    }

    public static void initAdapter(Activity activity, AutoCompleteTextView acPeriod) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                activity,
                R.layout.support_simple_spinner_dropdown_item,
                PERIOD_TYPES.keySet().toArray(new String[]{})
        );
        acPeriod.setAdapter(adapter);
        acPeriod.setText("Nekartoti", false);
    }
}
