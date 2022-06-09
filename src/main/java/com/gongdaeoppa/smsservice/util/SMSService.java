package com.gongdaeoppa.smsservice.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.logging.Logger;

public class SMSService {

    private final static String cafe24_hosting_id = "";
    private final static String cafe24_secure_key = "";
    private final static String sphone1 = "";
    private final static String sphone2 = "";
    private final static String sphone3 = "";
    private final static String apiUrl = "https://sslsms.cafe24.com/sms_sender.php";
    private final static String userAgent = "Mozilla/5.0";
    private final static String charset = "UTF-8";
    private final static boolean isTest = false;

    private static final Logger logger = Logger.getLogger(String.valueOf(SMSService.class));

    public void sendSMSAsync(String msg, String phone) {
        try {
            URL obj = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Accept-Charset", charset);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", userAgent);
            String postParams = "user_id=" + Base64Util.encode(cafe24_hosting_id)     // cafe24 호스팅 ID
                    + "&secure=" + Base64Util.encode(cafe24_secure_key)               // cafe24 에서 제공받은 보안키
                    + "&msg=" + Base64Util.encode(msg)                                // 메세지 내용
                    + "&rphone=" + Base64Util.encode(phone)                           // 수신 전화번호
                    + "&sphone1=" + Base64Util.encode(sphone1)
                    + "&sphone2=" + Base64Util.encode(sphone2)
                    + "&sphone3=" + Base64Util.encode(sphone3)
                    + "&mode=" + Base64Util.encode("1")
                    + "&smsType=S"; // SMS/LMS 여부
            if (isTest) {
                postParams += "&testflag=" + Base64Util.encode("Y");
            }
            //logger.info(postParams);
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(postParams.getBytes());
            os.flush();
            os.close();
            int responseCode = con.getResponseCode();
            logger.info("POST Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer buf = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    buf.append(inputLine);
                }
                in.close();
                logger.info("SMS Content : " + buf.toString());
            } else {
                logger.info("POST request not worked");
            }
        } catch (IOException ex) {
            logger.info("SMS IOException : " + ex.getMessage());
        }
    }
}
