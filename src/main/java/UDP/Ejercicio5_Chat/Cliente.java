package UDP.Ejercicio5_Chat;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    DatagramSocket socket;
    DatagramPacket datagramaEntrada;
    DatagramPacket datagramaSalida;
    InetAddress host;
    int puerto;

    public Cliente(int puerto) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        this.host = InetAddress.getByName("localhost");
        this.puerto = puerto;
    }

    public void chat() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Manda un mensaje al Servidor: ");
        while (true) {
            String mensajeEnviado = sc.nextLine();

            byte[] bufferSalida = mensajeEnviado.getBytes();
            datagramaSalida = new DatagramPacket(bufferSalida, bufferSalida.length, host, puerto);
            socket.send(datagramaSalida);

            if (mensajeEnviado.equalsIgnoreCase(".")) {
                break;
            }

            System.out.println("Cliente: " + mensajeEnviado);

            byte[] bufferEntrada = new byte[1024];
            datagramaEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
            socket.receive(datagramaEntrada);

            String mensajeRecibido = new String(datagramaEntrada.getData(), 0, datagramaEntrada.getLength());

            if (mensajeRecibido.equalsIgnoreCase(".")) {
                break;
            }

            System.out.println("Servidor: " + mensajeRecibido);
        }
    }
    
    public void stop(){
        socket.close();
    }
    
    public static void main(String[] args) {
        try {
            Cliente cliente = new Cliente(6080);
            cliente.chat();
            cliente.stop();
        } catch (SocketException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
