package future.code.dark.dungeon.config;

public interface Configuration {

    String GAME_NAME="Dark Dungeon";
    String MAP_FILE_PATH = "src/main/resources/maps/map.ber";
    Boolean ENEMIES_ACTIVE = true;
    int GAME_FRAMES_PER_SECOND = 25;
    char WALL_CHARACTER = '1';
    char EXIT_CHARACTER = 'E';
    char LAND_CHARACTER = '0';
    char PLAYER_CHARACTER = 'P';
    char ENEMY_CHARACTER = 'G';
    char COIN_CHARACTER = 'C';
    Integer SPRITE_SIZE = 64;
    String PLAYER_SPRITE0 = "src/main/resources/assets/hero/tile000.png";
    String PLAYER_SPRITE1 = "src/main/resources/assets/hero/tile001.png";
    String PLAYER_SPRITE2 = "src/main/resources/assets/hero/tile002.png";
    String PLAYER_SPRITE3 = "src/main/resources/assets/hero/tile003.png";
    String GHOST_SPRITE0 = "src/main/resources/assets/ghost/tile000.png";
    String GHOST_SPRITE1 = "src/main/resources/assets/ghost/tile001.png";
    String GHOST_SPRITE2 = "src/main/resources/assets/ghost/tile002.png";
    String GHOST_SPRITE3 = "src/main/resources/assets/ghost/tile003.png";
    String WALL_SPRITE = "src/main/resources/assets/land/wall.png";
    String LAND_SPRITE = "src/main/resources/assets/land/ground.png";
    String EXIT_SPRITE = "src/main/resources/assets/land/out.png";
    String COIN_SPRITE = "src/main/resources/assets/land/collectible.png";
    String VICTORY_SPRITE = "src/main/resources/assets/victory.jpg";
    String LOSS_SPRITE = "src/main/resources/assets/game_over_screen.jpeg";

}
