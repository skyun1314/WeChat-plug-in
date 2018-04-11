package com.example.wangxiaohao;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;

public class BRItem implements Parcelable {
    public static final Creator<BRItem> CREATOR = new Creator<BRItem>() {
        public BRItem createFromParcel(Parcel source) {
            return new BRItem(source);
        }

        public BRItem[] newArray(int size) {
            return new BRItem[size];
        }
    };
    public static final int ERR_AUTHENTICATION_FAILED = 2;
    public static final int ERR_BAKFILE_BROKEN = 7;
    public static final int ERR_BAKFILE_NOT_EXIST = 9;
    public static final int ERR_NONE = 0;
    public static final int ERR_NOT_ALLOW = 10;
    public static final int ERR_PKG_NOT_EXIST = 4;
    public static final int ERR_STORAGE_SPACE = 3;
    public static final int ERR_UNKNOWN = 1;
    public static final int ERR_USER_ABORT = 8;
    public static final int ERR_VERSION_TOO_OLD = 6;
    public static final int ERR_VERSION_UNSUPPORTED = 5;
    public static final int STATE_FAILED = 3;
    public static final int STATE_FINISHED = 1;
    public static final int STATE_PENDDING = 0;
    public static final int STATE_PRELOADING = 4;
    public static final int STATE_RUNNING = 2;
    public static final int STATE_TRANSING = 5;
    public static final int STATE_TRANSING_END = 6;
    public static final int TYPE_ACCOUNT = 3;
    public static final int TYPE_AUDIO = 6;
    public static final int TYPE_DOC = 8;
    public static final int TYPE_FILES = 4;
    public static final int TYPE_IMAGE = 5;
    public static final int TYPE_SYSTEM_APP = 1;
    public static final int TYPE_USER_APP = 2;
    public static final int TYPE_VIDEO = 7;
    public long apkSize;
    public String bakFilePath = "";
    public long bakFileSize;
    public long completedSize;
    public long dataSize;
    public int error;
    public int feature = -1;
    public ArrayList<String> localFileList = new ArrayList();
    public String packageName = "";
    public int progType = 0;
    public long sectionSize;
    public int sendingIndex;
    public int state;
    public long totalSize;
    public long transingCompletedSize;
    public long transingSdCompletedSize;
    public long transingSdTotalSize;
    public long transingTotalSize;
    public int type;

    public BRItem(int type) {
        this.type = type;
    }

    public BRItem(Parcel in) {
        this.type = in.readInt();
        this.packageName = in.readString();
        this.feature = in.readInt();
        this.bakFilePath = in.readString();
        this.totalSize = in.readLong();
        this.state = in.readInt();
        this.completedSize = in.readLong();
        this.error = in.readInt();
        this.progType = in.readInt();
        this.bakFileSize = in.readLong();
        this.localFileList = in.readArrayList(String.class.getClassLoader());
        this.transingCompletedSize = in.readLong();
        this.transingTotalSize = in.readLong();
        this.transingSdCompletedSize = in.readLong();
        this.transingSdTotalSize = in.readLong();
        this.sectionSize = in.readLong();
        this.sendingIndex = in.readInt();
    }

    public BRItem(BRItem another) {
        this.type = another.type;
        this.packageName = another.packageName;
        this.feature = another.feature;
        this.bakFilePath = another.bakFilePath;
        this.totalSize = another.totalSize;
        this.state = another.state;
        this.completedSize = another.completedSize;
        this.error = another.error;
        this.progType = another.progType;
        this.bakFileSize = another.bakFileSize;
        this.localFileList.addAll(another.localFileList);
        this.transingCompletedSize = another.transingCompletedSize;
        this.transingTotalSize = another.transingTotalSize;
        this.transingSdCompletedSize = another.transingSdCompletedSize;
        this.transingSdTotalSize = another.transingSdTotalSize;
        this.sectionSize = another.sectionSize;
        this.sendingIndex = another.sendingIndex;
    }

    public boolean isInternalFeature() {
        if (this.feature == 100 || this.feature == 101 || this.feature == 102) {
            return true;
        }
        return false;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.packageName);
        dest.writeInt(this.feature);
        dest.writeString(this.bakFilePath);
        dest.writeLong(this.totalSize);
        dest.writeInt(this.state);
        dest.writeLong(this.completedSize);
        dest.writeInt(this.error);
        dest.writeInt(this.progType);
        dest.writeLong(this.bakFileSize);
        dest.writeList(this.localFileList);
        dest.writeLong(this.transingCompletedSize);
        dest.writeLong(this.transingTotalSize);
        dest.writeLong(this.transingSdCompletedSize);
        dest.writeLong(this.transingSdTotalSize);
        dest.writeLong(this.sectionSize);
        dest.writeInt(this.sendingIndex);
    }

    public String toString() {
        return String.format("[type=%d, packageName=%s, feature=%d, bakFilePath=%s, totalSize=%d, state=%d, completedSize=%d, error=%d, progType=%d, bakFileSize=%d, transingCompletedSize=%d, transingTotalSize=%d, transingSdCompletedSize=%d, transingSdTotalSize=%d, sectionSize=%d, sendingIndex=%d, localFileList=%s]\r\n", new Object[]{Integer.valueOf(this.type), this.packageName, Integer.valueOf(this.feature), this.bakFilePath, Long.valueOf(this.totalSize), Integer.valueOf(this.state), Long.valueOf(this.completedSize), Integer.valueOf(this.error), Integer.valueOf(this.progType), Long.valueOf(this.bakFileSize), Long.valueOf(this.transingCompletedSize), Long.valueOf(this.transingTotalSize), Long.valueOf(this.transingSdCompletedSize), Long.valueOf(this.transingSdTotalSize), Long.valueOf(this.sectionSize), Integer.valueOf(this.sendingIndex), this.localFileList.toString()});
    }
}