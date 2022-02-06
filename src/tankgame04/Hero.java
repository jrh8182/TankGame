package tankgame04;

import java.util.Vector;

//玩家控制的坦克
public class Hero extends Tank {

    Shot shot = null; //表示设计行为，一个子弹线程
    Vector<Shot> shots = new Vector<>(); //子弹集合
    boolean isLive = true; //判断hero是否存活

    int i = 0;

    public Hero(int x, int y) {
        super(x, y);
    }
    @SuppressWarnings({"ALL"})
    public void shotEnemyTank(){

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
        shots.add(shot); //将子弹加入集合中
        new Thread(shot).start(); //如果从shots里面取得有bug

        }

    }


