package com.example.zk.myapplication.com;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;

public class RootCmd {
	private static boolean mHaveRoot = false;

	public static boolean haveRoot() {
		if (!mHaveRoot) {
			int ret = execRootCmdSilent("echo test");
			if (ret != 1) {
				System.out.println("have root!");
				mHaveRoot = true;
			} else {
				System.out.println("not root!");
			}
		} else {
			System.out.println("mHaveRoot = true, have root!");
		}
		return mHaveRoot;
	}

	// @SuppressWarnings("deprecation")
		public static String execRootCmd(String cmd) {
			String result = "";
			DataOutputStream dos = null;
			InputStreamReader isr = null;

			try {
				Process p = Runtime.getRuntime().exec("su");
				dos = new DataOutputStream(p.getOutputStream());
				// dis = new DataInputStream(p.getInputStream());
				Log.e("CMD", "cmd:   "+cmd);
				dos.writeBytes(cmd + "\n");
				dos.flush();
				dos.writeBytes("exit\n");
				dos.flush();

				InputStream stdin = p.getInputStream();
				isr = new InputStreamReader(stdin);
				BufferedReader br = new BufferedReader(isr);
				String line = null;

				while ((line = br.readLine()) != null) {
					result += (line + "\n");
				}
				 
				
//				Log.i("wodelog", "result:   "+result);
				p.waitFor();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (dos != null) {
					try {
						dos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (isr != null) {
					try {
						isr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			return result;

		}
	
	
	

	public static int execRootCmdSilent(String cmd) {
		int result = -1;
		DataOutputStream dos = null;

		try {
			Process p = Runtime.getRuntime().exec("su");
			dos = new DataOutputStream(p.getOutputStream());
			System.out.println(cmd);

			dos.writeBytes(cmd + "\n");
			dos.flush();
			 
			dos.flush();
			p.waitFor();
			result = p.exitValue();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dos != null) {
				try {
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public static String getVersion(){
		String cmd = "getprop ro.build.version.release";
		return execRootCmd(cmd);
	}
}