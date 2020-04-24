/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LQ
 * @Date: 2020/4/23
 * @Time: 8:34
 */
package com.qun.game;

import com.qun.config.Constant;
import com.qun.util.RandomColorUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 坦克类
 */
public class Tank {
    //中心位置
    private int x , y;
    //半径
    public static final int RADIUS = 27;

    //默认速度 每帧跑4跑pm，4pm/30ms
    public static final int DEFAULT_SPEED = 4;

    //四个方向
    public static final int DIR_UP = 0;
    public static final int DIR_DOWN = 1;
    public static final int DIR_LEFT = 2;
    public static final int DIR_RIGHT = 3;

    //坦克的状态：停下、移动、死亡
    public static final int STATE_STOP = 0;
    public static final int STATE_MOVE = 1;
    public static final int STATE_DIED = 2;

    //坦克的初始HP
    public static final int DEFAULT_HP = 1000;

    private int hp;
    private int atk;
    private int speed = DEFAULT_SPEED;
    private int dir;
    private int state = STATE_STOP;
    private Color color;

    //炮弹
    private List<Bullet> bullets = new ArrayList();


    public Tank(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        color = RandomColorUtil.getRandomColor();
    }

    /**
     * 绘制坦克
     * @param g
     */
    public void draw(Graphics g){
        logic();
        drawTank(g);
        drawBullets(g);
    }

    /**
     * 绘制坦克
     * @param g
     */
    private void drawTank(Graphics g){
        g.setColor(color);

        //绘制坦克的圆
        g.fillOval(x-RADIUS,y-RADIUS,RADIUS<<1,RADIUS<<1);

        int endX = x;
        int endY = y;

        //绘制炮管
        switch (dir){
            case DIR_UP: g.fillRect(x-1,y-RADIUS*2,3,RADIUS*2); break;
            case DIR_DOWN: g.fillRect(x-1,y,3,RADIUS*2); break;
            case DIR_LEFT: g.fillRect(x-RADIUS*2,y-1,RADIUS*2,3); break;
            case DIR_RIGHT: g.fillRect(x,y-1,RADIUS*2,3); break;
        }

    }


    //坦克的状态处理
    private void logic(){
        switch (state){
            case STATE_STOP: break;
            case STATE_MOVE: move(); break;
            case STATE_DIED: break;
        }
    }

    //坦克的移动
    private void move(){
        switch (dir){
            case DIR_UP: y -= speed; if (y < RADIUS + GameFrame.titleBarH){y = RADIUS + GameFrame.titleBarH;} break;
            case DIR_DOWN: y += speed; if (y > Constant.FRAME_HEIGHT-RADIUS){y = Constant.FRAME_HEIGHT-RADIUS;} break;
            case DIR_LEFT: x -= speed; if (x < RADIUS){x = RADIUS;} break;
            case DIR_RIGHT: x += speed; if (x > Constant.FRAME_WIDTH-RADIUS){x = Constant.FRAME_WIDTH-RADIUS;} break;
        }
    }

    /**
     * 坦克开火的方法
     * 创建一个炮弹对象，其属性通过坦克获得
     * 然后将创建的炮弹添加到管理炮弹的容器中
     */
    public void fire(){
        int bulletX = x;
        int bulletY = y;

        switch (dir){
            case DIR_UP: bulletY -= RADIUS << 1; break;
            case DIR_DOWN: bulletY += RADIUS << 1; break;
            case DIR_LEFT: bulletX -= RADIUS << 1; break;
            case DIR_RIGHT: bulletX += RADIUS << 1; break;
        }

        Bullet bullet = new Bullet(bulletX, bulletY, dir, atk, color);

        bullets.add(bullet);
    }

    /**
     * 绘制坦克已经发射的炮弹
     * @param g
     */
    private void drawBullets(Graphics g){
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}