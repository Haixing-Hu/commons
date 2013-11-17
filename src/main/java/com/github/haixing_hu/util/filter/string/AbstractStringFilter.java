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


package com.github.haixing_hu.util.filter.string;

import com.github.haixing_hu.util.filter.AbstractFilter;

/**
 * This abstract class provides an easier way to implements the StringFilter interface
 * using an anonymous class.
 *
 * @author Haixing Hu
 */
public abstract class AbstractStringFilter extends AbstractFilter<String> implements StringFilter {

  @Override
  public abstract boolean accept(String str);

}
