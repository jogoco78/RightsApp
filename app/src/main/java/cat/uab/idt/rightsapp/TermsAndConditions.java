package cat.uab.idt.rightsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class TermsAndConditions extends AppCompatActivity {

    private boolean showExplanation;
    private CheckBox checkBox;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_terms_and_conditions);

        // Gets preferences file
        final Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        showExplanation = sharedPreferences.getBoolean(Constants.SHOW_EXPLANATION, true);

        boolean agreed = sharedPreferences.getBoolean(Constants.AGREED, false);
        checkBox = findViewById(R.id.cb_terms_and_conditions);
        checkBox.setChecked(agreed);

        Button btn_continue = findViewById(R.id.btn_accept_terms_and_conditions);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    // Updates the agreed field in the preferences file
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(Constants.AGREED, true);
                    editor.apply();

                    if(showExplanation) {
                        Intent intent = new Intent(getApplicationContext(), ExplanationActivity.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(getApplicationContext(), RightsAppActivity.class);
                        startActivity(intent);
                    }
                }else{
                    // Updates the agreed field in the preferences file
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(Constants.AGREED, false);
                    editor.apply();

                    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(TermsAndConditions.this, R.style.myDialog));
                    builder.setMessage(R.string.must_accept_terms_and_conditions);

                    // Add the buttons
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button

                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            finish();
                        }
                    });

                    // Create the AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }
}
