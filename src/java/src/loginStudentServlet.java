import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.json.simple.JSONObject;

/**
 * TODO:
 * 1. Introdurre il concetto di sessione
 */

public class loginStudentServlet extends HttpServlet {

    private static final String STUDENT_CREDENTIALS = ".\\webapps\\votiServlet\\src\\json\\student.json";
    private static final String LAST_ACCESS_DATE = ".\\webapps\\votiServlet\\src\\json\\student_last_access.json";


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public loginStudentServlet() {

    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    throws IOException, ServletException {
        /**
         * TODO: 
         * 1. Aggiungere il corpo del metodo
         */

        String matricola = request.getParameter("matricola");
        String passwd = request.getParameter("passwd");

        response.setContentType("text/html");

        JSONObject credentials = (new JSONReader(loginStudentServlet.STUDENT_CREDENTIALS)).read();

        if (credentials.containsKey(matricola)){
            HashMap<String, String> innerJSON = (HashMap<String, String>) credentials.get(matricola);

            if (innerJSON.get("Password").equals(passwd)){
                Date now = new Date();

                ArrayList<String> values = new ArrayList<>();
                values.add(now.toString());

                (new JSONWriter(loginStudentServlet.LAST_ACCESS_DATE)).write(
                    Type.STUDENT_LAST_ACCESS, 
                    matricola, 
                    values
                );

                response.addCookie(new Cookie("matricola", matricola));

                

            } else {
                response.sendRedirect("/votiServlet/StudentLoginPage?info=password");
            }
        } else {
            response.sendRedirect("/votiServlet/StudentLoginPage?info=matricola");
        }
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {

        Cookie[] cookies = request.getCookies();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = cookies == null ? "" : cookies[cookies.length - 1].getValue();
        String info = request.getParameter("info");

        String htmlString = new String();

        if (info != null){
            if (info.equals("matricola")){
                htmlString = "<h3 style=\"color:red;\">La matricola non esiste, si prega di registrarsi</h3>\n";
            } else if (info.equals("password")) {
                htmlString = "<h3 style=\"color:red;\">La password inserita non è corretta</h3>\n";
            }
        }

        out.println(
            "<html>\n" + 
            "<head>\n" +
            "<meta charset=\"UTF-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "<title>Student Login Page</title>\n" + 
            "</head>\n" + 
            "<body>\n" +
            "<script src=\"/votiServlet/src/js/login_html/student_login.js\"></script>\n" +
            "<h1>Accedi come studente</h1>\n" +
            htmlString +
            "<fieldset>\n" +
            "<form action=\"/votiServlet/StudentLoginPage\" method=\"POST\">\n" +
            "<p><label>Matricola: </label><input type=\"text\" name=\"matricola\" required=\"true\" value=" + username + "></p>\n" +
            "<p><label>Password: </label>" +
            "<input type=\"password\" name=\"passwd\" id=\"input\" required=\"true\">" +
            "<input type=\"checkbox\" onclick=\"visible()\">Show Password" +
            "</p>\n" +
            "<p><a href=\"./src/html/register/student_register.html\">Se non sei registrato, fallo adesso!!</a></p>\n" +
            "<p><a href=\"/votiServlet/index.html\">Torna alla home page</a></p>\n" +
            "<p><input type=\"submit\" value=\"Login\"></p>\n" +
            "</form>\n" +
            "   </fieldset>\n" +
            "</body>\n" +
            "</html>\n"
        );

        out.close();

    }
}