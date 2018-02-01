package com.fkzhang.wechatxposed;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.fkzhang.xposed.a.AESUtil;
import com.fkzhang.xposed.a.b;
import com.fkzhang.xposed.a.d;
import com.fkzhang.xposed.a.e;
import com.fkzhang.xposed.a.f;
import com.fkzhang.xposed.a.g;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private MenuItem ˑ;
    private d י;
    private String ـ = com.fkzhang.wechatxposed.a.ᵢ;
    private String ٴ = ("请支持原作者 别用盗版！ ");
    private String ᐧ;
    private Button ᴵ;
    private e ᵎ;
    private f ᵔ;
    private TextView ᵢ;

    private void ʻ(String str, String str2) {
        if (this.ᵎ != null) {
            this.ᵎ.ʼ(str, str2);
        }
        if (this.ᵔ != null) {
            this.ᵔ.ʼ(str, str2);
        }
    }

    private void ʻ(String str, boolean z) {
        if (this.ᵎ != null) {
            this.ᵎ.ʼ(str, z);
        }
        if (this.ᵔ != null) {
            this.ᵔ.ʼ(str, z);
        }
    }

    private String ʼ(String str, String str2) {
        String ʻ = this.ᵎ != null ? this.ᵎ.ʻ(str, str2) : str2;
        return ((TextUtils.isEmpty(ʻ) || ʻ.equals(str2)) && this.ᵔ != null) ? this.ᵔ.ʻ(str, str2) : ʻ;
    }

    @SuppressLint("WrongConstant")
    private void ʼ(boolean z) {
        getPackageManager().setComponentEnabledSetting(ˏ(), z ? 1 : 2, 1);
    }

    private void ˎ() {
        if (ˑ()) {
            this.ˑ.setTitle(R.string.hide_icon);
        } else {
            this.ˑ.setTitle(R.string.show_icon);
        }
    }

    private ComponentName ˏ() {
        return new ComponentName(this, "com.fkzhang.wechatxposed.MainActivityAlias");
    }

    @SuppressLint("WrongConstant")
    private boolean ˑ() {
        return getPackageManager().getComponentEnabledSetting(ˏ()) == 1;
    }

    @SuppressLint("WrongConstant")
    public void onClick(View view) {
        new Intent().setFlags(268435456);
        b.ʻ((Activity) this, getString(R.string.lauching_program), 1);
        switch (view.getId()) {
            case R.id.donate_keycard:
                try {
                    String  ʻ = this.ᵎ.ʻ("keycard_url", "");
                    if (!TextUtils.isEmpty(ʻ)) {
                        b.ʻ((Activity) this, Uri.parse(AESUtil.ʼ(this.י.ʻ(), ʻ)));
                        return;
                    }
                    return;
                } catch (Throwable th) {
                    return;
                }
            case R.id.donate_paypal:
                try {
                    b.ʻ((Activity) this, Uri.parse(AESUtil.ʼ(this.י.ʻ(), com.fkzhang.wechatxposed.a.ˋ)));
                    return;
                } catch (Throwable th2) {
                    ʻ(com.fkzhang.wechatxposed.a.ˆ, true);
                    b.ʻ((Activity) this, this.ٴ, 1);
                    finish();
                    return;
                }
            default:
                return;
        }
    }

    @SuppressLint("WrongConstant")
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main);
     //   setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        try {
            if (VERSION.SDK_INT >= 16 && ContextCompat.checkSelfPermission((Context) this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 0);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        try {
            this.י = new d(this);
            this.י.ʼ(com.fkzhang.wechatxposed.a.ᐧ);
            this.ᵎ = new e(g.ʻ(com.fkzhang.wechatxposed.a.ٴ),com.fkzhang.wechatxposed. a.ˏ, com.fkzhang.wechatxposed.a.ᵢ);
            this.ᵔ = new f(this, "com.fkzhang.wechatxposed");
            this.ᐧ = "com.fkzhang.wechatxposed";
          //  this.ᐧ = AESUtil.ʼ(this.י.ʻ(),com.fkzhang.wechatxposed. a.ˉ);
            this.ـ = AESUtil.ʼ(this.י.ʻ(),com.fkzhang.wechatxposed. a.ʾ);
            this.ٴ = AESUtil.ʼ(this.י.ʻ(), com.fkzhang.wechatxposed.a.ˎ) + this.ـ;
            ((TextView) findViewById(R.id.textView0)).setText(Html.fromHtml(getString(R.string.support)));
            this.ᴵ = (Button) findViewById(R.id.goto_settings);
            this.ᴵ.setVisibility(8);
            this.ᵢ = (TextView) findViewById(R.id.donate_keycard);
            this.ᵢ.setOnClickListener(this);
            if (TextUtils.isEmpty(this.ᵎ.ʻ("keycard_url", ""))) {
                this.ᵢ.setVisibility(8);
            }
            ((TextView) findViewById(R.id.donate_paypal)).setOnClickListener(this);
            b.ʻ((Activity) this, this.ᵎ.ʻ(com.fkzhang.wechatxposed.a.ᴵ, false));
            ((TextView) findViewById(R.id.xposed_warning)).setOnLongClickListener(new OnLongClickListener() {
                final    MainActivity ʻ=MainActivity.this;

               /* {
                    this.ʻ = r1;
                }*/

                public boolean onLongClick(View view) {
                    this.ʻ.ʻ(com.fkzhang.wechatxposed.a.ᴵ, true);
                    return true;
                }
            });
        } catch (Throwable th2) {
            b.ʻ(th2);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.ˑ = menu.findItem(R.id.action_icon);
        ˎ();
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        boolean z = true;
        final ArrayList arrayList;
        final b.a ʻ;
        switch (menuItem.getItemId()) {
            case R.id.action_exclude_process:
                arrayList = new ArrayList();
                if (!TextUtils.isEmpty(ʼ(com.fkzhang.wechatxposed.a.ﾞ, ""))) {
                    arrayList.addAll(Arrays.asList(ʼ(com.fkzhang.wechatxposed.a.ﾞ, "").split(";")));
                }
                ʻ = b.ʻ(this, getString(R.string.action_exclude_process), arrayList, false, true, new b.c() {
                    final /* synthetic */ MainActivity ʼ=MainActivity.this;

                    public void ʻ() {
                        this.ʼ.ʻ(com.fkzhang.wechatxposed.a.ﾞ, TextUtils.join(";", arrayList));
                    }

                    public void ʻ(Object obj) {
                    }
                });
                if (ʻ != null) {
                    ʻ.ʻ.add(R.string.add).setOnMenuItemClickListener(new OnMenuItemClickListener() {
                        final /* synthetic */ MainActivity ʾ=MainActivity.this;

                        public boolean onMenuItemClick(MenuItem menuItem) {
                            b.ʻ(MainActivity.this, this.ʾ.getString(R.string.select_app), b.ʻ(MainActivity.this), true, false, new b.c() {
                               /* final *//* synthetic *//* AnonymousClass3 ʻ;

                                {
                                    this.ʻ = r1;
                                }*/

                                public void ʻ() {
                                }

                                public void ʻ(Object obj) {
                                    arrayList.add(((String) obj).split("\n")[1]);
                                    ʻ.ʻ();
                                }
                            });
                            return true;
                        }
                    });
                    ʻ.ʻ.add(R.string.remove_all).setOnMenuItemClickListener(new OnMenuItemClickListener() {
                       // final /* synthetic */ MainActivity ʽ;

                        public boolean onMenuItemClick(MenuItem menuItem) {
                            arrayList.clear();
                            ʻ.ʻ();
                            return true;
                        }
                    });
                    break;
                }
                break;
            case R.id.action_icon:
                if (ˑ()) {
                    z = false;
                }
                ʼ(z);
                ˎ();
                break;
            case R.id.action_include_packagename:
                arrayList = new ArrayList();
                if (!TextUtils.isEmpty(ʼ(com.fkzhang.wechatxposed.a.ﹳ, ""))) {
                    arrayList.addAll(Arrays.asList(ʼ(com.fkzhang.wechatxposed.a.ﹳ, "").split(";")));
                }
                ʻ = b.ʻ(this, getString(R.string.action_include_packagename), arrayList, false, true, new com.fkzhang.xposed.a.b.c() {
                    final /* synthetic */ MainActivity ʼ=MainActivity.this;

                    public void ʻ() {
                        this.ʼ.ʻ(com.fkzhang.wechatxposed.a.ﹳ, TextUtils.join(";", arrayList));
                    }

                    public void ʻ(Object obj) {
                    }
                });
                if (ʻ != null) {
                    ʻ.ʻ.add(R.string.add).setOnMenuItemClickListener(new OnMenuItemClickListener() {
                        final /* synthetic */ MainActivity ʾ=MainActivity.this;

                        public boolean onMenuItemClick(MenuItem menuItem) {
                            b.ʻ(MainActivity.this, this.ʾ.getString(R.string.select_app), com.fkzhang.xposed.a.b.ʻ(MainActivity.this), true, false, new com.fkzhang.xposed.a.b.c() {
                                /*final *//* synthetic *//* AnonymousClass6 ʻ;

                                {
                                    this.ʻ = r1;
                                }*/

                                public void ʻ() {
                                }

                                public void ʻ(Object obj) {
                                    arrayList.add(((String) obj).split("\n")[1]);
                                    ʻ.ʻ();
                                }
                            });
                            return true;
                        }
                    });
                    ʻ.ʻ.add(R.string.remove_all).setOnMenuItemClickListener(new OnMenuItemClickListener() {
                       // final /* synthetic */ MainActivity ʽ;

                        public boolean onMenuItemClick(MenuItem menuItem) {
                            arrayList.clear();
                            ʻ.ʻ();
                            return true;
                        }
                    });
                    break;
                }
                break;
            case R.id.action_reset:
                b.ʻ((Activity) this, this.ᵎ, this.ᵔ);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    protected void onPause() {
        super.onPause();
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        switch (i) {
            case 0:
                if (iArr.length > 0 && iArr[0] == 0) {
                    if (this.ᵎ == null) {
                        this.ᵎ = new e(g.ʻ(com.fkzhang.wechatxposed.a.ٴ), com.fkzhang.wechatxposed.a.ˏ, com.fkzhang.wechatxposed.a.ᵢ);
                        return;
                    } else {
                        this.ᵎ.ʻ();
                        return;
                    }
                }
                return;
            default:
                return;
        }
    }

    @SuppressLint("WrongConstant")
    protected void onResume() {
        boolean z = true;
        super.onResume();
        if (b.ʻ((Context) this)) {
            finish();
        }
        ʻ(com.fkzhang.wechatxposed.a.ˆ, false);
        ʻ("sig", this.י.ʼ());
        if (this.ᵎ.ʻ("keycard_url")) {
            this.ᵢ.setVisibility(0);
        } else {
            this.ᵢ.setVisibility(8);
        }
        if (this.י.ʻ().equals(this.י.ʼ())) {
            int i;
            String charSequence = ((TextView) findViewById(R.id.textView0)).getText().toString();
            if (!(charSequence.contains("free") || charSequence.contains("免费") || charSequence.contains("免費"))) {
                ʻ(com.fkzhang.wechatxposed.a.ˆ, true);
                b.ʻ((Activity) this, this.ٴ, 1);
                finish();
            }
            try {
                charSequence = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                i = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
                if (!(charSequence.equals("1.18") || i == 23)) {
                    ʻ(com.fkzhang.wechatxposed.a.ˆ, true);
                    b.ʻ((Activity) this, this.ٴ, 1);
                    finish();
                    return;
                }
            } catch (Throwable th) {
                b.ʻ(th);
            }
            if (getApplicationInfo().packageName.equals("com.fkzhang.wechatxposed") && "com.fkzhang.wechatxposed".equals(this.ᐧ)) {
                try {
                    charSequence = AESUtil.ʼ(this.י.ʻ(), com.fkzhang.wechatxposed.a.ʽ);
                    String charSequence2 = getApplicationInfo().loadLabel(getPackageManager()).toString();
                    try {
                        HashSet hashSet = new HashSet();
                        hashSet.addAll(Arrays.asList(AESUtil.ʼ(this.י.ʻ(), com.fkzhang.wechatxposed.a.ʼ).split(",")));
                        if (this.י.ʻ().equals(charSequence) && hashSet.contains(charSequence2)) {
                            b.ʻ((Activity) this, getString(R.string.settings), 1);
                            TextView textView = (TextView) findViewById(R.id.module_active);
                            i = ModuleActiveCheck.isActiveVersion();
                            if (i != 23 && i != -1) {
                                textView.setVisibility(0);
                                textView.setText(R.string.module_reboot_need);
                            } else if (i == -1) {
                                textView.setVisibility(0);
                                textView.setText(R.string.module_is_disable);
                            } else {
                                textView.setVisibility(8);
                                this.ᴵ.setVisibility(0);
                                this.ᴵ.setOnClickListener(new OnClickListener() {
                                    final /* synthetic */ MainActivity ʻ=MainActivity.this;

                                   /* {
                                        this.ʻ = r1;
                                    }*/

                                    public void onClick(View view) {
                                        Intent launchIntentForPackage = this.ʻ.getPackageManager().getLaunchIntentForPackage(com.fkzhang.wechatxposed.a.ˉˉ);
                                        launchIntentForPackage.setFlags(268435456);
                                        launchIntentForPackage.putExtra(com.fkzhang.wechatxposed.a.ⁱ, "com.fkzhang.wechatxposed");
                                        try {
                                            this.ʻ.startActivity(launchIntentForPackage);
                                        } catch (Throwable th) {
                                        }
                                    }
                                });
                            }
                            if ((this.ᵔ == null || !this.ᵔ.ʻ("isreset", false)) && (this.ᵎ == null || !this.ᵎ.ʻ("isreset", false))) {
                                z = false;
                            }
                            if (z) {
                                textView.setVisibility(0);
                                textView.setText(R.string.isreset);
                                return;
                            }
                            return;
                        }
                        ʻ(com.fkzhang.wechatxposed.a.ˆ, true);
                        b.ʻ((Activity) this, this.ٴ, 1);
                        finish();
                        return;
                    } catch (Throwable th2) {
                        ʻ(com.fkzhang.wechatxposed.a.ˆ, true);
                        b.ʻ((Activity) this, this.ٴ, 1);
                        finish();
                        return;
                    }
                } catch (Throwable th3) {
                    ʻ(com.fkzhang.wechatxposed.a.ˆ, true);
                    b.ʻ((Activity) this, this.ٴ, 1);
                    finish();
                    return;
                }
            }
            ʻ(com.fkzhang.wechatxposed.a.ˆ, true);
            b.ʻ((Activity) this, this.ٴ, 1);
            finish();
            return;
        }
        ʻ(com.fkzhang.wechatxposed.a.ˆ, true);
        b.ʻ((Activity) this, this.ٴ, 1);
        finish();
    }
}
