package webservice.demo;

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
            boolean ris=Db.Instance().Register(username, password);
            return "{'status':'ok','result':'ciao'}";
        } catch (SQLException e) {
            return "{'status':'error','message':'"+e.getMessage()+"'}";
        }
    }
    @GetMapping(value="/getToken.php", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String GetToken(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
         System.out.println(username+" "+password);
        return "{'success':true,'result':'ciao'}";
    }
    @GetMapping(value="/sayHello.php", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String SayHello(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
         System.out.println(username+" "+password);
        return "{'success':true,'result':'ciao'}";
    }
    @GetMapping(value="/setString.php", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String SetString(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
         System.out.println(username+" "+password);
        return "{'success':true,'result':'ciao'}";
    }
    @GetMapping(value="/getString.php", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String GetString(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
         System.out.println(username+" "+password);
        return "{'success':true,'result':'ciao'}";
    }

}