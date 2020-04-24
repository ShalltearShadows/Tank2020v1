/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LQ
 * @Date: 2020/4/21
 * @Time: 11:51
 **/

package com.qun.config;

import java.awt.*;

/*
* 游戏常量类
* */
public class Constant {
    /****************************游戏窗口相关******************************/
    //标题
    public static final String GAME_TITLE = "坦克大战";

    //窗口大小
    public static final int FRAME_WIDTH = 900;
    public static final int FRAME_HEIGHT = 700;

    public static final int X = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int Y = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    //窗口坐标
    public static final int FRAME_X = (X - FRAME_WIDTH ) >> 1;
    public static final int FRAME_Y = (Y - FRAME_HEIGHT ) >> 1;

    /****************************游戏菜单相关******************************/

    public static final int STATE_MENU = 0;
    public static final int STATE_HELP = 1;
    public static final int STATE_ABOUT = 2;
    public static final int STATE_RUN = 3;
    public static final int STATE_OVER = 4;

    public static final String[] MENUS = {
            "开始游戏",
            "继续游戏",
            "游戏帮助",
            "游戏关于",
            "退出游戏",
    };

    //字体
    public static final Font FONT = new Font("宋体",Font.BOLD,24);

    //FPS
    public static final int REPAINT_INTERVAL = 37;
}
