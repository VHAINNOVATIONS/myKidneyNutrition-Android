package com.topcoder.vakidney.fragments.home;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.topcoder.vakidney.R;

/**
 * A simple {@link Fragment} subclass. This fragment is second of three fragment used inside
 * HomeFragment. It shows two topics along with their image and description
 */
public class Home2Fragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home2, container, false);
    }

}
