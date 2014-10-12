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
package com.github.haixing_hu.util.filter.string;

import com.github.haixing_hu.util.filter.ChainedFilter;

/**
 * A {@code ChainedStringFilter} object is a instance of {@code StringFilter}
 * consists of a list of {@code StringFilter} objects.
 *
 * A string is accepted by a ChainedStringFilter object if and only if it is accepted
 * by all filters in the chain of the ChainedStringFilter object.
 *
 * Note that the order of the filters in the chain is crucial.
 *
 * @author Haixing Hu
 */
public class ChainedStringFilter extends ChainedFilter<String> implements StringFilter {

  // empty

}
