/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LQ
 * @Date: 2020/4/23
 * @Time: 8:34
 */
package com.qun.pojo;

import com.qun.game.Explode;
import com.qun.game.GameFrame;
import com.qun.map.MapTile;
import com.qun.util.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.qun.config.Constant.*;

/**
 * 坦克类
 */
public abstract class Tank {

    protected String name;
    protected int x , y;//中心位置
    protected int oldX,oldY;
    private int hp = DEFAULT_HP;
    private int atk;
    protected int speed = DEFAULT_SPEED;
    protected int dir;
    private int state = STATE_STOP;
    private boolean isEnemy = false;

    //上次开火时间
    private long fireTime ;

    //血条
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
        name = RandomUtil.getRandomName();
        atk = 99;
    }

    public Tank(){
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
        bloodBar.draw(x,y,hp,g);
    }

    /**
     * 绘制坦克名字
     */
    protected abstract void drawName(Graphics g);


    /**
     * 图片绘制坦克
     * @param g
     */
    protected abstract void drawImgTank(Graphics g);



    //坦克的状态处理
    private void logic(){
        switch (state){
            case STATE_STOP: break;
            case STATE_MOVE: move(); break;
            case STATE_DIED: break;
        }
    }

    //坦克的移动
    protected abstract void move();


    /**
     * 坦克开火的方法
     * 创建一个炮弹对象，其属性通过坦克获得
     * 然后将创建的炮弹添加到管理炮弹的容器中
     */
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

        bullet.initBulletByPool(bulletX, bulletY, dir, atk,true);

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
            GameFrame.setGameState(STATE_OVER);
        }
    }

    public boolean isDie(){
        return state == STATE_DIED;
    }

    /**
     * 绘制当前坦克上的所有的爆炸的效果
     * @param g
     */
    public void drawExplodes(Graphics g) {
        for (Explode explode : explodes) {
            explode.draw(g);
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
                    GameFrame.setGameState(STATE_OVER);
                }
            }
        }
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
            //点-3
            tileX += MapTile.radius*2;
            collide = Collide.isCollide(x, y, RADIUS, tileX, tileY);
            if(collide){
                return true;
            }
            //点-5
            tileY += MapTile.radius*2;
            collide = Collide.isCollide(x, y, RADIUS, tileX, tileY);
            if(collide){
                return true;
            }
            //点-7
            tileX -= MapTile.radius*2;
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

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setEnemy(boolean enemy) {
        isEnemy = enemy;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

}