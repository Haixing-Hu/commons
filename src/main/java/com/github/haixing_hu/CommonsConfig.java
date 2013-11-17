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

package com.github.haixing_hu;

import com.github.haixing_hu.util.config.Config;
import com.github.haixing_hu.util.config.ConfigUtils;

/**
 * Provides functions to get the configuration of the commons module and defines
 * the names and default values of properties.
 *
 * @author Haixing Hu
 */
public final class CommonsConfig {

  /**
   * The system property name for the XML resource of the configuration of commons module.
   */
  public static final String PROPERTY_RESOURCE =
    "com.github.haixing_hu.CommonsConfig";

  /**
   * The default name of XML resource of the configuration of commons module.
   */
  public static final String DEFAULT_RESOURCE = "ascent-commons.xml";

  /**
   * The static {@link Config} object.
   */
  private static volatile Config config = null;

  /**
   * Gets the configuration of the commons module.
   * <p>
   * The function will first try to search in the system's properties to find
   * the XML resource name of the configuration, if no such system properties
   * exists, it will use the default XML resource. Then it will try to load
   * the configuration from the XML file, and return the configuration if
   * success, or return an empty configuration if failed.
   * </p>
   *
   * @return the configuration of the commons module, or an empty configuration
   *         if failed.
   */
  public static Config get() {
    // use the double checked locking
    if (config == null) {
      synchronized (CommonsConfig.class) {
        if (config == null) {
          config = ConfigUtils.loadXmlConfig(PROPERTY_RESOURCE,
              DEFAULT_RESOURCE, CommonsConfig.class);
        }
      }
    }
    return config;
  }
}
