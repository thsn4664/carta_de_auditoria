/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.entidades;

/**
 *
 * @author F9066619
 */
public class AuxEstatistica {
    
    private Integer cdDep;
    private String nmDep;
    private Integer qtde;

    public AuxEstatistica() {
    }

    public AuxEstatistica(Integer cdDep, Integer qtde) {
        this.cdDep = cdDep;
        this.qtde = qtde;
    }

    public AuxEstatistica(Integer cdDep, String nmDep, Integer qtde) {
        this.cdDep = cdDep;
        this.nmDep = nmDep;
        this.qtde = qtde;
    }

    public Integer getCdDep() {
        return cdDep;
    }

    public void setCdDep(Integer cdDep) {
        this.cdDep = cdDep;
    }

    public String getNmDep() {
        return nmDep;
    }

    public void setNmDep(String nmDep) {
        this.nmDep = nmDep;
    }

    public Integer getQtde() {
        return qtde;
    }

    public void setQtde(Integer qtde) {
        this.qtde = qtde;
    }
}
