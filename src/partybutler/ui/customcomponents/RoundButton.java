package partybutler.ui.customcomponents;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by Administrator on 25.03.2014.
 */
public class RoundButton extends JButton{
        protected Shape shape, base;
        //     public RoundButton() {
//         super();
//     }
//     public RoundButton(Icon icon) {
//         super(icon);
//     }
//     public RoundButton(String text) {
//         super(text);
//     }
//     public RoundButton(Action a) {
//         super(a);
//         //setAction(a);
//     }
//     public RoundButton(String text, Icon icon) {
//         super(text, icon);
//         //setModel(new DefaultButtonModel());
//         //init(text, icon);
//     }
        public RoundButton(Icon icon, String pressed, String rollover) {
            super(icon);
            setPressedIcon(new ImageIcon((pressed)));
            setRolloverIcon(new ImageIcon((rollover)));
        }
        @Override public void updateUI() {
            super.updateUI();
            setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            setBackground(Color.BLACK);
            setContentAreaFilled(false);
            setFocusPainted(false);
            //setVerticalAlignment(SwingConstants.TOP);
            setAlignmentY(Component.TOP_ALIGNMENT);
            initShape();
        }
        @Override public Dimension getPreferredSize() {
            Icon icon = getIcon();
            Insets i = getInsets();
            int iw = Math.max(icon.getIconWidth(), icon.getIconHeight());
            return new Dimension(iw+i.right+i.left, iw+i.top+i.bottom);
        }
        protected void initShape() {
            if(!getBounds().equals(base)) {
                Dimension s = getPreferredSize();
                base = getBounds();
                shape = new Ellipse2D.Float(0, 0, s.width-1, s.height-1);
            }
        }
        @Override protected void paintBorder(Graphics g) {
            initShape();
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            //g2.setStroke(new BasicStroke(1.0f));
            //g2.draw(shape);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        }
        @Override public boolean contains(int x, int y) {
            initShape();
            return shape.contains(x, y);
        }
    }

// class RoundImageButtonUI extends BasicButtonUI{
//     protected Shape shape, base;
//     @Override protected void installDefaults(AbstractButton b) {
//         super.installDefaults(b);
//         clearTextShiftOffset();
//         defaultTextShiftOffset = 0;
//         Icon icon = b.getIcon();
//         if(icon==null) { return; }
//         b.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
//         b.setContentAreaFilled(false);
//         b.setFocusPainted(false);
//         b.setOpaque(false);
//         b.setBackground(Color.BLACK);
//         //b.setVerticalAlignment(SwingConstants.TOP);
//         b.setAlignmentY(Component.TOP_ALIGNMENT);
//         initShape(b);
//     }
//     @Override protected void installListeners(AbstractButton b) {
//         BasicButtonListener listener = new BasicButtonListener(b) {
//             @Override public void mousePressed(MouseEvent e) {
//                 AbstractButton b = (AbstractButton) e.getSource();
//                 initShape(b);
//                 if(shape.contains(e.getX(), e.getY())) {
//                     super.mousePressed(e);
//                 }
//             }
//             @Override public void mouseEntered(MouseEvent e) {
//                 if(shape.contains(e.getX(), e.getY())) {
//                     super.mouseEntered(e);
//                 }
//             }
//             @Override public void mouseMoved(MouseEvent e) {
//                 if(shape.contains(e.getX(), e.getY())) {
//                     super.mouseEntered(e);
//                 }else{
//                     super.mouseExited(e);
//                 }
//             }
//         };
//         if(listener != null) {
//             b.addMouseListener(listener);
//             b.addMouseMotionListener(listener);
//             b.addFocusListener(listener);
//             b.addPropertyChangeListener(listener);
//             b.addChangeListener(listener);
//         }
//     }
//     @Override public void paint(Graphics g, JComponent c) {
//         super.paint(g, c);
//         Graphics2D g2 = (Graphics2D)g;
//         initShape(c);
//         //Border
//         g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//         g2.setColor(c.getBackground());
//         //g2.setStroke(new BasicStroke(1.0f));
//         g2.draw(shape);
//         g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
//     }
//     @Override public Dimension getPreferredSize(JComponent c) {
//         JButton b = (JButton)c;
//         Icon icon = b.getIcon();
//         Insets i = b.getInsets();
//         int iw = Math.max(icon.getIconWidth(), icon.getIconHeight());
//         return new Dimension(iw+i.right+i.left, iw+i.top+i.bottom);
//     }
//     private void initShape(JComponent c) {
//         if(!c.getBounds().equals(base)) {
//             Dimension s = c.getPreferredSize();
//             base = c.getBounds();
//             shape = new Ellipse2D.Float(0, 0, s.width-1, s.height-1);
//         }
//     }
// }

    enum ButtonAlignments {
        Top    ("Top Alignment", Component.TOP_ALIGNMENT),
        Center ("Center Alignment", Component.CENTER_ALIGNMENT),
        Bottom ("Bottom Alignment", Component.BOTTOM_ALIGNMENT);
        private final String description;
        public final float alingment;
        private ButtonAlignments(String description, float alingment) {
            this.description = description;
            this.alingment   = alingment;
        }
        @Override public String toString() {
            return description;
        }
    }
