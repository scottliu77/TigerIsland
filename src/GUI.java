import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JPanel {

    private final int WIDTH = 1200;
    private final int HEIGHT = 800;

    public GUI() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }
}

