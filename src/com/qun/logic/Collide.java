/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/4/26
 * @Time: 16:16
 */
package com.qun.logic;

import com.qun.builder.Tank;
import static com.qun.views.GameFrame.*;

public class Collide {

    /**
     * 判断某个点是否在正方形内部
     * @param rectX 正方形的中心点的x坐标
     * @param rectY 正方形的中心点的y坐标
     * @param radius 正方形的边长一半
     * @param pointX 点的x坐标
     * @param pointY 点的y坐标
     * @return 在内部返回true,否则false
     */
    public static boolean isCollide(int rectX,int rectY,int radius,int pointX,int pointY){

        //点和中心的距离
        int disX = Math.abs(rectX - pointX);
        int disY = Math.abs(rectY - pointY);

        if (disX < radius && disY < radius){
            return true;
        }else {
            return false;
        }

    }

    //地图块的碰撞
    public static void CollideMapTile(){
        //自己的坦克的子弹和地图块的碰撞
        myTank.bulletsCollideMapTiles(gameMap.getTiles());
        //敌人的坦克的子弹和地图块的碰撞
        for (Tank enemy : enemies) {
            enemy.bulletsCollideMapTiles(gameMap.getTiles());
        }

        //坦克和地图的碰撞
        if (myTank.isCollideTile(gameMap.getTiles())){
            myTank.back();
        }

        //敌人和砖块的碰撞
        for (Tank enemy : enemies) {
            if (enemy.isCollideTile(gameMap.getTiles())){
                enemy.back();
            }
        }

        //清理所有不可见的砖块
        gameMap.clearDestoryTile();

    }


    /**
     * 炮弹与坦克相碰
     */
    public static void bulletCollideTank(){
        //玩家的炮弹射中了敌人
        for (Tank enemy : enemies) {
            enemy.collideBullets(myTank.getBullets());
        }
        //敌人的炮弹射中了玩家
        for (Tank enemy : enemies) {
            myTank.collideBullets(enemy.getBullets());
        }
    }
}
