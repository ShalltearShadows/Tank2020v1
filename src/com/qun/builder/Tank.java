/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/5/3
 * @Time: 15:21
 */
package com.qun.builder;

import com.qun.config.Constant;
import com.qun.pojo.Explode;
import com.qun.game.GameFrame;
import com.qun.map.MapTile;
import com.qun.pojo.BloodBar;
import com.qun.pojo.Bullet;
import com.qun.util.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.qun.config.Constant.*;

public class Tank {
    protected int x , y;//中心位置
    protected int oldX,oldY;
    protected int dir;
    private int state = STATE_STOP;
    private boolean isEnemy ;
    private long fireTime ;
    private long aiTime;
    private int type;
    private String[] color = {"brown","purple","cyan","yellow"};
    public static Image[][] tankImg;

    protected String name;

    private int hp = DEFAULT_HP;

    private int atk;

    protected int speed = DEFAULT_SPEED;
    //血条
    private BloodBar bloodBar = new BloodBar();

    //炮弹
    private List<Bullet> bullets = new ArrayList<>();

    //爆炸
    private List<Explode> explodes = new ArrayList<>();

    //在静态块初始化
    {
        tankImg = new Image[4][4];

        for (int i = 0; i < tankImg.length; i++) {
            tankImg[i][0] = ImageUtil.createImage("res/"+color[i]+"/up.png");
            tankImg[i][1] = ImageUtil.createImage("res/"+color[i]+"/down.png");
            tankImg[i][2] = ImageUtil.createImage("res/"+color[i]+"/left.png");
            tankImg[i][3] = ImageUtil.createImage("res/"+color[i]+"/right.png");
        }
    }

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
        isEnemy = false;
    }

    public Tank(){
        isEnemy = true;
        x = RandomUtil.getRandomNumber(0,2) == 0 ? TANK_RADIUS : Constant.FRAME_WIDTH - TANK_RADIUS;
        y = GameFrame.titleBarH + TANK_RADIUS;

        type = RandomUtil.getRandomNumber(2,4);

        dir = DIR_DOWN;
        state = STATE_MOVE;
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
    protected void drawName(Graphics g){
        if (isEnemy){
            g.setColor(Color.RED);
        }else {
            g.setColor(Color.GREEN);
        }

        g.setFont(SIMALL_FONT);

        g.drawString(name,x-TANK_RADIUS+5,y-TANK_RADIUS-15);
    }


    /**
     * 图片绘制坦克
     * @param g
     */
    public void drawImgTank(Graphics g) {
        g.drawImage(tankImg[type][getDir()],getX()-TANK_RADIUS,getY()-TANK_RADIUS,null);
    }


    //坦克的移动
    private void move(){

        oldX = x;
        oldY = y;
        switch (dir){
            case DIR_UP: y -= speed; if (y < TANK_RADIUS + GameFrame.titleBarH){y = TANK_RADIUS + GameFrame.titleBarH;} break;
            case DIR_DOWN: y += speed; if (y > Constant.FRAME_HEIGHT-TANK_RADIUS){y = Constant.FRAME_HEIGHT-TANK_RADIUS;} break;
            case DIR_LEFT: x -= speed; if (x < TANK_RADIUS){x = TANK_RADIUS;} break;
            case DIR_RIGHT: x += speed; if (x > Constant.FRAME_WIDTH-TANK_RADIUS){x = Constant.FRAME_WIDTH-TANK_RADIUS;} break;
        }
    }


    private void ai(){
        if (System.currentTimeMillis() - aiTime > Constant.ENEMY_AI_INTERVAL){
            //给敌人随机一个停止或移动的状态，并随机改变其方向
            setDir(RandomUtil.getRandomNumber(DIR_UP,DIR_RIGHT+1));
            setState(RandomUtil.getRandomNumber(0,2) == 0 ? STATE_STOP : STATE_MOVE);
            aiTime = System.currentTimeMillis();
        }

        //敌人的随机开火
        if (Math.random() < Constant.ENEMY_FIRE_PERCENT){
            fire();
        }
    }


    //坦克的状态处理
    private void logic(){
        if (isEnemy){
            ai();
        }
        switch (state){
            case STATE_STOP: break;
            case STATE_MOVE: move(); break;
            case STATE_DIED: break;
        }
    }

    /**
     * 坦克开火的方法
     * 创建一个炮弹对象，其属性通过坦克获得
     * 然后将创建的炮弹添加到管理炮弹的容器中
     */
    public void fire(){
        if (System.currentTimeMillis()-fireTime<FIRE_INTERVAL){
            return;
        }

        Bullet bullet = BulletPool.getBullet();

        int bulletX = x;
        int bulletY = y;

        switch (dir){
            case DIR_UP: bulletY -= TANK_RADIUS; break;
            case DIR_DOWN: bulletY += TANK_RADIUS; break;
            case DIR_LEFT: bulletX -= TANK_RADIUS; break;
            case DIR_RIGHT: bulletX += TANK_RADIUS; break;
        }

        bullet.initBullet(bulletX, bulletY, dir, atk,true);

        bullets.add(bullet);

        fireTime = System.currentTimeMillis();
    }

    /**
     * 绘制坦克已经发射的炮弹
     * @param g
     */
    private void drawBullets(Graphics g){
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (!bullet.isVisible()){
                BulletPool.returnBullet(bullets.remove(i));
                i--;
                continue;
            }
            bullet.draw(g);
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
            if (Collide.isCollide(x,y,TANK_RADIUS,bulletX,bulletY)){
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
            if (!isEnemy){
                GameFrame.setGameState(STATE_OVER);
            }

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
                //当老巢被击毁之后，一秒钟后切换到游戏结束的画面
                if (tile.isHouse()){
                    GameFrame.setGameState(STATE_OVER);
                }
                //归还对象池
                MapTilePool.returnMapTile(tile);
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
            boolean collide = Collide.isCollide(x, y, TANK_RADIUS, tileX, tileY);
            //如果碰上了就直接返回，否则继续判断下一个点
            if(collide){
                return true;
            }
            //点-3
            tileX += MapTile.radius*2;
            collide = Collide.isCollide(x, y, TANK_RADIUS, tileX, tileY);
            if(collide){
                return true;
            }
            //点-5
            tileY += MapTile.radius*2;
            collide = Collide.isCollide(x, y, TANK_RADIUS, tileX, tileY);
            if(collide){
                return true;
            }
            //点-7
            tileX -= MapTile.radius*2;
            collide = Collide.isCollide(x, y, TANK_RADIUS, tileX, tileY);
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

    public int getY() {
        return y;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDir() {
        return dir;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setBloodBar(BloodBar bloodBar) {
        this.bloodBar = bloodBar;
    }

    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }

    public void setExplodes(List<Explode> explodes) {
        this.explodes = explodes;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHp() {
        return hp;
    }

    public List<Explode> getExplodes() {
        return explodes;
    }
}
