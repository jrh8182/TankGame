package tankgame04;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class Recorder {

    //击毁敌方坦克数 存储不断更新的坦克集合用来存档
    private static int allEnemyTankNum = 0;
    private static Vector<EnemyTank> enemyTanks = null;


    //定义IO对象
    private static BufferedWriter bw = null;
    //文件存储路径和文件名
    private static String recordFile = "src\\myRecord.txt";


    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    //退出游戏时，击杀数存盘退出
    public static void savaRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum + "\r\n"); //加个换行

            //遍历敌人坦克集合，保存
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                if(enemyTank.isLive){ //判断保险，防止莫名其妙的bug
                    String record = enemyTank.getX() + " " + enemyTank.getY()+ " " +enemyTank.getDirect() ;
                    //写入文件
                    bw.write(record + "\r\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null){
                bw.close();}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    //当我方击毁敌方坦克，将allEnemyTankNum++
    public static void addAllEnemyTankNum(){
        Recorder.allEnemyTankNum++;
    }

}
