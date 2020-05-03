/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/5/3
 * @Time: 15:47
 */
package com.qun.builder;

public class TankDirector {
    public void makeTank(TankBuilder tankBuilder){
        tankBuilder.setName();
        tankBuilder.setHP();
        tankBuilder.setAtk();
        tankBuilder.setSpeed();
        tankBuilder.setBloodBar();
        tankBuilder.setBullets();
        tankBuilder.setExplodes();
    }
}
