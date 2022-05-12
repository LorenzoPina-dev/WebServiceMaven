package webservice.demo;

import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import org.json.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "*")
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
            {
                JSONObject ris=new JSONObject();
                ris.put("result", "utente registrato");
                ris.put("status", "ok");
                return ris.toString();
            }
            else{
                JSONObject ris=new JSONObject();
                ris.put("message", "utente gia registrato");
                ris.put("status", "error");
                return ris.toString();
            }
        } catch (SQLException e) {
            JSONObject ris=new JSONObject();
            ris.put("message", e.getMessage());
            ris.put("status", "error");
            return ris.toString();
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
                {
                    JSONObject result=new JSONObject();
                    result.put("token", token);
                    JSONObject ris=new JSONObject();
                    ris.put("result", result);
                    ris.put("status", "ok");
                    return ris.toString();
                }
                else
                {
                    JSONObject ris=new JSONObject();
                    ris.put("message", "username o passowrd errati");
                    ris.put("status", "error");
                    return ris.toString();
                }
            }
            else
            {
                JSONObject ris=new JSONObject();
                ris.put("message", "username o passowrd errati");
                ris.put("status", "error");
                return ris.toString();
            }
        } catch (SQLException e) {
            JSONObject ris=new JSONObject();
            ris.put("message", e.getMessage());
            ris.put("status", "error");
            return ris.toString();
        }
    }
    @GetMapping(value="/setString.php", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String SetString(@RequestParam(name = "token") String token , @RequestParam(name = "key") String key, @RequestParam(name = "string") String string) {
         try {
             int id=Db.Instance().GetUtente(token).getInt("Id");
            if(Db.Instance().SetString(id,key,string))
            {
                JSONObject ris=new JSONObject();
                ris.put("result", "Stringa inserira");
                ris.put("status", "ok");
                return ris.toString();
            }
            else if (Db.Instance().UpdateString(Db.Instance().GetUtente(token).getInt("Id"), key, string))
            {
                JSONObject ris=new JSONObject();
                ris.put("result", "Stringa aggiornata");
                ris.put("status", "ok");
                return ris.toString();
            }
            else{
                JSONObject ris=new JSONObject();
                ris.put("message", "key non valido'");
                ris.put("status", "error");
                return ris.toString();
            }
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
                JSONObject result=new JSONObject();
                result.put("key", key);
                result.put("string", rs.getString("testo"));
                JSONObject ris=new JSONObject();
                ris.put("result", result);
                ris.put("status", "ok");
                return ris.toString();
            }
            else
            {
                JSONObject ris=new JSONObject();
                ris.put("message", "riprova");
                ris.put("status", "error");
                return ris.toString();
            }
        } catch (SQLException e) {
            JSONObject ris=new JSONObject();
            ris.put("message", e.getMessage());
            ris.put("status", "error");
            return ris.toString();
        }
    }
    @GetMapping(value="/deleteString.php", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String DeleteString(@RequestParam(name = "token") String token , @RequestParam(name = "key") String key) {
        try {
            if(Db.Instance().DeleteString(Db.Instance().GetUtente(token).getInt("Id"),key))
            {
                JSONObject ris=new JSONObject();
                ris.put("result", "stringa rimossa");
                ris.put("status", "ok");
                return ris.toString();
            }
            else
            {
                JSONObject ris=new JSONObject();
                ris.put("message", "key non trovata'");
                ris.put("status", "error");
                return ris.toString();
            }
        } catch (SQLException e) {
            JSONObject ris=new JSONObject();
            ris.put("message", "key non trovata'");
            ris.put("status", "error");
            return ris.toString();
        }
    }
    @GetMapping(value="/getKeys.php", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String GetKeys(@RequestParam(name = "token") String token) {
        try {
            int id=Db.Instance().GetUtente(token).getInt("Id");
            if(id>=0)
            {
                ResultSet rs=Db.Instance().GetKeys(id);
                JSONArray keys=new JSONArray();
                if(rs!=null)
                {
                    if(rs.getRow()>0)
                    {keys.put(rs.getString("chiave"));
                    while(rs.next())
                        keys.put(rs.getString("chiave"));
                }
                    JSONObject ris=new JSONObject();
                    ris.put("result", keys);
                    ris.put("status", "ok");

                    return ris.toString();
                }
                else
                {
                    JSONObject ris=new JSONObject();
                    ris.put("message", "key non trovata'");
                    ris.put("status", "error");
                    return ris.toString();
                }
            }
                else
                {
                    JSONObject ris=new JSONObject();
                    ris.put("message", "key non trovata'");
                    ris.put("status", "error");
                    return ris.toString();
                }
        } catch (SQLException e) {
            JSONObject ris=new JSONObject();
            ris.put("message", e.getMessage());
            ris.put("status", "error");
            return ris.toString();
        }
    }

}