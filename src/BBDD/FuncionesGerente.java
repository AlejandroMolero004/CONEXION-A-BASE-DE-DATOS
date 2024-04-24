package BBDD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static BBDD.FuncionesComprobacion.comprobacionFormatoCorreo;
import static BBDD.FuncionesComprobacion.comprobacionFormatoTelef;

public class FuncionesGerente {
    public static void ConsultarUsuarios(Connection BD){
        // Crear una declaración SQL,tiene que estar dentro de un try catch
        try {
            //creo un statement
            Statement statement = BD.createStatement();
            // Seleciono una tabla de la base de datos
            String sqlQuery = "SELECT * FROM usuarios";//puedo poner columnas individuales tambien
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            if(!resultSet.next()){
                System.out.println("La tabla usuarios esta vacía");
            }
            else {
                resultSet.close();
                resultSet = statement.executeQuery(sqlQuery);
                // Procesar los resultados de la consulta
                System.out.println("Datos de la tabla usuarios:");
                while (resultSet.next()) {//este comando pasa de fila hasta que no haya mas filas
                    int id = resultSet.getInt("ID");//se pone el nombre de la columna
                    String nombre = resultSet.getString("Nombre");
                    String apellido = resultSet.getString("Apellido");
                    String telef = resultSet.getString("Telefono");
                    String correo = resultSet.getString("Correo");
                    System.out.println("ID: " + id + "  Nombre: " + nombre + "  Apellido: " + apellido + "  Telefono: " + telef + "  Correo: " + correo);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean BuscarUsuario(Connection BD,String correo, String telef, String contra){
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
                            System.out.println("Se ha encontrado un usuario con correo "+correo+" y telefono "+telef+" cuyo nombre es "+nombre);
                            compr = true;
                            break;
                        }
                    }
                    if (!compr) {
                        System.out.println("No se ha encontrado ningun usuario que contenga los datos introducidos.");
                    }
                }
            }
            return compr;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static int conseguirID(Connection BD,String correo, String telef, String contra){
        int id=0;
        try{
            Statement statement = BD.createStatement();
            String sqlQuery = "SELECT * FROM usuarios";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()){
                if(resultSet.getString("Correo").equals(correo) && resultSet.getString("Contraseña").equals(contra) && resultSet.getString("Telefono").equals(telef)){
                    id = resultSet.getInt("ID");
                    break;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return id;
    }
}
