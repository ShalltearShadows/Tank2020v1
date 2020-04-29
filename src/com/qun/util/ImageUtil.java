/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/4/26
 * @Time: 16:55
 */
package com.qun.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageUtil {
    /**
     * 根据图片的路径创建对象
     * @param path 图片路径
     * @return 图片对象
     */
    public static final Image createImage(String path){
        return Toolkit.getDefaultToolkit().createImage(path);
    }

    /**
     * 改变图片的尺寸
     *
     * @param newWidth, newHeight, path
     * @return boolean
     */
    public static boolean changeSize(int newWidth, int newHeight, String path) {
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(path));

            //字节流转图片对象
            Image bi = ImageIO.read(in);
            //构建图片流
            BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            //绘制改变尺寸后的图
            tag.getGraphics().drawImage(bi, 0, 0, newWidth, newHeight, null);
            //输出流
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
            //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            //encoder.encode(tag);
            ImageIO.write(tag, "PNG", out);
            in.close();
            out.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        changeSize(60,60,"res/brown/up.png");
        changeSize(60,60,"res/brown/down.png");
        changeSize(60,60,"res/brown/left.png");
        changeSize(60,60,"res/brown/right.png");
    }
}
