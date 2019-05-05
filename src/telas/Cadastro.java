/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import criptografia.RSA;
import static criptografia.RSA.PATH_CHAVE_PUBLICA;
import criptografia.Sha256;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import model.bean.Usuarios;
import model.dao.UsuariosDAO;

/**
 *
 * @author Jefferson Bruno de Assis Lima, José Cláudio de Figueiredo Filho e
 * Rodrigo Ferreira Rodrigues
 *
 */
public class Cadastro extends javax.swing.JInternalFrame {
    
    //Declarando as variáveis usadas para trabalhar no banco de dados.  
    Connection conecta = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public Cadastro() {
        initComponents();
        conexao.ConectaDB.verificaDB(); //Chama o método verificaDB da classe ConectaDB para validar se o banco está ok
    }

    //Método que lipa os campos
    public void limpaCampos() {
        jTextFieldNome.setText("");
        jPasswordFieldSenha.setText("");
        jFormattedTextFieldTelefone.setText("");
        jTextFieldEmail.setText("");
    }

    public void cadastra() {

        Sha256 sj = new Sha256();
        Usuarios u = new Usuarios();
        UsuariosDAO dao = new UsuariosDAO();

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PUBLICA));
            final PublicKey chavePublica = (PublicKey) inputStream.readObject();

            String identificador = "";
            if (jRadioButtonGerente.isSelected() == true) {
                identificador = "gerente";
            } else if (jRadioButtonComum.isSelected() == true) {
                identificador = "comum";
            }
            u.setIdentificador(identificador);
            u.setNome(jTextFieldNome.getText());
            u.setEmail(RSA.criptografa(jTextFieldEmail.getText(), chavePublica));
            u.setSenha(sj.getSHA256Hash(new String(jPasswordFieldSenha.getPassword())));
            u.setTelefone(RSA.criptografa(jFormattedTextFieldTelefone.getText(), chavePublica));

            if (utils.ConfirmaSenha.confirmaSenha()) {
                dao.cadastra(u);
            } else {
                JOptionPane.showMessageDialog(null, "Nenhuma alteração foi feita ao banco de dados");
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void validaEmail(Object value) throws Exception {
        String regex = "^[a-zA-Z0-9]+[a-zA-Z0-9_.-]+@{1}[a-zA-Z0-9_.-]*\\.+[a-z]{2,4}";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher((String) value);
        if (!m.matches()) {
            JOptionPane.showMessageDialog(null, "Informe um email válido", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupTipoUsuario = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jRadioButtonGerente = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jRadioButtonComum = new javax.swing.JRadioButton();
        jTextFieldNome = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPasswordFieldSenha = new javax.swing.JPasswordField();
        jButtonLimpaCampos = new javax.swing.JButton();
        jButtonIncluir = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jFormattedTextFieldTelefone = new javax.swing.JFormattedTextField();
        jTextFieldEmail = new javax.swing.JTextField();

        setBorder(null);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        jLabel1.setText("Cadastro de usuários");

        jLabel2.setText("Nome");

        buttonGroupTipoUsuario.add(jRadioButtonGerente);
        jRadioButtonGerente.setText("Gerente");
        jRadioButtonGerente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonGerenteActionPerformed(evt);
            }
        });

        jLabel3.setText("Tipo");

        buttonGroupTipoUsuario.add(jRadioButtonComum);
        jRadioButtonComum.setSelected(true);
        jRadioButtonComum.setText("Comum");
        jRadioButtonComum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonComumActionPerformed(evt);
            }
        });

        jTextFieldNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNomeActionPerformed(evt);
            }
        });

        jLabel4.setText("Email");

        jLabel5.setText("Senha");

        jButtonLimpaCampos.setText("Limpar Campos");
        jButtonLimpaCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLimpaCamposActionPerformed(evt);
            }
        });

        jButtonIncluir.setText("Incluir");
        jButtonIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIncluirActionPerformed(evt);
            }
        });

        jLabel6.setText("Telefone");

        try {
            jFormattedTextFieldTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextFieldTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextFieldTelefoneActionPerformed(evt);
            }
        });

        jTextFieldEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldEmailFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFormattedTextFieldTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPasswordFieldSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jRadioButtonComum)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonGerente))
                            .addComponent(jTextFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jButtonLimpaCampos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonIncluir))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(jLabel1)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jRadioButtonGerente)
                    .addComponent(jRadioButtonComum))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jPasswordFieldSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFormattedTextFieldTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonLimpaCampos)
                    .addComponent(jButtonIncluir))
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButtonGerenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonGerenteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonGerenteActionPerformed

    private void jRadioButtonComumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonComumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonComumActionPerformed

    private void jTextFieldNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNomeActionPerformed

    private void jButtonLimpaCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimpaCamposActionPerformed
        limpaCampos();
    }//GEN-LAST:event_jButtonLimpaCamposActionPerformed

    private void jButtonIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIncluirActionPerformed
        cadastra();
        limpaCampos();
    }//GEN-LAST:event_jButtonIncluirActionPerformed

    private void jFormattedTextFieldTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextFieldTelefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextFieldTelefoneActionPerformed

    private void jTextFieldEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldEmailFocusLost
        try {
            validaEmail(jTextFieldEmail.getText());
        } catch (Exception ex) {
            Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextFieldEmailFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupTipoUsuario;
    private javax.swing.JButton jButtonIncluir;
    private javax.swing.JButton jButtonLimpaCampos;
    private javax.swing.JFormattedTextField jFormattedTextFieldTelefone;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordFieldSenha;
    private javax.swing.JRadioButton jRadioButtonComum;
    private javax.swing.JRadioButton jRadioButtonGerente;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldNome;
    // End of variables declaration//GEN-END:variables

}
