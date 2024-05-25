import javax.swing.JFrame;

public class SnakeGame extends JFrame{

        SnakeGame(){

            super("Snake Game");
            add(new Board());
            pack();
            setSize(450, 450);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setVisible(false);
        }

}
