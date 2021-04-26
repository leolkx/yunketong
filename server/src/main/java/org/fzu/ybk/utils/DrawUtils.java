package org.fzu.ybk.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;


public class DrawUtils {


    private static final String[] FONT_TYPES = { "\u5b8b\u4f53", "\u65b0\u5b8b\u4f53", "\u9ed1\u4f53", "\u6977\u4f53", "\u96b6\u4e66" };

    private static final int VALICATE_CODE_LENGTH = 4;

    private final Logger logger = LoggerFactory.getLogger(DrawUtils.class);

    /**
     * 设置背景颜色及大小，干扰线
     *
     * @param graphics
     * @param width
     * @param height
     */
    private void fillBackground(Graphics graphics, int width, int height) {
        // 填充背景
        graphics.setColor(Color.WHITE);
        //设置矩形坐标x y 为0
        graphics.fillRect(0, 0, width, height);

        Random random = new Random();
        // 加入干扰线条
        for (int i = 0; i < 8; i++) {
            //设置随机颜色算法参数
            graphics.setColor(this.randomColor(40, 150));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            graphics.drawLine(x, y, x1, y1);
        }
    }

    /**
     * 生成随机字符
     *
     * @param width
     * @param height
     * @param os
     * @return
     * @throws IOException
     */

    public void draw(String randomStr, int width, int height, OutputStream os) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        this.fillBackground(graphics, width, height);
        this.createCharacter(graphics, randomStr);
        graphics.dispose();
        //设置JPEG格式
        ImageIO.write(image, "JPEG", os);
    }



    /**
     * 设置字符颜色大小
     *
     * @param g
     * @param randomStr
     */
    private void createCharacter(Graphics g, String randomStr) {
        Random random = new Random();

        char[] charArray = randomStr.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            //设置RGB颜色算法参数
            g.setColor(new Color(50 + random.nextInt(100),
                    50 + random.nextInt(100), 50 + random.nextInt(100)));
            //设置字体大小，类型
//            Font.PLAIN(0) + Font.BOLD(1) +  Font.ITALIC(2) =>   random.nextInt(4)
            g.setFont(new Font(FONT_TYPES[random.nextInt(FONT_TYPES.length)], random.nextInt(4), 26));
            //设置x y 坐标
            g.drawString(String.valueOf(charArray[i]), 15 * i + 5, 19 + random.nextInt(8));
        }
    }

    private Color randomColor(int fc, int bc) {
        int f = fc;
        int b = bc;
        Random random = new Random();
        if (f > 255) {
            f = 255;
        }
        if (b > 255) {
            b = 255;
        }
        return new Color(f + random.nextInt(b - f), f + random.nextInt(b - f), f + random.nextInt(b - f));
    }
}
