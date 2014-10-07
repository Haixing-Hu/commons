/******************************************************************************
 *
 * Copyright (c) 2014  Haixing Hu
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Haixing Hu (https://github.com/Haixing-Hu/) - Initial implementation and API.
 *
 ******************************************************************************/

package com.github.haixing_hu.text;

/**
 * A generic interface for formatters.
 *
 * @param <INPUT>
 *          the type of the formatting input.
 * @param <OUTPUT>
 *          the type of the formatting output.
 * @author Haixing Hu
 */
public interface Formatter<INPUT, OUTPUT> {

  /**
   * Formats an input to an output.
   *
   * @param input
   *          the input object to be formatted.
   * @return the output object as the formatting result.
   */
  public OUTPUT format(INPUT input);
}
