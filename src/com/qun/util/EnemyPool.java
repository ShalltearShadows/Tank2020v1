/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/5/3
 * @Time: 17:58
 */
package com.qun.util;

import com.qun.builder.EnemyTankBuilder;
import com.qun.builder.Tank;
import com.qun.builder.TankBuilder;
import com.qun.builder.TankDirector;

import java.util.ArrayList;
import java.util.List;

public class EnemyPool {
    private static List<Tank> pool = new ArrayList<>();

    private static TankDirector director = new TankDirector();

    //默认对象池的大小
    public static final int DEFAULT_POOL_SIZE = 20;
    //最大长度30
    public static final int MAX_POOL_SIZE = 30;

    //在类加载的时候创建200个子弹对象
    static {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {

            TankBuilder enemyBuilder = new EnemyTankBuilder();
            director.makeTank(enemyBuilder);
            Tank enemy = enemyBuilder.getTank();

            pool.add(enemy);
        }
    }

    public static Tank getTank(){
        Tank tank = null;

        //池塘被掏空了
        if (pool.size() == 0){
            TankBuilder enemyBuilder = new EnemyTankBuilder();
            director.makeTank(enemyBuilder);
            tank = enemyBuilder.getTank();
        }else {
            tank = pool.remove(0);
        }

        return tank;
    }


    public static void returnTank(Tank tank){
        //池塘已经满了
        if (pool.size() == MAX_POOL_SIZE){
            return;
        }
        pool.add(tank);

    }
}
