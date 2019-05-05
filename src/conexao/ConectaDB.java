/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Jefferson Bruno de Assis Lima, José Cláudio de Figueiredo Filho e
 * Rodrigo Ferreira Rodrigues.
 *
 */
/**
 *
 * Classe que conecta e fecha as conexões com o banco de dados
 *
 */
public class ConectaDB {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost/seginfo";
    private static final String USER = "root";
    private static final String PASS = "";

    public static void verificaDB() {

        int escolha = 0; //Zero é o valor que JOptionPane.YES_OPTION retorna, logo entrará no DO
        do {
            try {
                conexao.ConectaDB.conectaDB();
                break;
            } catch (Exception e) {
                escolha = JOptionPane.showConfirmDialog(null, "Falha ao verificar a conectividade com o banco de dados\n"
                        + "\n"
                        + "Rodar o programa de novo?\n"
                        +"(sim) o programa tentará a conexão após você iniciar o serviço do BD\n"
                        +"(não) o programa será fechado"
                        , "ERRO DE CONEXÃO", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (escolha == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        } while (escolha == JOptionPane.YES_OPTION); //Escolhendo o botão "sim", o laço se repete até que o serviço do banco inicie e emtão caia no "try"
    }

    /**
     *
     * @return
     */
    public static Connection conectaDB() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);

        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Erro na conexão: ", ex);
        }
    }

    /**
     *
     * @param conn
     */
    public static void fechaConexao(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConectaDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param conn
     * @param stmt
     */
    public static void fechaConexao(Connection conn, PreparedStatement stmt) {
        fechaConexao(conn);
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConectaDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param conn
     * @param stmt
     * @param rs
     */
    public static void fechaConexao(Connection conn, PreparedStatement stmt, ResultSet rs) {
        fechaConexao(conn, stmt);
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConectaDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
