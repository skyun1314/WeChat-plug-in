package com.example.wangxiaohao;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.backup.BackupManager;
import android.app.backup.FullBackupDataOutput;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.StatFs;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.StringBuilderPrinter;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class MainActivity extends AppCompatActivity {
    private static PackageManager mPackageManager ;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPackageManager = getPackageManager();
        try {
            PackageInfo packageInfo = mPackageManager.getPackageInfo(getPackageName(), 64);
            writeAppManifest(packageInfo,new File("/sdcard/_manifest1"));


            long length = new File(packageInfo.applicationInfo.publicSourceDir).length();



            ArrayList<PackageInfo> arrayList=new ArrayList<>();
            arrayList.add(packageInfo);


            writeBackupDescript(new File("/sdcard/descript"),length,arrayList,false,"");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }








    private void writeAppManifest(PackageInfo pkg, File manifestFile) throws IOException {
        Throwable th;
        StringBuilder builder = new StringBuilder(4096);
        StringBuilderPrinter printer = new StringBuilderPrinter(builder);
        printer.println(Integer.toString(1));
        printer.println(pkg.packageName);
        printer.println(Integer.toString(pkg.versionCode));
        printer.println(Integer.toString(Build.VERSION.SDK_INT));
        String installerName = mPackageManager.getInstallerPackageName(pkg.packageName);
        if (installerName == null) {
            installerName = "";
        }
        printer.println(installerName);
        printer.println("0");
        if (pkg.signatures == null) {
            printer.println("0");
        } else {
            printer.println(Integer.toString(pkg.signatures.length));
            for (Signature sig : pkg.signatures) {
                printer.println(sig.toCharsString());
            }
        }
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream outstream = new FileOutputStream(manifestFile);
            try {
                outstream.write(builder.toString().getBytes());
                if (outstream != null) {
                    outstream.close();
                }
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = outstream;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }




    private static void addXmlNode(XmlSerializer arg1, String arg2, String arg3) throws IOException {
        if(arg3 == null) {
            arg3 = "";
        }

        arg1.startTag("", arg2);
        arg1.text(arg3);
        arg1.endTag("", arg2);
    }




    private static void writePackagesToXml(Context arg10, XmlSerializer arg11, ArrayList arg12) throws IOException {
        CharSequence v5_1;
        arg11.startTag("", "packages");
        Iterator v2 = ((Iterable)arg12).iterator();
        while(v2.hasNext()) {
            Object v1 = v2.next();
            arg11.startTag("", "package");
            addXmlNode(arg11, "packageName", ((BRItem)v1).packageName);
            addXmlNode(arg11, "feature", String.valueOf(((BRItem)v1).feature));


            for(int v0 = 0; v0 < ((BRItem)v1).localFileList.size() - 1; ++v0) {
                addXmlNode(arg11, "bakFile", ((BRItem)v1).localFileList.get(v0));
            }

            addXmlNode(arg11, "bakFile", new File(((BRItem)v1).bakFilePath).getName());


            addXmlNode(arg11, "bakType", String.valueOf(((BRItem)v1).type));
            addXmlNode(arg11, "pkgSize", String.valueOf(((BRItem)v1).totalSize));
            addXmlNode(arg11, "sdSize", String.valueOf(((BRItem)v1).transingSdTotalSize));
            addXmlNode(arg11, "state", String.valueOf(((BRItem)v1).state));
            addXmlNode(arg11, "completedSize", String.valueOf(((BRItem)v1).completedSize));
            addXmlNode(arg11, "error", String.valueOf(((BRItem)v1).error));
            addXmlNode(arg11, "progType", String.valueOf(((BRItem)v1).progType));
            addXmlNode(arg11, "bakFileSize", String.valueOf(((BRItem)v1).bakFileSize));
            addXmlNode(arg11, "transingCompletedSize", String.valueOf(((BRItem)v1).transingCompletedSize));
            addXmlNode(arg11, "transingTotalSize", String.valueOf(((BRItem)v1).transingTotalSize));
            addXmlNode(arg11, "transingSdCompletedSize", String.valueOf(((BRItem)v1).transingSdCompletedSize));
            addXmlNode(arg11, "sectionSize", String.valueOf(((BRItem)v1).sectionSize));
            addXmlNode(arg11, "sendingIndex", String.valueOf(((BRItem)v1).sendingIndex));
            arg11.endTag("", "package");
        }

        arg11.endTag("", "packages");
    }

    public static boolean createFile(File arg1) throws IOException {
        return arg1.createNewFile();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getAvailableStorageSpace() {
        StatFs v4 = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        return v4.getAvailableBlocksLong() * v4.getBlockSizeLong();
    }



    public static final void writeBackupDescript(Context arg15, int arg16, File arg17, long arg18, long arg20, ArrayList arg22, boolean arg23, String arg24, TransDeviceInfo arg25, String arg26, ArrayList arg27, long arg28, String arg30) throws IOException {
        FileOutputStream v6;
        XmlSerializer v8 = Xml.newSerializer();
        StringWriter v9 = new StringWriter();
        String v4 = Build.DEVICE;
        String v7 = Build.VERSION.INCREMENTAL;
        v8.setOutput(((Writer)v9));
        v8.startDocument("UTF-8", Boolean.valueOf(true));
        v8.startTag("", "MIUI-backup");
        addXmlNode(v8, "jsonMsg", arg30);
        addXmlNode(v8, "bakVersion", String.valueOf(2));
        addXmlNode(v8, "brState", String.valueOf(arg16));
        addXmlNode(v8, "autoBackup", String.valueOf(arg23));
        addXmlNode(v8, "device", v4);
        addXmlNode(v8, "miuiVersion", v7);
        addXmlNode(v8, "date", String.valueOf(arg18));
        if(!TextUtils.isEmpty(((CharSequence)arg24))) {
            addXmlNode(v8, "key", arg24);
        }

        addXmlNode(v8, "size", String.valueOf(arg20));
        addXmlNode(v8, "storageLeft", String.valueOf(getAvailableStorageSpace()));
        addXmlNode(v8, "supportReconnect", String.valueOf(true));
        if(arg25 != null) {
            addXmlNode(v8, "connectionKey", String.valueOf(arg25.connectionKey));
            addXmlNode(v8, "canTransfer", String.valueOf(arg25.canRetransfer));
        }

        if(!TextUtils.isEmpty(((CharSequence)arg26))) {
            addXmlNode(v8, "remoteDescriptorDir", arg26);
        }

        addXmlNode(v8, "transRealCompletedSize", String.valueOf(arg28));
        writePackagesToXml(arg15, v8, arg22);
       // writePendingFilesToXml(arg15, v8, arg27);
        v8.endTag("", "MIUI-backup");
        v8.endDocument();
        String v3 = v9.toString();
        createFile(arg17);
        FileOutputStream v5 = null;
        try {
            v6 = new FileOutputStream(arg17);
            v6.write(v3.getBytes());
        }
        catch(Throwable v10) {
        }


    }

    public static String getTransTempBackupRootPath() {
        File tempBackupRootFile = new File(TRANS_TEMP_STORAGE_PATH);
        if (!tempBackupRootFile.exists()) {
            tempBackupRootFile.mkdirs();
        }
        return TRANS_TEMP_STORAGE_PATH;
    }

    private static final String TMP_MANIFEST = "_manifest";
    public static final String TRANSFER_BASE = "Transfer";
    public static final String APP_BASE = "backup";
    public static final String TRANSFER_PATH = ("MIUI" + File.separator + APP_BASE + File.separator + TRANSFER_BASE);
    private static final String EXTERNAL_STORAGE_DIR = Environment.getExternalStorageDirectory().toString();
    private static String TRANS_TEMP_STORAGE_PATH = (EXTERNAL_STORAGE_DIR + File.separator + TRANSFER_PATH);


    public static void unZipBakFile(File baseDir, File bakFile) throws IOException {
        Throwable th;
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
        InputStream inputStream;
        if (bakFile != null && bakFile.exists()) {
            ZipFile zipFile = null;
            try {
                ZipFile zipFile2 = new ZipFile(bakFile);
                try {
                    Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zipFile2.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry entry = (ZipEntry) entries.nextElement();
                        if (!entry.isDirectory()) {
                            inputStream = null;
                            OutputStream outputStream = null;
                            try {
                                inputStream = zipFile2.getInputStream(entry);
                                OutputStream os = new FileOutputStream(new File(baseDir, entry.getName()));
                                try {
                                    byte[] bytes = new byte[1024];
                                    while (true) {
                                        int readCount = inputStream.read(bytes);
                                        if (readCount <= 0) {
                                            break;
                                        }
                                        os.write(bytes, 0, readCount);
                                    }
                                    if (inputStream != null) {
                                        inputStream.close();
                                    }
                                    if (os != null) {
                                        os.close();
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    outputStream = os;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                            }
                        }
                    }
                    if (zipFile2 != null) {
                        zipFile2.close();
                        return;
                    }
                    return;
                } catch (Throwable th4) {
                    th = th4;
                    zipFile = zipFile2;
                }
            } catch (Throwable th5) {
                th = th5;
                if (zipFile != null) {
                    zipFile.close();
                }
            }
        }
        return;
    }


    private HashMap<String, String> fomartToMapFromXml(File xmlFile) throws IOException, XmlPullParserException {
        Throwable th;
        HashMap<String, String> map = new HashMap();
        if (xmlFile != null && xmlFile.exists()) {
            FileInputStream fileInputStream = null;
            try {
                FileInputStream fis = new FileInputStream(xmlFile);
                try {
                    XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
                    parser.setInput(fis, "UTF-8");
                    int type = 0;
                    while (type != 1 && type != 2) {
                        type = parser.next();
                    }
                    Object obj = null;
                    while (type != 1) {
                        switch (type) {
                            case 2:
                                obj = parser.getName();
                                break;
                            case 4:
                                map.put((String) obj, parser.getText());
                                break;
                            default:
                                break;
                        }
                        type = parser.next();
                    }
                    if (fis != null) {
                        fis.close();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream = fis;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            }
        }
        return map;
    }



    @SuppressLint("WrongConstant")
    private void transFromV1ToV2File(String pkg, int feature, File tmpCacheDir, File tmpBakFile, String v1FilePath) throws IOException, XmlPullParserException, PackageManager.NameNotFoundException, InterruptedException {
        File manifestFile = new File(getTransTempBackupRootPath(), TMP_MANIFEST);
        File descriptor = new File(tmpCacheDir, "descript.xml");
        if (descriptor.exists()) {
            HashMap<String, String> descMap = fomartToMapFromXml(descriptor);
            BackupMeta meta = new BackupMeta();
            meta.packageName = pkg;
            meta.feature = feature;
            meta.miuiVersion = (String) descMap.get(Customization.BACKUP_DESCRIPTOR_MIUIVERSION);
            meta.appVersionCode = 0;
            meta.appVersionName = "0";
            meta.createTime = 0;
            meta.deviceName = (String) descMap.get("device");
            meta.metaVersion = 1;
            meta.version = 1;
            store = new File(tmpCacheDir, (String) descMap.get("appPackage"));
            writeAppManifest(mPackageManager.getPackageInfo(pkg, 64), new File(getTransTempBackupRootPath(), TMP_MANIFEST));
            buildFormattedFileSysApp(pkg, feature, tmpCacheDir, tmpBakFile, manifestFile, store, meta);
        } else {
            BackupLog.w(TAG, "error, there's no descript.xml");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void buildFormattedFileSysApp(String pkg, int feature, File baseDir, File dst, File manifest, File store, BackupMeta meta) throws IOException, InterruptedException {
        Throwable th;
        OutputStream outputStream = null;
        ParcelFileDescriptor[] pipes = null;
        final AtomicBoolean latch = new AtomicBoolean(false);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                StringBuilder headerbuf = new StringBuilder(1024);
                headerbuf.append("MIUI BACKUP\n");
                headerbuf.append("2\n");
                headerbuf.append(pkg).append(MarketConstants.SPLIT_PATTERN_LINE_SEPARATOR);
                headerbuf.append(String.valueOf(feature)).append(MarketConstants.SPLIT_PATTERN_LINE_SEPARATOR);
                headerbuf.append(String.valueOf(0)).append(MarketConstants.SPLIT_PATTERN_LINE_SEPARATOR);
                headerbuf.append("ANDROID BACKUP\n");
                headerbuf.append(2);
                headerbuf.append("\n1\n");
                headerbuf.append("none\n");
                out.write(headerbuf.toString().getBytes("UTF-8"));
                outputStream = new DeflaterOutputStream(out, new Deflater(9), true);
                pipes = ParcelFileDescriptor.createPipe();
                ParcelFileDescriptor readSide = pipes[0];
                ParcelFileDescriptor writeSide = pipes[1];
                OutputStream finalOut = outputStream;
                final ParcelFileDescriptor parcelFileDescriptor = readSide;
                new Thread() {
                    public void run() {
                        IOException e;
                        Throwable th;
                        synchronized (latch) {
                            FileInputStream fileInputStream = null;
                            DataInputStream dataInputStream = null;
                            try {
                                FileInputStream raw = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
                                try {
                                    DataInputStream in = new DataInputStream(raw);
                                    try {
                                        byte[] buffer = new byte[32768];
                                        while (true) {
                                            int chunkTotal = in.readInt();
                                            if (chunkTotal <= 0) {
                                                break;
                                            }
                                            while (chunkTotal > 0) {
                                                int nRead = in.read(buffer, 0, chunkTotal > buffer.length ? buffer.length : chunkTotal);
                                                outputStream.write(buffer, 0, nRead);
                                                chunkTotal -= nRead;
                                            }
                                        }
                                        if (raw != null) {
                                            try {
                                                raw.close();
                                            } catch (IOException e2) {
                                            } catch (Throwable th2) {
                                                th = th2;
                                                fileInputStream = raw;
                                                throw th;
                                            }
                                        }
                                        if (in != null) {
                                            try {
                                                in.close();
                                            } catch (IOException e22) {
                                            }
                                        }
                                        latch.set(true);
                                        latch.notify();
                                        fileInputStream = raw;
                                    } catch (IOException e3) {
                                        dataInputStream = in;
                                        fileInputStream = raw;
                                        try {
                                            if (fileInputStream != null) {
                                                try {
                                                    fileInputStream.close();
                                                } catch (IOException e222) {
                                                } catch (Throwable th3) {
                                                    th = th3;
                                                    throw th;
                                                }
                                            }
                                            if (dataInputStream != null) {
                                                try {
                                                    dataInputStream.close();
                                                } catch (IOException e2222) {
                                                }
                                            }
                                            latch.set(true);
                                            latch.notify();
                                        } catch (Throwable th4) {
                                            th = th4;
                                            if (fileInputStream != null) {
                                                try {
                                                    fileInputStream.close();
                                                } catch (IOException e22222) {
                                                }
                                            }
                                            if (dataInputStream != null) {
                                                try {
                                                    dataInputStream.close();
                                                } catch (IOException e222222) {
                                                }
                                            }
                                            latch.set(true);
                                            latch.notify();
                                            throw th;
                                        }
                                    } catch (Throwable th5) {
                                        th = th5;
                                        dataInputStream = in;
                                        fileInputStream = raw;
                                        if (fileInputStream != null) {
                                            fileInputStream.close();
                                        }
                                        if (dataInputStream != null) {
                                            dataInputStream.close();
                                        }
                                        latch.set(true);
                                        latch.notify();
                                        throw th;
                                    }
                                } catch (IOException e4) {
                                    fileInputStream = raw;
                                    if (fileInputStream != null) {
                                        fileInputStream.close();
                                    }
                                    if (dataInputStream != null) {
                                        dataInputStream.close();
                                    }
                                    latch.set(true);
                                    latch.notify();
                                } catch (Throwable th6) {
                                    th = th6;
                                    fileInputStream = raw;
                                    if (fileInputStream != null) {
                                        fileInputStream.close();
                                    }
                                    if (dataInputStream != null) {
                                        dataInputStream.close();
                                    }
                                    latch.set(true);
                                    latch.notify();
                                }
                            } catch (IOException e5) {
                                if (fileInputStream != null) {
                                }
                                if (dataInputStream != null) {
                                }
                                latch.set(true);
                                latch.notify();
                            }
                        }
                    }
                }.start();
              /*  FullBackupDataOutput bakOut = new FullBackupDataOutput(writeSide);
                FullBackupProxy.backupToTar(pkg, null, null, manifest.getParent(), manifest.getAbsolutePath(), bakOut);
                meta.writeToTar(this.mContext, bakOut);
                FullBackupProxy.backupToTar(pkg, BackupManager.DOMAIN_BAK, null, store.getParent(), store.getAbsolutePath(), bakOut);
                tarAttaches(pkg, feature, baseDir, store, bakOut);
                FileOutputStream fileOutputStream = null;
                try {
                    FileOutputStream osEnd = new FileOutputStream(pipes[1].getFileDescriptor());
                    try {
                        osEnd.write(new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0});
                        if (osEnd != null) {
                            osEnd.close();
                        }
                        synchronized (latch) {
                            while (!latch.get()) {
                                latch.wait();
                            }
                        }
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        if (pipes != null) {
                            pipes[0].close();
                            pipes[1].close();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = osEnd;
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    throw th;
                }*/
            } catch (Throwable th4) {
                th = th4;
                outputStream = out;
                if (outputStream != null) {
                    outputStream.close();
                }
                if (pipes != null) {
                    pipes[0].close();
                    pipes[1].close();
                }
                throw th;
            }
        } catch (Throwable th5) {
            th = th5;
            if (outputStream != null) {
                outputStream.close();
            }
            if (pipes != null) {
                pipes[0].close();
                pipes[1].close();
            }
        }
    }

    private void tarAttaches(String pkg, int feature, File baseDir, File store, FullBackupDataOutput out) {
        if ("com.miui.notes".equals(pkg)) {
            baseDir = new File(this.mBakFile.getParent(), "note_attach");
        } else if ("com.android.mms".equals(pkg) && feature == 2) {
            baseDir = new File(this.mBakFile.getParent(), "mms_attach");
        }
        File[] attaches = baseDir.listFiles();
        if (attaches != null) {
            for (File f : attaches) {
                if (!(f.getName().equals(Customization.BACKUP_DESCRIPT_FILE_NAME) || (f.getName().equals(store.getName()) ^ 1) == 0)) {
                    FullBackupProxy.backupToTar(pkg, BackupManager.DOMAIN_ATTACH, null, f.getParent(), f.getAbsolutePath(), out);
                }
            }
        }
    }

}
