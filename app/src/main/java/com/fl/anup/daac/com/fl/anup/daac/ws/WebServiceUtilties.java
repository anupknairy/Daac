package com.fl.anup.daac.com.fl.anup.daac.ws;


import android.util.Log;

import com.fl.anup.daac.com.fl.anup.daac.models.User;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static java.net.Proxy.Type.HTTP;

/**
 * Created by Anup on 3/12/2017.
 */

public class WebServiceUtilties {

    private static final String LOGIN_END_POINT = "/doLoginMob";

    private static final String CREATE_USER_END_POINT = "/createUserMob";

    private static final String RECOVER_PWD_END_POINT = "/retrievePassword";

    private static final String UPDATE_PWD_END_POINT = "/updatePasswordMob";

    private static final String UPLOAD_FILE_END_POINT = "/upload.kd";

    public static Message doLogin(String userId, String pwd) {

        try {


           /* MultiValueMap<String, Object> formdata = new LinkedMultiValueMap<String, Object>();
            formdata.add("userId",userId);
            formdata.add("userPass",pwd);
            Message msg = sendRestCall(formdata,LOGIN_END_POINT);
            return msg;*/

            return returnDummyMessageResponse();

        } catch (Exception e) {
            Log.e("WS", e.getMessage(), e);
        }

        return null;

    }

    public static Message createUser(User user) {

        try {


            /*MultiValueMap<String, Object> formdata = new LinkedMultiValueMap<String, Object>();
            formdata.add("userName",user.getUserName());
            formdata.add("userEmail",user.geteMail());
            formdata.add("userPhone",user.getPhoneNum());
            formdata.add("userType",user.getUserType());
            Message msg = sendRestCall(formdata,CREATE_USER_END_POINT);
            return msg;*/

            return returnDummyCreateUSerMessageResponse();

        } catch (Exception e) {
            Log.e("WS", e.getMessage(), e);
        }

        return null;

    }

    public static Message recoveryPassword(String userId) {

        try {

            /*MultiValueMap<String, Object> formdata = new LinkedMultiValueMap<String, Object>();
            formdata.add("userId",userId);
            Message msg = sendRestCall(formdata,RECOVER_PWD_END_POINT);
            return msg;*/

            return returnDummyCreateUSerMessageResponse();

        } catch (Exception e) {
            Log.e("WS", e.getMessage(), e);
        }

        return null;

    }

    public static Message updatePassword(String userId,String pwd) {

        try {

           /* MultiValueMap<String, Object> formdata = new LinkedMultiValueMap<String, Object>();
            formdata.add("userId",userId);
            formdata.add("updatedPassword",pwd);
            Message msg = sendRestCall(formdata,RECOVER_PWD_END_POINT);
            return msg;*/

            return returnDummyCreateUSerMessageResponse();

        } catch (Exception e) {
            Log.e("WS", e.getMessage(), e);
        }

        return null;

    }

    private static Message sendRestCall(MultiValueMap<String, Object> formdata,String endPoint) {

        String uri = ""+endPoint;
        HttpHeaders reqHeader = new HttpHeaders();
        reqHeader.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity requestEntity = new HttpEntity(formdata,reqHeader);

        RestTemplate template = new RestTemplate(true);

        ResponseEntity<Message> responseEntity =
                template.exchange(uri, HttpMethod.POST,requestEntity,Message.class);
        return responseEntity.getBody();
    }

    private static Message returnDummyMessageResponse() {

       Message msg =new Message();
        msg.setUserId("anup");
        msg.setUserName("Anup Kumar");
        msg.setStatus("UPDATEPASSWORD");
        msg.setUserType("doctor");

        return msg;
    }

    private static Message returnDummyCreateUSerMessageResponse() {
        Message msg =new Message();
        msg.setStatus("SUCCESS");
        return msg;
    }
}
