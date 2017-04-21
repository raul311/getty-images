package com.images.vicenteruizsalcido.gettyimages;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;

public class MainActivity extends Activity implements MainFragment.OnFragmentInteractionListener {

    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mainFragment = new MainFragment();
        fragmentTransaction.add(R.id.fragment_container, mainFragment);
        fragmentTransaction.commit();
    }

    public void onFragmentInteraction(Uri uri) {}

    public void onClickBtn(View v) {
        mainFragment.performSearch();
    }
}
