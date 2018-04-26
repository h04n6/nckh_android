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

public class Part5 extends AppCompatActivity{

    private RadioGroup radioGroup;
    private Button buttonNext, buttonPrevious, buttonList;
    private TextView textView;
    private ImageView imageViewFlag;
    private RadioButton rdbA;
    private RadioButton rdbB;
    private RadioButton rdbC;
    private RadioButton rdbD;

    private int count = 0;

    ArrayList<Part5_info> arrayListP5;

    private int checkFlagImage = 0;
    private static final int MY_REQUEST_CODE = 2207;
    private int NUMBER_OF_QUESTION;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part5);

        NUMBER_OF_QUESTION = Integer.parseInt(getString(R.string.number_question_part5));

        getView();

        textView.setText(getString(R.string.question, String.valueOf(count + 1)));

        //button Next
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioGroup.getCheckedRadioButtonId() != -1){
                    new Scoring().checkUserAnswer(radioGroup, arrayListP5.get(count).getCorrect());
                }else{
                    //Toast.makeText(Part1.this, "You didnt choose", Toast.LENGTH_SHORT).show();
                }

                if(count != NUMBER_OF_QUESTION){
                    count++;
                }
                showData(count);
                textView.setText(getString(R.string.question, String.valueOf(count + 1)));
            }
        });

        //button Precious
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count != 0) {
                    count--;
                }
                showData(count);
                textView.setText(getString(R.string.question, String.valueOf(count + 1)));
            }
        });

        //button List
        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] question_number_list = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                Intent myIntent = new Intent(Part5.this, QuestionList.class);
                int arr[] = new int[NUMBER_OF_QUESTION];
                for(int i = 0; i < NUMBER_OF_QUESTION; i++){
                    arr[i] = arrayListP5.get(i).getState();
                }
                myIntent.putExtra("state", arr);
                myIntent.putExtra("question_number", question_number_list);
                startActivityForResult(myIntent, MY_REQUEST_CODE);
            }
        });

        //RadioGroup
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkFlagImage == 0) { //blank_flag
                    arrayListP5.get(count).setState(1); //checked
                }
            }
        });

        imageViewFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFlagImage == 0){
                    imageViewFlag.setImageDrawable(getResources().getDrawable((R.drawable.ic_red_flag)));
                    checkFlagImage = 1;
                    arrayListP5.get(count).setState(0); //flag
                }else{
                    imageViewFlag.setImageDrawable(getResources().getDrawable((R.drawable.ic_blank_flag)));
                    checkFlagImage = 0;
                    if(radioGroup.getCheckedRadioButtonId() != -1){
                        arrayListP5.get(count).setState(1); //checked
                    }else{
                        arrayListP5.get(count).setState(2); //unchecked
                    }
                }
            }
        });

        getData(getString(R.string.url_server_part1));
    }

    //contain structure of part5
    private class Part5_info {
        private String question, correct;
        private String[] answers;
        private int state; //0, 1, 2 = Flag, Checked, Unchecked

        public Part5_info(String question, String ans, String correct) {
            //this.answers = new String[4]; //we have 4 answers
            this.answers = ans.split("@");
            this.question = question;
            this.correct = correct;
            this.state = 2; //mặc định là unchecked
        }

        public String getQuestion() {
            return question;
        }

        public String[] getAnswers() {
            return answers;
        }

        public String getCorrect() {
            return this.correct;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }

    //mapped view
    private void getView() {
        radioGroup = findViewById(R.id.radioGroup_part5);
        buttonNext = findViewById(R.id.buttonNext_part5);
        buttonPrevious = findViewById(R.id.buttonPrevious_part5);
        buttonList = findViewById(R.id.buttonList_part5);
        textView = findViewById(R.id.textViewQuestion_part5);
        imageViewFlag = findViewById(R.id.imageViewFlag_part5);
        rdbA = findViewById(R.id.a_part5);
        rdbB = findViewById(R.id.b_part5);
        rdbC = findViewById(R.id.c_part5);
        rdbD = findViewById(R.id.d_part5);
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
                                arrayListP5.add(new Part5_info(
                                        object.getString("image"),
                                        object.getString("sound"),
                                        object.getString("correct")
                                ));
                                //Toast.makeText(Part5.this, arrayListP5.get(i).getSound(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Part5.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

    private void showData(int count) {
        rdbA.setText(arrayListP5.get(count).getAnswers()[0]);
        rdbB.setText(arrayListP5.get(count).getAnswers()[1]);
        rdbC.setText(arrayListP5.get(count).getAnswers()[2]);
        rdbD.setText(arrayListP5.get(count).getAnswers()[3]);
        textView.setText(arrayListP5.get(count).getQuestion());
    }
}
