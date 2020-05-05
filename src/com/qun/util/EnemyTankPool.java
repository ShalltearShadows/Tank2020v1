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
import com.qun.config.Constant;
import com.qun.game.GameFrame;

import java.util.ArrayList;
import java.util.List;

import static com.qun.config.Constant.*;

/**
 * 敌人坦克对象池
 */
public class EnemyTankPool {
    //存储坦克的容器
    private static List<Tank> pool = new ArrayList<>();
    //坦克建造者模式中的导演类
    private static TankDirector director = new TankDirector();
    //默认对象池的大小
    public static final int DEFAULT_POOL_SIZE = 20;
    //最大数量30
    public static final int MAX_POOL_SIZE = 30;
    //在类加载的时候创建20个对象
    static {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            TankBuilder enemyBuilder = new EnemyTankBuilder();
            director.makeTank(enemyBuilder);
            Tank enemy = enemyBuilder.getTank();
            pool.add(enemy);
        }
    }
    /**
     * 从池塘中获取一个坦克
     * @return
     */
    public static Tank getTank(){
        Tank tank = null;
        //池塘被掏空了
        if (pool.size() == 0){
            //new一个坦克对象
            TankBuilder enemyBuilder = new EnemyTankBuilder();
            director.makeTank(enemyBuilder);
            tank = enemyBuilder.getTank();
        }else {
            //从池塘获取
            tank = pool.remove(0);
        }
        return tank;
    }
    /**
     * 将死亡坦克放回池塘
     * @param tank
     */
    public static void returnTank(Tank tank){
        //池塘已经满了
        if (pool.size() == MAX_POOL_SIZE){
            return;
        }
        tank.setState(STATE_STOP);
        int x = RandomUtil.getRandomNumber(0,2) == 0 ? TANK_RADIUS : Constant.FRAME_WIDTH - TANK_RADIUS;
        int y = GameFrame.titleBarH + TANK_RADIUS;
        tank.setX(x);
        tank.setY(y);
        tank.setBullets(new ArrayList<>());
        tank.setExplodes(new ArrayList<>());
        tank.setHp(DEFAULT_HP);
        pool.add(tank);
    }
}
