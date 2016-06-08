package de.spektrakel.sling.cachecontrol;

import org.apache.felix.scr.annotations.sling.SlingFilter;
import org.apache.felix.scr.annotations.sling.SlingFilterScope;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;


@SlingFilter(
  metatype = false,
  generateComponent = true,
  generateService = true,
  order = 0, // The smaller the number, the earlier in the Filter chain (can go negative);
             // Defaults to Integer.MAX_VALUE which push it at the end of the chain
  scope = SlingFilterScope.REQUEST) // REQUEST, INCLUDE, FORWARD, ERROR, COMPONENT (REQUEST, INCLUDE, COMPONENT)
public class IfNoneMatchFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // Usually, do nothing
  }

  @Override
  public void destroy() {
    // Usually, do Nothing
  }

  @Override
  public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    SlingHttpServletRequest req = (SlingHttpServletRequest) request;
    SlingHttpServletResponse res = (SlingHttpServletResponse) response;

    if ("GET".equals(req.getMethod()) || "HEAD".equals(req.getMethod())) {
      // ETag
      String eTag = req.getResource().adaptTo(ETag.class).value();

      if (eTag.equals(req.getHeader("If-None-Match"))) {
        // TODO ... set response status code + header
        res.setStatus(304);

        return;
      }
    }

    // Finally, proceed with the the Filter chain
    chain.doFilter(request, response);
  }

}
