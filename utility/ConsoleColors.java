package utility;

public class ConsoleColors {

    static PrintMethods PrintMethods = new PrintMethods();

    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    public class REG { // Normal colours
        public static final String BLACK = "\033[0;30m";   // BLACK
        public static final String RED = "\033[0;31m";     // RED
        public static final String GREEN = "\033[0;32m";   // GREEN
        public static final String YELLOW = "\033[0;33m";  // YELLOW
        public static final String BLUE = "\033[0;34m";    // BLUE
        public static final String PURPLE = "\033[0;35m";  // PURPLE
        public static final String CYAN = "\033[0;36m";    // CYAN
        public static final String WHITE = "\033[0;37m";   // WHITE
    }

    public class BOLD {
        // Bold
        public static final String BLACK = "\033[1;30m";  // BLACK
        public static final String RED = "\033[1;31m";    // RED
        public static final String GREEN = "\033[1;32m";  // GREEN
        public static final String YELLOW = "\033[1;33m"; // YELLOW
        public static final String BLUE = "\033[1;34m";   // BLUE
        public static final String PURPLE = "\033[1;35m"; // PURPLE
        public static final String CYAN = "\033[1;36m";   // CYAN
        public static final String WHITE = "\033[1;37m";  // WHITE
    }

    public class UNDERLINED {
        // Underline
        public static final String BLACK = "\033[4;30m";  // BLACK
        public static final String RED = "\033[4;31m";    // RED
        public static final String GREEN = "\033[4;32m";  // GREEN
        public static final String YELLOW = "\033[4;33m"; // YELLOW
        public static final String BLUE = "\033[4;34m";   // BLUE
        public static final String PURPLE = "\033[4;35m"; // PURPLE
        public static final String CYAN = "\033[4;36m";   // CYAN
        public static final String WHITE = "\033[4;37m";  // WHITE
    }

    public class BG {
        // Background
        public static final String BLACK = "\033[40m";  // BLACK
        public static final String RED = "\033[41m";    // RED
        public static final String GREEN = "\033[42m";  // GREEN
        public static final String YELLOW = "\033[43m"; // YELLOW
        public static final String BLUE = "\033[44m";   // BLUE
        public static final String PURPLE = "\033[45m"; // PURPLE
        public static final String CYAN = "\033[46m";   // CYAN
        public static final String WHITE = "\033[47m";  // WHITE
    }

    public class ULTRA {
        // Ultra-saturated foreground colors (True Color/RGB)
        public static final String RED = "\033[38;2;255;0;0m";
        public static final String GREEN = "\033[38;2;0;255;0m";
        public static final String BLUE = "\033[38;2;0;0;255m";
        public static final String YELLOW = "\033[38;2;255;255;0m";
        public static final String MAGENTA = "\033[38;2;255;0;255m";
        public static final String ULTRA_CYAN = "\033[38;2;0;255;255m";
        public static final String ORANGE = "\033[38;2;255;165;0m";
        public static final String PINK = "\033[38;2;255;20;147m";
        public static final String PURPLE = "\033[38;2;128;0;128m";
        public static final String LIME = "\033[38;2;50;205;50m";
        public static final String TEAL = "\033[38;2;0;128;128m";
        public static final String GOLD = "\033[38;2;255;215;0m";
        public static final String SILVER = "\033[38;2;192;192;192m";
        public static final String CRIMSON = "\033[38;2;220;20;60m";
        public static final String CORAL = "\033[38;2;255;127;80m";
        public static final String VIOLET = "\033[38;2;238;130;238m";
        public static final String INDIGO = "\033[38;2;75;0;130m";
        public static final String MAROON = "\033[38;2;128;0;0m";
        public static final String OLIVE = "\033[38;2;128;128;0m";
        public static final String NAVY = "\033[38;2;0;0;128m";
        public static final String BLACK = "\033[38;2;0;0;0m";
        public static final String WHITE = "\033[38;2;255;255;255m";
        public static final String GRAY = "\033[38;2;128;128;128m";
        public static final String BROWN = "\033[38;2;165;42;42m";
        public static final String CYAN = "\033[38;2;0;255;255m";
    }

    public class ULTRA_BG {
        // Ultra-saturated background colors (True Color/RGB)
        public static final String RED = "\033[48;2;255;0;0m";
        public static final String GREEN = "\033[48;2;0;255;0m";
        public static final String BLUE = "\033[48;2;0;0;255m";
        public static final String YELLOW = "\033[48;2;255;255;0m";
        public static final String MAGENTA = "\033[48;2;255;0;255m";
        public static final String CYAN = "\033[48;2;0;255;255m";
        public static final String ORANGE = "\033[48;2;255;165;0m";
        public static final String PINK = "\033[48;2;255;20;147m";
        public static final String PURPLE = "\033[48;2;128;0;128m";
        public static final String LIME = "\033[48;2;50;205;50m";
        public static final String TEAL = "\033[48;2;0;128;128m";
        public static final String GOLD = "\033[48;2;255;215;0m";
        public static final String SILVER = "\033[48;2;192;192;192m";
        public static final String CRIMSON = "\033[48;2;220;20;60m";
        public static final String CORAL = "\033[48;2;255;127;80m";
        public static final String VIOLET = "\033[48;2;238;130;238m";
        public static final String INDIGO = "\033[48;2;75;0;130m";
        public static final String MAROON = "\033[48;2;128;0;0m";
        public static final String OLIVE = "\033[48;2;128;128;0m";
        public static final String NAVY = "\033[48;2;0;0;128m";
        public static final String BLACK = "\033[48;2;0;0;0m";
        public static final String WHITE = "\033[48;2;255;255;255m";
        public static final String GRAY = "\033[48;2;128;128;128m";
        public static final String BROWN = "\033[48;2;165;42;42m";
    }

    public class Fx {
        // ========== TEXT EFFECTS ==========
        public static final String BOLD = "\033[1m";
        public static final String FAINT = "\033[2m";
        public static final String ITALIC = "\033[3m";
        public static final String UNDERLINE = "\033[4m";
        public static final String SLOW_BLINK = "\033[5m";
        public static final String RAPID_BLINK = "\033[6m";
        public static final String REVERSE = "\033[7m";
        public static final String CONCEAL = "\033[8m";
        public static final String CROSSED_OUT = "\033[9m";
        public static final String DOUBLE_UNDERLINE = "\033[21m";
        public static final String FRAMED = "\033[51m";
        public static final String ENCIRCLED = "\033[52m";
        public static final String OVERLINED = "\033[53m";
    }
    
    public class ULTRA_BOLD {
        // Bold + Ultra colors
        public static final String RED = Fx.BOLD + ULTRA.RED;
        public static final String GREEN  = Fx.BOLD + ULTRA.GREEN;
        public static final String BLUE  = Fx.BOLD + ULTRA.BLUE;
        public static final String YELLOW  = Fx.BOLD + ULTRA.YELLOW;
        public static final String MAGENTA  = Fx.BOLD + ULTRA.MAGENTA;
        public static final String CYAN  = Fx.BOLD + ULTRA.CYAN;
        public static final String ORANGE  = Fx.BOLD + ULTRA.ORANGE;
        public static final String WHITE = Fx.BOLD + ULTRA.WHITE;
        public static final String BLACK = Fx.BOLD + ULTRA.BLACK;
        public static final String GRAY = Fx.BOLD + ULTRA.GRAY;
        public static final String PURPLE = Fx.BOLD + ULTRA.PURPLE;
        public static final String PINK = Fx.BOLD + ULTRA.PINK;
    }

    public class ULTRA_FG {
        // Background + Ultra foreground combinations
        public static final String ULTRA_RED_ON_BLACK = ULTRA.RED + BG.BLACK;
        public static final String ULTRA_GREEN_ON_BLACK = ULTRA.GREEN + BG.BLACK;
        public static final String ULTRA_BLUE_ON_BLACK = ULTRA.BLUE + BG.BLACK;
        public static final String ULTRA_YELLOW_ON_BLACK = ULTRA.YELLOW + BG.BLACK;
        public static final String ULTRA_MAGENTA_ON_BLACK = ULTRA.MAGENTA + BG.BLACK;
        public static final String ULTRA_CYAN_ON_BLACK = ULTRA.CYAN + BG.BLACK;
        public static final String ULTRA_ORANGE_ON_BLACK = ULTRA.ORANGE + BG.BLACK;
        public static final String ULTRA_WHITE_ON_BLACK = ULTRA.WHITE + BG.BLACK;
        public static final String ULTRA_BLACK_ON_WHITE = ULTRA.BLACK + BG.WHITE;
        public static final String ULTRA_BLACK_ON_RED = ULTRA.BLACK + BG.RED;
        public static final String ULTRA_WHITE_ON_RED = ULTRA.WHITE + BG.RED;
        public static final String ULTRA_BLACK_ON_GREEN = ULTRA.BLACK + BG.GREEN;
        public static final String ULTRA_BLACK_ON_YELLOW = ULTRA.BLACK + BG.YELLOW;
        public static final String ULTRA_BLACK_ON_BLUE = ULTRA.BLACK + BG.BLUE;
        public static final String ULTRA_BLACK_ON_CYAN = ULTRA.BLACK + BG.CYAN;
        public static final String ULTRA_BLACK_ON_MAGENTA = ULTRA.BLACK + BG.PURPLE;
        public static final String ULTRA_BLACK_ON_ORANGE = ULTRA.BLACK + ULTRA_BG.ORANGE;
    }

    // ========== THEMES ==========
    public static final String ERROR = ULTRA_BOLD.RED;
    public static final String SUCCESS = ULTRA_BOLD.GREEN;
    public static final String WARNING = ULTRA_BOLD.YELLOW;
    public static final String INFO = ULTRA_BOLD.BLUE;
    public static final String HIGHLIGHT = ULTRA_BOLD.CYAN;
    public static final String IMPORTANT = ULTRA_BOLD.MAGENTA;

    // ========== UTILITY METHODS ==========
    
    /**
     * Colorize text with reset
     * @param text Text to colorize
     * @param colorCode Color code to apply
     * @return Colorized text
     */
    public static String colorize(String text, String colorCode) {
        return colorCode + text + RESET;
    }

    /**
     * Print colorized text
     * @param text Text to print
     * @param colorCode Color code to apply
     */
    public static void printColor(String text, String colorCode) {
        PrintMethods.p(colorize(text, colorCode));
    }

    /**
     * Print colorized text with newline
     * @param text Text to print
     * @param colorCode Color code to apply
     */
    public static void printlnColor(String text, String colorCode) {
        PrintMethods.pln(colorize(text, colorCode));
    }

    // ========== CUSTOM COLOR METHODS ==========
    
    /**
     * Create custom RGB foreground color
     * @param r Red (0-255)
     * @param g Green (0-255)
     * @param b Blue (0-255)
     * @return ANSI escape code for custom RGB color
     */
    public static String rgb(int r, int g, int b) {
        return String.format("\033[38;2;%d;%d;%dm", r, g, b);
    }

    /**
     * Create custom RGB background color
     * @param r Red (0-255)
     * @param g Green (0-255)
     * @param b Blue (0-255)
     * @return ANSI escape code for custom RGB background
     */
    public static String rgbBg(int r, int g, int b) {
        return String.format("\033[48;2;%d;%d;%dm", r, g, b);
    }

    /**
     * Create custom HSL color (converted to RGB)
     * @param h Hue (0-360)
     * @param s Saturation (0-100)
     * @param l Lightness (0-100)
     * @return ANSI escape code for HSL color
     */
    public static String hsl(int h, int s, int l) {
        // Convert HSL to RGB
        float hue = h / 360f;
        float saturation = s / 100f;
        float lightness = l / 100f;
        
        float q = lightness < 0.5 ? lightness * (1 + saturation) : lightness + saturation - lightness * saturation;
        float p = 2 * lightness - q;
        
        float r = hueToRgb(p, q, hue + 1/3f);
        float g = hueToRgb(p, q, hue);
        float b = hueToRgb(p, q, hue - 1/3f);
        
        return rgb((int)(r * 255), (int)(g * 255), (int)(b * 255));
    }

    private static float hueToRgb(float p, float q, float t) {
        if (t < 0) t += 1;
        if (t > 1) t -= 1;
        if (t < 1/6f) return p + (q - p) * 6 * t;
        if (t < 1/2f) return q;
        if (t < 2/3f) return p + (q - p) * (2/3f - t) * 6;
        return p;
    }

    /**
     * Create gradient text effect
     * @param text Text to gradientize
     * @param startColor Start RGB color
     * @param endColor End RGB color
     * @return Gradient text string
     */
    public static String gradient(String text, int[] startColor, int[] endColor) {
        StringBuilder result = new StringBuilder();
        int length = text.length();
        
        for (int i = 0; i < length; i++) {
            float ratio = (float) i / (length - 1);
            int r = (int) (startColor[0] + ratio * (endColor[0] - startColor[0]));
            int g = (int) (startColor[1] + ratio * (endColor[1] - startColor[1]));
            int b = (int) (startColor[2] + ratio * (endColor[2] - startColor[2]));
            
            result.append(rgb(r, g, b)).append(text.charAt(i));
        }
        result.append(RESET);
        return result.toString();
    }
}