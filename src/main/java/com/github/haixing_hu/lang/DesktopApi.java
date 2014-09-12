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
 * Contributors:
 *     MightyPork (http://www.ondrovo.com/) - Initial implementation and API.
 *     Haixing Hu (https://github.com/Haixing-Hu/) - Port the class.
 *
 ******************************************************************************/

package com.github.haixing_hu.lang;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides APIs for launching the default application to browse an URI, mail
 * to an URI, open a file, or edit a file.
 * <p>
 * Since the "java.awt.Desktop" is not fully supported in some operating
 * systems, the implementation of this class will first try to execute a system
 * dependent command to perform the action; if the command failed, it will
 * fall back to call the API provided by "java.awt.Desktop".
 *
 * @author MightyPork (http://www.ondrovo.com/)
 * @author Haixing Hu (https://github.com/Haixing-Hu/)
 * @see http://stackoverflow.com/questions/18004150/desktop-api-is-not-supported-on-the-current-platform
 */
public final class DesktopApi {

  private static final Logger LOGGER = LoggerFactory.getLogger(DesktopApi.class);

  /**
   * Launches the default browser to display a URI. If the default browser is
   * not able to handle the specified URI, the application registered for
   * handling URIs of the specified type is invoked. The application is
   * determined from the protocol and path of the URI, as defined by the URI
   * class.
   *
   * @param uri
   *          the URI to be browser.
   * @return true if success; false otherwise.
   */
  public static boolean browse(URI uri) {
    if (openSystemSpecific(uri.toString())) {
      return true;
    }
    if (browseUsingDesktop(uri)) {
      return true;
    }
    return false;
  }

  /**
   * Launches the default browser to display a URI. If the default browser is
   * not able to handle the specified URI, the application registered for
   * handling URIs of the specified type is invoked. The application is
   * determined from the protocol and path of the URI, as defined by the URI
   * class.
   *
   * @param uri
   *          the string representation of the URI to be browser.
   * @return true if success; false otherwise.
   */
  public static boolean browse(String uri) {
    try {
      return browse(new URI(uri));
    } catch (final URISyntaxException e) {
      LOGGER.error("Browsing failed because of an invalid URI: {}", uri);
      return false;
    }
  }

  /**
   * Launches the mail composing window of the user default mail client,
   * filling the message fields specified by a "mailto:" URI.
   *
   * @param uri
   *    the URI to be browser.
   * @return
   *    true if success; false otherwise.
   */
  public static boolean mail(URI uri) {
    if (openSystemSpecific(uri.toString())) {
      return true;
    }
    if (mailUsingDesktop(uri)) {
      return true;
    }
    return false;
  }

  /**
   * Launches the mail composing window of the user default mail client,
   * filling the message fields specified by a "mailto:" URI.
   *
   * @param uri
   *    the string representation of the URI to be browser.
   * @return
   *    true if success; false otherwise.
   */
  public static boolean mail(String uri) {
    if (! uri.startsWith("mailto:")) {
      uri = "mailto:" + uri;
    }
    try {
      return mail(new URI(uri));
    } catch (final URISyntaxException e) {
      LOGGER.error("Mailing failed because of an invalid URI: {}", uri);
      return false;
    }
  }

  /**
   * Launches the associated application to open the file.
   * <p>
   * If the specified file is a directory, the file manager of the current
   * platform is launched to open it.
   *
   * @param file
   *    the file to be opened.
   * @return
   *    true if success; false otherwise.
   */
  public static boolean open(File file) {
    if (openSystemSpecific(file.getPath())) {
      return true;
    }
    if (openUsingDesktop(file)) {
      return true;
    }
    return false;
  }

  /**
   * Launches the associated application to open the file.
   * <p>
   * If the specified file is a directory, the file manager of the current
   * platform is launched to open it.
   *
   * @param path
   *    the path of the file to be opened.
   * @return
   *    true if success; false otherwise.
   */
  public static boolean open(String path) {
    return open(new File(path));
  }

  /**
   * Edit a file using the operating system's default application.
   *
   * @param file
   *    the file to be edited.
   * @return
   *    true if success; false otherwise.
   */
  public static boolean edit(File file) {
    // you can try something like
    // runCommand("gimp", "%s", file.getPath())
    // based on user preferences.
    if (openSystemSpecific(file.getPath())) {
      return true;
    }
    if (editUsingDesktop(file)) {
      return true;
    }
    return false;
  }

  /**
   * Edit a file using the operating system's default application.
   *
   * @param path
   *    the path of the file to be edited.
   * @return
   *    true if success; false otherwise.
   */
  public static boolean edit(String path) {
    return edit(new File(path));
  }

  private static boolean openSystemSpecific(String what) {
    if (SystemUtils.IS_OS_LINUX) {
      if (runCommand("kde-open", "%s", what)) {
        return true;
      }
      if (runCommand("gnome-open", "%s", what)) {
        return true;
      }
      if (runCommand("xdg-open", "%s", what)) {
        return true;
      }
    } else if (SystemUtils.IS_OS_MAC) {
      if (runCommand("open", "%s", what)) {
        return true;
      }
    } else if (SystemUtils.IS_OS_WINDOWS) {
      if (runCommand("explorer", "%s", what)) {
        return true;
      }
    }
    return false;
  }

  private static boolean browseUsingDesktop(URI uri) {
    LOGGER.debug("Trying to use Desktop.getDesktop().browse() with {}", uri);
    try {
      if (! Desktop.isDesktopSupported()) {
        LOGGER.error("The java.awt.Desktop is not supported by this platform "
            + "while browsing URI: {}", uri);
        return false;
      }
      final Desktop desktop = Desktop.getDesktop();
      if (! desktop.isSupported(Desktop.Action.BROWSE)) {
        LOGGER.error("The BROWSE action is not supported by the java.awt.Desktop "
            + "while browsing URI: {}", uri);
        return false;
      }
      desktop.browse(uri);
      return true;
    } catch (final Throwable t) {
      LOGGER.error("Error using desktop to browse {}", uri, t);
      return false;
    }
  }

  private static boolean mailUsingDesktop(URI uri) {
    LOGGER.debug("Trying to use Desktop.getDesktop().mail() with {}", uri);
    try {
      if (! Desktop.isDesktopSupported()) {
        LOGGER.error("The java.awt.Desktop is not supported by this platform "
            + "while mailing URI: {}", uri);
        return false;
      }
      final Desktop desktop = Desktop.getDesktop();
      if (! desktop.isSupported(Desktop.Action.MAIL)) {
        LOGGER.error("The BROWSE action is not supported by the java.awt.Desktop "
            + "while mailing URI: {}", uri);
        return false;
      }
      desktop.mail(uri);
      return true;
    } catch (final Throwable t) {
      LOGGER.error("Error using desktop to mail {}", uri, t);
      return false;
    }
  }

  private static boolean openUsingDesktop(File file) {
    LOGGER.debug("Trying to use Desktop.getDesktop().open() with {}", file);
    try {
      if (! Desktop.isDesktopSupported()) {
        LOGGER.error("The java.awt.Desktop is not supported by this platform "
            + "while opening file: {}", file);
        return false;
      }
      final Desktop desktop = Desktop.getDesktop();
      if (! desktop.isSupported(Desktop.Action.OPEN)) {
        LOGGER.error("The OPEN action is not supported by the java.awt.Desktop "
            + "while opening file: {}", file);
        return false;
      }
      desktop.open(file);
      return true;
    } catch (final Throwable t) {
      LOGGER.error("Error using desktop open with {}", file, t);
      return false;
    }
  }

  private static boolean editUsingDesktop(File file) {
    LOGGER.debug("Trying to use Desktop.getDesktop().edit() with {}", file);
    try {
      if (! Desktop.isDesktopSupported()) {
        LOGGER.error("The java.awt.Desktop is not supported by this platform "
            + "while editing file: {}", file);
        return false;
      }
      final Desktop desktop = Desktop.getDesktop();
      if (! desktop.isSupported(Desktop.Action.EDIT)) {
        LOGGER.error("The EDIT action is not supported by the java.awt.Desktop "
            + "while editing file: {}", file);
        return false;
      }
      desktop.edit(file);
      return true;
    } catch (final Throwable t) {
      LOGGER.error("Error using desktop edit with {}", file, t);
      return false;
    }
  }

  private static boolean runCommand(String command, String args, String file) {
    LOGGER.debug("Trying to exec:\n"
        + "   cmd = {}\n   args = {}\n   %s = {}\n",
        command, args, file);
    final String[] parts = prepareCommand(command, args, file);
    try {
      final Process p = Runtime.getRuntime().exec(parts);
      if (p == null) {
        return false;
      }
      try {
        final int retval = p.exitValue();
        if (retval == 0) {
          LOGGER.error("Process ended immediately:\n"
              + "   cmd = {}\n   args = {}\n   %s = {}\n",
              command, args, file);
          return false;
        } else {
          LOGGER.error("Process crashed:\n"
              + "   cmd = {}\n   args = {}\n   %s = {}\n",
              command, args, file);
          return false;
        }
      } catch (final IllegalThreadStateException itse) {
        LOGGER.error("Process is running:\n"
              + "   cmd = {}\n   args = {}\n   %s = {}\n",
              command, args, file);
        return true;
      }
    } catch (final IOException e) {
      LOGGER.error("Error running command:\n"
              + "   cmd = {}\n   args = {}\n   %s = {}\n",
              command, args, file, e);
      return false;
    }
  }

  private static String[] prepareCommand(String command, String args,
      String file) {
    final List<String> parts = new ArrayList<String>();
    parts.add(command);
    if (args != null) {
      for (String s : args.split(" ")) {
        s = String.format(s, file); // put in the filename thing
        parts.add(s.trim());
      }
    }
    return parts.toArray(new String[parts.size()]);
  }
}