package study16_3;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        Socket mysocket=null;
        ObjectInputStream inObject=null;
        ObjectOutputStream outObject=null;
        Thread thread ;
        ReadWindow readWindow=null;
        try{ mysocket=new Socket();
            readWindow = new ReadWindow();
            thread = new Thread(readWindow); //负责读取信息的线程
            System.out.print("输入服务器的 IP:");
            String IP = scanner.nextLine();
            System.out.print("输入端口号:");
            int port = scanner.nextInt();
            if(mysocket.isConnected()){}
            else{
                InetAddress address=InetAddress.getByName(IP);
                InetSocketAddress socketAddress=new InetSocketAddress(address,port);
                mysocket.connect(socketAddress);
                InputStream in= mysocket.getInputStream(); //mysocket 调用 getInputStream()返回输入流
                OutputStream out= mysocket.getOutputStream();//mysocket 调用 getOutputStream()返回输出流
                inObject =new ObjectInputStream(in);
                outObject = new ObjectOutputStream(out);
                readWindow.setObjectInputStream(inObject);
                thread.start(); //启动负责读取窗口的线程
            }
        }
        catch(Exception e) {
            System.out.println("服务器已断开"+e);
        }
    }
}
class ReadWindow implements Runnable {
    ObjectInputStream in;
    public void setObjectInputStream(ObjectInputStream in) {
        this.in = in;
    }
    public void run() {
        double result = 0;
        while(true) {
            try{ javax.swing.JFrame window = (javax.swing.JFrame)in.readObject();
                javax.swing.JFrame window1 = (javax.swing.JFrame)in.readObject();


                window.setTitle("这是从服务器上读入的窗口1");
                window.setVisible(true);
                window.requestFocusInWindow();//requestFocus();
                window.setSize(600,800);

                window1.setTitle("这是从服务器上读入的窗口2");
                window1.setVisible(true);
                window1.requestFocusInWindow();//requestFocus();
                window1.setSize(600,800);
            }
            catch(Exception e) {
                System.out.println("与服务器已断开"+e);
                break;
            }
        }
    }
}