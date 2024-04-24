package BBDD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static BBDD.FuncionesComprobacion.*;
public class FuncionesUsuario {
    public static void EliminarUsuario(Connection BD,String correo, String telef, String contra){
        try{
            boolean compr=false;
            Statement statement = BD.createStatement();
            String sqlQuery = "SELECT * FROM usuarios";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            if(!resultSet.next()){
                System.out.println("La tabla usuarios esta vacía");
            }
            else {
                resultSet.close();
                resultSet = statement.executeQuery(sqlQuery);
                if (comprobacionFormatoCorreo(correo) && comprobacionFormatoTelef(telef)) {
                    while (resultSet.next()) {
                        if (resultSet.getString("Correo").equals(correo) && resultSet.getString("Contraseña").equals(contra) && resultSet.getString("Telefono").equals(telef)) {
                            String nombre = resultSet.getString("Nombre");
                            String elimBD = "DELETE FROM usuarios WHERE Correo='" + correo + "' AND Contraseña='" + contra + "'";//los strings se ponen entre comillas simples
                            System.out.println("El usuario " + nombre + " y con correo "+ correo +" ha sido eliminado");
                            statement.executeUpdate(elimBD);
                            compr = true;
                            break;
                        }
                    }
                    if (!compr) {
                        System.out.println("Los datos introduccidos no se han encontrado y se ha cancelado la operacion");
                    }
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void RegistrarUsuario(Connection BD,String nombre,String apellido,String correo,String telef,String contra){
        try{
            if(comprobacionFormatoCorreo(correo) && comprobacionFormatoTelef(telef)) {
                if(comprobacionDisponibilidadCorreo(BD,correo) && comprobacionDisponibilidadTelefono(BD,telef)) {
                    Statement statement = BD.createStatement();
                    String IntroBD = "INSERT INTO usuarios (Nombre,Apellido,Correo,Telefono,Nivel,Contraseña)Values('" + nombre + "','" + apellido + "','" + correo + "','" + telef + "','0','" + contra + "')"; //Insert para meter un valor, primero seleciono las columnas, y luego le pongo los valores, entre ''
                    System.out.println("El usuario " + nombre + " con correo " + correo + " ha sido añadido");//Tambien puedo poner strings como Values('" + nombre +"',.....
                    statement.executeUpdate(IntroBD);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static String ConseguirNombre(Connection BD,String correo, String telef, String contra){
        String nombre = "";
        try{
            Statement statement = BD.createStatement();
            String sqlQuery = "SELECT * FROM usuarios";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()){
                if(resultSet.getString("Correo").equals(correo) && resultSet.getString("Contraseña").equals(contra) && resultSet.getString("Telefono").equals(telef)){
                    nombre = resultSet.getString("Nombre");
                    break;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return nombre;
    }

}
