package com.ist.cadillacpaltform.UI.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ist.cadillacpaltform.R;


public class PrevAndNextFragment extends Fragment {
    protected View mRoot;
    private TextView mTvPrevQuestion;
    private TextView mTvNextQuestion;

    private int questionIndex = 0;

    public interface OnNextClickListener {
        public boolean onNextClick();//返回值代表是否有产生页面向前或者向后翻
    }

    public interface OnPrevClickListener {
        public boolean onPrevClick();
    }

    public PrevAndNextFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_prev_and_next, container, false);
        mTvPrevQuestion = (TextView) mRoot.findViewById(R.id.tv_prev_question);
        mTvNextQuestion = (TextView) mRoot.findViewById(R.id.tv_next_question);
        return mRoot;
    }

    public void setOnNextClickListener(final OnNextClickListener onNextClick) {
        mTvNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onNextClick.onNextClick()){
                    questionIndex++;
                    setPrevAndNextColor();
                }
            }
        });
    }

    public void setOnPrevClickListener(final OnPrevClickListener onPrevClick) {
        mTvPrevQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onPrevClick.onPrevClick()){
                    questionIndex--;
                    setPrevAndNextColor();
                }
            }
        });
    }

    public void setQuestionIndex(int index) {
        questionIndex = index;
        setPrevAndNextColor();
    }

    public void setClickable(boolean flag){
        mTvPrevQuestion.setClickable(flag);
        mTvNextQuestion.setClickable(flag);
    }

    private void setPrevAndNextColor() {
        if (questionIndex <= 0) {
            mTvPrevQuestion.setTextColor(getResources().getColor(R.color.colorGreyText));
            mTvPrevQuestion.setClickable(false);
        } else {
            mTvPrevQuestion.setTextColor(getResources().getColor(R.color.colorRedText));
            mTvPrevQuestion.setClickable(true);
        }
//        if (questionIndex >= abstractGradeDetailList.size() - 1) {
//            //mTvNextQuestion.setTextColor(getResources().getColor(R.color.colorGreyText));
//        } else {
//            //mTvNextQuestion.setTextColor(getResources().getColor(R.color.colorRedText));
//        }
    }

}
