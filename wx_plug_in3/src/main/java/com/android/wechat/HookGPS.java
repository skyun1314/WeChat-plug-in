package com.android.wechat;

import android.content.ContentResolver;
import android.location.Location;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.CellLocation;
import android.telephony.gsm.GsmCellLocation;

import com.amap.api.maps.model.LatLng;
import com.android.wechat.My_XC_MethodHook;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class HookGPS {
    private static HookGPS a;
    private ArrayList<My_XC_MethodHook> b;

    public static synchronized HookGPS a() {
        HookGPS bVar;
        synchronized (HookGPS.class) {
            if (a == null) {
                a = new HookGPS();
            }
            bVar = a;
        }
        return bVar;
    }

    public static Location a(double d, double d2) {
        Location location = new Location("gps");
        location.setLatitude(d);
        location.setLongitude(d2);
        location.setAccuracy(100.0f);
        location.setTime(System.currentTimeMillis());
        if (VERSION.SDK_INT >= 17) {
            location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        }
        return location;
    }

    public static LatLng b() {
        XSharedPreferences xSharedPreferences = new XSharedPreferences(Main.packageNmae, "FAKEMAP");
        LatLng latLng = new LatLng(Double.parseDouble(xSharedPreferences.getString("latitude", "")), Double.parseDouble(xSharedPreferences.getString("longitude", "")));
        XposedBridge.log("read XSharedPreferences latitude:" + latLng.latitude + " longitude: " + latLng.longitude);
        return latLng;
    }

    public void a(XSharedPreferences xSharedPreferences, ClassLoader classLoader) {
        this.b = new ArrayList();
        this.b.add(new m(xSharedPreferences, classLoader, "requestLocationUpdatesHooker"));
        this.b.add(new h(xSharedPreferences, classLoader, "getGpsStatusHooker"));
        if (VERSION.SDK_INT < 24) {
            this.b.add(new d(xSharedPreferences, classLoader, "addGpsStatusListenerHooker"));
        }
        this.b.add(new j(xSharedPreferences, classLoader, "getLastLocationHooker"));
        this.b.add(new i(xSharedPreferences, classLoader, "getLastKnownLocationHooker"));
        this.b.add(new e(xSharedPreferences, classLoader, "addNmeaListenerHooker"));
        this.b.add(new f(xSharedPreferences, classLoader, "callLocationChangedLockedHooker"));
        this.b.add(new g(xSharedPreferences, classLoader, "getBestProviderHooker"));
        this.b.add(new k(xSharedPreferences, classLoader, "getProvidersHooker"));
        this.b.add(new l(xSharedPreferences, classLoader, "isProviderEnabledHooker"));
    }

    public void a(ClassLoader classLoader) {
        Iterator it = this.b.iterator();
        while (it.hasNext()) {
            ((My_XC_MethodHook) it.next()).a();
        }
        XposedHelpers.findAndHookMethod("android.location.Location", classLoader, "hasAccuracy", new Object[]{XC_MethodReplacement.returnConstant(Boolean.valueOf(true))});
        XposedHelpers.findAndHookMethod("android.location.Location", classLoader, "hasAltitude", new Object[]{XC_MethodReplacement.returnConstant(Boolean.valueOf(true))});
        XposedHelpers.findAndHookMethod("android.location.Location", classLoader, "hasBearing", new Object[]{XC_MethodReplacement.returnConstant(Boolean.valueOf(true))});
        XposedHelpers.findAndHookMethod("android.location.Location", classLoader, "hasSpeed", new Object[]{XC_MethodReplacement.returnConstant(Boolean.valueOf(true))});
        XposedHelpers.findAndHookMethod("android.location.Location", classLoader, "getExtras", new Object[]{new XC_MethodHook() {

            protected void afterHookedMethod(MethodHookParam methodHookParam) {
                Bundle bundle = new Bundle();
                bundle.putInt("satellites", 12);
                methodHookParam.setResult(bundle);
            }
        }});
        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", classLoader, "getNetworkOperatorName", new Object[]{XC_MethodReplacement.returnConstant("on")});
        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", classLoader, "getSimOperatorName", new Object[]{XC_MethodReplacement.returnConstant("os")});
        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", classLoader, "getSimOperator", new Object[]{XC_MethodReplacement.returnConstant("os")});
        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", classLoader, "getNetworkOperator", new Object[]{XC_MethodReplacement.returnConstant("on")});
        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", classLoader, "getSimCountryIso", new Object[]{XC_MethodReplacement.returnConstant("oc")});
        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", classLoader, "getNetworkCountryIso", new Object[]{XC_MethodReplacement.returnConstant("oc")});
        if (VERSION.SDK_INT < 23) {
            XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", classLoader, "getNeighboringCellInfo", new Object[]{new XC_MethodHook() {


                protected void beforeHookedMethod(MethodHookParam methodHookParam) {
                    XposedBridge.log("getNeighboringCellInfo  status=on ");
                    methodHookParam.setResult(null);
                }
            }});
        }
        if (VERSION.SDK_INT > 16) {
            XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", classLoader, "getAllCellInfo", new Object[]{new XC_MethodHook() {


                protected void beforeHookedMethod(MethodHookParam methodHookParam) {
                    XposedBridge.log("getAllCellInfo status=on ");
                    methodHookParam.setResult(null);
                }
            }});
            XposedHelpers.findAndHookMethod("android.telephony.PhoneStateListener", classLoader, "onCellInfoChanged", new Object[]{List.class, new XC_MethodHook() {


                protected void beforeHookedMethod(MethodHookParam methodHookParam) {
                    XposedBridge.log("onCellInfoChanged status=on ");
                    methodHookParam.setResult(null);
                }
            }});
        }
        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", classLoader, "getCellLocation", new Object[]{new XC_MethodHook() {


            protected void beforeHookedMethod(MethodHookParam methodHookParam) {
                XposedBridge.log("getCellLocation status=on ");
                methodHookParam.setResult(null);
            }
        }});
        XposedHelpers.findAndHookMethod("android.location.Location", classLoader, "getLatitude", new Object[]{new XC_MethodReplacement() {


            protected Object replaceHookedMethod(MethodHookParam methodHookParam) {
                return Double.valueOf(HookGPS.b().latitude);
            }
        }});
        XposedHelpers.findAndHookMethod("android.location.Location", classLoader, "getLongitude", new Object[]{new XC_MethodReplacement() {


            protected Object replaceHookedMethod(MethodHookParam methodHookParam) {
                return Double.valueOf(HookGPS.b().longitude);
            }
        }});
        XposedHelpers.findAndHookMethod("android.location.Location", classLoader, "getSpeed", new Object[]{XC_MethodReplacement.returnConstant(Float.valueOf(5.0f))});
        XposedHelpers.findAndHookMethod("android.location.Location", classLoader, "getAccuracy", new Object[]{XC_MethodReplacement.returnConstant(Float.valueOf(50.0f))});
        XposedHelpers.findAndHookMethod("android.location.Location", classLoader, "getBearing", new Object[]{XC_MethodReplacement.returnConstant(Float.valueOf(50.0f))});
        XposedHelpers.findAndHookMethod("android.location.Location", classLoader, "getAltitude", new Object[]{XC_MethodReplacement.returnConstant(Double.valueOf(50.0d))});
        XposedHelpers.findAndHookMethod("android.net.wifi.WifiManager", classLoader, "getScanResults", new Object[]{new XC_MethodHook() {


            protected void beforeHookedMethod(MethodHookParam methodHookParam) {
                XposedBridge.log("getScanResults status=on ");
                methodHookParam.setResult(new ArrayList());
            }
        }});
        XposedHelpers.findAndHookMethod("android.net.wifi.WifiInfo", classLoader, "getMacAddress", new Object[]{XC_MethodReplacement.returnConstant("00-00-00-00-00-00-00-E0")});
        XposedHelpers.findAndHookMethod("android.net.wifi.WifiInfo", classLoader, "getSSID", new Object[]{XC_MethodReplacement.returnConstant("null")});
        XposedHelpers.findAndHookMethod("android.net.wifi.WifiInfo", classLoader, "getBSSID", new Object[]{XC_MethodReplacement.returnConstant("00:00:00:00:00:00")});
        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", classLoader, "getNetworkType", new Object[]{XC_MethodReplacement.returnConstant(Integer.valueOf(1))});
        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", classLoader, "getPhoneType", new Object[]{XC_MethodReplacement.returnConstant(Integer.valueOf(0))});
        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", classLoader, "getCurrentPhoneType", new Object[]{XC_MethodReplacement.returnConstant(Integer.valueOf(0))});
        if (VERSION.SDK_INT < 24) {
            XposedHelpers.findAndHookMethod("android.location.GpsStatus", classLoader, "getTimeToFirstFix", new Object[]{XC_MethodReplacement.returnConstant(Integer.valueOf(1080))});
            XposedHelpers.findAndHookMethod("android.provider.Settings.Secure", classLoader, "getString", new Object[]{ContentResolver.class, String.class, new XC_MethodHook() {

                protected void beforeHookedMethod(MethodHookParam methodHookParam) {
                    if (((String) methodHookParam.args[1]).equals("mock_location")) {
                        methodHookParam.setResult("0");
                    }
                }
            }});
        } else {
            XposedHelpers.findAndHookMethod("android.location.GpsStatus", classLoader, "getTimeToFirstFix", new Object[]{XC_MethodReplacement.returnConstant(Integer.valueOf(1080))});
            XposedHelpers.findAndHookMethod("android.provider.Settings.Secure", classLoader, "getString", new Object[]{ContentResolver.class, String.class, /* anonymous class already generated */});
        }
        if (VERSION.SDK_INT >= 18) {
            XposedHelpers.findAndHookMethod("android.location.Location", classLoader, "isFromMockProvider", new Object[]{new XC_MethodHook() {


                protected void beforeHookedMethod(MethodHookParam methodHookParam) {
                    methodHookParam.setResult(Boolean.valueOf(false));
                }
            }});
            XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", classLoader, "getDataState", new Object[]{new XC_MethodHook() {


                protected void afterHookedMethod(MethodHookParam methodHookParam) {
                    methodHookParam.setResult(Integer.valueOf(2));
                }
            }});
            XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", classLoader, "getSimState", new Object[]{new XC_MethodHook() {

                protected void afterHookedMethod(MethodHookParam methodHookParam) {
                    methodHookParam.setResult(Integer.valueOf(0));
                }
            }});
            XposedHelpers.findAndHookMethod("android.telephony.PhoneStateListener", classLoader, "onCellLocationChanged", new Object[]{CellLocation.class, new XC_MethodHook() {


                protected void afterHookedMethod(MethodHookParam methodHookParam) {
                    GsmCellLocation gsmCellLocation = new GsmCellLocation();
                    gsmCellLocation.setLacAndCid(0, 0);
                    methodHookParam.setResult(gsmCellLocation);
                }
            }});
            if (VERSION.SDK_INT > 22) {
                XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", classLoader, "getPhoneCount", new Object[]{new XC_MethodHook() {


                    protected void afterHookedMethod(MethodHookParam methodHookParam) {
                        methodHookParam.setResult(Integer.valueOf(1));
                    }
                }});
            }
            XposedHelpers.findAndHookMethod("android.net.wifi.WifiManager", classLoader, "getWifiState", new Object[]{new XC_MethodHook() {


                protected void afterHookedMethod(MethodHookParam methodHookParam) {
                    methodHookParam.setResult(Integer.valueOf(1));
                }
            }});
            XposedHelpers.findAndHookMethod("android.net.wifi.WifiManager", classLoader, "isWifiEnabled", new Object[]{new XC_MethodHook() {

                protected void afterHookedMethod(MethodHookParam methodHookParam) {
                    methodHookParam.setResult(Boolean.valueOf(true));
                }
            }});
            XposedHelpers.findAndHookMethod("android.net.NetworkInfo", classLoader, "getTypeName", new Object[]{XC_MethodReplacement.returnConstant("WIFI")});
            XposedHelpers.findAndHookMethod("android.net.NetworkInfo", classLoader, "isConnectedOrConnecting", new Object[]{XC_MethodReplacement.returnConstant(Boolean.valueOf(true))});
            XposedHelpers.findAndHookMethod("android.net.NetworkInfo", classLoader, "isConnected", new Object[]{XC_MethodReplacement.returnConstant(Boolean.valueOf(true))});
            XposedHelpers.findAndHookMethod("android.net.NetworkInfo", classLoader, "isAvailable", new Object[]{XC_MethodReplacement.returnConstant(Boolean.valueOf(true))});
            XposedHelpers.findAndHookMethod("android.telephony.CellInfo", classLoader, "isRegistered", new Object[]{XC_MethodReplacement.returnConstant(Boolean.valueOf(true))});
        }
    }
}