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

package com.github.haixing_hu.util.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * A {@link ChainedFilter} is an implementation of {@link Filter} consists of a
 * list of {@link Filter} objects.
 *
 * An object is accepted by a {@link ChainedFilter} object if and only if it is
 * accepted by all filters in the internal chain of that {@link ChainedFilter}
 * object.
 *
 * Note that the order of the filters in the chain is crucial.
 *
 * @author Haixing Hu
 */
public class ChainedFilter<T> extends AbstractFilter<T> {

  protected List<Filter<T>> filterChain;

  public ChainedFilter() {
    filterChain = new ArrayList<Filter<T>>();
  }

  public List<Filter<T>> getFilterChain() {
    return filterChain;
  }

  public void setFilterChain(final List<Filter<T>> filterChain) {
    this.filterChain =  requireNonNull("filterChain", filterChain);
  }

  public int size() {
    return filterChain.size();
  }

  public boolean isEmpty() {
    return filterChain.isEmpty();
  }

  public void clear() {
    filterChain.clear();
  }

  public void addFilter(final Filter<T> filter) {
    filterChain.add(requireNonNull("filter", filter));
  }

  public void addFilters(final Collection<Filter<T>> filters) {
    requireNonNull("filters", filters);
    for (final Filter<T> filter : filters) {
      requireNonNull("filter", filter);
      filterChain.add(filter);
    }
  }

  public void addFilters(final Filter<T>... filters) {
    requireNonNull("filters", filters);
    for (final Filter<T> filter : filters) {
      requireNonNull("filter", filter);
      filterChain.add(filter);
    }
  }

  @Override
  public boolean accept(final T t) {
    for (final Filter<T> filter : filterChain) {
      if ((filter != null) && (! filter.accept(t))) {
        return false;
      }
    }
    return true;
  }

}
