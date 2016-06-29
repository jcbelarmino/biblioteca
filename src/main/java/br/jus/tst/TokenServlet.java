package br.jus.tst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

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
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.sendRedirect("http://revistadostribunais.com.br/maf/app/authentication/signon?sso-token="
				+ getToken());
	}

	public static String getToken() {
		String tokenResultado = "";
		// Definindo Usu�rio e Senha

		try {

			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(
					"http://revistadostribunais.com.br/maf/api/v1/authenticate.json?sp=[USUARIO]");
			HttpHost proxy = new HttpHost("localhost", 3128);
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			HttpResponse response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String resultado = "";
			String linha = "";
			while ((linha = rd.readLine()) != null) {
				resultado+=linha;
			}
			System.out.println("resultado:"+resultado+"|");

			Gson gson = new Gson();
			Token t = gson.fromJson(resultado, Token.class);
			System.out.println(t.getToken());
			tokenResultado = t.getToken();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tokenResultado;

	}

	public class Token {
		private String token;

		public Token() {
			// TODO Auto-generated constructor stub
		}
		public String getToken() {
			return this.token;
		}

		public void setToken(String token) {
			this.token = token;
		}

	}

}
