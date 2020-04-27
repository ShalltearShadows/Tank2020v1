/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/4/27
 * @Time: 11:16
 */
package com.qun.util;

import com.qun.pojo.EnemyTank;
import com.qun.pojo.Tank;

import java.util.ArrayList;
import java.util.List;


/**
 * 敌人坦克的对象池
 */
public class EnemyTankPool {
    //储存所有的坦克的容器
    private static List<Tank> pool = new ArrayList<>();

    //默认对象池的大小
    public static final int DEFAULT_POOL_SIZE = 20;
    //最大长度20
    public static final int MAX_POOL_SIZE = 20;

    //在类加载的时候创建20对象
    static {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            pool.add(new EnemyTank());
        }
    }

    /**
     * 从池塘获取一个炮弹对象
     * @return
     */
    public static Tank getTank(){
        Tank tank = null;

        //池塘被掏空了
        if (pool.size() == 0){
            tank = new EnemyTank();
        }else {
            tank = pool.remove(0);
        }


        return tank;
    }


    /**
     * 坦克死亡的时候，归还到池塘里
     */
    public static void returnTank(Tank tank){
        //池塘已经满了
        if (pool.size() == MAX_POOL_SIZE){
            return;
        }
        pool.add(tank);

    }
}
