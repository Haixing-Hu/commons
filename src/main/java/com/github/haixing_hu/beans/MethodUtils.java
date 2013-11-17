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

package com.github.haixing_hu.beans;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * Provides utility functions for the Java bean methods.
 *
 * @author Haixing Hu
 */
public final class MethodUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(MethodUtils.class);

  /**
   * Invoke the specified method of a specified object with the specified
   * arguments.
   *
   * This function simply wrap the IllegalArgumentException to make the message
   * more instructive.
   *
   * @param obj
   *          An object whose method should be invoked.
   * @param method
   *          The method to be invoked.
   * @param args
   *          The arguments used to invoke the method.
   * @return
   * @throws IllegalAccessException
   *           if this Method object enforces Java language access control and
   *           the underlying method is inaccessible.
   * @throws IllegalArgumentException
   *           if the method is an instance method and the specified object
   *           argument is not an instance of the class or interface declaring
   *           the underlying method (or of a subclass or implementor thereof);
   *           if the number of actual and formal parameters differ; if an
   *           unwrapping conversion for primitive arguments fails; or if, after
   *           possible unwrapping, a parameter value cannot be converted to the
   *           corresponding formal parameter type by a method invocation
   *           conversion.
   * @throws InvocationTargetException
   *           if the underlying method throws an exception.
   * @throws NullPointerException
   *           if the specified object is null or the specified method instance
   *           is null.
   * @throws ExceptionInInitializerError
   *           if the initialization provoked by this method fails.
   */
  public static Object invokeMethod(final Object obj, final Method method, final Object ... args)
          throws IllegalAccessException, InvocationTargetException {
    requireNonNull("obj", obj);
    requireNonNull("method", method);
    try {
      return method.invoke(obj, args);
    } catch (final NullPointerException cause) {
      // JDK 1.3 and JDK 1.4 throw NullPointerException if an argument is
      // null for a primitive value (JDK 1.5+ throw IllegalArgumentException)
      final String message = getBadSignatureMessage(obj, method, args, cause);
      throw new IllegalArgumentException(message, cause);
    } catch (final IllegalArgumentException cause) {
      final String message = getBadSignatureMessage(obj, method, args, cause);
      throw new IllegalArgumentException(message, cause);
    }
  }

  private static String getBadSignatureMessage(final Object obj, final Method method,
      final Object[] args, final Throwable cause) {
    final StringBuilder builder = new StringBuilder();
    String actual = "{}";
    if (args != null) {
      for (int i = 0; i < args.length; ++i) {
        if (i > 0) {
          builder.append(", ");
        }
        if (args[i] == null) {
          builder.append("<null>");
        } else {
          builder.append(args[i].getClass().getName());
        }
      }
      actual = builder.toString();
    }
    String expected = "{}";
    final Class<?>[] parameterTypes = method.getParameterTypes();
    if (parameterTypes != null) {
      builder.setLength(0);
      for (int i = 0; i < parameterTypes.length; ++i) {
        if (i > 0) {
          builder.append(", ");
        }
        builder.append(parameterTypes[i].getName());
      }
      expected = builder.toString();
    }
    builder.setLength(0);
    return builder.append("Can not invoke ")
                  .append(method.getDeclaringClass().getName())
                  .append('.')
                  .append(method.getName())
                  .append(" on the class ")
                  .append(obj.getClass())
                  .append(": ")
                  .append(cause.getMessage())
                  .append(" - had objects of type '")
                  .append(actual)
                  .append("' but expected signature '")
                  .append(expected)
                  .append("'.")
                  .toString();
  }

  /**
   * Return an accessible method (that is, one that can be invoked via
   * reflection) that implements the specified Method. If no such method can be
   * found, return <code>null</code>.
   *
   * @param clazz
   *          The class of the object
   * @param method
   *          The method that we wish to call
   * @return The accessible method
   */
  public static Method getAccessibleMethod(Class<?> clazz, Method method) {
    if (method == null) {
      return null;
    }
    // If the requested method is not public we cannot call it
    if (! Modifier.isPublic(method.getModifiers())) {
      return null;
    }
    final Class<?> declaringClass = method.getDeclaringClass();
    boolean sameClass = true;
    if (clazz == null) {
      clazz = declaringClass;
    } else {
      sameClass = clazz.equals(declaringClass);
      if (! declaringClass.isAssignableFrom(clazz)) {
        throw new IllegalArgumentException(clazz.getName()
            + " is not assignable from " + declaringClass.getName());
      }
    }
    // If the class is public, we are done
    if (Modifier.isPublic(clazz.getModifiers())) {
      if ((! sameClass) && (! Modifier.isPublic(declaringClass.getModifiers()))) {
        setMethodAccessible(method); // Default access superclass workaround
      }
      return method;
    }
    final String methodName = method.getName();
    final Class<?>[] parameterTypes = method.getParameterTypes();
    // Check the implemented interfaces and subinterfaces
    method = getAccessibleMethodFromInterfaceNest(clazz, methodName,
        parameterTypes);
    // Check the superclass chain
    if (method == null) {
      method = getAccessibleMethodFromSuperclass(clazz, methodName, parameterTypes);
    }
    return method;
  }

  /**
   * Try to make the method accessible
   *
   * @param method
   *          The source arguments
   */
  private static void setMethodAccessible(final Method method) {
    try {
      // XXX Default access superclass workaround
      //
      // When a public class has a default access superclass
      // with public methods, these methods are accessible.
      // Calling them from compiled code works fine.
      //
      // Unfortunately, using reflection to invoke these methods
      // seems to (wrongly) to prevent access even when the method
      // modifier is public.
      //
      // The following workaround solves the problem but will only
      // work from sufficiently privileges code.
      //
      // Better workarounds would be gratefully accepted.
      //
      if (! method.isAccessible()) {
        method.setAccessible(true);
      }
    } catch (final SecurityException e) {
      LOGGER.warn("Cannot setAccessible on method: {}", e);
    }
  }

  /**
   * Return an accessible method (that is, one that can be invoked via
   * reflection) by scanning through the super classes. If no such method can be
   * found, return <code>null</code>.
   *
   * @param clazz
   *          Class to be checked
   * @param methodName
   *          Method name of the method we wish to call
   * @param parameterTypes
   *          The parameter type signatures
   */
  private static Method getAccessibleMethodFromSuperclass(final Class<?> clazz,
      final String methodName, final Class<?>[] parameterTypes) {
    Class<?> parentClazz = clazz.getSuperclass();
    while (parentClazz != null) {
      if (Modifier.isPublic(parentClazz.getModifiers())) {
        try {
          return parentClazz.getMethod(methodName, parameterTypes);
        } catch (final NoSuchMethodException e) {
          return null;
        }
      }
      parentClazz = parentClazz.getSuperclass();
    }
    return null;
  }

  /**
   * Return an accessible method (that is, one that can be invoked via
   * reflection) that implements the specified method, by scanning through all
   * implemented interfaces and subinterfaces. If no such method can be found,
   * return <code>null</code>.
   *
   * There isn't any good reason why this method must be private. It is because
   * there doesn't seem any reason why other classes should call this rather
   * than the higher level methods.
   *
   * @param clazz
   *          Parent class for the interfaces to be checked
   * @param methodName
   *          Method name of the method we wish to call
   * @param parameterTypes
   *          The parameter type signatures
   */
  private static Method getAccessibleMethodFromInterfaceNest(Class<?> clazz,
      final String methodName, final Class<?>[] parameterTypes) {
    // Search up the superclass chain
    while (clazz != null) {
      // Check the implemented interfaces of the parent class
      final Class<?>[] interfaces = clazz.getInterfaces();
      for (int i = 0; i < interfaces.length; ++i) {
        // Is this interface public?
        if (! Modifier.isPublic(interfaces[i].getModifiers())) {
          continue;
        }
        // Does the method exist on this interface?
        Method method = null;
        try {
          method = interfaces[i].getDeclaredMethod(methodName, parameterTypes);
        } catch (final NoSuchMethodException e) {
          // Swallow, if no method is found after the loop then this method
          // returns null.
        }
        if (method != null) {
          return method;
        }
        // Recursively check our parent interfaces
        method = getAccessibleMethodFromInterfaceNest(interfaces[i],
            methodName, parameterTypes);
        if (method != null) {
          return method;
        }
      }
      clazz = clazz.getSuperclass();
    }
    // We did not find anything
    return null;
  }
}
