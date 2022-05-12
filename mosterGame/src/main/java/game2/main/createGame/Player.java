package game2.main.createGame;

import game2.main.createGame.utils.ImageUtils;
import game2.main.createGame.utils.TypeEnum;

import java.awt.*;

public class Player extends Block {
    public Player() {
        super();
        init();
    }

    private void init() {
        setImage(ImageUtils.RUN_R_IMAGE);
        setType(TypeEnum.RUN);
        this.getLocation().x = 50;
    }

    //更换跑步状态
    public Image changeRunStatus() {
        if (getImage() == ImageUtils.RUN_R_IMAGE) {
            return ImageUtils.RUN_R1_IMAGE;
        } else if (getImage() == ImageUtils.RUN_R1_IMAGE) {
            return ImageUtils.RUN_R2_IMAGE;
        } else if (getImage() == ImageUtils.RUN_R2_IMAGE) {
            return ImageUtils.RUN_R1_IMAGE;
        } else {
            return ImageUtils.RUN_R_IMAGE;
        }
    }



}
