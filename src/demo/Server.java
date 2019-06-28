package demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

// 服务器端
public class Server extends Thread{

    @Override
    public void run() {
        try {
            server();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void server() throws IOException {
        ServerSocket serverSocket;
        Socket socket;
        socket = null;
        try {
             serverSocket = new ServerSocket(1234);
            System.out.println("192.168.1.105");
            socket = serverSocket.accept();
            if (socket != null){
               // OutputStream outputStream = socket.getOutputStream();
               // PrintWriter pw = new PrintWriter(outputStream);
                // pw.write(123);
                //pw.flush();

                InputStream inputStream = socket.getInputStream();
                System.out.println(inputStream);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
//            socket.close();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
