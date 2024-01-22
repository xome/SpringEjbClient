# Setup

1. Deploy ejb-server-1.0.jar and ejb-server-1.0.pom to maven repository (de/mayer/ejb-server/1-0/)
2. Start Wildfly with ejb-server-1.0.jar deployed (see project EjbServer)
3. Start Spring Server:
```shell
mvn spring-boot:run
```

# Important dependencies

## Dependencies
- see pom.xml
- ejb-server: Needed to cast proxy
```maven
<dependency>
    <groupId>de.mayer</groupId>
    <artifactId>ejb-server</artifactId>
    <version>1.0</version>
    <type>ejb</type>
</dependency>
```
- EJB Stuff, needs to be for the same Wildfly Version as the EjbServer
```maven
<dependency>
    <groupId>org.wildfly</groupId>
    <artifactId>wildfly-ejb-client-bom</artifactId>
    <version>31.0.0.Beta1</version>
    <type>pom</type>
</dependency>
```

## Spring Configuration

- Context for WildflyNameService
```java
@Bean
public Context context() throws NamingException {
    Properties jndiProps = new Properties();
    jndiProps.put("java.naming.factory.initial",
            "org.wildfly.naming.client.WildFlyInitialContextFactory");
    jndiProps.put("java.naming.provider.url",
            "http-remoting://localhost:8080");
    return new InitialContext(jndiProps);
}
```

- JNDI names for the EJBs will be logged at wildfly like this:
```log
[INFO] 16:53:48,901 INFO  [org.jboss.as.ejb3.deployment] (MSC service thread 1-7) WFLYEJB0473: JNDI bindings for session bean named 'StatelessHello' in deployment unit 'deployment "ejb-server-1.0.jar"' are as follows:
[INFO] 
[INFO] 	java:global/ejb-server-1.0/StatelessHello!de.mayer.ejbServer.Hello
[INFO] 	java:app/ejb-server-1.0/StatelessHello!de.mayer.ejbServer.Hello
[INFO] 	java:module/StatelessHello!de.mayer.ejbServer.Hello
[INFO] 	java:jboss/exported/ejb-server-1.0/StatelessHello!de.mayer.ejbServer.Hello
[INFO] 	ejb:/ejb-server-1.0/StatelessHello!de.mayer.ejbServer.Hello
[INFO] 	java:global/ejb-server-1.0/StatelessHello
[INFO] 	java:app/ejb-server-1.0/StatelessHello
[INFO] 	java:module/StatelessHello
```

- for remote access you can only use this format: ejb:/ejb-server-1.0/StatelessHello!de.mayer.ejbServer.Hello
- stateful has to be put as a parameter
```text
ejb:/ejb-server-1.0/StatefulHello!de.mayer.ejbServer.Hello?stateful
```
