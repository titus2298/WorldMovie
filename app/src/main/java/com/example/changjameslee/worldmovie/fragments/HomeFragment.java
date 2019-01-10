package com.example.changjameslee.worldmovie.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.example.changjameslee.worldmovie.R;

public class HomeFragment extends Fragment {

ViewFlipper viewFlipper;
    private BottomNavigationView.OnNavigationItemSelectedListener tabListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId())
            {

                case R.id.nav_nowshowing:

                    selectedFragment = new NowPlayingFragment();
                    break;
                case R.id.nav_comingSoon:

                    selectedFragment = new ComingSoonFragment();
                    break;
                case R.id.nav_advance:

                    selectedFragment = new AdvanceTicketFragment();
                    break;
            }

            getFragmentManager().beginTransaction().replace(R.id.tab_navigation_frame, selectedFragment).commit();

            return true;
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_home, container, false);
    /*    ImageView image1= (ImageView)v.findViewById(R.id.image1);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction1  = getFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.frameLayout_homeScreenFrame,new MovieDetailFragment());

                fragmentTransaction1.addToBackStack(null);

                Toast.makeText(getActivity(),"success", Toast.LENGTH_SHORT).show();
                fragmentTransaction1.commit();
            }
        });*/

    //Imageflipper

        int images[]={R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4,R.drawable.img5,R.drawable.saying};
        viewFlipper = v.findViewById(R.id.flipper_image);

        for (int image: images)
        {
            flipperImage(image);
        }



        //for tab navigation
        BottomNavigationView tabNav = v.findViewById(R.id.tab_navigation);
        tabNav.setOnNavigationItemSelectedListener(tabListener);

//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.tab_navigation_frame,fragmentManager).commit();
        getFragmentManager().beginTransaction().replace(R.id.tab_navigation_frame, new NowPlayingFragment()).commit();



        return v;
    }
    public void flipperImage(int image)
    {

        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);


        viewFlipper.setInAnimation(getContext(),android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getContext(),android.R.anim.slide_out_right);

    }
}
