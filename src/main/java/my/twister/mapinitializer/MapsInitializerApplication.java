package my.twister.mapinitializer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;
import java.net.URLClassLoader;

@SpringBootApplication
@ComponentScan(basePackages = "my.twister")
public class MapsInitializerApplication {


  @Bean
  public JettyEmbeddedServletContainerFactory jettyEmbeddedServletContainerFactory(@Value("${server.port:7070}") final String port,
                                                                                   @Value("${jetty.threadPool.maxThreads:8}") final String maxThreads,
                                                                                   @Value("${jetty.threadPool.minThreads:4}") final String minThreads,
                                                                                   @Value("${jetty.threadPool.idleTimeout:60000}") final String idleTimeout) {
    final JettyEmbeddedServletContainerFactory factory = new JettyEmbeddedServletContainerFactory(Integer.valueOf(port));
    factory.addServerCustomizers(new JettyServerCustomizer() {
      @Override
      public void customize(final Server server) {
        // Tweak the connection pool used by Jetty to handle incoming HTTP connections
        final QueuedThreadPool threadPool = server.getBean(QueuedThreadPool.class);
        threadPool.setMaxThreads(Integer.valueOf(maxThreads));
        threadPool.setMinThreads(Integer.valueOf(minThreads));
        threadPool.setIdleTimeout(Integer.valueOf(idleTimeout));
      }
    });
    return factory;
  }

  public static void main(String[] args) {
    SpringApplication.run(MapsInitializerApplication.class, args);
  }
}
