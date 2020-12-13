package eu.vk.trackerapp.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import eu.vk.trackerapp.ListUpdateTracker;
import eu.vk.trackerapp.R;
import eu.vk.trackerapp.ui.model.Exercise;
import eu.vk.trackerapp.ui.model.Workout;
import eu.vk.trackerapp.ui.storage.DatabaseProvider;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

public class WorkoutCreationFragment extends DialogFragment {
    private Workout workout;
    private int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    private int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    private ArrayAdapter<Exercise> adapter;
    private List<Exercise> exercises = new LinkedList<>();

    public WorkoutCreationFragment(Workout workout) {
        this.workout = workout;
    }

    public WorkoutCreationFragment() {
    }

    @SuppressLint({"DefaultLocale", "ClickableViewAccessibility"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workout_creation, container, false);
        TextInputEditText acWorkoutName = root.findViewById(R.id.actv_workout_name);
        AutoCompleteTextView acDate = root.findViewById(R.id.actv_date);
        MaterialButton btSave = root.findViewById(R.id.bt_save);
        ListView lvExercises = root.findViewById(R.id.lv_exercises);
        MaterialButton mbAddExercise = root.findViewById(R.id.bt_add);

        btSave.setOnClickListener(v -> {
            if (nonNull(this.workout)) {
                workout.date = acDate.getText().toString();
                workout.name = acWorkoutName.getText().toString();
                workout.setExercises(exercises);
                DatabaseProvider.getInstance()
                        .workoutDao()
                        .update(workout);
            } else {
                workout = new Workout(
                        acDate.getText().toString(),
                        acWorkoutName.getText().toString(),
                        exercises
                );
                DatabaseProvider.getInstance()
                        .workoutDao()
                        .insertAll(workout);
            }
            dismiss();
            ListUpdateTracker.getInstance()
                    .getUpdateTracker()
                    .onNext(true);
        });

        acDate.setOnClickListener(view -> {
            DatePickerDialog picker = new DatePickerDialog(
                    requireActivity(),
                    (v, year, month, dayOfMonth) -> acDate.setText(format("%s-%02d-%02d", year, month + 1, dayOfMonth)),
                    currentYear,
                    currentMonth,
                    currentDay);
            picker.show();
        });

        if (nonNull(workout)) {
            acDate.setText(workout.date);
            acWorkoutName.setText(workout.name);
            exercises = workout.getExercises();
        }

        adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, exercises);
        lvExercises.setAdapter(adapter);
        lvExercises.setOnItemClickListener((parent, view, position, id) -> {
            exercises.remove(position);
            adapter.notifyDataSetChanged();
        });
        mbAddExercise.setOnClickListener(v -> {
            new ExerciseFragment(exercise -> {
                exercises.add(exercise);
                adapter.notifyDataSetChanged();
            }).show(getParentFragmentManager(), "AddExercise");
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
