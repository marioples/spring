package hr.vsite.dipl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import com.github.javaplugs.jsf.ViewScope;

@Configuration
@ImportResource("classpath:/com/github/javaplugs/jsf/jsfSpringScope.xml")
public class ViewConfiguration extends CustomScopeConfigurer {

	public void InitViewScope() {
        Map<String, Object> map = new HashMap<>();
        map.put("view", new ViewScope());
        super.setScopes(map);
    }
}
