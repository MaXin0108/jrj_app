package com.kiloseed.system.util;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import com.kiloseed.system.helper.Constant;

public class FileUtils {
	static PrintStream ps;

	/**
	 * 初始化日志输出流
	 */
	public static void initPrintStream() {
		try {
			makeDir(Constant.DATA_DIRECTORY);
			File file = new File(Constant.DATA_DIRECTORY, "log.txt");
			if (!file.exists())
				file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			ps = new PrintStream(fos);
		} catch (Exception tr) {
			// TODO: handle exception
			Log.e(Constant.TAG, LogUtil.getCurDate() + tr.getMessage(), tr);
		}
	}

	/**
	 * 获得输出流
	 * 
	 * @return
	 */
	public static PrintStream getPrintStream() {
		if (ps == null) {
			initPrintStream();
		}
		return ps;
	}

	/**
	 * 记录异常
	 * 
	 * @param tr
	 */
	public static void logError(Throwable tr) {
		tr.printStackTrace(getPrintStream());
	}

	public static void logError(String msg, Throwable tr) {
		PrintStream ps = getPrintStream();
		ps.println(msg);
		tr.printStackTrace(ps);
	}

	public static void logInfo(String msg) {
		PrintStream ps = getPrintStream();
		ps.println(msg);
	}

	/**
	 * 关闭输入流
	 * 
	 * @param is
	 */
	public static void closeInputStream(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				LogUtil.e(e);
			}
		}
	}

	/**
	 * 关闭输出流
	 * 
	 * @param os
	 */
	public static void closeOutputStream(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				LogUtil.e(e);
			}
		}
	}

	/**
	 * 获得文件保存路径
	 * 
	 * @return
	 */
	public static String getSavePath(String fileName) {
		File sdDir = getSavePath();
		return sdDir != null ? sdDir.toString() + "/" + fileName : fileName;
	}

	/**
	 * 获得文件保存路径
	 * 
	 * @return
	 */
	public static File getSavePath() {
		File sdDir = new File(Constant.DATA_DIRECTORY);
		if (!sdDir.exists()) {
			sdDir.mkdirs();
		}
		/*
		 * boolean sdCardExist = Environment.getExternalStorageState()
		 * .equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在 if
		 * (sdCardExist) { sdDir =
		 * Environment.getExternalStorageDirectory();//获取跟目录 }
		 */
		return sdDir;
	}

	/**
	 * 将附件流程写入到一个文件中
	 * 
	 * @param in
	 * @return
	 */
	public static String writeFile(InputStream in, String dirpath) {
		File file = new File(dirpath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String path = dirpath + "/" + UUIDUtil.getUUID();
		try {
			FileOutputStream fs = new FileOutputStream(path);
			byte[] buffer = new byte[1024];
			int byteread = 0;
			while ((byteread = in.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
			}
			fs.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return path;
	}

	/**
	 * 获取要产生的图片路径
	 * 
	 * @return
	 */
	public static String getImgPath() {
		return getSavePath() + "/" + UUIDUtil.getUUID() + ".jpg";
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 */
	public static void deleteFileByPath(String filePath) {
		if (isExists(filePath)) {
			new File(filePath).delete();
		}
	}

	/**
	 * 验证文件是否存在
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean isExists(String filePath) {
		if (filePath == null || filePath.length() == 0)
			return false;
		File file = new File(filePath);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 文件加密与解密
	 * 
	 * @param filePath
	 */
	public static void fileEncryptAndDecrypt(String picPath) {
		if (!isExists(picPath))
			return;
		final int header_length = 10;// 要修改的字节数
		FileChannel fc = null;
		try {
			fc = new RandomAccessFile(picPath, "rw").getChannel();
			// 注意，文件通道的可读可写要建立在文件流本身可读写的基础之上
			MappedByteBuffer out = fc.map(FileChannel.MapMode.READ_WRITE, 0, header_length); // 读取头44个字节
			byte[] header = new byte[header_length];
			for(int i=0;i<header_length;i++){
				header[i] = out.get(i);
			}
			//取反
			header = byteChange(header);
			out.put(header); // 重新头文件
		} catch (FileNotFoundException e) {
			LogUtil.e("没有找到文件：" + picPath + "！无法进行加密！");
		} catch (IOException e) {
			LogUtil.e("IO异常！无法进行加密！");
		} finally {
			if (fc != null) {
				try {
					fc.close();
				} catch (IOException e) {
					LogUtil.e("IO异常！无法进行加密！");
				}
			}
		}
	}
	
	/**
	 * 字节取反
	 * @param buff
	 * @return
	 */
	public static byte[] byteChange(byte[] buff){
		for (int i = 0; i < buff.length; i++) {
			int b = 0;
			for (int j = 0; j < 8; j++) {
				int bit = (buff[i] >> j & 1) == 0 ? 1 : 0;
				b += (1 << j) * bit;
			}
			buff[i] = (byte) b;
		}
		return buff;
	}

	/**
	 * 如果目录不存在，建立目录
	 * @param dir
	 */
	public static void makeDir(String dir){
		File file=new File(dir);
		if(!file.exists()){
			file.mkdirs();
		}
	}
}
