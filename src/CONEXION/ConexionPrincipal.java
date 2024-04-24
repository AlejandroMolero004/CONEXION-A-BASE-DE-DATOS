package CONEXION;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConexionPrincipal {
    public static Connection conectarBD() {
        Connection conexion;
        //jdbc:mysql:// es obligatorio, importante la barra del final
        String host = "jdbc:mysql://25.11.207.140/";//direcion de la base de datos. Tambien podemos poner una direcion IP
        String user = "remoto";
        String contrasena = "remoto";
        String BD = "datosaplicacion";//nombre base de datos
        System.out.print("Conectando, ");
        System.out.println("espere por favor...");

        try {
            conexion= DriverManager.getConnection(host+BD,user,contrasena);
            System.out.println("Conexion exitosa");
        } catch (Exception e) {
            System.err.println("Error al conectarse al la BBDD: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return conexion;
    }

    public static void desconexion(Connection BD){
        try {
            BD.close();
            System.out.println("Conexion finalizada");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
