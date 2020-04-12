import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.*;
import javax.servlet.http.*;

import org.json.simple.JSONObject;

public class studMainPageServlet extends HttpServlet {

    // Questo field rappresenta solo l'inizio del path del json che contiene i voti 
    // dello studente in questione. Sarà compito della servlet quello di completarlo inserendo
    // alla fine il file <matricola>.json creato al momento della registrazione.
    private static final String STUDENT_VOTE = ".\\webapps\\votiServlet\\src\\json\\vote\\student\\";

    private static final String STUDENT_CREDENTIAL = ".\\webapps\\votiServlet\\src\\json\\student.json";

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public studMainPageServlet(){}
    
    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    throws IOException, ServletException {

        Cookie matricola = request.getCookies()[0];

        HashMap<String, String> informations = (HashMap<String, String>) (new JSONReader(
            studMainPageServlet.STUDENT_CREDENTIAL
        )).read().get(matricola.getValue());

        String nome = informations.get("Nome");
        String cognome = informations.get("Cognome");
        String email = informations.get("Email");

        String voteFile = String.format(
            "%s%s.json",
            studMainPageServlet.STUDENT_VOTE,
            matricola.getValue()
        );

        JSONObject votations = (new JSONReader(voteFile)).read();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println(
            "<html>\n" +
            "<head>\n" +
            "<title>Home page</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<h1>Dear " + nome + ", welcome to your home page</h1>\n" +
            "<>"  
        );

    }

}