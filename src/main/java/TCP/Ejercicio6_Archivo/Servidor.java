
package TCP.Ejercicio6_Archivo;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Servidor {
    ServerSocket servidor;
    Socket socket;
    InputStream is;
    OutputStream os;
    String rutaArchivo;

    public Servidor(int puerto) throws IOException {
        servidor = new ServerSocket(puerto);
    }
    
    public void start() throws IOException{
        socket = servidor.accept();
        is = socket.getInputStream();
        os = socket.getOutputStream();
    }
    
    public void recibirRutaArchivo() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        rutaArchivo = br.readLine();
    }
    
    public void enviarArchivo() throws FileNotFoundException, IOException{
        File archivo = new File(rutaArchivo);
        FileInputStream fis = new FileInputStream(archivo);
        byte[] bytesArchivo = fis.readAllBytes();
        
        os.write(bytesArchivo);
    }
    
    public void stop() throws IOException{
        is.close();
        os.close();
        socket.close();
        servidor.close();
    }
    
    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor(6080);
            servidor.start();
            servidor.recibirRutaArchivo();
            servidor.enviarArchivo();
            servidor.stop();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
