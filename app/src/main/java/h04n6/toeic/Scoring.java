package h04n6.toeic;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hoang on 3/8/2018.
 */

public class Scoring {

    //TODO khi chấm điểm từng part, mỗi hàm/part sẽ chạy 1 vòng for với số thứ tự câu tương ứng
    //todo ví dụ : part 1 chạy từ 0 đến 9; part 2 chạy từ 10 đến 39; ...

    private static final int toeic_numberOflistenning = 100;
    private static final int toeic_numberOfReading = 100;

    private static final int toeic_numberOfPart1 = 10; //10
    private static final int toeic_numberOfPart2 = 40; //30
    private static final int toeic_numberOfPart3 = 70; //30
    private static final int toeic_numberOfPart4 = 100; //30
    private static final int toeic_numberOfPart5 = 140; //40
    private static final int toeic_numberOfPart6 = 162; //12
    private static final int toeic_numberOfPart7 = 200; //48

    ArrayList<Integer> userAnswer = new ArrayList<>(); //chứa toàn bộ 200 câu tl của người dùng

    //check && add if user is right : 1 else : 0
    public void checkUserAnswer(RadioGroup radioGroup, String correct){
        int checkedId = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(checkedId);
        int radioId = radioGroup.indexOfChild(radioButton);
        RadioButton rdb = (RadioButton) radioGroup.getChildAt(radioId);
        String uA = (String) rdb.getTag();

        if(uA.equals(correct)){
            userAnswer.add(1);
        }
        else{
            userAnswer.add(0);
        }
    }

    public String[] TOEIC_score_all(){
        int cL = 0, cR = 0; //count listen and read
        int total = toeic_numberOflistenning + toeic_numberOfReading;
        for(int i = 0; i < toeic_numberOflistenning; i++){
            if(userAnswer.get(i) == 1){
                cL++;
            }
        }
        for(int i = toeic_numberOflistenning; i < total; i++){
            if(userAnswer.get(i) == 1){
                cR++;
            }
        }

        String res[] = new String[2];
        //comment for listenning part
        if(cL <= 200) res[0] = "Bạn cần cố gắng hơn";
        else if(cL > 200 && cL < 250) res[0] = "Trung bình";
        else if(cL >= 250 && cL < 350) res[0] = "Cũng tạm được";

        //TODO: write comment for each point level

        return res;
    }

    //TODO mỗi part sẽ là 1 lời nhận xét riêng

    public String TOEIC_score_part1(){
        int c = 0;
        for(int i = 0; i < toeic_numberOfPart1; i++){
            if(userAnswer.get(i)==1) c++;
        }
        String res ="";
        //TODO
        return res;
    }

    public String TOEIC_score_part2(){
        int c = 0;
        for(int i = toeic_numberOfPart1; i < toeic_numberOfPart2; i++){
            if(userAnswer.get(i)==1) c++;
        }
        String res ="Your score: ";
        //TODO
        return res;
    }

    public String TOEIC_score_part3(){
        int c = 0;
        for(int i = toeic_numberOfPart2; i < toeic_numberOfPart3; i++){
            if(userAnswer.get(i)==1) c++;
        }
        String res ="Your score: ";
        //TODO
        return res;
    }

    public String TOEIC_score_part4(){
        int c = 0;
        for(int i = toeic_numberOfPart3; i < toeic_numberOfPart4; i++){
            if(userAnswer.get(i)==1) c++;
        }
        String res = "";
        //TODO
        return res;
    }

    public String TOEIC_score_part5(){
        int c = 0;
        for(int i = toeic_numberOfPart4; i < toeic_numberOfPart5; i++){
            if(userAnswer.get(i)==1) c++;
        }
        String res ="";
        //TODO
        return res;
    }

    public String TOEIC_score_part6(){
        int c = 0;
        for(int i = toeic_numberOfPart5; i < toeic_numberOfPart6; i++){
            if(userAnswer.get(i)==1) c++;
        }
        String res ="";
        //TODO
        return res;
    }

    public String TOEIC_score_part7(){
        int c = 0;
        for(int i = toeic_numberOfPart6; i < toeic_numberOfPart7; i++){
            if(userAnswer.get(i)==1) c++;
        }
        String res ="";
        //TODO
        return res;
    }
}
