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
    public static final int MAP_HEIGHT = Constant.FRAME_HEIGHT-Tank.RADIUS*6-GameFrame.titleBarH;

    //地图元素块的容器
    private List<MapTile> tiles = new ArrayList<>();

    //大本营
    private TankHouse house;

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

            //新生成的随机块和已经存在的块有重叠部分，那就重新生成
            if (isCollide(tiles,x,y)){
                i--;
                continue;
            }
            tile.setX(x);
            tile.setY(y);
            tiles.add(tile);
        }

        //初始化大本营
        house = new TankHouse();
        tiles.addAll(house.getTiles());
    }

    /**
     * 某一个点确定的地图块。是否和tiles 集合中的所有的块有重叠的部分
     * @param tiles
     * @param x
     * @param y
     * @return 有重叠返回true，否则false
     */
    private boolean isCollide(List<MapTile> tiles, int x ,int y){
        for (MapTile tile : tiles) {
            int tileX = tile.getX();
            int tileY = tile.getY();
            if(Math.abs(tileX-x) < MapTile.tileW && Math.abs(tileY-y) < MapTile.tileW){
                return true;
            }
        }
        return false;
    }

    public void draw(Graphics g){
        for (MapTile tile : tiles) {
            tile.draw(g);
        }
    }

    /**
     *将所有的不可见的地图块从容器中移除
     */
    public void clearDestoryTile() {
        for (int i = 0; i < tiles.size(); i++) {
            MapTile tile = tiles.get(i);
            if (!tile.isVisible())
                tiles.remove(i);
        }
    }

    public List<MapTile> getTiles() {
        return tiles;
    }
}
