package ncu.im3069.group14.tools;

import javax.servlet.http.*; 
import java.io.*; 
import org.json.*;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class JsonReader<br>
 * JsonReader類別（class）主要用?��??��?�Http Rrequest??�Http Rresponse之方法�?�method�?
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class JsonReader {
    /** request，用?��?��存�?��?�Http Request */
    private HttpServletRequest request;
    
    /** string，用?��?��存�?��???��?��?�Request字串 */
    private String request_string;

    /**
     * 實�?��?��?�Instantiates）�??�新??��?�new）JSONReader?���?
     *
     * @param request Servlet Http Request?��件�?��???�request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public JsonReader(HttpServletRequest request) throws IOException, UnsupportedEncodingException {
        /** 設�?? Request 之�?�串編碼?��UTF-8，避??�中??��?�出?��?���? */
        request.setCharacterEncoding("UTF-8");
        /** ?��存�?��?�Request之物�? */
        this.request = request;
        
        /** 建�?��??�StringBuilder組出字串 */
        StringBuilder sb = new StringBuilder();
        String s;
        
        /** 
         * ?��?? while 迴�?��?��?��???��?��??
         * 將其�??��後�?�入字串變數 s
         * ??終append?��StringBuilder?���?
         */
        while((s = request.getReader().readLine()) != null) sb.append(s);
        
        /** 將StringBuilder轉�?��?�String，並?��?��該�?�串 */
        this.request_string = sb.toString();
        System.out.println("[@JsonReader]" + this.request_string);
    }

    /**
     * ??��?�GET Request?��之�?�數（�?��?�網???��?��??
     *
     * @param key ?��?��要�?��?��?�key??
     * @return the parameter ??�傳該key?��?�value
     */
    public String getParameter(String key) {
        /** ?��?���? key ?��?��存在?��Request?��中�?�若??��?��?�傳�? key ???��存�?�value */
        if (this.request.getParameter(key) != null) return this.request.getParameter(key);
        /** ?��?��??��?�傳空�?�串 */
        else return "";
    }

    /**
     * �? Http Request 轉�?��?? JSONArray 之物件�?�Request之�?�串??外層必�?�為 [] Array
     *
     * @return the array ??�傳轉�?��?��?�JSONArray
     */
    public JSONArray getArray() {
        /** 
         * 建�?��??�JSONArray?��件�?�用以解??�Request之�?�串
         * ??Request之�?�串??外層必�?�為 [] Array
         */
        JSONArray request_array = new JSONArray(this.request_string);
        
        /** ??�傳Request之Array?���? */
        return request_array;
    }

    /**
     * �? Http Request 轉�?��?? JSONObject 之物件�?�Request之�?�串??外層必�?�為 {} Object
     *
     * @return the object ??�傳轉�?��?��?�JSONObject
     */
    public JSONObject getObject() {
        /** 
         * 建�?��??�JSONObject?��件�?�用以解??�Request之�?�串
         * ??Request之�?�串??外層必�?�為 {} Object
         */
        JSONObject request_object = new JSONObject(this.request_string);
        
        /** ??�傳Request之Object?���? */
        return request_object;
    }

    /**
     * �? JSON ?��式�?��?�串?��?��，�?��?��?��?��?��?��?��?��?��?��?�Response??��?�端，�?�串??外�?��?��?�為 {} Object
     * ?��?��多�?��?�overload）方法�?��??
     *
     * @param resp_string ??�Response之JSON?��式�?�串
     * @param response Servlet??�傳之HttpServletResponse之Response?���?
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public void response(String resp_string, HttpServletResponse response) throws IOException, UnsupportedEncodingException {
        /** 設�?�Response之�?��?�編�? */
        response.setCharacterEncoding("UTF-8");
        /** 設�?�Response之�?�件類�?? */
        response.setContentType("text/html; charset=UTF-8");
        /** 將JSON?��式�?��?�串轉�?��?? */
        JSONObject responseObj = new JSONObject(resp_string);
        /** ??��?�PrintWriter準Response??��?�端 */
        PrintWriter out = response.getWriter();
        /** 將Response Object?��?��，�?�傳??�端 */
        out.println(responseObj);
    }
    
    /**
     * �? JSON ?��式�?�JSONObject?��?��，�?��?��?��?��?��?��?��?��?��?��?�Response??��?�端
     * ?��?��多�?��?�overload）方法�?��??
     *
     * @param resp ??�Response之JSONObject Response之物�?
     * @param response Servlet??�傳之HttpServletResponse之Response?���?
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public void response(JSONObject resp, HttpServletResponse response) throws IOException, UnsupportedEncodingException {
        /** 設�?�Response之�?��?�編�? */
        response.setCharacterEncoding("UTF-8");
        /** 設�?�Response之�?�件類�?? */
        response.setContentType("text/html; charset=UTF-8");
        /** ??��?�PrintWriter準Response??��?�端 */
        PrintWriter out = response.getWriter();
        /** 將Response Object?��?��，�?�傳??�端 */
        out.println(resp);
    }
    
    /**
     * �? JSON ?��式�?�JSONObject?��?��，�?��?��?��?��?��?��?��?��?��?��?�Response??��?�端
     * ?��?��多�?��?�overload）方法�?��??
     *
     * @param status_code ??��?�Http之�???�碼
     * @param resp ??�Response之JSONObject Response之物�?
     * @param response Servlet??�傳之HttpServletResponse之Response?���?
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public void response(int status_code, JSONObject resp, HttpServletResponse response) throws IOException, UnsupportedEncodingException {
        /** 設�?�Response之�?��?�編�? */
        response.setCharacterEncoding("UTF-8");
        /** 設�?�Response之�?�件類�?? */
        response.setContentType("text/html; charset=UTF-8");
        /** 設�?��???�碼 */
        response.setStatus(status_code);
        /** ??��?�PrintWriter準Response??��?�端 */
        PrintWriter out = response.getWriter();
        /** 將Response Object?��?��，�?�傳??�端 */
        out.println(resp);
    }
}