package eu.vk.trackerapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import eu.vk.trackerapp.R;
import eu.vk.trackerapp.ui.model.User;
import eu.vk.trackerapp.ui.storage.DatabaseProvider;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class UserFragment extends DialogFragment {
    private TextInputEditText etName;
    private TextInputEditText etAge;
    private TextInputEditText etWeight;
    private MaterialButton btCreate;
    private RadioButton rbMale;
    private RadioButton rbFemale;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        etName = root.findViewById(R.id.et_name);
        etAge = root.findViewById(R.id.et_age);
        etWeight = root.findViewById(R.id.et_weight);
        btCreate = root.findViewById(R.id.bt_create);
        rbMale = root.findViewById(R.id.radio_button_1);
        rbFemale = root.findViewById(R.id.radio_button_2);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        User user = DatabaseProvider.getInstance()
                .userDao()
                .retrieveUser();

        btCreate.setOnClickListener(v -> {
                    if (isNull(etName.getText()) || isNull(etAge.getText()) || isNull(etWeight.getText()))
                        Snackbar.make(requireView(), "Būtina užpildyti visus laukus!", BaseTransientBottomBar.LENGTH_LONG);
                    else if (isNull(user)) {
                        User newUser = new User(etName.getText().toString(),
                                Integer.valueOf(etAge.getText().toString()),
                                Double.valueOf(etWeight.getText().toString()),
                                rbMale.isChecked());
                        DatabaseProvider.getInstance()
                                .userDao()
                                .insertAll(newUser);
                        dismiss();
                    } else {
                        User updatedUser = new User(
                                user.uid,
                                etName.getText().toString(),
                                Integer.valueOf(etAge.getText().toString()),
                                Double.valueOf(etWeight.getText().toString()),
                                rbMale.isChecked());
                        DatabaseProvider.getInstance()
                                .userDao()
                                .update(updatedUser);
                        dismiss();
                    }
                }
        );

        if (nonNull(user)) {
            etName.setText(user.name);
            etAge.setText(String.valueOf(user.age));
            etWeight.setText(String.valueOf(user.weight));

            if (user.male)
                rbMale.setChecked(true);
            else
                rbFemale.setChecked(true);
        }

        super.onViewCreated(view, savedInstanceState);
    }
}
