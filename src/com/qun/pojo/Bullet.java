/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LQ
 * @Date: 2020/4/24
 * @Time: 9:54
 */
package com.qun.pojo;


import com.qun.config.Constant;
import com.qun.util.ImageUtil;
import com.qun.util.RandomUtil;

import java.awt.*;
import static com.qun.config.Constant.*;

/**
 * 炮弹类
 */
public class Bullet {

    //炮弹的大小
    public static final int RADIUS = 5;

    private int x,y;
    private int speed = 12;
    private int dir;
    private int atk;

    private int color=0;

    private static Image[] bulletImg;

    static {
        bulletImg = new Image[4];
        for (int i = 1; i <= bulletImg.length; i++) {
            bulletImg[i-1]= ImageUtil.createImage("res/bullet/"+i+".png");
        }
    }

    //炮弹是否可见
    private boolean visible = true;

    //给对象池用的
    public Bullet(){
        color = RandomUtil.getRandomNumber(0,4);
    }

    /**
     * 炮弹的绘制
     * @param g
     */
    public void draw(Graphics g){
        if (!visible)return;
        logic();
        g.drawImage(bulletImg[color],x-RADIUS,y-RADIUS,null);
    }

    /**
     * 炮弹的逻辑
     */
    private void logic(){
        move();
    }

    private void move(){
        switch (dir){
            case DIR_UP: y -= speed; if (y<0) visible=false; break;
            case DIR_DOWN: y += speed; if (y>Constant.FRAME_HEIGHT) visible=false; break;
            case DIR_LEFT: x -= speed; if (x<0) visible=false; break;
            case DIR_RIGHT: x += speed; if (x>Constant.FRAME_WIDTH) visible=false; break;
        }


    }

    /**
     * 对象池初始化
     * @return
     */
    public void initBulletByPool(int x, int y, int dir, int atk, Boolean visible) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.atk = atk;
        this.visible = visible;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAtk() {
        return atk;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
