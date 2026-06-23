/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.listener;

import br.com.bb.exxi.cartaauditoria_nova.beans.LoginBean;
import br.com.bb.exxi.cartaauditoria_nova.beans.PedidosBean;
import br.com.bb.exxi.cartaauditoria_nova.entidades.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author F9066619
 */
public class LoginPhaseListener implements PhaseListener{

	private static final long serialVersionUID = 1L;
	
        @Override
	public void afterPhase(PhaseEvent event){
        
        }
	
        @Override
	public void beforePhase(PhaseEvent event) 
	{
		FacesContext context = event.getFacesContext();
		LoginBean loginBean = context.getApplication().evaluateExpressionGet(context, "#{loginBean}", LoginBean.class);
                PedidosBean pedidosBean = context.getApplication().evaluateExpressionGet(context, "#{pedidosBean}", PedidosBean.class);
		
		//loginBean.acessaAplicacao(context);
                pedidosBean.setContext(context);
                if(pedidosBean.getUsuario() == null){
                    HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
                    HttpSession session = request.getSession();
                    Usuario localUsuario;
                    localUsuario = (Usuario) session.getAttribute("usuario");
                    if(localUsuario != null){
                        //pedidosBean.setUsuario(localUsuario);
                        pedidosBean.autenticacao(localUsuario);
                    }else{
                        Logger.getLogger(LoginPhaseListener.class.getName()).log(Level.SEVERE, "Carta de Auditoria - Usuário não identificado!");
                        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                        loginBean.enviaPaginaLogin(response, request);
                        request = (HttpServletRequest) context.getExternalContext().getRequest();
                        session = request.getSession();
                        localUsuario = (Usuario) session.getAttribute("usuario");
                        pedidosBean.autenticacao(localUsuario);
                    }
                }
                
	}

        @Override
	public PhaseId getPhaseId(){
		return PhaseId.RENDER_RESPONSE;		
	}
}
