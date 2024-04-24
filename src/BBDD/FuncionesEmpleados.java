package BBDD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static BBDD.FuncionesUsuario.ConseguirNombre;
public class FuncionesEmpleados {
    public static void mostrarReservasTodas(Connection BD, String hotel){
        try {
            Statement statement = BD.createStatement();
            System.out.println("Reservas registradas para el hotel " + hotel +":");

            //comprobar si esta vacio
            ResultSet resultSet = statement.executeQuery("SELECT COUNT() AS count FROM reservas");
            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt("count");
            }
            if (count != 0) {
                //comprobamos las reservas
                String sqlQuery = "SELECT FROM reservas";
                resultSet = statement.executeQuery(sqlQuery);
                while (resultSet.next()) {
                    if(resultSet.getString("Hotel").equals(hotel)) {
                        int id = resultSet.getInt("ID");
                        String FechaF = resultSet.getString("Fecha");
                        String Hotel = resultSet.getString("Hotel");
                        System.out.println("ID: " + id + "  Fecha de reserva: " + FechaF + " Hotel: " + Hotel);
                    }
                }
            }
            else {
                System.out.println("La tabla reservas esta vacia");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
