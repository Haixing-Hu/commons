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

import org.junit.Test;

import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.net.DomainSuffix;
import com.github.haixing_hu.net.DomainSuffixRegistry;
import com.github.haixing_hu.net.TopLevelDomain;

import static org.junit.Assert.*;

/**
 * Unit test of the DomainSuffixes class.
 *
 * @author Haixing Hu
 */
public class DomainSuffixRegistryTest {

  @Test
  public void testParseDefaultResource() {
    final DomainSuffixRegistry domainSuffixes = DomainSuffixRegistry.getInstance();
    assertNotNull(domainSuffixes);
  }

  @Test
  public void testItlds() {
    final DomainSuffixRegistry domainSuffixes = DomainSuffixRegistry.getInstance();
    assertNotNull(domainSuffixes);
    TopLevelDomain tld;

    // check itlds
    tld = domainSuffixes.getTopLevelDomain("root");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.INFRASTRUCTURE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.INFRASTRUCTURE, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("arpa");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.INFRASTRUCTURE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.INFRASTRUCTURE, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());
  }

  @Test
  public void testGtlds() {
    final DomainSuffixRegistry domainSuffixes = DomainSuffixRegistry.getInstance();
    assertNotNull(domainSuffixes);
    TopLevelDomain tld;

    // check gtlds
    tld = domainSuffixes.getTopLevelDomain("aero");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.SPONSORED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("biz");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.UNSPONSORED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("cat");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.SPONSORED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("com");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.UNSPONSORED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("coop");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.SPONSORED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("edu");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.UNSPONSORED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("gov");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.UNSPONSORED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());


    tld = domainSuffixes.getTopLevelDomain("info");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.UNSPONSORED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("int");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.UNSPONSORED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("jobs");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.SPONSORED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("mil");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.UNSPONSORED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("mobi");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.SPONSORED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("museum");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.SPONSORED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("name");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.UNSPONSORED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("net");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.UNSPONSORED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("org");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.UNSPONSORED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("pro");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.SPONSORED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("travel");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.SPONSORED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("asia");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.STARTUP, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("post");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.PROPOSED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("tel");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.STARTUP, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("geo");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.PROPOSED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("gal");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.PROPOSED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("cym");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.PROPOSED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("sco");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.PROPOSED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("kid");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.PROPOSED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("kids");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.PROPOSED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("mail");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.PROPOSED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("web");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.PROPOSED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("xxx");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.PROPOSED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("nato");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.DELETED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("bitnet");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.PSEUDO_DOMAIN, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("csnet");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.PSEUDO_DOMAIN, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("uucp");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.PSEUDO_DOMAIN, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("local");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.PSEUDO_DOMAIN, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("internal");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.PSEUDO_DOMAIN, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("onion");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.PSEUDO_DOMAIN, tld.getStatus());
    assertEquals(TopLevelDomain.Type.GENERIC, tld.getType());
    assertEquals(StringUtils.EMPTY, tld.getCountry());
  }

  @Test
  public void testCctlds() {
    final DomainSuffixRegistry domainSuffixes = DomainSuffixRegistry.getInstance();
    assertNotNull(domainSuffixes);
    TopLevelDomain tld;

    tld = domainSuffixes.getTopLevelDomain("ac");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Ascension Island", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ad");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Andorra", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ae");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("United Arab Emirates", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("af");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Afghanistan", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ag");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Antigua and Barbuda", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ai");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Anguilla", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("al");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Albania", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("am");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Armenia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("an");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Netherlands Antilles", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ao");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Angola", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("aq");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Antarctica", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ar");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Argentina", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("as");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("American Samoa", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("at");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Austria", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("au");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Australia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("aw");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Aruba", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ax");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Aland Islands", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("az");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Azerbaijan", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ba");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Bosnia and Herzegovina", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("bb");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Barbados", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("bd");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Bangladesh", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("be");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Belgium", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("bf");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Burkina Faso", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("bg");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Bulgaria", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("bh");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Bahrain", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("bi");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Burundi", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("bj");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Benin", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("bm");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Bermuda", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("as");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("American Samoa", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("bn");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Brunei", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("bo");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Bolivia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("br");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Brazil", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("bs");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Bahamas", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("as");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("American Samoa", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("bt");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Bhutan", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("bu");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.NOT_IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Burma", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("bv");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.NOT_IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Bouvet Island", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("bw");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Botswana", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("by");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Belarus", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("bz");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Belize", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ca");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Canada", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("cc");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Cocos Keeling Islands", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("cd");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Democratic Republic of the Congo", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("cf");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Central African Republic", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("cg");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Republic of the Congo", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ch");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Switzerland", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ci");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Côte d'Ivoire", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ck");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Cook Islands", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("cl");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Chile", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("cm");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Cameroon", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("cn");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("People s Republic of China", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("co");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Colombia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("cr");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Costa Rica", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("cs");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.DELETED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Serbia and Montenegro", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("cu");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Cuba", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("cv");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Cape Verde", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("cx");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Christmas Island", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("cy");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Cyprus", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("cz");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Czech Republic", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("dd");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.DELETED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("German Democratic Republic(East Germany)", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("de");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Germany", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("dj");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Djibouti", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("dk");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Denmark", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("dm");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Dominica", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("do");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Dominican Republic", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("dz");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Algeria", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ec");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Ecuador", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ee");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Estonia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("eg");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Egypt", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("eh");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.NOT_IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Western Sahara", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("er");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Eritrea", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("es");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Spain", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("et");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Ethiopia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("eu");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("European Union", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("fi");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Finland", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("fj");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Fiji", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("fk");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Falkland Islands", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("fm");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Federated States of Micronesia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("fo");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Faroe Islands", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("fr");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("France", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ga");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Gabon", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("gb");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("United Kingdom", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("gd");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Grenada", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ge");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Georgia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("gf");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("French Guiana", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("gg");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Guernsey", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("gh");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Ghana", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("gi");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Gibraltar", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("gl");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Greenland", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("gm");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Gambia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("gn");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Guinea", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("gp");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Guadeloupe", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("gq");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Equatorial Guinea", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("gr");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Greece", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("gs");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("South Georgia and the South Sandwich Islands", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("gt");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Guatemala", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("gu");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Guam", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("gw");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Guinea Bissau", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("gy");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Guyana", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("hk");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Hong Kong", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("hm");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Heard Island and McDonald Islands", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("hn");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Honduras", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("hr");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Croatia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ht");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Haiti", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("hu");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Hungary", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("id");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Indonesia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("eg");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Egypt", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ie");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Ireland", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("il");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Israel", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("im");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Isle of Man", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("eg");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Egypt", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("in");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("India", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("io");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("British Indian Ocean Territory", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("iq");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Iraq", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ir");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Iran", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("is");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Iceland", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("it");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Italy", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("je");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Jersey", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("jm");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Jamaica", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("jo");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Jordan", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("jp");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Japan", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ke");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Kenya", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("kg");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Kyrgyzstan", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("kh");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Cambodia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ki");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Kiribati", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("km");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Comoros", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("kn");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Saint Kitts and Nevis", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("kp");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.NOT_IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("North Korea", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("kr");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("South Korea", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("kw");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Kuwait", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ky");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Cayman Islands", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("kz");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Kazakhstan", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("la");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Laos", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("lb");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Lebanon", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("lc");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Saint Lucia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("li");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Liechtenstein", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("lk");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Sri Lanka", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("lr");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Liberia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ls");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Lesotho", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("lt");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Lithuania", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("lu");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Luxembourg", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("lv");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Latvia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ly");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Libya", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ma");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Morocco", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("mc");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Monaco", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("md");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Moldova", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("me");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Montenegro", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("mg");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Madagascar", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("mh");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Marshall Islands", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("mk");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Republic of Macedonia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ml");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Mali", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("mm");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Myanmar", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("mn");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Mongolia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("mo");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Macau", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("mp");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Northern Mariana Islands", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("mq");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Martinique", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("mr");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Mauritania", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ms");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Montserrat", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("mt");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Malta", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("mu");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Mauritius", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("mv");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Maldives", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("mw");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Malawi", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("mx");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Mexico", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("my");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Malaysia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("mz");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Mozambique", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("na");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Namibia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("nc");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("New Caledonia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ne");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Niger", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("nf");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Norfolk Island", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ng");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Nigeria", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ni");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Nicaragua", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("nl");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Netherlands", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("no");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Norway", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("np");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Nepal", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("nr");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Nauru", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("nu");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Niue", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("nz");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("New Zealand", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("om");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Oman", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("pa");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Panama", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("pe");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Peru", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("pf");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("French Polynesia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("pg");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Papua New Guinea", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ph");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Philippines", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("pk");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Pakistan", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("pl");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Poland", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("pm");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Saint Pierre and Miquelon", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("pn");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Pitcairn Islands", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("pr");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Puerto Rico", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ps");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Palestinian territories", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("pt");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Portugal", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("pw");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Palau", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("py");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Paraguay", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("qa");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Qatar", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("re");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Réunion", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ro");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Romania", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("rs");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Serbia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ru");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Russia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("rw");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Rwanda", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("sa");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Saudi Arabia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("sb");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Solomon Islands", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("sc");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Seychelles", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("sd");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Sudan", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("se");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Sweden", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("sg");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Singapore", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("sh");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Saint Helena", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("si");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Slovenia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("sj");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.NOT_IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Svalbard and Jan Mayen Islands", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("sk");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Slovakia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("sl");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Sierra Leone", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("sm");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("San Marino", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("sn");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Senegal", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("so");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Somalia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("sr");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Suriname", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("st");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("São Tomé and Príncipe", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("su");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.DELETED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Soviet Union", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("sv");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("El Salvador", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("sy");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Syria", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("sz");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Swaziland", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("tc");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Turks and Caicos Islands", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("td");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Chad", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("tf");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("French Southern Territories", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("tg");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Togo", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("th");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Thailand", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("tj");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Tajikistan", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("tk");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Tokelau", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("tl");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("East Timor", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("tm");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Turkmenistan", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("tn");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Tunisia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("to");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Tonga", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("tp");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.DELETED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("East Timor", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("tr");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Turkey", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("tt");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Trinidad and Tobago", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("tv");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Tuvalu", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("tw");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Taiwan of the People's Republic of China", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("tz");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Tanzania", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ua");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Ukraine", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ug");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Uganda", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("uk");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("United Kingdom", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("um");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.DELETED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("United States Minor Outlying Islands", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("us");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("United States", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("uy");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Uruguay", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("uz");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Uzbekistan", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("va");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Vatican City", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("vc");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Saint Vincent and the Grenadines", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ve");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Venezuela", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("vg");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("British Virgin Islands", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("vi");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("United States Virgin Islands", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("vn");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Vietnam", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("vu");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Vanuatu", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("wf");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Wallis and Futuna", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ws");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Samoa", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("ye");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Yemen", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("yt");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Mayotte", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("yu");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Yugoslavia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("za");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("South Africa", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("zm");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Zambia", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("zr");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.DELETED, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Zaire", tld.getCountry());

    tld = domainSuffixes.getTopLevelDomain("zw");
    assertNotNull(tld);
    assertEquals(true, tld.isTopLevel());
    assertEquals(DomainSuffix.Status.IN_USE, tld.getStatus());
    assertEquals(TopLevelDomain.Type.COUNTRY, tld.getType());
    assertEquals("Zimbabwe", tld.getCountry());

  }

}
