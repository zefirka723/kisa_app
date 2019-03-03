package mosecom.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// Это для баловства

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity config) throws Exception {
        config
                .authorizeRequests()
                .antMatchers("/reccards").permitAll()
                .antMatchers("/reccards/img").permitAll()
                .antMatchers("/reccards/edit").hasRole("EDITOR")
                .antMatchers("/reccards/create").hasRole("EDITOR")
                .antMatchers("/reccards/edit/delete").hasRole("EDITOR")
                .and()
                .formLogin().loginPage("/reccards/login").defaultSuccessUrl("/reccards/edit").permitAll()
                .and()
                .logout().logoutUrl("/reccards/logout").permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication()
                .withUser("user").password("password").roles("EDITOR");
    }
}
