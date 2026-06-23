/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.beans;

import br.com.bb.exxi.cartaauditoria_nova.util.DataFormatada;
import br.com.bb.exxi.cartaauditoria_nova.dao.AcessoDAO;
import br.com.bb.exxi.cartaauditoria_nova.dao.AtualizacoesDAO;
import br.com.bb.exxi.cartaauditoria_nova.dao.AuxDependenciaDAO;
import br.com.bb.exxi.cartaauditoria_nova.dao.AuxEnderecoDepDAO;
import br.com.bb.exxi.cartaauditoria_nova.dao.AuxFuncaoDAO;
import br.com.bb.exxi.cartaauditoria_nova.dao.AuxFuncionarioAtacadoDAO;
import br.com.bb.exxi.cartaauditoria_nova.dao.CapMciDAO;
import br.com.bb.exxi.cartaauditoria_nova.dao.CapMciGruposDAO;
import br.com.bb.exxi.cartaauditoria_nova.dao.ClientesEnderecoDAO;
import br.com.bb.exxi.cartaauditoria_nova.dao.LogPedidosDAO;
import br.com.bb.exxi.cartaauditoria_nova.dao.OutorgadosDAO;
import br.com.bb.exxi.cartaauditoria_nova.dao.PedidosDAO;
import br.com.bb.exxi.cartaauditoria_nova.dao.PedidosLoteDAO;
import br.com.bb.exxi.cartaauditoria_nova.dao.ResultadosDAO;
import br.com.bb.exxi.cartaauditoria_nova.dao.SwapDAO;
import br.com.bb.exxi.cartaauditoria_nova.dao.VwResultadosDAO;
import br.com.bb.exxi.cartaauditoria_nova.entidades.Acesso;
import br.com.bb.exxi.cartaauditoria_nova.entidades.Atualizacoes;
import br.com.bb.exxi.cartaauditoria_nova.entidades.AuxDependencia;
import br.com.bb.exxi.cartaauditoria_nova.entidades.AuxEnderecoDep;
import br.com.bb.exxi.cartaauditoria_nova.entidades.AuxFuncao;
import br.com.bb.exxi.cartaauditoria_nova.entidades.AuxFuncionarioAtacado;
import br.com.bb.exxi.cartaauditoria_nova.entidades.VwPedidos;
import br.com.bb.exxi.cartaauditoria_nova.entidades.CapMci;
import br.com.bb.exxi.cartaauditoria_nova.entidades.CapMciGrupos;
import br.com.bb.exxi.cartaauditoria_nova.entidades.ClientesEndereco;
import br.com.bb.exxi.cartaauditoria_nova.entidades.LogPedidos;
import br.com.bb.exxi.cartaauditoria_nova.entidades.Outorgados;
import br.com.bb.exxi.cartaauditoria_nova.entidades.Pedidos;
import br.com.bb.exxi.cartaauditoria_nova.entidades.PedidosLote;
import br.com.bb.exxi.cartaauditoria_nova.entidades.PedidosLotePK;
import br.com.bb.exxi.cartaauditoria_nova.entidades.PedidosPK;
import br.com.bb.exxi.cartaauditoria_nova.entidades.Resultados;
import br.com.bb.exxi.cartaauditoria_nova.entidades.Swap;
import br.com.bb.exxi.cartaauditoria_nova.entidades.Usuario;
import br.com.bb.exxi.cartaauditoria_nova.entidades.VwResultados;
import br.com.bb.exxi.cartaauditoria_nova.util.ConstantsBB;
import br.com.bb.exxi.cartaauditoria_nova.util.Funcoes;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
//import java.util.HashSet;
import java.util.List;
import java.util.Map;
//import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import jxl.CellView;
import jxl.HeaderFooter;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Orientation;
import jxl.format.VerticalAlignment;
import jxl.write.Blank;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.joda.time.LocalDate;
import org.joda.time.YearMonth;

/**
 *
 * @author F9066619
 */
@ManagedBean
@SessionScoped
public class PedidosBean {

    private PedidosDAO pedidosDAO;
    private LogPedidosDAO logPedidosDAO;
    private Integer cdCli, cdCnpj, cdGrupo;
    private Date dtBase;
    private String dtBaseStr;
    private List<DataFormatada> dtsBase;
    private List<CapMciGrupos> grupos;
    private Integer cdFlow;
//    private List<Pedidos> pedidosAProcessar;
    private List<VwPedidos> pedidosAProcessar;
    private List<PedidosLote> pedidosLoteLista;
    private Pedidos pedido;
    private PedidosLote pedidoLote;
    private Usuario usuario;
    private FacesContext context;
    private int cdDep;
    private boolean bloqueioVisao;
    private boolean acessoFull;
    private WritableWorkbook arqExcel;
    private boolean geraOutorgados;
    private int chkLote;
    private AuxEnderecoDep enderecoDep;
    private static int numCaracteresBusca = 3;
    private Map<String, Integer> mapaBusca;
    private String campoBusca;
    
    private String nmCli;
    private String cnpj;
    private String nmDep;
    private String nmEndereco;
    private String nmBairro;
    private String nmCep;
    private String nmMunicipio;
    private Integer tpPessoa;

    private AuxFuncionarioAtacadoDAO auxFuncionarioDAO;
    private AuxFuncionarioAtacado funci;
    private AuxDependenciaDAO auxDependenciaDAO;
    private AuxDependencia dAg, dSureg, dSuper;
    private List<AuxDependencia> supers, suregs, agencias, csas, escrPrivate, cenops;
    private int cdSuper, cdSureg, cdAg, blqDep, blqSuper, blqSureg, blqDiret;
    private boolean okAcesso, okEstatistica;
    private Atualizacoes envio, recepcao;
    private AtualizacoesDAO atualizacoesDAO;

    
    
    @PostConstruct
    public void init() {
        pedidosDAO = new PedidosDAO();
        logPedidosDAO = new LogPedidosDAO();
        dtsBase = montaListaDtBase();
//        pedidosAProcessar = new ArrayList<Pedidos>();
        grupos = new ArrayList<CapMciGrupos>();
        pedidosAProcessar = new ArrayList<VwPedidos>();
        pedidosLoteLista = new ArrayList<PedidosLote>();
        acessoFull = false;
        geraOutorgados = false;
        enderecoDep = null;
        chkLote = 0;
        
        auxFuncionarioDAO = new AuxFuncionarioAtacadoDAO();
        auxDependenciaDAO = new AuxDependenciaDAO();
        cdSuper = 0;
        cdSureg = 0;
        cdAg = 0;
        blqDep = 0;
        blqSuper = 0;
        blqSuper = 0;
        dAg = new AuxDependencia();
        okAcesso = false;
        okEstatistica = false;
        atualizacoesDAO = new AtualizacoesDAO();
        
    }

    public void atualizar() {
        controlaVisao();
        if (usuario != null) {
//            pedidosAProcessar = pedidosDAO.listaPedidosAProcessar(Integer.parseInt(usuario.getCodigoPrefixoDependencia()));
            pedidosAProcessar = pedidosDAO.listaPedidos(Integer.parseInt(usuario.getCodigoPrefixoDependencia()));
            pedidosLoteLista = new PedidosLoteDAO().listaPedidos(Integer.parseInt(usuario.getCodigoPrefixoDependencia()));
            acessoFull = usuario.getCodigoPrefixoDependencia().equals("9951");
            dtsBase = montaListaDtBase();
            //grupos = new CapMciGruposDAO().listaGrupos(cdDep);
        }
    }
    
    public void atualizar2(){
        supers = auxDependenciaDAO.listaDependencia(ConstantsBB.SUPERINTENDENCIA, blqSuper, blqSureg, blqDep);
        suregs = auxDependenciaDAO.listaDependencia(ConstantsBB.REGIONAL, cdSuper, blqSureg, blqDep);
        csas = auxDependenciaDAO.listaDependencia(ConstantsBB.CSA, blqSuper, blqSureg, blqDep);
        cenops = auxDependenciaDAO.listaDependencia(ConstantsBB.CENOP, blqSuper, blqSureg, blqDep);
        escrPrivate = auxDependenciaDAO.listaDependencia(ConstantsBB.ESCRITORIO_PRIVATE, blqSuper, blqSureg, blqDep);
        agencias = new ArrayList<AuxDependencia>();
        AuxDependencia vipat = new AuxDependencia(8069, "VIPAT", 0, 0, 0, 16);
        if (blqDiret == 0) {
            agencias.add(vipat);
        }
        if (blqDiret == 0 || blqDiret == ConstantsBB.DICOR) {
            if (blqSuper == 0) {
                agencias.add(auxDependenciaDAO.lerDependencia(ConstantsBB.DICOR));
            }
        }
        if (blqDiret == 0 || blqDiret == ConstantsBB.UPB) {
            if (blqSuper == 0) {
                agencias.add(auxDependenciaDAO.lerDependencia(ConstantsBB.UPB));
            }
        }
        if (blqDiret == 0 || blqDiret == ConstantsBB.DICOR) {
            agencias.add(auxDependenciaDAO.lerDependencia(9003));
            for (AuxDependencia a : csas) {
                agencias.add(a);
            }
            for (AuxDependencia a : cenops) {
                agencias.add(a);
            }
            for (AuxDependencia a : supers) {
                agencias.add(a);
            }
            for (AuxDependencia a : auxDependenciaDAO.listaDependencia(ConstantsBB.AGENCIA_LARGE_CORPORATE, blqSuper, blqSureg, blqDep)) {
                agencias.add(a);
            }
            for (AuxDependencia a : auxDependenciaDAO.listaDependencia(ConstantsBB.AGENCIA_CORPORATE, blqSuper, blqSureg, blqDep)) {
                agencias.add(a);
            }
            for (AuxDependencia a : auxDependenciaDAO.listaDependencia(ConstantsBB.AGENCIA_EMPRESARIAL, blqSuper, blqSureg, blqDep)) {
                agencias.add(a);
            }
        }
        if (blqDiret == 0 || blqDiret == ConstantsBB.UPB) {
            agencias.add(auxDependenciaDAO.lerDependencia(9517));
            for (AuxDependencia a : escrPrivate) {
                agencias.add(a);
            }
        }

        if (blqDep != 0) {
            agencias = new ArrayList<AuxDependencia>();
            agencias.add(auxDependenciaDAO.lerDependencia(blqDep));
        }

        envio = atualizacoesDAO.leAtualizacao("enviar");
        recepcao = atualizacoesDAO.leAtualizacao("receber");
    }

    public void autenticacao(Usuario u) {
        if(this.usuario == null){
            this.usuario = u;
        }
        if (funci == null) {
            funci = auxFuncionarioDAO.lerFunci(u.getChave());
            dAg = auxDependenciaDAO.lerDependencia(funci.getDependencia().getCdDep());

            if (dAg != null) {
                blqDep = Integer.parseInt(u.getCodigoPrefixoDependencia());
                blqSuper = dAg.getCdSuper();
                blqSureg = dAg.getCdSureg();
                if ((int) dAg.getCdDep() == ConstantsBB.UPB || dAg.getCdTpDep() == ConstantsBB.SUPER_PRIVATE
                        || dAg.getCdTpDep() == ConstantsBB.ESCRITORIO_PRIVATE) {
                    blqDiret = ConstantsBB.UPB;
                } else if ((int) dAg.getCdDep() == ConstantsBB.DICOR || dAg.getCdTpDep() == ConstantsBB.SUPERINTENDENCIA
                        || dAg.getCdTpDep() == ConstantsBB.REGIONAL
                        || dAg.getCdTpDep() == ConstantsBB.AGENCIA_EMPRESARIAL || dAg.getCdTpDep() == ConstantsBB.AGENCIA_CORPORATE 
                        || dAg.getCdTpDep() == ConstantsBB.AGENCIA_LARGE_CORPORATE || dAg.getCdTpDep() == ConstantsBB.CSA || dAg.getCdTpDep() == ConstantsBB.CENOP
                        || dAg.getCdTpDep() == ConstantsBB.MIDDLE_OFFICE) {
                    blqDiret = ConstantsBB.DICOR;
                } else {
                    blqDiret = 0;
                }
                if (dAg.getCdTpDep() == ConstantsBB.SUPERINTENDENCIA || dAg.getCdTpDep() == ConstantsBB.REGIONAL || dAg.getCdTpDep() == ConstantsBB.SUPER_PRIVATE) {
                    blqDep = 0;
                } else if (dAg.getCdTpDep() == ConstantsBB.CSA || dAg.getCdTpDep() == ConstantsBB.CENOP) {
                    blqSuper = dAg.getCdCsa();
                    blqDep = 0;
                }
                if (dAg.getCdTpDep() == ConstantsBB.SUPERINTENDENCIA || dAg.getCdTpDep() == ConstantsBB.CSA || dAg.getCdTpDep() == ConstantsBB.CENOP || dAg.getCdTpDep() == ConstantsBB.SUPER_PRIVATE) {
                    blqSureg = 0;
                } else if (dAg.getCdTpDep() == ConstantsBB.DIRETORIA || dAg.getCdTpDep() == ConstantsBB.GERENCIA || dAg.getCdTpDep() == ConstantsBB.MIDDLE_OFFICE) {
                    blqDep = 0;
                    blqSureg = 0;
                    blqSuper = 0;
                }
                if (dAg.getCdTpDep() == ConstantsBB.AGENCIA_EMPRESARIAL || dAg.getCdTpDep() == ConstantsBB.AGENCIA_CORPORATE || dAg.getCdTpDep() == ConstantsBB.AGENCIA_LARGE_CORPORATE
                        || dAg.getCdTpDep() == ConstantsBB.ESCRITORIO_PRIVATE || dAg.getCdTpDep() == ConstantsBB.MIDDLE_OFFICE) {
                    if (funci != null) {
                        if (funci.getFuncao().getNmFuncao().contains("GERAL")) {
                            okAcesso = true;
                            okEstatistica = true;
                        }
                        if (funci.getFuncao().getNmFuncao().contains("NEG")) {
                            okAcesso = true;
                            okEstatistica = true;
                        }
                    }
                } else if (dAg.getCdTpDep() == ConstantsBB.CSA || dAg.getCdTpDep() == ConstantsBB.CENOP) {
                    if (funci != null) {
                        if (funci.getFuncao().getNmFuncao().contains("GERAL")) {
                            okAcesso = true;
                            okEstatistica = true;
                        }
                        if (funci.getFuncao().getNmFuncao().contains("AREA")) {
                            okAcesso = true;
                            okEstatistica = true;
                        }
                    }
                } else if (dAg.getCdTpDep() == ConstantsBB.GERENCIA || dAg.getCdTpDep() == ConstantsBB.DIRETORIA 
                        || dAg.getCdTpDep() == ConstantsBB.REGIONAL || dAg.getCdTpDep() == ConstantsBB.SUPERINTENDENCIA 
                        || dAg.getCdTpDep() == ConstantsBB.SUPER_PRIVATE /*|| dAg.getCdDep() == 8587*/) {
                    okEstatistica = true;
                }
//                *** SÓ PARA TESTE ***                
                if(funci.getMatricula().equals("F8711907") || funci.getMatricula().equals("F8315465")){
                    okEstatistica = true;
                    okAcesso = true;
                }

                if (cdAg == 0) {
                    cdAg = dAg.getCdDep();
                }
                
                /*TEMPORARIO*/
                okEstatistica = false;
            }
            atualizar2();
        }
        atualizar();
    }

    private List<DataFormatada> montaListaDtBase() {
        List<DataFormatada> lista = new ArrayList<DataFormatada>();
        Funcoes f = new Funcoes();
        //LocalDate menorDtBase = new LocalDate(2011, 1, 31);
        LocalDate auxDtBase = null;

        LocalDate hoje = new LocalDate(Calendar.getInstance().getTime());
        YearMonth anomes = new YearMonth(hoje);
        
        LocalDate menos5anos = hoje.minusYears(7);
        YearMonth anomes2 = new YearMonth(menos5anos);
        LocalDate menorDtBase = f.ultimoDiaMes(anomes2);

//        if (acessoFull) {
//            menorDtBase = new LocalDate(new ResultadosDAO().menorDataBase());
//        }

        //* este trecho tenta impedir os pedidos no mês corrente antes do 4º dia útil
        LocalDate primeiroDia = hoje.withDayOfMonth(1);
        if (f.intervaloDiasUteis(primeiroDia.minusDays(1), hoje) < 4) {
            anomes = anomes.minusMonths(1);
        }
        Date aux = null;
//        if (acessoFull) {
            do {
                anomes = anomes.minusMonths(1);
                auxDtBase = f.ultimoDiaMes(anomes);
                aux = auxDtBase.toDate();
                lista.add(new DataFormatada(aux, f.dia(aux)));
            } while (!auxDtBase.equals(menorDtBase));
//        } else {
//            for (int x = 0; x < 24; x++) {
//                anomes = anomes.minusMonths(1);
//                aux = f.ultimoDiaMes(anomes).toDate();
//                lista.add(new DataFormatada(aux, f.dia(aux)));
//            }
//        }

        return lista;
    }
    
    private void controlaVisao() {
        bloqueioVisao = false;
        cdDep = 0;
        if (usuario != null) {
            if (Integer.parseInt(usuario.getCodigoTipoDependencia()) == ConstantsBB.AGENCIA_EMPRESARIAL
                    || Integer.parseInt(usuario.getCodigoTipoDependencia()) == ConstantsBB.AGENCIA_CORPORATE
                    || Integer.parseInt(usuario.getCodigoTipoDependencia()) == ConstantsBB.AGENCIA_LARGE_CORPORATE
                    || Integer.parseInt(usuario.getCodigoTipoDependencia()) == ConstantsBB.CSA
                    || Integer.parseInt(usuario.getCodigoTipoDependencia()) == ConstantsBB.CENOP
                    || Integer.parseInt(usuario.getCodigoTipoDependencia()) == ConstantsBB.REGIONAL
                    || Integer.parseInt(usuario.getCodigoTipoDependencia()) == ConstantsBB.SUPERINTENDENCIA
                    || Integer.parseInt(usuario.getCodigoTipoDependencia()) == ConstantsBB.ESCRITORIO_PRIVATE
                    || Integer.parseInt(usuario.getCodigoTipoDependencia()) == ConstantsBB.SUPER_PRIVATE) {
                cdDep = Integer.parseInt(usuario.getCodigoPrefixoDependencia());
                /*if(Integer.parseInt(usuario.getCodigoPrefixoDependencia()) == 1921){*/
                if(Integer.parseInt(usuario.getCodigoTipoDependencia()) == ConstantsBB.CSA || Integer.parseInt(usuario.getCodigoTipoDependencia()) == ConstantsBB.CENOP){
                    cdDep = 0;
                }
                String nmFuncao = "";
                if(usuario.getTextoComissaoUsuario() == null){
                    AuxFuncao af = new AuxFuncaoDAO().lerFuncao(Integer.parseInt(usuario.getCodigoComissaoUsuario()));
                    nmFuncao = af.getNmFuncao();
                }else{
                    nmFuncao = usuario.getTextoComissaoUsuario();
                }
                if (!nmFuncao.contains("GER")) {
                    AcessoDAO acessoDAO = new AcessoDAO();
                    Acesso aux = acessoDAO.leAcesso(usuario.getChave());
                    if (aux == null) {
                        bloqueioVisao = true;
                    }
                }
            }
        }
    }
    
    public void processaSolicitacao(){
        if(chkLote == 0){
            solicitaCarta();
        }else if(chkLote == 1){
            solicitaLote();
        }else{
            if(campoBusca != null && !campoBusca.equals("")){
                if(mapaBusca.containsKey(campoBusca)){
                    cdGrupo = mapaBusca.get(campoBusca);
                    solicitaLote();
                }
            }
        }
    }

    private void solicitaCarta() {
        try {
            dtBase = new SimpleDateFormat("dd/MM/yyyy").parse(dtBaseStr);
        } catch (ParseException ex) {
            Logger.getLogger(PedidosBean.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        pedido = null;
        boolean dadosIncorretos = false;
        Integer clienteRestrito = 0;
        boolean foraJurisdicao = false;
        boolean adicionar = false;
        boolean pedidoFeito = false;
        
        if (cdCli == 0) {
            dadosIncorretos = true;
        } else {
            clienteRestrito = testaRestrito();
            if (cdDep != 0) {
                foraJurisdicao = testaJurisdicao();
            }
            if (!foraJurisdicao && (clienteRestrito == 0)) {
                pedido = pedidosDAO.lePedido(cdCli, dtBase);
                if (pedido.getCdStatus() == null) {
                    adicionar = true;
                    pedido = new Pedidos();
                    pedido.setPedidosPK(new PedidosPK(Integer.parseInt(usuario.getCodigoPrefixoDependencia()), cdCli, dtBase));
                    if (cdFlow != null) {
                        pedido.setCdCarta(cdFlow);
                    }
                    pedido.setCdOutorgado(geraOutorgados);
                    pedidosDAO.adiciona(pedido);
                    if(geraOutorgados){
                        new OutorgadosDAO().grava(pedido.getPedidosPK());
                    }
                } else {
                    if (pedido.getCdStatus() < 2) {
                        pedidoFeito = true;
                    } else if (!bloqueioVisao) {
                        if (pedido.getCdCarta() == 0) {
//                            if (cdDep != 0) {
                                if (cdFlow != null) {
                                    if (pedido.getPedidosPK().getCdPrf() == 9951) {
                                        PedidosPK pedidoPK = pedido.getPedidosPK();
                                        pedidoPK.setCdPrf(cdDep);
                                        pedido.setPedidosPK(pedidoPK);
                                    }
                                    pedido.setCdCarta(cdFlow);
                                    pedido.setCdOutorgado(geraOutorgados);
                                    pedidosDAO.atualiza(pedido);
                                    if(geraOutorgados){
                                        new OutorgadosDAO().grava(pedido.getPedidosPK());
                                    }
                                }else{
                                    pedido.setCdCarta(0);
                                }
//                            }
                        } else {
                            if (cdFlow != null){
//                                if(cdFlow != pedido.getCdCarta()){
                                    pedido.setCdCarta(cdFlow);
                                    pedidosDAO.atualiza(pedido);      
//                                }
                            }else{
                                pedido.setCdCarta(0);
                            }
                            //cdFlow = pedido.getCdCarta();
                        }
                    }
                }
            }
        }
        LogPedidos lp = new LogPedidos();
        lp.setCdCli(cdCli);
        lp.setCdFlow(cdFlow);
        lp.setCdPrf(Integer.parseInt(usuario.getCodigoPrefixoDependencia()));
        lp.setDtBase(dtBase);
        lp.setNmChave(usuario.getChave());
        FacesMessage fm = null;
        if (dadosIncorretos || foraJurisdicao || (clienteRestrito > 0) || adicionar || pedidoFeito || bloqueioVisao) {
            if (dadosIncorretos) {
                fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dados incorretos", "Você deve digitar um MCI.");
            } else if (clienteRestrito > 0) {
                fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cliente PEP e/ou Restrito", "O MCI solicitado é classificado como PEP e/ou Restrito. Solicitação recusada.");
                lp.setNmTipo("RESTRITO");
            } else if (foraJurisdicao) {
                fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Extrapolação de jurisdição", "O MCI solicitado não pertence a essa Agência e/ou essa Jurisdição. Solicitação recusada.");
                lp.setNmTipo("FORA JURISDICAO");
            } else if (adicionar) {
                fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pedido adicionado", "Não há dados disponíveis para este MCI e data-base. O pedido foi adicionado e será enviado na próxima remessa.");
                lp.setNmTipo("ADD");
            } else if (pedidoFeito) {
                fm = new FacesMessage(FacesMessage.SEVERITY_WARN, "Pedido em processamento", "Pedido já realizado. Aguarde o processamento.");
                lp.setNmTipo("LOOK");
            } else if (bloqueioVisao) {
                fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Acesso restrito", "Você não tem acesso para visualizar os dados solicitados. Solicite ao Administrador de Segurança de sua Dependência.");
                lp.setNmTipo("NO ACCESS");
            }
        } else {
            if(geraOutorgados || pedido.isCdOutorgado()){
                geraOutorgados = true;
                if(!(new OutorgadosDAO().testa(pedido.getPedidosPK()))){
                    new OutorgadosDAO().grava(pedido.getPedidosPK());
                }
            }else{
                geraOutorgados = false;
            }
            if (geraCarta(pedido)) {
                lp.setNmTipo("MAKE");
                fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Planilha gerada com sucesso.");
            } else {
                lp.setNmTipo("ERR");
                fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Houve algum erro que impediu a geração da planilha. Por favor, tente novamente ou contate a equipe de desenvolvimento.");
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, fm);
        if (lp.getNmTipo() != null) {
            logPedidosDAO.adiciona(lp);
        }
    }

    public void solicitaCarta(VwPedidos p) {
        Pedidos p1 = new PedidosDAO().lePedido(p.getVwPedidosPK().getCdCli(), p.getVwPedidosPK().getDtBase());
        if(geraOutorgados || p1.isCdOutorgado()){
            geraOutorgados = true;
            if(!(new OutorgadosDAO().testa(p1.getPedidosPK()))){
                new OutorgadosDAO().grava(p1.getPedidosPK());
            }
        }else{
            geraOutorgados = false;
        }
        LogPedidos lp = new LogPedidos();
        lp.setCdCli(p1.getPedidosPK().getCdCli());
        lp.setCdFlow(p1.getCdCarta());
        lp.setCdPrf(Integer.parseInt(usuario.getCodigoPrefixoDependencia()));
        lp.setDtBase(p1.getPedidosPK().getDtBase());
        lp.setNmChave(usuario.getChave());
        FacesMessage fm = null;
        if (geraCarta(p1)) {
            lp.setNmTipo("MAKE");
            fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Planilha gerada com sucesso.");
        } else {
            lp.setNmTipo("ERR");
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Houve algum erro que impediu a geração da planilha. Por favor, tente novamente ou contate a equipe de desenvolvimento.");
        }
        FacesContext.getCurrentInstance().addMessage(null, fm);
        logPedidosDAO.adiciona(lp);
    }

    private boolean testaJurisdicao() {
        boolean aux = false;
        if(chkLote == 1){
            CapMciGrupos mciAncora = new CapMciGruposDAO().lerClienteAncora(cdCnpj);
            if (mciAncora != null) {
                if(mciAncora.getAgencia() != null){
                    if (mciAncora.getAgencia().getCdDep() != cdDep && mciAncora.getAgencia().getCdCsa() != cdDep && mciAncora.getAgencia().getCdSureg() != cdDep && mciAncora.getAgencia().getCdSuper() != cdDep) {
                        aux = true;
                    }
                }else{
                    CapMci mci = new CapMciDAO().lerCliente(mciAncora.getCdMci());
                    if (mci != null) {
                        if (mci.getAgencia().getCdDep() != cdDep && mci.getAgencia().getCdCsa() != cdDep && mci.getAgencia().getCdSureg() != cdDep && mci.getAgencia().getCdSuper() != cdDep) {
                            aux = true;
                        }
                    }
                }
            }
        }else{
            CapMci mci = new CapMciDAO().lerCliente(cdCli);
            if (mci != null) {
                if (mci.getAgencia().getCdDep() != cdDep && mci.getAgencia().getCdCsa() != cdDep && mci.getAgencia().getCdSureg() != cdDep && mci.getAgencia().getCdSuper() != cdDep) {
                    aux = true;
                }
            }
        }
        return aux;
    }

    private Integer testaRestrito() {
        Integer aux = 0;
        CapMci mci = new CapMciDAO().lerCliente(cdCli);
        if (mci != null) {
            aux = mci.getCdRestrito();
        }
        return aux;
    }

    private void solicitaLote(){
        try {
            dtBase = new SimpleDateFormat("dd/MM/yyyy").parse(dtBaseStr);
        } catch (ParseException ex) {
            Logger.getLogger(PedidosBean.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        boolean dadosIncorretos = false;
        boolean foraJurisdicao = false;
        boolean adicionar = false;
        boolean pedidoFeito = false;
        boolean gerar = false;
        if (chkLote == 1 && cdCnpj == 0) {
            dadosIncorretos = true;
        } else {
            List<CapMciGrupos> listaMciLote = new ArrayList<CapMciGrupos>();
            if(chkLote == 1){
                listaMciLote = new CapMciGruposDAO().listaClientes(cdCnpj,1);
                if(listaMciLote.isEmpty()){
                    dadosIncorretos = true;
                }
            }else{
                listaMciLote = new CapMciGruposDAO().listaClientes(cdGrupo,2);
            }
            if(!dadosIncorretos){
                if (chkLote == 1 && cdDep != 0) {
                    foraJurisdicao = testaJurisdicao();
                }
                if (!foraJurisdicao) {
                    pedidoLote = new PedidosLoteDAO().lePedido((chkLote == 1 ? cdCnpj : cdGrupo), dtBase, chkLote);
                    if (pedidoLote.getCdStatus() == null) {
                        adicionar = true;
                        pedidoLote = new PedidosLote();
                        pedidoLote.setPedidosLotePK(new PedidosLotePK((chkLote == 1 ? cdCnpj : cdGrupo), dtBase, chkLote));
                        pedidoLote.setCdPrf(Integer.parseInt(usuario.getCodigoPrefixoDependencia()));
                        if(chkLote == 2){
                            pedidoLote.setNmGrupo(campoBusca);
                        }
                        if (cdFlow != null) {
                            pedidoLote.setCdCarta(cdFlow);
                        }
                        pedidoLote.setCdStatus(2);
                        for(CapMciGrupos cmg : listaMciLote){
                            pedido = pedidosDAO.lePedido(cmg.getCdMci(), dtBase);
                            if(pedido.getCdStatus() == null){
                                pedidoLote.setCdStatus(0);
                                pedido = new Pedidos();
                                pedido.setPedidosPK(new PedidosPK(Integer.parseInt(usuario.getCodigoPrefixoDependencia()), cmg.getCdMci(), dtBase));
                                if(chkLote == 1){
                                    pedido.setCdCnpj(cdCnpj);
                                }else{
                                    pedido.setCdGrupo(cdGrupo);
                                }
                                if (cdFlow != null) {
                                    pedido.setCdCarta(cdFlow);
                                }
                                pedido.setCdOutorgado(geraOutorgados);
                                pedidosDAO.adiciona(pedido);
                                
                                LogPedidos lp = new LogPedidos();
                                lp.setCdCli(cmg.getCdMci());
                                lp.setCdFlow(cdFlow);
                                lp.setCdPrf(Integer.parseInt(usuario.getCodigoPrefixoDependencia()));
                                lp.setDtBase(dtBase);
                                lp.setNmChave(usuario.getChave());
                                lp.setNmTipo("ADD");
                                logPedidosDAO.adiciona(lp);
                                
                                if(geraOutorgados){
                                    new OutorgadosDAO().grava(pedido.getPedidosPK());
                                }
                            }else{
                                if(pedido.getCdStatus() < pedidoLote.getCdStatus()){
                                    pedidoLote.setCdStatus(pedido.getCdStatus());
                                }
                                boolean atualiza = false;
                                if(pedido.getCdCnpj() == null && chkLote == 1){
                                    pedido.setCdCnpj(cdCnpj);
                                    atualiza = true;
                                }
                                if(pedido.getCdGrupo() == null && chkLote == 2){
                                    pedido.setCdGrupo(cdGrupo);
                                    atualiza = true;
                                }
                                if(pedido.getCdCarta() == 0){
//                                    if(cdDep != 0){
                                        if(cdFlow != null){
                                            if(pedido.getPedidosPK().getCdPrf() == 9951){
                                                PedidosPK pedidoPK = pedido.getPedidosPK();
                                                pedidoPK.setCdPrf(cdDep);
                                                pedido.setPedidosPK(pedidoPK);
                                            }
                                            pedido.setCdCarta(cdFlow);
                                            pedido.setCdOutorgado(geraOutorgados);
                                            atualiza = true;
                                            if(geraOutorgados){
                                                new OutorgadosDAO().grava(pedido.getPedidosPK());
                                            }
                                        }else{
                                            pedido.setCdCarta(0);
                                        }
//                                    }
                                }else{
                                    if(cdFlow != null){
//                                        if(cdFlow != pedido.getCdCarta()){
                                            pedido.setCdCarta(cdFlow);
                                            atualiza = true;
//                                        }
                                    }else{
                                        pedido.setCdCarta(0);
                                    }
                                }
                                if(atualiza){
                                    pedidosDAO.atualiza(pedido);
                                }
                            }
                        }
                        if(pedidoLote.getCdStatus() == 2){
                            adicionar = false;
                            gerar = true;
                        }
                        new PedidosLoteDAO().adiciona(pedidoLote);
                    } else {
                        if (pedidoLote.getCdStatus() < 2) {
                            pedidoFeito = true;
                        }else{
                            gerar = true;
                        }
                    }
                }
            }
        }
        pedidoLote = null;
        LogPedidos lp = new LogPedidos();
        lp.setCdCli((chkLote == 1 ? cdCnpj : cdGrupo));
        lp.setCdFlow(cdFlow);
        lp.setCdPrf(Integer.parseInt(usuario.getCodigoPrefixoDependencia()));
        lp.setDtBase(dtBase);
        lp.setNmChave(usuario.getChave());
        FacesMessage fm = null;
        if (dadosIncorretos || foraJurisdicao || adicionar || pedidoFeito || bloqueioVisao) {
            if (dadosIncorretos) {
                fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dados incorretos", "Você deve digitar um Radical de CNPJ válido e encarteirado.");
            } else if (foraJurisdicao) {
                fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Extrapolação de jurisdição", "O MCI âncora do "+(chkLote == 1?"CNPJ":"grupo")+" solicitado não pertence a essa Agência e/ou essa Jurisdição. Solicitação recusada.");
                lp.setNmTipo("LOTE - FORA JURISDICAO");
            } else if (adicionar) {
                fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pedido adicionado", "Não há dados disponíveis para todos os MCI vinculados a esse "+(chkLote == 1?"CNPJ":"grupo")+" nessa data-base. O pedido foi adicionado e será enviado na próxima remessa.");
                lp.setNmTipo("LOTE - ADD");
            } else if (pedidoFeito) {
                fm = new FacesMessage(FacesMessage.SEVERITY_WARN, "Pedido em processamento", "Pedido já realizado. Aguarde o processamento.");
                lp.setNmTipo("LOTE - LOOK");
            } else if (bloqueioVisao) {
                fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Acesso restrito", "Você não tem acesso para visualizar os dados solicitados. Solicite ao Administrador de Segurança de sua Dependência.");
                lp.setNmTipo("LOTE - NO ACCESS");
            }
        } else {
                lp.setNmTipo("LOTE - READY");
                fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Lote pronto para ser gerado.");
        }
        FacesContext.getCurrentInstance().addMessage(null, fm);
        if (lp.getNmTipo() != null) {
            logPedidosDAO.adiciona(lp);
        }
        if(gerar){
            gerarLote((chkLote == 1 ? cdCnpj : cdGrupo), dtBase, chkLote);
        }
    }
    
    public void setContext(FacesContext context) {
        this.context = context;
    }
    
    public void gerarLote(Integer cdRef, Date dtBase, Integer tipoLote){
        try{
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            List<Pedidos> listaPedidos = pedidosDAO.listaPedidosLote(cdRef,dtBase,tipoLote);
            boolean primeiro = true;
            for(Pedidos p : listaPedidos){
                if(primeiro){
                    nmCli = "";
                    cnpj = "";
                    nmDep = "";
                    nmEndereco = "";
                    nmBairro = "";
                    nmCep = "";
                    nmMunicipio = "";
                    tpPessoa = 2;
                    //boolean condACC = false;

                    CapMci mci = new CapMciDAO().lerCliente(p.getPedidosPK().getCdCli());
                    if (mci != null) {
                        nmCli = mci.getRzSocial();
                        cnpj = mci.getCnpj();
                        tpPessoa = mci.getTpPessoa();
                        ClientesEndereco endereco = new ClientesEnderecoDAO().leEndereco(p.getPedidosPK().getCdCli());
                        if (endereco != null) {
                            nmEndereco = endereco.getTxLgr() + " " + endereco.getTxCmpt();
                            nmBairro = endereco.getTxBai();
                            nmCep = String.format("%08d", endereco.getNrCep());
                            nmCep = nmCep.substring(0, 5) + "-" + nmCep.substring(5, 8);
                            nmMunicipio = endereco.getMunicipio().getNmMun() + " (" + endereco.getMunicipio().getSgUf() + ")";
                        }
                    }
                    AuxDependencia dependencia = new AuxDependenciaDAO().lerDependencia(Integer.parseInt(usuario.getCodigoPrefixoDependencia()));
                    if (dependencia != null) {
                        nmDep = dependencia.getNmDep();
                        enderecoDep = new AuxEnderecoDepDAO().leEndereco(dependencia.getCdDep());
                    }

                    externalContext.setResponseContentType("application/vnd.ms-excel");
                    if(tipoLote == 1){
                        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"LOTE_" + nmCli.replaceAll(" ", "_") + "_" + new SimpleDateFormat("yyyy-MM-dd").format(p.getPedidosPK().getDtBase()) + ".xls\"");
                    }else{
                        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"LOTE_GRUPO_" + nmCli.replaceAll(" ", "_") + "_" + new SimpleDateFormat("yyyy-MM-dd").format(p.getPedidosPK().getDtBase()) + ".xls\"");
                    }
                    //WritableWorkbook w = Workbook.createWorkbook(externalContext.getResponseOutputStream());
                    arqExcel = Workbook.createWorkbook(externalContext.getResponseOutputStream());

                    geraAR(p);
                    if(geraOutorgados){
                        geraListaOutorgados(p);
                    }
                    primeiro = false;
                }
                LogPedidos lp = new LogPedidos();
                lp.setCdCli(p.getPedidosPK().getCdCli());
                lp.setCdFlow(p.getCdCarta());
                lp.setCdPrf(Integer.parseInt(usuario.getCodigoPrefixoDependencia()));
                lp.setDtBase(p.getPedidosPK().getDtBase());
                lp.setNmChave(usuario.getChave());
                //FacesMessage fm = null;
                if (geraPlanilhaCarta(p,tipoLote)) {
                    lp.setNmTipo("MAKE");
                    //fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Planilha gerada com sucesso.");
                } else {
                    lp.setNmTipo("ERR");
                    //fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Houve algum erro que impediu a geração da planilha. Por favor, tente novamente ou contate a equipe de desenvolvimento.");
                }
                //FacesContext.getCurrentInstance().addMessage(null, fm);
                logPedidosDAO.adiciona(lp);
            }
            arqExcel.write();
            arqExcel.close();

            if (externalContext.getResponseOutputStream() != null) {
                externalContext.getResponseOutputStream().flush();
                externalContext.getResponseOutputStream().close();
            }
        } catch (Exception ex) {
            Logger.getLogger(PedidosBean.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            //saida = false;
        }
    }
    
    public boolean geraCarta(Pedidos p){
        boolean saida = true;
        try {
            nmCli = "";
            cnpj = "";
            nmDep = "";
            nmEndereco = "";
            nmBairro = "";
            nmCep = "";
            nmMunicipio = "";
            tpPessoa = 2;
            boolean condACC = false;

            CapMci mci = new CapMciDAO().lerCliente(p.getPedidosPK().getCdCli());
            if (mci != null) {
                nmCli = mci.getRzSocial();
                cnpj = mci.getCnpj();
                tpPessoa = mci.getTpPessoa();
                ClientesEndereco endereco = new ClientesEnderecoDAO().leEndereco(p.getPedidosPK().getCdCli());
                if (endereco != null) {
                    nmEndereco = endereco.getTxLgr() + " " + endereco.getTxCmpt();
                    nmBairro = endereco.getTxBai();
                    nmCep = String.format("%08d", endereco.getNrCep());
                    nmCep = nmCep.substring(0, 5) + "-" + nmCep.substring(5, 8);
                    nmMunicipio = endereco.getMunicipio().getNmMun() + " (" + endereco.getMunicipio().getSgUf() + ")";
                }
            }
            AuxDependencia dependencia = new AuxDependenciaDAO().lerDependencia(Integer.parseInt(usuario.getCodigoPrefixoDependencia()));
            if (dependencia != null) {
                nmDep = dependencia.getNmDep();
                enderecoDep = new AuxEnderecoDepDAO().leEndereco(dependencia.getCdDep());
            }

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.setResponseContentType("application/vnd.ms-excel");
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + String.format("%09d", p.getPedidosPK().getCdCli()) + "_" + nmCli.replaceAll(" ", "_") + "_" + new SimpleDateFormat("yyyy-MM-dd").format(p.getPedidosPK().getDtBase()) + ".xls\"");
            //WritableWorkbook w = Workbook.createWorkbook(externalContext.getResponseOutputStream());
            arqExcel = Workbook.createWorkbook(externalContext.getResponseOutputStream());
            
            geraAR(p);
            if(geraOutorgados){
                geraListaOutorgados(p);
            }
            geraPlanilhaCarta(p,0);

            arqExcel.write();
            arqExcel.close();

            if (externalContext.getResponseOutputStream() != null) {
                externalContext.getResponseOutputStream().flush();
                externalContext.getResponseOutputStream().close();
            }
        } catch (Exception ex) {
            Logger.getLogger(PedidosBean.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            saida = false;
        }
        return saida;
    }

    public boolean geraPlanilhaCarta(Pedidos p, Integer tipoLote) {
        boolean saida = true;
        try {
            String nmCli2 = "";
            cnpj = "00.000.000/0000-00";
//            nmDep = "";
//            nmEndereco = "";
//            nmBairro = "";
//            nmCep = "";
//            nmMunicipio = "";
            tpPessoa = 2;
            boolean condACC = false;
            boolean condCartao = false;

            CapMci mci = new CapMciDAO().lerCliente(p.getPedidosPK().getCdCli());
            if (mci == null){
                CapMciGrupos mciG = new CapMciGruposDAO().leCliente(p.getPedidosPK().getCdCli());
                if(mciG != null){
                    nmCli2 = mciG.getRzSocial();
                    cnpj = mciG.getCnpj();
                    tpPessoa = 2;
                }
            }else{
//            if (mci != null) {
                nmCli2 = mci.getRzSocial();
                cnpj = mci.getCnpj();
                tpPessoa = mci.getTpPessoa();
//                ClientesEndereco endereco = new ClientesEnderecoDAO().leEndereco(p.getPedidosPK().getCdCli());
//                if (endereco != null) {
//                    nmEndereco = endereco.getTxLgr() + " " + endereco.getTxCmpt();
//                    nmBairro = endereco.getTxBai();
//                    nmCep = String.format("%08d", endereco.getNrCep());
//                    nmCep = nmCep.substring(0, 5) + "-" + nmCep.substring(5, 8);
//                    nmMunicipio = endereco.getMunicipio().getNmMun() + " (" + endereco.getMunicipio().getSgUf() + ")";
//                }
            }
//            AuxDependencia dependencia = new AuxDependenciaDAO().lerDependencia(Integer.parseInt(usuario.getCodigoPrefixoDependencia()));
//            if (dependencia != null) {
//                nmDep = dependencia.getNmDep();
//                enderecoDep = new AuxEnderecoDepDAO().leEndereco(dependencia.getCdDep());
//            }
//
//            FacesContext facesContext = FacesContext.getCurrentInstance();
//            ExternalContext externalContext = facesContext.getExternalContext();
//            externalContext.setResponseContentType("application/vnd.ms-excel");
//            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + String.format("%09d", p.getPedidosPK().getCdCli()) + "_" + nmCli.replaceAll(" ", "_") + "_" + new SimpleDateFormat("yyyy-MM-dd").format(p.getPedidosPK().getDtBase()) + ".xls\"");
//            //WritableWorkbook w = Workbook.createWorkbook(externalContext.getResponseOutputStream());
//            arqExcel = Workbook.createWorkbook(externalContext.getResponseOutputStream());

            //definição de formatos
            //cabeçalho
            WritableFont arial16bold = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD);
            arial16bold.setColour(Colour.BLACK);
            WritableFont arial14bold = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            arial14bold.setColour(Colour.BLACK);
            WritableFont arial10bold = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10bold.setColour(Colour.BLACK);
            WritableFont arial10 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
            arial10.setColour(Colour.BLACK);
            WritableCellFormat normalStyle = new WritableCellFormat(arial10);
            normalStyle.setWrap(false);
            WritableCellFormat cabecalhoStyleBold = new WritableCellFormat(arial10bold);
            cabecalhoStyleBold.setWrap(false);
            WritableCellFormat cabecalhoStyleBold14 = new WritableCellFormat(arial14bold);
            cabecalhoStyleBold14.setWrap(false);
            cabecalhoStyleBold14.setAlignment(Alignment.CENTRE);
            WritableCellFormat cabecalhoStyleBold16 = new WritableCellFormat(arial16bold);
            cabecalhoStyleBold16.setWrap(false);
            WritableCellFormat normalStyleRight = new WritableCellFormat(arial10);
            normalStyleRight.setWrap(false);
            normalStyleRight.setAlignment(Alignment.RIGHT);
            WritableCellFormat cabecalhoStyleJustify = new WritableCellFormat(arial10);
            cabecalhoStyleJustify.setWrap(false);
            cabecalhoStyleJustify.setAlignment(Alignment.JUSTIFY);
            WritableCellFormat cabecalhoStyleBoldRight = new WritableCellFormat(arial10bold);
            cabecalhoStyleBoldRight.setWrap(false);
            cabecalhoStyleBoldRight.setAlignment(Alignment.RIGHT);

            WritableSheet s;
            if(tipoLote < 2){
                s = arqExcel.createSheet("Carta"+(tpPessoa == 2 ? "_"+cnpj.substring(11,15) : ""), arqExcel.getNumberOfSheets());
            }else{
                s = arqExcel.createSheet("Carta_"+String.format("%04d", arqExcel.getNumberOfSheets()), arqExcel.getNumberOfSheets());
            }

            //s.addCell(new Label(1, 2, "Banco do Brasil S.A.", cabecalhoStyleBold16));
            WritableImage image = new WritableImage(1, 2, 2, 2, new File("..\\applications\\cartaauditoria\\resources\\img\\LogoBB.png")); //web
            //WritableImage image = new WritableImage(1, 2, 2, 2, new File("C:\\Users\\PETERSON TANIGAKI\\Desktop\\cartaauditoria_nova\\src\\main\\resources\\img\\LogoBB.png"));
            //WritableImage image = new WritableImage(1, 2, 2, 2, new File("C:\\Projetos\\CartaAuditoriaWeb\\Carta de Auditoria\\cartaauditoria_nova\\src\\main\\resources\\img\\LogoBB.png"));
            //WritableImage image = new WritableImage(1, 2, 2, 2, new File("C:\\Users\\f8315465\\Desktop\\PROJETOS - 2019\\CARTA DE AUDITORIA - 2019\\cartaauditoria_nova\\src\\main\\resources\\img\\LogoBB.png"));
            image.setImageAnchor(WritableImage.NO_MOVE_OR_SIZE_WITH_CELLS); 
            s.addImage(image);
            
            s.addCell(new Label(1, 4, "São Paulo, " + new SimpleDateFormat("dd 'de' MMMM 'de' yyyy").format(new Date()), normalStyleRight));
            s.addCell(new Label(6, 3, "Carta nº " + new SimpleDateFormat("yyyy").format(new Date()) + "/" + String.format("%09d", p.getCdCarta()), normalStyleRight));
            s.addCell(new Label(1, 6, "À", normalStyle));
            s.addCell(new Label(1, 7, nmCli, cabecalhoStyleBold));
            s.addCell(new Label(1, 8, nmEndereco + " - " + nmBairro + " - " + nmMunicipio + " - CEP " + nmCep, normalStyle));
            s.addCell(new Label(1, 14, "Confirmação de Saldos Para fins de Auditoria".toUpperCase(), cabecalhoStyleBold14));
            s.addCell(new Label(1, 18, "Atendendo à solicitação de nosso cliente abaixo descrito, fornecemos a seguir, com posição em " + new SimpleDateFormat("dd/MM/yyyy").format(p.getPedidosPK().getDtBase()) + ", as seguintes informações sobre os produtos mantidos neste Banco:", cabecalhoStyleJustify));
            if(tpPessoa == 2){
                s.addCell(new Label(1, 21, "Empresa:", cabecalhoStyleBoldRight));
                s.addCell(new Label(1, 22, "CNPJ:", cabecalhoStyleBoldRight));
            }else{
                s.addCell(new Label(1, 21, "Nome:", cabecalhoStyleBoldRight));
                s.addCell(new Label(1, 22, "CPF:", cabecalhoStyleBoldRight));
            }
            s.addCell(new Label(2, 21, nmCli2, normalStyle));
            s.addCell(new Label(2, 22, cnpj, normalStyle));

            CellView cv = s.getRowView(18);
            cv.setSize(40 * 20);//altura da linha = altura desejada em pontos x 20
            s.setRowView(18, cv);

            s.mergeCells(1, 4, 6, 4);
            s.mergeCells(1, 7, 6, 7);
            s.mergeCells(1, 8, 6, 8);
            s.mergeCells(1, 14, 6, 14);
            s.mergeCells(1, 18, 6, 18);

            cv = s.getColumnView(0);
            cv.setSize(2 * 256);//largura da coluna = largura desejada em caracteres x 256
            s.setColumnView(0, cv);
            cv = s.getColumnView(1);
            cv.setSize(30 * 256);
            s.setColumnView(1, cv);
            cv = s.getColumnView(2);
            cv.setSize(10 * 256);
            s.setColumnView(2, cv);
            cv = s.getColumnView(3);
            cv.setSize(20 * 256);
            s.setColumnView(3, cv);
            cv = s.getColumnView(4);
            cv.setSize(25 * 256);
            s.setColumnView(4, cv);
            cv = s.getColumnView(5);
            cv.setSize(25 * 256);
            s.setColumnView(5, cv);
            cv = s.getColumnView(6);
            cv.setSize(25 * 256);
            s.setColumnView(6, cv);

            NumberFormat monetario = new NumberFormat("$ #,##0.00");
            WritableCellFormat numFormat = new WritableCellFormat(arial10, monetario);
            WritableCellFormat numFormatBold = new WritableCellFormat(arial10bold, monetario);
            numFormatBold.setBackground(Colour.GRAY_25);

            WritableCellFormat produtoStyleBold = new WritableCellFormat(arial10bold);
            produtoStyleBold.setWrap(false);
            produtoStyleBold.setBackground(Colour.GRAY_25);
            WritableCellFormat produtoStyleBoldRight = new WritableCellFormat(arial10bold);
            produtoStyleBoldRight.setWrap(false);
            produtoStyleBoldRight.setBackground(Colour.GRAY_25);
            produtoStyleBoldRight.setAlignment(Alignment.RIGHT);

            //dados
            int lincab = 25;
            int lin = 27;
            Double saldo_cheque_especial = 0.0;
            double total;
            double total1;
            double total2;
            String sql = "";
            ArrayList<String> conta_corrente = new ArrayList<String>();
            ArrayList<Double> saldo_conta = new ArrayList<Double>();

            List<Resultados> listaProdutos = new ArrayList<Resultados>();
            listaProdutos = new ResultadosDAO().listaProdutos(p.getPedidosPK());
            if (listaProdutos.isEmpty()) {
                s.addCell(new Label(1, lincab, "Nada consta.", cabecalhoStyleBold14));
                s.mergeCells(1, lincab, 6, lincab);
                lin = lincab;
            } else {
                for (Resultados r : listaProdutos) {
                    condACC = (r.getCdPrd() == 543 && (p.getPedidosPK().getDtBase().after(new SimpleDateFormat("dd/MM/yyyy").parse("31/10/2014"))));
                    condCartao = (r.getCdPrd() == 9 && (p.getPedidosPK().getDtBase().after(new SimpleDateFormat("dd/MM/yyyy").parse("29/09/2014"))));
                    if (r.getCdPrd() != 678 && r.getCdPrd() != 679) {
                        //*** tratamento normal para todos os produtos
                        s.addCell(new Label(1, lincab, r.getNmPrd(), produtoStyleBold));
                        s.addCell(new Label(2, lincab, "", produtoStyleBold));
                        s.addCell(new Label(3, lincab, "", produtoStyleBold));
                        s.addCell(new Label(4, lincab, "", produtoStyleBold));
                        s.addCell(new Label(5, lincab, "", produtoStyleBold));
                        s.addCell(new Label(6, lincab, "", produtoStyleBold));
                        s.addCell(new Label(1, lincab + 1, "Nome da Modalidade", produtoStyleBoldRight));
                        s.addCell(new Label(2, lincab + 1, "Agência", produtoStyleBoldRight));
                        s.addCell(new Label(3, lincab + 1, "Número de Referência", produtoStyleBoldRight));
                        s.addCell(new Label(4, lincab + 1, "", produtoStyleBoldRight));
                        //s.addCell(new Label(5, lincab + 1, "", produtoStyleBoldRight));
                        if (r.getCdPrd() == 88 || r.getCdPrd() == 311 || r.getCdPrd() == 313 || r.getCdPrd() == 356 || r.getCdPrd() == 358 || r.getCdPrd() == 637 || condACC) {
                            /**
                             * * produtos em moeda estrangeira **
                             */
                            s.addCell(new Label(5, lincab + 1, "", produtoStyleBoldRight));
                            s.addCell(new Label(6, lincab + 1, "Saldo Moeda Estrangeira", produtoStyleBoldRight));
                        } else {
                            if(r.getCdPrd() == 317){
                                /* existe deposito interfinanceiro em moeda estrangeira */
                                s.addCell(new Label(5, lincab + 1, "Saldo Moeda Estrangeira", produtoStyleBoldRight));
                            }else{
                                s.addCell(new Label(5, lincab + 1, "", produtoStyleBoldRight));
                            }
                            s.addCell(new Label(6, lincab + 1, "Saldo Moeda Nacional", produtoStyleBoldRight));
                        }
                        total = 0.0;
                        lin = lincab + 2;

                        List<VwResultados> listaResultados = new ArrayList<VwResultados>();
                        listaResultados = new VwResultadosDAO().listaResultados(p.getPedidosPK(), r.getCdPrd());
                        for (VwResultados vr : listaResultados) {
                            //** rotina para corrigir saldo de cheque especial quando houver saldo de conta corrente
                            saldo_cheque_especial = 0.0;
                            if (r.getCdPrd() == 6) {
                                conta_corrente.add(vr.getVwResultadosPK().getNmCtr());
                                saldo_conta.add(vr.getVlSld1());
                            } else if (r.getCdPrd() == 8 && !conta_corrente.isEmpty()) {
                                saldo_cheque_especial = vr.getVlSld1();
                                for (int c = 0; c < conta_corrente.size(); c++) {
                                    if (conta_corrente.get(c).equals(vr.getVwResultadosPK().getNmCtr()) && saldo_conta.get(c).compareTo(0.0) > 0 && saldo_cheque_especial.compareTo(0.0) > 0) {
                                        saldo_cheque_especial = 0.0;
                                        break;
                                    }
                                }
                            }
                            //***

                            s.addCell(new Label(1, lin, vr.getNmMdld(), normalStyle));
                            s.addCell(new Label(2, lin, String.format("%04d", vr.getVwResultadosPK().getCdDepe()), normalStyleRight));
                            s.addCell(new Label(3, lin, vr.getVwResultadosPK().getNmCtr(), normalStyle));
                            if (r.getCdPrd() == 8) {
                                s.addCell(new Number(6, lin, saldo_cheque_especial, numFormat));
                                total = total + saldo_cheque_especial;
                            } else if (condCartao) {
                                /* correção de Cartão de Crédito */
                                if(vr.getVlSld4() == null){
                                   s.addCell(new Number(6, lin, 0.0, numFormat)); 
                                } else {
                                   s.addCell(new Number(6, lin, vr.getVlSld4(), numFormat));
                                   total = total + vr.getVlSld4();
                                }
                            } else if (r.getCdPrd() == 1){
                                /* correção de Depósito a Prazo */
                                if(vr.getVlSld4() == null){
                                   s.addCell(new Number(6, lin, vr.getVlSld1(), numFormat)); 
                                   total = total + vr.getVlSld1();
                                } else {
                                   s.addCell(new Number(6, lin, vr.getVlSld4(), numFormat));
                                   total = total + vr.getVlSld4();
                                }
                            } else {
                                if (vr.getCdColSld() != null && vr.getCdColSld().equals("3")) {
//                                String aux = rs2.getString("vl_encargos");
                                    double aux2 = 0.0;
                                    /*if (vr.getVlEncargos() == null) {*/
                                    if (vr.getVlSld4() == null) {
                                        s.addCell(new Number(6, lin, vr.getVlSld3(), numFormat));
                                        total = total + vr.getVlSld3();
                                    } else {
                                        //** correção de operações xer, cop e lsg
                                        /*aux2 = vr.getVlSld1() + vr.getVlEncargos();
                                         s.addCell(new Number(6, lin, aux2, numFormat));
                                         total = total + aux2;*/
                                        s.addCell(new Number(6, lin, vr.getVlSld4(), numFormat));
                                        total = total + vr.getVlSld4();
                                        ////
                                    }
                                } else if (vr.getCdColSld() != null && vr.getCdColSld().equals("2")) {
                                    /* operações em moeda estrangeira */
                                    s.addCell(new Number(6, lin, vr.getVlSld2(), numFormat));
                                    total = total + vr.getVlSld2();
                                } else if (condACC) {
                                    /* tratamento ACC */
                                    double aux2 = 0.0;
                                    aux2 = new BigDecimal(vr.getVlSld1() / (vr.getVlSld2() / 100000)).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
                                    s.addCell(new Number(6, lin, aux2, numFormat));
                                    total = total + aux2;
                                } else {
                                    if(r.getCdPrd() == 317 && vr.getVwResultadosPK().getCdMdld() == 5){
                                        s.addCell(new Number(5,lin,vr.getVlSld2(),numFormat));
                                    }
                                    s.addCell(new Number(6, lin, vr.getVlSld1(), numFormat));
                                    total = total + vr.getVlSld1();
                                }
                            }
                            lin++;
                        }
                        if (!(r.getCdPrd() == 88 || r.getCdPrd() == 311 || r.getCdPrd() == 313 || r.getCdPrd() == 356 || r.getCdPrd() == 358 || r.getCdPrd() == 637 || condACC)) {
                            /* NÃO EMITIR TOTAL PARA MOEDA ESTRANGEIRA */
                            s.addCell(new Label(1, lin, "Total", produtoStyleBold));
                            s.addCell(new Label(2, lin, "", produtoStyleBold));
                            s.addCell(new Label(3, lin, "", produtoStyleBold));
                            s.addCell(new Label(4, lin, "", produtoStyleBold));
                            s.addCell(new Label(5, lin, "", produtoStyleBold));
                            s.addCell(new Number(6, lin, total, numFormatBold));
                        }
                        lincab = lin + 3;
                    } else {
                        //** tratamento para swap e termo de moedas
                        s.addCell(new Label(1, lincab, r.getNmPrd(), produtoStyleBold));
                        s.addCell(new Label(2, lincab, "", produtoStyleBold));
                        s.addCell(new Label(3, lincab, "", produtoStyleBold));
                        s.addCell(new Label(4, lincab, "Valores em Moeda Nacional", produtoStyleBoldRight));
                        s.addCell(new Label(5, lincab, "", produtoStyleBoldRight));
                        s.addCell(new Label(6, lincab, "", produtoStyleBoldRight));
                        s.mergeCells(4, lincab, 6, lincab);
                        s.addCell(new Label(1, lincab + 1, "Nome da Modalidade", produtoStyleBoldRight));
                        s.addCell(new Label(2, lincab + 1, "Agência", produtoStyleBoldRight));
                        s.addCell(new Label(3, lincab + 1, "Número de Referência", produtoStyleBoldRight));
                        s.addCell(new Label(4, lincab + 1, "Valor Contratado", produtoStyleBoldRight));
                        s.addCell(new Label(5, lincab + 1, "Saldo Marcado a Mercado", produtoStyleBoldRight));
                        s.addCell(new Label(6, lincab + 1, "Saldo Atualizado", produtoStyleBoldRight));
                        total = 0.0;
                        total1 = 0.0;
                        total2 = 0.0;
                        lin = lincab + 2;

                        List<Swap> listaSwap = new ArrayList<Swap>();
                        listaSwap = new SwapDAO().listaSwap(p.getPedidosPK(), r.getCdPrd());
                        for (Swap sw : listaSwap) {
                            s.addCell(new Label(1, lin, sw.getNmMdld(), normalStyle));
                            s.addCell(new Label(2, lin, String.format("%04d", sw.getCdDepe()), normalStyleRight));
                            s.addCell(new Label(3, lin, sw.getSwapPK().getNmCtr(), normalStyle));
                            s.addCell(new Number(4, lin, sw.getVlContratado(), numFormat));
                            total1 = total1 + sw.getVlContratado();
                            s.addCell(new Number(5, lin, sw.getVlMtm(), numFormat));
                            total2 = total2 + sw.getVlMtm();
                            s.addCell(new Number(6, lin, sw.getVlAtualizado(), numFormat));
                            total = total + sw.getVlAtualizado();
                            lin++;
                        }
                        s.addCell(new Label(1, lin, "Total", produtoStyleBold));
                        s.addCell(new Label(2, lin, "", produtoStyleBold));
                        s.addCell(new Label(3, lin, "", produtoStyleBold));
                        s.addCell(new Number(4, lin, total1, numFormatBold));
                        s.addCell(new Number(5, lin, total2, numFormatBold));
                        s.addCell(new Number(6, lin, total, numFormatBold));
                        lincab = lin + 3;
                    }
                }
            }

            lin = lin + 4;

            //Rodapé
            WritableCellFormat rodapeStyleCenter = new WritableCellFormat(arial10);
            rodapeStyleCenter.setWrap(false);
            rodapeStyleCenter.setAlignment(Alignment.CENTRE);
            WritableCellFormat rodapeStyleBoldCenter = new WritableCellFormat(arial10bold);
            rodapeStyleBoldCenter.setWrap(false);
            rodapeStyleBoldCenter.setAlignment(Alignment.CENTRE); 

            s.addCell(new Label(1, lin, "BANCO DO BRASIL S.A.", rodapeStyleBoldCenter));
            s.addCell(new Label(1, lin + 1, nmDep.toUpperCase(), rodapeStyleBoldCenter));
            s.addCell(new Label(1, lin + 5, "___________________________________________", rodapeStyleCenter));
            s.addCell(new Label(4, lin + 5, "___________________________________________", rodapeStyleCenter));
            s.addCell(new Label(1, lin + 6, "Gerente de Grupo UA", rodapeStyleCenter));
            s.addCell(new Label(4, lin + 6, "Assistente A UA", rodapeStyleCenter));
            s.addCell(new Label(1, lin + 11, "Ouvidoria - 0800 729 5678 - Atendimento em dias Úteis, das 8 às 18 horas", rodapeStyleBoldCenter));

            cv = s.getRowView(lin + 6);
            cv.setSize(20 * 20);
            s.setRowView(lin + 6, cv);

            s.mergeCells(1, lin, 6, lin);
            s.mergeCells(1, lin + 1, 6, lin + 1);
            s.mergeCells(1, lin + 5, 3, lin + 5);
            s.mergeCells(4, lin + 5, 6, lin + 5);
            s.mergeCells(1, lin + 6, 3, lin + 6);
            s.mergeCells(4, lin + 6, 6, lin + 6);
            s.mergeCells(1, lin + 11, 6, lin + 11);

            HeaderFooter footer = new HeaderFooter();
            footer.getRight().append("Página ");
            footer.getRight().appendPageNumber();
            footer.getRight().append("/");
            footer.getRight().appendTotalPages();

            s.getSettings().setPrintArea(1, 2, 6, lin + 11);
            s.getSettings().setFooter(footer);
            s.getSettings().setScaleFactor(100);
            s.getSettings().setFitWidth(1);
            
//            geraAR(p);
//            if(geraOutorgados){
//                geraListaOutorgados(p);
//            }
//
//            arqExcel.write();
//            arqExcel.close();
//
//            if (externalContext.getResponseOutputStream() != null) {
//                externalContext.getResponseOutputStream().flush();
//                externalContext.getResponseOutputStream().close();
//            }
        } catch (Exception ex) {
            Logger.getLogger(PedidosBean.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            saida = false;
        }
        return saida;
    }

//    public boolean geraCarta(VwPedidos p) {
//        Pedidos p1 = new PedidosDAO().lePedido(p.getVwPedidosPK().getCdCli(), p.getVwPedidosPK().getDtBase());
//        return geraCarta(p1);
//    }

    public void geraListaOutorgados(Pedidos p) {
        try {
            //definição de formatos
            //cabeçalho
            WritableFont arial16bold = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD);
            arial16bold.setColour(Colour.BLACK);
            WritableFont arial14bold = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            arial14bold.setColour(Colour.BLACK);
            WritableFont arial10bold = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10bold.setColour(Colour.BLACK);
            WritableFont arial10 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
            arial10.setColour(Colour.BLACK);
            WritableCellFormat normalStyle = new WritableCellFormat(arial10);
            normalStyle.setWrap(false);
            WritableCellFormat cabecalhoStyleBold = new WritableCellFormat(arial10bold);
            cabecalhoStyleBold.setWrap(false);
            WritableCellFormat cabecalhoStyleBold14 = new WritableCellFormat(arial14bold);
            cabecalhoStyleBold14.setWrap(false);
            cabecalhoStyleBold14.setAlignment(Alignment.CENTRE);
            WritableCellFormat cabecalhoStyleBold16 = new WritableCellFormat(arial16bold);
            cabecalhoStyleBold16.setWrap(false);
            WritableCellFormat normalStyleRight = new WritableCellFormat(arial10);
            normalStyleRight.setWrap(false);
            normalStyleRight.setAlignment(Alignment.RIGHT);
            WritableCellFormat cabecalhoStyleJustify = new WritableCellFormat(arial10);
            cabecalhoStyleJustify.setWrap(false);
            cabecalhoStyleJustify.setAlignment(Alignment.JUSTIFY);
            WritableCellFormat cabecalhoStyleBoldRight = new WritableCellFormat(arial10bold);
            cabecalhoStyleBoldRight.setWrap(false);
            cabecalhoStyleBoldRight.setAlignment(Alignment.RIGHT);

            WritableSheet s = arqExcel.createSheet("Outorgados", arqExcel.getNumberOfSheets());

            //s.addCell(new Label(1, 2, "Banco do Brasil S.A.", cabecalhoStyleBold16));
            WritableImage image = new WritableImage(1, 2, 2, 2, new File("..\\applications\\cartaauditoria\\resources\\img\\LogoBB.png")); //web
            //WritableImage image = new WritableImage(1, 2, 2, 2, new File("C:\\Users\\PETERSON TANIGAKI\\Desktop\\cartaauditoria_nova\\src\\main\\resources\\img\\LogoBB.png"));
            //WritableImage image = new WritableImage(1, 2, 2, 2, new File("C:\\Projetos\\CartaAuditoriaWeb\\Carta de Auditoria\\cartaauditoria_nova\\src\\main\\resources\\img\\LogoBB.png"));
            //WritableImage image = new WritableImage(1, 2, 2, 2, new File("C:\\Users\\f8315465\\Desktop\\PROJETOS - 2019\\CARTA DE AUDITORIA - 2019\\cartaauditoria_nova\\src\\main\\resources\\img\\LogoBB.png"));
            image.setImageAnchor(WritableImage.NO_MOVE_OR_SIZE_WITH_CELLS); 
            s.addImage(image);
            
            s.addCell(new Label(1, 4, "São Paulo, " + new SimpleDateFormat("dd 'de' MMMM 'de' yyyy").format(new Date()), normalStyleRight));
            s.addCell(new Label(6, 3, "Carta nº " + new SimpleDateFormat("yyyy").format(new Date()) + "/" + String.format("%09d", p.getCdCarta()), normalStyleRight));
            s.addCell(new Label(1, 6, "À", normalStyle));
            s.addCell(new Label(1, 7, nmCli, cabecalhoStyleBold));
            s.addCell(new Label(1, 8, nmEndereco + " - " + nmBairro + " - " + nmMunicipio + " - CEP " + nmCep, normalStyle));
            s.addCell(new Label(1, 14, "RELAÇÃO DE OUTORGADOS PARA FINS DE AUDITORIA".toUpperCase(), cabecalhoStyleBold14));
            s.addCell(new Label(1, 18, "Atendendo à solicitação de nosso cliente abaixo descrito, fornecemos a seguir, a relação de outorgados por ele mantidos neste Banco, com posição em " + new SimpleDateFormat("dd/MM/yyyy").format(p.getPedidosPK().getDtBase()) + ":", cabecalhoStyleJustify));
            if(tpPessoa == 2){
                s.addCell(new Label(1, 21, "Empresa:", cabecalhoStyleBoldRight));
                s.addCell(new Label(1, 22, "CNPJ:", cabecalhoStyleBoldRight));
            }else{
                s.addCell(new Label(1, 21, "Nome:", cabecalhoStyleBoldRight));
                s.addCell(new Label(1, 22, "CPF:", cabecalhoStyleBoldRight));
            }
            s.addCell(new Label(2, 21, nmCli, normalStyle));
            s.addCell(new Label(2, 22, cnpj, normalStyle));

            CellView cv = s.getRowView(18);
            cv.setSize(40 * 20);//altura da linha = altura desejada em pontos x 20
            s.setRowView(18, cv);

            s.mergeCells(1, 4, 6, 4);
            s.mergeCells(1, 7, 6, 7);
            s.mergeCells(1, 8, 6, 8);
            s.mergeCells(1, 14, 6, 14);
            s.mergeCells(1, 18, 6, 18);

            cv = s.getColumnView(0);
            cv.setSize(2 * 256);//largura da coluna = largura desejada em caracteres x 256
            s.setColumnView(0, cv);
            cv = s.getColumnView(1);
            cv.setSize(30 * 256);
            s.setColumnView(1, cv);
            cv = s.getColumnView(2);
            cv.setSize(10 * 256);
            s.setColumnView(2, cv);
            cv = s.getColumnView(3);
            cv.setSize(20 * 256);
            s.setColumnView(3, cv);
            cv = s.getColumnView(4);
            cv.setSize(25 * 256);
            s.setColumnView(4, cv);
            cv = s.getColumnView(5);
            cv.setSize(25 * 256);
            s.setColumnView(5, cv);
            cv = s.getColumnView(6);
            cv.setSize(25 * 256);
            s.setColumnView(6, cv);

            NumberFormat monetario = new NumberFormat("$ #,##0.00");
            WritableCellFormat numFormat = new WritableCellFormat(arial10, monetario);
            WritableCellFormat numFormatBold = new WritableCellFormat(arial10bold, monetario);
            numFormatBold.setBackground(Colour.GRAY_25);

            WritableCellFormat produtoStyleBold = new WritableCellFormat(arial10bold);
            produtoStyleBold.setWrap(false);
            produtoStyleBold.setBackground(Colour.GRAY_25);
            WritableCellFormat produtoStyleBoldRight = new WritableCellFormat(arial10bold);
            produtoStyleBoldRight.setWrap(false);
            produtoStyleBoldRight.setBackground(Colour.GRAY_25);
            produtoStyleBoldRight.setAlignment(Alignment.RIGHT);

            //dados
            int lincab = 25;
            int lin = 27;

            List<Outorgados> listaProcuradores = new ArrayList<Outorgados>();
            List<Outorgados> listaSaldoExtrato = new ArrayList<Outorgados>();
            listaProcuradores = new OutorgadosDAO().listaProcuradores(p.getPedidosPK());
            listaSaldoExtrato = new OutorgadosDAO().listaSaldoExtrato(p.getPedidosPK());
            if (listaProcuradores.isEmpty() && listaSaldoExtrato.isEmpty()) {
                s.addCell(new Label(1, lincab, "Nada consta.", cabecalhoStyleBold14));
                s.mergeCells(1, lincab, 6, lincab);
                lin = lincab;
            } else {
                if(!listaProcuradores.isEmpty()){
                    s.addCell(new Label(1, lincab, "OUTORGADOS ", produtoStyleBold));
                    s.addCell(new Label(2, lincab, "", produtoStyleBold));
                    s.addCell(new Label(3, lincab, "", produtoStyleBold));
                    s.addCell(new Label(4, lincab, "", produtoStyleBold));
                    s.addCell(new Label(5, lincab, "CPF/CNPJ", produtoStyleBoldRight));
                    s.addCell(new Label(6, lincab, "", produtoStyleBold));
                    s.mergeCells(1, lincab, 4, lincab);
                    lin = lincab + 1;
                    for (Outorgados r : listaProcuradores) {
                        s.addCell(new Label(1, lin, r.getNmOtgdo(), normalStyle));
                        s.addCell(new Label(5, lin, r.getCpfCnpjOtgdo(), normalStyleRight));
                        lin++;
                    }
                    lincab = lin+1;
                }
                if(!listaSaldoExtrato.isEmpty()){
                    s.addCell(new Label(1, lincab, "AUTORIZADO A SOLICITAR SALDO E EXTRATO", produtoStyleBold));
                    s.addCell(new Label(2, lincab, "", produtoStyleBold));
                    s.addCell(new Label(3, lincab, "", produtoStyleBold));
                    s.addCell(new Label(4, lincab, "", produtoStyleBold));
                    s.addCell(new Label(5, lincab, "CPF/CNPJ", produtoStyleBoldRight));
                    s.addCell(new Label(6, lincab, "", produtoStyleBold));
                    s.mergeCells(1, lincab, 4, lincab);
                    lin = lincab + 1;
                    for (Outorgados r : listaSaldoExtrato) {
                        s.addCell(new Label(1, lin, r.getNmOtgdo(), normalStyle));
                        s.addCell(new Label(5, lin, r.getCpfCnpjOtgdo(), normalStyleRight));
                        lin++;
                    }
                    lin++;
                }
                
            }

            lin = lin + 4;

            //Rodapé
            WritableCellFormat rodapeStyleCenter = new WritableCellFormat(arial10);
            rodapeStyleCenter.setWrap(false);
            rodapeStyleCenter.setAlignment(Alignment.CENTRE);
            WritableCellFormat rodapeStyleBoldCenter = new WritableCellFormat(arial10bold);
            rodapeStyleBoldCenter.setWrap(false);
            rodapeStyleBoldCenter.setAlignment(Alignment.CENTRE);

            s.addCell(new Label(1, lin, "BANCO DO BRASIL S.A.", rodapeStyleBoldCenter));
            s.addCell(new Label(1, lin + 1, nmDep.toUpperCase(), rodapeStyleBoldCenter));
            s.addCell(new Label(1, lin + 5, "___________________________________________", rodapeStyleCenter));
            s.addCell(new Label(4, lin + 5, "___________________________________________", rodapeStyleCenter));
            s.addCell(new Label(1, lin + 6, "Gerente de Grupo UA", rodapeStyleCenter));
            s.addCell(new Label(4, lin + 6, "Assistente A UA", rodapeStyleCenter));

            cv = s.getRowView(lin + 6);
            cv.setSize(20 * 20);
            s.setRowView(lin + 6, cv);

            s.mergeCells(1, lin, 6, lin);
            s.mergeCells(1, lin + 1, 6, lin + 1);
            s.mergeCells(1, lin + 5, 3, lin + 5);
            s.mergeCells(4, lin + 5, 6, lin + 5);
            s.mergeCells(1, lin + 6, 3, lin + 6);
            s.mergeCells(4, lin + 6, 6, lin + 6);

            HeaderFooter footer = new HeaderFooter();
            footer.getRight().append("Página ");
            footer.getRight().appendPageNumber();
            footer.getRight().append("/");
            footer.getRight().appendTotalPages();

            s.getSettings().setPrintArea(1, 2, 6, lin + 6);
            s.getSettings().setFooter(footer);
            s.getSettings().setScaleFactor(100);
            s.getSettings().setFitWidth(1);

        } catch (Exception ex) {
            Logger.getLogger(PedidosBean.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public void geraAR(Pedidos p){
        try{
            //definição de formatos
            //cabeçalho
            WritableFont arial20bold = new WritableFont(WritableFont.ARIAL, 20, WritableFont.BOLD);
            arial20bold.setColour(Colour.BLACK);
            WritableFont arial16bold = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD);
            arial16bold.setColour(Colour.BLACK);
            WritableFont arial11 = new WritableFont(WritableFont.ARIAL, 11, WritableFont.NO_BOLD);
            arial11.setColour(Colour.BLACK);
            WritableFont arial10 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
            arial10.setColour(Colour.BLACK);
            WritableFont arial9 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD);
            arial9.setColour(Colour.BLACK);
            WritableFont arial9Italic = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD);
            arial9Italic.setColour(Colour.BLACK);
            arial9Italic.setItalic(true);
            
            WritableCellFormat normalStyle11 = new WritableCellFormat(arial11);
            normalStyle11.setWrap(false);
            WritableCellFormat normalStyle = new WritableCellFormat(arial10);
            normalStyle.setWrap(false);
            normalStyle.setVerticalAlignment(VerticalAlignment.CENTRE);
            WritableCellFormat normalStyle9 = new WritableCellFormat(arial9);
            normalStyle9.setWrap(false);
            WritableCellFormat cabecalhoStyleBold20 = new WritableCellFormat(arial20bold);
            cabecalhoStyleBold20.setWrap(false);
            cabecalhoStyleBold20.setAlignment(Alignment.CENTRE);
            cabecalhoStyleBold20.setVerticalAlignment(VerticalAlignment.CENTRE);
            WritableCellFormat cabecalhoStyleBold16 = new WritableCellFormat(arial16bold);
            cabecalhoStyleBold16.setWrap(false);
            cabecalhoStyleBold16.setAlignment(Alignment.CENTRE);
            cabecalhoStyleBold16.setVerticalAlignment(VerticalAlignment.CENTRE);
            WritableCellFormat italicStyle9Center = new WritableCellFormat(arial9Italic);
            italicStyle9Center.setWrap(false);
            italicStyle9Center.setAlignment(Alignment.CENTRE);
            WritableCellFormat normalStyleCenter = new WritableCellFormat(arial10);
            normalStyleCenter.setWrap(false);
            normalStyleCenter.setAlignment(Alignment.CENTRE);
            WritableCellFormat verticalStyle11 = new WritableCellFormat(arial11);
            verticalStyle11.setWrap(false);
            verticalStyle11.setAlignment(Alignment.CENTRE);
            verticalStyle11.setVerticalAlignment(VerticalAlignment.CENTRE);
            verticalStyle11.setOrientation(Orientation.PLUS_90);
//            verticalStyle11.setBorder(Border.ALL, BorderLineStyle.THIN);

            WritableSheet s = arqExcel.createSheet("AR", arqExcel.getNumberOfSheets());
            
            /*WritableImage image = new WritableImage(
                2, 1,   //column, row
                2, 2,   //width, height in terms of number of cells
                new File("E:\\Projeto Atacado\\Carta de Auditoria\\correios-logo-5.png"));*/ //Supports only 'png' images 
            
            //WritableImage image = new WritableImage(2, 1, 2, 2, new File("E:\\Projeto Atacado\\Carta de Auditoria\\correios-logo-5.png")); //local
            WritableImage image = new WritableImage(2, 1, 2, 2, new File("..\\applications\\cartaauditoria\\resources\\img\\correios-logo-5.png")); //web
            //WritableImage image = new WritableImage(1, 2, 2, 2, new File("C:\\Users\\PETERSON TANIGAKI\\Desktop\\cartaauditoria_nova\\src\\main\\resources\\img\\correios-logo-5.png"));
            //WritableImage image = new WritableImage(2, 1, 2, 2, new File("C:\\Projetos\\CartaAuditoriaWeb\\Carta de Auditoria\\cartaauditoria_nova\\src\\main\\resources\\img\\correios-logo-5.png"));
            //WritableImage image = new WritableImage(2, 1, 2, 2, new File("C:\\Users\\f8315465\\Desktop\\PROJETOS - 2019\\CARTA DE AUDITORIA - 2019\\cartaauditoria_nova\\src\\main\\resources\\img\\correios-logo-5.png"));
            image.setImageAnchor(WritableImage.NO_MOVE_OR_SIZE_WITH_CELLS); 
            s.addImage(image);
            
            s.addCell(new Label(1, 1, "(ÁREA DE COLA NO VERSO)", verticalStyle11));
            s.addCell(new Label(4, 1, "AVISO DE RECEBIMENTO", cabecalhoStyleBold16));
            s.addCell(new Label(10, 1, "AR", cabecalhoStyleBold20));
            s.addCell(new Label(2, 3, "DESTINATÁRIO", normalStyle11));
            s.addCell(new Label(2, 9, "(CÓDIGO DE BARRAS OU Nº DE REGISTRO DO OBJETO)", italicStyle9Center));
            s.addCell(new Label(2, 12, "ENDEREÇO PARA DEVOLUÇÃO DO AR", normalStyle11));
            s.addCell(new Label(12, 1, "DATA DE POSTAGEM", normalStyleCenter));
            s.addCell(new Label(12, 2, "______/______/______", normalStyleCenter));
            s.addCell(new Label(12, 3, "UNIDADE DE POSTAGEM", normalStyleCenter));
            s.addCell(new Label(12, 5, "CARIMBO", normalStyleCenter));
            s.addCell(new Label(12, 6, "UNIDADE DE ENTREGA", normalStyleCenter));
            s.addCell(new Label(2, 18, "TENTATIVAS DE ENTREGA", normalStyleCenter));
            s.addCell(new Label(3, 20, "1ª ______/______/______", normalStyle11));
            s.addCell(new Label(3, 22, "2ª ______/______/______", normalStyle11));
            s.addCell(new Label(3, 24, "3ª ______/______/______", normalStyle11));
            s.addCell(new Label(6, 20, "______:______ h", normalStyle11));
            s.addCell(new Label(6, 22, "______:______ h", normalStyle11));
            s.addCell(new Label(6, 24, "______:______ h", normalStyle11));
            s.addCell(new Label(8, 18, "DECLARAÇÃO DE CONTEÚDO (SUJEITO À VERIFICAÇÃO)", normalStyle));
            s.addCell(new Label(8, 20, "MOTIVO DE DEVOLUÇÃO", normalStyleCenter));
            s.addCell(new Label(9, 21, "1 - Mudou-se", normalStyle9));
            s.addCell(new Label(9, 22, "2 - Endereço insuficiente", normalStyle9));
            s.addCell(new Label(9, 23, "3 - Não existe o número", normalStyle9));
            s.addCell(new Label(9, 24, "4 - Desconhecido", normalStyle9));
            s.addCell(new Label(9, 25, "5 - Recusado", normalStyle9));
            s.addCell(new Label(11, 21, "6 - Não procurado", normalStyle9));
            s.addCell(new Label(11, 22, "7 - Ausente", normalStyle9));
            s.addCell(new Label(11, 23, "8 - Falecido", normalStyle9));
            s.addCell(new Label(11, 24, "9 - Outros", normalStyle9));
            s.addCell(new Label(12, 20, "RUBRICA E MATRÍCULA", normalStyleCenter));
            s.addCell(new Label(12, 21, "DO CARTEIRO", normalStyleCenter));
            s.addCell(new Label(12, 26, "DATA DE ENTREGA", normalStyleCenter));
            s.addCell(new Label(12, 27, "______/______/______", normalStyleCenter));
            s.addCell(new Label(12, 28, "Nº DOC. DE IDENTIDADE", normalStyleCenter));
            s.addCell(new Label(2, 26, "ASSINATURA DO RECEBEDOR:", normalStyle));
            s.addCell(new Label(2, 28, "NOME LEGÍVEL DO RECEBEDOR:", normalStyle));
            
            s.addCell(new Label(2, 4, nmCli, normalStyle11));
            s.addCell(new Label(2, 5, nmEndereco + " - " + nmBairro, normalStyle11));
            s.addCell(new Label(2, 6, nmMunicipio + " - CEP " + nmCep, normalStyle11));
            s.addCell(new Label(2, 13, "BANCO DO BRASIL S.A.", normalStyle11));
            if(enderecoDep != null){
                s.addCell(new Label(2, 14, String.format("%04d", enderecoDep.getCdPrefDep())+"-"+enderecoDep.getDvPrefDep()+" "+enderecoDep.getNmDep(), normalStyle11));
                s.addCell(new Label(2, 15, enderecoDep.getTxLgr().trim()+" "+enderecoDep.getTxCmpt().trim(), normalStyle11));
                s.addCell(new Label(2, 16, enderecoDep.getTxBai().trim(), normalStyle11));
                String nmCepDep = String.format("%08d", enderecoDep.getNrCep());
                nmCepDep = nmCepDep.substring(0, 5) + "-" + nmCepDep.substring(5, 8);
                String nmMunDep = enderecoDep.getTxMun() + " (" + enderecoDep.getTxUf() + ")";
                s.addCell(new Label(2, 17, nmMunDep + " - CEP " + nmCepDep, normalStyle11));
            }
            s.addCell(new Label(8, 19, "Carta nº " + new SimpleDateFormat("yyyy").format(new Date()) + "/" + String.format("%09d", p.getCdCarta()), normalStyle11));

            CellView cv = s.getColumnView(0);
            cv.setSize(2 * 256 + 181);//largura da coluna = largura desejada em caracteres x 256
            s.setColumnView(0, cv);
            cv = s.getColumnView(1);
            cv.setSize(8 * 256 + 181);
            s.setColumnView(1, cv);
            cv = s.getColumnView(2);
            cv.setSize(5 * 256 + 181);
            s.setColumnView(2, cv);
            cv = s.getColumnView(3);
            cv.setSize(16 * 256 + 181);
            s.setColumnView(3, cv);
            cv = s.getColumnView(4);
            cv.setSize(8 * 256 + 181);
            s.setColumnView(4, cv);
            cv = s.getColumnView(5);
            cv.setSize(5 * 256 + 181);
            s.setColumnView(5, cv);
            cv = s.getColumnView(6);
            cv.setSize(16 * 256 + 181);
            s.setColumnView(6, cv);
            cv = s.getColumnView(7);
            cv.setSize(5 * 256 + 181);
            s.setColumnView(7, cv);
            cv = s.getColumnView(8);
            cv.setSize(2 * 256 + 181);
            s.setColumnView(8, cv);
            cv = s.getColumnView(9);
            cv.setSize(20 * 256 + 181);
            s.setColumnView(9, cv);
            cv = s.getColumnView(10);
            cv.setSize(2 * 256 + 181);
            s.setColumnView(10, cv);
            cv = s.getColumnView(11);
            cv.setSize(15 * 256 + 181);
            s.setColumnView(11, cv);
            cv = s.getColumnView(12);
            cv.setSize(23 * 256 + 181);
            s.setColumnView(12, cv);

            WritableCellFormat wcf = new WritableCellFormat(s.getWritableCell(1, 1).getCellFormat());
            wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
            s.getWritableCell(1,1).setCellFormat(wcf);
            s.mergeCells(1, 1, 1, 29);
//            borderRegion(1,1,1,29,BorderLineStyle.THIN, s);

            wcf = new WritableCellFormat(s.getWritableCell(4, 1).getCellFormat());
            wcf.setBorder(Border.TOP, BorderLineStyle.THIN);
            wcf.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
            s.getWritableCell(4,1).setCellFormat(wcf);
            s.mergeCells(4,1,9,2);

            wcf = new WritableCellFormat(s.getWritableCell(10, 1).getCellFormat());
            wcf.setBorder(Border.TOP, BorderLineStyle.THIN);
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
            wcf.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
            s.getWritableCell(10,1).setCellFormat(wcf);
            s.mergeCells(10,1,11,2);

/*            s.mergeCells(2,3,11,3);
            s.mergeCells(2,4,11,4);
            s.mergeCells(2,5,11,5);
            s.mergeCells(2,6,11,6);*/
            s.mergeCells(2,9,11,9);
/*            s.mergeCells(2,12,11,12);
            s.mergeCells(2,13,11,13);
            s.mergeCells(2,14,11,14);
            s.mergeCells(2,15,11,15);
            s.mergeCells(2,16,11,16);
            s.mergeCells(2,17,11,17);*/

            wcf = new WritableCellFormat(s.getWritableCell(2,18).getCellFormat());
            wcf.setBorder(Border.TOP, BorderLineStyle.THIN);
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
            wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
            s.getWritableCell(2,18).setCellFormat(wcf);
            s.mergeCells(2, 18, 7, 18);

            wcf = new WritableCellFormat(s.getWritableCell(8,18).getCellFormat());
            wcf.setBorder(Border.TOP, BorderLineStyle.THIN);
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
            wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
            s.getWritableCell(8,18).setCellFormat(wcf);
            s.mergeCells(8,18,12,18);

            wcf = new WritableCellFormat(s.getWritableCell(8,19).getCellFormat());
            wcf.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
            wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
            s.getWritableCell(8,19).setCellFormat(wcf);
            s.mergeCells(8,19,12,19);

            wcf = new WritableCellFormat(s.getWritableCell(8,20).getCellFormat());
            wcf.setBorder(Border.TOP, BorderLineStyle.THIN);
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
            wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
            s.getWritableCell(8,20).setCellFormat(wcf);
            s.mergeCells(8,20,11,20);

            wcf = new WritableCellFormat(s.getWritableCell(2,26).getCellFormat());
            wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
            s.getWritableCell(2,26).setCellFormat(wcf);
            s.mergeCells(2,26,11,27);

            wcf = new WritableCellFormat(s.getWritableCell(2,28).getCellFormat());
            wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
            s.getWritableCell(2,28).setCellFormat(wcf);
            s.mergeCells(2,28,11,29);
            
            borderRegion(2,1,3,2,true, false, true, false, BorderLineStyle.THIN,s);
            borderRegion(12,1,12,2,BorderLineStyle.THIN,s);
            borderRegion(12,3,12,4,BorderLineStyle.THIN,s);
            borderRegion(12,5,12,17,BorderLineStyle.THIN,s);
            borderRegion(12,20,12,25,BorderLineStyle.THIN,s);
            borderRegion(12,26,12,27,BorderLineStyle.THIN,s);
            borderRegion(12,28,12,29,BorderLineStyle.THIN,s);
            borderRegion(2,19,7,25,false,true,true,true,BorderLineStyle.THIN,s);
            borderRegion(8,21,11,25,false,true,true,true,BorderLineStyle.THIN,s);

            s.getSettings().setPrintArea(1, 1, 12,29);
            s.getSettings().setScaleFactor(100);
            s.getSettings().setFitWidth(1);
        } catch (Exception ex) {
            Logger.getLogger(PedidosBean.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
    private void borderRegion(int col1, int lin1, int col2, int lin2, BorderLineStyle lineStyle, WritableSheet ws){
        borderRegion(col1,lin1,col2,lin2,true,true,true,true,lineStyle,ws);
    }
    
    private void borderRegion(int col1, int lin1, int col2, int lin2, boolean top, boolean right, boolean bottom, boolean left, BorderLineStyle lineStyle, WritableSheet ws){
        try{
            WritableFont arial10 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
            arial10.setColour(Colour.BLACK);
            WritableCellFormat normalStyle = new WritableCellFormat(arial10);
            WritableCellFormat wcf = null;
            if(top){
            for(int a = col1; a <= col2; a++){
                if(ws.getCell(a, lin1).getCellFormat() == null){
                    ws.addCell(new Blank(a, lin1,normalStyle));
                }
                wcf = new WritableCellFormat(ws.getWritableCell(a, lin1).getCellFormat());
                wcf.setBorder(Border.TOP, lineStyle);
                ws.getWritableCell(a, lin1).setCellFormat(wcf);
            }}
            if(right){
            for(int a = lin1; a <= lin2; a++){
                if(ws.getCell(col2, a).getCellFormat() == null){
                    ws.addCell(new Blank(col2, a,normalStyle));
                }
                wcf = new WritableCellFormat(ws.getWritableCell(col2, a).getCellFormat());
                wcf.setBorder(Border.RIGHT, lineStyle);
                ws.getWritableCell(col2, a).setCellFormat(wcf);
            }}
            if(bottom){
            for(int a = col1; a <= col2; a++){
                if(ws.getCell(a, lin2).getCellFormat() == null){
                    ws.addCell(new Blank(a, lin2,normalStyle));
                }
                wcf = new WritableCellFormat(ws.getWritableCell(a, lin2).getCellFormat());
                wcf.setBorder(Border.BOTTOM, lineStyle);
                ws.getWritableCell(a, lin2).setCellFormat(wcf);
            }}
            if(left){
            for(int a = lin1; a <= lin2; a++){
                if(ws.getCell(col1, a).getCellFormat() == null){
                    ws.addCell(new Blank(col1, a,normalStyle));
                }
                wcf = new WritableCellFormat(ws.getWritableCell(col1, a).getCellFormat());
                wcf.setBorder(Border.LEFT, lineStyle);
                ws.getWritableCell(col1, a).setCellFormat(wcf);
            }}
        }catch(Exception ex){
            Logger.getLogger(PedidosBean.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
    public void apagaPedido(VwPedidos p) {
        limpaDados();
        LogPedidos lp = new LogPedidos();
        lp.setCdCli(p.getVwPedidosPK().getCdCli());
        lp.setCdPrf(Integer.parseInt(usuario.getCodigoPrefixoDependencia()));
        lp.setDtBase(p.getVwPedidosPK().getDtBase());
        lp.setNmChave(usuario.getChave());
        FacesMessage fm = null;
        if (pedidosDAO.remove(p)) {
            atualizar();
            lp.setNmTipo("DEL");
            fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Pedido apagado.");
        } else {
            lp.setNmTipo("ERR");
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Houve algum erro que impediu a remoção do pedido. Por favor, tente novamente ou contate a equipe de desenvolvimento.");
        }
        FacesContext.getCurrentInstance().addMessage(null, fm);
        if (lp.getNmTipo() != null) {
            logPedidosDAO.adiciona(lp);
        }
    }

    public void apagaPedidoLote(PedidosLote p) {
        limpaDados();
        LogPedidos lp = new LogPedidos();
        lp.setCdCli(p.getPedidosLotePK().getCdRef());
        lp.setCdPrf(Integer.parseInt(usuario.getCodigoPrefixoDependencia()));
        lp.setDtBase(p.getPedidosLotePK().getDtBase());
        lp.setNmChave(usuario.getChave());
        FacesMessage fm = null;
        if (new PedidosLoteDAO().remove(p)) {
            atualizar();
            lp.setNmTipo("LOTE - DEL");
            fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Pedido em lote apagado.");
        } else {
            lp.setNmTipo("LOTE - ERR");
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Houve algum erro que impediu a remoção do pedido. Por favor, tente novamente ou contate a equipe de desenvolvimento.");
        }
        FacesContext.getCurrentInstance().addMessage(null, fm);
        if (lp.getNmTipo() != null) {
            logPedidosDAO.adiciona(lp);
        }
    }

    public void limpaDados() {
        cdCli = null;
        cdCnpj = null;
        dtBase = null;
        cdFlow = null;
    }

    public Integer getCdCli() {
        return cdCli;
    }

    public void setCdCli(Integer cdCli) {
        this.cdCli = cdCli;
    }

    public Date getDtBase() {
        return dtBase;
    }

    public void setDtBase(Date dtBase) {
        this.dtBase = dtBase;
    }

    public List<DataFormatada> getDtsBase() {
        return dtsBase;
    }

    public void setDtsBase(List<DataFormatada> dtsBase) {
        this.dtsBase = dtsBase;
    }

    public Integer getCdFlow() {
        return cdFlow;
    }

    public void setCdFlow(Integer cdFlow) {
        this.cdFlow = cdFlow;
    }

    public String getDtBaseStr() {
        return dtBaseStr;
    }

    public void setDtBaseStr(String dtBaseStr) {
        this.dtBaseStr = dtBaseStr;
    }

//    public List<Pedidos> getPedidosAProcessar() {
//        return pedidosAProcessar;
//    }
//
//    public void setPedidosAProcessar(List<Pedidos> pedidosAProcessar) {
//        this.pedidosAProcessar = pedidosAProcessar;
//    }
    public List<VwPedidos> getPedidosAProcessar() {
        return pedidosAProcessar;
    }

    public void setPedidosAProcessar(List<VwPedidos> pedidosAProcessar) {
        this.pedidosAProcessar = pedidosAProcessar;
    }

    public boolean isGeraOutorgados() {
        return geraOutorgados;
    }

    public void setGeraOutorgados(boolean geraOutorgados) {
        this.geraOutorgados = geraOutorgados;
    }

    public int getChkLote() {
        return chkLote;
    }

    public void setChkLote(int chkLote) {
        this.chkLote = chkLote;
    }

    public Integer getCdCnpj() {
        return cdCnpj;
    }

    public void setCdCnpj(Integer cdCnpj) {
        this.cdCnpj = cdCnpj;
    }

    public List<PedidosLote> getPedidosLoteLista() {
        return pedidosLoteLista;
    }

    public void setPedidosLoteLista(List<PedidosLote> pedidosLoteLista) {
        this.pedidosLoteLista = pedidosLoteLista;
    }

    public List<CapMciGrupos> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<CapMciGrupos> grupos) {
        this.grupos = grupos;
    }

    public Integer getCdGrupo() {
        return cdGrupo;
    }

    public void setCdGrupo(Integer cdGrupo) {
        this.cdGrupo = cdGrupo;
    }

    public List<String> pesquisar(String campoBusca) {

        if (campoBusca.length() == numCaracteresBusca || (campoBusca.length() > numCaracteresBusca && (mapaBusca == null || mapaBusca.isEmpty()))) {
            //daoAfinPesquisa = new AfinPesquisaDAO();
//            mapaBusca = daoAfinPesquisa.mapBusca(campoBusca.toUpperCase());
            mapaBusca = new CapMciGruposDAO().mapBusca(campoBusca.toUpperCase(),cdDep);
        }

        return autoComplete(campoBusca, mapaBusca);
    }

    public List<String> autoComplete(String combo, Map<String, Integer> mapa) {

        List<String> results = new ArrayList<String>();

        if (mapa != null && mapa.size() > 0) {
            for (Map.Entry<String, Integer> itCombo : mapa.entrySet()) {
                if (itCombo.getKey().toUpperCase().contains(combo.toUpperCase())) {
                    results.add(itCombo.getKey());
                }
            }
        }

        return results;
    }

    public Map<String, Integer> getMapaBusca() {
        return mapaBusca;
    }

    public String getCampoBusca() {
        return campoBusca;
    }

    public void setCampoBusca(String campoBusca) {
        this.campoBusca = campoBusca;
    }

    public void trocaLote(){
        campoBusca = "";
        cdCnpj = null;
        cdCli = null;
    }

    public void setUsuario(Usuario localUsuario) {
        this.usuario = localUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean isOkAcesso() {
        return okAcesso;
    }

    public boolean isOkEstatistica() {
        return okEstatistica;
    }

    public AuxFuncionarioAtacado getFunci() {
        return funci;
    }

    public void setFunci(AuxFuncionarioAtacado funci) {
        this.funci = funci;
    }

    public Atualizacoes getEnvio() {
        return envio;
    }

    public void setEnvio(Atualizacoes envio) {
        this.envio = envio;
    }

    public Atualizacoes getRecepcao() {
        return recepcao;
    }

    public void setRecepcao(Atualizacoes recepcao) {
        this.recepcao = recepcao;
    }

}
