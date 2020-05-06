/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LQ
 * @Date: 2020/4/21
 * @Time: 11:01
 */
package com.qun.views;

import com.qun.builder.Tank;
import com.qun.logic.NewGame;
import com.qun.logic.RestGame;
import com.qun.map.GameMap;
import com.qun.util.ImageUtil;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import static com.qun.config.Constant.*;

;

/**
 * 游戏的主窗口
 */

public class GameFrame extends Frame implements Runnable{
    //游戏状态
    public static int gameState;

    //菜单指向
    public static int menuIndex;

    //标题栏高度
    public static int titleBarH;

    //定义缓冲区
    private Image offScreenImage = null;

    //定义坦克对象
    public static Tank myTank;

    //敌人的坦克
    public static List<Tank> enemies = new ArrayList<Tank>();

    //第一次使用的时候加载，而不是类加载的时候加载
    private Image overImg = null;

    private Image victoryImg = null;

    //地图容器
    public static GameMap gameMap;

    public static int select = -1;

    public static int count = 0;


    /**
     * 对窗口进行初始化
     */
    public GameFrame(){
        initFrame();
        initEventListener();

        //使用帧率重绘
        new Thread(this).start();
    }

    /**
     *对窗口属性进行初始化
     */
    private void initFrame() {
        //设置窗口标题
        setTitle(GAME_TITLE);

        //设置窗口大小
        setSize(FRAME_WIDTH,FRAME_HEIGHT);

        //设置窗口左上角坐标
        setLocation(FRAME_X,FRAME_Y);

        //设置窗口大小不可变
        setResizable(false);

        //设置窗口可见
        setVisible(true);

        //设置标题栏高度
        titleBarH = getInsets().top;

    }

    /**
     * 初始化窗口的事件监听
     */
    private void initEventListener(){

        addWindowListener(new WindowAdapter() {

            //点击关闭时，退出系统
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //添加按键监听事件
        addKeyListener(new KeyAdapter() {
            //按键按下的时候，调用的方法
            @Override
            public void keyPressed(KeyEvent e) {
                //返回按下的按键
                int keyCode = e.getKeyCode();

                switch (gameState){
                    case STATE_MENU : keyPressedEventMenu(keyCode); break;
                    case STATE_RUN : keyPressedEventRun(keyCode); break;
                    case STATE_HELP : keyPressedEventHelp(keyCode); break;
                    case STATE_WIN :
                    case STATE_OVER : KeyPressedEventOverOrWin(keyCode); break;
                }
            }

            //按键松开的时候，调用的方法
            @Override
            public void keyReleased(KeyEvent e) {
                //返回松开的按键
                int keyCode = e.getKeyCode();

                //
                if (gameState == STATE_RUN){
                    keyReleasedEventRun(keyCode);
                }

            }


        });
    }

    //菜单状态按下的按键的处理
    private void keyPressedEventMenu(int keyCode) {
        switch (keyCode){
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                menuIndex--;
                if (menuIndex < 0){
                    menuIndex = MENUS.length - 1;
                }
                break;

            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                menuIndex++;
                if (menuIndex > MENUS.length - 1){
                    menuIndex = 0;
                }
                break;

            case KeyEvent.VK_ENTER:
                switch (menuIndex){
                    case 0: NewGame.newGame(1); break;
                    case 1: NewGame.newGame(2); break;
                    case 2: NewGame.newGame(3); break;
                    case 3: gameState = STATE_HELP; break;
                    case 4: System.exit(0); break;
                }
        }
    }

    //游戏中按下的按键的处理
    private void keyPressedEventRun(int keyCode) {
        if (select==-1){
            switch (keyCode){
                case KeyEvent.VK_J:select = TYPE_BROWN; break;
                case KeyEvent.VK_K:select = TYPE_PURPLE; break;
            }
            myTank.setType(select);
        }else {
            switch (keyCode){
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W: myTank.setDir(DIR_UP);myTank.setState(STATE_MOVE); break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S: myTank.setDir(DIR_DOWN);myTank.setState(STATE_MOVE); break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A: myTank.setDir(DIR_LEFT);myTank.setState(STATE_MOVE); break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D: myTank.setDir(DIR_RIGHT);myTank.setState(STATE_MOVE); break;
                case KeyEvent.VK_J: myTank.fire(); break;
            }
        }
    }

    private void keyPressedEventHelp(int keyCode) {
        if (keyCode == KeyEvent.VK_ENTER){
            gameState = STATE_MENU;
        }
    }

    //按键松开时，让坦克停下
    private void keyReleasedEventRun(int keyCode) {

        switch (keyCode){
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D: myTank.setState(STATE_STOP); break;
        }

    }

    /**
     * 对游戏初始化
     */
    private void initGame(){
        gameState = STATE_MENU;
    }

    /**
     * 是Frame类的方法，继承下来的
     * 该方法负责了所有的绘制的内容
     * 所有需要在屏幕中显示的内容，都要在该方法内调用，但是该方法不能主动调用
     * 必须通过repaint()去回调该方法
     * @param g 画笔对象，系统提供
     */
    @Override
    public void update(Graphics g) {
        //创建一个与原图形界面大小相同的缓冲区，其中FRAME_WIDTH，FRAME_HEIGHT根据你的界面来修改。
        if (offScreenImage == null) {
            offScreenImage = this.createImage(FRAME_WIDTH, FRAME_HEIGHT);
        }

        //拿到缓冲区的画笔
        Graphics gbb = offScreenImage.getGraphics();

        //字体
        gbb.setFont(FONT);

        switch (gameState){
            case STATE_MENU : GameMenu.drawMenu(gbb,menuIndex); break;
            case STATE_RUN :
                if (select==-1){
                    drawSeclect(gbb);
                }else {
                    GameRun.drawRun(gbb);
                }
                break;
            case STATE_HELP : GameHelp.drawHelp(gbb); break;
            case STATE_WIN : drawWin(gbb); break;
            case STATE_OVER : drawOver(gbb); break;
        }

        //把缓冲区的图一次性画到当前界面上
        g.drawImage(offScreenImage, 0, 0, null);
    }


    private void drawWin(Graphics g){
        if (victoryImg == null){
            victoryImg = ImageUtil.createImage("res/victor.jpg");
        }

        int imgW = victoryImg.getWidth(null);
        int imgH = victoryImg.getHeight(null);

        g.drawImage(victoryImg,FRAME_WIDTH-imgW>>1,FRAME_HEIGHT-imgH>>1,null);

        //添加按键提示信息
        g. setColor(Color.WHITE);
        g. drawString(OVER_STRO,10, FRAME_HEIGHT-40);
        g. drawString(OVER_STR1,FRAME_WIDTH-223,FRAME_HEIGHT-40);
    }


    /**
     * 绘制游戏结束的画面
     * @param g
     */
    private void drawOver(Graphics g) {
        //保证只加载一次
        if (overImg == null){
            overImg = ImageUtil.createImage("res/over.jpg");
        }

        int imgW = overImg.getWidth(null);
        int imgH = overImg.getHeight(null);

        g.drawImage(overImg,FRAME_WIDTH-imgW>>1,FRAME_HEIGHT-imgH>>1,null);

        //添加按键提示信息
        g. setColor(Color.WHITE);
        g. drawString(OVER_STRO,10, FRAME_HEIGHT-40);
        g. drawString(OVER_STR1,FRAME_WIDTH-223,FRAME_HEIGHT-40);

    }

    private void drawSeclect(Graphics g){

        //绘制黑色的背景
        g.setColor(Color.BLACK);
        g.fillRect( 0, 0, FRAME_WIDTH, FRAME_HEIGHT) ;

        g.drawImage(Tank.tankImg[TYPE_BROWN][0],FRAME_WIDTH/2-200,FRAME_HEIGHT/2+100,null);
        g.drawImage(Tank.tankImg[TYPE_PURPLE][0],FRAME_WIDTH/2+200,FRAME_HEIGHT/2+100,null);

        g.setColor(Color.GREEN);
        g.setFont(MIDDLE_FONT);
        g.drawString("按J选择左边的坦克",FRAME_WIDTH/2-50,FRAME_HEIGHT/2);
        g.drawString("按K选择右边的坦克",FRAME_WIDTH/2-50,FRAME_HEIGHT/2+20);
    }


    //失败或胜利按键处理
    private void KeyPressedEventOverOrWin(int keyCode) {
        //结束游戏
        if(keyCode == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }else if(keyCode == KeyEvent.VK_ENTER){
            setGameState(STATE_MENU);
            //重置游戏属性
            RestGame.resetGame();
        }
    }

    public static void setGameState(int gameState) {
        GameFrame.gameState = gameState;
    }


    @Override
    public void run() {

        //控制帧率
        while (true){
            repaint();
            try {
                Thread.sleep(REPAINT_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
