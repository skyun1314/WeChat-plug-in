package com.encrypter;

/**
 * Created by chago on 2017-10-21.
 */

public class Test {

  public static void main(String[] args) {
    System.out.print(getHexBinary(Encrypter.doUleb128(0x4)));
    System.out.print("\n");
    System.out.print(Encrypter.lengthOfUleb128(0x4));
  }

  private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

  public static String getHexBinary(byte[] data) {
    StringBuilder r = new StringBuilder(data.length * 2);
    for (byte b : data) {
      r.append(hexCode[(b >> 4) & 0xF]);
      r.append(hexCode[(b & 0xF)]);
    }
    return r.toString();
  }

  public static String getHexBinary(byte data) {
    return getHexBinary(new byte[data]);
  }
}
