import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Category implements ActionListener{

    JFrame frame = new JFrame();
    JLabel label = new JLabel();
    JButton button1 = new JButton("Easy");
    JButton button2 = new JButton("Medium");
    JButton button3 = new JButton("Hard");

    Category(){

        button1.setFocusable(false);
        button1.addActionListener(this);
        button1.setBounds(100,100,200,40);

        button2.setFocusable(false);
        button2.addActionListener(this);
        button2.setBounds(100,150,200,40);

        button3.setFocusable(false);
        button3.addActionListener(this);
        button3.setBounds(100,200,200,40);

        label.setText("Choose your Category");
        label.setBounds(100,0,200,60);
        label.setFont(new Font(null,Font.PLAIN,20));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420,420);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Start");
        frame.setResizable(false);
        frame.add(label);
        frame.add(button1);
        frame.add(button2);
        frame.add(button3);


    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource()== button1){
            frame.dispose();
            Easy easy = new Easy();
        }else if(e.getSource() == button2){
            frame.dispose();
            Medium medium = new Medium();
        }else{
            frame.dispose();
            Hard hard = new Hard();
        }

    }

    
}
