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

import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.concurrent.GuardedBy;

import com.github.haixing_hu.lang.CharUtils;
import com.github.haixing_hu.lang.Cloneable;
import com.github.haixing_hu.lang.Equality;
import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

import static com.github.haixing_hu.lang.Argument.*;

/**
 * A {@link NumberFormatSymbols} object is used to store the symbols for
 * formatting and parsing numbers.
 *
 * @author Haixing Hu
 */
public final class NumberFormatSymbols implements Cloneable<NumberFormatSymbols> {

  public static final char[] DEFAULT_LOWERCASE_DIGITS     =  CharUtils.LOWERCASE_DIGITS;

  public static final char[] DEFAULT_UPPERCASE_DIGITS     =  CharUtils.UPPERCASE_DIGITS;

  public static final String[] DEFAULT_LOWERCASE_RADIX_PREFIXES = {
    /* 0 */  null,
    /* 1 */  null,
    /* 2 */  "0b",
    /* 3 */  null,
    /* 4 */  null,
    /* 5 */  null,
    /* 6 */  null,
    /* 7 */  null,
    /* 8 */  "0",
    /* 9 */  null,
    /* 10 */ null,
    /* 11 */ null,
    /* 12 */ null,
    /* 13 */ null,
    /* 14 */ null,
    /* 15 */ null,
    /* 16 */ "0x",
  };

  public static final String[] DEFAULT_UPPERCASE_RADIX_PREFIXES = {
    /* 0 */  null,
    /* 1 */  null,
    /* 2 */  "0B",
    /* 3 */  null,
    /* 4 */  null,
    /* 5 */  null,
    /* 6 */  null,
    /* 7 */  null,
    /* 8 */  "0",
    /* 9 */  null,
    /* 10 */ null,
    /* 11 */ null,
    /* 12 */ null,
    /* 13 */ null,
    /* 14 */ null,
    /* 15 */ null,
    /* 16 */ "0X",
  };

  public static final char DEFAULT_POSITIVE_SIGN = '+';

  public static final char DEFAULT_NEGATIVE_SIGN = '-';

  public static final char DEFAULT_RADIX_SEPARATOR = '.';

  public static final char DEFAULT_GROUPING_SEPARATOR = ',';

  public static final String DEFAULT_EXPONENT_SEPARATOR = "E";

  public static final char DEFAULT_PERCENT_SYMBOL = '%';

  public static final char DEFAULT_PERMILLE_SYMBOL = '\u2030';

  public static final String DEFAULT_CURRENCY_SYMBOL = "$";

  public static final char DEFAULT_MONETARY_RADIX_SEPARATOR = '.';

  public static final char DEFAULT_MONETARY_GROUPING_SEPARATOR = ',';

  public static final String DEFAULT_INFINITY_SYMBOL = "\u221E";

  public static final String DEFAULT_NAN_SYMBOL = "NaN.";

  @GuardedBy("NumberFormatSymbols.class")
  private static final Map<Locale, DecimalFormatSymbols> symbolsCache = new HashMap<Locale, DecimalFormatSymbols>();

  private static synchronized DecimalFormatSymbols getCachedSymbols(final Locale locale) {
    requireNonNull("locale", locale);
    DecimalFormatSymbols symbols = symbolsCache.get(locale);
    if (symbols == null) {
      symbols = DecimalFormatSymbols.getInstance(locale);
      symbolsCache.put(locale, symbols);
    }
    return symbols;
  }

  private char[] lowercaseDigits;
  private char[] uppercaseDigits;
  private String[] lowercaseRadixPrefixes;
  private String[] uppercaseRadixPrefixes;
  private char positiveSign;
  private char negativeSign;
  private char radixSeparator;
  private char groupingSeparator;
  private String exponentSeparator;
  private String infinitySymbol;
  private String nanSymbol;
  private char percentSymbol;
  private char permilleSymbol;
  private String currencySymbol;
  private char monetaryRadixSeparator;
  private char monetaryGroupingSeparator;

  public NumberFormatSymbols() {
    reset();
  }

  public NumberFormatSymbols(final Locale locale) {
    reset(locale);
  }

  public void reset() {
    lowercaseDigits = DEFAULT_LOWERCASE_DIGITS;
    uppercaseDigits = DEFAULT_UPPERCASE_DIGITS;
    lowercaseRadixPrefixes = DEFAULT_LOWERCASE_RADIX_PREFIXES;
    uppercaseRadixPrefixes = DEFAULT_UPPERCASE_RADIX_PREFIXES;
    positiveSign = DEFAULT_POSITIVE_SIGN;
    negativeSign = DEFAULT_NEGATIVE_SIGN;
    radixSeparator = DEFAULT_RADIX_SEPARATOR;
    groupingSeparator = DEFAULT_GROUPING_SEPARATOR;
    exponentSeparator = DEFAULT_EXPONENT_SEPARATOR;
    infinitySymbol = DEFAULT_INFINITY_SYMBOL;
    nanSymbol = DEFAULT_NAN_SYMBOL;
    percentSymbol = DEFAULT_PERCENT_SYMBOL;
    permilleSymbol = DEFAULT_PERMILLE_SYMBOL;
    currencySymbol = DEFAULT_CURRENCY_SYMBOL;
    monetaryRadixSeparator = DEFAULT_MONETARY_RADIX_SEPARATOR;
    monetaryGroupingSeparator = DEFAULT_MONETARY_GROUPING_SEPARATOR;
  }

  public void reset(final Locale locale) {
    final DecimalFormatSymbols symbols = getCachedSymbols(locale);
    if (symbols == null) {
      reset();
    } else { // formatSymbols != null
      lowercaseDigits = DEFAULT_LOWERCASE_DIGITS;
      uppercaseDigits = DEFAULT_UPPERCASE_DIGITS;
      lowercaseRadixPrefixes = DEFAULT_LOWERCASE_RADIX_PREFIXES;
      uppercaseRadixPrefixes = DEFAULT_UPPERCASE_RADIX_PREFIXES;
      // note that no positive sign is provided by the DecimalFormatSymbols in
      // JDK 1.6
      positiveSign = DEFAULT_POSITIVE_SIGN;
      negativeSign = symbols.getMinusSign();
      radixSeparator = symbols.getDecimalSeparator();
      groupingSeparator = symbols.getGroupingSeparator();
      exponentSeparator = symbols.getExponentSeparator();
      infinitySymbol = symbols.getInfinity();
      nanSymbol = symbols.getNaN();
      percentSymbol = symbols.getPercent();
      permilleSymbol = symbols.getPerMill();
      currencySymbol = symbols.getCurrencySymbol();
      monetaryRadixSeparator = symbols.getMonetaryDecimalSeparator();
      // note that no monetary grouping separator is provided by the
      // DecimalFormatSymbols in JDK 1.6
      monetaryGroupingSeparator = symbols.getGroupingSeparator();
    }
  }

  public char[] getLowercaseDigits() {
    return lowercaseDigits;
  }

  public void setLowercaseDigits(final char[] lowercaseDigits) {
    this.lowercaseDigits = requireLengthAtLeast("lowercaseDigits", lowercaseDigits, 16);
  }

  public char[] getUppercaseDigits() {
    return uppercaseDigits;
  }

  public void setUppercaseDigits(final char[] uppercaseDigits) {
    this.uppercaseDigits = requireLengthAtLeast("uppercaseDigits", uppercaseDigits, 16);
  }

  public char[] getDigits(final boolean uppercase) {
    return (uppercase ? uppercaseDigits : lowercaseDigits);
  }

  public String[] getLowercaseRadixPrefixes() {
    return lowercaseRadixPrefixes;
  }

  public void setLowercaseRadixPrefixes(final String[] lowercaseRadixPrefixes) {
    this.lowercaseRadixPrefixes = requireLengthAtLeast("lowercaseRadixPrefixes",
        lowercaseRadixPrefixes, 16);
  }

  public String[] getUppercaseRadixPrefixes() {
    return uppercaseRadixPrefixes;
  }

  public void setUppercaseRadixPrefixes(final String[] uppercaseRadixPrefixes) {
    this.uppercaseRadixPrefixes = requireLengthAtLeast("uppercaseRadixPrefixes",
        uppercaseRadixPrefixes, 16);
  }

  public String[] getRadixPrefixes(final boolean uppercase) {
    return (uppercase ? uppercaseRadixPrefixes : lowercaseRadixPrefixes);
  }

  public char getPositiveSign() {
    return positiveSign;
  }

  public void setPositiveSign(final char positiveSign) {
    this.positiveSign = positiveSign;
  }

  public char getNegativeSign() {
    return negativeSign;
  }

  public void setNegativeSign(final char negativeSign) {
    this.negativeSign = negativeSign;
  }

  public char getRadixSeparator() {
    return radixSeparator;
  }

  public void setRadixSeparator(final char radixSeparator) {
    this.radixSeparator = radixSeparator;
  }

  public char getGroupingSeparator() {
    return groupingSeparator;
  }

  public void setGroupingSeparator(final char groupingSeparator) {
    this.groupingSeparator = groupingSeparator;
  }

  public String getExponentSeparator() {
    return exponentSeparator;
  }

  public void setExponentSeparator(final String exponentSeparator) {
    this.exponentSeparator = requireNonNull("exponentSeparator",
        exponentSeparator);
  }

  public char getPercentSymbol() {
    return percentSymbol;
  }

  public void setPercentSymbol(final char percentSymbol) {
    this.percentSymbol = percentSymbol;
  }

  public char getPermilleSymbol() {
    return permilleSymbol;
  }

  public void setPermilleSymbol(final char permilleSymbol) {
    this.permilleSymbol = permilleSymbol;
  }

  public String getInfinitySymbol() {
    return infinitySymbol;
  }

  public void setInfinitySymbol(final String infinitySymbol) {
    this.infinitySymbol = requireNonNull("infinitySymbol", infinitySymbol);
  }

  public String getNanSymbol() {
    return nanSymbol;
  }

  public void setNanSymbol(final String nanSymbol) {
    this.nanSymbol = requireNonNull("nanSymbol", nanSymbol);
  }

  public String getCurrencySymbol() {
    return currencySymbol;
  }

  public void setCurrencySymbol(final String currencySymbol) {
    this.currencySymbol = requireNonNull("currencySymbol", currencySymbol);
  }

  public char getMonetaryRadixSeparator() {
    return monetaryRadixSeparator;
  }

  public void setMonetaryRadixSeparator(final char monetaryRadixSeparator) {
    this.monetaryRadixSeparator = monetaryRadixSeparator;
  }

  public char getMonetaryGroupingSeparator() {
    return monetaryGroupingSeparator;
  }

  public void setMonetaryGroupingSeparator(final char monetaryGroupingSeparator) {
    this.monetaryGroupingSeparator = monetaryGroupingSeparator;
  }

  public void assign(final NumberFormatSymbols that) {
    if (this == that) {
      return;
    }
    this.lowercaseDigits = that.lowercaseDigits;
    this.uppercaseDigits = that.uppercaseDigits;
    this.lowercaseRadixPrefixes = that.lowercaseRadixPrefixes;
    this.uppercaseRadixPrefixes = that.uppercaseRadixPrefixes;
    this.positiveSign = that.positiveSign;
    this.negativeSign = that.negativeSign;
    this.radixSeparator = that.radixSeparator;
    this.groupingSeparator = that.groupingSeparator;
    this.exponentSeparator = that.exponentSeparator;
    this.infinitySymbol = that.infinitySymbol;
    this.nanSymbol = that.nanSymbol;
    this.percentSymbol = that.percentSymbol;
    this.permilleSymbol = that.permilleSymbol;
    this.currencySymbol = that.currencySymbol;
    this.monetaryRadixSeparator = that.monetaryRadixSeparator;
    this.monetaryGroupingSeparator = that.monetaryGroupingSeparator;
  }

  @Override
  public NumberFormatSymbols clone() {
    final NumberFormatSymbols result = new NumberFormatSymbols();
    result.assign(this);
    return result;
  }

  @Override
  public int hashCode() {
    final int multiplier = 3;
    int code = 17;
    code = Hash.combine(code, multiplier, lowercaseDigits);
    code = Hash.combine(code, multiplier, uppercaseDigits);
    code = Hash.combine(code, multiplier, lowercaseRadixPrefixes);
    code = Hash.combine(code, multiplier, uppercaseRadixPrefixes);
    code = Hash.combine(code, multiplier, positiveSign);
    code = Hash.combine(code, multiplier, negativeSign);
    code = Hash.combine(code, multiplier, radixSeparator);
    code = Hash.combine(code, multiplier, groupingSeparator);
    code = Hash.combine(code, multiplier, exponentSeparator);
    code = Hash.combine(code, multiplier, infinitySymbol);
    code = Hash.combine(code, multiplier, nanSymbol);
    code = Hash.combine(code, multiplier, percentSymbol);
    code = Hash.combine(code, multiplier, permilleSymbol);
    code = Hash.combine(code, multiplier, currencySymbol);
    code = Hash.combine(code, multiplier, monetaryRadixSeparator);
    code = Hash.combine(code, multiplier, monetaryGroupingSeparator);
    return code;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final NumberFormatSymbols other = (NumberFormatSymbols) obj;
    return (positiveSign == other.positiveSign)
        && (negativeSign == other.negativeSign)
        && (radixSeparator == other.radixSeparator)
        && (groupingSeparator == other.groupingSeparator)
        && (percentSymbol == other.percentSymbol)
        && (permilleSymbol == other.permilleSymbol)
        && (monetaryRadixSeparator == other.monetaryRadixSeparator)
        && (monetaryGroupingSeparator == other.monetaryGroupingSeparator)
        && Equality.equals(lowercaseDigits, other.lowercaseDigits)
        && Equality.equals(uppercaseDigits, other.uppercaseDigits)
        && Equality.equals(lowercaseRadixPrefixes, other.lowercaseRadixPrefixes)
        && Equality.equals(uppercaseRadixPrefixes, other.uppercaseRadixPrefixes)
        && Equality.equals(exponentSeparator, other.exponentSeparator)
        && Equality.equals(infinitySymbol, other.infinitySymbol)
        && Equality.equals(nanSymbol, other.nanSymbol)
        && Equality.equals(currencySymbol, other.currencySymbol);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("lowercaseDigits", lowercaseDigits)
               .append("uppercaseDigits", uppercaseDigits)
               .append("lowercaseRadixPrefixes", lowercaseRadixPrefixes)
               .append("uppercaseRadixPrefixes", uppercaseRadixPrefixes)
               .append("positiveSign", positiveSign)
               .append("negativeSign", negativeSign)
               .append("radixSeparator", radixSeparator)
               .append("groupingSeparator", groupingSeparator)
               .append("exponentSeparator", exponentSeparator)
               .append("infinitySymbol", infinitySymbol)
               .append("nanSymbol", nanSymbol)
               .append("percentSymbol", percentSymbol)
               .append("permilleSymbol", permilleSymbol)
               .append("currencySymbol", currencySymbol)
               .append("monetaryRadixSeparator", monetaryRadixSeparator)
               .append("monetaryGroupingSeparator", monetaryGroupingSeparator)
               .toString();
  }
}
