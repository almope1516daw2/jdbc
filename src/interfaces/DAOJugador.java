/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

/**
 *
 * @author randeth
 */
public interface DAOJugador {
    public boolean sqlCrearCriatura(String nom, int atac, int defensa, String rasa,
            String medi, String habilitat_esp, String propietari) throws Exception;
    public void sqlModificarCriatura(String nom, String camp, String val, String propietari) throws Exception;
    public void sqlEliminarCriatura(String nom, String propietari) throws Exception;
    public String sqlCercarCriatura(String camp, String val, String propietari) throws Exception;
    public String sqlLlistarCriatura(String propietari) throws Exception;
    
    public void sqlCrearEquip(String nom, String propietari) throws Exception;
    public void sqlModificarEquip(String nom, String val, String propietari) throws Exception;
    public void sqlEliminarEquip(String nom, String propietari) throws Exception;
    public String sqlCercarEquip(String camp, String val, String propietari) throws Exception;
    public String sqlLlistarEquip(String propietari) throws Exception;
    public String sqlLlistarCriaturesEquip(String nom, String propietari) throws Exception;
    public void sqlAfegirCriaturaEquip(String nomCriatura, String nomEquip, String propietari) throws Exception;
    public void sqlEliminarCriaturaEquip(String nomCriatura, String propietari) throws Exception;
    
}
