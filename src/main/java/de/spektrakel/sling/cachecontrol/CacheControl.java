package de.spektrakel.sling.cachecontrol;

import org.apache.sling.api.SlingHttpServletRequest;

import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import javax.inject.Inject;


@Model(adaptables = SlingHttpServletRequest.class)
public class CacheControl {

  @Inject @Self
  private SlingHttpServletRequest request;

  public SlingHttpServletRequest request() {
    return request;
  }

  public CacheControl public_() { // Cache-Control: public
    return this;
  }

  public CacheControl private_() { // Cache-Control: private
    return this;
  }

  public CacheControl noCache() { // Cache-Control: no-cache
    return this;
  }

  public CacheControl noState() { // Cache-Control: no-cache, no-store
    return this;
  }

  public CacheControl maxAge(int seconds) { // implies public if none of private, no-cache, or no-store is defined
    return this;
  }

  public CacheControl sMaxAge(int seconds) { // ...
    return this;
  }

  public CacheControl mustRevalidate() { // ...
    return this;
  }

  public CacheControl proxyRevalidate() { // ...
    return this;
  }

  public CacheControl noTransform() { // ...
    return this;
  }

}
