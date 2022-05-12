package game2.main.createGame;

import game2.main.createGame.utils.Location;
import game2.main.createGame.utils.Size;
import game2.main.createGame.utils.TypeEnum;

import java.awt.*;

public abstract class Block {
    private Location location;
    private Size size;
    private Image image;


    TypeEnum type = TypeEnum.NONE;



    public Block() {
        location = new Location(10, 200);
        size = new Size(40, 100);
    }

    public Block(Location location, Size size, Image image) {
        this.location = location;
        this.size = size;
        this.image = image;
    }

    public Location getLocation() {
        return location;
    }

    public Size getSize() {
        return size;
    }

    public Image getImage() {
        return image;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }
}
