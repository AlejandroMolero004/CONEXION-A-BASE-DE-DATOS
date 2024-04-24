package BBDD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static BBDD.FuncionesGerente.BuscarUsuario;
import static BBDD.FuncionesGerente.conseguirID;
import static BBDD.FuncionesUsuario.ConseguirNombre;
public class FuncionesComprobacion {
    public static boolean comprobacionFormatoCorreo(String correo){
        boolean compr1=false;
        boolean compr2=false;
        int iAnt=0;
        if(!correo.isEmpty()) {
            for (int i = 0; i < correo.length(); i++) {
                if (correo.charAt(i) == '@' && i+1 < correo.length()) {
                    if(!correo.startsWith("@") && correo.charAt(i-1) != ' ') {
                        compr1 = true;
                        iAnt = i;
                    }
                    break;
                }
            }
            if(compr1){
                if(correo.charAt(iAnt+1)!='.') {
                    for (int i = iAnt; i < correo.length(); i++) {
                        if (correo.charAt(i)=='.' && i+1 < correo.length()){
                            if(correo.charAt(i+1) != ' ' ){
                                compr2=true;
                            }
                            break;
                        }
                    }
                }
            }
            if(!compr2){
                System.out.println("El correo no tiene un formato correcto");
            }
        }
        return compr2;
    }

    public static boolean comprobacionFormatoTelef(String telef){
        if(telef.length()>=9 && telef.length()<=15){
            return true;
        }
        else{
            System.out.println("El telefono no tiene un formato correcto");
            return false;
        }
    }

    public static boolean comprobacionFormatoFecha(String fecha){
        boolean compr=true;
        String aux="";
        int aux2;
        int mesA=0;
        if(fecha.charAt(4)=='-' && fecha.charAt(7)=='-' && fecha.length()==10){
            for(int i = 0;i<fecha.length();i++){
                if(i<=3 || i==5 || i==6 || i==8 || i==9){
                    if(!Character.isDigit(fecha.charAt(i))){
                        compr=false;
                        break;
                    }
                    else{
                        aux = aux + fecha.charAt(i);
                    }
                }
                if(i==4){
                    aux2 = Integer.parseInt(aux);
                    if(aux2<2024){
                        System.out.println("Anno invalido");
                        compr=false;
                        break;
                    }
                    aux2=0;
                    aux="";
                }
                if(i==7){
                    aux2 = Integer.parseInt(aux);
                    if(aux2<1 || aux2>12){
                        System.out.println("Mes invalido");
                        compr=false;
                        break;
                    }
                    mesA=aux2;
                    aux2=0;
                    aux="";
                }
                if(i+1==10){
                    aux2 = Integer.parseInt(aux);
                    if(mesA==2){
                        if (aux2 < 1 || aux2 > 28) {
                            System.out.println("Dia invalido");
                            compr=false;
                            break;
                        }
                    }else {
                        if (aux2 < 1 || aux2 > 31) {
                            System.out.println("Dia invalido");
                            compr=false;
                            break;
                        }
                    }
                    aux2=0;
                    aux="";
                }
            }
            if(!compr){
                System.out.println("La fecha no tiene un formato valido");
            }
        }
        else{
            compr=false;
            System.out.println("La fecha no tiene un formato valido");
        }
        return compr;
    }

    public static boolean comprobacionDisponibilidadCorreo(Connection BD,String correo){
        boolean compr=true;
        try {
            Statement statement = BD.createStatement();
            String sqlQuery = "SELECT Correo FROM usuarios";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()){
                if(resultSet.getString("Correo").equals(correo)){
                    System.out.println("El correo "+ correo +" no esta disponible");
                    compr=false;
                    break;
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return compr;
    }

    public static boolean comprobacionDisponibilidadTelefono(Connection BD,String telef){
        boolean compr=true;
        try {
            Statement statement = BD.createStatement();
            String sqlQuery = "SELECT Telefono FROM usuarios";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()){
                if(resultSet.getString("Telefono").equals(telef)){
                    System.out.println("El telefono "+ telef +" no esta disponible");
                    compr=false;
                    break;
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return compr;
    }

    public static boolean comprobacionTieneReservas(Connection BD,String correo, String telef, String contra){
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
                            System.out.println("El usuario "+nombre+" tiene reservas planificadas");
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

}
