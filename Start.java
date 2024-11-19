import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.*;

public class Start implements ActionListener {

    JFrame frame = new JFrame();
    JButton button = new JButton("Start the Game");
    JLabel label = new JLabel("MineSweeper Game!");

    Start(){

        label.setFont(new Font(null,Font.PLAIN,20));
        label.setBounds(100,50 ,200,100);

        button.setBounds(100,160,200,40);
        button.setFocusable(false);
        button.addActionListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420,420);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setTitle("MineSweeper");
        frame.add(button);
        frame.setResizable(false);
        frame.add(label);

    }
    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource()== button){
            frame.dispose();
            Category category = new Category();       }

    }
    
}
