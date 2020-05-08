package battleship;

import java.awt.*;

public class Utils {
    private static GraphicsEnvironment environment;
    private static GraphicsDevice screen;
    private static GraphicsConfiguration configuration;

    static {
        environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        screen = environment.getDefaultScreenDevice();
        configuration = screen.getDefaultConfiguration();
    }

    public static int getScreenWidth() {
        return configuration.getBounds().width;
    }

    public static int getScreenHeight() {
        return configuration.getBounds().height;
    }
}
