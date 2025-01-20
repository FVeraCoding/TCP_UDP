package TCP.Ejercicio4_Chat;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    ServerSocket servidor;
    Socket socket;
    InputStream is;
    OutputStream os;

    public Servidor(int puerto) throws IOException {
        servidor = new ServerSocket(6080);
    }

    public void start() throws IOException {
        socket = servidor.accept();
        is = socket.getInputStream();
        os = socket.getOutputStream();
    }

    public void chat() throws IOException {
        Scanner sc = new Scanner(System.in);
        String mensajeRecibido;
        String mensajeEnviado;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        PrintWriter pw = new PrintWriter(os, true);

        while (true) {

            mensajeRecibido = br.readLine();

            if (mensajeRecibido.equalsIgnoreCase(".")) {
                break;
            }
            System.out.println("Cliente: " + mensajeRecibido);

            mensajeEnviado = sc.nextLine();

            pw.println(mensajeEnviado);

            if (mensajeEnviado.equalsIgnoreCase(".")) {
                break;
            }
            System.out.println("Servidor: " + mensajeEnviado);
        }

    }

    public void stop() throws IOException {
        is.close();
        os.close();
        socket.close();
        servidor.close();
    }
    
    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor(6080);
            servidor.start();
            servidor.chat();
            servidor.stop();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
