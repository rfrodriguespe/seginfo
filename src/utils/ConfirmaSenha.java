/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import criptografia.Sha256;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import model.bean.Sessao;

/**
 *
 * @author Jefferson Bruno de Assis Lima, José Cláudio de Figueiredo Filho e
 * Rodrigo Ferreira Rodrigues
 *
 */
public class ConfirmaSenha {
    
    public static final Sha256 SJ = new Sha256();

    public static boolean confirmaSenha() {
        boolean validaOk = false;
        JPasswordField campoSenha = new JPasswordField();
        int confirma = JOptionPane.showConfirmDialog(null, campoSenha, "Confirme sua senha", JOptionPane.OK_CANCEL_OPTION);
        if (confirma == JOptionPane.OK_OPTION) {
            String senhadigitada = new String(campoSenha.getPassword());
         //   Sha256 sj = new Sha256();
            Sessao sessao = Sessao.getInstance();
            if (SJ.getSHA256Hash(senhadigitada).equals(sessao.getUsuario().getSenha())) {
                validaOk = true;
            } else {
                JOptionPane.showMessageDialog(null, "Senha não confere\n\n"
                        + "Usuário não cadastrado no banco de dados");
            }
        }
        return validaOk;
    }
}
