```bash
==========================
CONDITION EVALUATION DELTA
==========================


Positive matches:
-----------------

    None


Negative matches:
-----------------

   SpringBootWebSecurityConfiguration.WebSecurityEnablerConfiguration:
      Did not match:
         - @ConditionalOnMissingBean (names: springSecurityFilterChain; SearchStrategy: all) found beans named springSecurityFilterChain (OnBeanCondition)
      Matched:
         - @ConditionalOnClass found required class 'org.springframework.security.config.annotation.web.configuration.EnableWebSecurity' (OnClassCondition)

   UserDetailsServiceAutoConfiguration:
      Did not match:
         - @ConditionalOnMissingBean (types: org.springframework.security.authentication.AuthenticationManager,org.springframework.security.authentication.AuthenticationProvider,org.springframework.security.core.userdetails.UserDetailsService,org.springframework.security.authentication.AuthenticationManagerResolver,org.springframework.security.oauth2.jwt.JwtDecoder; SearchStrategy: all) found beans of type 'org.springframework.security.authentication.AuthenticationManager' authenticationManager and found beans of type 'org.springframework.security.core.userdetails.UserDetailsService' usersService (OnBeanCondition)
      Matched:
         - @ConditionalOnClass found required class 'org.springframework.security.authentication.AuthenticationManager' (OnClassCondition)
         - AnyNestedCondition 1 matched 2 did not; NestedCondition on UserDetailsServiceAutoConfiguration.MissingAlternativeOrUserPropertiesConfigured.PasswordConfigured @ConditionalOnProperty (spring.security.user.password) did not find property 'password'; NestedCondition on UserDetailsServiceAutoConfiguration.MissingAlternativeOrUserPropertiesConfigured.NameConfigured @ConditionalOnProperty (spring.security.user.name) did not find property 'name'; NestedCondition on UserDetailsServiceAutoConfiguration.MissingAlternativeOrUserPropertiesConfigured.MissingAlternative @ConditionalOnMissingClass did not find unwanted classes 'org.springframework.security.oauth2.client.registration.ClientRegistrationRepository', 'org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector', 'org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository' (UserDetailsServiceAutoConfiguration.MissingAlternativeOrUserPropertiesConfigured)
```

