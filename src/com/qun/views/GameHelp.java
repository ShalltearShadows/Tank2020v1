/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/5/6
 * @Time: 15:31
 */
package com.qun.views;

import com.qun.util.ImageUtil;

import java.awt.*;

import static com.qun.config.Constant.FRAME_HEIGHT;
import static com.qun.config.Constant.FRAME_WIDTH;

public class GameHelp {

    private static Image helpImg = null;

    /**
     * 绘制游戏说明画面
     * @param g 画笔
     */
    public static void drawHelp(Graphics g) {
        if (helpImg == null){
            helpImg = ImageUtil.createImage("res/help.png");
        }

        int imgW = helpImg.getWidth(null);
        int imgH = helpImg.getHeight(null);
        g.drawImage(helpImg,FRAME_WIDTH-imgW>>1,FRAME_HEIGHT-imgH>>1,null);
    }
}
