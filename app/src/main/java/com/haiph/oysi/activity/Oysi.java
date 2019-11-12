package com.haiph.oysi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.haiph.oysi.R;
import com.haiph.oysi.fragment.FragmentCountryInTheWorld;
import com.haiph.oysi.fragment.FragmentRanking;
import com.haiph.oysi.fragment.FragmentMyCountry;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.haiph.oysi",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        tabLayout.saveState(outState);
        super.onSaveInstanceState(outState);
    }
}
