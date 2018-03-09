package com.encrypter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googlecode.d2j.dex.writer.DexFileWriter;
import com.googlecode.d2j.dex.writer.item.ClassDataItem;
import com.googlecode.d2j.reader.DexFileReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by chago on 2017-10-14.
 */

public class DecryptedByOAT extends Encrypter {
  public static String fake_op_map_real_op_path =
      "360_op_mapping_android_op.txt";
  public static String op_map_length_path =
      "json_object_op_mapping_len.txt";
  public static String outputPath = "decrypted_all_code_dex.dex";

  private static Object[][] memoryDetailArray = {
      { 0x486DC000, 0x48766000, "code_index.bin" },//全局变量所在的内存
      { 0x49F94000, 0x4B829000, "49F94000-4B829000.Dump" },//oat文件
      { 0x48C3F000, 0x48C84000, "48C3F000-48C84000.Dump" },//方法节点又在的内存
      { 0x4423F000, 0x44247000, "4423F000-44247000.Dump" },//方法节点又在的内存
      { 0x4422A000, 0x44237000, "4422A000-44237000.Dump" },//方法节点又在的内存
      { 0x4420C000, 0x44227000, "4420C000-44227000.Dump" },//方法节点又在的内存
  };
  private static ArrayList<byte[]> memoryProxy = new ArrayList<>();
  private static int first_index_bin_start_ptr = 0x4875B5E0;
  public static int dex_ptr = 0x49F9AE24;//49F9AE24
  public static int oat2dexHeadLen = 0x6E24;
  static int nativeMethodCount = 0;
  private static ArrayList<Integer> methodStruct = new ArrayList<>();
  public static Map func2xro = new HashMap();
  private static Map<String, String> opcodeMap;
  private static Map<String, String> opcodeLenMap;
  private static DexFileWriter w = new DexFileWriter();

  public static void main(String[] args) {
    try {
      Gson gson = new Gson();
      opcodeMap =
          gson.fromJson(readString(fake_op_map_real_op_path), new TypeToken<Map<String, String>>() {
          }.getType());
      opcodeLenMap =
          gson.fromJson(readString(op_map_length_path), new TypeToken<Map<String, String>>() {
          }.getType());
      /**初始化虚拟内存**/
      for (int i = 0; i < memoryDetailArray.length; i++) {
        File fileHandle = new File((String) memoryDetailArray[i][2]);
        byte[] bytes = new byte[(int) fileHandle.length()];
        new DataInputStream(new FileInputStream(fileHandle)).read(bytes, 0,
            (int) fileHandle.length());
        memoryProxy.add(i, bytes);
      }
      /**遍历方法树，获取方法结构**/
      Stack<Integer> nodeStack = new Stack<Integer>();
      nodeStack.add(first_index_bin_start_ptr);
      int treeNodeCunt = 0;//包含根节点
      while (!nodeStack.isEmpty()) {
        Integer node = nodeStack.pop();
        //System.out.println("正在遍历的节点 " + node);
        methodStruct.add(bytesToInt(getData(node, 28, 4)));
        treeNodeCunt++;
        //获得节点的子节点，对于二叉树就是获得节点的左子结点和右子节点
        List<Integer> children = getChildrens(node);
        if (children != null && !children.isEmpty()) {
          for (int ptr : children) {
            nodeStack.push(ptr);
          }
        }
      }
      System.out.println("遍历完毕,一共" + treeNodeCunt + "个节点");

      for (int ptr : methodStruct) {
        String str = getStr(ptr, 80);//shorty的偏移是80
        if (str.contains("com.aisidi")) {
          System.out.println("ptr " + ptr);
          int first = bytesToInt(getData(ptr, 4, 4));
          System.out.println("first " + first);
          int second = bytesToInt(getData(first, 0, 4));
          System.out.println("second " + second);
          int third = bytesToInt(getData(second, 12, 4));//指向方法体
          System.out.println("third " + third);
          int code_padding = third + 16;//方法体后16个字节才是指令入口
          String shorty = str.substring(0, str.length() - 1);

          func2code.put(shorty, third);
          byte exo =
              (byte) (getData(second, 0, 1)[0] ^ getData(second, 4, 1)[0] ^ getData(dex_ptr, third,
                  1)[0] ^ 0x30);
          func2xro.put(shorty, exo);
          System.out.println("方法名 "
              + shorty
              + " 偏移 "
              + code_padding
              + " 真实地址 "
              + (third + dex_ptr)
              + " 异或因子 "
              + Test.getHexBinary(new byte[] { exo }));
          System.out.println("*************");
        }
      }
      /**开始修复**/
      DexFileReader dexFileReader = new DexFileReader(getData(dex_ptr, 0, 0x188E1DC));
      dexFileReader.accept(w);

      List<ClassDataItem> classDataList =
          Arrays.asList(w.cp.classDataItems.toArray(new ClassDataItem[] {}));
      int size = classDataList.size();
      for (int i = 0; i < size; ++i) {
        handleClassDataItem(classDataList.get(i), memoryProxy.get(1));
      }
      /**直接输出dex字节数组**/
      File outPutFile = new File(outputPath);
      FileOutputStream fs = new FileOutputStream(outPutFile);
      fs.write(getData(memoryProxy.get(1), oat2dexHeadLen, 0x188E1DC));

      fs.flush();
      fs.close();
      System.out.println("一共修复" + nativeMethodCount + "个native方法");
      System.out.println("修复完成");
    } catch (Exception e) {
      System.out.print(e);
      e.printStackTrace();
    }
  }

  public static void handleClassDataItem(ClassDataItem item, byte[] oatStruct) {
    int size = item.virtualMethods.size();
    for (int i = 0; i < size; ++i) {
      String name = item.virtualMethods.get(i).method.name.stringData.string;
      String clazz = item.virtualMethods.get(i).method.clazz.descriptor.stringData.string;

      String shorty = clazz + "->" + name;
      String shorty360 = clazz.replace(";", "").replace("/", ".") + "->" + name;
      shorty360 = shorty360.substring(1, shorty360.length());
      if (func2code.containsKey(shorty360)) {
        System.out.println(shorty);
        //修改access
        int accessPos = (int) Encrypter.methodAccessFlagsPos.get(shorty);
        oatStruct[oat2dexHeadLen + accessPos] = 0x4;
        int codeOffPos = oat2dexHeadLen + accessPos + 1;
        //修改code_off
        byte[] codeOffBytes = doUleb128((Integer) func2code.get(shorty360));
        for (int i1 = 0; i1 < codeOffBytes.length; ++i1) {
          oatStruct[codeOffPos + i1] = codeOffBytes[i1];
        }
        //解密代码
        int insnsOffset = (int) (func2code.get(shorty360)) + oat2dexHeadLen + 16;//代码入口
        byte xorValue = (byte) func2xro.get(shorty360);
        //System.out.println("基于dex偏移 " + ((int) func2code.get(shorty360) + 0x49F9AE24 + 16));
        //System.out.println(
        //    "基于dex偏移 " + Integer.toHexString(((int) func2code.get(shorty360) + 0x49F9AE24 + 16)));
        //System.out.println("异或因子 " + xorValue);
        int cursor = 0;
        byte op = -1;
        while (op != 0x0e) {
          System.out.println("当前游标 " + Integer.toHexString(cursor));
          byte[] encrypted_opcode = getData(oatStruct, insnsOffset + cursor, 2);
          System.out.println("异或前op指令 " + encrypted_opcode[0]);
          System.out.println("异或前op参数指令 " + encrypted_opcode[1]);
          byte firstByte = (byte) (encrypted_opcode[0] ^ xorValue);
          byte secondByte = (byte) (encrypted_opcode[1] ^ xorValue);

          System.out.println("解密前指令 " + (firstByte & 0xFF));
          System.out.println("置换表返回 " + opcodeMap.get(String.valueOf(firstByte & 0xFF)));
          System.out.println(
              "置换表返回转int " + Integer.parseInt(opcodeMap.get(String.valueOf(firstByte & 0xFF)), 16));
          firstByte = (byte) Integer.parseInt(opcodeMap.get(String.valueOf(firstByte & 0xFF)), 16);
          op = firstByte;
          System.out.println("解密后指令 " + Integer.toHexString(firstByte & 0xFF));
          oatStruct[insnsOffset + cursor] = firstByte;
          oatStruct[insnsOffset + cursor + 1] = secondByte;
          cursor += 2;
          int opLen = Integer.valueOf(opcodeLenMap.get(Integer.toHexString(firstByte & 0xff)));
          System.out.println("opcode长度 " + opLen);
          for (int i2 = 0; i2 < opLen - 1; i2++) {
            encrypted_opcode = getData(oatStruct, insnsOffset + cursor, 2);
            firstByte = (byte) (encrypted_opcode[0] ^ xorValue);
            secondByte = (byte) (encrypted_opcode[1] ^ xorValue);
            oatStruct[insnsOffset + cursor] = firstByte;
            oatStruct[insnsOffset + cursor + 1] = secondByte;
            cursor += 2;
          }
          System.out.println("**********************************");
        }
        System.out.println("指令总长度 " + cursor);
        System.out.println("解密后指令组 " + Test.getHexBinary(getData(oatStruct, insnsOffset, cursor)));
        nativeMethodCount++;
        System.out.println("**********************************");
      }
    }
  }

  private static List<Integer> getChildrens(int node) {
    int firstPtr = bytesToInt(getData(getMemoryByAddr(node), node - getMemoryStartByAddr(node), 4));
    int secondPtr =
        bytesToInt(getData(getMemoryByAddr(node), node - getMemoryStartByAddr(node) + 4, 4));
    ArrayList<Integer> arrayList = new ArrayList();
    if (firstPtr != 0) {
      arrayList.add(firstPtr);
    }
    if (secondPtr != 0) {
      arrayList.add(secondPtr);
    }
    return arrayList;
  }

  public static String getStr(int ptr, int offset) {
    return getStr(getMemoryByAddr(ptr), ptr - getMemoryStartByAddr(ptr) + offset);
  }

  public static byte[] getData(int ptr, int offset, int len) {
    return getData(getMemoryByAddr(ptr), ptr - getMemoryStartByAddr(ptr) + offset, len);
  }

  private static String readString(String fileName) {
    String str = "";
    File file = new File(fileName);
    try {
      FileInputStream in = new FileInputStream(file);
      // size  为字串的长度 ，这里一次性读完
      int size = in.available();
      byte[] buffer = new byte[size];
      in.read(buffer);
      in.close();
      str = new String(buffer, "GB2312");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }

    return str;
  }

  public static byte[] getMemoryByAddr(int addr) {
    for (int i = 0; i < memoryDetailArray.length; i++) {
      if (addr > (int) memoryDetailArray[i][0] && addr < (int) memoryDetailArray[i][1]) {
        return memoryProxy.get(i);
      }
    }
    System.out.println("找不到此内存位置 " + addr);
    return null;
  }

  public static int getMemoryStartByAddr(int addr) {
    for (int i = 0; i < memoryDetailArray.length; i++) {
      if (addr > (int) memoryDetailArray[i][0] && addr < (int) memoryDetailArray[i][1]) {
        return (int) memoryDetailArray[i][0];
      }
    }
    System.out.println("找不到此内存位置 " + addr);
    return 0;
  }

  public static int getMemoryEndByAddr(int addr) {
    for (int i = 0; i < memoryDetailArray.length; i++) {
      if (addr > (int) memoryDetailArray[i][0] && addr < (int) memoryDetailArray[i][1]) {
        return (int) memoryDetailArray[i][1];
      }
    }
    System.out.println("找不到此内存位置 " + addr);
    return 0;
  }
}
