/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.beans;

//import br.com.bb.exxi.cartaauditoria_nova.dao.AuxFuncionarioAtacadoDAO;
import br.com.bb.exxi.cartaauditoria_nova.dao.EstatisticaDAO;
import br.com.bb.exxi.cartaauditoria_nova.entidades.AuxEstatFunci;
import br.com.bb.exxi.cartaauditoria_nova.entidades.AuxEstatistica;
//import br.com.bb.exxi.cartaauditoria_nova.entidades.AuxFuncionarioAtacado;
import br.com.bb.exxi.cartaauditoria_nova.entidades.Estatistica;
//import br.com.bb.exxi.cartaauditoria_nova.entidades.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.joda.time.LocalDate;

/**
 *
 * @author F9066619
 */
@ManagedBean
@SessionScoped
public class EstatisticaBean {
    
    private EstatisticaDAO estatisticaDAO;
//    private AuxFuncionarioAtacado funci;
    private Date dtCorrente, dt30;
    private List<Estatistica> visaoTotal, visao30;
    private Estatistica totalVisaoTotal, totalVisao30;
    private List<AuxEstatFunci> visaoFunci;
    private AuxEstatFunci totalVisaoFunci;
    
    @PostConstruct
    public void init() {
        estatisticaDAO = new EstatisticaDAO();
        dtCorrente = new LocalDate().minusDays(1).toDate();
        dt30 = new LocalDate().minusDays(30).toDate();
    }

    public void atualizar() {
        visaoTotal = new ArrayList<Estatistica>();
        visao30 = new ArrayList<Estatistica>();
        totalVisaoTotal = new Estatistica();
        totalVisao30 = new Estatistica();

        List<AuxEstatistica> listaMake = estatisticaDAO.listaMake();
        List<AuxEstatistica> listaAdd = estatisticaDAO.listaAdd();
        List<AuxEstatistica> listaWF = estatisticaDAO.listaWorkFlow();
        
        for (AuxEstatistica ae : listaMake){
            Estatistica e = new Estatistica();
            e.setCdPrf(ae.getCdDep());
            e.setNmPrf(ae.getNmDep());
            e.setVlCartasTotal(ae.getQtde());
            for(AuxEstatistica ae2: listaAdd){
                if(e.getCdPrf().equals(ae2.getCdDep())){
                    e.setVlCartasOffline(ae2.getQtde());
                    break;
                }
            }
            for(AuxEstatistica ae2 : listaWF){
                if(e.getCdPrf().equals(ae2.getCdDep())){
                    e.setVlDemandasWorkflow(ae2.getQtde());
                    break;
                }
            }
            totalVisaoTotal.setVlCartasTotal(totalVisaoTotal.getVlCartasTotal() + e.getVlCartasTotal());
            totalVisaoTotal.setVlCartasOffline(totalVisaoTotal.getVlCartasOffline() + e.getVlCartasOffline());
            totalVisaoTotal.setVlDemandasWorkflow(totalVisaoTotal.getVlDemandasWorkflow() + e.getVlDemandasWorkflow());
            visaoTotal.add(e);
        }

        List<AuxEstatistica> listaMake30 = estatisticaDAO.listaMake30();
        List<AuxEstatistica> listaAdd30 = estatisticaDAO.listaAdd30();
        List<AuxEstatistica> listaWF30 = estatisticaDAO.listaWorkFlow30();
        
        for (AuxEstatistica ae : listaMake30){
            Estatistica e = new Estatistica();
            e.setCdPrf(ae.getCdDep());
            e.setNmPrf(ae.getNmDep());
            e.setVlCartasTotal(ae.getQtde());
            for(AuxEstatistica ae2: listaAdd30){
                if(e.getCdPrf().equals(ae2.getCdDep())){
                    e.setVlCartasOffline(ae2.getQtde());
                    break;
                }
            }
            for(AuxEstatistica ae2 : listaWF30){
                if(e.getCdPrf().equals(ae2.getCdDep())){
                    e.setVlDemandasWorkflow(ae2.getQtde());
                    break;
                }
            }
            totalVisao30.setVlCartasTotal(totalVisao30.getVlCartasTotal() + e.getVlCartasTotal());
            totalVisao30.setVlCartasOffline(totalVisao30.getVlCartasOffline() + e.getVlCartasOffline());
            totalVisao30.setVlDemandasWorkflow(totalVisao30.getVlDemandasWorkflow() + e.getVlDemandasWorkflow());
            visao30.add(e);
        }
        
        for(Estatistica e : visaoTotal){
            e.setPercTotalCartas(e.getVlCartasTotal()*1.0 / totalVisaoTotal.getVlCartasTotal());
        }
        for(Estatistica e : visao30){
            e.setPercTotalCartas(e.getVlCartasTotal()*1.0 / totalVisao30.getVlCartasTotal());
        }
        
        visaoFunci = estatisticaDAO.listaFunci();
        totalVisaoFunci = estatisticaDAO.sumListaFunci();
       
    }

//    public void autenticacao(Usuario u) {
//        this.funci = new AuxFuncionarioAtacadoDAO().lerFunci(u.getChave());
//        atualizar();
//    }
    
    public Date getDtCorrente() {
        return dtCorrente;
    }

    public void setDtCorrente(Date dtCorrente) {
        this.dtCorrente = dtCorrente;
    }

    public Date getDt30() {
        return dt30;
    }

    public void setDt30(Date dt30) {
        this.dt30 = dt30;
    }

    public List<Estatistica> getVisaoTotal() {
        return visaoTotal;
    }

    public void setVisaoTotal(List<Estatistica> visaoTotal) {
        this.visaoTotal = visaoTotal;
    }

    public List<Estatistica> getVisao30() {
        return visao30;
    }

    public void setVisao30(List<Estatistica> visao30) {
        this.visao30 = visao30;
    }

    public Estatistica getTotalVisaoTotal() {
        return totalVisaoTotal;
    }

    public void setTotalVisaoTotal(Estatistica totalVisaoTotal) {
        this.totalVisaoTotal = totalVisaoTotal;
    }

    public Estatistica getTotalVisao30() {
        return totalVisao30;
    }

    public void setTotalVisao30(Estatistica totalVisao30) {
        this.totalVisao30 = totalVisao30;
    }

//    public AuxFuncionarioAtacado getFunci() {
//        return funci;
//    }

//    public void setFunci(AuxFuncionarioAtacado funci) {
//        this.funci = funci;
//    }

    public List<AuxEstatFunci> getVisaoFunci() {
        return visaoFunci;
    }

    public void setVisaoFunci(List<AuxEstatFunci> visaoFunci) {
        this.visaoFunci = visaoFunci;
    }

    public AuxEstatFunci getTotalVisaoFunci() {
        return totalVisaoFunci;
    }

    public void setTotalVisaoFunci(AuxEstatFunci totalVisaoFunci) {
        this.totalVisaoFunci = totalVisaoFunci;
    }

}
