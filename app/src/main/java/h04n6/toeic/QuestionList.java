package h04n6.toeic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hoang on 4/2/2018.
 */

public class QuestionList extends AppCompatActivity {
    String arr_part1[];
    GridView gridView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_question);

        gridView = findViewById(R.id.gridViewListQuestion);
        List<Question> question_list = getListData();
        gridView.setAdapter(new CustomGridAdapter(this, question_list));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //todo show the exactly question of Part 1
                //selection.setText(arr_part1[position]);
            }
        });
    }

    private List<Question> getListData(){
        List<Integer> question_number_list = new ArrayList<>();
        List<Question> list = new ArrayList<>();
        Intent myIntent = this.getIntent();
        int state[] = myIntent.getIntArrayExtra("state");
        int question_number[] = myIntent.getIntArrayExtra("question_number");
        for(int i = 0; i < state.length; i++){
            list.add(new Question(question_number[i], state[i]));
        }
        return list;
    }

    private class Question{
        private int id;
        private int state; //0 = orange: "flag"; 1 = green: "checked"; 2 = white: "unchecked"

        public Question(int id, int state) {
            this.id = id;
            this.state = state;
        }

        public int getId() {
            return id;
        }

        public int getState() {
            return state;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setState(int state) {
            this.state = state;
        }
    }

    private class CustomGridAdapter extends BaseAdapter{
        private List<Question> listData;
        private LayoutInflater layoutInflater;
        private Context context;

        public CustomGridAdapter(Context acontext, List<Question> listData){
            this.context = acontext;
            this.listData = listData;
            layoutInflater = LayoutInflater.from(acontext);
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.grid_item_layout, null);
                holder = new ViewHolder();
                holder.textViewQuestion = (TextView) convertView.findViewById(R.id.textViewQuestion);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Question question = this.listData.get(position);
            holder.textViewQuestion.setText(getString(R.string.question, String.valueOf(question.getId())));
            int ck = question.getState();
            if(ck == 0){
                //Flag
                holder.textViewQuestion.setBackgroundColor(Color.RED);
            }else if(ck == 1){
                //Checked
                holder.textViewQuestion.setBackgroundColor(Color.GREEN);
            }else{
                //Unchecked
                holder.textViewQuestion.setBackgroundColor(Color.WHITE);
            }

            return convertView;
        }

        class ViewHolder {
            TextView textViewQuestion;
        }
    }

}
