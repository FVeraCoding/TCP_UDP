
    package TCP.Ejercicio2;

import java.io.File;
import java.io.FileOutputStream;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.OutputStream;
    import java.io.PrintWriter;
    import java.net.*;
    import java.util.Scanner;
    import java.util.logging.Level;
    import java.util.logging.Logger;


    public class Cliente {
        Socket socket;
        InputStream is;
        OutputStream os;
        String host;
        int puerto;

        public Cliente(String host, int puerto) {
            this.host = host;
            this.puerto = puerto;
        }

        public void start() throws IOException{
            System.out.println("Estableciendo conexion");
            socket = new Socket("localhost", 6080);
            is = socket.getInputStream();
            os = socket.getOutputStream();
            System.out.println("Conexion establecida.");
        }

        public void stop() throws IOException{
            System.out.println("Cerrando conexion.");
            is.close();
            os.close();
            socket.close();
            System.out.println("Conexion cerrada.");
        }

        public void enviarRutaArchivo(){
            Scanner sc = new Scanner(System.in);
            System.out.println("Introduce la ruta absoluta del fichero dentro del servidor: ");
            String ruta = sc.nextLine();

            PrintWriter pw = new PrintWriter(os, true);
            pw.println(ruta);
            
        }

        public void recibirCrearArchivo() throws IOException{
            
            File archivoRecibido = new File("C:\\Users\\Fernando\\Desktop\\archivo_copia.txt");
            FileOutputStream fos = new FileOutputStream(archivoRecibido);
            
            byte[] bytesFichero = is.readAllBytes();
            
            fos.write(bytesFichero);
            
        }

        public static void main(String[] args) {
            try {
                Cliente cliente = new Cliente("localhost", 6080);
                cliente.start();
                cliente.enviarRutaArchivo();
                cliente.recibirCrearArchivo();
                cliente.stop();
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
