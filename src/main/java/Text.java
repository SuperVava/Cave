import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

public class Text {
    PApplet processing;
    PFont font;
    int sise;

    public Text(PApplet processing) {
        this.processing = processing;
    }

    public void write(String message, int X, int Y, int sise) {
        this.sise = sise;
        processing.textAlign(PConstants.CENTER);
        font = processing.createFont("Consolas", sise);
        processing.textFont(font);
        processing.text(message, X, Y);
    }
}
