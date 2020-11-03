package com.mohammadmansour.geoquiz;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.os.Vibrator;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RadioGroup optionsGroup1;
    private RadioGroup optionsGroup2;
    private RadioGroup optionsGroup3;
    private EditText flagNameEditText1;
    private EditText flagNameEditText2;
    private int score;
    private ArrayList<CheckBox> checkboxesArray = new ArrayList<>();
    private ArrayList<Integer> answers = new ArrayList<>();

    private Vibrator vibrator;
    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res = getResources();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        Button submitBtn = findViewById(R.id.submit_btn);
        flagNameEditText1 = findViewById(R.id.ar_edit_text);
        flagNameEditText2 = findViewById(R.id.au_edit_text);
        optionsGroup1 = findViewById(R.id.options_radio_group1);
        optionsGroup2 = findViewById(R.id.options_radio_group2);
        optionsGroup3 = findViewById(R.id.options_radio_group3);
        checkboxesArray.add((CheckBox) findViewById(R.id.checkbox1));
        checkboxesArray.add((CheckBox) findViewById(R.id.checkbox2));
        checkboxesArray.add((CheckBox) findViewById(R.id.checkbox3));
        checkboxesArray.add((CheckBox) findViewById(R.id.checkbox4));
        answers.add(R.string.eg);
        answers.add(R.string.ar);
        answers.add(R.string.ca);
        answers.add(R.string.nz);
        answers.add(R.string.au);

        score = 0;

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check answers
                for (int i = 1; i <= 6; i++) {
                    validate(checkAnswer(i));
                }
                // reset answers
                optionsGroup1.clearCheck();
                optionsGroup2.clearCheck();
                optionsGroup3.clearCheck();
                flagNameEditText1.setText("");
                flagNameEditText2.setText("");
                for (CheckBox c: checkboxesArray){
                    c.setChecked(false);
                }
                // show score message & reset score
                Toast.makeText(MainActivity.this, String.format(res.getString(R.string.result_message),
                        score), Toast.LENGTH_LONG).show();
                score = 0;
            }
        });
    }
    /**
     * Checks every question's answer and returns boolean value: whether or not the answer is correct
     * @param quesNum is the question number */
    private boolean checkAnswer(int quesNum){
        try {
            switch (quesNum) {
                case 1:
                    return ((RadioButton) findViewById(optionsGroup1.getCheckedRadioButtonId())).getText()
                            == getString(answers.get(0));
                case 2:
                    return flagNameEditText1.getText().toString().trim().equals(getString(answers.get(1)));
                case 3:
                    return ((RadioButton) findViewById(optionsGroup2.getCheckedRadioButtonId())).getText()
                            == getString(answers.get(2));
                case 4:
                    return ((RadioButton) findViewById(optionsGroup3.getCheckedRadioButtonId())).getText()
                            == getString(answers.get(3));
                case 5:
                    return flagNameEditText2.getText().toString().trim().equals(getString(answers.get(4)));
                case 6:
                    return checkboxesArray.get(0).isChecked() && checkboxesArray.get(1).isChecked()
                            && !checkboxesArray.get(2).isChecked() && checkboxesArray.get(3).isChecked();
            }
        }
        // in case there's a question left blank
        catch (NullPointerException e){
            if(hasVibrator()) vibrator.vibrate(300);
        }
        return false;
    }
    /**
     * Updates score based on correct answers
     * @param isCorrect is the boolean value returned from checkAnswer function*/
    private void validate(boolean isCorrect){
        if (isCorrect){
            score++;
        }
    }
    /** Returns whether or not the device has a vibrator */
    private boolean hasVibrator() {
        return vibrator.hasVibrator();
    }

}