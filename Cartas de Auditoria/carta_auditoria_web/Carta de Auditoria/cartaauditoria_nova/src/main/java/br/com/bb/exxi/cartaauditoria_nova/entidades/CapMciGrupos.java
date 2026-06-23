/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 *
 * @author F9066619
 */
@Entity
//@Table(name = "tb_grupos_econ_mcis", schema="cap")
@Table(name = "tb_grupos_econ_mcis", schema="cartaauditoria")
public class CapMciGrupos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "cod_mci")
    private int cdMci;

    @Column(name = "razao_social")
    private String rzSocial;
    
    @Column(name = "cnpj")
    private String cnpj;
    
    @Column(name = "cnpj_rad")
    private Integer cnpjRad;
    
    @Column(name = "ancora")
    private String ancora;
    
    @Column(name = "cod_grupo")
    private Integer cdGrupo;
    
    @Column(name = "nm_grupo")
    private String nmGrupo;
    
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name = "pref_encarteirado", referencedColumnName = "cod_pref_dep")
    @ManyToOne
    private AuxDependencia agencia;

    public CapMciGrupos(){
    }

    public CapMciGrupos(int cdMci, String rzSocial, String cnpj, Integer cnpjRad, AuxDependencia agencia) {
        super();
        this.cdMci = cdMci;
        this.rzSocial = rzSocial;
        this.cnpj = cnpj;
        this.cnpjRad = cnpjRad;
        this.agencia = agencia;
    }

    public CapMciGrupos(int cdMci, String rzSocial, String cnpj, Integer cnpjRad, Integer cdDep, String nmDep, Integer cdSuper, Integer cdSureg, Integer cdCsa) {
        super();
        this.cdMci = cdMci;
        this.rzSocial = rzSocial;
        this.cnpj = cnpj;
        this.cnpjRad = cnpjRad;
        this.agencia = new AuxDependencia(cdDep, nmDep, cdSuper, cdSureg, cdCsa);
    }

    public CapMciGrupos(Integer cdGrupo, String nmGrupo, AuxDependencia agencia) {
        super();
        this.cdGrupo = cdGrupo;
        this.nmGrupo = nmGrupo;
        this.agencia = agencia;
    }
    
    public CapMciGrupos(Integer cdGrupo, String nmGrupo, Integer cdDep, String nmDep, Integer cdSuper, Integer cdSureg, Integer cdCsa) {
        super();
        this.cdGrupo = cdGrupo;
        this.nmGrupo = nmGrupo;
        this.agencia = new AuxDependencia(cdDep, nmDep, cdSuper, cdSureg, cdCsa);
    }

    public CapMciGrupos(Integer cdGrupo, String nmGrupo) {
        super();
        this.cdGrupo = cdGrupo;
        this.nmGrupo = nmGrupo;
    }

    public int getCdMci() {
        return cdMci;
    }

    public void setCdMci(int cdMci) {
        this.cdMci = cdMci;
    }

    public String getRzSocial() {
        return rzSocial;
    }

    public void setRzSocial(String rzSocial) {
        this.rzSocial = rzSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Integer getCnpjRad() {
        return cnpjRad;
    }

    public void setCnpjRad(Integer cnpjRad) {
        this.cnpjRad = cnpjRad;
    }

    public String getAncora() {
        return ancora;
    }

    public void setAncora(String ancora) {
        this.ancora = ancora;
    }

    public AuxDependencia getAgencia() {
        return agencia;
    }

    public void setAgencia(AuxDependencia agencia) {
        this.agencia = agencia;
    }

    public Integer getCdGrupo() {
        return cdGrupo;
    }

    public void setCdGrupo(Integer cdGrupo) {
        this.cdGrupo = cdGrupo;
    }

    public String getNmGrupo() {
        return nmGrupo;
    }

    public void setNmGrupo(String nmGrupo) {
        this.nmGrupo = nmGrupo;
    }

}
