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
import com.qun.util.MapTilePool;
import com.qun.util.RandomUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.qun.config.Constant.*;

/**
 * 游戏地图类
 */
public class GameMap {

    private static final int MAP_X = TANK_RADIUS*3;
    private static final int MAP_Y = TANK_RADIUS*3 + GameFrame.titleBarH;
    private static final int MAP_WIDTH = Constant.FRAME_WIDTH-TANK_RADIUS*6;
    private static final int MAP_HEIGHT = Constant.FRAME_HEIGHT-TANK_RADIUS*6-GameFrame.titleBarH;

    //地图元素块的容器
    private List<MapTile> tiles = new ArrayList<>();

    //大本营
    private TankHouse house;

    private static GameMap gameMap;

    private GameMap(int type) {
        switch (type){
            case 1: secondMap(); break;
            case 2: firstMap(); break;
            case 3: randomMap(); break;
        }
        initHouse();
    }


    public static GameMap getInstance(int type){
        gameMap = new GameMap(type);
        return gameMap;
    }


    private void initHouse(){
        //初始化大本营
        house = new TankHouse();
        tiles.addAll(house.getTiles());
    }


    /**
     * 随机地图
     */
    private void randomMap(){
        //得到CONUT个随机位置的砖块，添加到容器中
        final int COUNT = 30;

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

    }


    /**
     * 往地图块容器中添加一行指定类型的地图块
     * @param startX 派加地图块的起始的x坐标
     * @param startY 派加地图块的超始的Y坐标
     * @param endX 派加地图块的结束的x坐标
     * @param type 地图块的类型
     * @param DIS 地图块的间隔
     */
    public void addRow(int startX, int startY, int endX, int type, final int DIS){
        startY += 13;
        int count = (endX - startX )/(MapTile.tileW+DIS);
        for(int i=0;i<count;i++){
            MapTile tile = MapTilePool.getMapTile();
            tile.setType(type);
            tile.setX(startX + i * (MapTile.tileW+DIS));
            tile.setY(startY);
            tiles.add(tile);
        }
    }

    public void firstMap(){
        addRow(MAP_X,MAP_Y,MAP_X+MAP_WIDTH,MapTile.TYPE_NORMAL,0);
        addRow(MAP_X,MAP_Y+MapTile.tileW*2,MAP_X+MAP_WIDTH,MapTile.TYPE_NORMAL,0);
        addRow(MAP_X,MAP_Y+MapTile.tileW*4,MAP_X+MAP_WIDTH,MapTile.TYPE_NORMAL,MapTile.tileW);
        addRow(MAP_X,MAP_Y+MapTile.tileW*6,MAP_X+MAP_WIDTH,MapTile.TYPE_NORMAL,MapTile.tileW*2);
        addRow(MAP_X,MAP_Y+MapTile.tileW*8,MAP_X+MAP_WIDTH,MapTile.TYPE_NORMAL,MapTile.tileW*3);
    }

    public void secondMap(){
        addRow(MAP_X,MAP_Y,MAP_X+MAP_WIDTH,MapTile.TYPE_NORMAL,0);
        addRow(MAP_X,MAP_Y+MapTile.tileW,MAP_X+MAP_WIDTH,MapTile.TYPE_NORMAL,0);
        addRow(MAP_X,MAP_Y+MapTile.tileW*2,MAP_X+MAP_WIDTH,MapTile.TYPE_NORMAL,0);
        addRow(MAP_X,MAP_Y+MapTile.tileW*7,MAP_X+MAP_WIDTH,MapTile.TYPE_NORMAL,0);
        addRow(MAP_X,MAP_Y+MapTile.tileW*8,MAP_X+MAP_WIDTH,MapTile.TYPE_NORMAL,0);
        addRow(MAP_X,MAP_Y+MapTile.tileW*9,MAP_X+MAP_WIDTH,MapTile.TYPE_NORMAL,0);


        addRow(MAP_X,MAP_Y+MapTile.tileW*10,MAP_X+MapTile.tileW*4,MapTile.TYPE_NORMAL,0);
        addRow(MAP_X,MAP_Y+MapTile.tileW*11,MAP_X+MapTile.tileW*4,MapTile.TYPE_NORMAL,0);
        addRow(MAP_X,MAP_Y+MapTile.tileW*12,MAP_X+MapTile.tileW*4,MapTile.TYPE_NORMAL,0);
        addRow(MAP_X,MAP_Y+MapTile.tileW*13,MAP_X+MapTile.tileW*4,MapTile.TYPE_NORMAL,0);
        addRow(MAP_X,MAP_Y+MapTile.tileW*14,MAP_X+MapTile.tileW*4,MapTile.TYPE_NORMAL,0);

        addRow(MAP_X+MapTile.tileW*14,MAP_Y+MapTile.tileW*10,MAP_X+MapTile.tileW*18,MapTile.TYPE_NORMAL,0);
        addRow(MAP_X+MapTile.tileW*14,MAP_Y+MapTile.tileW*11,MAP_X+MapTile.tileW*18,MapTile.TYPE_NORMAL,0);
        addRow(MAP_X+MapTile.tileW*14,MAP_Y+MapTile.tileW*12,MAP_X+MapTile.tileW*18,MapTile.TYPE_NORMAL,0);
        addRow(MAP_X+MapTile.tileW*14,MAP_Y+MapTile.tileW*13,MAP_X+MapTile.tileW*18,MapTile.TYPE_NORMAL,0);
        addRow(MAP_X+MapTile.tileW*14,MAP_Y+MapTile.tileW*14,MAP_X+MapTile.tileW*18,MapTile.TYPE_NORMAL,0);
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

    public void clear(){
        tiles.clear();
    }

    public List<MapTile> getTiles() {
        return tiles;
    }
}
