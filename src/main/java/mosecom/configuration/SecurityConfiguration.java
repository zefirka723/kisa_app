package mosecom.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author <a href="mailto:izebit@gmail.com">Artem Konovalov</a> <br/>
 *         Creation date: 6/25/17.
 * @since 1.0
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity config) throws Exception {
        config
                .authorizeRequests()
//                .antMatchers("/reccards/").permitAll()
//                .antMatchers("/reccards/img").permitAll()
                .antMatchers("/test/reccards").permitAll()
                //hasRole("EDITOR")
                .antMatchers("/test/reccards/**").permitAll()
                //hasRole("EDITOR")

                .and()
                .formLogin().loginPage("/test/login").defaultSuccessUrl("/").permitAll()
                .and()
                .logout().logoutUrl("/test/logout").permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication()
                .withUser("user").password("password").roles("EDITOR");
    }
}
