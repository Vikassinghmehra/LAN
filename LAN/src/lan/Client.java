package lan;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author vikas
 */
public class Client extends Thread //implements Serializable
{

    //initialize socket and input stream
    public Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    public DataOutputStream out = null;
    static boolean keeprunning = false;
    static Client t1;

    public void msg(String str) {
        try {
            JOptionPane.showMessageDialog(null, str);
            System.out.println("Client");

        } catch (Exception e) {
            System.out.print(e.getCause());
        }

    }

    void Command(String str) {
        try {

            Process child = Runtime.getRuntime().exec(str);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "The Command is not valid !!!!");
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

    void Screen() {

        try {

            Robot robot = new Robot();
            System.out.print("this is working");
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.print("Problem 1");
            BufferedImage screenShot = null;
            System.out.print("Statement 2");
            screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            while (socket.isConnected()) {
                oos.writeObject(new ImageIcon(resize(screenShot)));
                System.out.println("Statement 4");
                ImageIO.write(screenShot, "JPG", new File("66.jpg"));
                screenShot.flush();
                oos.flush();
                Thread.sleep(600);
                screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

            }
            System.out.print("Problem 2");
            //ImageIO.write((RenderedImage) screenShot, "JPG", new File("55.jpg"));
            System.out.print("Problem 3");
            System.out.print("JOB'S DONE");

        } catch (Exception ex) {

            System.out.println("+-" + ex.getMessage() + "" + ex.getCause());
        }

    }

    public void run() {
        try {
            while (true) {

                server = new ServerSocket(5000);
                System.out.println("Server started");
                System.out.println("Waiting for a client ...");
                socket = server.accept();
                System.out.println("Client accepted");
                in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                String temp = in.readUTF();
                char ch = temp.charAt(0);
                String str = temp.substring(1);

                System.out.print("char at" + str + "char at 000" + temp.charAt(0));
                if (ch == '1') {
                    System.out.println("in the if");
                    msg(str);
                    System.out.println("Function end");
                }
                if (ch == '3') {
                    Command(str);
                } else if (ch == '5') {
                    // System.out.print("Screenfetching");
                    System.out.println("starting Screenfetching");
                    //Thread t1=new Thread(new Scrn());
                    //System.out.print("Screenfetching");
                    Screen();
                    //t1.start();

                    // Scrn s=new Scrn();
                    // s.start();
                    System.out.println("End of 5");
                    // t1.join();
                    //System.out.print("Screenfetching");
                }
                server.close();
                socket.close();
                in.close();
            }
        } catch (Exception w) {
            System.out.println("heloasasas" + w.getMessage() + w.getLocalizedMessage());
        }

    }

    public static void main(String args[]) {
        try {
            System.out.println("Main is started");
            t1 = new Client();
            System.out.println("Client script started");
            t1.start();
            System.out.println("");

        } catch (Exception e) {
            System.out.print(e.getCause());
        }
    }

}