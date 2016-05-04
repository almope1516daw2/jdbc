package Vista;

import Model.Administrador;
import Model.AdministrdorImpl;
import Model.Jugador;
import Model.JugadorImpl;
import interfaces.DAOAdministrador;
import interfaces.DAOJugador;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Main {
    
    public static void main(String[] args) {
           
              
        Administrador Morote = new Administrador();
        
        
        try {
            DAOAdministrador dao = new AdministrdorImpl();
            DAOJugador dao2 = new JugadorImpl();
            dao.sqlCrearEstructura();
            Jugador Inge = new Jugador("inge", "123");
            dao.sqlCrearJugador("inge", "12345","go");
            dao2.sqlCrearCriatura("C1", 2, 1, "mono", "tierra", null, Inge.getNom());
            dao2.sqlCrearCriatura("C2", 3, 1, "mono", "tierra", null, Inge.getNom());
            

            dao2.sqlCrearEquip("A", Inge.getNom());
            dao2.sqlCrearEquip("B", Inge.getNom());
            dao2.sqlAfegirCriaturaEquip("C1", "A", Inge.getNom());
            dao2.sqlAfegirCriaturaEquip("C2", "A", Inge.getNom());
            dao2.sqlEliminarCriaturaEquip("B", Inge.getNom());
            
            
            
        } catch (Exception e) {
            System.out.println(e);
        }
        /*Administrador Morote = new Administrador();
        
        Morote.crearJugador("NewPlayer","NewPass","NewLema");
        Morote.crearJugador("NewPlayer2","NewPass2","NewLema2");
        Morote.crearJugador("NewPlayer3","NewPass3","NewLema4");
        Morote.eliminarJugador("NewPlayer");
        Morote.cercarJugador("NewPlayer2");
        Morote.llistarJugador();*/

        /*Inge.setLema("Kill them all");
        Morote.modificarNomJugador(Inge, "Ingemar");
        Jugador Marta = Morote.creaJugador("Marta","123");
        
        System.out.println(Inge + "\n" + Marta);*/
        
    }
    
    
}
