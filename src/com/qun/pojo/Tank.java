/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LQ
 * @Date: 2020/4/23
 * @Time: 8:34
 */
package com.qun.pojo;

import com.qun.config.Constant;
import com.qun.game.GameFrame;
import com.qun.util.BulletPool;
import com.qun.util.Collide;
import com.qun.util.RandomUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 坦克类
 */
public abstract class Tank {

    //半径
    public static final int RADIUS = 37;

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

    private int x , y;//中心位置
    private int hp;
    private int atk;
    private int speed = DEFAULT_SPEED;
    private int dir;
    private int state = STATE_STOP;
    private Color color;
    private boolean isEnemy = false;

    //炮弹
    private List<Bullet> bullets = new ArrayList();


    /**
     * 创建的玩家的坦克
     * @param x 玩家的出生地
     * @param y
     * @param dir
     */
    public Tank(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        color = RandomUtil.getRandomColor();
    }




    /**
     * 绘制图像
     * @param g
     */
    public void draw(Graphics g){
        logic();
        drawImgTank(g);
        drawBullets(g);
    }

    /**
     * 图片绘制坦克
     * @param g
     */
    public abstract void drawImgTank(Graphics g);



    /**
     * 系统绘制坦克
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
            case DIR_UP: bulletY -= RADIUS; break;
            case DIR_DOWN: bulletY += RADIUS; break;
            case DIR_LEFT: bulletX -= RADIUS; break;
            case DIR_RIGHT: bulletX += RADIUS; break;
        }

        Bullet bullet = BulletPool.getBullet();

        bullet.initBulletByPool(bulletX, bulletY, dir, atk, color, true);

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

        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (!bullet.isVisible()){
                BulletPool.returnBullet(bullets.remove(i));
            }
        }


    }

    /**
     * 坦克和炮弹碰撞的方法
     * @return
     */
    public void collideBullets(List<Bullet> bullets){
        //遍历所有的炮弹是否和当前的坦克碰撞
        for (Bullet bullet : bullets) {
            //炮弹和坦克碰上了
            if (Collide.isCollide(x,y,RADIUS,bullet.getX(),bullet.getY())){
                //炮弹消失
                bullet.setVisible(false);
                //坦克收到伤害
                //添加爆炸效果
            }
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

    public boolean isEnemy() {
        return isEnemy;
    }

    public void setEnemy(boolean enemy) {
        isEnemy = enemy;
    }


    public List<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }
}