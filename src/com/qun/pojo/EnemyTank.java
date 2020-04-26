/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/4/26
 * @Time: 13:58
 */
package com.qun.pojo;

import com.qun.config.Constant;
import com.qun.game.GameFrame;
import com.qun.util.RandomUtil;

import java.awt.*;


/**
 * 敌军坦克
 */
public class EnemyTank extends Tank {

    //敌军坦克
    public static Image[] enemyImg;


    /**
     * 创建的敌军的坦克
     *
     * @param x   玩家的出生地
     * @param y
     * @param dir
     */
    public EnemyTank(int x, int y, int dir) {
        super(x, y, dir);
    }


    //在静态块初始化
    {
        enemyImg = new Image[4];
        enemyImg[0] = Toolkit.getDefaultToolkit().createImage("res/yellow/up.png");
        enemyImg[1] = Toolkit.getDefaultToolkit().createImage("res/yellow/down.png");
        enemyImg[2] = Toolkit.getDefaultToolkit().createImage("res/yellow/left.png");
        enemyImg[3] = Toolkit.getDefaultToolkit().createImage("res/yellow/right.png");
    }


    /**
     * 创建敌人坦克
     * @return
     */
    public static Tank createEnemy(){
        int x = RandomUtil.getRandomNumber(0,2) == 0 ? RADIUS : Constant.FRAME_WIDTH - RADIUS;
        int y = GameFrame.titleBarH + RADIUS;
        int dir = DIR_DOWN;

        Tank enemy = new EnemyTank(x,y,dir);
        enemy.setEnemy(true);

        enemy.setState(STATE_MOVE);

        return enemy;
    }


    @Override
    public void drawImgTank(Graphics g){
        g.drawImage(enemyImg[getDir()],getX()-RADIUS,getY()-RADIUS,null);
    }
}
