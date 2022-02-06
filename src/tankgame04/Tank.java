package tankgame04;

public class Tank {
    private int x;
    private int y;  //坦克的横纵坐标
    private int direct; //坦克方向 0 1 2 3 上
                        //       上 右 下 左
    private int speed = 2;  //tank速度,默认为1

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public void MoveUp(){
        if (y == 0){
            return; //判断边界超出条件
        }
        y -= speed;
    }

    public void MoveRight(){
        if (x + 60 == 1000){
            return; //判断边界超出条件
        }
        x += speed;
    }

    public void MoveLeft(){
        if (x == 0){
            return; //判断边界超出条件
        }
        x -= speed;
    }

    public void MoveDown(){
        if (y + 60 == 750){
            return; //判断边界超出条件
        }
        y += speed;

    }



    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
