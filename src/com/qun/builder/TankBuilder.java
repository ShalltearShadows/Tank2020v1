/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/5/3
 * @Time: 15:32
 */
package com.qun.builder;

public abstract class TankBuilder {

    public abstract void setName();
    public abstract void setHP();
    public abstract void setAtk();
    public abstract void setSpeed();
    public abstract void setBloodBar();
    public abstract void setBullets();
    public abstract void setExplodes();

    public abstract Tank getTank();

}
