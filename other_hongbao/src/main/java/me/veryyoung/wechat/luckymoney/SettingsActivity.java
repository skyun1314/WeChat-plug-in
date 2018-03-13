package me.veryyoung.wechat.luckymoney;


import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private SettingsFragment mSettingsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if (savedInstanceState == null) {
            mSettingsFragment = new SettingsFragment();
            replaceFragment(R.id.settings_container, mSettingsFragment);
        }

    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void replaceFragment(int viewId, android.app.Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(viewId, fragment).commit();
    }


    /**
     * A placeholder fragment containing a settings view.
     */
    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getPreferenceManager().setSharedPreferencesMode(MODE_WORLD_READABLE);
            addPreferencesFromResource(R.xml.pref_setting);

            Preference reset = findPreference("author");
            reset.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference pref) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.setData(Uri.parse("https://github.com/veryyoung"));
                    startActivity(intent);
                    return true;
                }
            });

            Preference donateAlipay = findPreference("donate_alipay");
            donateAlipay.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference pref) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    String payUrl = "https://qr.alipay.com/apbvye346u4wqkcr9b";
                    intent.setData(Uri.parse("alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=" + payUrl));
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        intent.setData(Uri.parse(payUrl));
                        startActivity(intent);
                    }
                    return true;
                }
            });

            Preference donateWechat = findPreference("donate_wechat");
            donateWechat.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference pref) {
                    Intent intent = new Intent();
                    intent.setClassName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                    intent.putExtra("donate", true);
                    startActivity(intent);
                    return true;
                }
            });

        }
    }
}
