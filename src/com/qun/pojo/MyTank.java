/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/4/26
 * @Time: 13:55
 */
package com.qun.pojo;

import java.awt.*;


/**
 * 玩家坦克
 */
public class MyTank extends Tank {

    //坦克的图片数组
    public static Image[] tankImg;


    /**
     * 创建的玩家的坦克
     *
     * @param x   玩家的出生地
     * @param y
     * @param dir
     */
    public MyTank(int x, int y, int dir) {
        super(x, y, dir);
    }


    //在静态块初始化
    {
        tankImg = new Image[4];
        tankImg[0] = Toolkit.getDefaultToolkit().createImage("res/brown/up.png");
        tankImg[1] = Toolkit.getDefaultToolkit().createImage("res/brown/down.png");
        tankImg[2] = Toolkit.getDefaultToolkit().createImage("res/brown/left.png");
        tankImg[3] = Toolkit.getDefaultToolkit().createImage("res/brown/right.png");
    }


    @Override
    public void drawImgTank(Graphics g) {
        g.drawImage(tankImg[getDir()],getX()-RADIUS,getY()-RADIUS,null);
    }

}
