package future.code.dark.dungeon.domen;

import future.code.dark.dungeon.config.Configuration;
import future.code.dark.dungeon.service.GameMaster;

import java.util.ArrayList;
import java.util.List;


public class Player extends DynamicObject {
    private static final int stepSize = 1;

    public Player(int xPosition, int yPosition) {
        super(xPosition, yPosition, getSprites());
    }
    private static ArrayList<String> getSprites(){
        ArrayList<String> sprites = new ArrayList<>();
        sprites.add(0,Configuration.PLAYER_SPRITE0);
        sprites.add(1,Configuration.PLAYER_SPRITE1);
        sprites.add(2,Configuration.PLAYER_SPRITE2);
        sprites.add(3,Configuration.PLAYER_SPRITE3);
        return sprites;
    }


    public void move(Direction direction) {
        move(direction, stepSize);
    }

    private boolean thisMoney(int xM,int yM){
        return GameMaster.getInstance().getStaticObjects().stream()
                .anyMatch(s -> s instanceof Coin && s.getXPosition() == xM && s.getYPosition() == yM);
    }
    private boolean thisExit(int xM,int yM){
        return GameMaster.getInstance().getStaticObjects().stream()
                .anyMatch(s -> s instanceof Exit && s.getXPosition() == xM && s.getYPosition() == yM);
    }
    @Override
    protected void move(Direction direction, int distance) {
        System.out.println("move");
        int tmpXPosition = getXPosition();
        int tmpYPosition = getYPosition();
        switch (direction) {
            case UP -> tmpYPosition -= distance;
            case DOWN -> tmpYPosition += distance;
            case LEFT -> tmpXPosition -= distance;
            case RIGHT -> tmpXPosition += distance;
        }

        if (isAllowedSurface(tmpXPosition, tmpYPosition, true)) {
            xPosition = tmpXPosition;
            yPosition = tmpYPosition;
            System.out.println("Complete move");
            if(thisMoney(tmpXPosition,tmpYPosition)){
                GameMaster.getInstance().deleteCoin(tmpXPosition,tmpYPosition);
            }
            if (thisExit(tmpXPosition,tmpYPosition)) {
                GameMaster.getInstance().statusGame[0] = false;
                GameMaster.getInstance().statusGame[1] = true;
            }
        }
    }

    @Override
    public String toString() {
        return "Player{[" + xPosition + ":" + yPosition + "]}";
    }
}
