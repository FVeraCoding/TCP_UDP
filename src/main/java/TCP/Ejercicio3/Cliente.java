package TCP.Ejercicio3;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Cliente {

    Socket socket;
    InputStream is;
    OutputStream os;
    String host;
    int puerto;

    public Cliente(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    public void start() throws IOException {
        socket = new Socket(host, puerto);
        is = socket.getInputStream();
        os = socket.getOutputStream();
        System.out.println("Conexion establecida");
    }

    public void stop() throws IOException {
        is.close();
        os.close();
        socket.close();
    }

    public void chat() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        PrintWriter pw = new PrintWriter(os, true);
        Scanner sc = new Scanner(System.in);
        System.out.println("Envia un mensaje al servidor: ");

        while (true) {
            
            String mensajeEnviado = sc.nextLine();
            System.out.println("Cliente: " + mensajeEnviado);
            pw.println(mensajeEnviado);

            if (mensajeEnviado.equalsIgnoreCase(".")) {
                break;
            }

            String mensajeRecibido = br.readLine();
            System.out.println("Servidor: " + mensajeRecibido);

            if (mensajeRecibido.equals(".")) {
                break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Cliente cliente = new Cliente("localhost", 6080);
        cliente.start();
        cliente.chat();
        cliente.stop();
    }
}
