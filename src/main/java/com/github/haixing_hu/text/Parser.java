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
 * A generic interface for parsers.
 *
 * @param <INPUT>
 *          the type of the parsing input.
 * @param <OUTPUT>
 *          the type of the parsing output.
 * @author Haixing Hu
 */
public interface Parser<INPUT, OUTPUT> {

  /**
   * Parses an input to an output.
   *
   * @param input
   *          the input object to be parsed.
   * @return the output object as the parsing result.
   * @throws ParseException
   *           if any error occurs.
   */
  public OUTPUT parse(INPUT input) throws ParseException ;
}
