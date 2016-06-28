package br.jus.tst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientRequestFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClientExecutor;

import com.google.gson.Gson;

/**
 * Servlet implementation class LerHTMLServlet
 */
public class TokenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TokenServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.sendRedirect("http://revistadostribunais.com.br/maf/app/authentication/signon?sso-token=" + getToken());
	}

	public static String getToken() {
		String tokenResultado = "";
		// Definindo Usu�rio e Senha
		
		try {
			HttpClient httpclient = new HttpClient();
			ClientExecutor clientExecutor = new ApacheHttpClientExecutor(httpclient);
			ClientRequestFactory factory = new ClientRequestFactory(clientExecutor, new URI(""));
			ClientRequest request = factory
					.createRequest("http://revistadostribunais.com.br/maf/api/v1/authenticate.json?sp=[USUARIO]");

			// obtendo o resultado do WebService via GET
			String resultado = request.getTarget(String.class);

			Gson gson = new Gson();
			Token t = gson.fromJson(resultado, Token.class);
			System.out.println(t.getToken());
			tokenResultado = t.getToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tokenResultado;

	}

	class Token {
		private String token;

		public String getToken() {
			return this.token;
		}

		public void setToken(String token) {
			this.token = token;
		}

	}

}
