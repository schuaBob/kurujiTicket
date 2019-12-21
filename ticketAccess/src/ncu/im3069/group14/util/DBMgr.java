package ncu.im3069.group14.util;

import java.sql.*;
import java.util.Properties;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class DBMgr<br>
 * DBMgr憿嚗lass嚗蜓閬恣�����澈撱箇������蝺�瘜�ethod嚗�蒂�摮���身摰���<br>
 * 瘥�����澈撱箇���蝺����府import�憿
 * </p>
 *
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class DBMgr {

    /** JDBC_DRIVER撣豢嚗身摰DBC撽�����迂 */
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    /** DB_URL撣豢嚗����澈���銋P��雯���ort��Ⅳ�����閬蝙�鞈�澈 */
    static final String DB_URL = "jdbc:mysql://localhost:3306/missa?allowPublicKeyRetrieval=true&useSSL=false";

    /** USER撣豢嚗�閬蝙�銋��澈雿輻�董��� */
    static final String USER = "root";

    /** PASS撣豢嚗���蝙�銋��澈雿輻��Ⅳ */
    static final String PASS = "123456";

    /** ������閬蝙�銋lass��迂 **/
    static {
        try {
            /** 頛JDBC撽��嚗蒂�銵���� */
            Class.forName(DBMgr.JDBC_DRIVER);
        } catch (Exception e) {
            /** ��隤文���隤方� */
            e.printStackTrace();
        }
    }

    /**
     * 撖虫���nstantiates嚗�����ew嚗BMgr�隞�
     */
    public DBMgr() {

    }

    /**
     * ��DBC撱箇���澈銋��蝺�
     *
     * @return the connection ����撱箇����澈��蝺�
     */
    public static Connection getConnection() {
        /** 鞈�澈��蝺��� */
        Properties props = new Properties();
        /** 閮剖���澈��蝺�閬蝙�SSL��蝺����身�雿輻SSL��蝺�迨�������False */
        props.setProperty("useSSL", "false");
        /** 閮剖���澈雿輻銋�� */
        props.setProperty("serverTimezone", "UTC");
        /** 閮剖��雿輻Unicode嚗迨����身摰True���葉��������� */
        props.setProperty("useUnicode", "true");
        /** 閮剖�蝙�銋��楊蝣潘��UTF-8 */
        props.setProperty("characterEncoding", "utf8");
        /** 閮剖���蝺�雿輻銋董����Ⅳ */
        props.setProperty("user", DBMgr.USER);
        props.setProperty("password", DBMgr.PASS);

        /** 摰���onnection霈嚗蒂����null */
        Connection conn = null;

        /** ��岫��riverManager��etConnection()撱箇�蒂�����澈��蝺� */
        try {
            conn = DriverManager.getConnection(DBMgr.DB_URL, props);
        } catch (Exception e) {
            /** ��隤文���隤方� */
            e.printStackTrace();
        }

        /** 撱箇������蝺�����閰脤��蝺�� */
        return conn;
    }

    /**
     * ��������澈��蝺��SQL鞈��
     *
     * @param stm SQL�閰Ｖ��誘
     * @param conn 鞈�澈銋��蝺�
     */
    public static void close(Statement stm, Connection conn) {
        try {
            /** 蝣箄�tatement���null嚗銝null���岫���tatement��鞈�� */
            if (stm != null) stm.close();
            /** 蝣箄�onnection���null嚗銝null���岫���onnection��鞈�� */
            if (conn != null) conn.close();
        } catch (Exception e) {
            /** ��隤文���隤方� */
            e.printStackTrace();
        }
    }

    /**
     * ��������澈��蝺��SQL鞈��
     *
     * @param rs 鞈�澈瑼Ｙ揣敺������
     * @param stm SQL�閰Ｖ��誘
     * @param conn 鞈�澈銋��蝺�
     */
    public static void close(ResultSet rs, Statement stm, Connection conn) {
        try {
            /** 蝣箄�esultSet���null嚗銝null���岫���esultSet��鞈�� */
            if (rs != null) rs.close();
            /** 蝣箄�tatement���null嚗銝null���岫���tatement��鞈�� */
            if (stm != null) stm.close();
            /** 蝣箄�onnection���null嚗銝null���岫���onnection��鞈�� */
            if (conn != null) conn.close();
        } catch (Exception e) {
            /** ��隤文���隤方� */
            e.printStackTrace();
        }
    }

    public static String[] stringToArray(String data, String delimiter) {
      String[] result;
      result = data.split(delimiter);
      return result;
    }
}
