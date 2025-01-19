package UDP.Ejercicio2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

public class Servidor {

    DatagramSocket socket;
    DatagramPacket datagramaEntrada;
    DatagramPacket datagramaSalida;

    public Servidor(int puerto) throws SocketException {
        socket = new DatagramSocket(puerto);
    }

    public void recibirDatagrama() throws IOException {
        byte[] buffer = new byte[1024];
        datagramaEntrada = new DatagramPacket(buffer, buffer.length);
        System.out.println("Esperando mensajes.");
        socket.receive(datagramaEntrada);
        File archivo = new File("C:\\Users\\Fernando\\Desktop\\archivo.txt");
        PrintWriter pw = new PrintWriter(new FileWriter(archivo, true));

        while (true) {

            String mensaje = new String(datagramaEntrada.getData(), 0, datagramaEntrada.getLength());
            if (mensaje.equalsIgnoreCase("FIN")) {
                System.out.println("Mensajes almacenados correctamente.");
                break;
            }
            pw.println(new String(buffer));
            socket.receive(datagramaEntrada);
        }
    }

    public void stop() {
        socket.close();
    }

    public static void main(String[] args) throws SocketException, IOException {
        Servidor servidor = new Servidor(6080);
        servidor.recibirDatagrama();
        servidor.stop();

    }

}
