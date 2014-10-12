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
package com.github.haixing_hu.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.annotation.concurrent.Immutable;

import com.github.haixing_hu.io.InputUtils;
import com.github.haixing_hu.io.OutputUtils;
import com.github.haixing_hu.io.exception.InvalidSignatureException;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * A class used to sign and verify the version signature.
 *
 * @author Haixing Hu
 */
@Immutable
public final class VersionSignature implements Serializable {

  private static final long serialVersionUID = 6492524387270673783L;

  public static final boolean DEFAULT_BACKWARD_COMPATIBLE = true;

  private final long uid;
  private final Version version;
  private final boolean backwardCompatible;

  public VersionSignature(final Version version) {
    this.uid = serialVersionUID;
    this.version = requireNonNull("version", version);
    this.backwardCompatible = DEFAULT_BACKWARD_COMPATIBLE;
  }

  public VersionSignature(final long uid, final Version version) {
    this.uid = uid;
    this.version = requireNonNull("version", version);
    this.backwardCompatible = DEFAULT_BACKWARD_COMPATIBLE;
  }

  public VersionSignature(final long uid, final Version version,
      final boolean backwardCompatible) {
    this.uid = uid;
    this.version = requireNonNull("version", version);
    this.backwardCompatible = backwardCompatible;
  }

  public long getUID() {
    return uid;
  }

  public Version getVersion() {
    return version;
  }

  public boolean isBackwardCompatible() {
    return backwardCompatible;
  }

  public Version verify(final InputStream in) throws IOException {
    final long uid = InputUtils.readLong(in);
    if (uid != this.uid) {
      throw new InvalidSignatureException();
    }
    final Version version = VersionBinarySerializer.INSTANCE.deserialize(in, false);
    if (backwardCompatible) {
      if (version.compareTo(this.version) > 0) {
        // the actual version is newer than this.version
        throw new VersionMismatchException(this.version, version);
      }
    } else {
      if (! version.equals(this.version)) {
        // the actual version does not equal this.version
        throw new VersionMismatchException(this.version, version);
      }
    }
    return version;
  }

  public void sign(final OutputStream out) throws IOException {
    OutputUtils.writeLong(out, uid);
    VersionBinarySerializer.INSTANCE.serialize(out, version);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("uid", uid)
               .appendToString("version", version.toString())
               .toString();
  }
}
