package TCP.Ejercicio5_infoArchivo;

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

    public void start() throws IOException {
        socket = servidor.accept();
        is = socket.getInputStream();
        os = socket.getOutputStream();
    }

    public void recibirRutaArchivo() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        this.rutaArchivo = br.readLine();
    }

    public void infoArchivo() throws FileNotFoundException, IOException {
        
        PrintWriter pw = new PrintWriter(os, true);
        File archivo = new File(this.rutaArchivo);
        BufferedReader br = new BufferedReader(new FileReader(archivo));

        if (archivo.exists()) {

            String linea = br.readLine();
            
            while(linea != null){
                pw.println(linea);
                linea = br.readLine();
            }
        } else {
            pw.println("El archivo no se encuentra en la ruta especificada.");
        }

    }

    public void stop() throws IOException {
        is.close();
        os.close();
        socket.close();
    }

    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor(6080);
            servidor.start();
            servidor.recibirRutaArchivo();
            servidor.infoArchivo();
            servidor.stop();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
