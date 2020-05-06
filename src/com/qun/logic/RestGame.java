/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/5/6
 * @Time: 15:57
 */
package com.qun.logic;

import com.qun.builder.Tank;
import static com.qun.views.GameFrame.*;

public class RestGame {
    public static void resetGame(){
        //先让自己的坦克的子弹还回对象池.
        myTank.bulletsReturn() ;
        //销毁自己的坦克
        myTank = null;
        //清空敌人
        for (Tank enemy : enemies) {
            enemy.bulletsReturn();
        }

        count = 0;

        enemies.clear();
        //清空地图资源
        gameMap.clear();
        gameMap = null;

        menuIndex = 0;
        select = -1;
    }
}
