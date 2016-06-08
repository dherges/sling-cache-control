package de.spektrakel.sling.cachecontrol;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.stream.Collectors;

import javax.inject.Inject;


/**
 * Entity Tag representation of a sling resource.
 */
@Model(adaptables = Resource.class)
public class ETag {

  @Inject @Self
  private Resource resource;

  /** @return the ETag value of this resource */
  public String value() {
    final Resource res = resource.getChild("jcr:content") != null
      ? resource.getChild("jcr:content")
      : resource;

    final ValueMap vm = res.getValueMap();
    if (vm.containsKey("jcr:etag")) {
      return vm.get("jcr:etag", "");
    }

    // if nt:file -> hash of binary content

    // if jcr:content tree -> recursive walk
    String longString = new ResourceTree(res)
      .collectChildren()
      .stream()
      .map(r -> r.getPath() + ":" + r.getValueMap())
      .collect(Collectors.joining(";"));

    return "";
  }

  private static String valueMapHash(ValueMap vm) {
    return vm.entrySet()
             .stream()
             .map(entry -> entry.getKey() + "=" + entry.getValue())
             .collect(Collectors.joining(","));
  }

  private static class ResourceTree {
    private final Resource resource;
    private final List<Resource> list = new ArrayList<Resource>();
    public ResourceTree(Resource resource) {
      this.resource = resource;
    }

    public List<Resource> collectChildren() {
      collectToList(resource);

      return list;
    }

    private void collectToList(Resource resource) {
      list.add(resource);

      final Iterable<Resource> children = resource.getChildren();
      for (Resource res : children) {
        collectToList(res);
      }
    }
  }

}
