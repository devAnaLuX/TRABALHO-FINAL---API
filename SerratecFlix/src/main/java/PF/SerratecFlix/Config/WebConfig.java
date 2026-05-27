package PF.SerratecFlix.Config;
 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 
@Configuration
public class WebConfig implements WebMvcConfigurer {
 
    @Value("${app.upload.dir}")
    private String uploadDir;
 
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mapeia GET /fotos-perfil/** para a pasta física no disco
        registry.addResourceHandler("/fotos-perfil/**")
                .addResourceLocations("file:" + uploadDir + "/");
    }
}
 