package game2.main.createGame;

import game2.main.createGame.utils.ImageUtils;

public class Obstacles extends Block{

    /**
     * 长宽不变的默认构建
     * w:40  h:100
     */
    public Obstacles() {
        super();
        init();
    }

    private void init() {
        setImage(ImageUtils.BLACK_BLOCK_IMAGE);
        //障碍物从右边第一次出现的位置
        this.getLocation().x = 1150;
        //设置障碍物的高度和宽度
    }

    /**
     * 随机构建长宽的障碍物
     * w: 20 - 50
     * h: 50 - 80
     */
    public static Obstacles randBuild() {
        Obstacles o = new Obstacles();
        o.getSize().w = (int) (Math.random() * 30 + 20);
        o.getSize().h = (int) (Math.random() * 30 + 30);
        o.getLocation().y = 300 - o.getSize().h;
        return o;
    }

}
