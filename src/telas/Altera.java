/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import criptografia.RSA;
import static criptografia.RSA.PATH_CHAVE_PRIVADA;
import static criptografia.RSA.PATH_CHAVE_PUBLICA;
import criptografia.Sha256;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bean.Sessao;
import model.bean.Usuarios;
import model.dao.UsuariosDAO;

/**
 *
 * @author Jefferson Bruno de Assis Lima José Cláudio de Figueiredo Filho
 * Rodrigo Ferreira Rodrigues
 *
 */
public class Altera extends javax.swing.JInternalFrame {

    Connection conecta = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public Altera() {
        initComponents();
        desabilitaAutoPromocao();//Inibe a autopromoção de usuário comum para gerente
        conexao.ConectaDB.verificaDB(); //Chama o método verificaDB da classe ConectaDB para validar se o banco está ok
    }
    
        public final void desabilitaAutoPromocao(){
        Sessao sessao = Sessao.getInstance();
        if (sessao.getUsuario().getIdentificador().equals("comum")) {
            jRadioButtonGerente.setEnabled(false);
            jRadioButtonComum.setEnabled(false);
        }
    }

    public void limpaCampos() {
        jTextFieldNome.setText("");
        jPasswordFieldSenha.setText("");
        jFormattedTextFieldTelefone.setText("");
        jTextFieldEmail.setText("");
    }

    public void validaEmail(Object value) throws Exception {
        String regex = "^[a-zA-Z0-9]+[a-zA-Z0-9_.-]+@{1}[a-zA-Z0-9_.-]*\\.+[a-z]{2,4}";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher((String) value);
        if (!m.matches()) {
            JOptionPane.showMessageDialog(null, "Informe um email válido", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void consultaUsuarios() {
        DefaultTableModel modelo = (DefaultTableModel) jTableUsuarios.getModel();
        modelo.setNumRows(0);
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PRIVADA));
            final PrivateKey chavePrivada = (PrivateKey) inputStream.readObject();
            UsuariosDAO dao = new UsuariosDAO();
            for (Usuarios u : dao.consulta()) {
                modelo.addRow(new Object[]{
                    u.getId(),
                    u.getIdentificador(),
                    u.getNome(),
                    new String(RSA.decriptografa(u.getEmail(), chavePrivada)),
                    u.getSenha(),
                    new String(RSA.decriptografa(u.getTelefone(), chavePrivada)),});
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Altera.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void alteraUsuarioComSenha() {
        Sha256 sj = new Sha256();

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PUBLICA));
            final PublicKey chavePublica = (PublicKey) inputStream.readObject();
            Usuarios u = new Usuarios();
            UsuariosDAO dao = new UsuariosDAO();
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
            u.setId((int) jTableUsuarios.getValueAt((jTableUsuarios.getSelectedRow()), 0));

            if (utils.ConfirmaSenha.confirmaSenha()) {
                dao.alteraComSenha(u);
            } else {
                JOptionPane.showMessageDialog(null, "Nenhuma alteração foi feita ao banco de dados");
            }

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Altera.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void alteraUsuario() {
        Sha256 sj = new Sha256();

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PUBLICA));
            final PublicKey chavePublica = (PublicKey) inputStream.readObject();
            Usuarios u = new Usuarios();
            UsuariosDAO dao = new UsuariosDAO();
            String identificador = "";
            if (jRadioButtonGerente.isSelected() == true) {
                identificador = "gerente";
            } else if (jRadioButtonComum.isSelected() == true) {
                identificador = "comum";
            }
            u.setIdentificador(identificador);
            u.setNome(jTextFieldNome.getText());
            u.setEmail(RSA.criptografa(jTextFieldEmail.getText(), chavePublica));
            u.setTelefone(RSA.criptografa(jFormattedTextFieldTelefone.getText(), chavePublica));
            u.setId((int) jTableUsuarios.getValueAt((jTableUsuarios.getSelectedRow()), 0));

            if (utils.ConfirmaSenha.confirmaSenha()) {
                dao.altera(u);
            } else {
                JOptionPane.showMessageDialog(null, "Nenhuma alteração foi feita ao banco de dados");
            }

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Altera.class.getName()).log(Level.SEVERE, null, ex);
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
        jLabel2 = new javax.swing.JLabel();
        jRadioButtonGerente = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jRadioButtonComum = new javax.swing.JRadioButton();
        jTextFieldNome = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPasswordFieldSenha = new javax.swing.JPasswordField();
        jButtonLimpaCampos = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jFormattedTextFieldTelefone = new javax.swing.JFormattedTextField();
        jTextFieldEmail = new javax.swing.JTextField();
        jButtonAltera = new javax.swing.JButton();
        jButtonConsulta = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableUsuarios = new javax.swing.JTable();
        jCheckBoxAlteraSenha = new javax.swing.JCheckBox();

        setClosable(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Alteração de Cadastro"));

        jLabel2.setText("Nome");

        buttonGroupTipoUsuario.add(jRadioButtonGerente);
        jRadioButtonGerente.setText("Gerente");
        jRadioButtonGerente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jRadioButtonGerenteFocusLost(evt);
            }
        });
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

        jPasswordFieldSenha.setEnabled(false);

        jButtonLimpaCampos.setText("Liompar Campos");
        jButtonLimpaCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLimpaCamposActionPerformed(evt);
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

        jButtonAltera.setText("Altera");
        jButtonAltera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlteraActionPerformed(evt);
            }
        });

        jButtonConsulta.setText("Consultar");
        jButtonConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConsultaActionPerformed(evt);
            }
        });

        jTableUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "identificador", "nome", "email", "senha", "telefone"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableUsuarios);

        jCheckBoxAlteraSenha.setText("Alterar a senha?");
        jCheckBoxAlteraSenha.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxAlteraSenhaItemStateChanged(evt);
            }
        });
        jCheckBoxAlteraSenha.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCheckBoxAlteraSenhaPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 702, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jButtonLimpaCampos)
                                    .addGap(11, 11, 11)
                                    .addComponent(jButtonConsulta)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButtonAltera))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jFormattedTextFieldTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel4))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jPasswordFieldSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jCheckBoxAlteraSenha)))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addGap(21, 21, 21)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jRadioButtonComum)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jRadioButtonGerente)))))))
                .addGap(20, 20, 20))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextFieldEmail, jTextFieldNome});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jRadioButtonGerente)
                    .addComponent(jRadioButtonComum))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordFieldSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jCheckBoxAlteraSenha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFormattedTextFieldTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonLimpaCampos)
                    .addComponent(jButtonConsulta)
                    .addComponent(jButtonAltera))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void jFormattedTextFieldTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextFieldTelefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextFieldTelefoneActionPerformed

    private void jTextFieldEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldEmailFocusLost
        try {
            validaEmail(jTextFieldEmail.getText());
        } catch (Exception ex) {
            Logger.getLogger(Altera.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextFieldEmailFocusLost

    private void jButtonAlteraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlteraActionPerformed
        if (jTableUsuarios.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um item");
        } else {
            if (jCheckBoxAlteraSenha.isSelected() == true) {
                alteraUsuarioComSenha();
                limpaCampos();
                consultaUsuarios();
            } else if (jCheckBoxAlteraSenha.isSelected() == false) {
                alteraUsuario();
                limpaCampos();
                consultaUsuarios();
            }
        }
    }//GEN-LAST:event_jButtonAlteraActionPerformed

    private void jButtonConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultaActionPerformed
        consultaUsuarios();
    }//GEN-LAST:event_jButtonConsultaActionPerformed

    private void jRadioButtonGerenteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jRadioButtonGerenteFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonGerenteFocusLost

    private void jTableUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableUsuariosMouseClicked
        if (jTableUsuarios.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um item");
        } else {
            if (jTableUsuarios.getValueAt((jTableUsuarios.getSelectedRow()), 1).toString().equals("gerente")) {
                jRadioButtonGerente.setSelected(true);
            } else if (jTableUsuarios.getValueAt((jTableUsuarios.getSelectedRow()), 1).toString().equals("comum")) {
                jRadioButtonComum.setSelected(true);
            }
            jTextFieldNome.setText(jTableUsuarios.getValueAt((jTableUsuarios.getSelectedRow()), 2).toString());
            jTextFieldEmail.setText(jTableUsuarios.getValueAt((jTableUsuarios.getSelectedRow()), 3).toString());
            jFormattedTextFieldTelefone.setText(jTableUsuarios.getValueAt((jTableUsuarios.getSelectedRow()), 5).toString());
        }
    }//GEN-LAST:event_jTableUsuariosMouseClicked

    private void jCheckBoxAlteraSenhaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCheckBoxAlteraSenhaPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxAlteraSenhaPropertyChange

    private void jCheckBoxAlteraSenhaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxAlteraSenhaItemStateChanged
        jPasswordFieldSenha.setEnabled(jCheckBoxAlteraSenha.isSelected());
    }//GEN-LAST:event_jCheckBoxAlteraSenhaItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupTipoUsuario;
    private javax.swing.JButton jButtonAltera;
    private javax.swing.JButton jButtonConsulta;
    private javax.swing.JButton jButtonLimpaCampos;
    private javax.swing.JCheckBox jCheckBoxAlteraSenha;
    private javax.swing.JFormattedTextField jFormattedTextFieldTelefone;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordFieldSenha;
    private javax.swing.JRadioButton jRadioButtonComum;
    private javax.swing.JRadioButton jRadioButtonGerente;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableUsuarios;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldNome;
    // End of variables declaration//GEN-END:variables
}
