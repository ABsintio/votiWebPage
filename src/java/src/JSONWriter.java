import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class JSONWriter {

    private String file;
    private JSONObject jsonObject;

    /**
     * TODO:
     *  - Add write method
     */

    public JSONWriter(String filename){
        this.file = filename;
        this.jsonObject = (new JSONReader(filename)).read();
    }

    public void write(Type operazione, String key, ArrayList<String> values) {

        switch (operazione) {
            case STUDENT_CREDENTIAL_WRITE:
                this.writeStudentCredential(key, values);
                break;

            case STUDENT_LAST_ACCESS:
                this.writeLastAccess(key, values.get(0));
                break;

            case PROFESSOR_LAST_ACCESS:
                this.writeLastAccess(key, values.get(0));
                break;

            case STUDENT_VOTE_WRITE:
                this.writeStudentVote(key, values);
                break;
        
            default:
                break;
        }

    }

    /**
     * 
     * @param key
     * @param values
     * 
     * Il json delle credenziali degli studenti avrà entry del tipo
     * {
     *      "matricola" : {
     *          "Nome" : ...,
     *          "Cognome": ...,
     *          "Email" : ...,
     *          "Password" : ...
     *      }
     * }
     */
    @SuppressWarnings("unchecked")
    private void writeStudentCredential(String key, ArrayList<String> values){
        
        // Devo creare il dizionario per le informazioni interne
        JSONObject innerJsonObject = new JSONObject();
        innerJsonObject.put("Nome", values.get(0));
        innerJsonObject.put("Cognome", values.get(1));
        innerJsonObject.put("Email", values.get(2));
        innerJsonObject.put("Password", values.get(3));

        // Inserisco nel json esistente
        this.jsonObject.put(key, innerJsonObject);

        // Write to json
        try (FileWriter file = new FileWriter(this.file)) {

            file.write(this.jsonObject.toJSONString());
            file.flush();

            File matricola = new File(String.format("./webapps/votiServlet/src/json/vote/student/%s.json", key));
            matricola.createNewFile();

            FileWriter stream = new FileWriter(matricola.getAbsolutePath());
            stream.write("{}");
            stream.flush();
            stream.close();

            file.close();

        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    /**
     * 
     * @param key
     * @param date
     * Il json degli ultimi accessi avrà entry del tipo
     * {
     *      "chiave" : "giorno ultimo accesso"
     * }
     */
    @SuppressWarnings("unchecked")
    private void writeLastAccess(String key, String date){
        this.jsonObject.put(key, date);

        try (FileWriter file = new FileWriter(this.file)) {

            file.write(this.jsonObject.toJSONString());
            file.flush();
            file.close();

        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    /**
     * 
     * @param key
     * @param values
     * Il json dei voti degli studenti avrà entry di questo tipo
     * {
     *      "materia" : {
     *          "Docente" : ...,
     *          "Data" : ...,
     *          "Voto" : ...
     *      }
     * }
     */
    @SuppressWarnings("unchecked")
    private void writeStudentVote(String key, ArrayList<String> values){

        // Creiamo il dizionario interno
        JSONObject innerJsonObject = new JSONObject();
        innerJsonObject.put("Docente", values.get(0));
        innerJsonObject.put("Data", values.get(1));
        innerJsonObject.put("Voto", values.get(2));

        this.jsonObject.put(key, innerJsonObject);

        try (FileWriter stream = new FileWriter(this.file)){
            
            stream.write(this.jsonObject.toJSONString());
            stream.flush();
            stream.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public JSONObject getJSON(){ return this.jsonObject; }

}