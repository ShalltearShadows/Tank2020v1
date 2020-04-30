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

    private static Image tileImg;

    public static int tileW = 40;

    public static int radius = tileW >> 1;

    private boolean visible = true;

    private String name;

    static {
        tileImg = ImageUtil.createImage("res/wall.png");
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
        g.drawImage(tileImg,x,y,null);

        //绘制块,上的名字
        if(name != null){
            g.setColor(Color.GREEN);
            g.drawString(name,x+radius/2-3,y+radius*3/2-3);
        }
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
