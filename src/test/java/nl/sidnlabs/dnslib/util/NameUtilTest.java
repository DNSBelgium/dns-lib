/*
 * ENTRADA, a big data platform for network data analytics
 *
 * Copyright (C) 2016 SIDN [https://www.sidn.nl]
 * 
 * This file is part of ENTRADA.
 * 
 * ENTRADA is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * ENTRADA is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with ENTRADA. If not, see
 * [<http://www.gnu.org/licenses/].
 *
 */
package nl.sidnlabs.dnslib.util;

import org.junit.Assert;
import org.junit.Test;

public class NameUtilTest {

  @Test
  public void testExtractDomainLevelOk() {
    Domaininfo info = NameUtil.getDomain("sidn.nl.");
    Assert.assertEquals("sidn.nl", info.getName());
    Assert.assertEquals(2, info.getLabels());

    info = NameUtil.getDomain("sidn.nl");
    Assert.assertEquals("sidn.nl", info.getName());
    Assert.assertEquals(2, info.getLabels());

    info = NameUtil.getDomain("www.sidn.nl.");
    Assert.assertEquals("sidn.nl", info.getName());
    Assert.assertEquals(3, info.getLabels());

    info = NameUtil.getDomain("test.www.sidn.nl.");
    Assert.assertEquals("sidn.nl", info.getName());
    Assert.assertEquals(4, info.getLabels());

    info = NameUtil.getDomain("www.nzrs.co.nz.");
    Assert.assertEquals("nzrs.co.nz", info.getName());
    Assert.assertEquals(4, info.getLabels());
  }

  @Test
  public void testEmailAddress2ndLevelOk() {
    // email address should not happen, but sometimes we do see these in the qname.
    Assert
        .assertEquals("email.test@example.com",
            NameUtil.getDomain("email.test@example.com.").getName());
  }

  @Test
  public void testDomainWith2ndLevelAndTldSuffixOk() {

    // test fqdn (including final dot)
    Domaininfo info = NameUtil.getDomain("name.example.co.uk");

    Assert.assertNotNull(info);
    Assert.assertNotNull(info.getName());
    Assert.assertEquals("example.co.uk", info.getName());
    Assert.assertTrue(info.getLabels() == 4);

  }


  @Test
  public void testIllegalQnameOk() {
    Domaininfo info = NameUtil.getDomain("-sub1.sidn.nl.");

    Assert.assertEquals("-sub1.sidn.nl", info.getName());
    Assert.assertEquals(3, info.getLabels());

    info = NameUtil.getDomain("test .sidn.nl.");

    Assert.assertEquals("test .sidn.nl", info.getName());
    Assert.assertEquals(3, info.getLabels());
  }


  @Test
  public void testShortQnameOk() {
    Domaininfo info = NameUtil.getDomain("nl.");

    Assert.assertEquals("nl", info.getName());
    Assert.assertEquals(1, info.getLabels());

    info = NameUtil.getDomain(".nl.");
    Assert.assertEquals(".nl", info.getName());
    Assert.assertEquals(1, info.getLabels());

    info = NameUtil.getDomain("co.nz.");

    Assert.assertEquals("co.nz", info.getName());
    Assert.assertEquals(2, info.getLabels());
  }

  @Test
  public void testEmptyQnameOk() {
    Domaininfo info = NameUtil.getDomain(".");

    Assert.assertEquals(".", info.getName());
    Assert.assertEquals(0, info.getLabels());

    info = NameUtil.getDomain(null);
    Assert.assertEquals(null, info.getName());
    Assert.assertEquals(0, info.getLabels());

    info = NameUtil.getDomain("");
    Assert.assertEquals(null, info.getName());
    Assert.assertEquals(0, info.getLabels());

  }


}
