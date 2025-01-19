
package UDP.Ejercicio1;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Servidor {
    DatagramSocket socket;
    DatagramPacket datagramaEntrada;
    DatagramPacket datagramaSalida;

    public Servidor(int puerto) throws SocketException {
        socket = new DatagramSocket(puerto);
    }
    
    public void recibirDatagrama() throws IOException{
        System.out.println("En espera de mensajes...");
        byte[] bufferLectura = new byte[1024];
        
        datagramaEntrada = new DatagramPacket(bufferLectura, bufferLectura.length);
        socket.receive(datagramaEntrada);
        
        System.out.println("Mensaje Recibido: "+new String(bufferLectura));
        
    }
    
    public void enviarDatagrama(String mensaje) throws IOException{
        System.out.println("Enviando mensaje... ");
        
        byte[] bufferSalida = mensaje.getBytes();
        
        datagramaSalida = new DatagramPacket(bufferSalida, bufferSalida.length, datagramaEntrada.getAddress(), datagramaEntrada.getPort());
        socket.send(datagramaSalida);
        
        System.out.println("Mensaje enviado: "+mensaje);
    }
    
    public void Stop(){
        System.out.println("Cerrando conexion... ");
        socket.close();
        System.out.println("Conexion cerrada.");
    }
    
    public static void main(String[] args) {
        try {
            Servidor server = new Servidor(6080);
            server.recibirDatagrama();
            server.enviarDatagrama("Adios");
            server.Stop();
            
        } catch (SocketException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
