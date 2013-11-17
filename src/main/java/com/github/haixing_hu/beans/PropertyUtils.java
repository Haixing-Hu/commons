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

import java.beans.BeanInfo;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.haixing_hu.lang.ArrayUtils;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * Provides utility functions for the Java bean properties.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class PropertyUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtils.class);

  private static final Map<Class<?>, PropertyDescriptor[]> DESCRIPTOR_CACHE =
    new ConcurrentHashMap<Class<?>, PropertyDescriptor[]>();

  private static final PropertyDescriptor[] EMPTY_PROPERTY_DESCRIPTORS =
    new PropertyDescriptor[0];

  /**
   * Retrieve the property descriptors for the specified class, introspecting
   * and caching them the first time a particular bean class is encountered.
   *
   * TODO: this function only get the simple properties and indexed properties,
   * the supporting for the mapped properties should be added.
   *
   * @param beanClass
   *          Bean class for which property descriptors are requested
   * @return the property descriptors
   * @exception NullPointerException
   *              if <code>beanClass</code> is null
   */
  public static PropertyDescriptor[] getPropertyDescriptors(final Class<?> beanClass) {
    requireNonNull("beanClass", beanClass);
    // Look up any cached descriptors for this bean class
    PropertyDescriptor[] descriptors = null;
    descriptors = DESCRIPTOR_CACHE.get(beanClass);
    if (descriptors != null) {
      return descriptors;
    }
    // Introspect the bean and cache the generated descriptors
    BeanInfo beanInfo = null;
    try {
      beanInfo = Introspector.getBeanInfo(beanClass);
    } catch (final IntrospectionException e) {
      LOGGER
          .error("Failed to get the bean info for {}", beanClass.getName(), e);
      DESCRIPTOR_CACHE.put(beanClass, EMPTY_PROPERTY_DESCRIPTORS);
      return EMPTY_PROPERTY_DESCRIPTORS;
    }
    descriptors = beanInfo.getPropertyDescriptors();
    if (descriptors == null) {
      DESCRIPTOR_CACHE.put(beanClass, EMPTY_PROPERTY_DESCRIPTORS);
      return EMPTY_PROPERTY_DESCRIPTORS;
    }

    DESCRIPTOR_CACHE.put(beanClass, descriptors);
    return descriptors;
  }

  /**
   * Retrieve the property descriptor for the specified property of the
   * specified bean, or return <code>null</code> if there is no such descriptor.
   *
   * This method resolves indexed and nested property references in the same
   * manner as other methods in this class, except that if the last (or only)
   * name element is indexed, the descriptor for the last resolved property
   * itself is returned.
   *
   * @param beanClass
   *          The class of a bean for which a property descriptor is requested.
   * @param name
   *          Possibly indexed and/or nested name of the property for which a
   *          property descriptor is requested.
   * @return the property descriptor
   * @exception NullPointerException
   *              if <code>bean</code> or <code>name</code> is null.
   */
  public static PropertyDescriptor getPropertyDescriptor(final Class<?> beanClass,
      final String name) {
    requireNonNull("beanClass", beanClass);
    requireNonNull("name", name);
    final PropertyDescriptor[] descriptors = getPropertyDescriptors(beanClass);
    if (descriptors != null) {
      for (final PropertyDescriptor descriptor : descriptors) {
        if (name.equals(descriptor.getName())) {
          return descriptor;
        }
      }
    }
    return null;
  }

  /**
   * Return an accessible property getter method for this property, if there is
   * one; otherwise return <code>null</code>.
   *
   * @param clazz
   *          The class of the read method will be invoked on
   * @param descriptor
   *          Property descriptor to return a getter for
   * @return The read method
   */
  public static Method getReadMethod(final Class<?> clazz, final PropertyDescriptor descriptor) {
    return MethodUtils.getAccessibleMethod(clazz, descriptor.getReadMethod());
  }

  /**
   * Return an accessible property setter method for this property, if there is
   * one; otherwise return <code>null</code>.
   *
   * @param clazz
   *          The class of the write method will be invoked on.
   * @param descriptor
   *          Property descriptor to return a setter for
   * @return The wrute method
   */
  public static Method getWriteMethod(final Class<?> clazz, final PropertyDescriptor descriptor) {
    return MethodUtils.getAccessibleMethod(clazz, descriptor.getWriteMethod());
  }

  /**
   * Return the value of the specified simple property of the specified bean,
   * with no type conversions.
   *
   * @param bean
   *          Bean whose property is to be extracted
   * @param name
   *          Name of the property to be extracted
   * @return The property value
   *
   * @exception IllegalAccessException
   *              if the caller does not have access to the property accessor
   *              method
   * @exception NullPointerException
   *              if <code>bean</code> or <code>name</code> is null
   * @exception InvocationTargetException
   *              if the property accessor method throws an exception
   * @exception NoSuchMethodException
   *              if an accessor method for this property cannot be found.
   */
  public static Object getSimpleProperty(final Object bean, final String name)
      throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    requireNonNull("bean", bean);
    requireNonNull("name", name);
    final Class<?> beanClass = bean.getClass();
    final PropertyDescriptor descriptor = getPropertyDescriptor(beanClass, name);
    if (descriptor == null) {
      throw new NoSuchMethodException("Unknown property '" + name
          + "' on class '" + beanClass.getName() + "'");
    }
    final Method readMethod = getReadMethod(bean.getClass(), descriptor);
    if (readMethod == null) {
      throw new NoSuchMethodException("Property '" + name
          + "' has no getter method in class '" + bean.getClass() + "'");
    }
    // Call the property getter and return the value
    final Object value = MethodUtils.invokeMethod(bean, readMethod,
        ArrayUtils.EMPTY_OBJECT_ARRAY);
    return value;
  }

  /**
   * Return the Java Class representing the property type of the specified
   * property, or <code>null</code> if there is no such property for the
   * specified bean. This method follows the same name resolution rules used by
   * <code>getPropertyDescriptor()</code>, so if the last element of a name
   * reference is indexed, the type of the property itself will be returned. If
   * the last (or only) element has no property with the specified name,
   * <code>null</code> is returned.
   *
   * @param beanClass
   *          the class of the bean for which a property descriptor is requested
   * @param name
   *          Possibly indexed and/or nested name of the property for which a
   *          property descriptor is requested
   * @return The property type
   *
   * @exception IllegalAccessException
   *              if the caller does not have access to the property accessor
   *              method
   * @exception NullPointerException
   *              if <code>bean</code> or <code>name</code> is null
   * @exception InvocationTargetException
   *              if the property accessor method throws an exception
   * @exception NoSuchMethodException
   *              if an accessor method for this propety cannot be found
   */
  public Class<?> getPropertyType(final Class<?> beanClass, final String name)
      throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    final PropertyDescriptor descriptor = getPropertyDescriptor(beanClass, name);
    if (descriptor == null) {
      return (null);
    } else if (descriptor instanceof IndexedPropertyDescriptor) {
      return (((IndexedPropertyDescriptor) descriptor).getIndexedPropertyType());
    } else {
      return (descriptor.getPropertyType());
    }
  }

  /**
   * Set the value of the specified simple property of the specified bean, with
   * no type conversions.
   *
   * @param bean
   *          Bean whose property is to be modified
   * @param name
   *          Name of the property to be modified
   * @param value
   *          Value to which the property should be set
   *
   * @exception IllegalAccessException
   *              if the caller does not have access to the property accessor
   *              method
   * @exception NullPointerException
   *              if <code>bean</code> or <code>name</code> is null
   * @exception InvocationTargetException
   *              if the property accessor method throws an exception
   * @exception NoSuchMethodException
   *              if an accessor method for this propety cannot be found
   */
  public void setSimpleProperty(final Object bean, final String name, final Object value)
      throws IllegalAccessException, InvocationTargetException,
      NoSuchMethodException {
    requireNonNull("bean", bean);
    requireNonNull("name", name);
    // Retrieve the property setter method for the specified property
    final Class<?> beanClass = bean.getClass();
    final PropertyDescriptor descriptor = getPropertyDescriptor(beanClass, name);
    if (descriptor == null) {
      throw new NoSuchMethodException("Unknown property '" + name
          + "' on class '" + beanClass + "'");
    }
    final Method writeMethod = getWriteMethod(beanClass, descriptor);
    if (writeMethod == null) {
      throw new NoSuchMethodException("Property '" + name
          + "' has no setter method in class '" + beanClass + "'");
    }
    // Call the property setter method
    final Object[] values = new Object[1];
    values[0] = value;
    MethodUtils.invokeMethod(bean, writeMethod, values);
  }

}
