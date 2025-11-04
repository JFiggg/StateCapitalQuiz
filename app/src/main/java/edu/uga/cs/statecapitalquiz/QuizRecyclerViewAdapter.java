package edu.uga.cs.statecapitalquiz;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.uga.cs.statecapitalquiz.databinding.FragmentResultsDataBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a list of quizzes including date, time, and grade.
 */
public class QuizRecyclerViewAdapter extends RecyclerView.Adapter<QuizRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Quiz> quizzesArrayList;

    public QuizRecyclerViewAdapter(ArrayList<Quiz> items) {
        quizzesArrayList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentResultsDataBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Quiz quiz = quizzesArrayList.get(position);
        holder.date.setText(quiz.getDate().toString());
        holder.time.setText(quiz.getTime());
        holder.grade.setText(String.valueOf(quiz.getResult()));
    }

    @Override
    public int getItemCount() {
        return quizzesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView time;
        TextView grade;

        public ViewHolder(FragmentResultsDataBinding binding) {
            super(binding.getRoot());
            date = binding.date;
            time = binding.time;
            grade = binding.grade;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + time.getText() + "'";
        }
    }
}