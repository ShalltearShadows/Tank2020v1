/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/5/6
 * @Time: 15:34
 */
package com.qun.views;

import com.qun.builder.Tank;
import com.qun.logic.Collide;
import com.qun.util.EnemyTankPool;

import java.awt.*;

import static com.qun.config.Constant.*;
import static com.qun.views.GameFrame.*;

public class GameRun {

    //游戏运行状态的绘制
    public static void drawRun(Graphics g) {
        //绘制黑色的背景
        g.setColor(Color.BLACK);
        g.fillRect( 0, 0, FRAME_WIDTH, FRAME_HEIGHT) ;
        drawEnemies(g);

        myTank.draw(g);

        //绘制地图
        gameMap.draw(g);
        //炮弹和砖块的碰撞
        Collide.CollideMapTile();
        //坦克与炮弹碰撞的判断
        Collide.bulletCollideTank();
        //爆炸效果
        drawExplodes(g);
        //游戏胜利判断
        gameWin();

    }


    //绘制所有的敌人的坦克,如果敌人已经死亡，从容器中移除
    private static void drawEnemies(Graphics g){
        for (int i = 0; i < enemies.size(); i++) {
            Tank enemy = enemies.get(i);
            if(enemy.isDie()){
                enemies.remove(i);
                EnemyTankPool.returnTank(enemy);
                i--;
                continue;
            }
            enemy.draw(g);
        }
    }

    /**
     * 所有的坦克上的爆炸效果
     */
    private static void drawExplodes(Graphics g){
        for (Tank enemy : enemies) {
            enemy.drawExplodes(g);
        }
        myTank.drawExplodes(g);
    }

    private static void gameWin(){
        boolean win = true;
        if (count==10){
            for (Tank enemy : enemies) {
                if (!enemy.isDie()){
                    win = false;
                    break;
                }
            }
            if (win){
                gameState = STATE_WIN;
            }
        }
    }
}
