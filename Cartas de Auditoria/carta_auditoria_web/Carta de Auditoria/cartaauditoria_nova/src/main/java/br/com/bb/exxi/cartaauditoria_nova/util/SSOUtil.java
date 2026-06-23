/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.util;

import br.com.bb.exxi.cartaauditoria_nova.entidades.Usuario;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.login.AccountExpiredException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;


/**
 *
 * @author F9066619
 */
public class SSOUtil {

	//private String servidorSSO = ConstantsSSO.SERVIDOR_SSO_PADRAO;
	//private String servidorSSODISEM = ConstantsSSO.SERVIDOR_SSO_DISEM;
        private String servidorSSO = ConstantsSSO.SERVIDOR_SSO;

	public String getCookieValue(Cookie[] cookies, String nomeCookie){
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equalsIgnoreCase(nomeCookie)) {
				return (cookie.getValue().toString());
			}
		}
		return null;
	}


	public Cookie excluirCookie(Cookie[] cookies, String nomeCookie){
		Cookie remove = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equalsIgnoreCase(nomeCookie)) {
					remove = cookies[i];
					break;
				}
			}
			remove.setMaxAge(0);
		}
		return remove;
	}


	@SuppressWarnings("deprecation")
	public Usuario getAtributosUsuario(String tokenId){
		Client client = Client.create();
		client.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);                              
		String response = "";
		try {
			//WebResource resource = client.resource(ConstantsSSO.PROTOCOLO + "://" + servidorSSO + "/sso/identity/attributes?subjectid="+ tokenId);
			//WebResource resource = client.resource(servidorSSODISEM + "/" + tokenId);
                        
                        //inicio velhos metodos de autenticação token  
                            /*
                            WebResource resource = client.resource(ConstantsSSO.SERVIDOR_SSO + tokenId);
                            response = resource.get(String.class);
                            ClientResponse clientResponse = resource.get(ClientResponse.class);
                            System.out.println("WebResource Resposta: " + response);
                            */
                        //fim   velhos metodos de autenticação token    
                            
                        //inicio novos metodos de autenticação token NÃO FUNCIONA                           
//                            WebResource resource = client.resource("https://sso.intranet.bb.com.br/sso/identity/attributes");
//                            response = resource.header("Cookie", "BBSSOToken" + "=" + tokenId).type("application/x-www-form-urlencoded").accept("*/*").post(String.class, "");
//                            ClientResponse clientResponse = resource.header("Cookie", "BBSSOToken" + "=" + tokenId).type("application/x-www-form-urlencoded").accept("*/*").post(ClientResponse.class);
//                            int status = clientResponse.getStatus();
//                            if (status == 200) {
//                                String resposta = clientResponse.getEntity(String.class);
//                                System.out.println("Resposta: " + resposta);
//                            } else {
//                                System.out.println("Erro HTTP: " + status);
//                            }                            
                        //fim novos metodos de autenticação token  NÃO FUNCIONA
                        
                        /*inicio novos metodos de autenticação token FUNCIONANDO*/ 
                            HttpsURLConnection conn = null;
                            SSLContext sc = SSLContext.getInstance("SSL");
                            sc.init(null, trustAllCerts, new java.security.SecureRandom());
                            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                            //System.setProperty("https.protocols", "TLSv1.2");
                            conn = (HttpsURLConnection) new URL("https://sso.intranet.bb.com.br/sso/identity/attributes").openConnection();
                            conn.setRequestMethod("POST");
                            conn.setConnectTimeout(900);
                            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            conn.setRequestProperty("Cookie", "BBSSOToken="+tokenId);
                            conn.setDoOutput(true);
                            PrintStream printStream = new PrintStream(conn.getOutputStream());
                            String input = "{}";
                            printStream.println(input);
                            conn.connect();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            StringBuilder responseBody = new StringBuilder();
                            String result= "";
                            while ((result = reader.readLine()) != null) {
                                responseBody.append(result +"\n");
                            }
                            
                            response=responseBody.toString();
                            //System.out.println(" response"+response.toString());
                        /*fim novos metodos de autenticação token FUNCIONANDO*/ 
                        			
			//if (clientResponse.getClientResponseStatus().getStatusCode() == 200) {  /*antigo hhtp*/
                        if (conn.getResponseCode() == 200) {
                        	String resposeString = response.toString();
				
				int index1 = resposeString.indexOf("userdetails.role=id=");
				int index2 = resposeString.indexOf("userdetails.attribute.name=");
				String acessos = resposeString.substring(index1, index2);
				String atributos = resposeString.substring(index2, response.length());
				String splitAcessos[] = acessos.split("\n");
				StringTokenizer st = new StringTokenizer(atributos, "\n");
				Map<String, String> map = new HashMap<String, String>();
			
				while (st.hasMoreElements()) {
					try{
						String key = ((String) st.nextElement()).toLowerCase();
						if (key.indexOf("bb-filtergroup") >= 0) {
							key = (String) st.nextElement();
							while((key).indexOf("userdetails.attribute.value") >= 0){
								key = ((String) st.nextElement()).toLowerCase();
							}
						}

						String value = (String) st.nextElement();
                                                map.put(key.substring(27, key.length()), value.substring(28, value.length()));

					} catch(Exception e){
						//e.printStackTrace();
					}
				}
				
				String acessosUsuario = "";
				
				for (int i = 0; i < splitAcessos.length; i++) {
					acessosUsuario += splitAcessos[i].substring(20, splitAcessos[i].indexOf(",")) + ",";
				}				
				
				/* Cria usuario e seta propriedades */
				
				Usuario usuario = new Usuario();
				try {
					
					usuario.setAcessos(acessosUsuario);
					usuario.setChave(getAttribute("ibm-nativeid", response));
					usuario.setChaveCripto(getAttribute("chavecripto", response));
					usuario.setCodigoCliente(getAttribute("cd-cli", response));
					usuario.setCodigoComissao(getAttribute("codigocomissao", response));
					usuario.setCodigoComissaoUsuario(getAttribute("cd-cmss-usu", response));
					usuario.setCodigoDivisao(getAttribute("cd-eqp", response));
					usuario.setCodigoIdentificacaoDigitalUsuario(getAttribute("cd-idgl-usu", response));
					usuario.setCodigoInstituicao(getAttribute("cd-inst", response));
					usuario.setCodigoInstituicaoOrganizacional(getAttribute("cd-ior", response));
					usuario.setCodigoNucleo(getAttribute("cd-ncl", response));
					usuario.setCodigoPilar(getAttribute("codigopilar", response));
					usuario.setCodigoPrefixoDependencia(getAttribute("cd-pref-depe", response));
					usuario.setCodigoReferenciaOrganizacional(getAttribute("cd-ref-orgc", response));
					usuario.setCodigoTipoDependencia(getAttribute("cd-tip-dep", response));
					usuario.setCodigoTipoIdentificacaoDigital(getAttribute("cd-tip-idgl", response));
					usuario.setCodigoUorDependencia(getAttribute("cd-uor-dep", response));
					usuario.setDiretorioHome(getAttribute("homedirectory", response));
					usuario.setEmail(getAttribute("mail", response));
					usuario.setEnderecoResidencial(getAttribute("homepostaladdress", response));
					usuario.setGrupamento(getAttribute("grupamento", response));
					usuario.setIdentificadorUnico(getAttribute("uid", response));
					usuario.setIdNativoIBM(getAttribute("ibm-nativeid", response));
					usuario.setIdSessao(getAttribute("idsessao", response));
					usuario.setLoginShell(getAttribute("loginshell", response));
					usuario.setNome(getAttribute("nm-idgl", response).toUpperCase());
					usuario.setNomeCamelCase(getAttribute("nm-idgl", response));
					usuario.setNomeComum(getAttribute("cn", response));
					usuario.setNomeDistinto(getAttribute("dn", response));
					usuario.setNomeGuerra(getAttribute("displayname", response));
					usuario.setNumeroCPF(getAttribute("nr-cpf", response));
					usuario.setNumeroIdentificadorDeGrupo(getAttribute("gidnumber", response));
					usuario.setNumeroIdentificadorUnico(getAttribute("uidnumber", response));
					usuario.setPrefixoDependencia(getAttribute("prefixodependencia", response));
					usuario.setPrefixoDiretoria(getAttribute("prefixodiretoria", response));
					usuario.setPrefixoSuperEstadual(getAttribute("prefixosuperestadual", response));
					usuario.setResponsabilidadeFuncional(getAttribute("responsabilidadefuncional", response));
					usuario.setSenhaCripto(getAttribute("pwd", response));
					usuario.setSiglaUF(getAttribute("nomeuf", response));
					usuario.setSobrenome(getAttribute("sn", response));
					usuario.setTelefoneCelular(getAttribute("mobile", response));
					usuario.setTelefoneComercial(getAttribute("telephonenumber", response));
					usuario.setTelefoneResidencial(getAttribute("homephone", response));
					usuario.setTextoComissaoUsuario(getAttribute("tx-cmss-usu", response));
					usuario.setTipoDependencia(getAttribute("tipodependencia", response));
					usuario.setTokenId(resposeString.substring(21, index1).trim());
					
			
				} catch (Exception e) {
					usuario = null;
				}
				
				return usuario;
				
			} else {
				return null;
				
			}

			
		} catch (Exception e) {
		
			return null;
		
		}
	}
	
	public boolean verificaTokenValido(String tokenId){
		try{
			SSLContext sslcontext = SSLContext.getInstance( "TLS" );
			sslcontext.init( null, null, null );
			DefaultClientConfig config = new DefaultClientConfig();
			Map<String, Object> properties = config.getProperties();
			HTTPSProperties httpsProperties = new HTTPSProperties(
					new HostnameVerifier()
					{
						@Override
						public boolean verify( String s, SSLSession sslSession )
						{
							return true;
						}
					}, sslcontext
			);
			properties.put( HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, httpsProperties );
			
			Client client = Client.create(config);
			client.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);
			String response = "";
			boolean tokenValido = false;
		
			WebResource resource = client.resource(ConstantsSSO.PROTOCOLO + "://" + servidorSSO + "/sso/identity/isTokenValid?tokenid="
					+ tokenId);
			response = resource.get(String.class);
			tokenValido = Boolean.parseBoolean(response.substring(8, response.length()).trim());
			return tokenValido;
		//}
		//catch (UniformInterfaceException uie) {
			//Logger.getLogger(SSOUtil.class.getName()).log(Level.SEVERE, "Causa: " + uie.getMessage());
		//	return false;
		//}
		//catch (ClientHandlerException che) {
			//Logger.getLogger(SSOUtil.class.getName()).log(Level.SEVERE, "Causa: " + che.getMessage());
		//	return false;
		} catch (Exception e) {
			return false;
		}
	}


	public String autentica(String username, String password){
		Client client = Client.create();
		client.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);
		String response = "";

		try {
			WebResource resource = client.resource(ConstantsSSO.PROTOCOLO + "://" + servidorSSO + "/sso/identity/authenticate?username="
					+ username + "&password=" + password);
			response = resource.get(String.class);
			if (!response.toString().contains("Authentication Failed"))
			{
				return response.substring(9, response.length()).trim();
			}
			else
			{
				return null;
			}
		}
		catch (UniformInterfaceException uie) {
			//Logger.getLogger(SSOUtil.class.getName()).log(Level.SEVERE, "Causa: " + uie.getMessage());
			return null;
		}
		catch (ClientHandlerException che) {
			//Logger.getLogger(SSOUtil.class.getName()).log(Level.SEVERE, "Causa: " + che.getMessage());
			return null;
		}
	}


	public void logout(String tokenId){
		Client client = Client.create();
		client.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);
		try
		{
			WebResource resource = client.resource(ConstantsSSO.PROTOCOLO + "://" + servidorSSO + "/sso/identity/logout?subjectid="
					+ tokenId);
			resource.get(String.class);
		}
		catch (UniformInterfaceException uie)
		{
		//	Logger.getLogger(SSOUtil.class.getName()).log(Level.SEVERE, "Causa: " + uie.getMessage());
		}
		catch (ClientHandlerException che)
		{
		//	Logger.getLogger(SSOUtil.class.getName()).log(Level.SEVERE, "Causa: " + che.getMessage());
		}
	}


	public boolean authorization(String uri, String tokenId){
		Client client = Client.create();
		client.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);
		String response = "";
		boolean autorizado = false;
		try
		{
			WebResource resource = client.resource(ConstantsSSO.PROTOCOLO + "://" + servidorSSO + "/sso/identity/authorize?uri=" + uri
					+ "subjectid=" + tokenId);
			response = resource.get(String.class);
			autorizado = Boolean.parseBoolean(response.substring(8, response.length()).trim());
			return autorizado;
		}
		catch (UniformInterfaceException uie)
		{
			Logger.getLogger(SSOUtil.class.getName()).log(Level.SEVERE, "Causa: " + uie.getMessage());
			return false;
		}
		catch (ClientHandlerException che)
		{
			Logger.getLogger(SSOUtil.class.getName()).log(Level.SEVERE, "Causa: " + che.getMessage());
			return false;
		}
	}


	public void refresh(String tokenId){
		Client client = Client.create();
		client.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);
		
		@SuppressWarnings("unused")
		String response = "";
		try{
			WebResource resource = client.resource(ConstantsSSO.PROTOCOLO + "://" + servidorSSO + "/sso/identity/isTokenValid?tokenId="
					+ tokenId + "&refresh=true");
			response = resource.get(String.class);
		}
		catch (UniformInterfaceException uie){
			Logger.getLogger(SSOUtil.class.getName()).log(Level.SEVERE, "Causa: " + uie.getMessage());
		}
		catch (ClientHandlerException che){
			Logger.getLogger(SSOUtil.class.getName()).log(Level.SEVERE, "Causa: " + che.getMessage());
		}
	}


	public static String getURL_LOGIN(HttpServletRequest httpServletRequest){
		return ConstantsSSO.URL_LOGIN + httpServletRequest.getRequestURL();
	}


	public void setServidorSSO(String servidorSSO){
		this.servidorSSO = servidorSSO;
	}


	public String getServidorSSO(){
		return servidorSSO;
	}
		
	private String getAttribute(String nomeAtributo, String response) {
		
		String valorAtributo = ""; 
		
		StringTokenizer st = new StringTokenizer(response, "\n");
		
		while (st.hasMoreElements()) {
			String key = ((String) st.nextElement()).toLowerCase();
			if (key.indexOf("name="+nomeAtributo.toLowerCase()) >= 0) {
				String value =  (String) st.nextElement();
				valorAtributo = value.substring(value.indexOf("="), value.length()).replace("=","").trim();
				break;
			}
		}

		return valorAtributo; 
	}
        
        TrustManager[] trustAllCerts = new TrustManager[]{
        new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
            }
        }
    };
    
}
