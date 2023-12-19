import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class BlocksMain extends JFrame implements MouseListener, KeyListener{
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int BLOCK_SIZE_MIN = 20;
    private static final int BLOCK_SIZE_MAX = 80;
    private static final Color[] BLOCK_COLORS = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE};

    public ArrayList<Block> blocks;
    private JPanel blockPanel;

    private boolean isMoving = false;

    private Timer timer;
    private JButton startButton, stopButton, stepButton, resetButton;

    private int selectedBlockIndex;

    private boolean shiftKeyDown;

    int[] array = {-2, -1, 1, 2};


    // add necessary private instance variables

    private final int SIZE = 500, BLOCK_MIN = 20, BLOCK_MAX = 100; // feel free to change these


    public BlocksMain() {
        initGUI();
        setTitle("Blocks Project");
        setUpMenuBar();
        pack(); // tell the layout manager to organize the components optimally
        setSize(WIDTH,HEIGHT);
        setLocationRelativeTo(null);  // centers the frame
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // set focusable to false for all buttons/containers

        // set focusable to true for the drawing panel and request focus



        // timer goes off every 10 milliseconds after invoking timer.start()
        timer = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 20; i++) {
                    blocks.get(i).move();
                }
            }
        });
        // Set up the window
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initGUI() {
        // instantiate and initialize


        // add 20 blocks to blocks manager

        blocks = new ArrayList<Block>();

        for (int i = 0; i < 20; i++) {

            int x = (int) (Math.random() * (WIDTH - BLOCK_SIZE_MAX));

            int y = (int) (Math.random() * (HEIGHT - BLOCK_SIZE_MAX));

            int width = (int) (Math.random() * (BLOCK_SIZE_MAX - BLOCK_SIZE_MIN) + BLOCK_SIZE_MIN);

            int height = (int) (Math.random() * (BLOCK_SIZE_MAX - BLOCK_SIZE_MIN) + BLOCK_SIZE_MIN);


            int velocityX = array[(int) (Math.random() * (array.length))];

            int velocityY = array[(int) (Math.random() * (array.length))];

            Color color = BLOCK_COLORS[(int) (Math.random() * BLOCK_COLORS.length)];

            System.out.println("Value " + i + ":" + x + ", " + y + ", " + width + ", " + height + ", " + color);

            blocks.add(new Block(x, y, width, height, velocityX, velocityY, color));

        }
        JPanel blockPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // draw the blocks
                for (Block block : blocks) {
                    block.draw(g);
                }
            }
        };
        blockPanel.setFocusable(true);

        blockPanel.setBackground(Color.BLACK);

        getContentPane().add(blockPanel);

        blockPanel.addMouseListener(new MouseListener() {

            @Override

            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) // left mouse button
                {
                    if (selectedBlockIndex != -1) {
                        if (shiftKeyDown) {
                            // Move block to the start of the block list
                            Block block = blocks.get(selectedBlockIndex);
                            blocks.remove(selectedBlockIndex);
                            blocks.add(0, block);
                        } else {
                            // Move block to the end of the block list
                            Block block = blocks.get(selectedBlockIndex);
                            blocks.remove(selectedBlockIndex);
                            blocks.add(block);
                        }
                        repaint();
                    }

                }


                if (e.getButton() == MouseEvent.BUTTON3) // right mouse button

                {

                    if (selectedBlockIndex != -1) {
                        if (shiftKeyDown) {
                            // remove all blocks that occupy that pixel

                            removeBlocks(e.getX(), e.getY());

                        } else {
                            // remove one block from the block list
                            Block block = blocks.get(selectedBlockIndex);
                            blocks.remove(selectedBlockIndex);

                        }
                        repaint();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

        });// To handle mouse clicking events on the blocks.




        // set sizes


        // set colors and borders
        Border border = BorderFactory.createLineBorder(Color.white);


        // add components
        // Create the buttons

        startButton = new JButton("Start");

        stopButton = new JButton("Stop");

        stepButton = new JButton("Step");

        resetButton = new JButton("Reset");


        JPanel buttonPanel = new JPanel();

        buttonPanel.add(startButton);

        buttonPanel.add(stopButton);

        buttonPanel.add(stepButton);

        buttonPanel.add(resetButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);


        // add buttons for controlling the blocks

        startButton.addActionListener(e -> startMoving());
        stopButton.addActionListener(e -> stopMoving());
        stepButton.addActionListener(e -> moveBlocks());
        resetButton.addActionListener(e -> resetBlocks());

        // add event listeners
        addListeners();

    }
    // start moving the blocks

    private void startMoving() {
        isMoving = true;
        new Thread(() -> {
            while (isMoving) {
                moveBlocks();
                try {
                    Thread.sleep(50); // update the blocks every 50 milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void stopMoving() {
        isMoving = false;
    }

    private void moveBlocks() {
        for (Block block : blocks) {
            block.move();
        }
        repaint();
    }

    private void resetBlocks() {
        for (Block block : blocks) {
            block.resetPosition();
        }
        repaint();
    }
    private void removeBlocks(int x, int y)
    {
        for (int i = blocks.size() - 1; i >= 0; i--) {
            if (blocks.get(i).contains(x, y)) {
                blocks.remove(i);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        updateSelectedBlock(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void updateSelectedBlock(int x, int y){
        for(int i = blocks.size() - 1; i >= 0; i--){
            if (blocks.get(i).contains(x, y)){
                selectedBlockIndex = i;
                return;
            }
        }
        selectedBlockIndex = -1;
    }








    private void addListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if (e.getButton() == MouseEvent.BUTTON1){
                    if (selectedBlockIndex != -1){
                        if (shiftKeyDown){
                            Block block = blocks.get(selectedBlockIndex);
                            blocks.remove(selectedBlockIndex);
                            blocks.add(0,block);
                        } else {
                            Block block = blocks.get(selectedBlockIndex);
                            blocks.remove(selectedBlockIndex);
                            blocks.add(block);
                        }
                        repaint();
                    }
                }
              //  System.out.println("hi");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
            }

        });




        // todo -> add action listeners for each button using a lamda expression
        // todo -> add mouse and key listeners for the drawing panel using an anonymous inner class
        //         call the appropriate helper method below


    }






    private void mousePressedEvent(MouseEvent m) {
        int x = m.getX(), y = m.getY();
        //todo -> respond accordingle to left click(#1), middle click(#2) and right click(#3)
        updateSelectedBlock(m.getX(), m.getY());




        repaint();
    }

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {

            shiftKeyDown = true;

        }

        if (e.getKeyCode() == KeyEvent.VK_N) {

            addBlock();

        } else if (e.getKeyCode() == KeyEvent.VK_S) {

            shuffleBlocks();

        }

    }
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {

            shiftKeyDown = false;

        }

    }

    public void keyTyped(KeyEvent e) {

        // Do nothing

    }
    private void addBlock() {

        int x = (int) (Math.random() * getWidth());

        int y = (int) (Math.random() * getHeight());

        int width = (int) (Math.random() * 50 + 20);

        int height = (int) (Math.random() * 50 + 20);

        Color color = BLOCK_COLORS[(int) (Math.random() * BLOCK_COLORS.length)];

        blocks.add(new Block(x, y, width, height, color));

        repaint();

    }

    private void shuffleBlocks() {

        Collections.shuffle(blocks);

        for (Block block : blocks) {

            int x = (int) (Math.random() * getWidth());

            int y = (int) (Math.random() * getHeight());

            block.setPosition(x, y);

        }

        repaint();

    }



    private void randomBlock() {
        // todo-> create a random block that will fit on the screen
        int x = (int) (Math.random() * getWidth());
        int y = (int) (Math.random() * getHeight());
        int width = (int) (Math.random() * 50 + 20);
        int height = (int) (Math.random() * 50 +20);
        Color color = BLOCK_COLORS[(int) (Math.random() * BLOCK_COLORS.length)];
        blocks.add(new Block(x, y, width, height, color));
        repaint();
    }
//
//    private Color randomColor() {
//        // todo
//
//    }

    // creates a basic menu bar
    private void setUpMenuBar() {
        JMenuItem saveAs = new JMenuItem("Save As...", 'A');
        JMenuItem exit = new JMenuItem("Exit", 'x');
        JMenuItem about = new JMenuItem("About...", 'A');

        //saveAs.addActionListener(e -> saveAs());
        saveAs.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        exit.addActionListener(e -> System.exit(0));
        String s = "Blocks Project\nwritten by Bryce Hulett\nfor AP Comp Sci A";
        about.addActionListener(e -> JOptionPane.showMessageDialog(this, s, "About", JOptionPane.INFORMATION_MESSAGE));

        JMenu file = new JMenu("File");
        file.setMnemonic('F');
        file.addSeparator();
        file.add(saveAs);
        file.addSeparator();
        file.add(exit);

        JMenu help = new JMenu("Help");
        help.setMnemonic('H');
        help.add(about);

        JMenuBar bar = new JMenuBar();
        bar.add(file);
        bar.add(help);
        setJMenuBar(bar);
    }

    // creates a png file of the drawing panel(you may need to modify the name)
//    public void saveAs() {
//
//        BufferedImage bi = new BufferedImage(drawingPanel.getWidth(), drawingPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
//        drawingPanel.paintAll(bi.createGraphics());
//        try {
//            ImageIO.write(bi, "png", new File("./output_image.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new BlocksMain());
        new BlocksMain();

    }

    class DrawingPanel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            // todo -> draw all of the blocks
        }
    }


}