package future.code.dark.dungeon.domen;

import future.code.dark.dungeon.config.Configuration;
import future.code.dark.dungeon.service.GameMaster;

import java.util.ArrayList;

public class Enemy extends DynamicObject{

    public Enemy(int xPosition, int yPosition) {
        super(xPosition, yPosition, getSprites());
    }
    private static ArrayList<String> getSprites(){
        ArrayList<String> sprites = new ArrayList<>();
        sprites.add(0,Configuration.GHOST_SPRITE0);
        sprites.add(1,Configuration.GHOST_SPRITE1);
        sprites.add(2,Configuration.GHOST_SPRITE2);
        sprites.add(3,Configuration.GHOST_SPRITE3);
        return sprites;
    }
    public void move() {
        boolean upB = MupB();
        boolean downB = MdownB();
        boolean rightB = MrightB();
        boolean leftB = MleftB();
        int xPl = GameMaster.getInstance().getPlayer().xPosition;
        int yPl = GameMaster.getInstance().getPlayer().yPosition;
        boolean rnd = getRandomBoolean();

        if (xPl<xPosition){
            if (leftB){left();}
            else if (yPl<yPosition && upB){up();}
            else if (yPl>yPosition && downB){down();}
            else if (rnd && upB){up();}
            else if ( (!rnd) && downB){down();}
            else right();
        }
        else if (xPl>xPosition){
            if (rightB){right();}
            else if (yPl<yPosition && upB){up();}
            else if (yPl>yPosition && downB){down();}
            else if (rnd && upB){up();}
            else if ( (!rnd) && downB){down();}
            else left();
        }
        else if (yPl<yPosition){
            if (upB){up();}
            else if (xPl<xPosition && leftB){left();}
            else if (xPl>xPosition && rightB){right();}
            else if (rnd && rightB){right();}
            else if ( (!rnd) && leftB){left();}
            else down();
        }
        else {
            if (downB){down();}
            else if (xPl<xPosition && leftB){left();}
            else if (xPl>xPosition && rightB){right();}
            else if (rnd && rightB){right();}
            else if ( (!rnd) && leftB){left();}
            else up();
        }

    }
    private void up(){
        if(GameMaster.getInstance().antiStackEnemy(xPosition,yPosition-1)) yPosition-=1;
    }
    private void down(){
        if(GameMaster.getInstance().antiStackEnemy(xPosition,yPosition+1)) yPosition+=1;
    }
    private void right(){
        if(GameMaster.getInstance().antiStackEnemy(xPosition+1,yPosition)) xPosition+=1;
    }
    private void left(){
        if(GameMaster.getInstance().antiStackEnemy(xPosition-1,yPosition)) xPosition-=1;
    }
    private boolean MupB() {
        return isAllowedSurface(xPosition,yPosition-1,false);
    }
    private boolean MdownB(){
        return isAllowedSurface(xPosition,yPosition+1,false);
    }
    private boolean MrightB(){
        return isAllowedSurface(xPosition+1,yPosition,false);
    }
    private boolean MleftB(){
        return isAllowedSurface(xPosition-1,yPosition,false);
    }
    private boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }
}
