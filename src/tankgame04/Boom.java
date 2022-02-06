package tankgame04;

public class Boom {
    int x, y;//炸弹的坐标
    int life = 15 ;//炸弹生命周期
    boolean isLive = true;

    public Boom (int x, int y){
        this.x = x;
        this.y = y;
    }

    //减少炸弹的生命周期
    public void lifeDown(){
        if(life>0){
            life--;
        }else {
            isLive = false;
        }
    }
}
