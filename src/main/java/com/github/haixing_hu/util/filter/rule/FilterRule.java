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
package com.github.haixing_hu.util.filter.rule;

/**
 * The filter rule for a chained filter.
 *
 * @author Haixing Hu
 */
public abstract class FilterRule {

  /**
   * Given the current state, a filter result and a filter action returned
   * by a filter in the filter chain, get the next state of the chained filter.
   *
   * @param currentState
   *    the current state of the chained filter, which is a value defined in FilterState.
   * @param filterState
   *    a value defined in FilterState returned by the current filter in the chain.
   * @param filterAction
   *    a value defined in FilterAction returned by the current filter in the chain.
   * @return
   *    a bitwise combination of the next state and the action to be take by the
   *    chained filter.
   */
  public abstract int nextStateAction(int currentState, int filterState, int filterAction);

}
