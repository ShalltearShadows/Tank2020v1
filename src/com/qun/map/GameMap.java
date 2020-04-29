/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/4/29
 * @Time: 8:56
 */
package com.qun.map;

import com.qun.config.Constant;
import com.qun.game.GameFrame;
import com.qun.pojo.Tank;
import com.qun.util.MapTilePool;
import com.qun.util.RandomUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 游戏地图类
 */
public class GameMap {

    public static final int MAP_X = Tank.RADIUS*3;
    public static final int MAP_Y = Tank.RADIUS*3 + GameFrame.titleBarH;
    public static final int MAP_WIDTH = Constant.FRAME_WIDTH-Tank.RADIUS*6;
    public static final int MAP_HEIGHT = Constant.FRAME_HEIGHT-Tank.RADIUS*8-GameFrame.titleBarH;

    //地图元素块的容器
    private List<MapTile> tiles = new ArrayList<>();

    public GameMap() {
        initMap();
    }

    /**
     * 初始化地图砖块
     */
    private void initMap(){
        //得到CONUT个随机位置的砖块，添加到容器中
        final int COUNT = 20;

        for (int i = 0; i < COUNT; i++) {
            MapTile tile = MapTilePool.getMapTile();

            int x = RandomUtil.getRandomNumber(MAP_X ,MAP_X+MAP_WIDTH-MapTile.tileW);
            int y = RandomUtil.getRandomNumber(MAP_Y,MAP_Y+MAP_HEIGHT-MapTile.tileW);
            tile.setX(x);
            tile.setY(y);
            tiles.add(tile);
        }
    }

    public void draw(Graphics g){
        for (MapTile tile : tiles) {
            tile.draw(g);
        }
    }
}
