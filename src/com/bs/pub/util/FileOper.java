package com.bs.pub.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

/**
 * �ļ�����
 * @author ���п�
 *
 */
public class FileOper {
	/**
	 * ���Ƶ����ļ�
	 * 
	 * @param oldPath
	 *            String ԭ�ļ�·�� �磺c:/fqf.txt
	 * @param newPath
	 *            String ���ƺ�·�� �磺f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) throws Exception {
		File f1 = new File(oldPath);
		File f2 = new File(newPath);
		copyFile(f1,f2);
	}
	/**
	 * �ܵ��Թܵ������ļ�
	 * <p>�������ƣ�        </p>
	 * <p>����˵����
	 *
	 * </p>
	 *<p>����˵����</p>
	 * @param f1
	 * @param f2
	 * @return
	 * @throws Exception
	 *
	 * @date   ����ʱ�䣺2011-8-26
	 * @author ���ߣ����п�
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
