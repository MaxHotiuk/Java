package com.paint;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

public class SimplePaint extends JFrame {
    private BufferedImage canvas;
    private Color drawColor = Color.BLACK;
    private Color fillColor = Color.RED;
    private int brushSize = 5;
    private String currentTool = "Pencil";
    private JLabel statusBar;
    private final JScrollPane scrollPane;
    private JPanel drawingPanel;
    private final ButtonGroup toolGroup;
    private JComboBox<Integer> brushSizeComboBox;
    private JPanel drawColorPanel;
    private JPanel fillColorPanel;

    public SimplePaint() {
        setTitle("Simple Paint");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        clearCanvas();
        
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(canvas, 0, 0, null);
            }
        };
        drawingPanel.setPreferredSize(new Dimension(800, 600));
        
        scrollPane = new JScrollPane(drawingPanel);
        
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if ("Fill".equals(currentTool)) {
                    floodFill(e.getX(), e.getY(), fillColor);
                    repaint();
                }
            }
        });
        
        drawingPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Graphics2D g = (Graphics2D) canvas.getGraphics();
                g.setColor(drawColor);
                switch (currentTool) {
                    case "Pencil" -> {
                        g.setStroke(new BasicStroke(brushSize)); 
                        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
                        g.drawLine(e.getX(), e.getY(), e.getX(), e.getY());
                    }
                    case "Pen" -> {
                        g.setStroke(new BasicStroke(brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        g.drawLine(e.getX(), e.getY(), e.getX(), e.getY());
                    }
                    case "Eraser" -> {
                        g.setColor(Color.WHITE);
                        g.fillRect(e.getX() - brushSize/2, e.getY() - brushSize/2, brushSize, brushSize);
                    }
                }
                repaint();
            }
        });

        // Створення меню
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem resizeItem = new JMenuItem("Resize Canvas");
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(resizeItem);
        menuBar.add(fileMenu);

        // Створення контекстного меню
        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem clearItem = new JMenuItem("Clear");
        contextMenu.add(clearItem);

        drawingPanel.setComponentPopupMenu(contextMenu);

        // Створення панелі інструментів
        JToolBar toolBar = new JToolBar();
        toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.Y_AXIS));
        
        // Створення радіо-кнопок для вибору інструментів
        toolGroup = new ButtonGroup();
        JRadioButton pencilButton = new JRadioButton("Pencil");
        JRadioButton penButton = new JRadioButton("Pen");
        JRadioButton eraserButton = new JRadioButton("Eraser");
        JRadioButton fillButton = new JRadioButton("Fill");
        
        toolGroup.add(pencilButton);
        toolGroup.add(penButton);
        toolGroup.add(eraserButton);
        toolGroup.add(fillButton);
        
        toolBar.add(pencilButton);
        toolBar.add(penButton);
        toolBar.add(eraserButton);
        toolBar.add(fillButton);
        
        pencilButton.setSelected(true);  // Олівець вибрано за замовчуванням

        // Створення панелей для відображення кольорів
        drawColorPanel = new JPanel();
        drawColorPanel.setPreferredSize(new Dimension(30, 30));
        drawColorPanel.setBackground(drawColor);
        drawColorPanel.setBorder(BorderFactory.createTitledBorder("Draw"));

        fillColorPanel = new JPanel();
        fillColorPanel.setPreferredSize(new Dimension(30, 30));
        fillColorPanel.setBackground(fillColor);
        fillColorPanel.setBorder(BorderFactory.createTitledBorder("Fill"));

        JButton drawColorButton = new JButton("Draw Color");
        JButton fillColorButton = new JButton("Fill Color");

        toolBar.add(Box.createVerticalStrut(10));
        toolBar.add(drawColorPanel);
        toolBar.add(drawColorButton);
        toolBar.add(Box.createVerticalStrut(5));
        toolBar.add(fillColorPanel);
        toolBar.add(fillColorButton);

        // Створення списку для вибору розміру пензля
        Integer[] brushSizes = {1, 2, 3, 5, 8, 10, 15, 20};
        brushSizeComboBox = new JComboBox<>(brushSizes);
        brushSizeComboBox.setSelectedItem(5);  // За замовчуванням вибрано розмір 5
        toolBar.add(Box.createVerticalStrut(10));
        toolBar.add(new JLabel("Brush Size:"));
        toolBar.add(brushSizeComboBox);

        // Створення рядка стану
        statusBar = new JLabel("Ready");
        statusBar.setBorder(BorderFactory.createLoweredBevelBorder());

        // Менеджер розміщення
        setLayout(new BorderLayout());
        add(menuBar, BorderLayout.NORTH);
        add(toolBar, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        // Обробники подій
        newItem.addActionListener(e -> {
            clearCanvas();
            repaint();
            statusBar.setText("New canvas created");
        });

        openItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    canvas = ImageIO.read(file);
                    drawingPanel.setPreferredSize(new Dimension(canvas.getWidth(), canvas.getHeight()));
                    drawingPanel.revalidate();
                    repaint();
                    statusBar.setText("File opened: " + file.getName());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error opening file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        saveItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
        
                // Check if the file name ends with .png; if not, add it
                if (!file.getName().toLowerCase().endsWith(".png")) {
                    file = new File(file.getAbsolutePath() + ".png");
                }
        
                try {
                    ImageIO.write(canvas, "png", file);
                    statusBar.setText("File saved: " + file.getName());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        

        resizeItem.addActionListener(e -> {
            String widthInput = JOptionPane.showInputDialog(this, "Enter new width:", canvas.getWidth());
            String heightInput = JOptionPane.showInputDialog(this, "Enter new height:", canvas.getHeight());
            try {
                int newWidth = Integer.parseInt(widthInput);
                int newHeight = Integer.parseInt(heightInput);
                resizeCanvas(newWidth, newHeight);
                statusBar.setText("Canvas resized to " + newWidth + "x" + newHeight);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearItem.addActionListener(e -> {
            clearCanvas();
            repaint();
            statusBar.setText("Canvas cleared");
        });

        ActionListener toolListener = e -> {
            currentTool = e.getActionCommand();
            statusBar.setText("Tool selected: " + currentTool);
        };

        pencilButton.addActionListener(toolListener);
        penButton.addActionListener(toolListener);
        eraserButton.addActionListener(toolListener);
        fillButton.addActionListener(toolListener);

        drawColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choose Draw Color", drawColor);
            if (newColor != null) {
                drawColor = newColor;
                drawColorPanel.setBackground(drawColor);
                statusBar.setText("Draw color selected: " + drawColor.toString());
            }
        });

        fillColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choose Fill Color", fillColor);
            if (newColor != null) {
                fillColor = newColor;
                fillColorPanel.setBackground(fillColor);
                statusBar.setText("Fill color selected: " + fillColor.toString());
            }
        });

        brushSizeComboBox.addActionListener(e -> {
            brushSize = (Integer) brushSizeComboBox.getSelectedItem();
            statusBar.setText("Brush size set to: " + brushSize);
        });
    }

    private void clearCanvas() {
        Graphics2D g = canvas.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g.dispose();
    }

    private void resizeCanvas(int width, int height) {
        BufferedImage newCanvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newCanvas.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.drawImage(canvas, 0, 0, null);
        g.dispose();
        canvas = newCanvas;
        drawingPanel.setPreferredSize(new Dimension(width, height));
        drawingPanel.revalidate();
        repaint();
    }

    private void floodFill(int x, int y, Color targetColor) {
        int targetRGB = targetColor.getRGB();
        int originalRGB = canvas.getRGB(x, y);
        
        if (targetRGB == originalRGB) {
            return;
        }

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(x, y));
        
        while (!queue.isEmpty()) {
            Point p = queue.remove();
            if (p.x < 0 || p.x >= width || p.y < 0 || p.y >= height) {
                continue;
            }
            
            if (canvas.getRGB(p.x, p.y) == originalRGB) {
                canvas.setRGB(p.x, p.y, targetRGB);
                queue.add(new Point(p.x + 1, p.y));
                queue.add(new Point(p.x - 1, p.y));
                queue.add(new Point(p.x, p.y + 1));
                queue.add(new Point(p.x, p.y - 1));
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SimplePaint().setVisible(true);
        });
    }
}