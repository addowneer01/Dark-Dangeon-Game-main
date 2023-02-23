package future.code.dark.dungeon.domen;

import future.code.dark.dungeon.config.Configuration;

import java.util.List;


public abstract class AnimatedObject extends GameObject{
    public int spr = 0;
    private final List<String> listImage;
    public AnimatedObject(int xPosition, int yPosition, List<String> imagePathA) {
        super(xPosition, yPosition, imagePathA.get(0));
        this.listImage = imagePathA;
    }
    public void nextAnimation(){
        if (spr<3){
            spr++;
        }else spr = 0;
        String x = listImage.get(spr);
        super.setImage(x);
    }
}
