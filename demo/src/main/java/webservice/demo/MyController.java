package webservice.demo;

import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import org.json.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class MyController {
    @GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String index() {

        return "{'success':true,'result':'ciao'}";
    }

    @GetMapping(value="/register.php", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String Register(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        try {
            if(Db.Instance().Register(username, password))
                return "{'status':'ok','result':'ciao'}";
            else
            return "{'status':'error','message':'utente gia registrato'}";
        } catch (SQLException e) {
            return "{'status':'error','message':'utente gia registrato'}";
        }
    }
    @GetMapping(value="/getToken.php", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String GetToken(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
         try {
             int id=Db.Instance().GetToken(username, password);
            if(id>=0)
            {
                SecureRandom secureRandom = new SecureRandom(); // threadsafe
                Base64.Encoder base64Encoder = Base64.getUrlEncoder();
                byte[] randomBytes = new byte[24];
                secureRandom.nextBytes(randomBytes);
                String token= base64Encoder.encodeToString(randomBytes);
                if(Db.Instance().SetToken(id, token))
                    return "{'status':'ok','result':{'token':'"+token+"'}}";
                else
                    return "{'status':'error','message':'username o passowrd errati'}";
            }
            else
                return "{'status':'error','message':'username o passowrd errati'}";
        } catch (SQLException e) {
            return "{'status':'error','message':'"+e.getMessage()+"'}";
        }
    }
    @GetMapping(value="/setString.php", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String SetString(@RequestParam(name = "token") String token , @RequestParam(name = "key") String key, @RequestParam(name = "string") String string) {
         try {
             int id=Db.Instance().GetUtente(token).getInt("Id");
            if(Db.Instance().SetString(id,key,string))
            {
                return "{'status':'ok','result':{'stringa inserira'}}";
            }
            else if (Db.Instance().UpdateString(Db.Instance().GetUtente(token).getInt("Id"), key, string))
                return "{'status':'error','message':'Stringa aggiornata'}";
            else
                return "{'status':'error','message':'token non valido'}";
        } catch (SQLException e) {
            return "{'status':'error','message':'"+e.getMessage()+"'}";
        }
    }
    @GetMapping(value="/getString.php", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String GetString(@RequestParam(name = "token") String token , @RequestParam(name = "key") String key) {
        try {
            ResultSet rs=Db.Instance().GetString(Db.Instance().GetUtente(token).getInt("Id"),key);
            if(rs!=null)
            {
                return "{'status':'ok','result':{"+rs.getString("testo")+"}}";
            }
            else
                return "{'status':'error','message':'riprova'}";
        } catch (SQLException e) {
            return "{'status':'error','message':'"+e.getMessage()+"'}";
        }
    }
    @GetMapping(value="/deleteString.php", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String DeleteString(@RequestParam(name = "token") String token , @RequestParam(name = "key") String key) {
        try {
            if(Db.Instance().DeleteString(Db.Instance().GetUtente(token).getInt("Id"),key))
            {
                return "{'status':'ok','result':{'testo':'stringa rimossa'}}";
            }
            else
                return "{'status':'error','message':'key non trovata'}";
        } catch (SQLException e) {
            return "{'status':'error','message':'key non trovata'}";
        }
    }
    @GetMapping(value="/getKeys.php", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String GetKeys(@RequestParam(name = "token") String token) {
        try {
            ResultSet rs=Db.Instance().GetKeys(Db.Instance().GetUtente(token).getInt("Id"));
            if(rs!=null)
            {
                List<String> keys=new ArrayList<String>();
                keys.add(rs.getString("chiave"));
                while(rs.next())
                    keys.add(rs.getString("chiave"));
                JSONObject ris=new JSONObject();
                ris.put("result", keys);
                ris.put("status", "ok");

                return ris.toString();
            }
            else
                return "{'status':'error','message':'token non valido'}";
        } catch (SQLException e) {
            return "{'status':'error','message':'token non valido'}";
        }
    }

}