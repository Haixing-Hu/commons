/******************************************************************************
 * Copyright (c) 2009-2011 Ascent Dimension, Inc. All rights reserved.
 ******************************************************************************/

package com.github.haixing_hu.lang;

import javax.annotation.Nullable;


/**
 * Provides utilities functions for {@link Enum}.
 *
 * @author Haixing Hu
 */
public class EnumUtils {

  /**
   * Gets the short name of an {@link Enum} value.
   * <p>
   * A short name of an {@link Enum} value comes from lowercase the enumeration
   * name of the {@link Enum} value and replacing all '_' with '-'.
   * </p>
   *
   * @param <E>
   *          The class object of a {@link Enum} class.
   * @param e
   *          A {@link Enum} value.
   * @return The short name of the {@link Enum} value.
   */
  public static <E extends Enum<E>> String getShortName(final E e) {
    return e.name().toLowerCase().replace('_', '-');
  }

  /**
   * Gets the enumeration name of a short name of an {@link Enum} value.
   * <p>
   * A short name of an {@link Enum} value comes from lowercase the enumeration
   * name of the {@link Enum} value and replacing all '_' with '-'.
   * </p>
   *
   * @param shortName
   *          The short name of an {@link Enum} value.
   * @return The enumeration name of the {@link Enum} value.
   */
  public static String getFullName(final String shortName) {
    return shortName.replace('-', '_').toUpperCase();
  }

  /**
   * Gets the {@link Enum} value accroding to its name.
   *
   * @param <E>
   *          The class object of a {@link Enum} class.
   * @param name
   *          The name of an {@link Enum} value, either the enumeration name or
   *          the short name.
   * @param isShortName
   *          whether the name is the short name.
   * @param ignoreCase
   *          whether to ignore the case while comparing strings.
   * @param enumClass
   *          The class object of the {@link Enum} class.
   * @return
   */
  public static <E extends Enum<E>> E forName(final String name,
      final boolean isShortName, final boolean ignoreCase,
      final Class<E> enumClass) {
    final String enumName = (isShortName ? getFullName(name) : name);
    final E[] enumValues = enumClass.getEnumConstants();
    for (int i = 0; i < enumValues.length; ++i) {
      if (StringUtils.equals(enumName, enumValues[i].name(), ignoreCase)) {
        return enumValues[i];
      }
    }
    return null;
  }

  public static <E extends Enum<E>> String toString(@Nullable final E value,
      final boolean useShortName) {
    if (value == null) {
      return null;
    } else if (useShortName) {
      return getShortName(value);
    } else {
      return value.name();
    }
  }
}
