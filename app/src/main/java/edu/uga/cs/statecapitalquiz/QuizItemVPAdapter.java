package edu.uga.cs.statecapitalquiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuizItemVPAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<QuizItemViewPager> quizItemViewPagerArrayList;
    private static final int TYPE_QUIZ = 0;
    private static final int TYPE_SCORE = 1;
    FragmentManager fragmentManager;

    public QuizItemVPAdapter(ArrayList<QuizItemViewPager> quizItemViewPagerArrayList, FragmentManager fragmentManager) {
        this.quizItemViewPagerArrayList = quizItemViewPagerArrayList;
        this.fragmentManager = fragmentManager;
    }

    public int getItemViewType(int position) {
        if (position == quizItemViewPagerArrayList.size() - 1) {
            return TYPE_SCORE;
        }
        return TYPE_QUIZ;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_SCORE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item_view_pager, parent, false);
            return new ScoreViewHolder(view);
        } else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_item_view_pager, parent, false);
            return new QuizViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof QuizViewHolder) {
            QuizItemViewPager quizItemViewPager = quizItemViewPagerArrayList.get(position);

            ((QuizViewHolder) holder).questionNumber.setText(quizItemViewPager.questionNumber + "/6");
            ((QuizViewHolder) holder).questionText.setText(quizItemViewPager.questionText + "?");
            ((QuizViewHolder) holder).radioButton1.setText("A) " + quizItemViewPager.radioButton1);
            ((QuizViewHolder) holder).radioButton2.setText("B) " + quizItemViewPager.radioButton2);
            ((QuizViewHolder) holder).radioButton3.setText("C) " + quizItemViewPager.radioButton3);
        }
        else if (holder instanceof ScoreViewHolder) {
            ((ScoreViewHolder) holder).scoreText.setText(" Your final score: 6/6"); // placeholder
            ((ScoreViewHolder) holder).returnButton.setOnClickListener(v -> {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragmentContainerView, homeFragment);
                transaction.addToBackStack("quiz screen");
                transaction.commit();
            });
        }
    }

    @Override
    public int getItemCount() {
        return quizItemViewPagerArrayList.size();
    }

    public class QuizViewHolder extends RecyclerView.ViewHolder {
        TextView questionNumber;
        TextView questionText;
        RadioButton radioButton1;
        RadioButton radioButton2;
        RadioButton radioButton3;

        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);
            questionNumber = itemView.findViewById(R.id.questionNum);
            questionText = itemView.findViewById(R.id.questionText);
            radioButton1 = itemView.findViewById(R.id.radioButton);
            radioButton2 = itemView.findViewById(R.id.radioButton2);
            radioButton3 = itemView.findViewById(R.id.radioButton3);
        }
    }

    public static class ScoreViewHolder extends RecyclerView.ViewHolder {
        TextView scoreText;
        Button returnButton;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            scoreText = itemView.findViewById(R.id.scorePercentText);
            returnButton = itemView.findViewById(R.id.returnHomeButton);
        }
    }
}
