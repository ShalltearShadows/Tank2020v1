/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LQ
 * @Date: 2020/4/21
 * @Time: 11:01
 */
package com.qun.game;

import com.qun.pojo.EnemyTank;
import com.qun.pojo.MyTank;
import com.qun.pojo.Tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import static com.qun.config.Constant.*;

/**
 * 游戏的主窗口
 */

public class GameFrame extends Frame implements Runnable{
    //游戏状态
    public static int gameState;

    //菜单指向
    private int menuIndex;

    //标题栏高度
    public static int titleBarH;

    //定义缓冲区
    private Image offScreenImage = null;

    //定义坦克对象
    private Tank myTank;

    //敌人的坦克
    private List<Tank> enemies = new ArrayList<>();

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

                //
                switch (gameState){
                    case STATE_MENU : KeyPressedEventMenu(keyCode); break;
                    case STATE_HELP : KeyPressedEventHelp(keyCode); break;
                    case STATE_ABOUT : KeyPressedEventAbout(keyCode); break;
                    case STATE_RUN : KeyPressedEventRun(keyCode); break;
                    case STATE_OVER : KeyPressedEventOver(keyCode); break;
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
            case STATE_MENU : drawMenu(gbb); break;
            case STATE_HELP : drawHelp(gbb); break;
            case STATE_ABOUT : drawAbout(gbb); break;
            case STATE_RUN : drawRun(gbb); break;
            case STATE_OVER : drawOver(gbb); break;
        }

        //把缓冲区的图一次性画到当前界面上
        g.drawImage(offScreenImage, 0, 0, null);

    }

    /**
     * 在缓冲区绘制菜单状态下的内容
     * @param gbb 缓冲区画笔对象，系统提供
     */
    private void drawMenu(Graphics gbb){


        //绘制黑色的背景
        gbb.setColor(Color.BLACK);
        gbb.fillRect( 0, 0, FRAME_WIDTH, FRAME_HEIGHT) ;

        final int STR_WIDTH = 76;
        int x = FRAME_WIDTH - STR_WIDTH >> 1;
        int y = FRAME_HEIGHT/3;

        final int DIS = 50;

        for (int i = 0; i < MENUS.length; i++) {

            //选中的菜单项的颜色设置为红色
            if (i == menuIndex){
                gbb.setColor(Color.RED);
            }else {
                gbb.setColor(Color.WHITE);
            }

            gbb.drawString(MENUS[i],x,y + DIS*i);
        }


    }


    private void drawOver(Graphics g) {

    }

    //游戏运行状态的绘制
    private void drawRun(Graphics g) {
        //绘制黑色的背景
        g.setColor(Color.BLACK);
        g.fillRect( 0, 0, FRAME_WIDTH, FRAME_HEIGHT) ;

        drawEnemies(g);

        myTank.draw(g);

    }

    private void drawEnemies(Graphics g){
        for (Tank enemy : enemies) {
            enemy.draw(g);
        }
    }

    private void drawAbout(Graphics g) {

    }

    private void drawHelp(Graphics g) {

    }




    //菜单状态按下的按键的处理
    private void KeyPressedEventMenu(int keyCode) {
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
                //TODO
                //启动游戏
                newGame();
                break;
        }


    }

    /**
     * 开始新游戏的方法
     */
    private void newGame() {
        gameState = STATE_RUN;
        //创建坦克对象，敌人的坦克对象
        myTank = new MyTank(400,200, Tank.DIR_DOWN);

        //使用单独一个线程用于控制产生敌人的坦克
        new Thread(){
            @Override
            public void run() {
                while (true){
                    if (enemies.size() < ENEMY_MAX_COUNT){
                        Tank enemy = EnemyTank.createEnemy();
                        enemies.add(enemy);
                    }
                    try {
                        Thread.sleep(ENEMY_BORN_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void KeyPressedEventOver(int keyCode) {

    }

    //游戏中按下的按键的处理
    private void KeyPressedEventRun(int keyCode) {

        switch (keyCode){
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W: myTank.setDir(Tank.DIR_UP);myTank.setState(Tank.STATE_MOVE); break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S: myTank.setDir(Tank.DIR_DOWN);myTank.setState(Tank.STATE_MOVE); break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A: myTank.setDir(Tank.DIR_LEFT);myTank.setState(Tank.STATE_MOVE); break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D: myTank.setDir(Tank.DIR_RIGHT);myTank.setState(Tank.STATE_MOVE); break;
            case KeyEvent.VK_J: myTank.fire(); break;
        }

    }

    private void KeyPressedEventAbout(int keyCode) {

    }

    private void KeyPressedEventHelp(int keyCode) {

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
            case KeyEvent.VK_D: myTank.setState(Tank.STATE_STOP); break;
        }

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
