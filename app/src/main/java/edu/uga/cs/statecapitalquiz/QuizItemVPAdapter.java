package edu.uga.cs.statecapitalquiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuizItemVPAdapter extends RecyclerView.Adapter<QuizItemVPAdapter.ViewHolder> {

    ArrayList<QuizItemViewPager> quizItemViewPagerArrayList;

    public QuizItemVPAdapter(ArrayList<QuizItemViewPager> quizItemViewPagerArrayList) {
        this.quizItemViewPagerArrayList = quizItemViewPagerArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_item_view_pager, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuizItemViewPager quizItemViewPager = quizItemViewPagerArrayList.get(position);
        holder.questionText.setText((CharSequence) quizItemViewPager.questionText);
        holder.radioButton1.setText((CharSequence) quizItemViewPager.radioButton1);
        holder.radioButton2.setText((CharSequence) quizItemViewPager.radioButton2);
        holder.radioButton3.setText((CharSequence) quizItemViewPager.radioButton3);
    }

    @Override
    public int getItemCount() {
        return quizItemViewPagerArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView questionText;
        RadioButton radioButton1;
        RadioButton radioButton2;
        RadioButton radioButton3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.questionText);
            radioButton1 = itemView.findViewById(R.id.radioButton);
            radioButton2 = itemView.findViewById(R.id.radioButton2);
            radioButton3 = itemView.findViewById(R.id.radioButton3);
        }
    }
}
