import javax.swing.*;

public class app
{
    public static void main(String[] args) throws Exception{
        int boardWidth =600;
        int boardHeight = boardWidth;
        int titleSize = 25;

        JFrame frame =new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game snakegame = new game(boardWidth,boardHeight);
        frame.add(snakegame);
        frame.pack();
        snakegame.requestFocus();

    }
}