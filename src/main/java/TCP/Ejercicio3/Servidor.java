package TCP.Ejercicio3;

import java.io.*;
import java.net.*;
import java.util.Scanner;

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
        System.out.println("Conexion establecida");

        is = socket.getInputStream();
        os = socket.getOutputStream();
    }

    public void stop() throws IOException {
        is.close();
        os.close();
        socket.close();
        System.out.println("Conexion finalizada");

    }

    public void chat() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        PrintWriter pw = new PrintWriter(os, true);
        Scanner sc = new Scanner(System.in);

        while (true) {
            String mensajeRecibido = br.readLine();
            System.out.println("Cliente: " + mensajeRecibido);

            if (mensajeRecibido.equals(".")) {
                break;
            }

            String mensajeEnviado = sc.nextLine();
            System.out.println("Servidor: " + mensajeEnviado);
            pw.println(mensajeEnviado);

            if (mensajeEnviado.equalsIgnoreCase(".")) {
                break;
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        Servidor servidor = new Servidor(6080);
        servidor.start();
        servidor.chat();
        servidor.stop();
    }

}
