package edu.uga.cs.statecapitalquiz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment {

    private ViewPager2 viewPager;
    ArrayList<QuizItemViewPager> quizItemViewPagerArrayList;


    public QuizFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment QuizFragment.
     */
    public static QuizFragment newInstance(String param1, String param2) {
        QuizFragment fragment = new QuizFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = getView().findViewById(R.id.viewPager);
        ArrayList<Integer> randomIds = getRandomIds();
        ArrayList<String> randomStates = getStates(randomIds);
        ArrayList<ArrayList<String>> capitalAnswers = getCapitals(randomIds);

        quizItemViewPagerArrayList = new ArrayList<>();

        for (int i = 0; i < randomIds.size(); i++) {
            Log.d("DEBUG", "randomStates: " + randomStates.get(i));
            QuizItemViewPager quizItemViewPager = new QuizItemViewPager(i+1, randomStates.get(i), capitalAnswers.get(i).get(0), capitalAnswers.get(i).get(1), capitalAnswers.get(i).get(2) );
            quizItemViewPagerArrayList.add(quizItemViewPager);
        }


        QuizItemVPAdapter quizItemVPAdapter = new QuizItemVPAdapter(quizItemViewPagerArrayList);
        viewPager.setAdapter(quizItemVPAdapter);
        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(2);
        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);



    }

    private ArrayList<Integer> getRandomIds() {
        ArrayList<Integer> randomIds = new ArrayList<>();
        while (randomIds.size() != 6) {
            int numToAdd = (int) Math.floor(Math.random() * 50);
            if (randomIds.contains(numToAdd)){
                continue;
            } else {
                randomIds.add(numToAdd);
            }
        }
        return randomIds;
    }

    private ArrayList<String> getStates(ArrayList<Integer> randomIds) {
        ArrayList<String> randomStates = new ArrayList<>();

        StringBuilder ids = new StringBuilder();
        for (int i = 0; i < randomIds.size(); i++) {
            ids.append(randomIds.get(i));
            if (i < randomIds.size() - 1) ids.append(",");
        }

        SQLiteDatabase db = QuizDBHelper.getInstance(getContext()).getReadableDatabase();
        String query = "SELECT state FROM " + QuizDBHelper.TABLE_QUESTIONS +
                " WHERE " + QuizDBHelper.QUESTIONS_COLUMN_ID + " IN (" + ids + ")";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                randomStates.add(cursor.getString(cursor.getColumnIndexOrThrow("state")));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return randomStates;
    }

    private ArrayList<ArrayList<String>> getCapitals(ArrayList<Integer> randomIds) {
        ArrayList<ArrayList<String>> capitalAnswers = new ArrayList<>();

        StringBuilder ids = new StringBuilder();
        for (int i = 0; i < randomIds.size(); i++) {
            ids.append(randomIds.get(i));
            if (i < randomIds.size() - 1) ids.append(",");
        }

        SQLiteDatabase db = QuizDBHelper.getInstance(getContext()).getReadableDatabase();
        String query = "SELECT capital, wrong_capital1, wrong_capital2 FROM " + QuizDBHelper.TABLE_QUESTIONS +
                " WHERE " + QuizDBHelper.QUESTIONS_COLUMN_ID + " IN (" + ids + ")";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                ArrayList<String> capitals = new ArrayList<>();
                capitals.add(cursor.getString(cursor.getColumnIndexOrThrow("capital")));
                capitals.add(cursor.getString(cursor.getColumnIndexOrThrow("wrong_capital1")));
                capitals.add(cursor.getString(cursor.getColumnIndexOrThrow("wrong_capital2")));
                Collections.shuffle(capitals);
                capitalAnswers.add(capitals);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return capitalAnswers;
    }
}