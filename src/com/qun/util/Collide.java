/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/4/26
 * @Time: 16:16
 */
package com.qun.util;

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
    public static final boolean isCollide(int rectX,int rectY,int radius,int pointX,int pointY){

        //点和中心的距离
        int disX = Math.abs(rectX - pointX);
        int disY = Math.abs(rectY - pointY);

        if (disX < radius && disY < radius){
            return true;
        }else {
            return false;
        }

    }
}
