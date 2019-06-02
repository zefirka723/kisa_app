//package mosecom.configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private DataSource dataSource;
//
//    @Override
//    protected void configure(HttpSecurity config) throws Exception {
//        config
//                .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/fgi/*").permitAll()
//                .anyRequest().authenticated()
//                //hasRole("EDITOR")
//                .and()
//                .formLogin().loginPage("/fgi/login").permitAll()
//                .and()
//                .logout().logoutUrl("/logout").permitAll();
//    }
////
//
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.jdbcAuthentication()
////                .dataSource(dataSource)
////                .passwordEncoder(NoOpPasswordEncoder.getInstance())
////                .usersByUsernameQuery("select Login, Password from dictionary.Dictionary_Employees where Login=?")
////                .authoritiesByUsernameQuery("select e.\"Login\", er.\"Role_ID\"\n" +
////                        "     from dictionaries.\"Dictionary_Employees\" e\n" +
////                        "            inner join work.\"Employees_Roles\" er\n" +
////                        "                       on e.\"Emploe_ID\" = er.\"Emploe_ID\"\n" +
////                        "     where e.\"Login\" = ?");
////    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
//        builder.inMemoryAuthentication()
//                .withUser("user").password("password").roles("EDITOR");
//
//    }
//}
