package com.example.wangxiaohao;

/**
 * Created by zhaokai on 2018/4/11.
 */


import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Locale;

public class TransDeviceInfo implements Parcelable {
    public static final int CONNECTION_TYPE_ADB = 1;
    public static final int CONNECTION_TYPE_AP = 0;
    public static final int CONNECTION_TYPE_DEFAULT = 0;
    public static final Creator<TransDeviceInfo> CREATOR = new Creator<TransDeviceInfo>() {
        public TransDeviceInfo createFromParcel(Parcel source) {
            return new TransDeviceInfo(source);
        }

        public TransDeviceInfo[] newArray(int size) {
            return new TransDeviceInfo[size];
        }
    };
    public boolean canRetransfer;
    public int connectionKey;
    public int connectionType;
    public String displayName;
    public String id;
    public int serverPort;

    public TransDeviceInfo() {
        this(0);
    }

    public TransDeviceInfo(int connectionType) {
        this.id = "";
        this.displayName = "";
        this.serverPort = 0;
    }

    public TransDeviceInfo(TransDeviceInfo another) {
        if (another != null) {
            this.id = another.id;
            this.displayName = another.displayName;
            this.serverPort = another.serverPort;
            this.connectionKey = another.connectionKey;
            this.canRetransfer = another.canRetransfer;
            this.connectionType = another.connectionType;
        }
    }

    public TransDeviceInfo(Parcel in) {
        boolean z = true;
        this.id = in.readString();
        this.displayName = in.readString();
        this.serverPort = in.readInt();
        this.connectionKey = in.readInt();
        if (in.readInt() != 1) {
            z = false;
        }
        this.canRetransfer = z;
        this.connectionType = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.displayName);
        dest.writeInt(this.serverPort);
        dest.writeInt(this.connectionKey);
        dest.writeInt(this.canRetransfer ? 1 : 0);
        dest.writeInt(this.connectionType);
    }

    public boolean equals(Object o) {
        boolean z = false;
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (o instanceof TransDeviceInfo) {
            z = this.id.equals(((TransDeviceInfo) o).id);
        }
        return z;
    }

    public String toString() {
        return String.format(Locale.US, "[id=%s, displayName=%s, serverPort=%d, connectionKey=%d, canRetransfer=%b, connectionType=%d]", new Object[]{this.id, this.displayName, Integer.valueOf(this.serverPort), Integer.valueOf(this.connectionKey), Boolean.valueOf(this.canRetransfer), Integer.valueOf(this.connectionType)});
    }
}