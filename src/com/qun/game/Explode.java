/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/4/27
 * @Time: 12:58
 */
package com.qun.game;

import com.qun.util.RandomUtil;

import java.awt.*;

public class Explode {

    public static final int EXPLODE_FRAME_COUNT = 12;

    public static final int RADIUS = 50;

    private static final Explode EXPLODE = new Explode();

    //爆炸的位置
    private int x,y;

    private int index;

    //是否可见
    private boolean visible = true;

    public Explode() {
    }

    public void init(int x, int y) {
        this.x = x;
        this.y = y;
        index = 0;
    }

    public static Explode getIns(){
        return EXPLODE;
    }

    public void draw(Graphics g){

        g.setColor(RandomUtil.getRandomColor());

        if (!visible){ return; }
        g.fillOval(x-RADIUS/2,y-RADIUS/2,RADIUS,RADIUS);
        index++;
        //播放完最后一帧，设置不可见
        if (index >= EXPLODE_FRAME_COUNT){ visible=false; }

    }

    public void setVisible(boolean visible) {
        this.visible = visible;
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

    public boolean isVisible() {
        return visible;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
