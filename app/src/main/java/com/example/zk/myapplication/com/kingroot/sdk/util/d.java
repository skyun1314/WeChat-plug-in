package com.example.zk.myapplication.com.kingroot.sdk.util;/*
package com.kingroot.sdk.util;

import android.util.Base64;
import com.kingroot.sdk.commom.a.a;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public final class d {
    public static long a(InputStream inputStream, OutputStream outputStream) {
        byte[] bArr = new byte[4096];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                return j;
            }
            outputStream.write(bArr, 0, read);
            j += (long) read;
        }
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    public static void a(File file, String str) {
        Closeable bufferedWriter;
        IOException e;
        Throwable th;
        if (file != null && file.exists() && file.length() > 102400) {
            file.delete();
        }
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            try {
                bufferedWriter.append(new String(Base64.encode(str.getBytes(), 2))).append("\n");
                bufferedWriter.flush();
                a(bufferedWriter);
            } catch (IOException e2) {
                e = e2;
                try {
                    e.printStackTrace();
                    a(bufferedWriter);
                } catch (Throwable th2) {
                    th = th2;
                    a(bufferedWriter);
                    throw th;
                }
            }
        } catch (IOException e3) {
            e = e3;
            bufferedWriter = null;
            e.printStackTrace();
            a(bufferedWriter);
        } catch (Throwable th3) {
            th = th3;
            bufferedWriter = null;
            a(bufferedWriter);
            throw th;
        }
    }

    public static List a(File file) {
        Closeable bufferedReader;
        IOException e;
        Throwable th;
        List arrayList = new ArrayList();
        if (file.exists()) {
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        try {
                            arrayList.add(new String(Base64.decode(readLine, 2)));
                        } catch (IllegalArgumentException e2) {
                            e2.printStackTrace();
                        }
                    } catch (IOException e3) {
                        e = e3;
                    }
                }
                a(bufferedReader);
            } catch (IOException e4) {
                e = e4;
                bufferedReader = null;
                try {
                    e.printStackTrace();
                    a(bufferedReader);
                    return arrayList;
                } catch (Throwable th2) {
                    th = th2;
                    a(bufferedReader);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                bufferedReader = null;
                a(bufferedReader);
                throw th;
            }
        }
        return arrayList;
    }

    public static void a(File file, String str, String str2) {
        Closeable bufferedReader;
        Closeable fileWriter;
        IOException e;
        Throwable th;
        Closeable closeable = null;
        File file2 = new File(file.getAbsolutePath() + ".tmp");
        Object obj = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            try {
                fileWriter = new FileWriter(file2);
                while (true) {
                    try {
                        Object readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        if (readLine.contains(str)) {
                            readLine = readLine.replace(str, str2);
                            a.e("line = " + readLine);
                            obj = 1;
                        }
                        fileWriter.write(new StringBuilder(String.valueOf(readLine)).append("\n").toString());
                    } catch (IOException e2) {
                        e = e2;
                        closeable = bufferedReader;
                    } catch (Throwable th2) {
                        th = th2;
                    }
                }
                a(bufferedReader);
                a(fileWriter);
            } catch (IOException e3) {
                e = e3;
                fileWriter = null;
                closeable = bufferedReader;
                try {
                    e.printStackTrace();
                    a(closeable);
                    a(fileWriter);
                    if (obj != null) {
                        file2.delete();
                    }
                    file.delete();
                    file2.renameTo(file);
                    return;
                } catch (Throwable th3) {
                    th = th3;
                    bufferedReader = closeable;
                    a(bufferedReader);
                    a(fileWriter);
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                fileWriter = null;
                a(bufferedReader);
                a(fileWriter);
                throw th;
            }
        } catch (IOException e4) {
            e = e4;
            fileWriter = null;
            e.printStackTrace();
            a(closeable);
            a(fileWriter);
            if (obj != null) {
                file.delete();
                file2.renameTo(file);
                return;
            }
            file2.delete();
        } catch (Throwable th5) {
            th = th5;
            fileWriter = null;
            bufferedReader = null;
            a(bufferedReader);
            a(fileWriter);
            throw th;
        }
        if (obj != null) {
            file.delete();
            file2.renameTo(file);
            return;
        }
        file2.delete();
    }

    public static void a(File file, String... strArr) {
        Closeable fileWriter;
        Object obj;
        IOException e;
        Closeable closeable;
        Throwable th;
        File file2 = new File(file.getAbsolutePath() + ".tmp");
        Closeable bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            try {
                fileWriter = new FileWriter(file2);
                obj = null;
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        for (int i = 0; i < strArr.length; i += 2) {
                            if (readLine.contains(strArr[i])) {
                                readLine = readLine.replace(strArr[i], strArr[i + 1]);
                                a.e("line = " + readLine);
                                obj = 1;
                            }
                        }
                        fileWriter.write(new StringBuilder(String.valueOf(readLine)).append("\n").toString());
                    } catch (IOException e2) {
                        e = e2;
                        closeable = fileWriter;
                        fileWriter = bufferedReader;
                    } catch (Throwable th2) {
                        th = th2;
                    }
                }
                a(bufferedReader);
                a(fileWriter);
            } catch (IOException e3) {
                e = e3;
                fileWriter = bufferedReader;
                obj = null;
                closeable = null;
                try {
                    e.printStackTrace();
                    a(fileWriter);
                    a(closeable);
                    if (obj != null) {
                        file.delete();
                        file2.renameTo(file);
                        return;
                    }
                    file2.delete();
                } catch (Throwable th3) {
                    th = th3;
                    bufferedReader = fileWriter;
                    fileWriter = closeable;
                    a(bufferedReader);
                    a(fileWriter);
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                fileWriter = null;
                a(bufferedReader);
                a(fileWriter);
                throw th;
            }
        } catch (IOException e4) {
            e = e4;
            fileWriter = null;
            obj = null;
            closeable = null;
            e.printStackTrace();
            a(fileWriter);
            a(closeable);
            if (obj != null) {
                file2.delete();
            }
            file.delete();
            file2.renameTo(file);
            return;
        } catch (Throwable th5) {
            th = th5;
            fileWriter = null;
            bufferedReader = null;
            a(bufferedReader);
            a(fileWriter);
            throw th;
        }
        if (obj != null) {
            file.delete();
            file2.renameTo(file);
            return;
        }
        file2.delete();
    }
}*/
