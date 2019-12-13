package ncu.im3069.group14.tools;

import javax.servlet.http.*; 
import java.io.*; 
import org.json.*;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class JsonReader<br>
 * JsonReaderé¡åˆ¥ï¼ˆclassï¼‰ä¸»è¦ç”¨?–¼??•ç?†Http Rrequest??ŒHttp Rresponseä¹‹æ–¹æ³•ï?ˆmethodï¼?
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class JsonReader {
    /** requestï¼Œç”¨?–¼?„²å­˜å?Ÿå?‹Http Request */
    private HttpServletRequest request;
    
    /** stringï¼Œç”¨?–¼?„²å­˜ç?“è???–å?Œä?‹Requestå­—ä¸² */
    private String request_string;

    /**
     * å¯¦ä?‹å?–ï?ˆInstantiatesï¼‰ä??‹æ–°??„ï?ˆnewï¼‰JSONReader?‰©ä»?
     *
     * @param request Servlet Http Request?‰©ä»¶ï?Œç???„request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public JsonReader(HttpServletRequest request) throws IOException, UnsupportedEncodingException {
        /** è¨­å?? Request ä¹‹å?—ä¸²ç·¨ç¢¼?‚ºUTF-8ï¼Œé¿??ä¸­??‡å?—å‡º?¾?Œ¯èª? */
        request.setCharacterEncoding("UTF-8");
        /** ?„²å­˜å?Ÿå?‹Requestä¹‹ç‰©ä»? */
        this.request = request;
        
        /** å»ºç?‹ä??‹StringBuilderçµ„å‡ºå­—ä¸² */
        StringBuilder sb = new StringBuilder();
        String s;
        
        /** 
         * ?é?? while è¿´å?ˆé?è?Œè???–è?‡æ??
         * å°‡å…¶è®??‡ºå¾Œå?˜å…¥å­—ä¸²è®Šæ•¸ s
         * ??çµ‚append?ˆ°StringBuilder?•¶ä¸?
         */
        while((s = request.getReader().readLine()) != null) sb.append(s);
        
        /** å°‡StringBuilderè½‰æ?›æ?Stringï¼Œä¸¦?°?‡ºè©²å?—ä¸² */
        this.request_string = sb.toString();
        System.out.println("[@JsonReader]" + this.request_string);
    }

    /**
     * ??–å?—GET Request?…§ä¹‹å?ƒæ•¸ï¼ˆé?é?ç¶²???‚³?¼ï??
     *
     * @param key ?‚³?…¥è¦å?–å?—ä?‹key??
     * @return the parameter ??å‚³è©²key?¼ä?‹value
     */
    public String getParameter(String key) {
        /** ?ˆ¤?–·è©? key ?˜¯?¦å­˜åœ¨?–¼Request?•¶ä¸­ï?Œè‹¥??‰å?‡å?å‚³è©? key ???„²å­˜ä?‹value */
        if (this.request.getParameter(key) != null) return this.request.getParameter(key);
        /** ?‹¥?„¡??‡å?å‚³ç©ºå?—ä¸² */
        else return "";
    }

    /**
     * å°? Http Request è½‰æ?›æ?? JSONArray ä¹‹ç‰©ä»¶ï?ŒRequestä¹‹å?—ä¸²??å¤–å±¤å¿…é?ˆç‚º [] Array
     *
     * @return the array ??å‚³è½‰æ?›æ?ç?„JSONArray
     */
    public JSONArray getArray() {
        /** 
         * å»ºç?‹ä??‹JSONArray?‰©ä»¶ï?Œç”¨ä»¥è§£??Requestä¹‹å?—ä¸²
         * ??Requestä¹‹å?—ä¸²??å¤–å±¤å¿…é?ˆç‚º [] Array
         */
        JSONArray request_array = new JSONArray(this.request_string);
        
        /** ??å‚³Requestä¹‹Array? ¼å¼? */
        return request_array;
    }

    /**
     * å°? Http Request è½‰æ?›æ?? JSONObject ä¹‹ç‰©ä»¶ï?ŒRequestä¹‹å?—ä¸²??å¤–å±¤å¿…é?ˆç‚º {} Object
     *
     * @return the object ??å‚³è½‰æ?›æ?ç?„JSONObject
     */
    public JSONObject getObject() {
        /** 
         * å»ºç?‹ä??‹JSONObject?‰©ä»¶ï?Œç”¨ä»¥è§£??Requestä¹‹å?—ä¸²
         * ??Requestä¹‹å?—ä¸²??å¤–å±¤å¿…é?ˆç‚º {} Object
         */
        JSONObject request_object = new JSONObject(this.request_string);
        
        /** ??å‚³Requestä¹‹Object? ¼å¼? */
        return request_object;
    }

    /**
     * å°? JSON ? ¼å¼ä?‹å?—ä¸²?‚³?…¥ï¼Œå?‡è?•ç?†å?Œæ?å?Œç?„è?‡æ?™é?²è?ŒResponse??å?ç«¯ï¼Œå?—ä¸²??å¤–å?å?…é?ˆç‚º {} Object
     * ?¡?”¨å¤šè?‰ï?ˆoverloadï¼‰æ–¹æ³•é?²è??
     *
     * @param resp_string ??ˆResponseä¹‹JSON? ¼å¼å?—ä¸²
     * @param response Servlet??å‚³ä¹‹HttpServletResponseä¹‹Response?‰©ä»?
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public void response(String resp_string, HttpServletResponse response) throws IOException, UnsupportedEncodingException {
        /** è¨­å?šResponseä¹‹å?—å?ƒç·¨ç¢? */
        response.setCharacterEncoding("UTF-8");
        /** è¨­å?šResponseä¹‹æ?‡ä»¶é¡å?? */
        response.setContentType("text/html; charset=UTF-8");
        /** å°‡JSON? ¼å¼ä?‹å?—ä¸²è½‰æ?›æ?? */
        JSONObject responseObj = new JSONObject(resp_string);
        /** ??–å?—PrintWriteræº–Response??å?ç«¯ */
        PrintWriter out = response.getWriter();
        /** å°‡Response Object?”¾?…¥ï¼Œå?å‚³??ç«¯ */
        out.println(responseObj);
    }
    
    /**
     * å°? JSON ? ¼å¼ä?‹JSONObject?‚³?…¥ï¼Œå?‡è?•ç?†å?Œæ?å?Œç?„è?‡æ?™é?²è?ŒResponse??å?ç«¯
     * ?¡?”¨å¤šè?‰ï?ˆoverloadï¼‰æ–¹æ³•é?²è??
     *
     * @param resp ??ˆResponseä¹‹JSONObject Responseä¹‹ç‰©ä»?
     * @param response Servlet??å‚³ä¹‹HttpServletResponseä¹‹Response?‰©ä»?
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public void response(JSONObject resp, HttpServletResponse response) throws IOException, UnsupportedEncodingException {
        /** è¨­å?šResponseä¹‹å?—å?ƒç·¨ç¢? */
        response.setCharacterEncoding("UTF-8");
        /** è¨­å?šResponseä¹‹æ?‡ä»¶é¡å?? */
        response.setContentType("text/html; charset=UTF-8");
        /** ??–å?—PrintWriteræº–Response??å?ç«¯ */
        PrintWriter out = response.getWriter();
        /** å°‡Response Object?”¾?…¥ï¼Œå?å‚³??ç«¯ */
        out.println(resp);
    }
    
    /**
     * å°? JSON ? ¼å¼ä?‹JSONObject?‚³?…¥ï¼Œå?‡è?•ç?†å?Œæ?å?Œç?„è?‡æ?™é?²è?ŒResponse??å?ç«¯
     * ?¡?”¨å¤šè?‰ï?ˆoverloadï¼‰æ–¹æ³•é?²è??
     *
     * @param status_code ??‡å?šHttpä¹‹ç???‹ç¢¼
     * @param resp ??ˆResponseä¹‹JSONObject Responseä¹‹ç‰©ä»?
     * @param response Servlet??å‚³ä¹‹HttpServletResponseä¹‹Response?‰©ä»?
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public void response(int status_code, JSONObject resp, HttpServletResponse response) throws IOException, UnsupportedEncodingException {
        /** è¨­å?šResponseä¹‹å?—å?ƒç·¨ç¢? */
        response.setCharacterEncoding("UTF-8");
        /** è¨­å?šResponseä¹‹æ?‡ä»¶é¡å?? */
        response.setContentType("text/html; charset=UTF-8");
        /** è¨­å?šç???‹ç¢¼ */
        response.setStatus(status_code);
        /** ??–å?—PrintWriteræº–Response??å?ç«¯ */
        PrintWriter out = response.getWriter();
        /** å°‡Response Object?”¾?…¥ï¼Œå?å‚³??ç«¯ */
        out.println(resp);
    }
}