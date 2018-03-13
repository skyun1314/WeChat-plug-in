package me.iweizi.stepchanger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import me.iweizi.stepchanger.alipay.AlipayFragment;
import me.iweizi.stepchanger.qq.QQFragment;
import me.iweizi.stepchanger.utils.StepCounterSensorListener;
import me.iweizi.stepchanger.wechat.WechatFragment;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private BottomNavigationView mBottomNavigationView;
    private ViewPager mViewPager;
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mViewPager.setCurrentItem(item.getOrder());
            return true;
        }

    };
    private ArrayList<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StepCounterSensorListener.get(this).register();
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // mFragments.add顺序需要和res/menu/navigation.xml中的orderInCategory 0,1保持一致
        mFragments = new ArrayList<>();
        mFragments.add(WechatFragment.newInstance());
        mFragments.add(QQFragment.newInstance());
        mFragments.add(AlipayFragment.newInstance());

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_help:
                Intent intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mBottomNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onDestroy() {
        StepCounterSensorListener.get(this).unregister();
        super.onDestroy();
    }
}
