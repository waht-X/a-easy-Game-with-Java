package game2.main.createGame;

import game2.main.createGame.utils.TypeEnum;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {

    private boolean enterW = false;

    private boolean enterD = false;

    private boolean enterA = false;

    private boolean enterS = false;

    private Block block;

    public KeyListener(Block block) {
        this.block = block;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_SPACE) {
            enterW = true;
        }
        //当按下w时，起跳，前提是当前状态是run
        if (enterW && block.type == TypeEnum.RUN) {
            block.type = TypeEnum.JUMPING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_SPACE) {
            enterW = false;
        }
    }
}
