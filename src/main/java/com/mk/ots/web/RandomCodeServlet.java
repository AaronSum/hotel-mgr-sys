package com.mk.ots.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mk.ots.utils.Tools;

public class RandomCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 0L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// init
		response.setContentType("image/jpeg");
		// response.setCharacterEncoding("utf-8");
		// 设置缓存为空
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		// out
		// PrintWriter out = response.getWriter();
		int c = Tools.getIntValue(request.getParameter("c"), 0);
		if (c > 0) {
			String a = request.getParameter("a");
			this.drawImage(c, a, response);
			return;
		}
		int m = Tools.getIntValue(request.getParameter("m"), 1);
		int t = Tools.getIntValue(request.getParameter("t"), 0);
		if ((m > 2) && (t == 0)) {
			m = 2;
		}

		try {
			// 在内存中创建图象
			int width = 60 * m, height = 20 * m;
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			// 获取图形上下文
			Graphics g = image.getGraphics();

			// 生成随机类
			Random random = new Random();

			// 设定背景色
			g.setColor(this.getRandColor(200, 250));
			g.fillRect(0, 0, width, height);

			// 设定字体
			g.setFont(new Font("Times New Roman", Font.PLAIN, 18 * m));

			// 画边框
			// g.setColor(new Color());
			// g.drawRect(0,0,width-1,height-1);

			// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
			g.setColor(this.getRandColor(150, 200));
			for (int i = 0; i < 100; i++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(10 * m);
				int yl = random.nextInt(10 * m);
				for (int j = 0; j < m; j++) {
					g.drawLine(x, y + j, x + xl, y + yl + j);
				}
			}

			// 取随机产生的认证码(4位数字)
			String sRand = "";
			for (int i = 0; i < 4; i++) {
				int s = random.nextInt(36);
				String rand = null;
				if (s < 10) {
					rand = String.valueOf(s);
				} else {
					rand = String.valueOf((char) (s + 55));
				}
				sRand += rand;
				// 将认证码显示到图象中
				g.setColor(new Color(90 + random.nextInt(60), 90 + random.nextInt(60), 90 + random.nextInt(60)));
				// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
				g.drawString(rand, (13 * m * i) + (6 * m), 16 * m);
			}

			// 将认证码存入SESSION
			request.getSession().setAttribute("validateCode", sRand);

			// 图象生效
			g.dispose();

			// 输出图象到页面
			try {
				ImageIO.write(image, "JPEG", response.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 获取随机颜色
	 *
	 * @param fc
	 *            随机数1
	 * @param bc
	 *            随机数2
	 * @return 随机颜色实例
	 */
	private Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	private void drawImage(int count, String code, HttpServletResponse response) {
		try {
			// 在内存中创建图象
			int width = (700 / count) - 10, height = 700 / count;
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			// 获取图形上下文
			Graphics g = image.getGraphics();

			// 生成随机类
			Random random = new Random();

			// 设定背景色
			g.setColor(this.getRandColor(230, 250));
			g.fillRect(0, 0, width, height);

			// 设定字体
			g.setFont(new Font("宋体", Font.PLAIN, 24));

			// 取随机产生的认证码(4位数字)
			String str = code, str2 = "";
			int line = 1, lw = 4;
			;
			if (line == 1) {
				// 将认证码显示到图象中
				g.setColor(new Color(60 + random.nextInt(60), 60 + random.nextInt(60), 60 + random.nextInt(60)));
				// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
				g.drawString(str, (width - (lw * 12)) / 2, (height * 2) / 5);
			} else {
				// 将认证码显示到图象中
				g.setColor(new Color(90 + random.nextInt(60), 90 + random.nextInt(60), 90 + random.nextInt(60)));
				// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
				g.drawString(str, (width - (lw * 12)) / 2, height / 3);

				// 将认证码显示到图象中
				g.setColor(new Color(90 + random.nextInt(60), 90 + random.nextInt(60), 90 + random.nextInt(60)));
				// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
				g.drawString(str2, (width - (4 * 12)) / 2, (height * 3) / 5);
			}
			// 图象生效
			g.dispose();

			// 输出图象到页面
			try {
				ImageIO.write(image, "JPEG", response.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
		}
	}
}
