package com.fkzhang.xposed.a;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Debug;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.fkzhang.wechatxposed.R;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class b {

    public interface c {
        void ʻ();

        void ʻ(Object obj);
    }

    public interface bbbb {
        void ʻ();

        void ʼ();
    }

    public static class a {
        public Menu ʻ;
        public ArrayAdapter<String> ʼ;

        public void ʻ() {
            this.ʼ.notifyDataSetChanged();
        }
    }

    public static int ʻ(int i) {
        return (int) (((float) i) * Resources.getSystem().getDisplayMetrics().density);
    }

    public static Menu ʻ(Activity activity, LinearLayout linearLayout, String str) {
        Toolbar toolbar = new Toolbar(activity);
        toolbar.setLayoutParams(new Toolbar.LayoutParams(-1, -2));
        toolbar.setBackgroundColor(Color.parseColor("#2196F3"));
        if (!TextUtils.isEmpty(str)) {
            toolbar.setTitle((CharSequence) str);
            toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        }
        ʻ(linearLayout, toolbar);
        return toolbar.getMenu();
    }

    @SuppressLint("WrongConstant")
    public static a ʻ(final Activity activity, String str, final List<String> list, final boolean z, boolean z2, final c cVar) {
        try {
            Builder builder = new Builder(activity);
            int ʻ = ʻ(15);
            LinearLayout linearLayout = new LinearLayout(activity);
            LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
            linearLayout.setGravity(48);
            linearLayout.setOrientation(1);
            linearLayout.setFocusableInTouchMode(true);
            linearLayout.setLayoutParams(layoutParams);
            Menu ʻ2 = ʻ(activity, linearLayout, str);
            LinearLayout linearLayout2 = new LinearLayout(activity);
            linearLayout2.setGravity(48);
            linearLayout2.setOrientation(1);
            linearLayout2.setPadding(ʻ, ʻ, ʻ, ʻ);
            linearLayout2.setFocusableInTouchMode(true);
            linearLayout2.setLayoutParams(layoutParams);
            linearLayout.addView(linearLayout2);
            ListView listView = new ListView(activity);
            @SuppressLint("ResourceType") final ArrayAdapter arrayAdapter = new ArrayAdapter(activity, 17367043, 16908308, list);
            listView.setAdapter(arrayAdapter);
            linearLayout2.addView(listView, new LinearLayout.LayoutParams(-1, -2));
            builder.setView(linearLayout);
            @SuppressLint("ResourceType") final AlertDialog create = builder.setPositiveButton(17039370, new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (cVar != null) {
                        cVar.ʻ();
                    }
                }
            }).setNegativeButton(17039360, new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }).create();
            listView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    if (z) {
                        create.dismiss();
                    }
                    try {
                        if (cVar != null) {
                            cVar.ʻ(list.get(i));
                        }
                    } catch (Throwable th) {
                        com.fkzhang.xposed.a.b.ʻ(th);
                    }
                }
            });
            if (z2) {
                listView.setOnItemLongClickListener(new OnItemLongClickListener() {
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long j) {





                        com.fkzhang.xposed.a.b.ʻ(activity, activity.getString(R.string.delete), activity.getString(R.string.delete_confirmation), new bbbb() {


                            public void ʻ() {
                                list.remove(i);
                                arrayAdapter.notifyDataSetChanged();
                            }

                            public void ʼ() {
                            }
                        });
                        return true;


                    }
                });
            }
            create.show();
            a aVar = new a();
            aVar.ʻ = ʻ2;
            aVar.ʼ = arrayAdapter;
            return aVar;
        } catch (Throwable th) {
            ʻ(th);
            return null;
        }
    }

    @SuppressLint("WrongConstant")
    public static String ʻ(Context context, int i) {
        if (context == null) {
            return "";
        }
        for (RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == i) {
                return runningAppProcessInfo.processName;
            }
        }
        return "";
    }

    private static String ʻ(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        char charAt = str.charAt(0);
        return !Character.isUpperCase(charAt) ? Character.toUpperCase(charAt) + str.substring(1) : str;
    }

    public static ArrayList<String> ʻ(Activity activity) {
        List<PackageInfo> installedPackages = activity.getPackageManager().getInstalledPackages(0);
        ArrayList<String> arrayList = new ArrayList();
        for (PackageInfo packageInfo : installedPackages) {
            String str = packageInfo.packageName;
            if (!(str.contains("fkzhang") || str.contains("xposed") || str.contains("com.android") || str.contains("com.google") || str.contains("pro.burgerz.wsm.manager"))) {
                arrayList.add(((String) packageInfo.applicationInfo.loadLabel(activity.getPackageManager())) + "\n" + str);
            }
        }
        return arrayList;
    }

    public static void ʻ(Activity activity, Uri uri) {
        try {
            activity.startActivity(new Intent("android.intent.action.VIEW", uri));
        } catch (Throwable th) {
            ʻ(th);
        }
    }

    @SuppressLint({"WrongConstant", "ResourceType"})
    public static void ʻ(Activity activity, final e eVar, final f fVar) {
        boolean z = true;
        int ʻ = ʻ(15);
        LinearLayout linearLayout = new LinearLayout(activity);
        LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        linearLayout.setGravity(48);
        linearLayout.setOrientation(1);
        linearLayout.setFocusableInTouchMode(true);
        linearLayout.setLayoutParams(layoutParams);
        ʻ(activity, linearLayout, activity.getString(R.string.action_reset));
        LinearLayout linearLayout2 = new LinearLayout(activity);
        linearLayout2.setGravity(48);
        linearLayout2.setOrientation(1);
        linearLayout2.setPadding(ʻ, ʻ, ʻ, ʻ);
        linearLayout2.setFocusableInTouchMode(true);
        linearLayout2.setLayoutParams(layoutParams);
        linearLayout.addView(linearLayout2);
        Builder builder = new Builder(activity);
        builder.setView(linearLayout);
        if ((fVar == null || !fVar.ʻ("isreset", false)) && (eVar == null || !eVar.ʻ("isreset", false))) {
            z = false;
        }
        Switch view = new Switch(activity);
        view.setText(activity.getString(R.string.action_reset));
        view.setChecked(z);
        view.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (eVar != null) {
                    eVar.ʼ("isreset", z);
                }
                if (fVar != null) {
                    fVar.ʼ("isreset", z);
                }
            }
        });
        ʻ(linearLayout2, view, ʻ);
        TextView textView = new TextView(activity);
        textView.setText(activity.getString(R.string.action_reset_desc));
        ʻ(linearLayout2, textView, ʻ);
        builder.setPositiveButton(17039379, new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setNegativeButton(17039369, new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).create().show();
    }

    public static void ʻ(Activity activity, String str, int i) {
        if (!TextUtils.isEmpty(str)) {
            Toast.makeText(activity, str, i).show();
        }
    }

    @SuppressLint("ResourceType")
    public static void ʻ(Activity activity, String str, String str2, final bbbb bVar) {
        int ʻ = ʻ(15);
        LinearLayout linearLayout = new LinearLayout(activity);
        LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        linearLayout.setGravity(48);
        linearLayout.setOrientation(1);
        linearLayout.setFocusableInTouchMode(true);
        linearLayout.setLayoutParams(layoutParams);
        ʻ(activity, linearLayout, str);
        LinearLayout linearLayout2 = new LinearLayout(activity);
        linearLayout2.setGravity(48);
        linearLayout2.setOrientation(1);
        linearLayout2.setPadding(ʻ, ʻ, ʻ, ʻ);
        linearLayout2.setFocusableInTouchMode(true);
        linearLayout2.setLayoutParams(layoutParams);
        linearLayout.addView(linearLayout2);
        Builder builder = new Builder(activity);
        builder.setView(linearLayout);
        TextView textView = new TextView(activity);
        textView.setText(str2);
        textView.setTextSize(30.0f);
        ʻ(linearLayout2, textView, ʻ);
        builder.setPositiveButton(17039379, new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (bVar != null) {
                    bVar.ʻ();
                }
            }
        }).setNegativeButton(17039369, new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if (bVar != null) {
                    bVar.ʼ();
                }
            }
        }).create().show();
    }

    @SuppressLint("WrongConstant")
    public static void ʻ(final Activity activity, boolean z) {
        TextView textView = (TextView) activity.findViewById(R.id.xposed_warning);
        textView.setVisibility(8);
        if (!(z || ʻ((Context) activity, "de.robv.android.xposed.installer") || ʻ((Context) activity, "pro.burgerz.wsm.manager"))) {
            textView.setVisibility(0);
            textView.setText(activity.getString(R.string.xposed_warning));
            textView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (Locale.getDefault().getLanguage().equals("zh")) {
                        b.ʻ(activity, Uri.parse("http://www.baidu.com/s?wd=" + b.ʼ() + " 安装xposed框架"));
                    } else {
                        b.ʻ(activity, Uri.parse("http://www.google.com/search?q=" + b.ʼ() + " install xposed framework"));
                    }
                }
            });
        }
        ((TextView) activity.findViewById(R.id.textView0)).setText(Html.fromHtml(activity.getString(R.string.support)));
    }

    public static void ʻ(LinearLayout linearLayout, View view) {
        ʻ(linearLayout, view, 0);
    }

    public static void ʻ(LinearLayout linearLayout, View view, int i) {
        view.setPadding(0, 0, 0, i);
        linearLayout.addView(view, new LinearLayout.LayoutParams(-1, -2));
    }

    public static void ʻ(File file) {
        if (file != null) {
            try {
                if (file.exists()) {
                    if (file.isDirectory()) {
                        File[] listFiles = file.listFiles();
                        if (!(listFiles == null || listFiles.length == 0)) {
                            for (File ʻ : listFiles) {
                                ʻ(ʻ);
                            }
                        }
                    }
                    file.delete();
                }
            } catch (Throwable th) {
                ʻ(th);
            }
        }
    }

    public static void ʻ(Throwable th) {
        th.printStackTrace();
        Log.d("Xposed", th.toString());
    }

    public static boolean ʻ(Context context) {
        return Debug.isDebuggerConnected() || (context.getApplicationInfo().flags & 2) != 0;
    }

    @SuppressLint("WrongConstant")
    public static boolean ʻ(Context context, String str) {
        try {
            return context.createPackageContext(str, 2) != null;
        } catch (Throwable th) {
            return false;
        }
    }

    private static String ʼ() {
        String str = Build.MANUFACTURER;
        String str2 = Build.MODEL;
        return str2.startsWith(str) ? ʻ(str2) : ʻ(str) + " " + str2;
    }
}
