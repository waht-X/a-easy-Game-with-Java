package game2.main.createGame;

import game2.main.createGame.utils.ImageUtils;
import game2.main.createGame.utils.TypeEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class CreateGame extends JFrame {
    //缓冲区,双缓冲解决闪烁问题
    BufferedImage bufferedImage;
    Image offsetImg;

    //历史最高分数
    long historyHighestScore;

    //全局计数器
    long optCount;

    /**
     * 障碍物计数器
     */
    int obsCount;

    //玩家
    private Player player;

    //障碍物
    //private Obstacles obstacles;

    //所有的障碍物
    private List<Obstacles> obstaclesList;

    /**
     * 障碍物速度
     */
    int obsSpeech;

    //绘制工具
    private Graphics g;

    //进行画面渲染的线程
    private Thread renderThread;

    //游戏进行标志
    private static boolean GAME_STATUS;

    //异常标志
    private static boolean ERR;

    //游戏开始时间
    private long startTime;

    private static CreateGame game;

    public static CreateGame getGame() {
        if (game == null) {
            game = new CreateGame();
        }
        game.setVisible(true);
        game.reset();
        return game;
    }

    private CreateGame(){

        //初始化窗口参数
        setTitle("game");
        setResizable(false);
        setSize(1200, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                GAME_STATUS = false;
                ERR = true;
            }
        });

        //设置居中
        setLocationRelativeTo(null);

        //初始化自定义内容
//        init();

        //设置重置按钮
//        resetButton();

        //初始化最高fen
        historyHighestScore = 0;

        repaint();
        validate();
    }

    private void init() {
        //加载缓冲区
        bufferedImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        //加载绘制工具
        g = bufferedImage.getGraphics();
        offsetImg = this.createImage(this.getWidth(), this.getHeight());

        //重置计数器
        optCount = 0;
        obsCount = 0;

        //初始化障碍物速度
        obsSpeech = 10;

        //设置游戏状态标志为开始
        GAME_STATUS = true;

        //设置为正常
        ERR = false;

        //加载障碍物集
        obstaclesList = new ArrayList<>();

        //加载静态工具类
        ImageUtils.init();

        //加载玩家
        player = new Player();

        //加载按键监听
        addKeyListener(new KeyListener(player));

        //加载绘制工具
//        g = this.getGraphics();

        //创建一个线程进行画面渲染
        createRenderThread();
        renderThread.start();

        //记录游戏开始时间
        startTime = System.currentTimeMillis();
    }

    //绘制图像
    public void paint() {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        //绘制地面
        g.drawLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
        //绘制人物
        g.drawImage(player.getImage(), player.getLocation().x, player.getLocation().y, player.getSize().w, player.getSize().h, this);

        //创建障碍物
        createObstacles();

        //绘制所有的障碍物
        for (Obstacles o : obstaclesList) {
            g.drawImage(o.getImage(), o.getLocation().x, o.getLocation().y, o.getSize().w, o.getSize().h, this);
            //判断人物与障碍物是否碰撞
            if (collision(player, o)) {
                //碰撞则结束游戏
                GAME_STATUS = false;
            }
        }

        //使用人物运动逻辑
        thePlayer();

        //绘制得分面板
        scorePanel();

        offsetImg.getGraphics().drawImage(bufferedImage, 0, 0, this);
        this.getGraphics().drawImage(offsetImg, 0, 0, this);

    }

    //创建渲染进程
    private void createRenderThread() {
        renderThread = new Thread(() -> {
            while (GAME_STATUS) {
                optCount = (int) ((optCount + 1) % 1e9);
                obsCount = (int) ((obsCount + 1) % 1e9);
                paint();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //结束游戏
            //正常游戏结束则结算分数
            if (!ERR)
                end();
        });
    }

    //人物运动逻辑
    private void thePlayer() {
        //人物下落速度
        int fallSpeech = 10;
        //人物跳起速度
        int jumpSpeech = 10;
        //人物跳起高度
        int jumpHeight = 120;
        //跑步动作更换频率
        int runStatusChange = 10;
        if (player.type == TypeEnum.JUMP) {
            player.getLocation().y += fallSpeech;
            //当人物到达地面时，更改状态为run
            if (player.getLocation().y == (this.getHeight() / 2 - player.getSize().h)) {
                player.type = TypeEnum.RUN;
            }
        }
        //当人物处于起跳（上升）状态
        if (player.type == TypeEnum.JUMPING) {
            player.getLocation().y -= jumpSpeech;
            //当人物跳到最高高度时，改变当前人物状态
            if (player.getLocation().y == (this.getHeight() / 2 - jumpHeight - player.getSize().h)) {
                player.type = TypeEnum.JUMP;
            }
        }
        //角色状态图片更换
        if (player.type == TypeEnum.JUMPING || player.type == TypeEnum.JUMP) {
            if (player.getImage() == ImageUtils.RUN_R_IMAGE ||
            player.getImage() == ImageUtils.RUN_R1_IMAGE ||
            player.getImage() == ImageUtils.RUN_R2_IMAGE) {
                player.setImage(ImageUtils.JUMP_R_IMAGE);
            }
        } else if (player.type == TypeEnum.RUN) {
            if (player.getImage() == ImageUtils.JUMP_R_IMAGE) {
                player.setImage(ImageUtils.RUN_R_IMAGE);
            } else {
                //如果当前处于正在跑步的状态，更换跑步动作
                if (optCount % runStatusChange == 0)
                    player.setImage(player.changeRunStatus());
            }
        }



    }

    //创建障碍物并运行障碍物逻辑
    private void createObstacles() {
        //障碍物创建的间隔
        int obsInterval = (int) (Math.random() * 30 + 30);
        //障碍物速度
//        int obsSpeech = (int) (Math.random() * 20 + 10);
        //障碍物最大数量
        int max = 3;
        //障碍物创建
        if (obsCount >= obsInterval) {
            obstaclesList.add(Obstacles.randBuild());
            obsCount -= obsInterval;
        }
        //障碍物移动
        for (Obstacles o : obstaclesList) {
            o.getLocation().x -= obsSpeech;
        }
        //将超出的障碍物移除
        if (obstaclesList.size() > max && obstaclesList.get(0).getLocation().x <= 0) {
            obstaclesList.remove(0);
        }

        //每经过一段时间，障碍物速度增加
        int incrInterval = 200;
        if (optCount % incrInterval == 0) {
            obsSpeech++;
        }

    }

    /**
     * 碰撞逻辑<br>
     * @param block1 player
     * @param block2 obstacles
     * 碰撞-->true<br>
     * 反之-->false
    */
    private boolean collision(Block block1, Block block2) {
        int x1 = block1.getLocation().x;
        int y1 = block1.getLocation().y;
        int w1 = block1.getSize().w;
        int h1 = block1.getSize().h;

        int x2 = block2.getLocation().x;
        int y2 = block2.getLocation().y;
        int w2 = block2.getSize().w;
        int h2 = block2.getSize().h;

        if (x1 + w1 < x2 || x1 > x2 + w2) {
            return false;
        } else {
            if (y1 + h1 <= y2) {
                return false;
            } else {
                System.out.printf("y1:%d, h1:%d, y2:%d\n", y1, h1, y2);
                return true;
            }
        }
    }

    /**
     * 绘制得分面板
     */
    private void scorePanel() {
        int score = (int) (optCount / 10);
        long endTime = System.currentTimeMillis();
        int time = (int) (endTime - startTime) / 1000;
        g.drawString("score:" + score, 20, 60);
        g.drawString("game time:" + time, 100, 60);
        g.drawString("historyHighestScore: " + historyHighestScore, 200, 60);
    }

    /**
     * 结束游戏，打印出分数
     */
    private void end() {
        int score = (int) (optCount / 10);
        long endTime = System.currentTimeMillis();
        String msg = String.format("game over\n" +
                "your score: %d  game time: %d \n" +
                " 是否重新开始游戏", score, (endTime - startTime) / 1000);
        String title = "game over";
        int option = JOptionPane.OK_OPTION;
        int choice = JOptionPane.showConfirmDialog(this, msg, title, option);

        //记录最高fen
        historyHighestScore = Math.max(historyHighestScore, score);

        if (choice == 0) {
            reset();
        } else if (choice == 1) {
            //退出
            dispose();
        }
    }

    /**
     * 重置游戏
     */
    public void reset() {
        init();
    }

    /**
     * 游戏重置按钮
     */
//    private void resetButton() {
//        JMenuBar jMenuBar = new JMenuBar();
//        setJMenuBar(jMenuBar);
//        JMenu menu = new JMenu("menu");
//        jMenuBar.add(menu);
//        JMenuItem reset = new JMenuItem("reset");
//        menu.add(reset);
//
//        JMenuBar b = this.getJMenuBar();
//        if (b != null) {
//            System.out.println("not null");
//            if (b == jMenuBar) {
//                System.out.println("yes");
//            } else {
//                System.out.println("no");
//            }
//        } else {
//            System.out.println("is null");
//        }
//
//
//        reset.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                reset();
//                System.out.println("click");
//            }
//        });
//    }

}
