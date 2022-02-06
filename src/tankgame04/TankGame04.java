package tankgame04;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/*  version 3
    加入了敌方坦克
    坦克可运动
    绘制了多方向坦克形态
    ------------
    坦克发射多个子弹
    击中敌方坦克爆炸效果
    问题--》hero发射多个子弹的多线程优化问题
    ---》敌方坦克被击中后如何处理，remove的方法--
    已解决
    -------------
    敌方坦克可随机移动在指定范围内
    敌方子弹可在shot子弹消亡后发射新子弹 --》问题，在哪里删除
    我方坦克可被击毁
    -------------
    将我方坦克和敌方坦克的击中判断 封装成一个方法-->多态，父类指向子类引用--（已解决）
    -------------
* */

/*
*  version 4
*   防止敌方坦克重叠 ----已解决
*   记录玩家的成绩，存盘退出【io流】 ---已解决
*   记录退出时敌人坦克坐标，存盘退出【io流】 --解决--可以换成储存对象，再用Vector数组恢复试试,Recorder中修改
*   玩游戏时，选择新游戏或者继续游戏 --已解决
* */

/*
*  version 5
*  进入游戏时播放音乐 --- 已完成
*  异常排除 --- 已完成
*  功能已大体实现，地图等等还有很多可以拓展。。。
* */


public class TankGame04 extends JFrame  {

    MyPanel mp = null;

    public static void main(String[] args) {

        TankGame04 tankGame03 = new TankGame04();

    }

    public TankGame04(){
        mp = new MyPanel();
        //MyPanel实现RUNABLE接口，调用mp的run方法重绘
        Thread thread = new Thread(mp);
        thread.start();

        this.add(mp);
        this.setSize(1300,750);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(3);
        this.setVisible(true);

        //增加响应关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { //匿名内部类重写
//                System.out.println("监听到关闭窗口");
                Recorder.savaRecord();//调用工具类Recorder中的静态方法存盘
                System.exit(0);
            }
        });
    }
}
