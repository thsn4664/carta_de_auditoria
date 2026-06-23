/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.dao;

/**
 *
 * @author F9066619
 */
public class ConstantesConexao2 {
    
    public static final String ipConexao = "172.29.14.99:6432";
    public static final String nomeBD = "ferramentas";
    public static final String userLogin = "portal";
    public static final String userPassword = "p0rtal:{DICOM}";
    public static final String conexao = "jdbc:postgresql://"+ ipConexao + "/" + nomeBD;
    
    public ConstantesConexao2(){
        throw new AssertionError();
    }
    
}
