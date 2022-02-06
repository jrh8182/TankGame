package tankgame04;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable{
    //在敌方坦克类中，用vector保存多个shot

    //使得再类中得到MyPanel中的集合元素
    Vector<EnemyTank> enemyTanks = new Vector<>();

    Vector<Shot> shots = new Vector<>();
    Shot shot = null;
    //敌方坦克是否存活
    boolean isLive = true ;


    public EnemyTank(int x, int y) {
        super(x, y);
    }
    @SuppressWarnings({"all"})

    //将MyPanel中的敌方坦克集合赋值给 成员集合用来遍历
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks){
        this.enemyTanks = enemyTanks;
    }

    //编写方法判断 当前这个敌方坦克是否 与其他坦克重叠或碰撞
    public boolean isTouchEnemyTank(){
        switch (this.getDirect()){
            case 0://up
                for (int i = 0; i < enemyTanks.size(); i++) { //遍历比较
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (this != enemyTank){
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2){
                            //若敌方坦克为 上下 ..()先判断 左上判断右上 自己的坐标范围
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60){
                                this.setDirect(2); //调转相反方向，防止短暂的卡死
                                return true;
                            }
                            if (this.getX()+ 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60){
                                this.setDirect(2);
                                return true;
                            }
                        }
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3){
                            //若敌方坦克为 右左
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40){
                                this.setDirect(2);
                                return true;
                            }
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40){
                                this.setDirect(2);
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1://right
                for (int i = 0; i < enemyTanks.size(); i++) { //遍历比较
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (this != enemyTank){
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2){
                            //若敌方坦克为 上下 ..()先判断 右上判断右下 自己的坐标范围
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60){
                                this.setDirect(3);
                                return true;
                            }
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60){
                                this.setDirect(3);
                                return true;
                            }
                        }
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3){
                            //若敌方坦克为 右左
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40){
                                this.setDirect(3);
                                return true;
                            }
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() + 40>= enemyTank.getY()
                                    && this.getY() + 40<= enemyTank.getY() + 40){
                                this.setDirect(3);
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2://down
                for (int i = 0; i < enemyTanks.size(); i++) { //遍历比较
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (this != enemyTank){
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2){
                            //若敌方坦克为 上下 ..()先判断 左下判断右下 自己的坐标范围
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60){
                                this.setDirect(0);
                                return true;
                            }
                            if (this.getX()+ 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60){
                                this.setDirect(0);
                                return true;
                            }
                        }
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3){
                            //若敌方坦克为 右左
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40){
                                this.setDirect(0);
                                return true;
                            }
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40){
                                this.setDirect(0);
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3://left
                for (int i = 0; i < enemyTanks.size(); i++) { //遍历比较
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (this != enemyTank){
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2){
                            //若敌方坦克为 上下 ..()先判断 左上判断左下 自己的坐标范围
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60){
                                this.setDirect(1);
                                return true;
                            }
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX()  <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60){
                                this.setDirect(1);
                                return true;
                            }
                        }
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3){
                            //若敌方坦克为 右左
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40){
                                this.setDirect(1);
                                return true;
                            }
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40){
                                this.setDirect(1);
                                return true;
                            }
                        }
                    }
                }
                break;
        }

        return false;


    }

    public void shotByEnemy(){

        switch (getDirect()){ //坦克方向
            case 0: //向上
                shot = new Shot(getX()+20, getY(), 0);
                break;
            case 1://向右
                shot = new Shot(getX()+60, getY()+20, 1);
                break;
            case 2://向下
                shot = new Shot(getX()+20, getY()+60,2);
                break;
            case 3:
                shot = new Shot(getX(), getY()+20,3);
                break;
        }

        new Thread(shot).start(); //将子弹加入线程
        shots.add(shot);  //加入集合
    }

    @Override
    public void run() {
        //当坦克存活时时候随意移动
        while (true){ //循环判断条件是否改为while(true);if（isLive） break;
                        // 若修改，循环中发射子弹方法的条件也要修改----->已修改
            //自动移动
            for (int i = 0; i < (int)(16*Math.random()+15); i++) { //走15到30步

                switch (getDirect()){
                    case 0: if (!isTouchEnemyTank()) {
                        MoveUp();
                    }       break;
                    case 1: if (!isTouchEnemyTank()) {
                        MoveRight();
                    }    break;
                    case 2 :if (!isTouchEnemyTank()) {
                       MoveDown();
                    }    break;

                    case 3 :if (!isTouchEnemyTank()) {
                        MoveLeft();
                    }    break;

                }
                try {
                    Thread.sleep(50);    //可控制坦克速度
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //随机方向
            this.setDirect((int)(4 * Math.random()));   //(3-0+1)*math.random+0 ,生成随机的[0,1,2,3]

            //目前面板内只可存在一个子弹
            if (isLive && shots.size() < 2){ //可修改子弹发射效率和版面共存
                shotByEnemy();
            }

            if (!isLive){ //坦克死亡结束循环,退出线程
                break;
            }
        }
    }
}
