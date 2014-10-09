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

package com.github.haixing_hu.util.config;

import javax.annotation.Nullable;

import com.github.haixing_hu.util.value.NamedMultiValues;


/**
 * A {@link Property} object represents a property, which has the following
 * attributes:
 * <ul>
 * <li>a name</li>
 * <li>a type</li>
 * <li>a optional description</li>
 * <li>a boolean mark to indicate whether this property is final</li>
 * <li>one or more values of the specified type</li>
 * </ul>
 * Currently the {@link Property} objects support the following types:
 * <ul>
 * <li>{@code boolean}</li>
 * <li>{@code char}</li>
 * <li>{@code byte}</li>
 * <li>{@code short}</li>
 * <li>{@code int}</li>
 * <li>{@code long}</li>
 * <li>{@code float}</li>
 * <li>{@code double}</li>
 * <li>{@code String}</li>
 * <li>{@code java.util.Date}</li>
 * <li>{@code java.math.BigDecimal}</li>
 * <li>{@code java.math.BigInteger}</li>
 * <li>{@code byte[]}</li>
 * <li>{@code Class}</li>
 * </ul>
 *
 * @author Haixing Hu
 */
public interface Property extends NamedMultiValues {

  /**
   * Gets the description of this property.
   *
   * @return the description of this property, which could be null.
   */
  public @Nullable
  String getDescription();

  /**
   * Sets the description of this property.
   *
   * @param description
   *          the description of this property, which could be null.
   */
  public void setDescription(@Nullable String description);

  /**
   * Tests whether this property if final.
   * <p>
   * A final property can not be override by merging with the property having
   * the same name.
   * </p>
   *
   * @return true if this property if final; false otherwise.
   */
  public boolean isFinal();

  /**
   * Sets whether this property is final.
   * <p>
   * A final property can not be override by merging with the property having
   * the same name.
   * </p>
   *
   * @param isFinal
   *          if it is true, this property will be set to be final; otherwise,
   *          it will be set to be non-final.
   */
  public void setFinal(boolean isFinal);

  /**
   * Assigns another {@link Property} object to this object.
   *
   * @param other
   *          the other {@link Property} object to be assigned to this object.
   */
  public void assign(Property other);
}
