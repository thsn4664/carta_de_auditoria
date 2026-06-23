/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.entidades;

/**
 *
 * @author F9066619
 */
public class AuxEstatFunci {
    
    private String matricula;
    private String nmFunci;
    private String nmFuncao;
    private Integer cdDep;
    private Integer qtdeAdd;
    private Integer qtdeDel;
    private Integer qtdeLook;
    private Integer qtdeMake;
    private Integer qtdeErr;
    private Integer qtdeNoAccess;
    private Integer qtdeForaJurisdicao;
    private Integer qtdeTotal;

    public AuxEstatFunci() {
    }

    public AuxEstatFunci(Integer qtdeAdd, Integer qtdeDel, Integer qtdeLook, Integer qtdeMake, Integer qtdeErr, Integer qtdeNoAccess, Integer qtdeForaJurisdicao) {
        super();
        this.qtdeAdd = qtdeAdd;
        this.qtdeDel = qtdeDel;
        this.qtdeLook = qtdeLook;
        this.qtdeMake = qtdeMake;
        this.qtdeErr = qtdeErr;
        this.qtdeNoAccess = qtdeNoAccess;
        this.qtdeForaJurisdicao = qtdeForaJurisdicao;
        atualizaTotal();
    }

    public AuxEstatFunci(String matricula, String nmFunci, String nmFuncao, Integer cdDep, Integer qtdeAdd, Integer qtdeDel, Integer qtdeLook, Integer qtdeMake, Integer qtdeErr, Integer qtdeNoAccess, Integer qtdeForaJurisdicao) {
        super();
        this.matricula = matricula;
        this.nmFunci = nmFunci;
        this.nmFuncao = nmFuncao;
        this.cdDep = cdDep;
        this.qtdeAdd = qtdeAdd;
        this.qtdeDel = qtdeDel;
        this.qtdeLook = qtdeLook;
        this.qtdeMake = qtdeMake;
        this.qtdeErr = qtdeErr;
        this.qtdeNoAccess = qtdeNoAccess;
        this.qtdeForaJurisdicao = qtdeForaJurisdicao;
        atualizaTotal();
    }

    private void atualizaTotal(){
        this.qtdeTotal = qtdeAdd + qtdeDel + qtdeLook + qtdeMake + qtdeErr + qtdeNoAccess + qtdeForaJurisdicao;
    }
    
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNmFunci() {
        return nmFunci;
    }

    public void setNmFunci(String nmFunci) {
        this.nmFunci = nmFunci;
    }

    public String getNmFuncao() {
        return nmFuncao;
    }

    public void setNmFuncao(String nmFuncao) {
        this.nmFuncao = nmFuncao;
    }

    public Integer getCdDep() {
        return cdDep;
    }

    public void setCdDep(Integer cdDep) {
        this.cdDep = cdDep;
    }

    public Integer getQtdeAdd() {
        return qtdeAdd;
    }

    public void setQtdeAdd(Integer qtdeAdd) {
        this.qtdeAdd = qtdeAdd;
    }

    public Integer getQtdeDel() {
        return qtdeDel;
    }

    public void setQtdeDel(Integer qtdeDel) {
        this.qtdeDel = qtdeDel;
    }

    public Integer getQtdeLook() {
        return qtdeLook;
    }

    public void setQtdeLook(Integer qtdeLook) {
        this.qtdeLook = qtdeLook;
    }

    public Integer getQtdeMake() {
        return qtdeMake;
    }

    public void setQtdeMake(Integer qtdeMake) {
        this.qtdeMake = qtdeMake;
    }

    public Integer getQtdeErr() {
        return qtdeErr;
    }

    public void setQtdeErr(Integer qtdeErr) {
        this.qtdeErr = qtdeErr;
    }

    public Integer getQtdeNoAccess() {
        return qtdeNoAccess;
    }

    public void setQtdeNoAccess(Integer qtdeNoAccess) {
        this.qtdeNoAccess = qtdeNoAccess;
    }

    public Integer getQtdeForaJurisdicao() {
        return qtdeForaJurisdicao;
    }

    public void setQtdeForaJurisdicao(Integer qtdeForaJurisdicao) {
        this.qtdeForaJurisdicao = qtdeForaJurisdicao;
    }

    public Integer getQtdeTotal() {
        return qtdeTotal;
    }

    public void setQtdeTotal(Integer qtdeTotal) {
        this.qtdeTotal = qtdeTotal;
    }

}
