/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LQ
 * @Date: 2020/4/23
 * @Time: 8:34
 */
package com.qun.game;

import com.qun.config.Constant;
import com.qun.util.BulletPool;
import com.qun.util.RandomUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 坦克类
 */
public class Tank {
    //坦克的图片数组
    public static Image[] tankImg;
    //敌军坦克
    public static Image[] enemyImg;
    //在静态块初始化
    {
        tankImg = new Image[4];
        tankImg[0] = Toolkit.getDefaultToolkit().createImage("res/brown/up.png");
        tankImg[1] = Toolkit.getDefaultToolkit().createImage("res/brown/down.png");
        tankImg[2] = Toolkit.getDefaultToolkit().createImage("res/brown/left.png");
        tankImg[3] = Toolkit.getDefaultToolkit().createImage("res/brown/right.png");

        enemyImg = new Image[4];
        enemyImg[0] = Toolkit.getDefaultToolkit().createImage("res/yellow/up.png");
        enemyImg[1] = Toolkit.getDefaultToolkit().createImage("res/yellow/down.png");
        enemyImg[2] = Toolkit.getDefaultToolkit().createImage("res/yellow/left.png");
        enemyImg[3] = Toolkit.getDefaultToolkit().createImage("res/yellow/right.png");
    }

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
     * 创建敌人坦克
     * @return
     */
    public static Tank createEnemy(){
        int x = RandomUtil.getRandomNumber(0,2) == 0 ? RADIUS : Constant.FRAME_WIDTH - RADIUS;
        int y = GameFrame.titleBarH + RADIUS;
        int dir = DIR_DOWN;

        Tank enemy = new Tank(x,y,dir);
        enemy.isEnemy = true;

//        TODO
        enemy.state = STATE_MOVE;

        return enemy;
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
    private void drawImgTank(Graphics g){
        if (isEnemy){
            g.drawImage(enemyImg[dir],x-RADIUS,y-RADIUS,null);
        }else {
            g.drawImage(tankImg[dir],x-RADIUS,y-RADIUS,null);
        }

    }



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

//        if (!(bullets.size()==0)){
//            System.out.println("坦克炮弹的数量"+bullets.size());
//        }

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