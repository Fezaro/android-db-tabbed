package com.example.tabbedviewtut;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tabbedviewtut.fragments.DetailsFragment;
import com.example.tabbedviewtut.fragments.DisplayFragment;
import com.example.tabbedviewtut.fragments.UnitsFragment;

public class StudentViewPagerAdapter extends FragmentStateAdapter {

    public StudentViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {

            case 1:
                return new   UnitsFragment();
            case 2:
                return new   DisplayFragment();
            default:
                return new  DetailsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
