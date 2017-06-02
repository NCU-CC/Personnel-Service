package tw.edu.ncu.cc.student.server.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import tw.edu.ncu.cc.oauth.resource.filter.AccessTokenDecisionFilter

@EnableWebSecurity
public class SecurityConfig {

    @Order( 1 )
    @Configuration
    public static class OauthGuard extends WebSecurityConfigurerAdapter {

        @Autowired
        def AccessTokenDecisionFilter accessTokenDecisionFilter

        @Override
        protected void configure( HttpSecurity http ) throws Exception {
            http.requestMatchers()
                    .antMatchers( HttpMethod.GET, "/v*/cards/**", "/v*/info/**" )
                    .antMatchers( HttpMethod.POST, "/v*/cards/**", "/v*/info/**" )
                    .antMatchers( HttpMethod.PUT, "/v*/cards/**", "/v*/info/**" )
                    .antMatchers( HttpMethod.DELETE, "/v*/cards/**", "/v*/info/**" )
                    .and()
                    .addFilterAfter( accessTokenDecisionFilter, UsernamePasswordAuthenticationFilter )
                    .csrf().disable()
        }
    }

    @Order( 2 )
    @Configuration
    public static class ManagementAPI extends WebSecurityConfigurerAdapter {

        @Value( '${custom.management.security.access}' )
        def String managementAccess

        @Override
        protected void configure( HttpSecurity http ) throws Exception {
            http.requestMatchers()
                    .antMatchers( HttpMethod.GET, "/management/**" )
                    .antMatchers( HttpMethod.POST, "/management/**" )
                    .antMatchers( HttpMethod.PUT, "/management/**" )
                    .antMatchers( HttpMethod.DELETE, "/management/**" )
                    .and()
                    .authorizeRequests()
                        .anyRequest().access( managementAccess )
        }

    }

}
