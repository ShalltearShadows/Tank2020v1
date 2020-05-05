/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/4/30
 * @Time: 9:53
 */
package com.qun.map;

import com.qun.config.Constant;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TankHouse {
    //老巢的xy坐标
    public static final int HOUSE_X = Constant.FRAME_WIDTH-3*MapTile.tileW >> 1;
    public static final int HOUSE_Y = Constant.FRAME_HEIGHT-2*MapTile.tileW-7;
    //一共六块地图块
    private List<MapTile> tiles = new ArrayList<>();
    public TankHouse() {
        tiles.add(new MapTile(HOUSE_X,HOUSE_Y));
        tiles.add(new MapTile(HOUSE_X,HOUSE_Y+MapTile.tileW));
        tiles.add(new MapTile(HOUSE_X+MapTile.tileW,HOUSE_Y));

        tiles.add(new MapTile(HOUSE_X+MapTile.tileW*2,HOUSE_Y));
        tiles.add(new MapTile(HOUSE_X+MapTile.tileW*2,HOUSE_Y+MapTile.tileW));

        //设置老巢地图块
        tiles.add(new MapTile(HOUSE_X+MapTile.tileW,HOUSE_Y+MapTile.tileW));
        tiles.get(tiles.size()-1).setType(MapTile.TYPE_HOUSE);
    }

    public void draw(Graphics g){
        for (MapTile tile : tiles) {
            tile.draw(g);
        }
    }

    public List<MapTile> getTiles() {
        return tiles;
    }


}
