package com.proj.androidlib.tool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.util.Log;

/**
 * 加解密工具类
 *
 */
public class CryptionUtil {
	/**
	 * 默认扩展名“.bin”
	 * 
	 */
	public static String CRYPTION_EXTENSION_NAME = ".bin";
	private static String sPswd = "KINGDON";
	private short[] S = new short[256];
	private short i = 0;
	private short j = 0;
	private short tmp = 0;

	public CryptionUtil() {
		byte[] b = sPswd.getBytes();
		crypto(b, b.length);
	}

	/**
	 * @param strPswd
	 *            密码
	 * @param strExtName
	 *            文件后缀名 ,如：“.bin”
	 */
	public CryptionUtil(String strPswd, String strExtName) {
		sPswd = strPswd;
		CRYPTION_EXTENSION_NAME = strExtName;
		byte[] b = sPswd.getBytes();
		crypto(b, b.length);
	}

	private void crypto(byte[] K, int keylen) {
		for (i = 0; i < 0x100; ++i) {
			S[i] = i;
		}
		j = 0;
		for (i = 0; i < 0x100; ++i) {
			j = (short) ((j + S[i] + K[i % keylen]) % 0x100);
			tmp = S[i];
			S[i] = S[j];
			S[j] = tmp;
		}
		i = 0;
		j = 0;
	}

	/**
	 * 加/解密字节流
	 * 
	 * @return
	 */
	public byte cryptoStream() {
		i = (short) ((i + 1) % 256);
		j = (short) ((j + S[i]) % 256);
		tmp = S[i];
		S[i] = S[j];
		S[j] = tmp;
		tmp = (short) ((S[i] + S[j]) % 256);
		return (byte) S[tmp];
	}

	/**
	 * 加密文件
	 * 
	 * @param file
	 *            原文件路径
	 * @param cFile
	 *            加密后文件路径
	 */

	public void fileEncryption(String file, String cFile) {
		long l = System.currentTimeMillis();
		Log.d("MyTag", "加密开始时间：" + l);
		BufferedInputStream is = null;
		BufferedOutputStream os = null;
		try {
			is = new BufferedInputStream(new FileInputStream(file));
			os = new BufferedOutputStream(new FileOutputStream(cFile
					+ CRYPTION_EXTENSION_NAME));
			byte[] buf = new byte[1024 * 4];
			int i = 0;
			while (is.read(buf, i, 1) > 0) {
				buf[0] ^= cryptoStream();
				os.write(buf, i, 1);
			}
			is.close();
			os.flush();
			os.close();
			Log.d("MyTag", "加密成功" + (System.currentTimeMillis() - l));
		} catch (Exception e) {
			Log.d("MyTag", "加密失败" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 加密后文件路径
	 * 
	 * @param cFile
	 *            加密后文件路径
	 * @return 文件名
	 */
	public String fileDecryption(String cFile) {
		long l = System.currentTimeMillis();
		Log.d("MyTag", "解密开始时间：" + l);
		String file = cFile;
		if (cFile.endsWith(CRYPTION_EXTENSION_NAME)) {
			BufferedInputStream is = null;
			BufferedOutputStream os = null;
			file = cFile.replace(CRYPTION_EXTENSION_NAME, "");
			try {
				is = new BufferedInputStream(new FileInputStream(cFile));
				os = new BufferedOutputStream(new FileOutputStream(file));
				byte[] buf = new byte[1024 * 4];
				int i = 0;
				while (is.read(buf, i, 1) > 0) {
					buf[0] ^= cryptoStream();
					os.write(buf, i, 1);
				}
				is.close();
				os.flush();
				os.close();
				Log.d("MyTag", "解密成功" + (System.currentTimeMillis() - l));
			} catch (Exception e) {
				Log.d("MyTag", "解密失败" + e.getMessage());
				e.printStackTrace();
			}
		}
		return file;
	}
}
