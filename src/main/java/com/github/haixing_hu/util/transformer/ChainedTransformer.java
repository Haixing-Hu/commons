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

package com.github.haixing_hu.util.transformer;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * A {@code ChainedTransformer} object is a instance of
 * {@code Transformer} consists of a list of {@code Transformer}
 * objects.
 *
 * A ChainedTransformer object will transform the input object using all the
 * transformers in its chain one by one, until the object become
 * {@code null}.
 *
 * Note that the order of the transformers in the chain is crucial.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class ChainedTransformer<T> extends AbstractTransformer<T> {

  protected List<Transformer<T>> transformerChain;

  public ChainedTransformer() {
    transformerChain = null;
  }

  public List<Transformer<T>> getTransformerChain() {
    return transformerChain;
  }

  public void setTransformerChain(List<Transformer<T>> transformerChain) {
    this.transformerChain = transformerChain;
  }

  public void addTransformer(Transformer<T> transformer) {
    if (transformerChain == null) {
      transformerChain = new LinkedList<Transformer<T>>();
    }
    transformerChain.add(transformer);
  }

  public int size() {
    return (transformerChain == null ? 0 : transformerChain.size());
  }

  public boolean isEmpty() {
    return (transformerChain == null) || transformerChain.isEmpty();
  }

  public void clear() {
    if (transformerChain != null) {
      transformerChain.clear();
    }
  }

  @Override
  public T transform(T t) {
    if (t == null) {
      return null;
    }
    if (transformerChain != null) {
      for (Transformer<T> transformer : transformerChain) {
        t = transformer.transform(t);
        if (t == null) {
          break;
        }
      }
    }
    return t;
  }

}
