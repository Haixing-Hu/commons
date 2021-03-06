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
package com.github.haixing_hu.reflect;

import com.github.haixing_hu.reflect.testbed.Bean;

/**
 * This is a package private subclass of TestBean. All of our properties should
 * still be accessible via reflection.
 *
 * @author Haixing Hu
 */
class BeanPackageSubclass extends Bean {

  private static final long serialVersionUID = - 8752952652673035766L;

}
