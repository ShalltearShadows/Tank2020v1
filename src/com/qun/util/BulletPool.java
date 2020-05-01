/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/4/25
 * @Time: 8:54
 */
package com.qun.util;

import com.qun.pojo.Bullet;

import java.util.ArrayList;
import java.util.List;


/**
 * 炮弹的对象池
 */
public class BulletPool {
    //储存所有的炮弹的容器
    private static List<Bullet> pool = new ArrayList<>();

    //默认对象池的大小
    public static final int DEFAULT_POOL_SIZE = 50;
    //最大长度300
    public static final int MAX_POOL_SIZE = 100;

    //在类加载的时候创建200个子弹对象
    static {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            pool.add(new Bullet());
        }
    }

    /**
     * 从池塘获取一个炮弹对象
     * @return
     */
    public static Bullet getBullet(){
        Bullet bullet = null;

        //池塘被掏空了
        if (pool.size() == 0){
            bullet = new Bullet();
        }else {
           bullet = pool.remove(0);
        }

        return bullet;
    }


    /**
     * 炮弹被销毁的时候，归还到池塘里
     */
    public static void returnBullet(Bullet bullet){
        //池塘已经满了
        if (pool.size() == MAX_POOL_SIZE){
            return;
        }
        pool.add(bullet);

    }
}
