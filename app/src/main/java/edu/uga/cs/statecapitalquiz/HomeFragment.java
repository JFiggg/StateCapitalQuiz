package edu.uga.cs.statecapitalquiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private Button startButton;
    private Button reviewButton;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startButton = getView().findViewById(R.id.start_button);
        reviewButton = getView().findViewById(R.id.review_button);
        startButton.setOnClickListener( new startButtonClick() );
        reviewButton.setOnClickListener( new reviewButtonClick() );
    }

    public class startButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            QuizFragment quizFragment = new QuizFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, quizFragment);
            transaction.addToBackStack("main screen");
            transaction.commit();
        }
    }

    public class reviewButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ResultsFragment quizFragment = new ResultsFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, quizFragment);
            transaction.addToBackStack("main screen");
            transaction.commit();
        }
    }

}