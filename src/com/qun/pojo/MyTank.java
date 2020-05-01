/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/4/26
 * @Time: 13:55
 */
package com.qun.pojo;

import com.qun.util.ImageUtil;

import java.awt.*;


/**
 * 玩家坦克
 */
public class MyTank extends Tank {

    //坦克的图片数组
    public static Image[][] tankImg;

    public static final int TYPE_BROWN = 0;
    public static final int TYPE_PURPLE = 1;

    private int type;

    private String[] color = {"brown","purple"};


    /**
     * 创建的玩家的坦克
     * @param x   玩家的出生地
     * @param y
     * @param dir
     */
    public MyTank(int x, int y, int dir,int type) {
        super(x, y, dir);
        this.type = type;
    }


    //在静态块初始化
    {
        tankImg = new Image[2][4];

        for (int i = 0; i < tankImg.length; i++) {
            tankImg[i][0] = ImageUtil.createImage("res/"+color[i]+"/up.png");
            tankImg[i][1] = ImageUtil.createImage("res/"+color[i]+"/down.png");
            tankImg[i][2] = ImageUtil.createImage("res/"+color[i]+"/left.png");
            tankImg[i][3] = ImageUtil.createImage("res/"+color[i]+"/right.png");
        }
    }


    @Override
    public void drawImgTank(Graphics g) {
        g.drawImage(tankImg[type][getDir()],getX()-RADIUS,getY()-RADIUS,null);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
