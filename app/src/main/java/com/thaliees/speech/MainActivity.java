package com.thaliees.speech;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    // Declare our result code
    private static Integer CODE_SPEECH = 100;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        ImageButton voice = findViewById(R.id.voice);
        voice.setOnClickListener(listen);
    }

    private View.OnClickListener listen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Declare the Intent
            Intent speech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            // Informs the recognizer which speech model to prefer when performing
            // In our case, use a language model based on free-form speech recognition.
            // This extra is required
            speech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            // This tag informs the recognizer to perform speech recognition in a language different than the one set in the Locale.getDefault().
            // In our case, use the current value
            // This extra is optional
            speech.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            // Optional text prompt to show to the user when asking them to speak.
            // This extra is optional
            speech.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.msg_voice));

            // Start intent.
            // NOTE: startActivity() is not supported
            try {
                // The results will be returned via activity results (onActivityResult)
                startActivityForResult(speech, CODE_SPEECH);
            }
            catch (ActivityNotFoundException ignored){

            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_SPEECH && resultCode == RESULT_OK && data != null){
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            text.setText(result.get(0));
        }
    }
}
