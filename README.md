# 坦克大战

## 设计过程记录

> 游戏主界面 4.21

 1. 对窗口进行初始化，设置标题，大小，坐标，窗口事件的监听

 2. 游戏菜单的显示，以及游戏菜单的按键的控制

    游戏的若干状态：gameState

    1. 游戏菜单
    2. 游戏帮助
    3. 游戏关于
    4. 游戏中
    5. 游戏结束

    在不同的状态下，绘制不同的内容，按键有不同的处理方式

> 调整重绘机制 4.22

   1. 调整整个游戏窗口画面的重绘机制，来提高帧率FPS

   2. 采用类似动画的机制，要求界面每秒固定的刷新界面

   * **每隔30ms刷新一次界面（调用一次repaint()）**
   * 实现方法：单独启用一个进程用于窗口的重绘

> 绘制坦克 4.23
   
   1. 创建坦克类Tank
   2. 绘制坦克图像
   3. 设置坦克炮管的指向


> 坦克的四向行走 4.24
  
   1. 控制坦克的四个方向的移动
   2. 控制移动的边界
   
> 坦克发射炮弹 4.24
   
   1. 绘制炮弹图像
   2. 让炮弹飞
   3. 坦克控制发射炮弹
   
> 使用图片代替系统绘制的图像 4.24
   
   1. 使用坦克图片