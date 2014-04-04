package partybutler.ui.customcomponents;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JFadingPanel extends JPanel implements ActionListener {
    private static final int COMP_SIZE = 100;
    private static final int RULE = AlphaComposite.SRC_OVER;
    private static final int TIMER_DELAY = 20;
    private Composite[] comps = new Composite[COMP_SIZE];
    private int compsIndex = 0;
    private Composite comp;
    private JPanel parentContainer;

    private String name;



    public JFadingPanel(final JPanel parentContainer, String name) {
        this.name = name;
        this.parentContainer = parentContainer;
        setOpaque(false);
        for (int i = 0; i < comps.length; i++) {
            float alpha = (float) Math.cos(2 * Math.PI * i / (COMP_SIZE * 2));
            alpha += 1;
            alpha /= 2.0;
            comps[i] = AlphaComposite.getInstance(RULE, alpha);
        }
        comp = comps[0];
    }


    public void setParentContainer(JPanel parentContainer) {
        this.parentContainer = parentContainer;
    }

    public void fadeIn() {
        Logger.getRootLogger().debug("Fading in: "+name);
        if(compsIndex == COMP_SIZE){
            Logger.getRootLogger().debug("Can't fade in: compIndex already at "+COMP_SIZE);
            return;
        }
        final Timer timer = new Timer(TIMER_DELAY, null);
        timer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                compsIndex++;
                if (compsIndex % COMP_SIZE == 0) {
                    timer.stop();
                    Logger.getRootLogger().debug("Fade in done: "+name);
                } else  if(compsIndex >= 0 && compsIndex < comps.length){
                    comp = comps[compsIndex];
                    parentContainer.repaint();
                }
            }

        });
        timer.start();
    }

    public void fadeOut(final ActionListener listener) {
        Logger.getRootLogger().debug("Fading out: "+name);
        if(compsIndex == 0){
            Logger.getRootLogger().debug("Can't fade out: compIndex already at 0");
            return;
        }
        final Timer timer = new Timer(TIMER_DELAY, null);
        timer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                compsIndex--;
                if (compsIndex == 0) {
                    timer.stop();

                    Logger.getRootLogger().debug("Fading out completed: "+name);
                    if(listener != null)
                        listener.actionPerformed(new ActionEvent(this,1,"fadeoutFinished"));
                } else if(compsIndex > 0) {
                    comp = comps[compsIndex];
                    parentContainer.repaint();
                }
            }

        });
        timer.start();
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(comp);
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paint(g2);
        g2.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}