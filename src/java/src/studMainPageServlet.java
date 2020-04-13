import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws IOException, ServletException {

        HttpSession session = request.getSession();
        String matricola = (String) session.getAttribute("matricola");


        HashMap<String, String> informations = (HashMap<String, String>) (new JSONReader(
            studMainPageServlet.STUDENT_CREDENTIAL
        )).read().get(matricola);

        String nome = informations.get("Nome");
        String cognome = informations.get("Cognome");
        String email = informations.get("Email");

        String voteFile = String.format(
            "%s%s.json",
            studMainPageServlet.STUDENT_VOTE,
            matricola
        );

        HashMap<String, JSONObject> votations = (new JSONReader(voteFile)).read();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println(
            "<html>\n" +
            "<head>\n" +
            "<title>Home page</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<link rel=\"stylesheet\" href=\"/votiServlet/src/css/home/stud_home.css\" type=\"text/css\">" +
            "<h1>Dear " + nome + ", welcome to your home page</h1>\n" +
            "<h2>Informazioni personali</h2>\n" +
            "<p><label><strong>Nome: </strong></label>" + nome + "</p>\n" +
            "<p><label><strong>Cognome: </strong></label>" + cognome + "</p>\n" +
            "<p><label><strong>Email: </strong></label>" + email + "</p>\n" 
        );

        if (! votations.isEmpty()){
            out.println("<h4>I tuoi voti registrati sono i seguenti<h4/>\n");
            out.println(
                "<table border=\"1\">\n" +
                "<thead>\n" +
                "<tr>\n" +
                "<th>Esame</th>\n" +
                "<th>Professore</th>\n" +
                "<th>Giorno</th>\n" +
                "<th>Votazione</th>\n"+
                "</tr>\n" +
                "</thead>\n" +
                "<tbody>\n"
            );

            for (String key: votations.keySet()){
                HashMap<String, String> vote = votations.get(key);
                out.println(
                    "<tr>\n" +
                    "<td>" + key + "</td>\n" +
                    "<td>" + vote.get("Docente") + "</td>\n" +
                    "<td>" + vote.get("Data") + "</td>\n" +
                    "<td>" + vote.get("Voto") + "</td>\n" +
                    "</tr>\n"
                );
            } 
        
            out.println(
                "</tbody>\n" +
                "</table>\n"
            );
        } else {
            out.println("<h4 style=\"color: orange;\">Per adesso non ci sono votazioni inserite</h4>\n");
        }

        out.println(
            "<ul>\n" +
            "<li><a href=\"/votiServlet/src/html/vote/stud_register_vote.html\"><input type=\"button\" value=\"+ New Vote\"></a></li>\n" +
            "<li><a href=\"\"><input type=\"button\" value=\"Exit\"></a></li>" +
            "</ul>\n" + 
            "</body>\n" +
            "</html>"
        );

        out.close();

    }

}