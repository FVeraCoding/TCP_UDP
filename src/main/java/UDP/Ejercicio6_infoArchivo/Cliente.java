package UDP.Ejercicio6_infoArchivo;

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
        this.host = InetAddress.getByName("localhost");
        this.puerto = puerto;
    }

    public void enviarRuta() throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Envía la ruta absoluta del archivo dentro del servidor: ");
        String ruta = sc.nextLine();

        byte[] bufferSalida = ruta.getBytes();
        datagramaSalida = new DatagramPacket(bufferSalida, bufferSalida.length, host, puerto);
        socket.send(datagramaSalida);
    }

    public void recibirInformacion() throws IOException {

        byte[] bufferEntrada = new byte[1024];
        datagramaEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
        socket.receive(datagramaEntrada);

        String linea = new String(datagramaEntrada.getData(), 0, datagramaEntrada.getLength());

        while (!linea.equalsIgnoreCase(".")) {
            System.out.println(linea);
            bufferEntrada = new byte[1024];
            datagramaEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
            socket.receive(datagramaEntrada);
            linea = new String(datagramaEntrada.getData(), 0, datagramaEntrada.getLength());

        }
    }
    
    public static void main(String[] args) {
        try {
            Cliente cliente = new Cliente(6080);
            cliente.enviarRuta();
            cliente.recibirInformacion();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
