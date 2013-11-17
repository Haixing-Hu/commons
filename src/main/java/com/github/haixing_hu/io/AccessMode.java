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

package com.github.haixing_hu.io;


/**
 * The enumeration of the file access modes.
 *
 * @author Haixing Hu
 */
public enum AccessMode {
  /**
   *  Open the device for read access.
   */
  READ_ONLY,

  /**
   *  Open the device for write access.
   */
  WRITE_ONLY,

  /**
   *  Open the device for read and write access.
   */
  READ_WRITE;

  /**
   * Tests whether this access mode is compatible with the specified open
   * option.
   *
   * @param openOption
   *          a specified open option.
   * @return true if this access mode is compatible with the specified open
   *         option; false otherwise.
   */
  public boolean compatibleWith(final OpenOption openOption) {
    switch (this) {
      case READ_ONLY:
        return (openOption == OpenOption.OPEN_EXISTING);
      case WRITE_ONLY:
      case READ_WRITE:
        return true;
      default:
        return false;
    }
  }
}
