/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import criptografia.RSA;
import static criptografia.RSA.PATH_CHAVE_PRIVADA;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.PrivateKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.bean.Sessao;
import model.bean.Usuarios;
import model.dao.UsuariosDAO;

/**
 *
 * @author Jefferson Bruno de Assis Lima José Cláudio de Figueiredo Filho
 * Rodrigo Ferreira Rodrigues
 *
 */
public class Consulta extends javax.swing.JInternalFrame {

    /**
     * Creates new form consulta
     */
    public Consulta() {
        initComponents();
        conexao.ConectaDB.verificaDB(); //Chama o método verificaDB da classe ConectaDB para validar se o banco está ok
        DefaultTableModel modelo = (DefaultTableModel) jTableUsuarios.getModel();
        jTableUsuarios.setRowSorter(new TableRowSorter(modelo));

        Sessao sessao = Sessao.getInstance();
        if (sessao.getUsuario().getIdentificador().equals("comum")) {
            jComboBoxTipoBusca.setVisible(false);
            jButtonBusca.setVisible(false);
            jTextFieldBusca.setVisible(false);
            jLabelUsuarioComum.setVisible(true);
        } else {
            jLabelUsuarioComum.setVisible(false);
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

    public void buscaPorNome(String nome) {
        DefaultTableModel modelo = (DefaultTableModel) jTableUsuarios.getModel();
        modelo.setNumRows(0);
        ObjectInputStream inputStream = null;
        UsuariosDAO dao = new UsuariosDAO();
        try {
            inputStream = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PRIVADA));
            final PrivateKey chavePrivada = (PrivateKey) inputStream.readObject();
            for (Usuarios u : dao.buscaNome(nome)) {
                modelo.addRow(new Object[]{
                    u.getId(),
                    u.getIdentificador(),
                    u.getNome(),
                    new String(RSA.decriptografa(u.getEmail(), chavePrivada)),
                    u.getSenha(),
                    new String(RSA.decriptografa(u.getTelefone(), chavePrivada)),});
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Consulta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Consulta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buscaPorIdentificador(String identificador) {
        DefaultTableModel modelo = (DefaultTableModel) jTableUsuarios.getModel();
        modelo.setNumRows(0);
        RSA rsa = new RSA();
        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PRIVADA));
            final PrivateKey chavePrivada = (PrivateKey) inputStream.readObject();

            UsuariosDAO dao = new UsuariosDAO();
            for (Usuarios u : dao.buscaIdentificador(identificador)) {
                modelo.addRow(new Object[]{
                    u.getId(),
                    u.getIdentificador(),
                    u.getNome(),
                    new String(RSA.decriptografa(u.getEmail(), chavePrivada)),
                    u.getSenha(),
                    new String(RSA.decriptografa(u.getTelefone(), chavePrivada)),});
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Consulta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Consulta.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButtonBusca = new javax.swing.JButton();
        jComboBoxTipoBusca = new javax.swing.JComboBox<>();
        jButtonConsulta = new javax.swing.JButton();
        jTextFieldBusca = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableUsuarios = new javax.swing.JTable();
        jLabelUsuarioComum = new javax.swing.JLabel();

        setClosable(true);
        setMaximizable(true);

        jLabel1.setText("Consulta ao Banco de dados");

        jButtonBusca.setText("Buscar");
        jButtonBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscaActionPerformed(evt);
            }
        });

        jComboBoxTipoBusca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione o Tipo da Busca", "por Nome", "por Identificador" }));

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
        jScrollPane1.setViewportView(jTableUsuarios);

        jLabelUsuarioComum.setForeground(new java.awt.Color(255, 0, 51));
        jLabelUsuarioComum.setText("Como você está logado como usuário comum, a busca só retorna o seu prórpio usuário");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 10, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonConsulta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxTipoBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonBusca)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 702, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelUsuarioComum)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelUsuarioComum))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonConsulta)
                    .addComponent(jTextFieldBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxTipoBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonBusca))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultaActionPerformed
        consultaUsuarios();
    }//GEN-LAST:event_jButtonConsultaActionPerformed

    private void jButtonBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscaActionPerformed
        //buscaUsuarios(jTextFieldBusca.getText());
        int opcaoBusca = jComboBoxTipoBusca.getSelectedIndex();
        switch (opcaoBusca) {
            case 0:
                JOptionPane.showMessageDialog(null, "Escolha uma opção");
                break;
            case 1:
                buscaPorNome(jTextFieldBusca.getText());
                break;
            case 2:
                buscaPorIdentificador(jTextFieldBusca.getText());
                break;
        }
    }//GEN-LAST:event_jButtonBuscaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBusca;
    private javax.swing.JButton jButtonConsulta;
    private javax.swing.JComboBox<String> jComboBoxTipoBusca;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelUsuarioComum;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableUsuarios;
    public javax.swing.JTextField jTextFieldBusca;
    // End of variables declaration//GEN-END:variables

}
