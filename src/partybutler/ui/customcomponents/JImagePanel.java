package partybutler.ui.customcomponents;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Administrator on 26.03.2014.
 */
public class JImagePanel extends JPanel {
    private BufferedImage img;

    public JImagePanel(BufferedImage img) {
        // load the background image
            this.img = img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // paint the background image and scale it to fill the entire space
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }
}

