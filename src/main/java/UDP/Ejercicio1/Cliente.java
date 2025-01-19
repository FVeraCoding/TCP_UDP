
package UDP.Ejercicio1;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Cliente {
    DatagramSocket socket;
    DatagramPacket datagramaSalida;
    DatagramPacket datagramaEntrada;
    InetAddress direccionHost;
    int puerto;

    public Cliente(int puerto) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        direccionHost = InetAddress.getByName("localhost");
        this.puerto = puerto;
    }
    
    public void recibirDatagrama() throws IOException{
        byte[] buffer = new byte[1024];
        
        datagramaEntrada = new DatagramPacket(buffer, buffer.length);
        socket.receive(datagramaEntrada);
        
        System.out.println("Mensaje recibido: "+new String(buffer));
        
    }
    
    public void enviarDatagrama(String mensaje) throws IOException{
        byte[] buffer = mensaje.getBytes();
        
        
        datagramaSalida = new DatagramPacket(buffer, buffer.length, direccionHost, puerto);
        socket.send(datagramaSalida);
        
        System.out.println("Mensaje enviado: "+mensaje);
    }
    
    
    public void stop(){
        System.out.println("Cerrando conexion.");
        socket.close();
        System.out.println("Conexion cerrada.");
    }
    
    public static void main(String[] args) {
        try {
            Cliente cliente = new Cliente(6080);
            
            cliente.enviarDatagrama("Hola.");
            cliente.recibirDatagrama();
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
