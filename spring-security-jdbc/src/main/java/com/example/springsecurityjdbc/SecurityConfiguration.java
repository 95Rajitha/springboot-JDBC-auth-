package com.example.springsecurityjdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired // the general way to tell spring security that where is your database by configuring a dataSource bean
    private DataSource dataSource; // autowiring this datasource bean make you to configure datasource bean in somewhere else.
                                    // here the datasource has been wired in as an instance and passed this instance to the JDBC authentication as in the below method
                                    // here the datasource is pointed to H2 database and it could be changed to mysql or Oracle and the spring security do all the following stuffs to the database

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);

        auth.jdbcAuthentication()           // this time we change here since we do jdbc authentication
                .dataSource(dataSource) // here i have configure spring security to point to your h2 database
                .withDefaultSchema()     // spring sec is configured with the default schema if we have given a clean database and it has created two tables as users tables and authorization table with keys in the embeded database;
                .withUser(              //adding users as user objects as follows for the default tables
                        User.withUsername("user").password("123").roles("USER")
                ).withUser(
                        User.withUsername("admin").password("123").roles("ADMIN")
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);

        http.authorizeRequests().antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER","ADMIN")
                .and().formLogin();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }



}
