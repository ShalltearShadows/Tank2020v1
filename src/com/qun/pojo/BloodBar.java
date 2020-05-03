/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/5/1
 * @Time: 19:33
 */
package com.qun.pojo;

import java.awt.*;

import static com.qun.config.Constant.DEFAULT_HP;
import static com.qun.config.Constant.RADIUS;

public class BloodBar{
    public static final int BAR_LENGHT = 50;
    public static final int BAR_HEIGHT = 5;

    public void draw(int x,int y,int hp,Graphics g){

        int barX = x - RADIUS + 5;

        //填充底色
        g.setColor(Color.YELLOW);
        g.fillRect(barX,y - RADIUS - BAR_HEIGHT*2,BAR_LENGHT,BAR_HEIGHT);
        //血量
        g.setColor(Color.RED);
        g.fillRect(barX,y - RADIUS - BAR_HEIGHT*2,hp*BAR_LENGHT/DEFAULT_HP,BAR_HEIGHT);
        //边框
        g.setColor(Color.BLUE);
        g.drawRect(barX,y - RADIUS - BAR_HEIGHT*2,BAR_LENGHT,BAR_HEIGHT);
    }
}