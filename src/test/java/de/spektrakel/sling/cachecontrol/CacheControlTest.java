package de.spektrakel.sling.cachecontrol;

import org.apache.sling.api.SlingHttpServletResponse;

import org.apache.sling.testing.mock.sling.junit.SlingContext;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;

import static org.assertj.core.api.Assertions.assertThat;


public class CacheControlTest {

  @Rule
  public final SlingContext context = new SlingContext();

  private CacheControl cacheControl;
  private SlingHttpServletResponse response;

  @Before
  public void setup() {
    context.addModelsForPackage("de.spektrakel.sling.cachecontrol");

    // basic usage
    response = context.response();
    cacheControl = response.adaptTo(CacheControl.class);
    assertThat(cacheControl).isNotNull();
    assertThat(cacheControl.response()).isSameAs(response);
  }

  @Test
  public void public_headerValue() {
    assertThat(cacheControl
      .pub()
      .headerValue())
      .isEqualTo("public");
  }

  @Test
  public void private_headerValue() {
    assertThat(cacheControl
      .priv()
      .headerValue())
      .isEqualTo("private");
  }

  @Test
  public void noCache_headerValue() {
    assertThat(cacheControl
      .noCache()
      .headerValue())
      .isEqualTo("no-cache");
  }

  @Test
  public void noStore_headerValue() {
    assertThat(cacheControl
      .noStore()
      .headerValue())
      .isEqualTo("no-cache, no-store");
  }

  @Test
  public void noTransform_headerValue() {
    assertThat(cacheControl
      .noTransform()
      .headerValue())
      .isEqualTo("no-transform");
  }

  @Test
  public void mustRevalidate_headerValue() {
    assertThat(cacheControl
      .mustRevalidate()
      .headerValue())
      .isEqualTo("must-revalidate");
  }

  @Test
  public void proxyRevalidate_headerValue() {
    assertThat(cacheControl
      .proxyRevalidate()
      .headerValue())
      .isEqualTo("proxy-revalidate");
  }

}
