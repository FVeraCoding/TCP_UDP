
package UDP.Ejercicio6_infoArchivo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Servidor {
    DatagramSocket socket;
    DatagramPacket datagramaEntrada;
    DatagramPacket datagramaSalida;
    String rutaArchivo;

    public Servidor(int puerto) throws SocketException {
        socket = new DatagramSocket(puerto);
    }
    
    public void recibirRuta() throws IOException{
        byte[] bufferEntrada = new byte[1024];
        datagramaEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
        socket.receive(datagramaEntrada);
        
        this.rutaArchivo = new String(datagramaEntrada.getData(), 0, datagramaEntrada.getLength());
    }
    
    public void mandarInfoArchivo() throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader(new File(this.rutaArchivo)));
        
        String linea = br.readLine();
        
        while(linea != null){
            byte[] bufferSalida = linea.getBytes();
            datagramaSalida = new DatagramPacket(bufferSalida, bufferSalida.length, datagramaEntrada.getAddress(), datagramaEntrada.getPort());
            socket.send(datagramaSalida);
            
            linea = br.readLine();
        }
        
        String mensajeFinalizacion = ".";
        byte[] bufferSalida =  mensajeFinalizacion.getBytes();
            datagramaSalida = new DatagramPacket(bufferSalida, bufferSalida.length, datagramaEntrada.getAddress(), datagramaEntrada.getPort());
            socket.send(datagramaSalida);
    }
    
    public void stop(){
        socket.close();
    }
    
    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor(6080);
            servidor.recibirRuta();
            servidor.mandarInfoArchivo();
            servidor.stop();
        } catch (SocketException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
