/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/5/6
 * @Time: 15:25
 */
package com.qun.views;

import java.awt.*;

import static com.qun.config.Constant.*;
import static com.qun.config.Constant.MENUS;

public class GameMenu {
    /**
     * 在缓冲区绘制菜单状态下的内容
     * @param gbb 缓冲区画笔对象，系统提供
     */
    public static void drawMenu(Graphics gbb,int menuIndex){
        //绘制黑色的背景
        gbb.setColor(Color.BLACK);
        gbb.fillRect( 0, 0, FRAME_WIDTH, FRAME_HEIGHT) ;

        final int STR_WIDTH = 76;
        int x = FRAME_WIDTH - STR_WIDTH >> 1;
        int y = FRAME_HEIGHT/3;

        final int DIS = 50;

        for (int i = 0; i < MENUS.length; i++) {

            //选中的菜单项的颜色设置为红色
            if (i == menuIndex){
                gbb.setColor(Color.RED);
            }else {
                gbb.setColor(Color.WHITE);
            }

            gbb.drawString(MENUS[i],x,y + DIS*i);
        }
    }
}
