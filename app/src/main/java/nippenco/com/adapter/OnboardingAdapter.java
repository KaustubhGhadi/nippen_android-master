package nippenco.com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import nippenco.com.fragment.onboarding.OnboardingFragment1;
import nippenco.com.fragment.onboarding.OnboardingFragment2;
import nippenco.com.fragment.onboarding.OnboardingFragment3;

/**
 * Created by aishwarydhare on 30/01/18.
 */

public class OnboardingAdapter extends FragmentStatePagerAdapter {

    public OnboardingAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new OnboardingFragment1();
            case 1:
                return new OnboardingFragment2();
            case 2:
                return new OnboardingFragment3();
            default:
                return new OnboardingFragment1();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


}
