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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.ObjectStreamConstants;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.haixing_hu.io.IoUtils;
import com.github.haixing_hu.lang.ArrayUtils;
import com.github.haixing_hu.reflect.ReflectionException;
import com.github.haixing_hu.reflect.ReflectionProvider;

/**
 * Pure Java object factory that instantiates objects using standard Java
 * reflection, however the types of objects that can be constructed are limited.
 * <p>
 * Can new instance of:
 * </p>
 * <ul>
 * <li>classes with public visibility</li>
 * <li>outer classes</li>
 * <li>static inner classes</li>
 * <li>classes with default constructors</li>
 * <li>and any class that implements java.io.Serializable</li>
 * </ul>
 * <p>
 * Can not new instance of:
 * </p>
 * <ul>
 * <li>classes without public visibility</li>
 * <li>non-static inner classes</li>
 * <li>classes without default constructors</li>
 * </ul>
 * <p>
 * Note that any code in the constructor of a class will be executed when the
 * object factory instantiates the object.
 * </p>
 *
 * @author Haixing Hu
 */
@ThreadSafe
public class PureJavaReflectionProvider implements ReflectionProvider {

  private static Logger LOGGER = LoggerFactory.getLogger(PureJavaReflectionProvider.class);

  private static final class ConstructionInfo {
    Constructor<?> constructor = null;     // the default constructor
    byte[]         data = null;            // the serialization data
  };

  /**
   * Cache of constructors for each class. Pins the classes so they can't be
   * garbage collected until ReflectionUtils can be collected.
   */
  private final Map<Class<?>, ConstructionInfo> constructionCache;

  public PureJavaReflectionProvider() {
    constructionCache = new ConcurrentHashMap<Class<?>, ConstructionInfo>();
  }

  @Override
  public Object newInstance(final Class<?> type) throws ReflectionException {
    ConstructionInfo info = constructionCache.get(type);
    if (info == null) {
      info = getConstructionInfo(type);
      constructionCache.put(type, info);
    }
    if (info.constructor != null) {
      try {
        return info.constructor.newInstance(ArrayUtils.EMPTY_OBJECT_ARRAY);
      } catch (final Exception e) {
        if (e instanceof ReflectionException) {
          throw (ReflectionException) e;
        } else {
          throw new ReflectionException(e);
        }
      }
    } else if (info.data != null) {
      ObjectInputStream in = null;
      try {
        in = new ObjectInputStream(new ByteArrayInputStream(info.data)) {
          @Override
          protected Class<?> resolveClass(final ObjectStreamClass desc)
              throws IOException, ClassNotFoundException {
              return Class.forName(desc.getName(), false, type.getClassLoader());
          }
        };
        return in.readObject();
      } catch (final ClassNotFoundException e) {
        throw new ReflectionException(e);
      } catch (final IOException e) {
        throw new ReflectionException(e);
      }
    } else {
      throw new ReflectionException("Can't construct instance of class "
          + type.getName());
    }
  }

  private static ConstructionInfo getConstructionInfo(final Class<?> type) {
    final ConstructionInfo result = new ConstructionInfo();
    result.constructor = getDefaultConstructor(type);
    if (result.constructor == null) {
      result.data = getSerializationData(type);
    }
    return result;
  }

  private static Constructor<?> getDefaultConstructor(final Class<?> type) {
    try {
      final Constructor<?>[] constructors = type.getDeclaredConstructors();
      for (final Constructor<?> constructor : constructors) {
        if (constructor.getParameterTypes().length == 0) {
          if (! constructor.isAccessible()) {
            constructor.setAccessible(true);
          }
          return constructor;
        }
      }
      LOGGER.debug("The class has no default constructor: {}", type.getName());
      return null;
    } catch (final SecurityException e) {
      LOGGER.warn("Failed to get the default constructor of class {}: {}",
          type.getName(), e.getMessage());
      return null;
    }
  }

  private static byte[] getSerializationData(final Class<?> type) {
    if (! Serializable.class.isAssignableFrom(type)) {
      LOGGER.debug("The class is not serializable: {}", type.getName());
      return null;
    }
    final ByteArrayOutputStream data = new ByteArrayOutputStream();
    final DataOutputStream out = new DataOutputStream(data);
    try {
      out.writeShort(ObjectStreamConstants.STREAM_MAGIC);
      out.writeShort(ObjectStreamConstants.STREAM_VERSION);
      out.writeByte(ObjectStreamConstants.TC_OBJECT);
      out.writeByte(ObjectStreamConstants.TC_CLASSDESC);
      out.writeUTF(type.getName());
      out.writeLong(ObjectStreamClass.lookup(type).getSerialVersionUID());
      out.writeByte(2); // classDescFlags (2 = Serializable)
      out.writeShort(0); // field count
      out.writeByte(ObjectStreamConstants.TC_ENDBLOCKDATA);
      out.writeByte(ObjectStreamConstants.TC_NULL);
      return data.toByteArray();
    } catch (final IOException e) {
      LOGGER.warn("Failed to serialize the object of class {}: {}",
          type.getName(), e.getMessage());
      return null;
    } finally {
      IoUtils.closeQuietly(out);
    }
  }

  @Override
  public boolean hasField(final Class<?> type, final String fieldName) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Field getField(final Class<?> type, final String fieldName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Class<?> getFieldType(final Class<?> type, final String fieldName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object getFieldValue(final Class<?> type, final String fieldName,
      final Object object) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setFieldValue(final Class<?> type, final String fieldName,
      final Object object, final Object fieldValue) {
    // TODO Auto-generated method stub

  }

}
