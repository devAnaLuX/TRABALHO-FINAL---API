package PF.SerratecFlix.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI(){
        Contact contato = new Contact();
        contato.setEmail("alcreis2303@gmail.com");
        contato.setName("Ana Luísa");

        License apacheLicense = new License()
                .name("Apache License")
                .url("https://www.apache.org/licenses/LICENSE-2.0");

        Info info = new Info()
                .title("API SerratecFlix")
                .version("1.0")
                .contact(contato)
                .description("API para cadastro de usuário, busca de séries e filmes, avaliações de series e filmes.")
                .license(apacheLicense);

        return  new OpenAPI().info(info);

    }
}
