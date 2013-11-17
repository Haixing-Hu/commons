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

package com.github.haixing_hu.text;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.Map;

/**
 * A class provides a function to convert a Windows code page to a Java charset.
 *
 * @author Haixing Hu
 * @see http://msdn.microsoft.com/en-us/library/dd317756(VS.85).aspx
 */
public final class WinCodePage {
  private static Map<Integer, String> codePageMap = null;

  public static Charset toCharset(final int codePage) {
    final String name = codePageMap.get(new Integer(codePage));
    if (name == null) {
      return null;
    } else {
      Charset result = null;
      try {
        result = Charset.forName(name);
      } catch (final IllegalCharsetNameException e) {
        result = null; // Suppress the exception
      } catch (final UnsupportedCharsetException e) {
        result = null; // Suppress the exception
      }
      return result;
    }
  }

  static {
    codePageMap = new HashMap<Integer, String>();
    codePageMap.put(20127, "ASCII");
    codePageMap.put(28591, "ISO-8859-1");
    codePageMap.put(28592, "ISO-8859-2");
    codePageMap.put(28593, "ISO-8859-3");
    codePageMap.put(28594, "ISO-8859-4");
    codePageMap.put(28595, "ISO-8859-5");
    codePageMap.put(28596, "ISO-8859-6");
    codePageMap.put(28597, "ISO-8859-7");
    codePageMap.put(28598, "ISO-8859-8");
    codePageMap.put(28599, "ISO-8859-9");
    codePageMap.put(932, "SHIFT_JIS");
    codePageMap.put(51932, "EUC-JP");
    codePageMap.put(949, "KS_C_5601-1987");
    codePageMap.put(50225, "ISO-2022-KR");
    codePageMap.put(51949, "EUC-KR");
    codePageMap.put(50222, "ISO-2022-JP");
    codePageMap.put(709, "ASMO_449");
    codePageMap.put(38598, "ISO-8859-8-I");
    codePageMap.put(50227, "ISO-2022-CN");
    codePageMap.put(50227, "ISO-2022-CN-EXT");
    codePageMap.put(65001, "UTF-8");
    codePageMap.put(28603, "ISO-8859-13");
    codePageMap.put(28605, "ISO-8859-15");
    codePageMap.put(936, "GBK");
    codePageMap.put(54936, "GB18030");
    codePageMap.put(65000, "UTF-7");
    codePageMap.put(1201, "UTF-16BE");
    codePageMap.put(1200, "UTF-16LE");
    codePageMap.put(12001, "UTF-32BE");
    codePageMap.put(12000, "UTF-32LE");
    codePageMap.put(850, "IBM850");
    codePageMap.put(862, "IBM862");
    codePageMap.put(20838, "IBM-THAI");
    codePageMap.put(936, "GB2312");
    codePageMap.put(950, "BIG5");
    codePageMap.put(37, "IBM037");
    codePageMap.put(20273, "IBM273");
    codePageMap.put(20277, "IBM277");
    codePageMap.put(20278, "IBM278");
    codePageMap.put(20280, "IBM280");
    codePageMap.put(20284, "IBM284");
    codePageMap.put(20285, "IBM285");
    codePageMap.put(20290, "IBM290");
    codePageMap.put(20297, "IBM297");
    codePageMap.put(20420, "IBM420");
    codePageMap.put(20423, "IBM423");
    codePageMap.put(20424, "IBM424");
    codePageMap.put(437, "IBM437");
    codePageMap.put(500, "IBM500");
    codePageMap.put(852, "IBM852");
    codePageMap.put(855, "IBM855");
    codePageMap.put(857, "IBM857");
    codePageMap.put(860, "IBM860");
    codePageMap.put(861, "IBM861");
    codePageMap.put(863, "IBM863");
    codePageMap.put(864, "IBM864");
    codePageMap.put(865, "IBM865");
    codePageMap.put(869, "IBM869");
    codePageMap.put(870, "IBM870");
    codePageMap.put(20871, "IBM871");
    codePageMap.put(20880, "IBM880");
    codePageMap.put(20905, "IBM905");
    codePageMap.put(1026, "IBM1026");
    codePageMap.put(20866, "KOI8-R");
    codePageMap.put(52936, "HZ-GB-2312");
    codePageMap.put(866, "IBM866");
    codePageMap.put(21866, "KOI8-U");
    codePageMap.put(20924, "IBM00924");
    codePageMap.put(1140, "IBM01140");
    codePageMap.put(1141, "IBM01141");
    codePageMap.put(1142, "IBM01142");
    codePageMap.put(1143, "IBM01143");
    codePageMap.put(1144, "IBM01144");
    codePageMap.put(1145, "IBM01145");
    codePageMap.put(1146, "IBM01146");
    codePageMap.put(1147, "IBM01147");
    codePageMap.put(1149, "IBM01149");
    codePageMap.put(1047, "IBM1047");
    codePageMap.put(1250, "WINDOWS-1250");
    codePageMap.put(1251, "WINDOWS-1251");
    codePageMap.put(1252, "WINDOWS-1252");
    codePageMap.put(1253, "WINDOWS-1253");
    codePageMap.put(1254, "WINDOWS-1254");
    codePageMap.put(1255, "WINDOWS-1255");
    codePageMap.put(1256, "WINDOWS-1256");
    codePageMap.put(1257, "WINDOWS-1257");
    codePageMap.put(1258, "WINDOWS-1258");
  }
}
