package h04n6.toeic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button buttonPart1;
    private Button buttonPart2;
    private Button buttonPart3;
    private Button buttonPart4;
    private Button buttonPart5;
    private Button buttonPart6;
    private Button buttonPart7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getView();

        buttonPart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(MainActivity.this, Part1.class);
                startActivity(myintent);
            }
        });

        buttonPart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(MainActivity.this, Part2.class);
                startActivity(myintent);
            }
        });

        buttonPart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(MainActivity.this, Part3.class);
                myintent.putExtra("part", 3);
                startActivity(myintent);
            }
        });

        buttonPart4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(MainActivity.this, Part3.class);
                myintent.putExtra("part", 4);
                startActivity(myintent);
            }
        });

        buttonPart5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(MainActivity.this, Part5.class);
                startActivity(myintent);
            }
        });

        buttonPart6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(MainActivity.this, Part6.class);
                startActivity(myintent);
            }
        });

        buttonPart7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(MainActivity.this, Part7.class);
                startActivity(myintent);
            }
        });
    }

    public void getView(){
        buttonPart1 = findViewById(R.id.buttonPart1);
        buttonPart2 = findViewById(R.id.buttonPart2);
        buttonPart3 = findViewById(R.id.buttonPart3);
        buttonPart4 = findViewById(R.id.buttonPart4);
        buttonPart5 = findViewById(R.id.buttonPart5);
        buttonPart6 = findViewById(R.id.buttonPart6);
        buttonPart7 = findViewById(R.id.buttonPart7);
    }

}
