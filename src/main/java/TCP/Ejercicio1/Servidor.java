
package TCP.Ejercicio1;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Servidor {
    ServerSocket servidor;
    Socket socket;
    InputStream is;
    OutputStream os;
    File fichero;

    public Servidor(int puerto) throws IOException {
        this.servidor = new ServerSocket(puerto);
    }
    
    public void start() throws IOException{
        System.out.println("Inicializando comunicacion.");
        socket = servidor.accept();
        is = socket.getInputStream();
        os = socket.getOutputStream();
        System.out.println("Comunicaion establecida");
    }
    
    public void stop() throws IOException{
        System.out.println("Cerrando conexion.");
        is.close();
        os.close();
        socket.close();
        servidor.close();
        System.out.println("Conexion cerrada.");
    }
    
    public void recibirNombreFichero() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        this.fichero = new File(br.readLine());
    }
    
    public void leerEnviarFichero(File fichero) throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader(fichero));
        PrintWriter pw = new PrintWriter(os, true);
        
        String linea = br.readLine();
        
        while(linea != null){
            pw.println(linea);
            linea = br.readLine();
        }
        
        pw.println("FIN");
    }
    
    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor(6080);
            servidor.start();
            servidor.recibirNombreFichero();
            servidor.leerEnviarFichero(servidor.fichero);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
