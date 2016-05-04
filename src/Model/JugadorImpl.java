/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import dao.Conexion;
import interfaces.DAOJugador;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class JugadorImpl extends Conexion implements DAOJugador {

    @Override
    public boolean sqlCrearCriatura(String nom, int atac, int defensa, String rasa, String medi, String habilitat_esp, String propietari) throws Exception {
        try{
            this.conectar();
            int max=11;
            if(!"".equals(habilitat_esp)){
                max=9;
            }
            if((atac+defensa)<=max && defensa>0){
                PreparedStatement st = this.conexion.prepareStatement("INSERT INTO Criatura(nom, atac, defensa, rasa, medi, habilitat_esp, propietari) VALUES (?,?,?,?,?,?,?)");
                st.setString(1, nom);
                st.setInt(2, atac);
                st.setInt(3, defensa);
                st.setString(4, rasa);
                st.setString(5, medi);
                st.setString(6, habilitat_esp);
                st.setString(7, propietari);
                st.executeUpdate();
                return true;
            }else
                return false;
        } catch (Exception e){
            throw e;
        } finally{
            this.cerrar();
            
        }
    }

    @Override
    public void sqlModificarCriatura(String nom, String camp, String val, String propietari) throws Exception {
        try{
            this.conectar();
            String campAux;
            if("nom".equals(camp))
                campAux = "nom";
            else if("atac".equals(camp))
                campAux = "atac";
            else if("defensa".equals(camp))
                campAux = "defensa";
            else if("rasa".equals(camp))
                campAux = "rasa";
            else if("medi".equals(camp))
                campAux = "medi";
            else
                campAux = "habilitat_esp";
            
            PreparedStatement st = this.conexion.prepareStatement("UPDATE Criatura SET "+campAux+"=? WHERE nom = ? AND propietari = ?");
            if(camp == "atac" || camp == "defensa"){
                
                int valAux = Integer.parseInt(val);
                if(camp == "defensa" && valAux!=0){
                st.setInt(1, valAux);
                }
            }else
                st.setString(1, val);
            
            st.setString(2, nom);
            st.setString(3, propietari);
            st.executeUpdate();
            
            
            //st.setString(1, camp);
            
        } catch (Exception e){
            throw e;
        } finally{
            this.cerrar();
            
        }
    }

    @Override
    public void sqlEliminarCriatura(String nom, String propietari) throws Exception {
        try{
            this.conectar();
            PreparedStatement st = this.conexion.prepareStatement("DELETE FROM Criatura WHERE nom = ? AND propietari = ?");
            st.setString(1, nom);
            st.setString(2, propietari);
            st.executeUpdate();
        } catch (Exception e){
            throw e;
        } finally{
            this.cerrar();
            
        }
    }

    @Override
    public String sqlCercarCriatura(String camp, String val, String propietari) throws Exception {
        try{
            this.conectar();
            String campAux;
            String content="";
            if("nom".equals(camp))
                campAux = "nom";
            else if("atac".equals(camp))
                campAux = "atac";
            else if("defensa".equals(camp))
                campAux = "defensa";
            else if("rasa".equals(camp))
                campAux = "rasa";
            else if("medi".equals(camp))
                campAux = "medi";
            else
                campAux = "habilitat_esp";
            
            
            Statement stat = conexion.createStatement();
            if("atac".equals(camp) || "defensa".equals(camp)){
                int valAux = Integer.parseInt(val);
                
                ResultSet resultat = stat.executeQuery("SELECT nom, atac, defensa, rasa, medi, habilitat_esp, equip FROM Criatura WHERE "+campAux+" = "+valAux+" AND propietari = '"+propietari+"'");
                while (resultat.next()) {
                    content+=resultat.getString(1)
                            + "-" + resultat.getInt(2)
                            + "-" + resultat.getInt(3)
                            + "-" + resultat.getString(4)
                            + "-" + resultat.getString(5)
                            + "-" + resultat.getString(6)
                            + "-" + resultat.getInt(7)+"\n";
                }
            }else{
                ResultSet resultat = stat.executeQuery("SELECT nom, atac, defensa, rasa, medi, habilitat_esp, equip FROM Criatura WHERE "+campAux+" = '"+val+"' AND propietari = '"+propietari+"'");
                while (resultat.next()) {
                    content+=resultat.getString(1)
                            + "-" + resultat.getInt(2)
                            + "-" + resultat.getInt(3)
                            + "-" + resultat.getString(4)
                            + "-" + resultat.getString(5)
                            + "-" + resultat.getString(6)
                            + "-" + resultat.getInt(7)+"\n";
                }
            }
            return content;
            
        } catch (Exception e){
            throw e;
        } finally{
            this.cerrar();
        }  
        
    }

    @Override
    public String sqlLlistarCriatura(String propietari) throws Exception {
        try{
            this.conectar();
            String content="";
            Statement stat = conexion.createStatement();
            ResultSet resultat = stat.executeQuery("SELECT nom, atac, defensa, rasa, medi, habilitat_esp, equip FROM Criatura WHERE propietari = '"+propietari+"'");
            while (resultat.next()) {
                    content+=resultat.getString(1)
                            + "-" + resultat.getInt(2)
                            + "-" + resultat.getInt(3)
                            + "-" + resultat.getString(4)
                            + "-" + resultat.getString(5)
                            + "-" + resultat.getString(6)
                            + "-" + resultat.getInt(7)+"\n";
                }
            return content;
        } catch (Exception e){
            throw e;
        } finally{
            this.cerrar();
        }  
    }

    @Override
    public void sqlCrearEquip(String nom, String propietari) throws Exception {
        try{
            this.conectar();
            String cadenaSQL="INSERT INTO Equip(nom, propietari) VALUES (?,?)";
            PreparedStatement st = this.conexion.prepareStatement(cadenaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setString(1, nom);
            st.setString(2, propietari);
            //st.executeUpdate();
            int n = st.executeUpdate();
            try (ResultSet rs = st.getGeneratedKeys()) {
                while (rs.next()) {
                    System.out.println("Codi generat per getGeneratedKeys():"
                            + rs.getInt(1));
                }
            }
            System.out.println("S'ha afegit " + n + " items");
            st.clearParameters();
        } catch (Exception e){
            throw e;
        } finally{
            this.cerrar();
            
        }
    }

    @Override
    public void sqlModificarEquip(String nom, String val, String propietari) throws Exception {
        try{
            this.conectar();
            PreparedStatement st = this.conexion.prepareStatement("UPDATE Equip SET nom=? WHERE nom = ? AND propietari = ?");
            st.setString(1, val);
            st.setString(2, nom);
            st.setString(3, propietari);
            st.executeUpdate();
        } catch (Exception e){
            throw e;
        } finally{
            this.cerrar();
            
        }
    }

    @Override
    public void sqlEliminarEquip(String nom, String propietari) throws Exception {
        try{
            this.conectar();
            PreparedStatement st = this.conexion.prepareStatement("DELETE FROM Equip WHERE nom = ? AND propietari = ?");
            st.setString(1, nom);
            st.setString(2, propietari);
            st.executeUpdate();
        } catch (Exception e){
            throw e;
        } finally{
            this.cerrar();
            
        }
    }

    @Override
    public String sqlCercarEquip(String camp, String val, String propietari) throws Exception {
        try{
            this.conectar();
            String campAux;
            String content="";
            if("nom".equals(camp))
                campAux = "nom";
            else
                campAux = "potencial";
            
            Statement stat = conexion.createStatement();
            if("potencial".equals(camp)){
                int valAux = Integer.parseInt(val);
                ResultSet resultat = stat.executeQuery("SELECT id, nom, potencial FROM Equip WHERE "+campAux+" = "+valAux+" AND propietari = '"+propietari+"'");
                while (resultat.next()) {
                    content+=resultat.getInt(1)
                            + "-" + resultat.getString(2)
                            + "-" + resultat.getInt(3)+"\n";
                }
            }else{
                ResultSet resultat = stat.executeQuery("SELECT id, nom, potencial FROM Equip WHERE "+campAux+" = '"+val+"' AND propietari = '"+propietari+"'");
                while (resultat.next()) {
                    content+=resultat.getInt(1)
                            + "-" + resultat.getString(2)
                            + "-" + resultat.getInt(3)+"\n";
                }
            }
            return content;
            
        } catch (Exception e){
            throw e;
        } finally{
            this.cerrar();
        }  
    }

    @Override
    public String sqlLlistarEquip(String propietari) throws Exception {
         try{
            this.conectar();
            String content="";
            Statement stat = conexion.createStatement();
            ResultSet resultat = stat.executeQuery("SELECT id, nom, potencial FROM Equip WHERE propietari = '"+propietari+"'");
            while (resultat.next()) {
                content+=resultat.getInt(1)
                        + "-" + resultat.getString(2)
                        + "-" + resultat.getInt(3)+"\n";
            }
            return content;
        } catch (Exception e){
            throw e;
        } finally{
            this.cerrar();
        }
    }

    @Override
    public void sqlAfegirCriaturaEquip(String nomCriatura, String nomEquip, String propietari) throws Exception {
        try{
            this.conectar();
            Statement stat = conexion.createStatement();
            //(SELECT equip FROM Criatura WHERE equip = (SELECT e.nom FROM Equip E JOIN Criatura c ON e.id=c.equip WHERE e.nom = 'z')
            
            ResultSet resultat = stat.executeQuery("SELECT (SELECT equip FROM Criatura WHERE nom = '"+nomCriatura+"' AND propietari = '"+propietari+"'),(SELECT nom FROM Equip WHERE id = (SELECT equip FROM Criatura WHERE nom = '"+nomCriatura+"' AND propietari = '"+propietari+"')),id, (SELECT COUNT(equip) FROM Criatura WHERE equip = (SELECT distinct e.id FROM Equip e JOIN Criatura c ON e.id=c.equip WHERE e.nom = '"+nomEquip+"')) FROM Equip WHERE nom = '"+nomEquip+"' AND propietari = '"+propietari+"'");
            while (resultat.next()) {
                int oldID =resultat.getInt(1);
                String oldNomEquip = resultat.getString(2);
                int newID =resultat.getInt(3);
                int count= resultat.getInt(4);
                if(count<=6){
                PreparedStatement st = this.conexion.prepareStatement("UPDATE Criatura SET equip=? WHERE nom = ? AND propietari = ?");
                st.setInt(1, newID);
                st.setString(2, nomCriatura);
                st.setString(3, propietari);
                st.executeUpdate();
                
                PreparedStatement st2 = this.conexion.prepareStatement("UPDATE Equip SET potencial="
                        + "(SELECT (SUM(atac)+SUM(defensa))/2 FROM Criatura WHERE equip = ? AND propietari = ?) WHERE nom = ? AND propietari = ?");
                st2.setInt(1, newID);
                st2.setString(2, propietari);
                st2.setString(3, nomEquip);
                st2.setString(4, propietari);
                st2.executeUpdate();
                
                PreparedStatement st3 = this.conexion.prepareStatement("UPDATE Equip SET potencial="
                        + "(SELECT (SUM(atac)+SUM(defensa))/2 FROM Criatura WHERE equip = ? AND propietari = ?) WHERE nom = ? AND propietari = ?");
                st3.setInt(1, oldID);
                st3.setString(2, propietari);
                st3.setString(3, oldNomEquip);
                st3.setString(4, propietari);
                st3.executeUpdate();
                }
                }
            
                
            
        } catch (Exception e){
            throw e;
        } finally{
            this.cerrar();
            
        }
    }

    @Override
    public void sqlEliminarCriaturaEquip(String nomCriatura, String propietari) throws Exception {
        try{
            this.conectar();
            PreparedStatement st = this.conexion.prepareStatement("UPDATE Criatura SET equip = 0 WHERE nom = ? AND propietari = ?");
            st.setString(1, nomCriatura);
            st.setString(2, propietari);
            st.executeUpdate();
            
        } catch (Exception e){
            throw e;
        } finally{
            this.cerrar();
            
        }
    }

    @Override
    public String sqlLlistarCriaturesEquip(String nom, String propietari) throws Exception {
        try{
            this.conectar();
            String content="";
            Statement stat = conexion.createStatement();
            ResultSet resultat = stat.executeQuery("SELECT nom, atac, defensa, rasa, medi, habilitat_esp FROM Criatura WHERE equip = (SELECT DISTINCT c.equip FROM Criatura c JOIN Equip e ON c.equip=e.id WHERE e.nom = '"+nom+"') AND propietari = '"+propietari+"'");
            while (resultat.next()) {
                content+=resultat.getString(1)
                            + "-" + resultat.getInt(2)
                            + "-" + resultat.getInt(3)
                            + "-" + resultat.getString(4)
                            + "-" + resultat.getString(5)
                            + "-" + resultat.getString(6)+"\n";
            }
            return content;
        } catch (Exception e){
            throw e;
        } finally{
            this.cerrar();
        }
    }
    
}
