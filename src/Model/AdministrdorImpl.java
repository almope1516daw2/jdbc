/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import dao.Conexion;
import interfaces.DAOAdministrador;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author randeth
 */
public class AdministrdorImpl extends Conexion implements DAOAdministrador {

    @Override
    public void sqlCrearJugador(String nom, String pass, String lema) throws Exception {
        try{
            this.conectar();
            PreparedStatement st = this.conexion.prepareStatement("INSERT INTO Jugador VALUES (?,?,?,0,0,0)");
            st.setString(1, nom);
            st.setString(2, pass);
            st.setString(3, lema);
            st.executeUpdate();
        } catch (Exception e){
            throw e;
        } finally{
            this.cerrar();
            
        }
    }

    @Override
    public void sqlModificarJugador(String nom, String camp, String val) throws Exception {
        try{
            this.conectar();
            String campAux;
            if("nom".equals(camp))
                campAux = "nom";
            else if("pass".equals(camp))
                campAux = "pass";
            else
                campAux = "lema";
            PreparedStatement st = this.conexion.prepareStatement("UPDATE Jugador SET "+campAux+"=? WHERE nom = ?");
            st.setString(1, val);
            st.setString(2, nom);
            st.executeUpdate();
            
            
            //st.setString(1, camp);
            
        } catch (Exception e){
            throw e;
        } finally{
            this.cerrar();
            
        }
    }

    @Override
    public void sqlEliminarJugador(String nom) throws Exception {
        try{
            this.conectar();
            PreparedStatement st = this.conexion.prepareStatement("DELETE FROM Jugador where nom = ?");
            st.setString(1, nom);
            st.executeUpdate();
        } catch (Exception e){
            throw e;
        } finally{
            this.cerrar();
            
        }
    }

    @Override
    public void sqlCercarJugador(String nom) throws Exception {
        try{
            this.conectar();
            Statement stat = conexion.createStatement();
            ResultSet resultat = stat.executeQuery("SELECT nom, pass, lema, victories, derrotes, empats FROM Jugador WHERE nom = '"+nom+"'");
            while (resultat.next()) {
                    System.out.println(resultat.getString(1)
                            + "-" + resultat.getString(2)
                            + "-" + resultat.getString(3)
                            + "-" + resultat.getInt(4)
                            + "-" + resultat.getInt(5)
                            + "-" + resultat.getInt(6));
                }
        } catch (Exception e){
            throw e;
        } finally{
            this.cerrar();
            
        }
    }

    @Override
    public void sqlLlistarJugador() throws Exception {
        try{
            this.conectar();
            Statement stat = conexion.createStatement();
            ResultSet resultat = stat.executeQuery("SELECT nom, pass, lema, victories, derrotes, empats FROM Jugador");
            while (resultat.next()) {
                    System.out.println(resultat.getString(1)
                            + "-" + resultat.getString(2)
                            + "-" + resultat.getString(3)
                            + "-" + resultat.getInt(4)
                            + "-" + resultat.getInt(5)
                            + "-" + resultat.getInt(6));
                }
        } catch (Exception e){
            throw e;
        } finally{
            this.cerrar();
            
        }
    }

    @Override
    public void sqlCrearEstructura() throws Exception {
        try {
            this.conectar();
            Statement stat = conexion.createStatement();
            
            stat.executeUpdate("DROP TABLE IF EXISTS Criatura");
            stat.executeUpdate("DROP TABLE IF EXISTS Equip");
            stat.executeUpdate("DROP TABLE IF EXISTS Jugador");

            
             stat.executeUpdate("CREATE TABLE Jugador ("
                    + "nom VARCHAR(20),"
                    + "pass VARCHAR(20),"
                    + "lema VARCHAR(20),"
                    + "victories INT," 
                    + "derrotes INT," 
                    + "empats INT," 
                    + "PRIMARY KEY(nom))");
            
            stat.executeUpdate("CREATE TABLE Criatura ("
                    + "nom VARCHAR(20),"
                    + "atac INT,"
                    + "defensa INT,"
                    + "rasa VARCHAR(10),"
                    + "medi VARCHAR(10),"
                    + "habilitat_esp VARCHAR(20),"
                    + "imatge VARCHAR(20),"
                    + "propietari VARCHAR(20),"
                    + "ready BOOLEAN,"
                    + "equip INT,"
                    + "PRIMARY KEY(nom),"
                    + "FOREIGN KEY (propietari) REFERENCES Jugador(nom))");

           
           
            stat.executeUpdate("CREATE TABLE Equip ("
                    + "id INT AUTO_INCREMENT,"
                    + "nom VARCHAR(20),"
                    + "propietari VARCHAR(20),"
                    + "potencial DOUBLE DEFAULT '0',"
                    + "ready BOOLEAN,"
                    + "PRIMARY KEY(id),"
                    + "FOREIGN KEY (propietari) REFERENCES Jugador(nom))");
            
            stat.executeUpdate("INSERT INTO Jugador(nom, pass) VALUES('player','p')");
            stat.executeUpdate("INSERT INTO Criatura(nom,atac,defensa,propietari) VALUES('A',5,3,'player')");
            stat.executeUpdate("INSERT INTO Criatura(nom,atac,defensa,propietari) VALUES('B',1,2,'player')");
            stat.executeUpdate("INSERT INTO Criatura(nom,atac,defensa,propietari) VALUES('C',1,2,'player')");
            stat.executeUpdate("INSERT INTO Criatura(nom,atac,defensa,propietari) VALUES('D',1,2,'player')");
            stat.executeUpdate("INSERT INTO Criatura(nom,atac,defensa,propietari) VALUES('E',1,2,'player')");
            stat.executeUpdate("INSERT INTO Criatura(nom,atac,defensa,propietari) VALUES('F',1,2,'player')");
            stat.executeUpdate("INSERT INTO Criatura(nom,atac,defensa,propietari) VALUES('G',1,2,'player')");
            

            
        } finally {
            this.cerrar();
        }
    }

    @Override
    public String sqlJugadorLogin(String camp, String val) throws Exception {
        try{
            this.conectar();
            Statement stat = conexion.createStatement();
            String campAux;
            if("nom".equals(camp))
               campAux="nom";
            else
                campAux="pass";
            ResultSet resultat = stat.executeQuery("SELECT "+campAux+" FROM Jugador WHERE "+campAux+" = '"+val+"'");
            if (resultat.next()) {
                    return resultat.getString(1);
                }
            else return "0";
        } catch (Exception e){
            throw e;
        } finally{
            this.cerrar();
            
        }
    }
    
}
