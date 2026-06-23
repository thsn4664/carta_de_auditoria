/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.beans;

import br.com.bb.exxi.cartaauditoria_nova.util.Funcoes;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author F9066619
 */
@ManagedBean
@SessionScoped
public class AuxiliarBean {

    private static final String ZEROS = "0000000000000000000000";

    @PostConstruct
    public void init() {
    }

    public String diaHora(Date dt) {
        if (dt == null) {
            return "";
        }
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dt.getTime());
    }

    public String hora(Date dt) {
        if (dt == null) {
            return "";
        }
        return new SimpleDateFormat("HH:mm").format(dt);
    }

    public String maxString(String txt, int tam) {
        if (txt == null) {
            return "";
        }
        if (txt.length() > tam) {
            return txt.substring(0, tam);
        }
        return txt;
    }

    public String valor(double vl, int decimais) {
        return new Funcoes().valor(vl, decimais);
    }

    public String verCpf(String cpf) {
        cpf = cpf.trim();
        if (cpf.length() < 11) {
            cpf = ZEROS.substring(0, 11 - cpf.length()) + cpf;
        }
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "."
                + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
    }

    public String verCnpj(String cnpj) {
        cnpj = cnpj.trim();
        if (cnpj.length() < 14) {
            cnpj = ZEROS.substring(0, 14 - cnpj.length()) + cnpj;
        }
        return cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "."
                + cnpj.substring(5, 8) + "/" + cnpj.substring(8, 12) + "-"
                + cnpj.substring(12, 14);
    }

    public String mesAno(int mes) {
        return new Funcoes().mesAno("" + mes);
    }

    public String mesAno(String mes) {
        return new Funcoes().mesAno(mes);
    }

    public String dia(Date dt) {
        if (dt == null) {
            return "";
        }
        return new SimpleDateFormat("dd/MM/yyyy").format(dt);
    }

}
