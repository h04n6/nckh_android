package h04n6.toeic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
 * Created by hoang on 3/23/2018.
 */

public class Part1 extends AppCompatActivity{

    private RadioGroup radioGroup;
    private Button buttonAgain, buttonNext, buttonPrevious, buttonList;
    private ImageView imageView;
    private TextView textView;
    private SeekBar seekBar;
    private ImageView imageViewFlag;
    private PlaySound[] playSound;
    private TextView song_duration;
    private TextView current_position;

    private int count = 0;

    ArrayList<Part1_info> arrayListP1;

    private int checkFlagImage = 0;
    private static final int MY_REQUEST_CODE = 2207;
    private int NUMBER_OF_QUESTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part1);

        NUMBER_OF_QUESTION = Integer.parseInt(getString(R.string.number_question_part1));

        getView();

        textView.setText(getString(R.string.question, String.valueOf(count + 1)));

        //button Next
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound[count].stopSound();
                if(radioGroup.getCheckedRadioButtonId() != -1){
                    new Scoring().checkUserAnswer(radioGroup, arrayListP1.get(count).getCorrect());
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
                playSound[count].stopSound();
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
                Intent myIntent = new Intent(Part1.this, QuestionList.class);
                int arr[] = new int[NUMBER_OF_QUESTION];
                for(int i = 0; i < NUMBER_OF_QUESTION; i++){
                    arr[i] = arrayListP1.get(i).getState();
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
                    arrayListP1.get(count).setState(1); //checked
                }
            }
        });

        imageViewFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFlagImage == 0){
                    imageViewFlag.setImageDrawable(getResources().getDrawable((R.drawable.ic_red_flag)));
                    checkFlagImage = 1;
                    arrayListP1.get(count).setState(0); //flag
                }else{
                    imageViewFlag.setImageDrawable(getResources().getDrawable((R.drawable.ic_blank_flag)));
                    checkFlagImage = 0;
                    if(radioGroup.getCheckedRadioButtonId() != -1){
                        arrayListP1.get(count).setState(1); //checked
                    }else{
                        arrayListP1.get(count).setState(2); //unchecked
                    }
                }
            }
        });

        getData(getString(R.string.url_server_part1));
    }

    //contain structure of part1
    private class Part1_info {
        private String image, sound, correct;
        private int state; //0, 1, 2 = Flag, Checked, Unchecked

        public Part1_info(String image, String sound, String correct) {
            this.image = image;
            this.sound = sound;
            this.correct = correct;
            this.state = 2; //mặc định là unchecked
        }

        public String getImage() {
            return this.image;
        }

        public String getSound() {
            return this.sound;
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
        radioGroup = findViewById(R.id.radioGroup_part1);
        buttonAgain = findViewById(R.id.buttonAgain_part1);
        buttonNext = findViewById(R.id.buttonNext_part1);
        buttonPrevious = findViewById(R.id.buttonPrevious_part1);
        buttonList = findViewById(R.id.buttonList_part1);
        imageView = findViewById(R.id.imageViewPart1);
        textView = findViewById(R.id.textViewQuestion_part1);
        seekBar = findViewById(R.id.seekBar_part1);
        arrayListP1 = new ArrayList<>();
        playSound = new PlaySound[10]; //đối tượng mediaPlayer chơi nhạc cho 10 câu
        imageViewFlag = findViewById(R.id.imageViewFlag_part1);
        current_position = findViewById(R.id.textViewCurrentTime_part1);
        song_duration = findViewById(R.id.textViewSongDuration_part1);
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
                                arrayListP1.add(new Part1_info(
                                        object.getString("image"),
                                        object.getString("sound"),
                                        object.getString("correct")
                                ));

                                playSound[i] = new PlaySound(seekBar, buttonAgain, song_duration, current_position);

                                Toast.makeText(Part1.this, arrayListP1.get(i).getSound(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Part1.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

    private void showData(int count) {
        new PlayImage(imageView).execute(getString(R.string.url_image, arrayListP1.get(count).getImage()));
        playSound[count].startSound(getString(R.string.url_sound, arrayListP1.get(count).getSound()));
        seekBar.setProgress(0);
    }
}
