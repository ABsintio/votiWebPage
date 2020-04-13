import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.ArrayList;

public class studRegisterVoteServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final String STUD_VOTE = ".\\webapps\\votiServlet\\src\\json\\vote\\student\\";

    public studRegisterVoteServlet(){

    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {

        HttpSession session = request.getSession(true);
        String matricola = (String)session.getAttribute("matricola");

        String filename = String.format(
            "%s%s.json",
            studRegisterVoteServlet.STUD_VOTE,
            matricola
        );

        String materia = request.getParameter("subject");
        String docente = request.getParameter("docente");
        String date = request.getParameter("sostenuto");
        String voto = request.getParameter("vote");

        ArrayList<String> values = new ArrayList<>();
        values.add(docente);
        values.add(date);
        values.add(voto);

        (new JSONWriter(filename)).write(
            Type.STUDENT_VOTE_WRITE,
            materia,
            values
        );

        response.sendRedirect("/votiServlet/StudentHomePage");

    }

}