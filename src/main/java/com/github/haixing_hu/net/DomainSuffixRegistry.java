/******************************************************************************
 *
 * Copyright (c) 2013  Haixing Hu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package com.github.haixing_hu.net;

import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.haixing_hu.CommonsConfig;
import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.text.xml.InvalidXmlNodeContentException;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.text.xml.XmlUtils;
import com.github.haixing_hu.util.config.Config;

import static com.github.haixing_hu.text.xml.DomUtils.*;

/**
 * A {@link DomainSuffixRegistry} object stores the map of the
 * {@link DomainSuffix} objects.
 *
 * @author Haixing Hu
 */
@Immutable
public final class DomainSuffixRegistry {

  /**
   * The value of this property is the name of the XML resource file of the
   * {@link DomainSuffixRegistry} class.
   *
   * <table border="1">
   * <tr>
   * <th>Type</th>
   * <th>Count</th>
   * <th>Value</th>
   * <th>Required</th>
   * <th>Default</th>
   * <th>Range</th>
   * </tr>
   * <tr>
   * <td>String</td>
   * <td>1</td>
   * <td>the name of the XML resource file of the {@link DomainSuffixRegistry} class.</td>
   * <td>no</td>
   * <td>{@link DEFAULT_RESOURCE}</td>
   * <td></td>
   * </tr>
   * </table>
   *
   * @see #DEFAULT_RESOURCE
   */
  public static final String PROPERTY_RESOURCE = "com.github.haixing_hu.net.DomainSuffixRegistry.resource";

  /**
   * The default value of the property {@link PROPERTY_RESOURCE}.
   *
   * @see PROPERTY_RESOURCE
   */
  public static final String DEFAULT_RESOURCE = "domain-suffixes.xml";

  public static final String ROOT_NODE = "domains";

  public static final String SUFFIXES_NODE = "suffixes";

  public static final String TLDS_NODE = "tlds";

  public static final String ITLDS_NODE = "itlds";

  public static final String GTLDS_NODE = "gtlds";

  public static final String CCTLDS_NODE = "cctlds";

  public static final String TLD_NODE = "tld";

  public static final String STATUS_NODE = "status";

  public static final String DESCRIPTION_NODE = "descrption";

  public static final String COUNTRY_NODE = "country";

  public static final String SUFFIX_NODE = "suffix";

  public static final String DOMAIN_ATTRIBUTE = "domain";

  private static final Logger LOGGER = LoggerFactory.getLogger(DomainSuffixRegistry.class);

  private static volatile DomainSuffixRegistry instance;

  /**
   * Get the singleton instance of the DomainSuffixes class, with lazy
   * instantiation.
   *
   * @return the singleton instance of the DomainSuffixes class.
   */
  public static DomainSuffixRegistry getInstance() {
    // use the double locked check trick.
    if (instance == null) {
      synchronized (DomainSuffixRegistry.class) {
        if (instance == null) {
          final Config config = CommonsConfig.get();
          final String resource = config.getString(PROPERTY_RESOURCE,
              DEFAULT_RESOURCE);
          instance = new DomainSuffixRegistry(resource);
        }
      }
    }
    return instance;
  }

  private final HashMap<String, DomainSuffix> domains;

  public DomainSuffixRegistry(final String configResourceName) {
    domains = new HashMap<String, DomainSuffix>();
    final ClassLoader classLoader = DomainSuffixRegistry.class.getClassLoader();
    final URL configResourceUrl = classLoader.getResource(configResourceName);
    if (configResourceUrl == null) {
      LOGGER.error("Can't find the domain suffixes configuration file: {}.",
          configResourceName);
    } else {
      loadFromResource(configResourceUrl);
    }
  }

  public DomainSuffixRegistry(final URL configResourceUrl) {
    domains = new HashMap<String, DomainSuffix>();
    loadFromResource(configResourceUrl);
  }

  /**
   * Tests whether the extension is a registered domain entry.
   */
  public boolean isDomainSuffix(final String extension) {
    return domains.containsKey(extension);
  }

  /**
   * Tests whether the extension is a top level domain entry.
   */
  public boolean isTopLevelDomain(final String domain) {
    final DomainSuffix suffix = domains.get(domain);
    return (suffix != null) && suffix.isTopLevel();
  }

  /**
   * Return the {@link DomainSuffix} object for the extension, if extension is a
   * top level domain returned object will be an instance of
   * {@link TopLevelDomain}
   *
   * @param extension
   *          extension of the domain
   */
  public DomainSuffix getDomainSuffix(final String extension) {
    return domains.get(extension);
  }

  /**
   * Return the {@link TopLevelDomain} object for the extension, if extension is
   * not a top level domain returns null.
   *
   * @param extension
   *          extension of the top level domain.
   */
  public TopLevelDomain getTopLevelDomain(final String extension) {
    final DomainSuffix suffix = domains.get(extension);
    if ((suffix != null) && (suffix instanceof TopLevelDomain)) {
      return (TopLevelDomain) suffix;
    } else {
      return null;
    }
  }

  private void loadFromResource(final URL configResourceUrl) {
    try {
      final Document doc = XmlUtils.parse(configResourceUrl);
      parse(doc.getDocumentElement());
    } catch (final XmlException e) {
      LOGGER.error("Parsring {} failed.", configResourceUrl, e);
    }
  }

  private void parse(final Element root) throws XmlException {
    checkNode(root, ROOT_NODE);
    List<Element> nodeList = getReqChildren(root, 2, 2, null);
    final Element tldsNode = nodeList.get(0);
    checkNode(tldsNode, TLDS_NODE);
    final Element suffixesNode = nodeList.get(1);
    checkNode(suffixesNode, SUFFIXES_NODE);
    nodeList = getReqChildren(tldsNode, 3, 3, nodeList);
    final Element itldsNode = nodeList.get(0);
    checkNode(itldsNode, ITLDS_NODE);
    final Element gtldsNode = nodeList.get(1);
    checkNode(gtldsNode, GTLDS_NODE);
    final Element cctldsNode = nodeList.get(2);
    checkNode(cctldsNode, CCTLDS_NODE);
    parseItlds(itldsNode, nodeList);
    parseGtlds(gtldsNode, nodeList);
    parseCctlds(cctldsNode, nodeList);
    parseSuffixes(suffixesNode, nodeList);
  }

  private void parseItlds(final Element itlds, List<Element> nodeList)
      throws XmlException {
    LOGGER.trace("Parsing <itlds>.");
    nodeList = getChildren(itlds, nodeList);
    if ((nodeList != null) && (! nodeList.isEmpty())) {
      for (final Element node : nodeList) {
        checkNode(node, TLD_NODE);
        final TopLevelDomain tld = parseGtld(node,
            TopLevelDomain.Type.INFRASTRUCTURE);
        domains.put(tld.domain, tld);
        LOGGER.trace("Parsed a <itld>: {}", tld);
      }
    }
  }

  private void parseGtlds(final Element gtldsNode, List<Element> nodeList)
      throws XmlException {
    LOGGER.trace("Parsing <gtlds>.");
    nodeList = getChildren(gtldsNode, nodeList);
    if ((nodeList != null) && (! nodeList.isEmpty())) {
      for (final Element tldNode : nodeList) {
        checkNode(tldNode, TLD_NODE);
        final TopLevelDomain tld = parseGtld(tldNode,
            TopLevelDomain.Type.GENERIC);
        domains.put(tld.domain, tld);
        LOGGER.trace("Parsed a <gtld>: {}", tld);
      }
    }
  }

  private void parseCctlds(final Element cctldsNode, List<Element> nodeList)
      throws XmlException {
    LOGGER.trace("Parsing <cctlds>.");
    nodeList = getChildren(cctldsNode, nodeList);
    if ((nodeList != null) && (! nodeList.isEmpty())) {
      for (final Element tldNode : nodeList) {
        checkNode(tldNode, TLD_NODE);
        final TopLevelDomain tld = parseCctld(tldNode);
        domains.put(tld.domain, tld);
        LOGGER.trace("Parsed a <cctld>: {}", tld);
      }
    }
  }

  private TopLevelDomain parseGtld(final Element tldNode,
      final TopLevelDomain.Type type) throws XmlException {
    final String domain = getReqStringAttr(tldNode, DOMAIN_ATTRIBUTE,
        true, false);
    final DomainSuffix.Status status = parseStatus(tldNode);
    final String description = parseDescription(tldNode);
    return new TopLevelDomain(domain, status, description, type,
        StringUtils.EMPTY);
  }

  private TopLevelDomain parseCctld(final Element cctldNode)
      throws XmlException {
    final String domain = getReqStringAttr(cctldNode,
        DOMAIN_ATTRIBUTE, true, false);
    final String country = parseCountry(cctldNode);
    final DomainSuffix.Status status = parseRestrictedStatus(cctldNode);
    final String description = parseDescription(cctldNode);
    return new TopLevelDomain(domain, status, description,
        TopLevelDomain.Type.COUNTRY, country);
  }

  private DomainSuffix.Status parseStatus(final Element tldNode)
      throws XmlException {
    final Element statusNode = getOptChild(tldNode, STATUS_NODE);
    if (statusNode == null) {
      return DomainSuffix.DEFAULT_STATUS;
    }
    final String statusName = getReqString(statusNode, null, true,
        false);
    try {
      return DomainSuffix.Status.valueOf(statusName);
    } catch (final IllegalArgumentException e) {
      throw new InvalidXmlNodeContentException(STATUS_NODE, statusName);
    }
  }

  private String parseDescription(final Element tldNode) throws XmlException {
    final Element descriptionNode = getOptChild(tldNode,
        DESCRIPTION_NODE);
    if (descriptionNode == null) {
      return StringUtils.EMPTY;
    } else {
      return getOptString(descriptionNode, null, true,
          StringUtils.EMPTY);
    }
  }

  private String parseCountry(final Element tldNode) throws XmlException {
    final Element countryNode = getReqChild(tldNode, COUNTRY_NODE);
    return getReqString(countryNode, null, true, false);
  }

  private DomainSuffix.Status parseRestrictedStatus(final Element tldNode)
      throws XmlException {
    final Element statusNode = getOptChild(tldNode, STATUS_NODE);
    if (statusNode == null) {
      return DomainSuffix.DEFAULT_STATUS;
    }
    final String statusName = getReqString(statusNode, null, true,
        false);
    DomainSuffix.Status status;
    try {
      status = DomainSuffix.Status.valueOf(statusName);
    } catch (final IllegalArgumentException e) {
      throw new InvalidXmlNodeContentException(STATUS_NODE, statusName);
    }
    switch (status) {
      case IN_USE:
      case NOT_IN_USE:
      case DELETED:
        return status;
      default:
        throw new InvalidXmlNodeContentException(STATUS_NODE, statusName);
    }
  }

  private void parseSuffixes(final Element suffixesNode, List<Element> nodeList)
      throws XmlException {
    LOGGER.trace("Parsing <suffixes> ...");
    nodeList = getChildren(suffixesNode, nodeList);
    if ((nodeList != null) && (! nodeList.isEmpty())) {
      for (final Element suffixNode : nodeList) {
        checkNode(suffixNode, SUFFIX_NODE);
        final DomainSuffix suffix = parseSuffix(suffixNode);
        domains.put(suffix.domain, suffix);
        LOGGER.trace("Parsed a <suffix>: {}", suffix);
      }
    }
  }

  private DomainSuffix parseSuffix(final Element suffixNode)
      throws XmlException {
    final String domain = getReqStringAttr(suffixNode,
        DOMAIN_ATTRIBUTE, true, false);
    final DomainSuffix.Status status = parseRestrictedStatus(suffixNode);
    final String description = parseDescription(suffixNode);
    return new DomainSuffix(domain, status, description);
  }
}
