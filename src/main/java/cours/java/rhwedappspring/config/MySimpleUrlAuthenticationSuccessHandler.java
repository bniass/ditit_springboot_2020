package cours.java.rhwedappspring.config;

import cours.java.rhwedappspring.dao.UtilisateurRepository;
import cours.java.rhwedappspring.model.Utilisateur;
import cours.java.rhwedappspring.service.CustomUserDetailService;
import cours.java.rhwedappspring.service.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@SuppressWarnings("unused")
public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  protected Log logger = LogFactory.getLog(this.getClass());

  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response, Authentication authentication)
    throws IOException {

      handle(request, response, authentication);
      clearAuthenticationAttributes(request);
  }

  protected void handle(HttpServletRequest request,
                        HttpServletResponse response, Authentication authentication)
    throws IOException {

      String targetUrl = determineTargetUrl(authentication);

      if (response.isCommitted()) {
          logger.debug(
            "Response has already been committed. Unable to redirect to "
            + targetUrl);
          return;
      }

      redirectStrategy.sendRedirect(request, response, targetUrl);
  }

  @Autowired
  private UtilisateurRepository iUtilisateur;
  @Autowired
  private Utils utils;

  @Autowired
  private CustomUserDetailService userDetailsService;
  
  protected String determineTargetUrl(Authentication authentication) {
      boolean isMedecin = false;
      boolean isAdmin = false;
      Collection<? extends GrantedAuthority> authorities
       = authentication.getAuthorities();

      for (GrantedAuthority grantedAuthority : authorities) {
          if (grantedAuthority.getAuthority().equals("ROLE_MEDECIN")) {
        	  isMedecin = true;
              break;
          }
          else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
              isAdmin = true;
              break;
          }
      }
      if (isMedecin) {
          return "/medecin/all";
      } else if (isAdmin) {
          return "/medecin/add";
      }
      else {
          throw new IllegalStateException();
      }
  }

  protected void clearAuthenticationAttributes(HttpServletRequest request) {
      HttpSession session = request.getSession(false);
      if (session == null) {
          return;
      }
      session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
  }

  public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
      this.redirectStrategy = redirectStrategy;
  }
  protected RedirectStrategy getRedirectStrategy() {
      return redirectStrategy;
  }

}