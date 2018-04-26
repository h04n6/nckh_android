package h04n6.toeic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hoang on 4/5/2018.
 */

public class Part3 extends AppCompatActivity {

    private RadioGroup radioGroup_1;
    private RadioGroup radioGroup_2;
    private RadioGroup radioGroup_3;
    private TextView textViewQuestionValue_1;
    private TextView textViewQuestionValue_2;
    private TextView textViewQuestionValue_3;
    private TextView textView;
    private Button buttonAgain, buttonNext, buttonPrevious, buttonList;
    private SeekBar seekBar;
    private ImageView imageViewFlag;
    private PlaySound[] playSound;
    private TextView song_duration;
    private TextView current_position;

    private int count = 0;
    private static final int p3 = 41;
    private static final int p4 = 71;

    ArrayList<Part3_info> arrayListP3;

    private int checkFlagImage = 0;
    private static final int MY_REQUEST_CODE = 2207;
    private static final int NUMBER_OF_QUESTION = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part3);

        getView();

        //set an Intent in here
        textView.setText(getString(R.string.question, String.valueOf(count + p3) + " - " + String.valueOf(count + p3 + 2)));

        //button Next
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound[count].stopSound();
                if(radioGroup_1.getCheckedRadioButtonId() != -1){
                    new Scoring().checkUserAnswer(radioGroup_1, arrayListP3.get(count).getCorrect());
                }
                if(radioGroup_2.getCheckedRadioButtonId() != -1){
                    new Scoring().checkUserAnswer(radioGroup_2, arrayListP3.get(count + 1).getCorrect());
                }
                if(radioGroup_3.getCheckedRadioButtonId() != -1){
                    new Scoring().checkUserAnswer(radioGroup_3, arrayListP3.get(count + 2).getCorrect());
                }
                if(count != NUMBER_OF_QUESTION){
                    count = count + 3;
                }
                showData(count);
                textView.setText(getString(R.string.question, String.valueOf(count + p3)));
            }
        });

        //button Precious
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound[count].stopSound();
                if(count != 0) {
                    count = count - 3;
                }
                showData(count);
                textView.setText(getString(R.string.question, String.valueOf(count + p3)));
            }
        });

        //button again
        buttonAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //button List
        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] question_number_list = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                Intent myIntent = new Intent(Part3.this, QuestionList.class);
                int arr[] = new int[NUMBER_OF_QUESTION];
                for(int i = 0; i < NUMBER_OF_QUESTION; i+=3){
                    if(checkFlagImage == 1){
                        arr[i] = 0;
                    } else if(arrayListP3.get(i).getState() == 1
                            && arrayListP3.get(i + 1).getState() == 1
                            && arrayListP3.get(i + 2).getState() == 1){
                        arr[i] = 1;
                    }else {
                        arr[i] = 2;
                    }
                }
                myIntent.putExtra("state", arr);
                myIntent.putExtra("question_number", question_number_list);
                startActivityForResult(myIntent, MY_REQUEST_CODE);
            }
        });

        //RadioGroup
        radioGroup_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkFlagImage == 0) { //blank_flag
                    arrayListP3.get(count).setState(1); //checked
                }
            }
        });

        radioGroup_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkFlagImage == 0) { //blank_flag
                    arrayListP3.get(count + 1).setState(1); //checked
                }
            }
        });

        radioGroup_3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkFlagImage == 0) { //blank_flag
                    arrayListP3.get(count + 2).setState(1); //checked
                }
            }
        });

        //image Flag
        imageViewFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFlagImage == 0){
                    imageViewFlag.setImageDrawable(getResources().getDrawable((R.drawable.ic_red_flag)));
                    checkFlagImage = 1;
                    //arrayListP3.get(count).setState(0); //flag
                }else{
                    imageViewFlag.setImageDrawable(getResources().getDrawable((R.drawable.ic_blank_flag)));
                    checkFlagImage = 0;
                    if(radioGroup_1.getCheckedRadioButtonId() != -1){
                        arrayListP3.get(count).setState(1); //checked
                    }

                    if(radioGroup_2.getCheckedRadioButtonId() != -1){
                        arrayListP3.get(count + 1).setState(1); //checked
                    }

                    if(radioGroup_3.getCheckedRadioButtonId() != -1){
                        arrayListP3.get(count + 2).setState(1); //checked
                    }
                }
            }
        });

        getDataFrom();

    }

    private void getView(){
        radioGroup_1 = findViewById(R.id.radioGroup_part3_1);
        radioGroup_2 = findViewById(R.id.radioGroup_part3_2);
        radioGroup_3 = findViewById(R.id.radioGroup_part3_3);
        buttonAgain = findViewById(R.id.buttonAgain_part3);
        buttonNext = findViewById(R.id.buttonNext_part3);
        buttonPrevious = findViewById(R.id.buttonPrevious_part3);
        buttonList = findViewById(R.id.buttonList_part3);
        seekBar = findViewById(R.id.seekBar_part3);
        arrayListP3 = new ArrayList<>();
        playSound = new PlaySound[R.string.number_question_part3 / 3]; //đối tượng mediaPlayer chơi nhạc cho 10 câu
        imageViewFlag = findViewById(R.id.imageViewFlag_part3);
        current_position = findViewById(R.id.textViewCurrentTime_part3);
        song_duration = findViewById(R.id.textViewSongDuration_part3);
        textView = findViewById(R.id.textViewQuestion_part3);
        textViewQuestionValue_1 = findViewById(R.id.textViewQuestionValue_part3_1);
        textViewQuestionValue_2 = findViewById(R.id.textViewQuestionValue_part3_2);
        textViewQuestionValue_3 = findViewById(R.id.textViewQuestionValue_part3_3);
    } //để ý số lượng sound trong mảng mediaplayer

    //determine part 3 or 4
    private void getDataFrom(){
        Intent myIntent = this.getIntent();
        int part = myIntent.getIntExtra("part", 3);
        if(part == 3){
            getData(getString(R.string.url_server_part3));
        }else if(part == 4){
            getData(getString(R.string.url_server_part4));
        }
    }

    private class Part3_info{
        private String questionValue, sound;
        private String answerA, answerB, answerC, answerD;
        private String correct;
        private int state; //0, 1, 2 = Flag, Checked, Unchecked

        public Part3_info(String sound, String questionValue, String answerA, String answerB, String answerC, String answerD, String correct) {
            this.questionValue = questionValue;
            this.answerA = answerA;
            this.answerB = answerB;
            this.answerC = answerC;
            this.answerD = answerD;
            this.correct = correct;
            this.sound = sound;
            this.state = 2;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getSound() {
            return sound;
        }

        public String getQuestionValue() {
            return questionValue;
        }

        public String getAnswerA() {
            return answerA;
        }

        public String getAnswerB() {
            return answerB;
        }

        public String getAnswerC() {
            return answerC;
        }

        public String getAnswerD() {
            return answerD;
        }

        public String getCorrect() {
            return correct;
        }

        public void setQuestionValue(String questionValue) {
            this.questionValue = questionValue;
        }

        public void setAnswerA(String answerA) {
            this.answerA = answerA;
        }

        public void setAnswerB(String answerB) {
            this.answerB = answerB;
        }

        public void setAnswerC(String answerC) {
            this.answerC = answerC;
        }

        public void setAnswerD(String answerD) {
            this.answerD = answerD;
        }

        public void setCorrect(String correct) {
            this.correct = correct;
        }

        public void setSound(String sound) {
            this.sound = sound;
        }
    }

    private void getData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                arrayListP3.add(new Part3_info(
                                        object.getString("question"),
                                        object.getString("answerA"),
                                        object.getString("answerB"),
                                        object.getString("answerC"),
                                        object.getString("answerD"),
                                        object.getString("correct"),
                                        object.getString("sound")
                                ));

                                playSound[i] = new PlaySound(seekBar, buttonAgain, song_duration, current_position);

                                //Toast.makeText(Part3.this, arrayListP3.get(i).getSound(), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //điều kiện đảm bảo mọi thứ đã được load xong xuôi
                            //tức là i đã chạy hết vòng chạy
                            if(i == response.length() - 1){
                                showData(0);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Part3.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

    private void showData(int count) {
        playSound[count].startSound(getString(R.string.url_sound, arrayListP3.get(count).getSound()));

        textViewQuestionValue_1.setText(arrayListP3.get(count).getQuestionValue());
        textViewQuestionValue_2.setText(arrayListP3.get(count + 1).getQuestionValue());
        textViewQuestionValue_3.setText(arrayListP3.get(count + 2).getQuestionValue());

        ((RadioButton)radioGroup_1.getChildAt(0)).setText(getString
                (R.string.answerA, arrayListP3.get(count).getAnswerA()));
        ((RadioButton)radioGroup_1.getChildAt(1)).setText(getString
                (R.string.answerB, arrayListP3.get(count).getAnswerB()));
        ((RadioButton)radioGroup_1.getChildAt(2)).setText(getString
                (R.string.answerC, arrayListP3.get(count).getAnswerC()));
        ((RadioButton)radioGroup_1.getChildAt(3)).setText(getString
                (R.string.answerD, arrayListP3.get(count).getAnswerD()));

        ((RadioButton)radioGroup_2.getChildAt(0)).setText(getString
                (R.string.answerA, arrayListP3.get(count + 1).getAnswerA()));
        ((RadioButton)radioGroup_2.getChildAt(1)).setText(getString
                (R.string.answerB, arrayListP3.get(count + 1).getAnswerB()));
        ((RadioButton)radioGroup_2.getChildAt(2)).setText(getString
                (R.string.answerC, arrayListP3.get(count + 1).getAnswerC()));
        ((RadioButton)radioGroup_2.getChildAt(3)).setText(getString
                (R.string.answerD, arrayListP3.get(count + 1).getAnswerD()));

        ((RadioButton)radioGroup_3.getChildAt(0)).setText(getString
                (R.string.answerA, arrayListP3.get(count + 2).getAnswerA()));
        ((RadioButton)radioGroup_3.getChildAt(1)).setText(getString
                (R.string.answerB, arrayListP3.get(count + 2).getAnswerB()));
        ((RadioButton)radioGroup_3.getChildAt(2)).setText(getString
                (R.string.answerC, arrayListP3.get(count + 2).getAnswerC()));
        ((RadioButton)radioGroup_3.getChildAt(3)).setText(getString
                (R.string.answerD, arrayListP3.get(count + 2).getAnswerD()));

        seekBar.setProgress(0);
    }
}
