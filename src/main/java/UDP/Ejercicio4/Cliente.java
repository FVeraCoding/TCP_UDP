package UDP.Ejercicio4;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    DatagramSocket socket;
    DatagramPacket datagramaSalida;
    DatagramPacket datagramaEntrada;
    InetAddress host;
    int puerto;
    String nombre;

    public Cliente(int puerto, String nombre) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        host = InetAddress.getByName("localhost");
        this.puerto = puerto;
        this.nombre = nombre;
    }

    public void jugar() throws IOException {
        Scanner sc = new Scanner(System.in);
        int numeroIntroducido;
        String mensajeEnviado;
        while (true) {
            System.out.println("Introduce un numero entre el 1 y el 100: ");
            numeroIntroducido = sc.nextInt();
            sc.nextLine(); //flush del scanner.

            mensajeEnviado = "@" + this.nombre + "#" + String.valueOf(numeroIntroducido) + "@";

            byte[] bufferSalida = mensajeEnviado.getBytes();
            datagramaSalida = new DatagramPacket(bufferSalida, bufferSalida.length, host, puerto);
            socket.send(datagramaSalida);

            byte[] bufferEntrada = new byte[1024];
            datagramaEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
            socket.receive(datagramaEntrada);
            String mensajeRecibido = new String(datagramaEntrada.getData(), 0, datagramaEntrada.getLength());

            if (mensajeRecibido.startsWith("@acierto")) {
                System.out.println("Enhorabuena, has acertado el numero");
                break;
            } else {
                System.out.println("Servidor: " + mensajeRecibido);
            }
        }

    }
    
    public void stop(){
        socket.close();
    }
    
    public static void main(String[] args) {
        try {
            Cliente cliente = new Cliente(6200, "Fernando");
            cliente.jugar();
            cliente.stop();
            
            
        } catch (SocketException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
