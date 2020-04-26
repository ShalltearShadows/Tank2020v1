/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/4/26
 * @Time: 16:55
 */
package com.qun.util;

import java.awt.*;

public class ImageUtil {
    /**
     * 根据图片的路径创建对象
     * @param path 图片路径
     * @return 图片对象
     */
    public static final Image createImage(String path){
        return Toolkit.getDefaultToolkit().createImage(path);
    }
}
