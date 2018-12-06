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
import android.widget.Toast;
import com.example.changjameslee.worldmovie.R;

public class MovieDetailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View v= inflater.inflate(R.layout.movie_detail_fragment, container, false);
        Button branch =v.findViewById(R.id.bbtn_branch);
        branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction1  = getFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.frameLayout_homeScreenFrame,new BranchFragmentMovie());
                fragmentTransaction1.addToBackStack(null);


                fragmentTransaction1.commit();
            }
        });
      return v;
    }
}
