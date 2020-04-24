/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LQ
 * @Date: 2020/4/24
 * @Time: 9:54
 */
package com.qun.game;


import java.awt.*;

/**
 * 炮弹类
 */
public class Bullet {
    //炮弹的速度默认为坦克移速的4倍
    public static final int DEFAULT_SPEED = Tank.DEFAULT_SPEED << 2;

    //炮弹的大小
    public static final int RADIUS = 4;

    private int x,y;
    private int speed = DEFAULT_SPEED;
    private int dir;
    private int atk;
    private Color color;

    public Bullet(int x, int y, int dir, int atk, Color color) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.atk = atk;
        this.color = color;
    }

    /**
     * 炮弹的绘制
     * @param g
     */
    public void draw(Graphics g){
        logic();
        g.setColor(color);
        g.fillOval(x-RADIUS,y-RADIUS,RADIUS<<1,RADIUS<<1);
    }

    /**
     * 炮弹的逻辑
     */
    private void logic(){
        move();
    }

    private void move(){
        switch (dir){
            case Tank.DIR_UP: y -= speed; break;
            case Tank.DIR_DOWN: y += speed; break;
            case Tank.DIR_LEFT: x -= speed; break;
            case Tank.DIR_RIGHT: x += speed; break;
        }
    }
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }
}
