package UDP.Ejercicio2;

import java.io.IOException;
import java.net.*;

public class Cliente {

    DatagramSocket socket;
    DatagramPacket salida;
    InetAddress host;
    int puerto;

    public Cliente(int puerto) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        host = InetAddress.getByName("localhost");
        this.puerto = puerto;
    }

    public void enviarDatagramas(String mensaje) throws IOException {
        byte[] buffer = mensaje.getBytes();
        salida = new DatagramPacket(buffer, buffer.length, host, puerto);

        for (int i = 1; i <= 10003; i++) {

            if (i == 10003) {
                mensaje = "FIN";
                buffer = mensaje.getBytes();
                salida = new DatagramPacket(buffer, buffer.length, host, puerto);
                socket.send(salida);

            } else {
                socket.send(salida);
                mensaje = String.valueOf(i);
                buffer = mensaje.getBytes();
                salida = new DatagramPacket(buffer, buffer.length, host, puerto);
            }

        }
    }
    
    public void stop(){
        socket.close();
    }

    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        Cliente cliente = new Cliente(6080);
        cliente.enviarDatagramas("0");
        cliente.stop();
    }

}
