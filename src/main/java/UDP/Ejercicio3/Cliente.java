package UDP.Ejercicio3;

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

    public Cliente(int puerto) throws UnknownHostException, SocketException {
        socket = new DatagramSocket();
        host = InetAddress.getByName("localhost");
        this.puerto = puerto;
    }

    public void chat() throws IOException {
        Scanner sc = new Scanner(System.in);
        String mensajeEnviado;
        System.out.println("Envia un mensaje al servidor: ");

        while (true) {
            mensajeEnviado = sc.nextLine();

            byte[] bufferSalida = mensajeEnviado.getBytes();
            datagramaSalida = new DatagramPacket(bufferSalida, bufferSalida.length, this.host, this.puerto);

            socket.send(datagramaSalida);

            mensajeEnviado = new String(datagramaSalida.getData(), 0, datagramaSalida.getLength());

            if (mensajeEnviado.equalsIgnoreCase(".")) {
                break;
            }
            
            System.out.println("Cliente: " + mensajeEnviado);

            byte[] bufferLectura = new byte[1024];
            datagramaEntrada = new DatagramPacket(bufferLectura, bufferLectura.length);

            socket.receive(datagramaEntrada);

            String mensajeRecibido = new String(datagramaEntrada.getData(), 0, datagramaEntrada.getLength());

            if (mensajeRecibido.equalsIgnoreCase(".")) {
                break;
            }
            System.out.println("Servidor: " + mensajeRecibido);

        }

    }

    public void stop() {
        socket.close();
    }

    public static void main(String[] args) {
        try {
            Cliente cliente = new Cliente(6080);
            cliente.chat();
            cliente.stop();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
