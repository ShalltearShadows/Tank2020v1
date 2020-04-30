/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LQ
 * @Date: 2020/4/23
 * @Time: 8:34
 */
package com.qun.pojo;

import com.qun.config.Constant;
import com.qun.game.Explode;
import com.qun.game.GameFrame;
import com.qun.map.MapTile;
import com.qun.util.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.qun.config.Constant.SIMALL_FONT;
import static com.qun.config.Constant.STATE_OVER;

/**
 * 坦克类
 */
public abstract class Tank {

    //半径
    public static final int RADIUS = 30;

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


    private String name;
    private int x , y;//中心位置
    private int oldX,oldY;
    private int hp = DEFAULT_HP;
    private int atk;
    private int speed = DEFAULT_SPEED;
    private int dir;
    private int state = STATE_STOP;
    private Color color;
    private boolean isEnemy = false;
    private BloodBar bloodBar = new BloodBar();

    //炮弹
    private List<Bullet> bullets = new ArrayList();

    //爆炸
    private List<Explode> explodes = new ArrayList<>();


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
        name = RandomUtil.getRandomName();
        atk = 99;
    }

    public Tank(){
        color = RandomUtil.getRandomColor();
        name = RandomUtil.getRandomName();
        atk = 99;
    }



    /**
     * 绘制图像
     * @param g
     */
    public void draw(Graphics g){
        logic();
        drawImgTank(g);
        drawBullets(g);
        drawName(g);
        bloodBar.draw(g);
    }
    /**
     * 绘制坦克名字
     */
    private void drawName(Graphics g){
        g.setFont(SIMALL_FONT);
        g.setColor(color);
        g.drawString(name,x-RADIUS+5,y-RADIUS-15);
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
        oldX = x;
        oldY = y;
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
    //上次开火时间
    private long fireTime ;
    public static final int FIRE_INTERVAL = 150;

    public void fire(){
        if (System.currentTimeMillis()-fireTime<FIRE_INTERVAL){
            return;
        }

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

        fireTime = System.currentTimeMillis();
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
                i--;
            }
        }
    }

    /**
     *坦克销毁的时候处理坦克的所有的子弹
     */
    public void bulletsReturn(){
        for (Bullet bullet : bullets) {
            BulletPool.returnBullet(bullet);
        }
        bullets.clear();
    }

    /**
     * 坦克和炮弹碰撞的方法
     * @return
     */
    public void collideBullets(List<Bullet> bullets){
        //遍历所有的炮弹是否和当前的坦克碰撞
        for (Bullet bullet : bullets) {

            int bulletX = bullet.getX();
            int bulletY = bullet.getY();

            //炮弹和坦克碰上了
            if (Collide.isCollide(x,y,RADIUS,bulletX,bulletY)){
                //炮弹消失
                bullet.setVisible(false);
                //坦克收到伤害
                hurt(bullet);
                //添加爆炸效果
                addExplode(bulletX,bulletY-10);

            }
        }

    }

    private void addExplode(int bulletX,int bulletY) {
        Explode explode = ExplodePool.getExplode();
        explode.setX(bulletX);
        explode.setY(bulletY);
        explode.setVisible(true);
        explode.setIndex(0);
        explodes.add(explode);
    }

    //坦克收到伤害
    private void hurt(Bullet bullet){
        int atk = bullet.getAtk();

        hp -= atk;
        if(hp < 0){
            hp=0;
            state = STATE_DIED;
            die();
        }
    }

    /**
     * 坦克死亡
     */
    private void die(){
        if (isEnemy){
            EnemyTankPool.returnTank(this);
        }else {
            delaySecondToOver(1000);
        }
    }

    public boolean isDie(){
        return state == STATE_DIED;
    }

    /**
     * 绘制当前坦克.上的所有的爆炸的效果
     * @param g
     */
    public void drawExplodes(Graphics g) {
        for (Explode exp1ode : explodes) {
            exp1ode.draw(g);
        }

        for (int i = 0; i < explodes.size(); i++) {
            Explode explode = explodes.get(i);
            if (!explode.isVisible()){
                Explode remove = explodes.remove(i);
                ExplodePool.returnExplode(remove);
                i--;
            }
        }
    }



    //内部类表示血条
    class BloodBar{
        public static final int BAR_LENGHT = 50;
        public static final int BAR_HEIGHT = 5;

        public void draw(Graphics g){

            int barX = x - RADIUS + 5;

            //填充底色
            g.setColor(Color.YELLOW);
            g.fillRect(barX,y - RADIUS - BAR_HEIGHT*2,BAR_LENGHT,BAR_HEIGHT);
            //血量
            g.setColor(Color.RED);
            g.fillRect(barX,y - RADIUS - BAR_HEIGHT*2,hp*BAR_LENGHT/DEFAULT_HP,BAR_HEIGHT);
            //边框
            g.setColor(Color.BLUE);
            g.drawRect(barX,y - RADIUS - BAR_HEIGHT*2,BAR_LENGHT,BAR_HEIGHT);
        }
    }

    //坦克的子弹和地图所有的块的碰撞
    public void bulletsCollideMapTiles(List<MapTile> tiles) {
        for (MapTile tile : tiles) {
            if (tile.isCollideBullet(bullets)) {
                //添加爆炸效果
                addExplode(tile.getX()+MapTile.tileW/2,tile.getY()+MapTile.tileW/2);
                //销毁砖块
                tile.setVisible(false);
                //归还对象池
                MapTilePool.returnMapTile(tile);
                //当老巢被击毁之后，一秒钟后切换到游戏结束的画面
                if (tile.isHouse()){
                    delaySecondToOver(1000);
                }
            }
        }
    }

    /**
     * 延迟若干毫秒出现游戏结束画面
     * @param millisSecond
     */
    private void delaySecondToOver(int millisSecond){
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                GameFrame.setGameState(STATE_OVER);
            }
        }.start();
    }



    /**
     * 一个地图块和当前的坦克碰撞的方法
     * 从tile 中提取8个点来判断8个点是否有任何一个点和当前的坦克有了碰撞
     * 点的顺序从左上角的点开始，顺时针遍历
     */
    public boolean isCollideTile(List<MapTile> tiles){

        for (MapTile tile : tiles) {
            //点-1
            int tileX = tile.getX();
            int tileY = tile.getY();
            boolean collide = Collide.isCollide(x, y, RADIUS, tileX, tileY);
            //如果碰上了就直接返回，否则继续判断下一个点
            if(collide){
                return true;
            }

            //点-2
            tileX += MapTile.radius;
            collide = Collide.isCollide(x, y, RADIUS, tileX, tileY);
            if(collide){
                return true;
            }

            //点-3
            tileX += MapTile.radius;
            collide = Collide.isCollide(x, y, RADIUS, tileX, tileY);
            if(collide){
                return true;
            }
            //点-4
            tileY += MapTile.radius;
            collide = Collide.isCollide(x, y, RADIUS, tileX, tileY);
            if(collide){
                return true;
            }
            //点-5
            tileY += MapTile.radius;
            collide = Collide.isCollide(x, y, RADIUS, tileX, tileY);
            if(collide){
                return true;
            }
            //点-6
            tileX -= MapTile.radius;
            collide = Collide.isCollide(x, y, RADIUS, tileX, tileY);
            if(collide){
                return true;
            }
            //点-7
            tileX -= MapTile.radius;
            collide = Collide.isCollide(x, y, RADIUS, tileX, tileY);
            if(collide){
                return true;
            }
            //点-8
            tileY -= MapTile.radius;
            collide = Collide.isCollide(x, y, RADIUS, tileX, tileY);
            if(collide){
                return true;
            }
        }

        return false;

    }

    public void back() {
        x = oldX;
        y = oldY;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}