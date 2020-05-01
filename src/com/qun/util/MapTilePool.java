/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/4/29
 * @Time: 9:00
 */
package com.qun.util;

import com.qun.map.MapTile;

import java.util.ArrayList;
import java.util.List;

public class MapTilePool {
    //储存砖块的容器
    private static List<MapTile> pool = new ArrayList<>();

    //默认对象池的大小
    public static final int DEFAULT_POOL_SIZE = 150;
    //最大长度20
    public static final int MAX_POOL_SIZE = 200;

    //在类加载的时候创建20对象
    static {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            pool.add(new MapTile());
        }
    }

    /**
     * 从池塘获取一个砖块对象
     * @return
     */
    public static MapTile getMapTile(){
        MapTile mapTile = null;

        //池塘被掏空了
        if (pool.size() == 0){
            mapTile = new MapTile();
        }else {
            mapTile = pool.remove(0);
        }

        mapTile.setVisible(true);

        return mapTile;
    }


    /**
     * 砖块销毁的时候，归还到池塘里
     */
    public static void returnMapTile(MapTile mapTile){
        //池塘已经满了
        if (pool.size() == MAX_POOL_SIZE){
            return;
        }
        pool.add(mapTile);

    }
}
