import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import org.json.simple.JSONObject;

public class loginProfServlet extends HttpServlet {

    private static final String PROF_CREDENTIAL = ".\\webapps\\votiServlet\\src\\json\\prof_credential.json";
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public loginProfServlet() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {

        /**
         * TODO:
         * 1. Rifare la servlet di login
         */
    }
}