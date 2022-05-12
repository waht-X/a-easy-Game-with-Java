package game2.main.createGame.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    public static String RUN_R_PATH = "src/main/resources/game2Image/run_R.png";

    public static String RUN_R1_PATH = "src/main/resources/game2Image/run_R1.png";

    public static String RUN_R2_PATH = "src/main/resources/game2Image/run_R2.png";

    public static String JUMP_R_PATH = "src/main/resources/game2Image/jump_R.png";

    public static String BLACK_BLOCK_PATH = "src/main/resources/game2Image/blackBlock.png";

    public static Image RUN_R_IMAGE;
    public static Image RUN_R1_IMAGE;
    public static Image RUN_R2_IMAGE;
    public static Image JUMP_R_IMAGE;
    public static Image BLACK_BLOCK_IMAGE;


    public static void init() {
        try {
            //加载资源文件路径
            String rootPath = System.getProperty("user.dir") + File.separator + "game2Image";
            RUN_R_PATH = rootPath + File.separator + "run_R.png";
            RUN_R1_PATH = rootPath + File.separator + "run_R1.png";
            RUN_R2_PATH = rootPath + File.separator + "run_R2.png";
            JUMP_R_PATH = rootPath + File.separator + "jump_R.png";
            BLACK_BLOCK_PATH = rootPath + File.separator + "blackBlock.png";

            RUN_R_IMAGE = ImageIO.read(new File(RUN_R_PATH));
            RUN_R1_IMAGE = ImageIO.read(new File(RUN_R1_PATH));
            RUN_R2_IMAGE = ImageIO.read(new File(RUN_R2_PATH));
            JUMP_R_IMAGE = ImageIO.read(new File(JUMP_R_PATH));
            BLACK_BLOCK_IMAGE = ImageIO.read(new File(BLACK_BLOCK_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
