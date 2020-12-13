package eu.vk.trackerapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import eu.vk.trackerapp.R;
import eu.vk.trackerapp.ui.model.Exercise;
import eu.vk.trackerapp.ui.model.Repetition;

import static java.util.Arrays.asList;

public class ExerciseFragment extends DialogFragment {
    private final ExerciseCallback callback;

    public ExerciseFragment(ExerciseCallback callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_exercise, container, false);
        TextInputEditText tvName = root.findViewById(R.id.actv_exercise_name);
        TextInputEditText tvWeight = root.findViewById(R.id.actv_exercise_weight);
        TextInputEditText tvReps = root.findViewById(R.id.actv_exercise_reps);
        MaterialButton btSave = root.findViewById(R.id.bt_save);

        btSave.setOnClickListener(v -> {
            callback.apply(new Exercise(
                    tvName.getText().toString(),
                    asList(new Repetition(
                                    Double.parseDouble(tvWeight.getText().toString()),
                                    Integer.parseInt(tvReps.getText().toString())
                            )
                    )
            ));
            dismiss();
        });
        return root;
    }

    public interface ExerciseCallback {
        void apply(Exercise exercise);
    }
}
