/*
 * Copyright (c) 2014  Haixing Hu
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
 */
package com.github.haixing_hu.text.xml.jaxb;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The customized JAXB data type adaptor for the {@link Date} type.
 * <p>
 * This adaptor will print the {@link Date} type in the xsd:dateTime format
 * using the UTC time zone, i.e., the epoch ({@code new Date(0)}) will be
 * printed in the form of "1970-01-01T00:00:00Z".
 *
 * @author Haixing Hu
 */
public final class DateXmlAdaptor extends XmlAdapter<String, Date> {

  private final TimeZone utc = TimeZone.getTimeZone("UTC");

  @Override
  public Date unmarshal(String v) throws Exception {
    if (v == null) {
      return null;
    } else {
      final Calendar cal = DatatypeConverter.parseDateTime(v);
      return cal.getTime();
    }
  }

  @Override
  public String marshal(Date v) throws Exception {
    if (v == null) {
      return null;
    } else {
      final Calendar cal = new GregorianCalendar(utc);
      cal.setTime(v);
      return DatatypeConverter.printDateTime(cal);
    }
  }
}
