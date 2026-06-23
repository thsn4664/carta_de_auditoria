/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 *
 * @author F9066619
 */
@Entity
@Table(name = "tbcap_mci", schema="cap")
public class CapMci implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "cod_mci")
    private int cdMci;

    @Column(name = "razao_social")
    private String rzSocial;
    
    @Column(name = "tp_cadastro")
    private String tpCadastro;
    
    @Column(name = "sit_cadastro")
    private String sitCadastro;
    
    @Column(name = "nat_juridica")
    private String natJur;
    
    @Column(name = "sit_funcionamento")
    private String sitFunc;
    
    @Column(name = "ok_baixa_cdc")
    private String okBaixaCdc;
    
    @Column(name = "ok_autorizacao_scr")
    private String okScr;
    
    @Column(name = "anot_imped")
    private String okAnotImped;
    
    @Column(name = "cnpj")
    private String cnpj;
    
    @Column(name = "dt_ref")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dtRef;
    
    @Column(name = "cod_grp_econ")
    private Integer cdGrupo;

    @Column(name = "nm_grp_econ")
    private String nmGrupo;

    @Column(name = "cod_atv_econ")
    private Integer cdAtvEcon;

    @Column(name = "nm_atv_econ")
    private String nmAtvEcon;

    @Column(name = "vl_faturamento")
    private Double vlFat;

    @Column(name = "ano_mes_faturamento")
    private String mesFat;
    
    @Column(name = "cod_carteira")
    private Integer cdCarteira;
    
    @Column(name = "nm_carteira")
    private String nmCarteira;
    
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name = "cod_pref_dep", referencedColumnName = "cod_pref_dep")
    @ManyToOne
    private AuxDependencia agencia;

    @Column(name = "cod_restrito")
    private Integer cdRestrito;
    
    @Column(name = "tp_pessoa")
    private Integer tpPessoa;
    
    public CapMci(){
    }

    public CapMci(int cdMci, String rzSocial, String cnpj, Integer cdDep, String nmDep, Integer cdSuper, Integer cdSureg, Integer cdCsa) {
        super();
        this.cdMci = cdMci;
        this.rzSocial = rzSocial;
        this.cnpj = cnpj;
        this.agencia = new AuxDependencia(cdDep, nmDep, cdSuper, cdSureg, cdCsa);
    }

    public CapMci(int cdMci, String rzSocial, String cnpj, Integer cdDep, String nmDep, Integer cdSuper, Integer cdSureg, Integer cdCsa, Integer cdRestrito, Integer tpPessoa) {
        super();
        this.cdMci = cdMci;
        this.rzSocial = rzSocial;
        this.cnpj = cnpj;
        this.agencia = new AuxDependencia(cdDep, nmDep, cdSuper, cdSureg, cdCsa);
        this.cdRestrito = cdRestrito;
        this.tpPessoa = tpPessoa;
    }

    public CapMci(int cdMci, String rzSocial, String tpCadastro, String sitCadastro, String natJur, String sitFunc, String okBaixaCdc, String okScr, String okAnotImped, Integer cdDep, String nmDep, String cnpj, Date dtRef, Integer cdGrupo, String nmGrupo, Integer cdAtvEcon, String nmAtvEcon, Double vlFat, String mesFat, Integer cdTpCarteira, String nmTpCarteira, Integer cdCarteira, String matricula, String nmGerel) {
        super();
        this.cdMci = cdMci;
        this.rzSocial = rzSocial;
        this.tpCadastro = tpCadastro;
        this.sitCadastro = sitCadastro;
        this.natJur = natJur;
        this.sitFunc = sitFunc;
        this.okBaixaCdc = okBaixaCdc;
        this.okScr = okScr;
        this.okAnotImped = okAnotImped;
        this.cnpj = cnpj;
        this.dtRef = dtRef;
        this.cdGrupo = cdGrupo;
        this.nmGrupo = nmGrupo;
        this.cdAtvEcon = cdAtvEcon;
        this.nmAtvEcon = nmAtvEcon;
        this.vlFat = vlFat;
        this.mesFat = mesFat;
        this.cdCarteira = cdCarteira;
        this.agencia = new AuxDependencia(cdDep, nmDep);
    }

    public CapMci(int cdMci, String rzSocial, String tpCadastro, String sitCadastro, String natJur, String sitFunc, String okBaixaCdc, String okScr, String okAnotImped, Integer cdDep, String nmDep, Integer cdCsa, String cnpj, Date dtRef, Integer cdGrupo, String nmGrupo, Integer cdAtvEcon, String nmAtvEcon, Double vlFat, String mesFat, Integer cdTpCarteira, String nmTpCarteira, Integer cdCarteira, String matricula, String nmGerel) {
        super();
        this.cdMci = cdMci;
        this.rzSocial = rzSocial;
        this.tpCadastro = tpCadastro;
        this.sitCadastro = sitCadastro;
        this.natJur = natJur;
        this.sitFunc = sitFunc;
        this.okBaixaCdc = okBaixaCdc;
        this.okScr = okScr;
        this.okAnotImped = okAnotImped;
        this.cnpj = cnpj;
        this.dtRef = dtRef;
        this.cdGrupo = cdGrupo;
        this.nmGrupo = nmGrupo;
        this.cdAtvEcon = cdAtvEcon;
        this.nmAtvEcon = nmAtvEcon;
        this.vlFat = vlFat;
        this.mesFat = mesFat;
        this.cdCarteira = cdCarteira;
        this.agencia = new AuxDependencia(cdDep, nmDep, cdCsa);
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

    public String getTpCadastro() {
        return tpCadastro;
    }

    public void setTpCadastro(String tpCadastro) {
        this.tpCadastro = tpCadastro;
    }

    public String getSitCadastro() {
        return sitCadastro;
    }

    public void setSitCadastro(String sitCadastro) {
        this.sitCadastro = sitCadastro;
    }

    public String getNatJur() {
        return natJur;
    }

    public void setNatJur(String natJur) {
        this.natJur = natJur;
    }

    public String getSitFunc() {
        return sitFunc;
    }

    public void setSitFunc(String sitFunc) {
        this.sitFunc = sitFunc;
    }

    public String getOkBaixaCdc() {
        return okBaixaCdc;
    }

    public void setOkBaixaCdc(String okBaixaCdc) {
        this.okBaixaCdc = okBaixaCdc;
    }

    public String getOkScr() {
        return okScr;
    }

    public void setOkScr(String okScr) {
        this.okScr = okScr;
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

    public Integer getCdAtvEcon() {
        return cdAtvEcon;
    }

    public void setCdAtvEcon(Integer cdAtvEcon) {
        this.cdAtvEcon = cdAtvEcon;
    }

    public String getNmAtvEcon() {
        return nmAtvEcon;
    }

    public void setNmAtvEcon(String nmAtvEcon) {
        this.nmAtvEcon = nmAtvEcon;
    }

    public Double getVlFat() {
        return vlFat;
    }

    public void setVlFat(Double vlFat) {
        this.vlFat = vlFat;
    }

    public String getMesFat() {
        return mesFat;
    }

    public void setMesFat(String mesFat) {
        this.mesFat = mesFat;
    }

    public Date getDtRef() {
        return dtRef;
    }

    public void setDtRef(Date dtRef) {
        this.dtRef = dtRef;
    }

    public Integer getCdCarteira() {
        return cdCarteira;
    }

    public void setCdCarteira(Integer cdCarteira) {
        this.cdCarteira = cdCarteira;
    }

    public AuxDependencia getAgencia() {
        return agencia;
    }

    public void setAgencia(AuxDependencia agencia) {
        this.agencia = agencia;
    }

    public String getNmCarteira() {
        return nmCarteira;
    }

    public void setNmCarteira(String nmCarteira) {
        this.nmCarteira = nmCarteira;
    }

    public String getOkAnotImped() {
        return okAnotImped;
    }

    public void setOkAnotImped(String okAnotImped) {
        this.okAnotImped = okAnotImped;
    }

    public Integer getCdRestrito() {
        return cdRestrito;
    }

    public void setCdRestrito(Integer cdRestrito) {
        this.cdRestrito = cdRestrito;
    }

    public Integer getTpPessoa() {
        return tpPessoa;
    }

    public void setTpPessoa(Integer tpPessoa) {
        this.tpPessoa = tpPessoa;
    }
    
}
