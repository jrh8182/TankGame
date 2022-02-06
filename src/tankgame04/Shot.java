package tankgame04;

public class Shot implements Runnable{
    int x ;
    int y ;  //子弹坐标
    int direct = 0; //子弹方向
    int speed = 2; //子弹速度
    boolean isLive = true; //判断子弹是否成活
//    Graphics g ;

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {


        while (true){

            //休眠 不然子弹瞬间飞出
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //根据方向来改变x，y坐标
            switch (direct){
                case 0: //up
                    y -= speed; break;
                case 1: //right
                    x += speed; break;
                case 2: //down
                    y += speed; break;
                case 3: //left
                    x -= speed; break;
            }
            //超出边界退出run
            if(!(x >= 0 && x<=1000 && y>=0 && y<= 750 && isLive)){
                isLive = false;
                break;
            }
        }
    }


}
