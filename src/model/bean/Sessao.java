/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean;

/**
 *
 * @author Jefferson Bruno de Assis Lima, José Cláudio de Figueiredo Filh e
 * Rodrigo Ferreira Rodrigues.
 *
 * Essa classe é usada para gravar o usuário logado, podendo então ser
 * consultado de qualquer parte do sistema para permitir ou bloquear os acessos
 * de "gerente" e "comum"
 * 
 */

public class Sessao {

    private static Sessao instance = null;
    private Usuarios usuario;

    public Sessao() {
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public static Sessao getInstance() {
        if (instance == null) {
            instance = new Sessao();
        }
        return instance;
    }
}
