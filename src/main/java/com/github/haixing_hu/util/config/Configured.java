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

import javax.annotation.concurrent.NotThreadSafe;

import com.github.haixing_hu.util.config.impl.DefaultConfig;

import static com.github.haixing_hu.lang.Argument.requireNonNull;


/**
 * A base class implements the Configurable interface.
 *
 * <p>The subclass of this class could override the {@link #onConfigChanged} method,
 * in order to do some additional operations after the configuration was changed.</p>
 *
 *  <p>NOTE: This class does not implement a constructor which accept a {@link Config} as
 *  argument, since if it does, the {@link Configured#onConfigChanged()} will be called
 *  in the constructor, while the subclass may not have been constructed.</p>
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class Configured implements Configurable {

  protected Config config;

  public Configured() {
    this.config = new DefaultConfig();
  }

  @Override
  public final Config getConfig() {
    return config;
  }

  @Override
  public final void setConfig(final Config config) {
    this.config = requireNonNull("config", config);
    onConfigChanged();
  }

  /**
   * This function is called at the last of the functions
   * {@link #setConfig(Config)}, {@link #setConfig(Config, String)}, and
   * {@link #mergeConfig(Config, MergingPolicy)}.
   *
   * The subclass of {@link Configured} may override this function in
   * order to provide additional operations after the configuration is
   * changed.
   */
  protected void onConfigChanged() {
    // do nothing, but it could be override by subclasses.
  }

  @Override
  public int hashCode() {
    return config.hashCode();
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
    final Configured other = (Configured) obj;
    return config.equals(other.config);
  }


}
