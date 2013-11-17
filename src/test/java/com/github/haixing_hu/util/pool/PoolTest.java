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

package com.github.haixing_hu.util.pool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import junit.framework.TestCase;

import com.github.haixing_hu.util.pool.Pool;
import com.github.haixing_hu.util.pool.PoolClosedException;
import com.github.haixing_hu.util.pool.PoolException;
import com.github.haixing_hu.util.pool.PoolableFactory;
import com.github.haixing_hu.util.pool.impl.BasicPool;
import com.github.haixing_hu.util.pool.impl.ValidateAction;
import com.github.haixing_hu.util.pool.impl.ValidatePolicy;

/**
 * Abstract {@link TestCase} for {@link Pool<Integer>} implementations.
 *
 * @author Haixing Hu
 */
public abstract class PoolTest extends TestCase {

  public PoolTest(final String testName) {
    super(testName);
  }

  /**
   * Create an <code>Pool<Integer></code> with the specified factory. The pool
   * should be in a default configuration and conform to the expected behaviors
   * described in {@link Pool<Integer>}. Generally speaking there should be no
   * limits on the various object counts.
   *
   * @throws UnsupportedOperationException
   *           if the pool being tested does not follow pool contracts.
   */
  protected abstract <T> Pool<T> makeEmptyPool(PoolableFactory<T> factory)
      throws UnsupportedOperationException;

  public void testClosedPoolBehavior() throws PoolException {
    final Pool<Integer> pool;
    try {
      pool = makeEmptyPool(new MethodCallPoolableFactory());
    } catch (final UnsupportedOperationException uoe) {
      return; // test not supported
    }
    final Integer o1 = pool.borrow();
    final Integer o2 = pool.borrow();

    pool.close();
    try {
      pool.add();
      fail("A closed pool must throw an PoolClosedException when add is called.");
    } catch (final PoolClosedException ise) {
      // expected
    }

    try {
      pool.borrow();
      fail("A closed pool must throw an IllegalStateException when borrow is called.");
    } catch (final PoolClosedException ise) {
      // expected
    }

    // The following should not throw exceptions just because the pool is
    // closed.
    assertEquals("A closed pool shouldn't have any idle objects.", 0,
        pool.getIdleCount());

    assertEquals("A closed pool should still keep count of active objects.", 2,
        pool.getActiveCount());

    pool.restore(o1);
    assertEquals(
        "restore should not add items back into the idle object pool for a closed pool.",
        0, pool.getIdleCount());

    assertEquals("A closed pool should still keep count of active objects.", 1,
        pool.getActiveCount());

    pool.invalidate(o2);
    assertEquals(
        "invalidate must not add items back into the idle object pool.", 0,
        pool.getIdleCount());

    assertEquals("A closed pool should still keep count of active objects.", 0,
        pool.getActiveCount());

    pool.clear();
    pool.close();
  }

  private final Integer ZERO = new Integer(0);
  private final Integer ONE = new Integer(1);

  public void testPOFAddUsage() throws PoolException {
    final MethodCallPoolableFactory factory = new MethodCallPoolableFactory();
    final Pool<Integer> pool;
    try {
      pool = makeEmptyPool(factory);
    } catch (final UnsupportedOperationException uoe) {
      return; // test not supported
    }
    final List<MethodCall> expectedMethods = new ArrayList<MethodCall>();

    assertEquals(0, pool.getActiveCount());
    assertEquals(0, pool.getIdleCount());
    // add should make a new object, passivate it and put it in the pool
    pool.add();
    assertEquals(0, pool.getActiveCount());
    assertEquals(1, pool.getIdleCount());
    expectedMethods.add(new MethodCall("create").returned(ZERO));
    // StackPool<Integer>, SoftReferencePool<Integer> also validate on add
    // FIXME
    // if ((pool instanceof StackPool<Integer>)
    // || (pool instanceof SoftReferencePool<Integer>)) {
    // expectedMethods.add(new MethodCall("validate",
    // ZERO).returned(Boolean.TRUE));
    // }
    expectedMethods.add(new MethodCall("passivate", ZERO));
    assertEquals(expectedMethods, factory.getMethodCalls());

    // // Test exception handling of add
    reset(pool, factory, expectedMethods);

    // create Exceptions should be propagated to client code from add
    factory.setCreateFail(true);
    try {
      pool.add();
      fail("Expected add to propagate create exception.");
    } catch (final PrivateException pe) {
      // expected
    }
    expectedMethods.add(new MethodCall("create"));
    assertEquals(expectedMethods, factory.getMethodCalls());

    clear(factory, expectedMethods);

    // passivate Exceptions should be propagated to client code from
    // add
    factory.setCreateFail(false);
    factory.setPassivateFail(true);
    try {
      pool.add();
      fail("Expected add to propagate passivate exception.");
    } catch (final PrivateException pe) {
      // expected
    }
    expectedMethods.add(new MethodCall("create").returned(ONE));
    // StackPool<Integer>, SofReferencePool<Integer> also validate on add
    // FIXME
    // if ((pool instanceof StackPool<Integer>)
    // || (pool instanceof SoftReferencePool<Integer>)) {
    // expectedMethods.add(new MethodCall("validate",
    // ONE).returned(Boolean.TRUE));
    // }
    expectedMethods.add(new MethodCall("passivate", ONE));
    assertEquals(expectedMethods, factory.getMethodCalls());
  }

  public void testPOFBorrowUsages() throws PoolException {
    final MethodCallPoolableFactory factory = new MethodCallPoolableFactory();
    final Pool<Integer> pool;
    try {
      pool = makeEmptyPool(factory);
    } catch (final UnsupportedOperationException uoe) {
      return; // test not supported
    }
    if (pool instanceof BasicPool) {
      final ValidatePolicy<Integer> policy = ((BasicPool<Integer>) pool).getValidatePolicy();
      policy.setOnBorrowAction(ValidateAction.LOG_ON_FAILED);
    }
    final List<MethodCall> expectedMethods = new ArrayList<MethodCall>();
    Integer obj;

    // / Test correct behavior code paths

    // existing idle object should be activated and validated
    pool.add();
    clear(factory, expectedMethods);
    obj = pool.borrow();
    expectedMethods.add(new MethodCall("activate", ZERO));
    expectedMethods.add(new MethodCall("validate", ZERO).returned(Boolean.TRUE));
    assertEquals(expectedMethods, factory.getMethodCalls());
    pool.restore(obj);

    // // Test exception handling of borrow
    reset(pool, factory, expectedMethods);

    // create Exceptions should be propagated to client code from
    // borrow
    factory.setCreateFail(true);
    try {
      obj = pool.borrow();
      fail("Expected borrow to propagate create exception.");
    } catch (final PrivateException pe) {
      // expected
    }
    expectedMethods.add(new MethodCall("create"));
    assertEquals(expectedMethods, factory.getMethodCalls());

    // when activate fails in borrow, a new object should be
    // borrowed/created
    reset(pool, factory, expectedMethods);
    pool.add();
    clear(factory, expectedMethods);

    factory.setActivateFail(true);
    expectedMethods.add(new MethodCall("activate", obj));
    try {
      obj = pool.borrow();
      fail("Expecting NoSuchElementException");
    } catch (final NoSuchElementException ex) {
      // Expected - newly created object will also fail to activate
    }
    // Idle object fails activation, new one created, also fails
    expectedMethods.add(new MethodCall("create").returned(ONE));
    expectedMethods.add(new MethodCall("activate", ONE));
    removeDestroyCall(factory.getMethodCalls()); // The exact timing of
                                                 // destroyObject is
                                                 // flexible here.
    assertEquals(expectedMethods, factory.getMethodCalls());

    // when validate fails in borrow, a new object should be
    // borrowed/created
    reset(pool, factory, expectedMethods);
    pool.add();
    clear(factory, expectedMethods);

    factory.setValidateFail(true);
    expectedMethods.add(new MethodCall("activate", ZERO));
    expectedMethods.add(new MethodCall("validate", ZERO));
    try {
      obj = pool.borrow();
    } catch (final NoSuchElementException ex) {
      // Expected - newly created object will also fail to validate
    }
    // Idle object is activated, but fails validation.
    // New instance is created, activated and then fails validation
    expectedMethods.add(new MethodCall("create").returned(ONE));
    expectedMethods.add(new MethodCall("activate", ONE));
    expectedMethods.add(new MethodCall("validate", ONE));
    // The exact timing of destroy is flexible here.
    removeDestroyCall(factory.getMethodCalls());
    // Second activate and validate are missing from expectedMethods
    assertTrue(factory.getMethodCalls().containsAll(expectedMethods));
  }

  public void testPOFReturnUsages() throws PoolException {
    final MethodCallPoolableFactory factory = new MethodCallPoolableFactory();
    final Pool<Integer> pool;
    try {
      pool = makeEmptyPool(factory);
    } catch (final UnsupportedOperationException uoe) {
      return; // test not supported
    }
    final List<MethodCall> expectedMethods = new ArrayList<MethodCall>();
    Integer obj;

    // / Test correct behavior code paths
    obj = pool.borrow();
    clear(factory, expectedMethods);

    // returned object should be passivated
    pool.restore(obj);
    // StackPool<Integer>, SoftReferencePool<Integer> also validate on return
    // FIXME
    // if ((pool instanceof StackPool<Integer>)
    // || (pool instanceof SoftReferencePool<Integer>)) {
    // expectedMethods.add(new MethodCall("validate",
    // obj).returned(Boolean.TRUE));
    // }
    expectedMethods.add(new MethodCall("passivate", obj));
    assertEquals(expectedMethods, factory.getMethodCalls());

    // // Test exception handling of restore
    reset(pool, factory, expectedMethods);
    pool.add();
    pool.add();
    pool.add();
    assertEquals(3, pool.getIdleCount());
    // passivate should swallow exceptions and not add the object to the
    // pool
    obj = pool.borrow();
    pool.borrow();
    assertEquals(1, pool.getIdleCount());
    assertEquals(2, pool.getActiveCount());
    clear(factory, expectedMethods);
    factory.setPassivateFail(true);
    pool.restore(obj);
    // StackPool<Integer>, SoftReferencePool<Integer> also validate on return
    // FIXME
    // if ((pool instanceof StackPool<Integer>)
    // || (pool instanceof SoftReferencePool<Integer>)) {
    // expectedMethods.add(new MethodCall("validate",
    // obj).returned(Boolean.TRUE));
    // }
    expectedMethods.add(new MethodCall("passivate", obj));
    removeDestroyCall(factory.getMethodCalls()); // The exact timing of
                                                 // destroyObject is
                                                 // flexible here.
    assertEquals(expectedMethods, factory.getMethodCalls());
    assertEquals(1, pool.getIdleCount()); // Not returned
    assertEquals(1, pool.getActiveCount()); // But not in active count

    // destroy should swallow exceptions too
    reset(pool, factory, expectedMethods);
    obj = pool.borrow();
    clear(factory, expectedMethods);
    factory.setPassivateFail(true);
    pool.restore(obj);
  }

  public void testPOFInvalidateUsages() throws Exception {
    final MethodCallPoolableFactory factory = new MethodCallPoolableFactory();
    final Pool<Integer> pool;
    try {
      pool = makeEmptyPool(factory);
    } catch (final UnsupportedOperationException uoe) {
      return; // test not supported
    }
    final List<MethodCall> expectedMethods = new ArrayList<MethodCall>();
    Integer obj;

    // / Test correct behavior code paths

    obj = pool.borrow();
    clear(factory, expectedMethods);

    // invalidated object should be destroyed
    pool.invalidate(obj);
    expectedMethods.add(new MethodCall("destroyObject", obj));
    assertEquals(expectedMethods, factory.getMethodCalls());

    // // Test exception handling of invalidate
    reset(pool, factory, expectedMethods);
    obj = pool.borrow();
    clear(factory, expectedMethods);
//    factory.setDestroyFail(true);
//    try {
//      pool.invalidate(obj);
//      fail("Expecting destroy exception to propagate");
//    } catch (final PrivateException ex) {
//      // Expected
//    }
    Thread.sleep(250); // could be defered
    removeDestroyCall(factory.getMethodCalls());
    assertEquals(expectedMethods, factory.getMethodCalls());
  }

  public void testPOFClearUsages() throws Exception {
    final MethodCallPoolableFactory factory = new MethodCallPoolableFactory();
    final Pool<Integer> pool;
    try {
      pool = makeEmptyPool(factory);
    } catch (final UnsupportedOperationException uoe) {
      return; // test not supported
    }

    // / Test correct behavior code paths
    for (int i = 0; i < 5; ++i) {
      pool.add();
    }
    pool.clear();

    // // Test exception handling clear should swallow destory object failures
//    final List<MethodCall> expectedMethods = new ArrayList<MethodCall>();
//    reset(pool, factory, expectedMethods);
//    factory.setDestroyFail(true);
//    PoolUtils.prefill(pool, 5);
//    pool.clear();
  }

  public void testPOFCloseUsages() throws Exception {
    final MethodCallPoolableFactory factory = new MethodCallPoolableFactory();
    Pool<Integer> pool;
    try {
      pool = makeEmptyPool(factory);
    } catch (final UnsupportedOperationException uoe) {
      return; // test not supported
    }


    // / Test correct behavior code paths
    for (int i = 0; i < 5; ++i) {
      pool.add();
    }
    pool.close();

    // // Test exception handling close should swallow failures
//    try {
//      pool = makeEmptyPool(factory);
//    } catch (final UnsupportedOperationException uoe) {
//      return; // test not supported
//    }
//    final List<MethodCall> expectedMethods = new ArrayList<MethodCall>();
//    reset(pool, factory, expectedMethods);
//    factory.setDestroyFail(true);
//    PoolUtils.prefill(pool, 5);
//    pool.close();
  }

  public void testToString() {
    Pool<Integer> pool;
    try {
      pool = makeEmptyPool(new MethodCallPoolableFactory());
    } catch (final UnsupportedOperationException uoe) {
      return; // test not supported
    }
    pool.toString();
  }

  static void removeDestroyCall(final List<MethodCall> calls) {
    final Iterator<MethodCall> iter = calls.iterator();
    while (iter.hasNext()) {
      final MethodCall call = iter.next();
      if ("destroyObject".equals(call.getName())) {
        iter.remove();
      }
    }
  }

  private static void reset(final Pool<Integer> pool,
      final MethodCallPoolableFactory factory,
      final List<MethodCall> expectedMethods) throws PoolException {
    pool.clear();
    clear(factory, expectedMethods);
    factory.reset();
  }

  private static void clear(final MethodCallPoolableFactory factory,
      final List<MethodCall> expectedMethods) {
    factory.getMethodCalls().clear();
    expectedMethods.clear();
  }
}
