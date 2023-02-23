package future.code.dark.dungeon.domen;

import future.code.dark.dungeon.config.Configuration;
import future.code.dark.dungeon.service.GameMaster;
import java.util.List;

public abstract class DynamicObject extends AnimatedObject {

    public DynamicObject(int xPosition, int yPosition, List<String> imagePath) {
        super(xPosition, yPosition, imagePath);
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    protected void move(Direction direction, int distance) {
        int tmpXPosition = getXPosition();
        int tmpYPosition = getYPosition();
        switch (direction) {
            case UP -> tmpYPosition -= distance;
            case DOWN -> tmpYPosition += distance;
            case LEFT -> tmpXPosition -= distance;
            case RIGHT -> tmpXPosition += distance;
        }

        if (isAllowedSurface(tmpXPosition, tmpYPosition, false)) {

            xPosition = tmpXPosition;
            yPosition = tmpYPosition;
        }
    }
    protected boolean exitOpen(int x, int y, boolean c){
        if (GameMaster.getInstance().getStaticObjects().stream().noneMatch(s->s instanceof Exit && s.getXPosition() == x && s.getYPosition() == y)) {
            if (c) System.out.println("true1");
            return true;
        }else if (c && GameMaster.ScoreCoin == 0){
            if (c) System.out.println("true2");
            return true;
        }else {
            if (c) System.out.println("false");
            return false;
        }
    }


    protected Boolean isAllowedSurface(int x, int y, boolean c) {
        if (c) System.out.println("proverka");
        return GameMaster.getInstance().getMap().getMap()[y][x] != Configuration.WALL_CHARACTER && exitOpen(x,y,c);
    }

}
