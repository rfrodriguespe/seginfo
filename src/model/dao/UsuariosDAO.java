/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.bean.Sessao;
import model.bean.Usuarios;

/**
 *
 * @author Jefferson Bruno de Assis Lima José Cláudio de Figueiredo Filho
 * Rodrigo Ferreira Rodrigues
 *
 * "DAO" responsável por prover ao sistema os métodos de acesso ao banco para as
 * funções de "CRUD", assim o sistema "não se preocupa" em saber como é feito
 *
 */
public class UsuariosDAO {

    public void cadastra(Usuarios u) {
        Connection conn = conexao.ConectaDB.conectaDB();
        PreparedStatement stmt = null;
        String sql = "INSERT INTO usuarios (identificador, nome, email, senha, telefone) VALUES (?, ?, ?, ?, ?);";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, u.getIdentificador());
            stmt.setString(2, u.getNome());
            stmt.setBytes(3, u.getEmail());
            stmt.setString(4, u.getSenha());
            stmt.setBytes(5, u.getTelefone());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Dados inseridos ao banco de dados com sucesso");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar" + ex);
        } finally {
            conexao.ConectaDB.fechaConexao(conn, stmt);
        }
    }

    public List<Usuarios> consulta() {
        Connection conn = conexao.ConectaDB.conectaDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuarios> usuarios = new ArrayList<>();
        try {
            Sessao sessao = Sessao.getInstance();
            if (sessao.getUsuario().getIdentificador().equals("gerente")) {
                stmt = conn.prepareStatement("SELECT * FROM usuarios");
            } else {
                int usuarioLogado = sessao.getUsuario().getId();
                stmt = conn.prepareStatement("SELECT * FROM usuarios WHERE id = ?");
                stmt.setInt(1, usuarioLogado);
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                Usuarios u = new Usuarios();
                u.setId(rs.getInt("id"));
                u.setIdentificador(rs.getString("identificador"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getBytes("email"));
                u.setSenha(rs.getString("senha"));
                u.setTelefone(rs.getBytes("telefone"));
                usuarios.add(u);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Consultar os dados" + ex);
        } finally {
            conexao.ConectaDB.fechaConexao(conn, stmt, rs);
        }
        return usuarios;
    }

    public List<Usuarios> buscaNome(String nome) {
        Connection conn = conexao.ConectaDB.conectaDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuarios> usuarios = new ArrayList<>();
        try {
            stmt = conn.prepareStatement("SELECT * FROM usuarios WHERE nome LIKE ?");
            stmt.setString(1, "%" + nome + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Usuarios u = new Usuarios();
                u.setId(rs.getInt("id"));
                u.setIdentificador(rs.getString("identificador"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getBytes("email"));
                u.setSenha(rs.getString("senha"));
                u.setTelefone(rs.getBytes("telefone"));
                usuarios.add(u);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Consultar os dados" + ex);
        } finally {
            conexao.ConectaDB.fechaConexao(conn, stmt, rs);
        }
        return usuarios;

    }

    public List<Usuarios> buscaIdentificador(String identificador) {
        Connection conn = conexao.ConectaDB.conectaDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuarios> usuarios = new ArrayList<>();
        try {
            stmt = conn.prepareStatement("SELECT * FROM usuarios WHERE identificador LIKE ?");
            stmt.setString(1, "%" + identificador + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Usuarios u = new Usuarios();
                u.setId(rs.getInt("id"));
                u.setIdentificador(rs.getString("identificador"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getBytes("email"));
                u.setSenha(rs.getString("senha"));
                u.setTelefone(rs.getBytes("telefone"));
                usuarios.add(u);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Consultar os dados" + ex);
        } finally {
            conexao.ConectaDB.fechaConexao(conn, stmt, rs);
        }
        return usuarios;

    }

    public void altera(Usuarios u) {
        Connection conn = conexao.ConectaDB.conectaDB();
        PreparedStatement stmt = null;
        String sql = "UPDATE `usuarios` SET `identificador`=?,`nome`=?,`email`=?,`telefone`=? WHERE id=?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, u.getIdentificador());
            stmt.setString(2, u.getNome());
            stmt.setBytes(3, u.getEmail());
            stmt.setBytes(4, u.getTelefone());
            stmt.setInt(5, u.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Dados inseridos ao banco de dados com sucesso");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar" + ex);
        } finally {
            conexao.ConectaDB.fechaConexao(conn, stmt);
        }
    }

    public void alteraComSenha(Usuarios u) {
        Connection conn = conexao.ConectaDB.conectaDB();
        PreparedStatement stmt = null;
        String sql = "UPDATE `usuarios` SET `identificador`=?,`nome`=?,`email`=?,`senha`=?,`telefone`=? WHERE id=?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, u.getIdentificador());
            stmt.setString(2, u.getNome());
            stmt.setBytes(3, u.getEmail());
            stmt.setString(4, u.getSenha());
            stmt.setBytes(5, u.getTelefone());
            stmt.setInt(6, u.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar" + ex);
        } finally {
            conexao.ConectaDB.fechaConexao(conn, stmt);
        }
    }

    public void deleta(Usuarios u) {
        Connection conn = conexao.ConectaDB.conectaDB();
        PreparedStatement stmt = null;
        String sql = "DELETE from usuarios WHERE id=?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, u.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Dados excluidos com sucesso");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir" + ex);
        } finally {
            conexao.ConectaDB.fechaConexao(conn, stmt);
        }
    }

    public boolean validaLogin(String login, String senha) {
        Connection conn = conexao.ConectaDB.conectaDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean validaOk = false;

        try {
            stmt = conn.prepareStatement("SELECT * FROM usuarios WHERE nome = ? AND senha = ?");
            stmt.setString(1, login);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Usuarios u = new Usuarios();

                u.setId(rs.getInt("id"));
                u.setIdentificador(rs.getString("identificador"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getBytes("email"));
                u.setSenha(rs.getString("senha"));
                u.setTelefone(rs.getBytes("telefone"));

                Sessao sessao = Sessao.getInstance();
                sessao.setUsuario(u);

                validaOk = true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Consultar os dados" + ex);
        } finally {
            conexao.ConectaDB.fechaConexao(conn, stmt, rs);
        }

        return validaOk;
    }

}
