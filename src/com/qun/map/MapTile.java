/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/4/29
 * @Time: 8:48
 */
package com.qun.map;

import com.qun.util.ImageUtil;

import java.awt.*;

/**
 * 地图元素块
 */
public class MapTile {

    private static Image tileImg;

    public static int tileW;

    static {
        tileImg = ImageUtil.createImage("res/wall.png");
        if (tileW <= 0){
            tileW = tileImg.getWidth(null);
        }
    }

    //图片资源的左上角
    private int x,y;

    public MapTile() {
    }

    public MapTile(int x, int y) {
        this.x = x;
        this.y = y;
        if (tileW <= 0){
            tileW = tileImg.getWidth(null);
        }
    }

    public void draw(Graphics g){
        if (tileW <= 0){
            tileW = tileImg.getWidth(null);
        }
        g.drawImage(tileImg,x,y,null);
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
}
