package ncu.im3069.group14.tools;

import javax.servlet.http.*; 
import java.io.*; 
import org.json.*;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class JsonReader<br>
 * JsonReader憿嚗lass嚗蜓閬����ttp Rrequest��ttp Rresponse銋瘜�ethod嚗�
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class JsonReader {
    /** request嚗��摮��ttp Request */
    private HttpServletRequest request;
    
    /** string嚗��摮������equest摮葡 */
    private String request_string;

    /**
     * 撖虫���nstantiates嚗�����ew嚗SONReader�隞�
     *
     * @param request Servlet Http Request�隞塚����equest
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public JsonReader(HttpServletRequest request) throws IOException, UnsupportedEncodingException {
        /** 閮剖�� Request 銋�葡蝺函Ⅳ�UTF-8嚗��葉�����隤� */
        request.setCharacterEncoding("UTF-8");
        /** �摮��equest銋隞� */
        this.request = request;
        
        /** 撱箇���tringBuilder蝯摮葡 */
        StringBuilder sb = new StringBuilder();
        String s;
        
        /** 
         * ��� while 餈游���������
         * 撠霈��敺�摮葡霈 s
         * ��蝯ppend�StringBuilder�銝�
         */
        while((s = request.getReader().readLine()) != null) sb.append(s);
        
        /** 撠tringBuilder頧��tring嚗蒂��閰脣�葡 */
        this.request_string = sb.toString();
        System.out.println("[@JsonReader]" + this.request_string);
    }

    /**
     * ���ET Request�銋�嚗��雯����潘��
     *
     * @param key ��閬���ey��
     * @return the parameter ��閰焊ey�潔�alue
     */
    public String getParameter(String key) {
        /** ��閰� key ��摮�Request�銝哨�����閰� key ���摮�alue */
        if (this.request.getParameter(key) != null) return this.request.getParameter(key);
        /** �����蝛箏�葡 */
        else return "";
    }

    /**
     * 撠� Http Request 頧��� JSONArray 銋隞塚�equest銋�葡��憭惜敹� [] Array
     *
     * @return the array ��頧���SONArray
     */
    public JSONArray getArray() {
        /** 
         * 撱箇���SONArray�隞塚�隞亥圾��equest銋�葡
         * ��Request銋�葡��憭惜敹� [] Array
         */
        JSONArray request_array = new JSONArray(this.request_string);
        
        /** ��Request銋rray�撘� */
        return request_array;
    }

    /**
     * 撠� Http Request 頧��� JSONObject 銋隞塚�equest銋�葡��憭惜敹� {} Object
     *
     * @return the object ��頧���SONObject
     */
    public JSONObject getObject() {
        /** 
         * 撱箇���SONObject�隞塚�隞亥圾��equest銋�葡
         * ��Request銋�葡��憭惜敹� {} Object
         */
        JSONObject request_object = new JSONObject(this.request_string);
        
        /** ��Request銋bject�撘� */
        return request_object;
    }

    /**
     * 撠� JSON �撘��葡��嚗����������脰�esponse���垢嚗�葡��憭��� {} Object
     * ��憭��verload嚗瘜�脰��
     *
     * @param resp_string ��esponse銋SON�撘�葡
     * @param response Servlet��銋ttpServletResponse銋esponse�隞�
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public void response(String resp_string, HttpServletResponse response) throws IOException, UnsupportedEncodingException {
        /** 閮剖�esponse銋��楊蝣� */
        response.setCharacterEncoding("UTF-8");
        /** 閮剖�esponse銋�辣憿�� */
        response.setContentType("text/html; charset=UTF-8");
        /** 撠SON�撘��葡頧��� */
        JSONObject responseObj = new JSONObject(resp_string);
        /** ���rintWriter皞esponse���垢 */
        PrintWriter out = response.getWriter();
        /** 撠esponse Object��嚗���垢 */
        out.println(responseObj);
    }
    
    /**
     * 撠� JSON �撘�SONObject��嚗����������脰�esponse���垢
     * ��憭��verload嚗瘜�脰��
     *
     * @param resp ��esponse銋SONObject Response銋隞�
     * @param response Servlet��銋ttpServletResponse銋esponse�隞�
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public void response(JSONObject resp, HttpServletResponse response) throws IOException, UnsupportedEncodingException {
        /** 閮剖�esponse銋��楊蝣� */
        response.setCharacterEncoding("UTF-8");
        /** 閮剖�esponse銋�辣憿�� */
        response.setContentType("text/html; charset=UTF-8");
        /** ���rintWriter皞esponse���垢 */
        PrintWriter out = response.getWriter();
        /** 撠esponse Object��嚗���垢 */
        out.println(resp);
    }
    
    /**
     * 撠� JSON �撘�SONObject��嚗����������脰�esponse���垢
     * ��憭��verload嚗瘜�脰��
     *
     * @param status_code ���ttp銋���Ⅳ
     * @param resp ��esponse銋SONObject Response銋隞�
     * @param response Servlet��銋ttpServletResponse銋esponse�隞�
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public void response(int status_code, JSONObject resp, HttpServletResponse response) throws IOException, UnsupportedEncodingException {
        /** 閮剖�esponse銋��楊蝣� */
        response.setCharacterEncoding("UTF-8");
        /** 閮剖�esponse銋�辣憿�� */
        response.setContentType("text/html; charset=UTF-8");
        /** 閮剖����Ⅳ */
        response.setStatus(status_code);
        /** ���rintWriter皞esponse���垢 */
        PrintWriter out = response.getWriter();
        /** 撠esponse Object��嚗���垢 */
        out.println(resp);
    }
}