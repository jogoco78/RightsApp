package cat.uab.idt.rightsapp.models;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData message = new MutableLiveData();

    public void setMessage(int _message){
        message.setValue(_message);
    }

    public MutableLiveData getMessage(){
        return message;
    }
}
