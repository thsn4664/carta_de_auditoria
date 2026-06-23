/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.dao;

/**
 *
 * @author F9066619
 */
public class ConstantesConexao {
    
    //public static final String ipConexao = "172.29.14.96:6432";
    public static final String ipConexao = "10.2.98.39:5432";
    public static final String nomeBD = "ferramentas";
    public static final String userLogin = "<xX_SUPRIMIDO_Xx>";
    public static final String userPassword = "<xX_SUPRIMIDO_Xx>";
    public static final String conexao = "jdbc:postgresql://"+ ipConexao + "/" + nomeBD;
    
    public ConstantesConexao(){
        throw new AssertionError();
    }
    
}
