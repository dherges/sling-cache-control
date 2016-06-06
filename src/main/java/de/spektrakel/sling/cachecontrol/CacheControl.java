package de.spektrakel.sling.cachecontrol;

import org.apache.sling.api.SlingHttpServletResponse;

import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import javax.inject.Inject;


/**
 * Cache control response directive for a sling response.
 *
 * @link https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9
 */
@Model(adaptables = SlingHttpServletResponse.class)
public class CacheControl {

  @Inject @Self
  private SlingHttpServletResponse response;

  private boolean noCache = false;
  private boolean noStore = false;
  private boolean isPrivate = false;
  private boolean isPublic = false;
  private boolean mustRevalidate = false;
  private boolean proxyRevalidate = false;
  private boolean noTransform = false;
  private long maxAgeSeconds = -1;
  private long sMaxAgeSeconds = -1;


  /** @return the original response that backs this response directive */
  public SlingHttpServletResponse response() {
    return response;
  }

  /** @return the response with Cache-Control header */
  public SlingHttpServletResponse build() {
    response.setHeader("Cache-Control", headerValue());

    return response;
  }

  /** @return the string value of the Cache-Control header */
  public String headerValue() {
    StringBuilder result = new StringBuilder();
    if (noCache) result.append("no-cache, ");
    if (noStore) result.append("no-store, ");
    if (maxAgeSeconds != -1) result.append("max-age=").append(maxAgeSeconds).append(", ");
    if (sMaxAgeSeconds != -1) result.append("s-maxage=").append(sMaxAgeSeconds).append(", ");
    if (isPrivate) result.append("private, ");
    if (isPublic) result.append("public, ");
    if (mustRevalidate) result.append("must-revalidate, ");
    if (proxyRevalidate) result.append("proxy-revalidate, ");
    if (noTransform) result.append("no-transform, ");
    if (result.length() == 0) return "";
    result.delete(result.length() - 2, result.length());

    return result.toString();
  }

  /** Cache-Control: public */
  public CacheControl pub() {
    isPublic = true;

    return this;
  }

  /** Cache-Control: private */
  public CacheControl priv() {
    isPrivate = true;

    return this;
  }

  /** Cache-Control: no-cache */
  public CacheControl noCache() {
    noCache = true;

    return this;
  }

  /** Cache-Control: no-cache, no-store */
  public CacheControl noStore() {
    noCache = true;
    noStore = true;

    return this;
  }

  /** Cache-Control: no-transform */
  public CacheControl noTransform() {
    noTransform = true;

    return this;
  }

  /** Cache-Control: must-revalidate */
  public CacheControl mustRevalidate() {
    mustRevalidate = true;

    return this;
  }

  /** Cache-Control: proxy-revalidate */
  public CacheControl proxyRevalidate() {
    proxyRevalidate = true;

    return this;
  }

  /** Cache-Control: max-age=<deltaSeconds> ... implies public if none of private, no-cache, or no-store is defined */
  public CacheControl maxAge(int deltaSeconds) {
    maxAgeSeconds = deltaSeconds;

    return this;
  }

  /** Cache-Control: s-maxage=<deltaSeconds> ... same as max-age for shared caches */
  public CacheControl sMaxAge(int deltaSeconds) {
    sMaxAgeSeconds = deltaSeconds;

    return this;
  }

}
