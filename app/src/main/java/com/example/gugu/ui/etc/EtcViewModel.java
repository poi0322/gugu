package com.example.gugu.ui.etc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EtcViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EtcViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is etc fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}