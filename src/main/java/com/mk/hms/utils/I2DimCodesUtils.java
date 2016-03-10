package com.mk.hms.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码工具类
 * @author hdy
 *
 */
public class I2DimCodesUtils {

	private static final Logger logger = LoggerFactory.getLogger(I2DimCodesUtils.class);
	//private static final int BLACK = 0xff000000;
    //private static final int WHITE = 0xFFFFFFFF;
    
	/** 二维码宽度*/
	private static final int width = 150;
	/** 二维码高度*/
	private static final int height = 150;
	/** icon x坐标*/
	private static final int x = 58;
	/** icon y坐标*/
	private static final int y = 58;
	
	public static void drawI2DimCode2OutputStream(String content, String icon, HttpServletRequest request, HttpServletResponse response) {
		try {
			//B规则二维码显示
			String bRuleConfig = HmsFileUtils.getSysContentItem(ContentUtils.RULE_B_URL);
			//如果B规则直接显示公众账号图片
			if(content.equals(bRuleConfig)){
				logger.info("B规则二维码显示处理");
				//showqrcode图片路径
				String imgPath = request.getSession().getServletContext().getRealPath("/") + "/images/showqrcode.png";
				//加载二维码图片
				BufferedImage image = ImageIO.read(new FileInputStream(imgPath));
				//画图
				ImageIO.write(image, "png", response.getOutputStream());
			}else{
				logger.info("A规则二维码显示处理");
				//A规则二维码显示
				Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>(); 
		        // 指定纠错等级  
		        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		        // 指定编码格式  
		        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		        // 边框留白，默认为4
		        hints.put(EncodeHintType.MARGIN, 0);
				BitMatrix byteMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
				BufferedImage image = MatrixToImageWriter.toBufferedImage(byteMatrix);
				/*int width = byteMatrix.getWidth();
				int height = byteMatrix.getHeight();
				BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						image.setRGB(x, y, byteMatrix.get(x, y) ? BLACK : WHITE);
					}
				}*/
	            File f = null;
	            Graphics2D gs = image.createGraphics();
				try {
					if (StringUtils.isNotBlank(icon)) {
						gs.setBackground(Color.WHITE);
			            gs.clearRect(x, y, 33, 33);
			            Font font = new Font(null, Font.PLAIN, 30);
			            gs.setFont(font);
			            gs.setPaint(Color.BLACK);
			            gs.drawRoundRect(x + 1 , y + 1, 30, 30, 5, 5);
			            gs.drawString(icon, x + 7, y + 28);
					} else {
						f = new File(request.getSession().getServletContext().getRealPath("/") + "/images/2dimcode-icon.png");
						Image img = ImageIO.read(f);//实例化一个Image对象
						gs.drawImage(img, x, y, 35, 35, null);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					gs.dispose();
					image.flush();
		            ImageIO.write(image, "png", response.getOutputStream());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * 生成条形码
	 * @param content 内容
	 * @param width 宽度
	 * @param height 高度
	 */
	public static void drawEAN13code2OutputStream(String content, int width, int height) {
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		int codeWidth = 3 + // start guard
                (7 * 6) + // left bars
                5 + // middle guard
                (7 * 6) + // right bars
                3; // end guard
        codeWidth = Math.max(codeWidth, width);
        BufferedImage image = null;
        try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.EAN_13, codeWidth, height, null);
			image = MatrixToImageWriter.toBufferedImage(bitMatrix);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			image.flush();
			try {
				ImageIO.write(image, "png", response.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
