package cat.uab.idt.rightsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

public class TermsAndConditions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_terms_and_conditions);




    }
}
