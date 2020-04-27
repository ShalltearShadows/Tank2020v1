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
import com.qun.util.EnemyTankPool;
import com.qun.util.ImageUtil;
import com.qun.util.RandomUtil;

import java.awt.*;


/**
 * 敌军坦克
 */
public class EnemyTank extends Tank {

    //敌军坦克
    public static Image[] enemyImg;

    //记录敌人的秒龄
    private long aiTime;


    /**
     * 创建的敌军的坦克
     *
     * @param x   玩家的出生地
     * @param y
     * @param dir
     */
    public EnemyTank(int x, int y, int dir) {
        super(x, y, dir);
        //敌人一创建就计时
        aiTime = System.currentTimeMillis();
    }


    //在静态块初始化
    {
        enemyImg = new Image[4];
        enemyImg[0] = ImageUtil.createImage("res/yellow/up.png");
        enemyImg[1] = ImageUtil.createImage("res/yellow/down.png");
        enemyImg[2] = ImageUtil.createImage("res/yellow/left.png");
        enemyImg[3] = ImageUtil.createImage("res/yellow/right.png");
    }

    public EnemyTank() {
        aiTime = System.currentTimeMillis();
    }


    /**
     * 创建敌人坦克
     * @return
     */
    public static Tank createEnemy(){
        int x = RandomUtil.getRandomNumber(0,2) == 0 ? RADIUS : Constant.FRAME_WIDTH - RADIUS;
        int y = GameFrame.titleBarH + RADIUS;
        int dir = DIR_DOWN;

        Tank enemy = EnemyTankPool.getTank();
        enemy.setX(x);
        enemy.setY(y);
        enemy.setDir(dir);
        enemy.setEnemy(true);
        enemy.setState(STATE_MOVE);
        enemy.setHp(DEFAULT_HP);
        return enemy;
    }


    @Override
    public void drawImgTank(Graphics g){
        ai();
        g.drawImage(enemyImg[getDir()],getX()-RADIUS,getY()-RADIUS,null);
    }


    /**
     * 敌人的AI
     */
    private void ai(){
        if (System.currentTimeMillis() - aiTime > Constant.ENEMY_AI_INTERVAL){
            //给敌人随机一个停止或移动的状态，并随机改变其方向
            setDir(RandomUtil.getRandomNumber(DIR_UP,DIR_RIGHT+1));
            setState(RandomUtil.getRandomNumber(0,2) == 0 ? STATE_STOP : STATE_MOVE);
            aiTime = System.currentTimeMillis();
        }

        //敌人的随机开火
        if (Math.random() < Constant.ENEMY_FIRE_PERCENT){
            fire();
        }
    }
}
