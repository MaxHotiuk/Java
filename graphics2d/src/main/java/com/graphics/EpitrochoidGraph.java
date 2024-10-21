package com.graphics;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class EpitrochoidGraph extends JFrame {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final double A = 3;
    private static final double SMALL_A = 1;
    private static final double LAMBDA = 2;
    
    private Color lineColor = Color.BLUE;
    private float lineThickness = 2.0f;
    private float[] dashPattern = null;

    private final Random random = new Random();

    public EpitrochoidGraph() {
        setTitle("Графік епітрохоїди");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGraph(g);
            }
        };
        
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeLineStyle();
                repaint();
            }
        });
        
        add(panel);
    }
    
    private void drawGraph(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        double scale = Math.min(getWidth(), getHeight()) / 15.0;
        
        g2d.setColor(Color.BLACK);
        g2d.drawLine(0, centerY, getWidth(), centerY);
        g2d.drawLine(centerX, 0, centerX, getHeight());
        
        g2d.setColor(lineColor);
        g2d.setStroke(new BasicStroke(lineThickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dashPattern, 0));
        
        Path2D path = new Path2D.Double();
        boolean first = true;
        for (double t = 0; t <= 2 * Math.PI; t += 0.01) {
            double x = (A + SMALL_A) * Math.cos(t) - LAMBDA * SMALL_A * Math.cos((A / SMALL_A + 1) * t);
            double y = (A + SMALL_A) * Math.sin(t) - LAMBDA * SMALL_A * Math.sin((A / SMALL_A + 1) * t);
            
            x = centerX + x * scale;
            y = centerY - y * scale;
            
            if (first) {
                path.moveTo(x, y);
                first = false;
            } else {
                path.lineTo(x, y);
            }
        }
        g2d.draw(path);
        
        g2d.setColor(Color.BLACK);
        g2d.drawString("Автор: Готюк Максим", 10, 20);
        g2d.drawString("Варіант: 3", 10, 40);
    }
    
    private void changeLineStyle() {
        lineColor = new Color(random.nextInt(0x1000000));
        
        lineThickness = 1 + (float)(Math.random() * 5);
        
        int styleChoice = random.nextInt(4);
        dashPattern = switch (styleChoice) {
            case 0 -> null;
            case 1 -> new float[]{5};
            case 2 -> new float[]{10, 5, 2, 5};
            case 3 -> new float[]{15, 5};
            default -> dashPattern;
        };
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EpitrochoidGraph frame = new EpitrochoidGraph();
            frame.setVisible(true);
        });
    }
}