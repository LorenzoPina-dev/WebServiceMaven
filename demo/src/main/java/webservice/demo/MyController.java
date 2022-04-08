package webservice.demo;

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

    //@GetMapping(value="/register.php", produces = MediaType.TEXT_PLAIN_VALUE)
    @RequestMapping(value="/register.php", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String Register(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
         System.out.println(username+" "+password);
        return "{'success':true,'result':'ciao'}";
    }
    @RequestMapping(value="/getToken.php", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String GetToken(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
         System.out.println(username+" "+password);
        return "{'success':true,'result':'ciao'}";
    }
    @RequestMapping(value="/register.php", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String sayHello(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
         System.out.println(username+" "+password);
        return "{'success':true,'result':'ciao'}";
    }
    @RequestMapping(value="/setString.php", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String SetString(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
         System.out.println(username+" "+password);
        return "{'success':true,'result':'ciao'}";
    }
    @RequestMapping(value="/getString.php", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String GetString(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
         System.out.println(username+" "+password);
        return "{'success':true,'result':'ciao'}";
    }

}