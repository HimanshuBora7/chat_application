package chatting;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;

public class Server  implements ActionListener {

    JTextField tex1;
    JPanel ta1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;

    Server(){
    // this constructor will pop up the chatting frame as soon as main method of server runs
    f.setLayout(null);

    JPanel p1 = new JPanel();
    p1.setBackground(new Color(0, 0, 0));
    p1.setBounds(0,0,650,170);
    p1.setLayout(null);
    f.add(p1);

    ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/ar1.png"));
    Image i2 = i1.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
    ImageIcon i3  =new ImageIcon(i2);
    JLabel back = new JLabel(i3);
    back.setBounds(5,45,50,50);
    p1.add(back);



    back.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.exit(0);
        }
    });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/amca.png"));
        Image i5 = i4.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT);
        ImageIcon i6  =new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(265,3,100,100);
        p1.add(profile);

        JLabel name = new JLabel("AMCA");
        name.setBounds(282,60,100,110);
        name.setForeground(Color.white);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,22));
        p1.add(name);

         ta1 = new JPanel();
        ta1.setBounds(0,75,650,1000);
        ta1.setBackground(Color.PINK);
        f.add(ta1);

     tex1 = new JTextField();
    tex1.setBounds(5,1080,520,80);
    tex1.setFont(new Font("SAN_SERIF",Font.PLAIN,32));
    f.add(tex1);
    JButton send = new JButton("Send");
    send.setFont(new Font("SAN_SERIF",Font.BOLD,25));
    send.setBackground(new Color(7,94,84));
    send.setForeground(Color.WHITE);
    send.addActionListener(this);
    send.setBounds(535,1080,100,80);
    f.add(send);


    f.setSize(650,1170);
    f.getContentPane().setBackground(Color.white);
    f.setUndecorated(true);
    f.setLocation(200,250);
    f.setVisible(true);

    }
    public void actionPerformed(ActionEvent ae){
        try {
            String sent_text = tex1.getText();


            JPanel p2 = formatLabel(sent_text);
            ta1.add(p2);

//    System.out.println(sent_text);
            ta1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.setBackground(Color.PINK);
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            ta1.add(vertical, BorderLayout.PAGE_START);
            dout.writeUTF(sent_text);
            tex1.setText("");

            //repainting the frame
            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static JPanel formatLabel(String out ){
 JPanel panel = new JPanel();

 panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

 JLabel output = new JLabel("<html><p style=\"width =350px\">" +out +"</p></html>");
 output.setFont(new Font("Tahome",Font.PLAIN,30));
 output.setBackground(Color.yellow);
 output.setOpaque(true);
 output.setBorder( new EmptyBorder(15,15,15,50));
 panel.add(output);


 return panel;
    }
    public static void main (String [] args){
        new Server();

        //creating server
        try{
            ServerSocket skt = new ServerSocket(6001);
            while(true){
               Socket s =  skt.accept(); //accepting the messages and storing it
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while(true){
                    String msg = din.readUTF();
                    JPanel panel =formatLabel(msg);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }
}
