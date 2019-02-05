package optidb.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class OptiDbServerApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(OptiDbServerApplication.class, args);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home()
	{
		System.out.println("La version est : " + SpringVersion.getVersion());
		return "La version est : " + SpringVersion.getVersion();
	}

}

