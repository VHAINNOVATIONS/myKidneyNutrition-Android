package com.topcoder.vakidney.MainFragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.topcoder.vakidney.Adapter.ViewPagerAdapter;
import com.topcoder.vakidney.MainFragments.MedicationFragments.DrugFragment;
import com.topcoder.vakidney.MainFragments.MedicationFragments.NutritionFragment;
import com.topcoder.vakidney.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicationFragment extends Fragment {


    private TabLayout tabLayout;
    private ViewPager viewPager;

    public MedicationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_medication, container, false);
        viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    /**
     * Populates the viewpager
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new NutritionFragment(), "Nutrition");
        adapter.addFragment(new DrugFragment(), "Drug Interactions");
        viewPager.setAdapter(adapter);
    }


}
