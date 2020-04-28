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
   
   3. 解决图片缓冲
      
      使用双缓冲

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
   
> 解决子弹效率的问题 4.25
   
   **问题：坦克发射子弹时，会一直创建新的炮弹对象，因此早晚有一天，又来储存炮弹对象的容器会被撑爆。**
   
   解决方案：使用对象池来解决：提前创建好若干个子弹对象，放到一个容器中。需要的时候，从对象池中拿一个出来，当子弹需要销毁时，再放回去
     
     
> 敌人的坦克 4.25

   1. 随机的在屏幕的左上角和右上角产生敌人的坦克。
   
> 坦克类的优化 4.26

   1. 坦克相关的类：Tank(父类)、MyTank(子类)、EnemyTank(子类)
   
   
> 敌人的AI 4.26
    
   敌人的AI：每隔5秒让敌人随机获得一个状态（站立、行走）
   敌人发射炮弹的AI：游戏的每一帧都随机判断敌人是否发射炮弹
   
   
> 炮弹相碰 4.26

   1. 炮弹与坦克相碰，炮弹消失
   
   2. 使用系统绘制简单的爆炸效果
   
   
> 添加爆炸对象池 4.27


   
> 给坦克添加名字和HP 4.27

   1. 随机生成名字
   2. 添加血条
   3. 死亡处理
   
> 玩家坦克的死亡处理 4.28

   1. 切换游戏状态，用图片显示游戏结束
   2. 提供两个选项，回到主菜单和退出游戏。

   
> 结束游戏回到主菜单，游戏重置处理 4.28
   
   1. 停止newGame()创建敌人线程
   2. 清空坦克的弹夹
   

   
   
   
   