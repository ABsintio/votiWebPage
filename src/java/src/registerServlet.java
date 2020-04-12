import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.ArrayList;



public class registerServlet extends HttpServlet {

    private static final String STUDENT_ = ".\\webapps\\votiServlet\\src\\json\\student.json";

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public registerServlet() {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        /**
         * TODO:
         * 1. Aggiungere il corpo della funzione
         */

        

        // Prendiamo i parametri della registrazione
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("surname");
        String matricola = request.getParameter("matricola");
        String email = request.getParameter("email");
        String passwd = request.getParameter("passwd");

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(nome);
        arrayList.add(cognome);
        arrayList.add(email);
        arrayList.add(passwd);

        Cookie cookie = new Cookie("matricola", matricola);
        response.addCookie(cookie);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (! (new JSONReader(registerServlet.STUDENT_)).read().containsKey(matricola)){
            (new JSONWriter(registerServlet.STUDENT_)).write(
                Type.STUDENT_CREDENTIAL_WRITE, 
                matricola, 
                arrayList
            );

            out.println(
                "<html>\n" +
                "<head>\n" +
                "<title>Successful registration</title>\n" +
                "</head>\n" + 
                "<body>\n" +
                "<h1>Le credenziali sono state salvate.</h1>\n" +
                "<h3>Ora puoi fare il login!!!!</h3>\n" +
                "<a href=\"/votiServlet/StudentLoginPage\">Torna alla pagina di login</a>\n" +
                "</body>\n" +
                "</html>\n"
            );

        } else {

            out.println(
                "<html>\n" +
                "<head>\n" +
                "<title>Registration Error</title>\n" +
                "</head>\n" + 
                "<body>\n" +
                "<h1>Le credenziali inserite sono state già salvate.</h1>\n" +
                "<h3>Devi fare il login.</h3>\n" +
                "<a href=\"/votiServlet/StudentLoginPage\">Torna alla pagina di login</a>\n" +
                "</body>\n" +
                "</html>\n"
            );
        }

        out.close();

    }

}