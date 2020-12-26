package eu.vk.trackerapp.ui.help;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HelpViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public HelpViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Progresas\n\n" +
                "Suvesk dabartines savo apimtis, kad po to galėtum stebėti kaip jos keičiasi. \n\n" +
                "  • Apimtis matuok ryte nieko nevalgius.\n" +
                "  • Raumenys turi būti įtempti.\n" +
                "  • Apimtys matuojamos kas savaitę.\n");
    }

    public LiveData<String> getText() {
        return mText;
    }
}