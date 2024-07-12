package br.com.ruandevjs.developmentsblog.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.ruandevjs.developmentsblog.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterUserAuth extends OncePerRequestFilter {

  @Autowired
  IUserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    var serveletPath = request.getServletPath();
    var requestMethod = request.getMethod().trim();

    if (serveletPath.startsWith("/users") && requestMethod.equals("GET")) {
      var authorization = request.getHeader("Authorization");
      var authorizationEnconded = authorization.substring("Basic".length()).trim();

      byte[] authDecode = Base64.getDecoder().decode(authorizationEnconded);
      var authString = new String(authDecode);

      String[] credentials = authString.split(":");

      var userName = credentials[0];
      var password = credentials[1];

      var user = this.userRepository.findByUsername(userName);

      if (user == null) {
        response.sendError(401, "Usuário sem autenticação!");
      } else {
        var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

        if (passwordVerify.verified) {
          filterChain.doFilter(request, response);
        } else {
          response.sendError(401, "Usuário sem autenticação!");
        }
      }
    } else {
      filterChain.doFilter(request, response);
    }

  }
}
