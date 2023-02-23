package future.code.dark.dungeon.service;

import future.code.dark.dungeon.config.Configuration;
import future.code.dark.dungeon.domen.*;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static future.code.dark.dungeon.config.Configuration.*;

public class GameMaster {

    private static GameMaster instance;
    public static int ScoreCoin;
    private static final Image victoryImage = new ImageIcon(VICTORY_SPRITE).getImage();
    private static final Image lossImage = new ImageIcon(LOSS_SPRITE).getImage();
    private final Map map;
    private final List<GameObject> gameObjects;
    public boolean[] statusGame = {true, false};
    private static int fps= 1;

    public static synchronized GameMaster getInstance() {
        if (instance == null) {
            instance = new GameMaster();
        }
        return instance;
    }

    private GameMaster() {
        try {
            this.map = new Map(Configuration.MAP_FILE_PATH);
            this.gameObjects = initGameObjects(map.getMap());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private List<GameObject> initGameObjects(char[][] map) {
        List<GameObject> gameObjects = new ArrayList<>();
        Consumer<GameObject> addGameObject = gameObjects::add;
        Consumer<Enemy> addEnemy = enemy -> {if (ENEMIES_ACTIVE) gameObjects.add(enemy);};

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                switch (map[i][j]) {
                    case EXIT_CHARACTER -> addGameObject.accept(new Exit(j, i));
                    case COIN_CHARACTER -> addGameObject.accept(new Coin(j, i));
                    case ENEMY_CHARACTER -> addEnemy.accept(new Enemy(j, i));
                    case PLAYER_CHARACTER -> addGameObject.accept(new Player(j, i));
                }
            }
        }

        return gameObjects;
    }

    public void renderFrame(Graphics graphics) {
        checkGameOver();
        if (statusGame[0]) {

            if (fps<40){
                fps++;
            }else fps = 1;
            if (fps%10 == 0){
                getPlayer().nextAnimation();
                getEnemies().forEach(AnimatedObject::nextAnimation);
            }
            if (fps%20 ==0){
                getEnemies().forEach(Enemy::move);
            }
            System.out.println(getPlayer().spr+" "+fps);
            getMap().render(graphics);
            getStaticObjects().forEach(gameObject -> gameObject.render(graphics));
            getEnemies().forEach(gameObject -> gameObject.render(graphics));
            getPlayer().render(graphics);
            graphics.setColor(Color.WHITE);
            graphics.drawString(getPlayer().toString(), 10, 20);
            graphics.drawString("Score: " + (9 - getScoreCoin()) + "/9", 10, 40);
        }else if (statusGame[1]){
            graphics.drawImage(victoryImage,0,0,null);
        }else graphics.drawImage(lossImage,0,0,null);
    }

    public boolean antiStackEnemy(int x, int y){
        return getEnemies().stream().noneMatch(s->s.getXPosition()==x && s.getYPosition() == y);
    }
    private void checkGameOver(){
        int x = getPlayer().getXPosition();
        int y = getPlayer().getYPosition();
        if(getEnemies().stream().anyMatch(s->s.getXPosition() == x && s.getYPosition() == y)){
            statusGame[0] = false;
            statusGame[1] = false;
        }
    }

    public Player getPlayer() {
        return (Player) gameObjects.stream()
                .filter(gameObject -> gameObject instanceof Player)
                .findFirst()
                .orElseThrow();
    }

    public List<GameObject> getStaticObjects() {
        return gameObjects.stream()
                .filter(gameObject -> !(gameObject instanceof DynamicObject))
                .collect(Collectors.toList());
    }
    private int getScoreCoin(){
        int x = (int) getStaticObjects().stream().filter(s -> s instanceof Coin).count();
        ScoreCoin = x;
        return x;
    }
    public void deleteCoin(int x, int y){
        GameMaster.getInstance().gameObjects.removeIf(s -> s instanceof Coin && s.getYPosition() == y && s.getXPosition() == x);
    }

    private List<Enemy> getEnemies() {
        return gameObjects.stream()
                .filter(gameObject -> gameObject instanceof Enemy)
                .map(gameObject -> (Enemy) gameObject)
                .collect(Collectors.toList());
    }

    public Map getMap() {
        return map;
    }

}
