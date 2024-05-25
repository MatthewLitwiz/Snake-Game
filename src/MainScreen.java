import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainScreen extends JFrame implements ActionListener {

    MainScreen(){

        super("Main Menu");
        setSize(450, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addGuiComponents();
        setVisible(true);
    }

    private void addGuiComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel title = new JLabel("Welcome to Snake Game!");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Dialog", Font.BOLD, 24));

        JButton play = new JButton("Play");
        play.setFont(new Font("Dialog", Font.BOLD, 18));
        play.setFocusable(false);
        play.addActionListener(this);

        panel.add(title);
        panel.add(play);

        getContentPane().add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equalsIgnoreCase("Play")){
            new SnakeGame().setVisible(true);
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new MainScreen();
    }
    
}
