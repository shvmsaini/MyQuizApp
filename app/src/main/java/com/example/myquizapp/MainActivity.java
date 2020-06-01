package com.example.myquizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {

    Button mtruebutton;
    Button mfalsebutton;
    TextView mscoreview;
    TextView mQuestiontextview;
    ProgressBar mProgressBar;
    int mIndex;
    int mScore=0;
    TrueFalse[] QuestionBank= new TrueFalse[]{
            new TrueFalse(R.string.question_1,true),
            new TrueFalse(R.string.question_2,false),
            new TrueFalse(R.string.question_3,false),
            new TrueFalse(R.string.question_4,false),
            new TrueFalse(R.string.question_5,true),
            new TrueFalse(R.string.question_6,true),
            new TrueFalse(R.string.question_7,true),
            new TrueFalse(R.string.question_8,false),
            new TrueFalse(R.string.question_9,true),
            new TrueFalse(R.string.question_10,true)
    };
    final int progress_bar_increment = (int) Math.ceil(100.0 / QuestionBank.length);

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null){
            mScore = savedInstanceState.getInt("Score");
            mIndex = savedInstanceState.getInt("Index");


        }else{
            mScore  =0 ;
            mIndex = 0;
        }
        mtruebutton = findViewById(R.id.true_button);
        mfalsebutton = findViewById(R.id.false_button);
        mQuestiontextview = findViewById(R.id.question_view);
        mProgressBar = findViewById(R.id.progressBar);
        mscoreview = findViewById(R.id.score);
        mscoreview.setText("Score:"+mScore+"/"+QuestionBank.length);
        TrueFalse first_question = QuestionBank[mIndex];
        int questionRID = first_question.getmQuestionID(); // getting resource ID

        mQuestiontextview.setText(questionRID);
        mtruebutton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                checkAnswer(true);
                updateQuestion();
            }
        });
        mfalsebutton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                checkAnswer(false);
                updateQuestion();
            }
        });

    }
    @SuppressLint("SetTextI18n")
    public void updateQuestion(){
        mIndex =(mIndex+1) %QuestionBank.length;
        if (mIndex==0){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Gave is finished");
            alert.setCancelable(false).setMessage("You scored "+ mScore+ " points out of " +QuestionBank.length ).setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).setNegativeButton("Restart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mProgressBar.setProgress(0);
                    recreate();
                }
            });
            alert.show();
        }
        int questionRID =  QuestionBank[mIndex].getmQuestionID();
        mQuestiontextview.setText(questionRID);
        mProgressBar.incrementProgressBy(progress_bar_increment);
        mscoreview.setText("Score:"+mScore+"/"+QuestionBank.length);

    }


    public void checkAnswer(boolean userSelection){
        boolean correctAnswer = QuestionBank[mIndex].ismAnswer();
        if (userSelection==correctAnswer){
            mScore++;
            Toast.makeText(getApplicationContext(),R.string.correct_toast,Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(getApplicationContext(),R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("Score",mScore);
        outState.putInt("Index",mIndex);
    }
}

