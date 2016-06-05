package de.spektrakel.sling.cachecontrol;

import org.apache.sling.api.SlingHttpServletRequest;

import org.apache.sling.testing.mock.sling.junit.SlingContext;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;

import static org.assertj.core.api.Assertions.assertThat;


public class CacheControlTest {

  @Rule
  public final SlingContext context = new SlingContext();

  private CacheControl cacheControl;
  private SlingHttpServletRequest request;

  @Before
  public void setup() {
    context.addModelsForPackage("de.spektrakel.sling.cachecontrol");

    // basic usage
    request = context.request();
    cacheControl = request.adaptTo(CacheControl.class);
    assertThat(cacheControl).isNotNull();
    assertThat(cacheControl.request()).isSameAs(request);
  }

  @Test
  public void requestAdaptToCacheControl() {
    // TODO ...
  }

}
