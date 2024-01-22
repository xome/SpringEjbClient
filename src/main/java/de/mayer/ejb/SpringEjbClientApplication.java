package de.mayer.ejb;

import de.mayer.ejbServer.Hello;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;


@SpringBootApplication
public class SpringEjbClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringEjbClientApplication.class, args);
    }

    @Bean
    public Context context() throws NamingException {
        Properties jndiProps = new Properties();
        jndiProps.put("java.naming.factory.initial",
                "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProps.put("java.naming.provider.url",
                "http-remoting://localhost:8080");
        return new InitialContext(jndiProps);
    }

    @Bean(name = "statelessHello")
    public Hello statelessHello(Context context)
            throws NamingException {

        Object obj = context.lookup("ejb:/ejb-server-1.0/StatelessHello!de.mayer.ejbServer.Hello");

        return (Hello) obj;
    }

    @Bean(name = "statefulHello")
    public Hello statefulHello(Context context)
            throws NamingException {

        return (Hello)
                context.lookup("ejb:/ejb-server-1.0/StatefulHello!de.mayer.ejbServer.Hello?stateful");
    }

}
