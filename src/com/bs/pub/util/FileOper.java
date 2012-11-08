package com.bs.pub.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

/**
 * 文件操作
 * @author 李中俊
 *
 */
public class FileOper {
	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) throws Exception {
		File f1 = new File(oldPath);
		File f2 = new File(newPath);
		copyFile(f1,f2);
	}
	/**
	 * 管道对管道复制文件
	 * <p>函数名称：        </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param f1
	 * @param f2
	 * @return
	 * @throws Exception
	 *
	 * @date   创建时间：2011-8-26
	 * @author 作者：李中俊
	 */
	 public static long copyFile(File f1,File f2) throws Exception{
	        long time=new Date().getTime();
	        int length=2097152;
	        FileInputStream in=new FileInputStream(f1);
	        FileOutputStream out=new FileOutputStream(f2);
	        FileChannel inC=in.getChannel();
	        FileChannel outC=out.getChannel();
	        ByteBuffer b=null;
	        while(true){
	            if(inC.position()==inC.size()){
	                inC.close();
	                outC.close();
	                return new Date().getTime()-time;
	            }
	            if((inC.size()-inC.position())<length){
	                length=(int)(inC.size()-inC.position());
	            }else
	                length=2097152;
	            b=ByteBuffer.allocateDirect(length);
	            inC.read(b);
	            b.flip();
	            outC.write(b);
	            outC.force(false);
	        }
	 }
	public static void main(String[] args){
		try {
			FileOper.copyFile("I:\\pay.log",
					"I:\\pay2.log");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
