package com.example.changjameslee.worldmovie;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.changjameslee.worldmovie.fragments.BranchFragment;
import com.example.changjameslee.worldmovie.fragments.HomeFragment;
import com.example.changjameslee.worldmovie.fragments.ProfileFragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mtoggle;

    private TextView mTextMessage;


    GoogleSignInClient mGoogleSignInClient;

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });
    }

    private void selectItemDrawer(MenuItem itemId) {

        //creating fragment object
        Class fragmentClass;
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId.getItemId()) {
            case R.id.nav_home:
                getSupportActionBar().setTitle("Home");
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_prof:
                getSupportActionBar().setTitle("Profile");
                fragmentClass = ProfileFragment.class;
                break;
            case R.id.nav_branch:
                getSupportActionBar().setTitle("Branch");
                fragmentClass = BranchFragment.class;
                break;
            case R.id.nav_logOut:

                  mGoogleSignInClient.signOut();
                  mGoogleSignInClient.revokeAccess();
                  Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                  startActivity(intent);
                  finish();
                  fragmentClass = LoginActivity.class;
                Toast.makeText(getApplicationContext(), "Successfully Signed Out!",Toast.LENGTH_LONG).show();


                break;
            default:
                fragmentClass = HomeActivity.class;
        }
        try{
            fragment=(Fragment) fragmentClass.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout_homeScreenFrame,fragment).commit();
        itemId.setChecked(true);
        setTitle(itemId.getTitle());
        mDrawerlayout.closeDrawers();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId())
                    {

                        case R.id.navigation_home:
                            getSupportActionBar().setTitle("Home");
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.navigation_profile:

                            getSupportActionBar().setTitle("Profile");

                            selectedFragment = new ProfileFragment();
                            break;
                        case R.id.navigation_branch:
                            getSupportActionBar().setTitle("Profile");
                           selectedFragment = new BranchFragment();

                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_homeScreenFrame, selectedFragment).commit();

                    return true;
                }
            };
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void profileInfo()
    {
        String personName="";
        String personGivenName;
        String personFamilyName;
        String personEmail="";
        String personId;
        Uri personPhoto= null;

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            personName = acct.getDisplayName();
            personGivenName = acct.getGivenName();
            personFamilyName = acct.getFamilyName();
            personEmail = acct.getEmail();
            personId = acct.getId();
            personPhoto = acct.getPhotoUrl();

        }

        NavigationView navigationView =  findViewById(R.id.navigationSideBar);

        View headerView = navigationView.getHeaderView(0);
        ImageView drawerImage =  headerView.findViewById(R.id.profileInfo_Image);
        TextView drawerUsername =  headerView.findViewById(R.id.profileInfo_Name);
        TextView drawerAccount =headerView.findViewById(R.id.profileInfo_Email);

        Picasso.get().load(personPhoto.toString()).error(R.drawable.venom).into(drawerImage);
        drawerUsername.setText(personName);
        drawerAccount.setText(personEmail);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        profileInfo();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mDrawerlayout = (DrawerLayout) findViewById(R.id.container);
        mtoggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mtoggle);
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.navigationSideBar);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTextMessage = (TextView) findViewById(R.id.message);


        //BOTTOMNAVIGATION
        BottomNavigationView bottomNav= findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_homeScreenFrame, new HomeFragment()).commit();
        getSupportActionBar().setTitle("Home");
        setupDrawerContent(nvDrawer);


    }
}
