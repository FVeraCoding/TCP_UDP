package UDP.Ejercicio4;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    DatagramSocket socket;
    DatagramPacket datagramaEntrada;
    DatagramPacket datagramaSalida;
    int numeroAleatorio;

    public Servidor(int puerto) throws SocketException {
        socket = new DatagramSocket(6200);
    }

    public void generarNumAleatorio() {
        int numeroAleatorio = (int) (Math.random() * 101);
        this.numeroAleatorio = numeroAleatorio;
        System.out.println("El numero aleatorio es: "+this.numeroAleatorio);

    }

    public void jugar() throws IOException {
        byte[] bufferEntrada = new byte[1024];
        String mensajeRecibido;

        while (true) {
            datagramaEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
            socket.receive(datagramaEntrada);

            mensajeRecibido = new String(datagramaEntrada.getData(), 0, datagramaEntrada.getLength());

            int indiceAlmohadilla = mensajeRecibido.indexOf('#');
            int numeroRecibido = Integer.valueOf(mensajeRecibido.substring(indiceAlmohadilla + 1, mensajeRecibido.lastIndexOf("@")));

            if (this.numeroAleatorio == numeroRecibido) {
                String mensajeEnviado = "@acierto#" + numeroRecibido + "@";
                byte[] bufferSalida = mensajeEnviado.getBytes();
                datagramaSalida = new DatagramPacket(bufferSalida, bufferSalida.length, datagramaEntrada.getAddress(), datagramaEntrada.getPort());
                socket.send(datagramaSalida);
                System.out.println("Mensaje enviado: " + new String(datagramaSalida.getData(), 0, datagramaSalida.getLength()));
                break;

            } else {
                String mensajeEnviado = "@fallo#" + numeroRecibido + "@";
                byte[] bufferSalida = mensajeEnviado.getBytes();
                datagramaSalida = new DatagramPacket(bufferSalida, bufferSalida.length, datagramaEntrada.getAddress(), datagramaEntrada.getPort());
                socket.send(datagramaSalida);
                System.out.println("Mensaje enviado: " + new String(datagramaSalida.getData(), 0, datagramaSalida.getLength()));
            }
        }
    }

    public void stop() {
        socket.close();
    }

    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor(6200);
            servidor.generarNumAleatorio();
            servidor.jugar();
            servidor.stop();
        } catch (SocketException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
