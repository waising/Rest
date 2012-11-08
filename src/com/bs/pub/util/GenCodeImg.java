package com.bs.pub.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class GenCodeImg {
	private String code;
	private Color getRandColor(int fc,int bc){//������Χ��������ɫ
        Random random = new Random();
        if(fc>255) fc=255;
        if(bc>255) bc=255;
        int r=fc+random.nextInt(bc-fc);
        int g=fc+random.nextInt(bc-fc);
        int b=fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
    }
	
	/**
	 * ������֤��ͼƬ��������ͼƬ��
	 * <p>�������ƣ�        </p>
	 * <p>����˵����
	 *
	 * </p>
	 *<p>����˵����</p>
	 * @return
	 *
	 * @date   ����ʱ�䣺2011-12-31
	 * @author ���ߣ����п�
	 */
	public ByteArrayInputStream getImg(){
		int width=65, height=25;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// ��ȡͼ��������
		Graphics g = image.getGraphics();

        //������������� 
        Random random = new Random();
        g.setColor(getRandColor(230,250));
		g.fillRect(0, 0, width-1, height-1);
        //��ͼƬ�ı��������� 
        for (int i = 0; i < 25; i++)
        {
            int x1 = random.nextInt(width);
            int x2 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int y2 = random.nextInt(height);
            
            g.setColor(getRandColor(210,230));
            g.drawLine(x1,y1,x1+x2,y1+y2);
        }
        
        //�趨����
        String familys[]={"Bookman Old Style","Arial","Times New Roman","Book antiqua",""};
        int styles[]={Font.PLAIN,Font.BOLD,Font.ITALIC};
        String texts="1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        
		// ȡ�����������֤��(4λ����)
		String sRand="";
		for (int i=0;i<4;i++){
		    int index=random.nextInt(34);
		    String rand = texts.substring(index, index+1);
		    sRand+=rand;
		    // ����֤����ʾ��ͼ����
		    String family = familys[random.nextInt(5)];
		    int style= styles[random.nextInt(3)];
		    g.setFont(new Font(family,style,random.nextInt(4)+18));
		    g.setColor(new Color(20+random.nextInt(100),20+random.nextInt(100),20+random.nextInt(100)));//���ú�����������ɫ��ͬ����������Ϊ����̫�ӽ�������ֻ��ֱ������
		    g.drawString(rand,13*i+6,19);
		}

		for (int i=0;i<5;i++)
		{
		 g.setColor(getRandColor(110,200));
		 int x = random.nextInt(width-4);
		 int y = random.nextInt(height-4);
		 int xl = random.nextInt(width-4);
		 int yl = random.nextInt(height-4);
		 g.drawLine(x,y,x+xl,y+yl);
		}

		//g.setColor(new Color());
		g.drawRect(0,0,width,height);
		
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		ByteArrayInputStream in = null;
		try {
			ImageIO.write(image, "GIF", os);
			in=new ByteArrayInputStream(os.toByteArray());//ת��Ϊ��������
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				os.flush();
				os.close();
			} catch (IOException e) {
			}
		}
		
		this.setCode(sRand);
		return in;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	}

}
