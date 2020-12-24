package eu.vk.trackerapp.ui.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import eu.vk.trackerapp.R;
import eu.vk.trackerapp.ui.model.User;
import eu.vk.trackerapp.ui.storage.DatabaseProvider;

import static java.util.Objects.nonNull;

public class CalculatorFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calculator, container, false);
        TextView tvLose = root.findViewById(R.id.tv_lose_value);
        LinearLayout llLose = root.findViewById(R.id.ll_lose);
        TextView tvSustain = root.findViewById(R.id.tv_sustain_value);
        LinearLayout llSustain = root.findViewById(R.id.ll_sustain);
        TextView tvGain = root.findViewById(R.id.tv_gain_value);
        LinearLayout llGain = root.findViewById(R.id.ll_gain);
        TextView tvEmpty = root.findViewById(R.id.tv_empty);

        User user = DatabaseProvider.getInstance()
                .userDao()
                .retrieveUser();

        if (nonNull(user)) {
            tvLose.setText(String.valueOf(countCalories(user.weight, user.height, user.age) - 500));
            tvSustain.setText(String.valueOf(countCalories(user.weight, user.height, user.age)));
            tvGain.setText(String.valueOf(countCalories(user.weight, user.height, user.age) + 500));
            tvEmpty.setVisibility(View.GONE);
            llGain.setVisibility(View.VISIBLE);
            llSustain.setVisibility(View.VISIBLE);
            llLose.setVisibility(View.VISIBLE);
        } else {
            llLose.setVisibility(View.GONE);
            llSustain.setVisibility(View.GONE);
            llGain.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        }
        return root;
    }

    private long countCalories(double weight, int height, int age) {
        return Math.round(((10 * weight) + (6.25 * height) - (5 * age)) * 1.25);
    }
}