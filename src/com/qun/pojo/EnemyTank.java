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
import static com.qun.config.Constant.*;

/**
 * 敌军坦克
 */
public class EnemyTank extends Tank {

    //敌军坦克
    public static Image[][] enemyImg;

    //记录敌人的秒龄
    private long aiTime;

    public static final int TYPE_CYAN = 0;
    public static final int TYPE_YELLOW = 1;

    private int tpye = TYPE_CYAN;

    private String[] color = {"cyan","yellow"};

    //在静态块初始化
    {
        enemyImg = new Image[2][4];

        for (int i = 0; i < enemyImg.length; i++) {
            enemyImg[i][0] = ImageUtil.createImage("res/"+color[i]+"/up.png");
            enemyImg[i][1] = ImageUtil.createImage("res/"+color[i]+"/down.png");
            enemyImg[i][2] = ImageUtil.createImage("res/"+color[i]+"/left.png");
            enemyImg[i][3] = ImageUtil.createImage("res/"+color[i]+"/right.png");
        }
    }

    public EnemyTank() {
        aiTime = System.currentTimeMillis();
        tpye = RandomUtil.getRandomNumber(0,2);
    }


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
    protected void drawName(Graphics g) {
        g.setFont(SIMALL_FONT);
        g.setColor(Color.RED);
        g.drawString(name,x-RADIUS+5,y-RADIUS-15);
    }


    @Override
    public void drawImgTank(Graphics g){
        g.drawImage(enemyImg[tpye][getDir()],getX()-RADIUS,getY()-RADIUS,null);
    }

    /**
     * 敌人的AI
     */
    @Override
    protected void move(){
        if (System.currentTimeMillis() - aiTime > Constant.ENEMY_AI_INTERVAL){
            //给敌人随机一个停止或移动的状态，并随机改变其方向
            setDir(RandomUtil.getRandomNumber(DIR_UP,DIR_RIGHT+1));
            setState(RandomUtil.getRandomNumber(0,2) == 0 ? STATE_STOP : STATE_MOVE);
            aiTime = System.currentTimeMillis();
        }

        oldX = x;
        oldY = y;
        switch (dir){
            case DIR_UP: y -= speed; if (y < RADIUS + GameFrame.titleBarH){y = RADIUS + GameFrame.titleBarH;} break;
            case DIR_DOWN: y += speed; if (y > Constant.FRAME_HEIGHT-RADIUS){y = Constant.FRAME_HEIGHT-RADIUS;} break;
            case DIR_LEFT: x -= speed; if (x < RADIUS){x = RADIUS;} break;
            case DIR_RIGHT: x += speed; if (x > Constant.FRAME_WIDTH-RADIUS){x = Constant.FRAME_WIDTH-RADIUS;} break;
        }

        //敌人的随机开火
        if (Math.random() < Constant.ENEMY_FIRE_PERCENT){
            fire();
        }
    }
}
