package main;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import static BBDD.FuncionesEmpleados.mostrarReservasTodas;
import static BBDD.FuncionesGerente.ConsultarUsuarios;
import static CONEXION.ConexionPrincipal.conectarBD;
import static CONEXION.ConexionPrincipal.desconexion;

public class BASEDATOS {
    public static void prueba() {
        //String nombre="Victor",apellido="Arroyo",correo="victor.ar@gmail.com",telef="689321547",contra="123456";
        String nombre="Pablo",apellido="Borderas",correo="pablo.ba@gmail.com",telef="644363218",contra="1234";
        Connection BD = conectarBD();
        ConsultarUsuarios(BD);
        //MostrarReservasdeUsuario(BD,correo,telef,contra);
        //RegistrarUsuario(BD,nombre,apellido,correo,telef,contra);
        //BuscarUsuario(BD,correo,telef,contra);
        //crearReservas(BD,correo,telef,contra,"Ambassador");
        //BuscarReserva(BD,correo,telef,contra,"2025-04-15","Ambassador");
        // eliminarReservas(BD,correo,telef,contra);
        //EliminarUsuario(BD,correo,telef,contra);
       // mostrarReservasTodas(BD,"Ambassador");
        //ConsultarUsuarios(BD);
        desconexion(BD);
    }
}
