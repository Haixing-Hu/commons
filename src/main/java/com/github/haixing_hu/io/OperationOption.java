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
package com.github.haixing_hu.io;

/**
 * Defines the constants of file operation options.
 *
 * @author Haixing Hu
 */
public final class OperationOption {

  /**
   * If this option is presented, the file operation will automatically create
   * the directories of the file if necessary.
   */
  public static final int MAKE_DIRS     = 0x0001;

  /**
   * If this option is presented, the file operation will overwrite the existing
   * file.
   */
  public static final int OVERWRITE     = 0x0002;

  /**
   * If this option is presented, the file operation will preserve the file last
   * modify date of the source file.
   */
  public static final int PRESERVE_DATE = 0x0004;

}
