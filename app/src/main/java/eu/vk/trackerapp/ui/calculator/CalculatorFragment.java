package eu.vk.trackerapp.ui.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        TextView tvLose = root.findViewById(R.id.tv_lose);
        TextView tvSustain = root.findViewById(R.id.tv_sustain);
        TextView tvGain = root.findViewById(R.id.tv_gain);

        User user = DatabaseProvider.getInstance()
                .userDao()
                .retrieveUser();

        if (nonNull(user)) {
            tvLose.setText(String.format("Numesti svorio: %s kcal", countCalories(user.weight - 0.5, user.height, user.age)));
            tvSustain.setText(String.format("Palaikyti svorį: %s kcal", countCalories(user.weight, user.height, user.age)));
            tvGain.setText(String.format("Priaugti svorio: %s kcal", countCalories(user.weight + 0.5, user.height, user.age)));
        }

        return root;
    }

    private long countCalories(double weight, int height, int age) {
        return Math.round(((10 * weight) + (6.25 * height) - (5 * age)) * 1.25);
    }
}