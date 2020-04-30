/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/4/29
 * @Time: 8:48
 */
package com.qun.map;

import com.qun.pojo.Bullet;
import com.qun.util.BulletPool;
import com.qun.util.Collide;
import com.qun.util.ImageUtil;

import java.awt.*;
import java.util.List;

/**
 * 地图元素块
 */
public class MapTile {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_HOUSE = 1;

    private static Image[] tileImg;

    public static int tileW = 40;

    public static int radius = tileW >> 1;

    private boolean visible = true;

    private int type = TYPE_NORMAL;

    static {
        tileImg = new Image[2];
        tileImg[TYPE_NORMAL] = ImageUtil.createImage("res/wall.png");
        tileImg[TYPE_HOUSE] = ImageUtil.createImage("res/house.png");
    }

    //图片资源的左上角
    private int x,y;

    public MapTile() {
    }

    public MapTile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g){
        if (!visible){
            return ;
        }
        g.drawImage(tileImg[type],x,y,null);

    }


    /**
     * 地图砖块是否和若干子弹有碰撞
     * @param
     * @return
     */
    public boolean isCollideBullet(List<Bullet> bullets){
        if (!visible){
            return false;
        }

        for (Bullet bullet : bullets) {
            int bulletX = bullet.getX();
            int bulletY = bullet.getY();
            boolean collide1 = Collide.isCollide(x+radius, y+radius,radius,bulletX,bulletY);
            boolean collide2 = Collide.isCollide(x+radius, y+radius,radius,bulletX-4,bulletY-4);

            if (collide1||collide2){
                //炮弹销毁
                bullet.setVisible(false);
                BulletPool.returnBullet(bullet);
                return true;
            }
        }
        return false;
    }

    //判断当前的地图块是否是老巢
    public boolean isHouse( ){
        return type == TYPE_HOUSE;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
