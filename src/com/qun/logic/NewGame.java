/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/5/6
 * @Time: 16:00
 */
package com.qun.logic;

import com.qun.builder.PlayerTankBuilder;
import com.qun.builder.Tank;
import com.qun.builder.TankBuilder;
import com.qun.builder.TankDirector;
import com.qun.map.GameMap;
import com.qun.util.EnemyTankPool;

import static com.qun.config.Constant.*;
import static com.qun.views.GameFrame.*;

public class NewGame {
    /**
     * 开始新游戏的方法
     */
    public static void newGame(int mapType) {
        gameState = STATE_RUN;

        TankDirector director = new TankDirector();
        TankBuilder builder = new PlayerTankBuilder();
        director.makeTank(builder);

        myTank = builder.getTank();

        gameMap = GameMap.getInstance(mapType);


        //使用单独一个线程用于控制产生敌人的坦克
        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < ENEMY_MAX_COUNT ; i++) {

                    if (gameState == STATE_OVER||gameState == STATE_WIN){
                        Thread.interrupted();
                    }

                    //只有在游戏运行状态，才能创建敌人
                    if (gameState != STATE_RUN ){
                        break;
                    }

                    Tank enemy = EnemyTankPool.getTank();
                    enemies.add(enemy);

                    try {
                        Thread.sleep(ENEMY_BORN_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    count = i+1;
                }
            }
        }.start();
    }
}
