package h04n6.toeic;


/**
 * Created by hoang on 3/22/2018.
 */

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Part7 extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //không hiển thị tiêu đề
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_part7);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    public static class PlaceholderFragment extends Fragment {

        private static final String KEY_COLOR = "key_color";

        public PlaceholderFragment(){}

        //Method static dạng singleton, cho phép tạo fragment mới, lấy tham số đầu vào để cài đặt màu sắc
        public static PlaceholderFragment newInstance(int color){
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(KEY_COLOR, color);
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_part7_questions, container, false);
            LinearLayout relativeLayout = rootView.findViewById(R.id.linearLayout_part7_questions);

            //số 1: màu xanh
            //số 2: màu đỏ
            //số 3: màu vàng

            switch (getArguments().getInt(KEY_COLOR)){
                case 1:
                    relativeLayout.setBackgroundColor(Color.GREEN);
                    break;
                case 2:
                    relativeLayout.setBackgroundColor(Color.BLUE);
                    break;
                case 3:
                    relativeLayout.setBackgroundColor(Color.RED);
                    break;
                default:
                    relativeLayout.setBackgroundColor(Color.GREEN);
                    break;
            }

            //TextView textView = rootView.findViewById(R.id.textView);
            //textView.setText("h04n6");

            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            //position + 1 vì position bắt đầu với số 0
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            //số lượng fragment được tạo ra
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}

