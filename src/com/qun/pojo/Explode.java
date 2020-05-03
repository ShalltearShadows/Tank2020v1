/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/4/27
 * @Time: 12:58
 */
package com.qun.pojo;

import com.qun.util.ImageUtil;

import java.awt.*;

public class Explode {

    public static final int EXPLODE_FRAME_COUNT = 12;

    //爆炸图
    private static Image[] img;

    static {
        img = new Image[EXPLODE_FRAME_COUNT/3];
        for (int i = 0; i < img.length; i++) {
            img[i] = ImageUtil.createImage("res/boom/boom_"+i+".png");
        }
    }

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

        if (!visible){ return; }
        g.drawImage(img[index/3],x-RADIUS/2,y-RADIUS/2,null);
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
