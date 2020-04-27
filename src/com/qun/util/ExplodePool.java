/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/4/27
 * @Time: 9:06
 */
package com.qun.util;

import com.qun.game.Explode;

import java.util.ArrayList;
import java.util.List;

public class ExplodePool {
    //储存所有的爆炸的容器
    private static List<Explode> pool = new ArrayList<>();

    //默认对象池的大小
    public static final int DEFAULT_POOL_SIZE = 20;
    //最大长度300
    public static final int MAX_POOL_SIZE = 30;

    //在类加载的时候创建20个爆炸对象
    static {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            pool.add(new Explode());
        }
    }

    /**
     * 从池塘获取一个爆炸对象
     * @return
     */
    public static Explode getExplode(){
        Explode explode = null;

        //池塘被掏空了
        if (pool.size() == 0){
            explode = new Explode();
        }else {
            explode = pool.remove(0);
        }


        return explode;
    }


    /**
     * 爆炸被销毁的时候，归还到池塘里
     */
    public static void returnExplode(Explode explode){
        //池塘已经满了
        if (pool.size() == MAX_POOL_SIZE){
            return;
        }
        pool.add(explode);

    }
}
