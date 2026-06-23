package br.com.bb.exxi.cartaauditoria_nova.jdbc_base;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.InputSource;


public class Uteis {     
       
	
    //retorna a SQL conforme parametros
    public static String getSqlResource(String arquivo, String queryGroup, String sqlName) throws XPathExpressionException {
       
        final InputSource is = new org.xml.sax.InputSource(Uteis.class
                .getResourceAsStream(arquivo));
      
        return XPathFactory.newInstance().newXPath().evaluate(
                "/queries/query[@group='" + queryGroup + "']/sql[@name='" + sqlName + "']/text()", is);
        
    }
    
    
    //fecha os recursos utilizados na conexão
    public static void fecharRecursos(ResultSet rs, PreparedStatement ps, Connection con) {
		
		if (rs != null) {
			try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
		}

		if (ps != null) { 
			try { ps.close(); } catch (Exception e) { e.printStackTrace();	}
		}
		
		if (con != null) {
			try { con.close(); } catch (Exception e) {	e.printStackTrace(); }
		}
		
	}
    
    
    //fecha os recursos utilizados na conexão
    public static void fecharRecursos(ResultSet rs, Statement st, Connection con) {
		
		if (rs != null) {
			try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
		}

		if (st != null) { 
			try { st.close(); } catch (Exception e) { e.printStackTrace();	}
		}
		
		if (con != null) {
			try { con.close(); } catch (Exception e) {	e.printStackTrace(); }
		}
		
	}
       
       
}
   
