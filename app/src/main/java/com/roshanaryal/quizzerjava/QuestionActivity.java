package com.roshanaryal.quizzerjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuestionActivity extends AppCompatActivity {

    private TextView question;
    private TextView noIndicator;
    private FloatingActionButton bookmarks;
    private LinearLayout optionContainer;
    private Button shareButton,nextButton;
    private int count=0;
    private List<QuestionModal> questionModalList;
    private int position=0;
    private  int score=0;

    private String category;
    private int setNo;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private Dialog loadingDialog;

    ConnectivityManager connectivityManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        question=findViewById(R.id.questionTextView);
        bookmarks=findViewById(R.id.bookmarksButton);
        optionContainer=findViewById(R.id.linearLayout2);
        shareButton=findViewById(R.id.shareButton);
        nextButton=findViewById(R.id.nextButton);
        noIndicator=findViewById(R.id.textIndicator);

        Intent intent=getIntent();
        category=intent.getStringExtra("category");
        setNo=intent.getIntExtra("setNo",1);

         questionModalList=new ArrayList<>();

        if (haveNetworkConnection()) {


            loadingDialog = new Dialog(this);
            loadingDialog.setContentView(R.layout.loading_dialog);
            Objects.requireNonNull(loadingDialog.getWindow()).setBackgroundDrawable(getDrawable(R.drawable.rounded_corners));

            loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            loadingDialog.show();


            myRef.child("SETS").child(category).child("questions").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        questionModalList.add(dataSnapshot1.getValue(QuestionModal.class));
                    }

                    loadingDialog.dismiss();


                    if (questionModalList.size() > 0) {
                        for (int i = 0; i < 4; i++) {

                            optionContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    checkAnswer(((Button) v));
                                }
                            });
                        }


                    } else {
                        Toast.makeText(QuestionActivity.this, "no question", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(QuestionActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                    finish();
                }
            });


        }
        else {

            questionModalList.add(new QuestionModal("This is sample question  1","optionA","optionB","optionC","optionD","optionD",2));
            questionModalList.add(new QuestionModal("This is sample question 2","optionA","optionB","optionC","optionD","optionD",2));
            questionModalList.add(new QuestionModal("This is sample question 3","optionA","optionB","optionC","optionD","optionD",2));
            questionModalList.add(new QuestionModal("This is sample question 4","optionA","optionB","optionC","optionD","optionD",2));
            questionModalList.add(new QuestionModal("This is sample question 5","optionA","optionB","optionC","optionD","optionD",2));
            questionModalList.add(new QuestionModal("This is sample question 6","optionA","optionB","optionC","optionD","optionD",2));
        }

        for (int i = 0; i < 4; i++) {

            optionContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(((Button) v));
                }
            });
        }

        playAnim(question, 0, questionModalList.get(position).getQuestion());

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;
                nextButton.setEnabled(false);
                nextButton.setAlpha(0.7f);

                position++;

                if (position == questionModalList.size()) {
                    //score activity
                    Intent scoreIntent = new Intent(getApplicationContext(), ScoreActivity.class);
                    scoreIntent.putExtra("score", String.valueOf(score));

                    startActivity(scoreIntent);
                    finish();


                } else {
                    enableOption(true);
                    playAnim(question, 0, questionModalList.get(position).getQuestion());
                }


                count = 0;


            }
        });


    }

    private void  playAnim(final View view , final int value, final String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(100).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

                if (value==0 && count<4){
                    String option="";

                    if (count==0){
                        option=questionModalList.get(position).getOptionA();
                    }
                    else if (count==1)
                    {
                        option=questionModalList.get(position).getOptionB();
                    }
                    else if (count==2)
                    {
                        option=questionModalList.get(position).getOptionC();
                    }
                    else if (count==3)
                    {
                        option=questionModalList.get(position).getOptionD();
                    }
                    playAnim(optionContainer.getChildAt(count),0,option);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //change data here and make view visibe


                            if (value==0){

                                try {
                                    ( (TextView)view).setText(data);
                                    String position1=String.valueOf(position+1);
                                    noIndicator.setText(position1+"/"+questionModalList.size());
                                }catch (Exception e){

                                }

                                view.setTag(data);

                                playAnim(view,1,data);
                            }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void checkAnswer(Button slectedOption){
            enableOption(false);
            nextButton.setEnabled(true);
            nextButton.setAlpha(1);
            if (slectedOption.getText().toString().equals(questionModalList.get(position).getCorrectAnswer())){
                //correct
                        slectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
                        score++;
            }
            else
            {
             //incorrect
                slectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
                  Button correctButton=  (Button)optionContainer.findViewWithTag(questionModalList.get(position).getCorrectAnswer());
                correctButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
            }

    }


    private void enableOption(boolean b){
        for (int i=0;i<4;i++){

           optionContainer.getChildAt(i).setEnabled(b);
            if (b){
                optionContainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));
            }
        }


    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        try {

            assert cm != null;
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
