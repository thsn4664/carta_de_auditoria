/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.entidades;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author F9066619
 */
@Entity
@Table(name = "outorgados", schema = "cartaauditoria")
public class Outorgados implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_outorgado")
    private Integer id;

    @Column(name = "cd_cli")
    private int cdCli;
    @Column(name = "dt_base")
    @Temporal(TemporalType.DATE)
    private Date dtBase;
    @Column(name = "cd_cli_otgdo")
    private int cdCliOtgdo;
    @Column(name = "nm_otgdo")
    private String nmOtgdo;
    @Column(name = "tp_pessoa")
    private String tpPessoa;
    @Column(name = "cpf_cnpj_otgdo")
    private String cpfCnpjOtgdo;
    @Column(name = "sum_tipo_doc")
    private Integer sumTipoDoc;

    public Outorgados() {
    }

    public Outorgados(Date dtBase, int cdCli, int cdCliOtgdo, String nmOtgdo, String tpPessoa, String cpfCnpjOtgdo) {
        this.cdCli = cdCli;
        this.dtBase = dtBase;
        this.cdCliOtgdo = cdCliOtgdo;
        this.nmOtgdo = nmOtgdo;
        this.tpPessoa = tpPessoa;
        this.cpfCnpjOtgdo = cpfCnpjOtgdo;
    }

    public Outorgados(Date dtBase, int cdCli, int cdCliOtgdo, String nmOtgdo, String tpPessoa, String cpfCnpjOtgdo, Integer sumTipoDoc) {
        this.cdCli = cdCli;
        this.dtBase = dtBase;
        this.cdCliOtgdo = cdCliOtgdo;
        this.nmOtgdo = nmOtgdo;
        this.tpPessoa = tpPessoa;
        this.cpfCnpjOtgdo = cpfCnpjOtgdo;
        this.sumTipoDoc = sumTipoDoc;
    }

    public int getCdCli() {
        return cdCli;
    }

    public void setCdCli(int cdCli) {
        this.cdCli = cdCli;
    }

    public Date getDtBase() {
        return dtBase;
    }

    public void setDtBase(Date dtBase) {
        this.dtBase = dtBase;
    }

    public int getCdCliOtgdo() {
        return cdCliOtgdo;
    }

    public void setCdCliOtgdo(int cdCliOtgdo) {
        this.cdCliOtgdo = cdCliOtgdo;
    }

    public String getNmOtgdo() {
        return nmOtgdo;
    }

    public void setNmOtgdo(String nmOtgdo) {
        this.nmOtgdo = nmOtgdo;
    }

    public String getTpPessoa() {
        return tpPessoa;
    }

    public void setTpPessoa(String tpPessoa) {
        this.tpPessoa = tpPessoa;
    }

    public String getCpfCnpjOtgdo() {
        return cpfCnpjOtgdo;
    }

    public void setCpfCnpjOtgdo(String cpfCnpjOtgdo) {
        this.cpfCnpjOtgdo = cpfCnpjOtgdo;
    }

    public Integer getSumTipoDoc() {
        return sumTipoDoc;
    }

    public void setSumTipoDoc(Integer sumTipoDoc) {
        this.sumTipoDoc = sumTipoDoc;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.cdCli;
        hash = 37 * hash + (this.dtBase != null ? this.dtBase.hashCode() : 0);
        hash = 37 * hash + this.cdCliOtgdo;
        hash = 37 * hash + (this.nmOtgdo != null ? this.nmOtgdo.hashCode() : 0);
        hash = 37 * hash + (this.tpPessoa != null ? this.tpPessoa.hashCode() : 0);
        hash = 37 * hash + (this.cpfCnpjOtgdo != null ? this.cpfCnpjOtgdo.hashCode() : 0);
        hash = 37 * hash + this.sumTipoDoc;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Outorgados other = (Outorgados) obj;
        if (this.cdCli != other.cdCli) {
            return false;
        }
        if (this.dtBase != other.dtBase && (this.dtBase == null || !this.dtBase.equals(other.dtBase))) {
            return false;
        }
        if (this.cdCliOtgdo != other.cdCliOtgdo) {
            return false;
        }
        if ((this.nmOtgdo == null) ? (other.nmOtgdo != null) : !this.nmOtgdo.equals(other.nmOtgdo)) {
            return false;
        }
        if ((this.tpPessoa == null) ? (other.tpPessoa != null) : !this.tpPessoa.equals(other.tpPessoa)) {
            return false;
        }
        if ((this.cpfCnpjOtgdo == null) ? (other.cpfCnpjOtgdo != null) : !this.cpfCnpjOtgdo.equals(other.cpfCnpjOtgdo)) {
            return false;
        }
        if (this.sumTipoDoc != other.sumTipoDoc) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "'" + new SimpleDateFormat("yyyy-MM-dd").format(dtBase) + "'," + cdCli + "," + cdCliOtgdo + ",'" + nmOtgdo + "','" + tpPessoa + "','" + cpfCnpjOtgdo + "'," + sumTipoDoc + "";
    }

}
