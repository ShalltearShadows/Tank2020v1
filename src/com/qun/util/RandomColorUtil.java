/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LQ
 * @Date: 2020/4/23
 * @Time: 9:19
 */
package com.qun.util;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RandomColorUtil {
    private RandomColorUtil(){}

    /**
     * 得到指定区间的随机数
     * @param min 区间最小值，包含
     * @param max 区间最大值，不包含
     * @return 返回随机数
     */
    public static final int getRandomNumber(int min,int max){
        return (int) (Math.random()*(max-min)+min);
    }

    /**
     * 得到随机颜色
     * @return
     */
    public static final Color getRandomColor(){
        int c = getRandomNumber(0,6);

        List<Color> color = new ArrayList<>();
        color.add(Color.RED);
        color.add(Color.GREEN);
        color.add(Color.YELLOW);
        color.add(Color.BLUE);
        color.add(Color.WHITE);
        color.add(Color.PINK);

        return color.get(c);
    }
}
