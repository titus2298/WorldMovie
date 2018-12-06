package com.example.changjameslee.worldmovie.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.changjameslee.worldmovie.R;

public class MovieSched extends Fragment {
    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View val= inflater.inflate(R.layout.fragment_movie_sched_branch, container, false);
        Button cinema1 =val.findViewById(R.id.cine1);
        cinema1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction1  = getFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.frameLayout_homeScreenFrame,new SeatFragment());
                fragmentTransaction1.addToBackStack(null);


                fragmentTransaction1.commit();
            }
        });
    return val;
    }

}
