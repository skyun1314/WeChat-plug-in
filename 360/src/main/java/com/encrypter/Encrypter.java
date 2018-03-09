package com.encrypter;

import com.googlecode.d2j.DexConstants;
import com.googlecode.d2j.dex.writer.DexFileWriter;
import com.googlecode.d2j.dex.writer.item.ClassDataItem;
import com.googlecode.d2j.dex.writer.item.FieldIdItem;
import com.googlecode.d2j.dex.writer.item.MethodIdItem;
import com.googlecode.d2j.reader.DexFileReader;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.security.MessageDigest;

public class Encrypter implements DexConstants {
  public static final int ENCRYPT_DEMO = 1;    //DEMO锟斤拷锟斤拷募锟斤拷锟�
  public static final int ENCRYPT_PRO = 2;    //专业锟斤拷锟斤拷募锟斤拷锟�
  public static final int ENCRYPT_CUSTOM = 3;    //锟斤拷锟狡硷拷锟斤拷募锟斤拷锟�

  private long fileSize = 0x0;
  private long startPos = 0x0;

  public static void main(String[] args) {
    Encrypter t = new Encrypter();
    try {
      //t.encryptFieldAndMethod("E:/apks/lab/tax1.dex", "E:/apks/lab/tax.dex", ENCRYPT_DEMO, true,
      //    true);
      //t.fixDexFile("E:/apks/lab/tax1.dex", "E:/apks/lab/tax1_.dex", "E:/apks/lab/datarc.bin");
      t.fixDexFile2("E:/apks/lab/tax1.dex", "E:/apks/lab/temp.dex", "E:/apks/lab/tax1_new.dex",
          "E:/apks/lab/datarc.bin");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  DexFileWriter w = null;

  public Encrypter() {
    w = new DexFileWriter();

    //debugNameSet.add("TABLE_NAME");
    //debugNameSet.add("list");
    //readTxtFile("doEncryptListField.txt", 0, 200);

    //blackFieldSet.add("onCheckedChanged");
  }

  public static boolean DEBUG_MODE = true;
  public static Set<String> debugNameSet = new HashSet<String>();
  public static Set<String> blackFieldSet = new HashSet<String>();

  public static void readTxtFile(String filePath, int min, int max) {
    if (!DEBUG_MODE) return;

    int count = 0;
    try {
      String encoding = "GBK";
      File file = new File(filePath);
      if (file.isFile() && file.exists()) {
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        while ((lineTxt = bufferedReader.readLine()) != null) {
          if (count <= max && count >= min) {
            debugNameSet.add(lineTxt.substring(0, lineTxt.indexOf("@->")));
          }
          count++;
        }
        read.close();
        System.out.println("锟窖硷拷锟杰憋拷锟斤拷锟斤拷目锟斤拷" + count);
      } else {
        System.out.println("锟揭诧拷锟斤拷指锟斤拷锟斤拷锟侥硷拷");
      }
    } catch (Exception e) {
      System.out.println("锟斤拷取锟侥硷拷锟斤拷锟捷筹拷锟斤拷");
      e.printStackTrace();
    }
  }

  public void encryptFieldAndMethod(String inPath, String outPath, int encryptLvl,
      boolean handleField, boolean handleMethod) throws IOException {

    try {
      File f = new File(inPath);
      fileSize = f.length();
      DexFileReader dexFileReader = new DexFileReader(f);
      Checker.init();
      dexFileReader.accept(w);
    } catch (NoSuchFileException e) {
      //e.printStackTrace();
      System.out.println(e.getFile() + "锟斤拷锟斤拷锟侥硷拷锟斤拷锟斤拷锟斤拷");
      return;
    }

    Checker.init(w.cp);

    int size = 0;

    if (handleField) {
      List<FieldIdItem> fieldIdList =
          Arrays.asList(w.cp.fields.values().toArray(new FieldIdItem[] {}));
      size = fieldIdList.size();
      for (int i = 0; i < size; ++i) {
        handleFieldItem(fieldIdList.get(i));
      }
    }

    if (handleMethod) {
      List<MethodIdItem> methodIdList =
          Arrays.asList(w.cp.methods.values().toArray(new MethodIdItem[] {}));
      size = methodIdList.size();
      for (int i = 0; i < size; ++i) {
        handleMethodItem(methodIdList.get(i));
      }
    }

    Debuger.PrintInfos();

    byte[] b = w.toByteArray(true);

    File file = new File(outPath);
    FileOutputStream fs = new FileOutputStream(file);
    fs.write(b);

    fs.flush();
    fs.close();

    System.out.println("OK");
  }

  //恢复dex之重组dex版
  private void fixDexFile2(String inPath, String tempPath, String outPath, String dataPath)
      throws IOException {
    byte[] oriDex;
    try {
      //读取原始dex
      File f = new File(inPath);
      fileSize = f.length();
      FileInputStream in1 = new FileInputStream(f);
      DataInputStream data_in = new DataInputStream(in1);
      oriDex = new byte[(int) fileSize];//原dex的字节
      data_in.read(oriDex, 0, (int) fileSize);

      //DexFileReader dexFileReader = new DexFileReader(f);
      //dexFileReader.accept(w);		//锟斤拷DEX锟斤拷锟捷讹拷取锟斤拷cp锟斤拷锟斤拷锟斤拷锟斤拷锟�

      System.out.println(fileSize);
      startPos = getNearBigNum(fileSize);
      System.out.println(startPos);

      //读取并解析datarc.bin
      File data = new File(dataPath);
      int dataSize = (int) (data.length());
      FileInputStream dIn = new FileInputStream(data);
      DataInputStream dInS = new DataInputStream(dIn);
      byte[] dataBuf = new byte[dataSize];
      dInS.read(dataBuf, 0, dataSize);

      int indexOff = bytesToInt(getData(dataBuf, 0x18, 0x4));
      //System.out.println(indexOff);

      for (int i = 0; i < 207; ++i) {
        int nameOff = bytesToInt(getData(dataBuf, indexOff + i * 0xC, 0x4));
        int shortyOff = bytesToInt(getData(dataBuf, indexOff + 4 + i * 0xC, 0x4));
        int codeOff = bytesToInt(getData(dataBuf, indexOff + 8 + i * 0xC, 0x4));
        String funcInfo = getStr(dataBuf, nameOff).trim();
        String shorty = getStr(dataBuf, shortyOff).trim();
        //funcInfo = funcInfo.substring(0, funcInfo.indexOf('('));
        //System.out.println(funcInfo + "%%%%%%**");
        //System.out.println(shorty + "%%%%%%**");
        //System.out.println(codeOff);
        //System.out.println("===========");
        func2code.put(funcInfo, codeOff + this.startPos);
      }

      //将原始dex和datarc.bin文件合并
      int s = (int) (dataSize + this.startPos);
      byte[] finalFile = new byte[s];

      for (int i = 0; i < fileSize; ++i) {
        finalFile[i] = oriDex[i];
      }

      for (int i = 0; i < dataSize; ++i) {
        finalFile[(int) (i + this.startPos)] = dataBuf[i];
      }

      File file = new File(tempPath);
      FileOutputStream fs = new FileOutputStream(file);
      fs.write(finalFile);

      fs.flush();
      fs.close();
    } catch (NoSuchFileException e) {
      //e.printStackTrace();
      System.out.println(e.getFile() + "锟斤拷锟斤拷锟侥硷拷锟斤拷锟斤拷锟斤拷");
      return;
    }

    //解析新dex修改对应code_off并重组dex
    File f = new File(tempPath);
    DexFileReader dexFileReader = new DexFileReader(f);
    dexFileReader.accept(w);//重组dex
    byte[] b = w.toByteArray(true);      //锟斤拷锟斤拷DEX锟侥硷拷

    File file = new File(outPath);
    FileOutputStream fs = new FileOutputStream(file);
    fs.write(b);

    fs.flush();
    fs.close();

    System.out.println("OK");
  }

  public static Map func2code = new HashMap();

  //恢复dex之直接修改code_off
  private void fixDexFile(String inPath, String outPath, String dataPath) throws IOException {
    byte[] oriDex;
    try {
      File f = new File(inPath);
      fileSize = f.length();
      FileInputStream in1 = new FileInputStream(f);
      DataInputStream data_in = new DataInputStream(in1);
      oriDex = new byte[(int) fileSize];
      data_in.read(oriDex, 0, (int) fileSize);

      DexFileReader dexFileReader = new DexFileReader(f);
      dexFileReader.accept(w);
    } catch (NoSuchFileException e) {
      //e.printStackTrace();
      System.out.println(e.getFile());
      return;
    }

    System.out.println(fileSize);
    startPos = getNearBigNum(fileSize);
    System.out.println(startPos);

    File data = new File(dataPath);
    int dataSize = (int) (data.length());
    FileInputStream in1 = new FileInputStream(data);
    DataInputStream data_in = new DataInputStream(in1);
    byte[] dataBuf = new byte[dataSize];
    data_in.read(dataBuf, 0, dataSize);

    int indexOff = bytesToInt(getData(dataBuf, 0x18, 0x4));
    //System.out.println(indexOff);

    for (int i = 0; i < 207; ++i) {
      int nameOff = bytesToInt(getData(dataBuf, indexOff + i * 0xC, 0x4));
      int shortyOff = bytesToInt(getData(dataBuf, indexOff + 4 + i * 0xC, 0x4));
      int codeOff = bytesToInt(getData(dataBuf, indexOff + 8 + i * 0xC, 0x4));
      String funcInfo = getStr(dataBuf, nameOff);
      funcInfo = funcInfo.substring(0, funcInfo.indexOf('('));
      /*System.out.println(funcInfo);
      System.out.println(getStr(dataBuf, shortyOff));
			System.out.println(codeOff);
			System.out.println("===========");*/
      func2code.put(funcInfo, codeOff);
    }

    //code相关的
    List<ClassDataItem> classDataList =
        Arrays.asList(w.cp.classDataItems.toArray(new ClassDataItem[] {}));
    int size = classDataList.size();
    for (int i = 0; i < size; ++i) {
      handleClassDataItem(classDataList.get(i), oriDex);
    }
    /**从原始dex中生成dex，并没有调用DexFileWriter的toByteCode()方法**/
    int s = (int) (dataSize + this.startPos);
    byte[] finalFile = new byte[s];

    for (int i = 0; i < fileSize; ++i) {
      finalFile[i] = oriDex[i];
    }

    for (int i = 0; i < dataSize; ++i) {
      finalFile[(int) (i + this.startPos)] = dataBuf[i];
    }

    File file = new File(outPath);
    FileOutputStream fs = new FileOutputStream(file);
    fs.write(finalFile);

    fs.flush();
    fs.close();

    System.out.println("OK");
  }

  public static Map func2codePos = new HashMap();
  public static Map methodAccessFlagsPos = new HashMap();
  public static Map func2codeOff = new HashMap();

  private void handleClassDataItem(ClassDataItem item, byte[] oriDex) {
    int size = item.virtualMethods.size();
    for (int i = 0; i < size; ++i) {
      String name = item.virtualMethods.get(i).method.name.stringData.string;
      String clazz = item.virtualMethods.get(i).method.clazz.descriptor.stringData.string;
      //System.out.println(clazz + name);

      if (item.virtualMethods.get(i).code != null) {
        if (this.func2code.containsKey(clazz + name)) {
          int offset = (int) (this.func2code.get(clazz + name)) + (int) (this.startPos);
          //item.virtualMethods.get(i).code.updateOffset(offset);
          //System.out.println(clazz + name);
          //System.out.println(func2codePos.get(clazz + name));
          int pos = (int) (func2codePos.get(clazz + name));
          byte[] newOff = doUleb128(offset);
          if (newOff.length != doUleb128((int) (func2codeOff.get(clazz + name))).length) {
            System.out.println(clazz + name);
            System.out.println(newOff.length);
            System.out.println(doUleb128((int) (func2codeOff.get(clazz + name))).length);
            System.out.println("*************");
          } else {
            for (int i1 = 0; i1 < newOff.length; ++i1) {
              oriDex[pos + i1] = newOff[i1];
            }
          }
          //System.out.println(offset);
        }
      }
    }
  }

  public static byte[] doUleb128(int value) {
    ByteBuffer buffer = ByteBuffer.allocate(lengthOfUleb128(value));
    buffer.order(ByteOrder.LITTLE_ENDIAN);
    int remaining = value >>> 7;

    while (remaining != 0) {
      buffer.put((byte) ((value & 0x7f) | 0x80));
      value = remaining;
      remaining >>>= 7;
    }

    buffer.put((byte) (value & 0x7f));
    return buffer.array();
  }

  public static int lengthOfUleb128(final int s) {
    int length = 1;
    if (s > 0x7f) {
      length++;
      if (s > (0x3fff)) {
        length++;
        if (s > (0x1fffff)) {
          length++;
          if (s > (0xfffffff)) {
            length++;
          }
        }
      }
    }
    return length;
  }

  public static long getNearBigNum(long num) {
    int count = 0;
    while (num / 0x10 != 0) {
      num /= 0x10;
      ++count;
    }
    ++num;
    for (int i = 0; i < count; ++i) {
      num *= 0x10;
    }
    return num;
  }

  public static byte[] getData(byte[] bytes, int offset, int len) {
    byte[] ret = new byte[len];
    for (int i = 0; i < len; ++i) {
      ret[i] = bytes[offset + i];
    }
    return ret;
  }

  public static String getStr(byte[] bytes, int offset) {
    int len = 0;
    while (bytes[offset + len] != 0x00) {
      ++len;
    }
    ++len;
    byte[] ret = new byte[len];
    for (int i = 0; i < len; ++i) {
      ret[i] = bytes[offset + i];
    }
    return new String(ret);
  }

  private static ByteBuffer longBuf = ByteBuffer.allocate(8);

  public static long bytesToLong(byte[] bytes) {
    longBuf.put(bytes, 0, bytes.length);
    longBuf.flip();//need flip
    return longBuf.getLong();
  }

  private static ByteBuffer intBuf = ByteBuffer.allocate(4);

  public static int bytesToInt(byte[] bytes) {
    int r = 0;
    r |= (bytes[3] & 0xff);
    r <<= 8;
    r |= (bytes[2] & 0xff);
    r <<= 8;
    r |= (bytes[1] & 0xff);
    r <<= 8;
    r |= (bytes[0] & 0xff);
    return r;
  }

  private static ByteBuffer shortBuf = ByteBuffer.allocate(2);

  public static short bytesToShort(byte[] bytes) {
    short r = 0;
    r |= (bytes[1] & 0xff);
    r <<= 8;
    r |= (bytes[0] & 0xff);
    return r;
  }

  //锟斤拷锟斤拷锟街讹拷锟斤拷息
  private int countField = 0;

  private void handleFieldItem(FieldIdItem item) {
    String clazz = item.clazz.descriptor.stringData.string;
    String type = item.getTypeString();
    String name = item.name.stringData.string;

    countField++;
    if (Checker.checkField(item)) {

      String encryptedName = null;

      if (DEBUG_MODE) {
        if (!debugNameSet.contains(name)) {
          return;
        }
      }

      if (blackFieldSet.contains(name)) return;

      encryptedName = "_" + EncryptWithMD5(name);
      item.name.stringData.string = encryptedName;
      Checker.appendEncryptedSet(encryptedName);
      Debuger.AddDoEncryptInfoField(clazz, name, type, encryptedName);
    }
  }

  private int countMethod = 0;

  private void handleMethodItem(MethodIdItem item) {
    String clazz = item.clazz.descriptor.stringData.string;
    String name = item.name.stringData.string;

    if (Checker.checkMethod(item)) {

      //锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷姆锟斤拷锟斤拷锟斤拷筒锟斤拷锟斤拷锟�
      if (blackFieldSet.contains(name)) return;

      String encryptedName = "A" + EncryptWithMD5(name);      //锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷A锟斤拷头
      item.name.stringData.string = encryptedName;
      Checker.appendEncryptedSet(encryptedName);
      Debuger.AddDoEncryptInfoMethod(name, encryptedName);
    }
  }

  private String EncryptWithMD5(String OriData) {
    String result = null;
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] buff = md.digest(OriData.getBytes());
      result = bytesToHex(buff);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return result;
  }

  public static String bytesToHex(byte[] bytes) {
    StringBuffer md5str = new StringBuffer();
    int digital;
    for (int i = 0; i < bytes.length; i++) {
      digital = bytes[i];
      if (digital < 0) {
        digital += 256;
      }
      if (digital < 16) {
        md5str.append("0");
      }
      md5str.append(Integer.toHexString(digital));
    }
    return md5str.toString().toUpperCase();
  }
}
