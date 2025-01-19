package UDP.Ejercicio3;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    DatagramSocket socket;
    DatagramPacket datagramaEntrada;
    DatagramPacket datagramaSalida;

    public Servidor(int puerto) throws SocketException {
        this.socket = new DatagramSocket(6080);
    }

    public void chat() throws IOException {
        Scanner sc = new Scanner(System.in);
        String mensajeRecibido;

        while (true) {
            byte[] bufferLectura = new byte[1024];

            datagramaEntrada = new DatagramPacket(bufferLectura, bufferLectura.length);
            socket.receive(datagramaEntrada);

            mensajeRecibido = new String(datagramaEntrada.getData(), 0, datagramaEntrada.getLength());
            
            if(mensajeRecibido.equals(".")){
                break;
            }

            System.out.println("Cliente: " + mensajeRecibido);

            String mensajeEnviado;
            mensajeEnviado = sc.nextLine();

            byte[] bufferSalida = mensajeEnviado.getBytes();
            datagramaSalida = new DatagramPacket(bufferSalida, bufferSalida.length, datagramaEntrada.getAddress(), datagramaEntrada.getPort());
            socket.send(datagramaSalida);
            mensajeEnviado = new String(datagramaSalida.getData(), 0, datagramaSalida.getLength());
            
            if(mensajeEnviado.equalsIgnoreCase(".")){
                break;
            }
            
            System.out.println("Servidor: "+mensajeEnviado);
        }
    }
    
    public void stop(){
        socket.close();
    }

    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor(6080);
            servidor.chat();
            servidor.stop();
        } catch (SocketException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
