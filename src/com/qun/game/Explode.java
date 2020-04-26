/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/4/26
 * @Time: 16:50
 */
package com.qun.game;


import com.qun.util.RandomUtil;

import java.awt.*;

/**
 * 用来控制爆炸效果的类
 */
public class Explode {
    public static final int EXPLODE_FRAME_COUNT = 12;

    public static final int RADIUS = 50;

    //爆炸的位置
    private int x,y;

    private int index;

    //是否可见
    private boolean visible = true;


    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
        index = 0;
    }

    public void draw(Graphics g){

        g.setColor(RandomUtil.getRandomColor());

        if (!visible){ return; }
        g.fillOval(x-RADIUS/5,y-RADIUS/2,RADIUS,RADIUS);
        index++;
        //播放完最后一帧，设置不可见
        if (index >= EXPLODE_FRAME_COUNT){ visible=false; }

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
