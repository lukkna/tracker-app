package eu.vk.trackerapp.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import eu.vk.trackerapp.R;
import eu.vk.trackerapp.ui.MeasuresFragment.OnMeasuresInteractionListener;
import eu.vk.trackerapp.ui.model.Measures;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

public class MeasuresListAdapter extends Adapter<MeasuresListAdapter.ViewHolder> {
    private final List<Measures> measures;
    private final OnMeasuresInteractionListener listener;
    private final Context context;

    public MeasuresListAdapter(List<Measures> items,
                               OnMeasuresInteractionListener listener,
                               Context context) {
        measures = items;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_measures, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (position == 0) {
            holder.btSave.setVisibility(View.VISIBLE);
            holder.btSave.setOnClickListener(v -> listener.apply(
                    new Measures(
                            holder.acDate.getText().toString(),
                            parseViewValue(holder.tvWeight),
                            parseViewValue(holder.tvWaistNarrow),
                            parseViewValue(holder.tvWaistWide),
                            parseViewValue(holder.tvButt),
                            parseViewValue(holder.tvThigh),
                            parseViewValue(holder.tvShin),
                            parseViewValue(holder.tvChest),
                            parseViewValue(holder.tvBicep),
                            parseViewValue(holder.tvWrist)
                    )
            ));
            holder.tvWeight.setEnabled(true);
            holder.tvWaistNarrow.setEnabled(true);
            holder.tvWaistWide.setEnabled(true);
            holder.tvButt.setEnabled(true);
            holder.tvThigh.setEnabled(true);
            holder.tvShin.setEnabled(true);
            holder.tvChest.setEnabled(true);
            holder.tvBicep.setEnabled(true);
            holder.tvWrist.setEnabled(true);
            LocalDate today = LocalDate.now();
            holder.acDate.setText(today.toString());
            DatePickerDialog pickerDialog = new DatePickerDialog(
                    context,
                    (v, year, month, dayOfMonth) -> holder.acDate.setText(format("%s-%02d-%02d", year, month + 1, dayOfMonth)),
                    today.getYear(),
                    today.getMonthValue() - 1,
                    today.getDayOfMonth()
            );
            holder.acDate.setEnabled(true);
            holder.acDate.setOnClickListener(view -> pickerDialog.show());


        } else {
            holder.measures = measures.get(position - 1);
            setValueOrGone(measures.get(position - 1).weight, holder.tvWeight, holder.tiWeight);
            setValueOrGone(measures.get(position - 1).waistNarrowest, holder.tvWaistNarrow, holder.tiWaistNarrow);
            setValueOrGone(measures.get(position - 1).waistWidest, holder.tvWaistWide, holder.tiWaistWide);
            setValueOrGone(measures.get(position - 1).butt, holder.tvButt, holder.tiButt);
            setValueOrGone(measures.get(position - 1).thigh, holder.tvThigh, holder.tiThigh);
            setValueOrGone(measures.get(position - 1).shin, holder.tvShin, holder.tiShin);
            setValueOrGone(measures.get(position - 1).chest, holder.tvChest, holder.tiChest);
            setValueOrGone(measures.get(position - 1).bicep, holder.tvBicep, holder.tiBicep);
            setValueOrGone(measures.get(position - 1).wrist, holder.tvWrist, holder.tiWrist);
            holder.acDate.setText(measures.get(position - 1).date);

            holder.mView.setOnClickListener(v -> {
                if (null != listener) {
                    listener.apply(holder.measures);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return measures.size() + 1;
    }

    private void setValueOrGone(Double input, TextInputEditText view, TextInputLayout container) {
        String value = Optional.ofNullable(input)
                .map(String::valueOf)
                .orElse(null);
        if (nonNull(value))
            view.setText(value);
        else container.setVisibility(View.GONE);
    }

    private Double parseViewValue(TextInputEditText view) {
        return Optional.ofNullable(view.getText())
                .map(CharSequence::toString)
                .map(String::trim)
                .filter(text -> !text.isEmpty())
                .map(Double::valueOf)
                .orElse(null);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextInputEditText tvWeight;
        final TextInputEditText tvWaistNarrow;
        final TextInputEditText tvWaistWide;
        final TextInputEditText tvButt;
        final TextInputEditText tvThigh;
        final TextInputEditText tvShin;
        final TextInputEditText tvChest;
        final TextInputEditText tvBicep;
        final TextInputEditText tvWrist;
        final TextInputLayout tiWeight;
        final TextInputLayout tiWaistNarrow;
        final TextInputLayout tiWaistWide;
        final TextInputLayout tiButt;
        final TextInputLayout tiThigh;
        final TextInputLayout tiShin;
        final TextInputLayout tiChest;
        final TextInputLayout tiBicep;
        final TextInputLayout tiWrist;
        final MaterialButton btSave;
        final AutoCompleteTextView acDate;
        Measures measures;

        ViewHolder(View view) {
            super(view);
            mView = view;
            tvWeight = view.findViewById(R.id.et_weight);
            tvWaistNarrow = view.findViewById(R.id.et_waist_narrow);
            tvWaistWide = view.findViewById(R.id.et_waist_wide);
            tvButt = view.findViewById(R.id.et_butt);
            tvThigh = view.findViewById(R.id.et_thigh);
            tvShin = view.findViewById(R.id.et_shin);
            tvChest = view.findViewById(R.id.et_chest);
            tvBicep = view.findViewById(R.id.et_bicep);
            tvWrist = view.findViewById(R.id.et_wrist);
            btSave = view.findViewById(R.id.bt_add_measures);
            acDate = view.findViewById(R.id.actv_date);
            tiWeight = view.findViewById(R.id.ti_weight);
            tiWaistNarrow = view.findViewById(R.id.ti_waist_narrow);
            tiWaistWide = view.findViewById(R.id.ti_waist_wide);
            tiButt = view.findViewById(R.id.ti_butt);
            tiThigh = view.findViewById(R.id.ti_thigh);
            tiShin = view.findViewById(R.id.ti_shin);
            tiChest = view.findViewById(R.id.ti_chest);
            tiBicep = view.findViewById(R.id.ti_bicep);
            tiWrist = view.findViewById(R.id.ti_wrist);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvWaistNarrow.getText() + "'";
        }
    }
}
