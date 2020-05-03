/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/5/3
 * @Time: 15:40
 */
package com.qun.builder;

import com.qun.pojo.BloodBar;
import com.qun.util.RandomUtil;

import java.util.ArrayList;

import static com.qun.config.Constant.*;

public class PlayerTankBuilder extends TankBuilder {

    private Tank tank;

    public PlayerTankBuilder() {
        tank = new Tank(FRAME_WIDTH/3,FRAME_HEIGHT-TANK_RADIUS*2,DIR_UP);
    }

    @Override
    public void setName() {
        String playName = RandomUtil.getRandomName();
        tank.setName(playName);
    }

    @Override
    public void setHP() {
        tank.setHp(DEFAULT_HP);
    }

    @Override
    public void setAtk() {
        tank.setAtk(30);
    }

    @Override
    public void setSpeed() {
        tank.setSpeed(DEFAULT_SPEED);
    }

    @Override
    public void setBloodBar() {
        tank.setBloodBar(new BloodBar());
    }

    @Override
    public void setBullets() {
        tank.setBullets(new ArrayList<>());
    }

    @Override
    public void setExplodes() {
        tank.setExplodes(new ArrayList<>());
    }

    @Override
    public Tank getTank() {
        return tank;
    }
}
