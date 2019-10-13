
package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class HandlebarsHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(HandlebarsHelper.class);

    public static byte[] apply(Map<String, ?> model) throws IOException {
        TemplateLoader templateLoader = new ClassPathTemplateLoader();
        templateLoader.setPrefix("/templates");
        templateLoader.setPrefix(".html");

        Handlebars handlebars = new Handlebars(templateLoader);

        Template template = handlebars.compile("user/list");

        return template.apply(Collections.singletonMap("users", model.values())).getBytes();
    }
}