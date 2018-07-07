package nippenco.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import nippenco.com.adapter.OnboardingAdapter;
import nippenco.com.customview.CirclePagerIndicator;

/**
 * Created by aishwarydhare on 03/02/18.
 */

public class OnboardingActivity extends AppCompatActivity{

    ViewPager viewPager;
    CirclePagerIndicator circlePagerIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        viewPager = findViewById(R.id.view_pager);
        circlePagerIndicator = findViewById(R.id.indicator);
        circlePagerIndicator.setViewPager(viewPager);

        OnboardingAdapter onboardingAdapter = new OnboardingAdapter(getSupportFragmentManager());
        onboardingAdapter.registerDataSetObserver(circlePagerIndicator.getDataSetObserver());

        viewPager.setAdapter(onboardingAdapter);

        final Activity temp_act = this;
        findViewById(R.id.get_started_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(temp_act, SignUpActivity.class));
                finish();
            }
        });
    }

}
