import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class game extends JPanel implements ActionListener, KeyListener {
    private class Tile{
        int x;
        int y;

        Tile(int x,int y){
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    Tile snakehead;
    ArrayList<Tile> snakebody;

    Tile food;
    Random random;

    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameover = false;

    game(int boardWidth,int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakehead = new Tile(5,5);
        snakebody = new ArrayList<Tile>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        //GRID

       // for(int i=0;i<boardHeight/tileSize;i++){
       //    g.drawLine(i*tileSize,0, i*tileSize, boardHeight);
      //      g.drawLine(0,i*tileSize, boardWidth, i*tileSize);

       // }
        //FOOD

         g.setColor(Color.red);
        // g.fillRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize);
         g.fill3DRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize, true);
        
         //Snake

         g.setColor(Color.green);
         // g.fillRect(snakehead.x*tileSize,snakehead.y* tileSize, tileSize, tileSize);
         g.fill3DRect(snakehead.x*tileSize,snakehead.y* tileSize, tileSize, tileSize, true);

         //snake body

         for (int i=0; i< snakebody.size(); i++) {
            Tile snakepart = snakebody.get(i);
           // g.fillRect(snakepart.x*tileSize, snakepart.y*tileSize, tileSize, tileSize);
           g.fill3DRect(snakepart.x*tileSize, snakepart.y*tileSize, tileSize, tileSize, true);
         }

         //score

         g.setFont(new Font("Arial", Font.PLAIN, 16));
         if (gameover) {
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakebody.size()),tileSize - 16, tileSize);
         }

         else {
            g.drawString("Score: " + String.valueOf(snakebody.size()), tileSize - 16, tileSize);
         }

    
    }
    

    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize);
        food.y = random.nextInt(boardHeight/tileSize);
  
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }



    public void move(){

        if (collision(snakehead, food)) {
            snakebody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //snake body

        for (int i = snakebody.size()-1; i >= 0; i--) {
            Tile snakepart = snakebody.get(i);
            if (i == 0){
                snakepart.x = snakehead.x;
                snakepart.y = snakehead.y;
            }

            else {
                Tile prevsnakepart = snakebody.get(i-1);
                snakepart.x = prevsnakepart.x;
                snakepart.y = prevsnakepart.y;
            }
        }

        //snake head
        snakehead.x += velocityX;
        snakehead.y += velocityY;

        //game over 

        for (int i = 0; i < snakebody.size(); i++) {
            Tile snakepart = snakebody.get(i);

            if (collision(snakehead, snakepart)) {
                gameover = true;

            }

        }

        if(snakehead.x*tileSize < 0 || snakehead.x*tileSize > boardWidth || 
           snakehead.y*tileSize < 0 || snakehead.y*tileSize > boardHeight) {
            gameover = true;

         }

    }

    @Override
    public void actionPerformed(ActionEvent e){
    move();
    repaint();
    if(gameover) {
        gameLoop.stop();

    }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY !=1){
            velocityX = 0;
            velocityY = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        }

        else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != -1) {
            velocityX = -1;
            velocityY = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    
}
            