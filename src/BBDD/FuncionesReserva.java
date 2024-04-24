package BBDD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static BBDD.FuncionesComprobacion.comprobacionFormatoFecha;
import static BBDD.FuncionesGerente.BuscarUsuario;
import static BBDD.FuncionesGerente.conseguirID;
import static BBDD.FuncionesUsuario.ConseguirNombre;
public class FuncionesReserva {
    public static boolean MostrarReservasdeUsuario(Connection BD, String correo, String telef, String contra){
        boolean compr= false;
        if(BuscarUsuario(BD,correo,telef,contra)) {
            String nombre = ConseguirNombre(BD,correo,telef,contra);
            try {
                Statement statement = BD.createStatement();
                System.out.println("Reservas registradas para el usuario " + nombre +":");
                int id = conseguirID(BD,correo,telef,contra);
                //comprobar si esta vacio
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS count FROM reservas");
                int count = 0;
                if (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
                if (count != 0) {
                    //comprobamos las reservas
                    String sqlQuery = "SELECT * FROM reservas";
                    resultSet = statement.executeQuery(sqlQuery);
                    while (resultSet.next()) {
                        int id2 = resultSet.getInt("ID");
                        if (id2 == id) {
                            String FechaF = resultSet.getString("Fecha");
                            String Hotel = resultSet.getString("Hotel");
                            System.out.println("Reserva el dia " + FechaF + " en el hotel "+Hotel);
                            compr = true;
                        }
                    }
                }
                if (!compr) {
                    System.out.println("El usuario "+nombre+" no tiene ninguna reserva planificada");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return compr;
    }

    //Comprueba
    public static boolean BuscarReserva(Connection BD,String correo, String telef, String contra,String fecha,String hotel){
        boolean compr2 = false;
        if(BuscarUsuario(BD,correo,telef,contra)) {
            try {
                Statement statement = BD.createStatement();

                int id = conseguirID(BD,correo,telef,contra);

                //comprobar si esta vacio
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS count FROM reservas");
                int count = 0;
                if (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
                String nombre = ConseguirNombre(BD,correo,telef,contra);
                if (count != 0) {

                    //comprobamos las reservas
                    String sqlQuery = "SELECT * FROM reservas";
                    resultSet = statement.executeQuery(sqlQuery);
                    while (resultSet.next()) {
                        int id2 = resultSet.getInt("ID");
                        if (id2 == id) {
                            if(resultSet.getString("Fecha").equals(fecha) && resultSet.getString("Hotel").equals(hotel)) {
                                System.out.println("El usuario "+nombre+" tiene una reserva planificada en esta fecha y en este hotel");
                                compr2 = true;
                                break;
                            }
                        }
                    }
                    if (!compr2) {
                        System.out.println("El usuario "+nombre+" no tiene ninguna reserva planificada en esta fecha y en este hotel");
                    }
                }
                if (!compr2) {
                    System.out.println("El usuario "+nombre+" no tiene ninguna reserva planificada en esta fecha y en este hotel");
                }
                return compr2;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return compr2;
    }

    public static void crearReservas(Connection BD,String correo, String telef, String contra,String hotel){
        if(BuscarUsuario(BD,correo,telef,contra)){
            try{
                int id;
                id = conseguirID(BD,correo,telef,contra);
                Scanner scanner = new Scanner(System.in);
                String fecha;
                do {
                    System.out.print("Introduzca la fecha de la reserva(En formato yyyy-mm-dd): ");
                    fecha = scanner.nextLine();
                }while(!comprobacionFormatoFecha(fecha));
                Statement statement = BD.createStatement();
                String sqlQuery = "INSERT INTO reservas (ID,Fecha,Hotel) VALUES ('"+id+"','" + fecha + "','"+ hotel +"')";
                statement.executeUpdate(sqlQuery);
                System.out.println("Reserva hecha para la fecha "+fecha+" en el hotel "+hotel);
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }

    public static void eliminarReservas(Connection BD,String correo, String telef, String contra){
        if(BuscarUsuario(BD,correo,telef,contra)){
            int id;
            id = conseguirID(BD,correo,telef,contra);
            boolean compr;
            compr = MostrarReservasdeUsuario(BD,correo,telef,contra);
            if(compr){
                try {
                    boolean compr2=false;
                    Scanner scanner = new Scanner(System.in);
                    Statement statement = BD.createStatement();
                    String sqlQuery = "SELECT * FROM reservas";
                    ResultSet resultSet = statement.executeQuery(sqlQuery);
                    String fecha;
                    do {
                        System.out.print("Introduzca la fecha de la reserva que desea eliminar: ");
                        fecha = scanner.nextLine();
                    }while(!comprobacionFormatoFecha(fecha));
                    System.out.print("Introduzca el nombre del hotel en el que esta hecha la reserva: ");
                    String hotel = scanner.nextLine();
                    while(resultSet.next()){
                        if(resultSet.getInt("ID")==id && resultSet.getString("Fecha").equals(fecha) && resultSet.getString("Hotel").equals(hotel)){
                            compr2=true;
                            String elimBD = "DELETE FROM reservas WHERE ID='"+id+"' AND Fecha='"+fecha+"' AND Hotel='"+hotel+"'";
                            statement.executeUpdate(elimBD);
                            System.out.println("Reserva eliminada");
                            break;
                        }
                    }
                    if(!compr2){
                        System.out.println("No se puede eliminar una reserva que no existe.");
                    }
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
