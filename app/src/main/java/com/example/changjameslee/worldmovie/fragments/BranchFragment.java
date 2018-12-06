package com.example.changjameslee.worldmovie.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.changjameslee.worldmovie.HomeActivity;
import com.example.changjameslee.worldmovie.R;

public class BranchFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch, container, false);
        TextView angoText = (TextView)view.findViewById(R.id.angono);

        angoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction1  = getFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.frameLayout_homeScreenFrame,new MovieDetailFragment());
                fragmentTransaction1.addToBackStack(null);

                fragmentTransaction1.commit();

            }
        });


        return view;
    }



}
