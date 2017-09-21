package lan;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;

class Screen2 extends JFrame implements Runnable {

    public static Socket socket = null;
    static int glocal;
    static String ipaddress;
    JFrame j = new JFrame();
    JLabel l1 = new JLabel();
    DataOutputStream output;
    JButton button = new JButton("Back");
    public Screen2(int g, String ip) {
        try {
            ipaddress = ip;
            glocal = g;
            System.out.print(ipaddress + "sdfdsf" + glocal);
           // j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there
           // j.setExtendedState(JFrame.MAXIMIZED_BOTH);
           // j.setUndecorated(true);
           setUndecorated(true);
           j.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
           Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
           this.setLocation(dim.width/5-this.getSize().width/5, dim.height/5-this.getSize().height/5);
            System.out.print("this is connect");
            getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            //j.setMinimumSize(new Dimension(400, 400));
            this.setSize(1200, 760);
            getContentPane().add(l1);
             JPanel panel = new JPanel();
                button.setSize(20,20);
              // Add button to JPanel
              panel.add(button);
              // And JPanel needs to be added to the JFrame itself!
              this.getContentPane().add(panel);

               setVisible(true);
               button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
            //  Connect();

        } catch (Exception e) {
        }
    }
     private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {                                         

        try {
            // TODO add your handling code here:
            socket.close();
            Portal ppp=new Portal();
            ppp.getIP();
            this.setVisible(false);
            ppp.setVisible(true);
            
        } catch (Exception ex) {
           // Logger.getLogger(Screen2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   

    public static BufferedImage resize(BufferedImage img) {
        Image tmp = img.getScaledInstance(1200, 720, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(1200, 720, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public static void main(String args[]) {
        System.out.println("main is running");

        Screen2 s = new Screen2(glocal, ipaddress);

        System.out.print("Screen");

        // Thread t=new Thread();
        //t.start();
    }

    
    @Override
    public void run() {
        try {
            socket = new Socket(ipaddress, 5000);
            System.out.print("Connection");
            System.out.print(ipaddress + "+" + glocal);
            System.out.println("xcv" + ipaddress);
            output = new DataOutputStream(socket.getOutputStream());
            System.out.println("hey");
            output.writeUTF("5");
            System.out.println("how r u");
            System.out.println("very good");
            ImageIcon i;
            BufferedImage screenShot = null;
            this.setVisible(true);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            try {

                System.out.println("111asdghth");
                //screenShot=ImageIO.read(ImageIO.createImageInputStream(socket.getInputStream()));
                // while(true)
                // {
                /* System.out.println("22222asdghth");
            ImageIO.write((RenderedImage)screenShot, "JPG", new File("5556.jpg"));
            System.out.println("3333sdghth");
            screenShot=resize(screenShot);
            System.out.println("asdghth");
            i=new ImageIcon(screenShot);
            System.out.println("jkhfdskn");
            l1.setIcon(i);
            l1.repaint();
            screenShot.flush();
            System.out.println("jkhfdskn");
            Thread.sleep(50);*/
                //}
                //Thread.sleep(50);

                while (socket.isConnected()) {
                    if ((i = (ImageIcon) ois.readObject()) != null) {
                        System.out.println("jkhfdskn");
                        l1.setIcon(i);
                        l1.repaint();
                        Thread.sleep(600);
                    }
                }
                Portal p = new Portal();
                this.setVisible(false);
                p.setVisible(true);
                socket.close();

            } catch (Exception e) {
                System.out.println("Exception is" + e.getMessage());
                // break;
            }

            // } 
            System.out.println("yuirty");
            System.out.println("erffsfds");

            System.out.println("This is the end");

        } catch (Exception e) {
            System.out.print("this is cause" + e.getMessage());
            // System.exit(0);
        }

    }

}
