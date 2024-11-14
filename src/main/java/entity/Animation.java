package entity;
import java.io.IOException;


//Old Implementation Doesn't matter Anymore now that we have a new implementation
public class Animation {
    public static String left1 = "assets/CSC207_asset_char_left.png";
    public static String left2 = "assets/CSC207_asset_char_left_02.png";
    public static String right1 = "assets/character.png";
    public static String right2 = "assets/character_right_02.png";
    public static String jump1 = "assets/char_jump_straight.png";
    public static String jump2 = "assets/character_jump.png";
    ;
    private static int frameDelay = 20; // Adjust this value to control the speed
    private static int frameCounter = 0;
    private static int currentFrameIndex = 0;
    private static String currentDirection = "";

    public static void updateFrame(String key) {
        if ("A".equals(key) || "D".equals(key)) {
            currentDirection = key;
            frameCounter++;
            if (frameCounter >= frameDelay) {
                currentFrameIndex = (currentFrameIndex + 1) % 2; // Toggle between 0 and 1
                frameCounter = 0;
            }
        }
    }

    public static String getCurrentFrame() {
        if ("A".equals(currentDirection)) {
            return currentFrameIndex == 0 ? left1 : left2;
        }
        if ("D".equals(currentDirection)) {
            return currentFrameIndex == 0 ? right1 : right2;
        }

        if ("SPACE".equals(currentDirection)) {
            return currentFrameIndex == 0 ? jump1 : jump2;

        }
        return null;
    }
}

