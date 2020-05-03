/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/4/26
 * @Time: 13:55
 */
package com.qun.pojo;

import com.qun.config.Constant;
import com.qun.game.GameFrame;
import com.qun.util.ImageUtil;

import java.awt.*;

import static com.qun.config.Constant.*;

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
    public void drawName(Graphics g) {
        g.setFont(SIMALL_FONT);
        g.setColor(Color.GREEN);
        g.drawString(name,x-RADIUS+5,y-RADIUS-15);
    }

    @Override
    public void drawImgTank(Graphics g) {
        g.drawImage(tankImg[type][getDir()],getX()-RADIUS,getY()-RADIUS,null);
    }

    @Override
    protected void move() {
        oldX = x;
        oldY = y;
        switch (dir){
            case DIR_UP: y -= speed; if (y < RADIUS + GameFrame.titleBarH){y = RADIUS + GameFrame.titleBarH;} break;
            case DIR_DOWN: y += speed; if (y > Constant.FRAME_HEIGHT-RADIUS){y = Constant.FRAME_HEIGHT-RADIUS;} break;
            case DIR_LEFT: x -= speed; if (x < RADIUS){x = RADIUS;} break;
            case DIR_RIGHT: x += speed; if (x > Constant.FRAME_WIDTH-RADIUS){x = Constant.FRAME_WIDTH-RADIUS;} break;
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
