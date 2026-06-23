/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.jdbc_base;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class ConsultaDB2 {

    public String getAPIDB2MySQLConnection(String BBSSOToken, String query) {
        HttpsURLConnection conn = null;
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            
            query= query.replaceAll("\n" , " ").replaceAll("\t", " ").replaceAll("''","\'\'");

            System.setProperty("https.protocols", "TLSv1.2");

            conn = (HttpsURLConnection) new URL("https://redediope.jokerapi.servicos.bb.com.br/query").openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(1600);
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			
            conn.setRequestProperty("Authorization", "Basic " + "<xX_SUPRIMIDO_Xx>="); 
			
            conn.setRequestProperty("Cookie", "BBSSOToken="+BBSSOToken+";ssoacr=sso.intranet.bb.com.br");
            conn.setDoOutput(true);
            PrintStream printStream = new PrintStream(conn.getOutputStream());
            String input = "{\"query\":\" "+query+" \"}";
            printStream.println(input);
            conn.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            String result= reader.readLine();  
            String formatString= result;
            
            //System.out.println("print conn:"+conn);
            
            //System.out.println(" retorno consulta db2 pre filtro"+result);
            
            try{
                // replace divido a bases externas do banco com diferentes charsets
                formatString =  formatString.replaceAll("Ã‡", "Ã§")
                                            .replaceAll("Ãƒ", "Ã£")
                                            .replaceAll("Ã‚", "Ã¢")
                                            .replaceAll("Ãˆ", "Ã¨")
                                            .replaceAll("Ã‰","Ã©")
                                            .replaceAll("ÃŠ", "Ãª")
                                            .replaceAll("ÃŒ", "Ã¬")
                                            .replaceAll("Ã‘", "Ã±")
                                            .replaceAll("Ã“", "Ã³")
                                            .replaceAll("Ã•", "Ãµ")
                                            .replaceAll("Ãš", "Ãº")
                                            .replaceAll("Ð", "Ãµ")
                                            .replaceAll("   ", "");

                    result = new String(formatString.getBytes("ISO-8859-1"), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                result = formatString;
            };     
            
            //System.out.println(" retorno consulta db2 pos filtro"+result);
            
            return result;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        } finally {
            desconectar(conn);
        }
    }  

   
    private void desconectar(HttpsURLConnection conn) {
        try {
            conn.disconnect();
        } catch (Exception e) {
        }
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
