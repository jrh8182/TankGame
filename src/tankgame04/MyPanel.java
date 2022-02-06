package tankgame04;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Scanner;
import java.util.Vector;


public class MyPanel extends JPanel implements KeyListener, Runnable {
    //我的坦克
    Hero hero = null;
    //敌人坦克集合，用Vector
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //存放爆炸效果
    Vector<Boom> booms = new Vector<>();
//    Scanner in  = new Scanner(System.in);
    //定义三张图片，显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;
    BufferedReader br = null;

    private static String recordFile = "src\\myRecord.txt";


    int enemyTankSize = 5;

    public MyPanel() {
        hero = new Hero(100, 100);
        //引用给Recorder中的敌方坦克集合用来存存档
        Recorder.setEnemyTanks(enemyTanks);
        //
        boolean loop = true ;
        while (loop) {

            System.out.println("请选择 新的游戏  or  继续上局游戏  [1,2]");
            int choice = Utility.readInt(); //控制选择游戏方式,调了一下工具类的方法，可以自己写

            if (choice == 1) { //若选择新游戏
                loop = false; //选择完毕，不再循环选择
                for (int i = 0; i < enemyTankSize; i++) {
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
                    //将此集合赋值给每一个EnemyTank对象的 集合元素Vector,用以后续的重叠判定
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置炮筒向下
                    enemyTank.setDirect(2);
                    new Thread(enemyTank).start();
                    enemyTanks.add(enemyTank);
                    //生成敌方的子弹->已在EnemyTank run方法中生成,下面几行只是初期测试子弹伴随坦克生成发射子弹
                    //            Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //            new Thread(shot).start();
                    //            enemyTank.shots.add(shot);
                }
            } else if (choice == 2) { // 继续游戏，恢复原来的坦克和击杀
                loop = false ;  //先将循环关闭

                //判断存档文件是否为空，若为空只能进行新游戏
                File file = new File(MyPanel.recordFile);
                if(file.length()==0){
                    System.out.println("当前无已保存的游戏，请按“1”选择开始新的游戏。。。");
                    loop = true ; //还需要重新循环
                    continue; //跳过下列步骤，进入下一轮循环
                }

                try {
                    String line;
                    br = new BufferedReader(new FileReader(MyPanel.recordFile)); //创建输出流,用来读取敌方坦克坐标

                    Recorder.setAllEnemyTankNum(Integer.parseInt(br.readLine())); //读取上一次退出时的击杀数，要用readLine不然指针不会移到下一行
                    //注意修改要生产的坦克数量，不然会报空指针异常
                    while ((line = br.readLine()) != null) {

                        String[] spilt = line.split(" "); //分隔成数组
                        int x = Integer.parseInt(spilt[0]); //存储x和y
                        int y = Integer.parseInt(spilt[1]);
                        int direct = Integer.parseInt(spilt[2]); //炮筒方向

                        EnemyTank enemyTank = new EnemyTank(x, y);//
                        // 将此集合赋值给每一个EnemyTank对象的 集合元素Vector,用以后续的重叠判定
                        enemyTank.setEnemyTanks(enemyTanks);
                        //设置炮筒向下
                        enemyTank.setDirect(direct);
                        new Thread(enemyTank).start();
                        enemyTanks.add(enemyTank);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            } else {
                //输入不是1或2,重新选择，直至选择正确
            }
        }
//        hero.setSpeed(3);
        //加载爆炸图像
        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/boom1.png"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/boom2.png"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/boom3.png"));

        //播放背景音乐
        new AePlayWave("src\\startmusic.wav").start();
    }



    /*
     * 显示玩家的成绩(击毁敌方坦克的数量),用图像形式表现
     * */
    public void showInfo(Graphics g) {
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font); //设置字体，颜色

        g.drawString("您累计击毁敌方坦克", 1020, 30);
        drawTank(1020, 60, g, 0, 0);
        g.setColor(Color.BLACK); //重置画笔颜色
        g.drawString(Recorder.getAllEnemyTankNum() + "", 1080, 100);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);
        showInfo(g);
        //绘制玩家坦克
        if (hero.isLive) { //判断坦克生命
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
        }

        //绘制敌方坦克和子弹
        for (int i = 0; i < enemyTanks.size(); i++) {
            //取出敌方坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //若坦克死亡，则不绘制
            if (enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
            }
            if (enemyTank.shots.size() != 0) {
                //取出每一个子弹绘制，创建新子弹时已经在 监听时 删除false子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot now = enemyTank.shots.get(j);
                    if (now.isLive) {
                        drawShot(now.x, now.y, g, now.direct, 0);
                    } else {
                        enemyTank.shots.remove(now);
                        j--; //删除后索引复位
                    }
                }
            }
        }

        //遍历hero的子弹集合，若接受到‘j’，则射出子弹绘制图像,若子弹死亡则从vector删除子弹
        if (hero.shots.size() != 0) {
            //取出每一个子弹绘制，创建新子弹时已经在 监听时 删除false子弹
            for (int i = 0; i < hero.shots.size(); i++) {
                Shot now = hero.shots.get(i);
                if (now.isLive) {
                    drawShot(now.x, now.y, g, now.direct, 1);
                } else {
                    hero.shots.remove(now); //删除敌方失效子弹
                    i--; //索引复位
                }
            }
            //通过画板实现线程接口来实现重绘 了
        }

        //若booms中有对象，绘制炸弹
        for (int i = 0; i < booms.size(); i++) {
            Boom boom = booms.get(i);
            try {
                Thread.sleep(15);      //休眠一会让第一个炮弹能显示出来
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //根据周期显示爆炸效果
            if (boom.life > 10) {
                g.drawImage(image1, boom.x, boom.y, 60, 60, this);
            } else if (boom.life > 5) {
                g.drawImage(image2, boom.x, boom.y, 60, 60, this);
            } else if (boom.life > 0) {
                g.drawImage(image3, boom.x, boom.y, 60, 60, this);
            }
            //炸弹生命递减
            boom.lifeDown();
            //若生命为0，销毁炸弹从集合
            if (boom.life == 0) {
                booms.remove(boom);
                i--; //索引复位
            }

        }


    }


    /*
     * 多态判断是否tank被击中
     * */
    public void hitTankAll(Shot s, Tank tank) {
        switch (tank.getDirect()) {

            case 0://上下情况一致
            case 2://判断坦克体积区域
                if (s.x > tank.getX() && s.x < tank.getX() + 40
                        && s.y > tank.getY() && s.y < tank.getY() + 60) {
                    s.isLive = false;
                    if (tank instanceof Hero) {//若为hero类型
                        ((Hero) tank).isLive = false;//该子弹和被击中坦克销毁
                    }
                    if (tank instanceof EnemyTank) {
                        ((EnemyTank) tank).isLive = false;
                        enemyTanks.remove((EnemyTank) tank);//从集合中移除该坦克,击杀数加一
                        Recorder.addAllEnemyTankNum();
                    }

                    //创建boom对象爆炸效果
                    Boom boom = new Boom(tank.getX(), tank.getY());
                    booms.add(boom);
                }
                break;
            case 1://左右 情况一致
            case 3:
                if (s.x > tank.getX() && s.x < tank.getX() + 60
                        && s.y > tank.getY() && s.y < tank.getY() + 40) {
                    s.isLive = false;
                    if (tank instanceof Hero) {//若为hero类型
                        ((Hero) tank).isLive = false;//该子弹和被击中坦克销毁
                    }
                    if (tank instanceof EnemyTank) {
                        ((EnemyTank) tank).isLive = false;
                        enemyTanks.remove((EnemyTank) tank);//从集合中移除该坦克,
                        Recorder.addAllEnemyTankNum();//击杀数加一
                    }

                    //创建boom对象爆炸效果
                    Boom boom = new Boom(tank.getX(), tank.getY());
                    booms.add(boom);
                }
                break;
        }
    }


    /*
     *编写方法，判断我方坦克是否 被击中 为了后面双人游戏的友伤判断，独立封装---》已被多态方法替代
     **/
//    public void hitHero(Shot s ,Hero hero){
//        switch (hero.getDirect()){
//
//            case 0://上下情况一致
//            case 2://判断坦克体积区域
//                if(s.x > hero.getX() && s.x < hero.getX()+ 40
//                        && s.y > hero.getY() && s.y <hero.getY() + 60 ){
//                    s.isLive = false;
//                    hero.isLive = false; //该子弹和被击中坦克销毁
//
//                    //创建boom对象爆炸效果
//                    Boom boom = new Boom(hero.getX(), hero.getY());
//                    booms.add(boom);
//                }
//                break;
//            case 1://左右 情况一致
//            case 3:
//                if(s.x > hero.getX() && s.x < hero.getX() + 60
//                        && s.y > hero.getY() && s.y <hero.getY() + 40 ){
//                    s.isLive = false;
//                    hero.isLive = false; //该子弹和被击中坦克销毁
//                    Boom boom = new Boom(hero.getX(), hero.getY());
//                    booms.add(boom);
//                }
//                break;
//        }
//    }
//
//
//
//    /*
//    * 编写方法，判断我方子弹是否击中敌方坦克--->已被多态方法替代
//    * */
//    public void hitTank(Shot s ,EnemyTank enemyTank){
//        switch (enemyTank.getDirect()){
//
//             case 0://上下情况一致
//             case 2://判断坦克体积区域
//                 if(s.x > enemyTank.getX() && s.x < enemyTank.getX()+ 40
//                 && s.y > enemyTank.getY() && s.y <enemyTank.getY() + 60 ){
//                     s.isLive = false;
//                     enemyTank.isLive = false; //该子弹和被击中坦克销毁
//                     enemyTanks.remove(enemyTank);//从集合中移除该坦克
//                     //创建boom对象爆炸效果
//                     Boom boom = new Boom(enemyTank.getX(), enemyTank.getY());
//                     booms.add(boom);
//                 }
//                 break;
//            case 1://左右 情况一致
//            case 3:
//                if(s.x > enemyTank.getX() && s.x < enemyTank.getX() + 60
//                && s.y > enemyTank.getY() && s.y <enemyTank.getY() + 40 ){
//                    s.isLive = false;
//                    enemyTank.isLive = false; //该子弹和被击中坦克销毁
//                    enemyTanks.remove(enemyTank);
//                    Boom boom = new Boom(enemyTank.getX(), enemyTank.getY());
//                    booms.add(boom);
//                }
//                break;
//        }
//    }


    /*
     * 坦克发射子弹
     * */
    public void drawShot(int x, int y, Graphics g, int direct, int type) {
        if (type == 0) { //敌人的-》青色
            g.setColor(Color.cyan);
        } else if (type == 1) { //hero->黄色
            g.setColor(Color.yellow);
        }
        g.draw3DRect(x, y, 10, 10, false);
    }

    /*
     * x 坦克左上x坐标
     * y 坦克左上y坐标
     * g 画笔
     * direct 坦克方向  （0->上 1->右 2->下 3->左）
     * type 坦克类型
     * */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            case 0:
                g.setColor(Color.cyan);
                break;//敌人的
            case 1:
                g.setColor(Color.yellow);
                break; //我们坦克
        }
        switch (direct) {
            case 0://向上的
                g.fill3DRect(x, y, 10, 60, false); //坦克左边履带
                g.fill3DRect(x + 30, y, 10, 60, false); //坦克右边履带
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//中间矩形
                g.fillOval(x + 10, y + 20, 20, 20); //  盖子
                g.drawLine(x + 20, y + 30, x + 20, y);// 炮筒
                break;
            case 1://向右的
                g.fill3DRect(x, y, 60, 10, false); //坦克上边履带
                g.fill3DRect(x, y + 30, 60, 10, false); //坦克下边履带
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//中间矩形
                g.fillOval(x + 20, y + 10, 20, 20); //  盖子
                g.drawLine(x + 30, y + 20, x + 60, y + 20);// 炮筒
                break;
            case 2://向下的
                g.fill3DRect(x, y, 10, 60, false); //坦克左边履带
                g.fill3DRect(x + 30, y, 10, 60, false); //坦克右边履带
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//中间矩形
                g.fillOval(x + 10, y + 20, 20, 20); //  盖子
                g.drawLine(x + 20, y + 30, x + 20, y + 60);// 炮筒
                break;
            case 3://向左的
                g.fill3DRect(x, y, 60, 10, false); //坦克上边履带
                g.fill3DRect(x, y + 30, 60, 10, false); //坦克下边履带
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//中间矩形
                g.fillOval(x + 20, y + 10, 20, 20); //  盖子
                g.drawLine(x + 30, y + 20, x, y + 20);// 炮筒
                break;
            default:
                System.out.println("暂时不处理");
        }


    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {


        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirect(0); //修改坦克方向
            hero.MoveUp(); //调用Tank类的Move方法,修改x，，y

        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirect(1);
            hero.MoveRight();

        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirect(2);
            hero.MoveDown();

        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirect(3);
            hero.MoveLeft();

        }

        if (e.getKeyCode() == KeyEvent.VK_J) {
            if (hero.shots.size() < 8) { //面板同时存在8个子弹
                hero.shotEnemyTank();
            } //增加新子弹
        }

        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(32);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //判断坦克是否击中
            //!!!!!!!!!!!!!!!判断子弹是否存活，注意是否改为
            if (hero.shots.size() != 0) { //判断子弹是否为空
                for (int i = 0; i < enemyTanks.size(); i++) {

                    EnemyTank enemyTank = enemyTanks.get(i);

                    //遍历判断hero的所有存在的子弹
                    for (int j = 0; j < hero.shots.size(); j++) {
                        hitTankAll(hero.shots.get(j), enemyTank); //用多态方法代替
//                            Tank(hero.shots.get(j), enemyTank);
                        if (!enemyTank.isLive) {
                            i--; //坦克在hit——tank中已被从集合中删除,集合后元素索引-1 ，为同步元素 i-1再+1保持不变，
                            break; //若改坦克已销，结束其它子弹与其判定，避免冲突，下一次循环删除改坦克
                        }
                    }

                }
            }

            //判断我方坦克是否被击中
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);

                for (int j = 0; j < enemyTank.shots.size(); j++) { //循环取出子弹
                    hitTankAll(enemyTank.shots.get(j), hero); //用多态方法代替
//                    hitHero(enemyTank.shots.get(j), hero);  //若被击中则hero.isLive=false, shot也是
                    if (!hero.isLive) {
                        break; //被击中都输了，还判断个p
                    }
                }

                if (!hero.isLive) {
                    break; //同上跳出循环
                }

            }


            //把画板当成一个线程不停重绘
            this.repaint();
//**********************************************************
//            if (!hero.isLive) { //若hero死亡游戏结束
//                try {
//                    Thread.sleep(50); //让hero爆炸画面出现
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                break;
//            }
            //游戏退出控制，是否设置线程通知关系?
//************************************************************

        }
    }


}
