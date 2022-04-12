package webservice.demo;

import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                return "{'status':'error','message':'riprova'}";
        } catch (SQLException e) {
            return "{'status':'error','message':'"+e.getMessage()+"'}";
        }
    }
    @GetMapping(value="/getToken.php", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String GetToken(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
         try {
             int id=Db.Instance().GetToken(username, password);
            if(id>=0)
            {
                SecureRandom random = new SecureRandom();
                byte bytes[] = new byte[20];
                random.nextBytes(bytes);
                String token = bytes.toString();
                if(Db.Instance().SetToken(id, token))
                return "{'status':'ok','result':{'token':'"+token+"'}}";
            }
            else
                return "{'status':'error','message':'riprova'}";
        } catch (SQLException e) {
            return "{'status':'error','message':'"+e.getMessage()+"'}";
        }
        return "";
    }
    @GetMapping(value="/setString.php", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String SetString(@RequestParam(name = "token") String token , @RequestParam(name = "key") String key, @RequestParam(name = "string") String string) {
         try {
            if(Db.Instance().SetString(Db.Instance().GetUtente(token).getInt("Id"),key,string))
            {
                return "{'status':'ok','result':{}}";
            }
            else
                return "{'status':'error','message':'riprova'}";
        } catch (SQLException e) {
            return "{'status':'error','message':'"+e.getMessage()+"'}";
        }
    }
    @GetMapping(value="/getString.php", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String GetString(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
         System.out.println(username+" "+password);
        return "{'success':true,'result':'ciao'}";
    }

}