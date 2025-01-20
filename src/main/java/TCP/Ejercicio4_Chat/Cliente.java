package TCP.Ejercicio4_Chat;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    }

    public void chat() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        PrintWriter pw = new PrintWriter(os, true);
        Scanner sc = new Scanner(System.in);
        String mensajeEnviado;
        String mensajeRecibido;
        System.out.println("Envia un mensaje al servidor: ");

        while (true) {
            mensajeEnviado = sc.nextLine();
            pw.println(mensajeEnviado);
            
            if(mensajeEnviado.equalsIgnoreCase(".")){
                break;
            }
            
            System.out.println("Cliente: "+mensajeEnviado);

            mensajeRecibido = br.readLine();
            
            if(mensajeRecibido.equalsIgnoreCase(".")){
                break;
            }
            
            System.out.println("Servidor: "+mensajeRecibido);
        }

    }

    public void stop() throws IOException {
        is.close();
        os.close();
        socket.close();
    }
    
    public static void main(String[] args) {
        try {
            Cliente cliente = new Cliente("localhost", 6080);
            cliente.start();
            cliente.chat();
            cliente.stop();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
