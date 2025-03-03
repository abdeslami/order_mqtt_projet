package ma.fso.dmn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class DmnApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DmnApplication.class, args);
		Environment env = context.getEnvironment();
		String port = env.getProperty("server.port");
		String ordersApi = env.getProperty("app.api.orders");
		System.out.println("App running on port " + port + " : http://localhost:" + port + ordersApi);
	}
}
