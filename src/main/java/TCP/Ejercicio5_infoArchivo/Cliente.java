
package TCP.Ejercicio5_infoArchivo;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Cliente {
    Socket socket;
    String host;
    int puerto;
    InputStream is;
    OutputStream os;

    public Cliente(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }
    
    public void start() throws IOException{
        socket = new Socket(host, puerto);
        is = socket.getInputStream();
        os = socket.getOutputStream();
    }
    
    public void enviarRuta(){
        PrintWriter pw = new PrintWriter(os, true);
        Scanner sc = new Scanner(System.in);
        System.out.println("Envia la ruta absoluta del archivo en el servidor: ");
        
        pw.println(sc.nextLine());
        
    }
    
    public void imprimirInformacion() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        String linea = br.readLine();
        
        while(linea != null){
            System.out.println(linea);
            linea = br.readLine();
        }
        
        System.out.println("Informacion enviada correctamente.");
    }
    
    public void stop() throws IOException{
        is.close();
        os.close();
        socket.close();
    }
    
    public static void main(String[] args) {
        try {
            Cliente cliente = new Cliente("localhost", 6080);
            cliente.start();
            cliente.enviarRuta();
            cliente.imprimirInformacion();
            cliente.stop();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
