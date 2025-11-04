package edu.uga.cs.statecapitalquiz;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.uga.cs.statecapitalquiz.placeholder.PlaceholderContent;

/**
 * A fragment representing a list of Items.
 */
public class ResultsDataFragment extends Fragment {

    private RecyclerView recyclerView;
    private QuizRecyclerViewAdapter adapter;
    private TextView emptyMessageTextView;
    private ArrayList<Quiz> quizzesArrayList;
    private QuizDBHelper dbHelper;

    public ResultsDataFragment() {
        // Required empty public constructor
    }

    @SuppressWarnings("unused")
    public static ResultsDataFragment newInstance(int columnCount) {
        return new ResultsDataFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results_data_list, container, false);
        emptyMessageTextView = view.findViewById(R.id.empty_message_text);
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.dbHelper = QuizDBHelper.getInstance(getContext());
        this.quizzesArrayList = new ArrayList<>();
        this.adapter = new QuizRecyclerViewAdapter(quizzesArrayList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshQuizList();
    }

    private void refreshQuizList() {
        if (quizzesArrayList == null || dbHelper == null || adapter == null) {
            // This check prevents crashes if something went wrong during creation
            Log.e("ResultsDataFragment", "Fragment not fully initialized, skipping refresh.");
            return;
        }

        Log.d("ResultsDataFragment", "Refreshing quiz list...");
        quizzesArrayList.clear();
        quizzesArrayList.addAll(dbHelper.getAllQuizzes());
        adapter.notifyDataSetChanged();

        if (quizzesArrayList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyMessageTextView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyMessageTextView.setVisibility(View.GONE);
        }

        Log.d("ResultsDataFragment", "Loaded " + quizzesArrayList.size() + " quizzes from DB");
    }
}