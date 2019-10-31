package com.haiph.oysi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.haiph.oysi.R;
import com.haiph.oysi.fragment.FragmentCountryInTheWorld;
import com.haiph.oysi.fragment.FragmentRanking;
import com.haiph.oysi.fragment.FragmentMyCountry;

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
        fragmentList.add(new FragmentCountryInTheWorld());
        fragmentList.add(new FragmentMyCountry());
        fragmentList.add(new FragmentRanking());

        viewPager = findViewById(R.id.viewPager);
        tabLayout =  findViewById(R.id.spaceTabLayout);
        tabLayout.initialize(viewPager,getSupportFragmentManager(),fragmentList,savedInstanceState);
        tabLayout.setTabTwoText("Map");
        tabLayout.setTabTwoIcon(R.drawable.locationcountry);
        tabLayout.setTabOneText("List");
        tabLayout.setTabOneIcon(R.drawable.listcountry);
        tabLayout.setTabThreeText("Ranking");
        tabLayout.setTabThreeIcon(R.drawable.list_bottombar);



    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        tabLayout.saveState(outState);
        super.onSaveInstanceState(outState);
    }
}
