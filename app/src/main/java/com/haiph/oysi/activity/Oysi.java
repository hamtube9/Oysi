package com.haiph.oysi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.haiph.oysi.R;
import com.haiph.oysi.fragment.FragmentMain;
import com.haiph.oysi.fragment.fragmentMyCountry;
import com.haiph.oysi.fragment.fragmentWorldQuality;

import java.util.ArrayList;
import java.util.List;

import eu.long1.spacetablayout.SpaceTabLayout;

public class Oysi extends AppCompatActivity {
    SpaceTabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oysi);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentMain());
        fragmentList.add(new fragmentWorldQuality());
        fragmentList.add(new fragmentMyCountry());
        viewPager = findViewById(R.id.viewPager);
        tabLayout =  findViewById(R.id.spaceTabLayout);
        tabLayout.initialize(viewPager,getSupportFragmentManager(),fragmentList,savedInstanceState);
        tabLayout.setTabTwoIcon(R.drawable.location_bottombar);
        tabLayout.setTabTwoText("Map");
        tabLayout.setTabOneIcon(R.drawable.list_bottombar);
        tabLayout.setTabOneText("List");
        tabLayout.setTabThreeText("Location");
        tabLayout.setTabThreeIcon(R.drawable.locationcountry);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        tabLayout.saveState(outState);
        super.onSaveInstanceState(outState);
    }
}
