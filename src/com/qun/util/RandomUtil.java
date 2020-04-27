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

public class RandomUtil {
    private RandomUtil(){}

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

    private static final String[] NAMES = {
            "乐园","花草","左手","年代","灰尘",
            "沙子","小说","明星","本子","彩色","水珠",
            "房屋","心愿","左边", "新闻","早点",
            "雨点","细雨","毛巾","绿豆","本领","起点",
            "土豆","礼貌","右边","路灯","把握",
            "老鼠","猴子","树懒","斑马","狐狸","狗熊","黑熊",
            "豹子","麝牛","狮子","熊猫","疣猪","羚羊","驯鹿",
            "犀牛","猞猁","猩猩","海牛","水獭","海豚","海象","袋鼠",
            "犰狳","河马","海豹","蝙蝠","白虎","狸猫","水牛","山羊",
            "绵羊","牦牛","猿猴","松鼠","野猪","麋鹿","花豹","野狼",
            "灰狼","蜂猴","熊猴","叶猴","紫貂","貂熊","云豹","雪豹",
            "黑麂","鼷鹿","坡鹿","豚鹿","野牛","藏羚","河狸","驼鹿",
            "黄羊","鬣羚","斑羚","岩羊","盘羊","雪兔"
    };

    private static final String[] MODIFIY = {
            "可爱","傻傻","萌萌","羞羞","笨笨","呆呆","美丽","聪明",
            "漂亮","可爱","聪明","懂事","乖巧","淘气","伶俐","狡猾",
            "淘气","顽劣","调皮","顽皮","天真","可爱","无邪","单纯",
            "纯真","稚气","温润","好奇","纯洁","无暇"
    };

    /**
     * 得到一个随机的名字
     * @return
     */
    public static final String getRandomName(){
        return MODIFIY[getRandomNumber(0,MODIFIY.length)] + "的" + NAMES[getRandomNumber(0,NAMES.length)];
    }
}
