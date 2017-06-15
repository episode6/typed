package com.episode6.hackit.typed.bundles;

import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.typed.testing.Rules;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.MockPolicy;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Tests {@link TypedBundles#memoize(TypedBundle)}
 */
@MockPolicy({TestResources.MockPolicy.class})
public class MemoizedTypedBundleTest {

  @Rule public final Mockspresso.Rule mockspresso = Rules.mockspresso();

  static class TestObj {}

  static final BundleNamespace NAMESPACE = BundleNamespace.fromClass(MemoizedTypedBundleTest.class);

  static final BundleKey<TestObj> TEST_OBJ_KEY = NAMESPACE.key(TestObj.class).named("testObjKey").buildWithDefault(new TestObj());
  static final ReqBundleKey<TestObj> TEST_OBJ_REQ_KEY = NAMESPACE.key(TestObj.class).named("testObjReqKey").buildRequired();
  static final OptBundleKey<TestObj> TEST_OBJ_OPT_KEY = NAMESPACE.key(TestObj.class).named("testObjOptKey").buildOptional();

  @Mock TypedBundle mTypedBundle;
  @Mock TestObj mTestObj;

  @Test
  public void testTranslateOnlyOnFirstGetBundleKey() {
    when(mTypedBundle.get(TEST_OBJ_KEY)).thenReturn(mTestObj);
    TypedBundle memoized = TypedBundles.memoize(mTypedBundle);

    TestObj result1 = memoized.get(TEST_OBJ_KEY);
    TestObj result2 = memoized.get(TEST_OBJ_KEY);
    TestObj result3 = memoized.get(TEST_OBJ_KEY);

    assertThat(result1)
        .isEqualTo(result2)
        .isEqualTo(result3);
    verify(mTypedBundle, times(1)).get(TEST_OBJ_KEY);
    verifyNoMoreInteractions(mTypedBundle);
  }

  @Test
  public void testTranslateOnlyOnFirstGetReqBundleKey() {
    when(mTypedBundle.get(TEST_OBJ_REQ_KEY)).thenReturn(mTestObj);
    TypedBundle memoized = TypedBundles.memoize(mTypedBundle);

    TestObj result1 = memoized.get(TEST_OBJ_REQ_KEY);
    TestObj result2 = memoized.get(TEST_OBJ_REQ_KEY);
    TestObj result3 = memoized.get(TEST_OBJ_REQ_KEY);

    assertThat(result1)
        .isEqualTo(result2)
        .isEqualTo(result3);
    verify(mTypedBundle, times(1)).get(TEST_OBJ_REQ_KEY);
    verifyNoMoreInteractions(mTypedBundle);
  }

  @Test
  public void testTranslateOnlyOnFirstGetOptBundleKey() {
    when(mTypedBundle.get(TEST_OBJ_OPT_KEY)).thenReturn(mTestObj);
    TypedBundle memoized = TypedBundles.memoize(mTypedBundle);

    TestObj result1 = memoized.get(TEST_OBJ_OPT_KEY);
    TestObj result2 = memoized.get(TEST_OBJ_OPT_KEY);
    TestObj result3 = memoized.get(TEST_OBJ_OPT_KEY);

    assertThat(result1)
        .isEqualTo(result2)
        .isEqualTo(result3);
    verify(mTypedBundle, times(1)).get(TEST_OBJ_OPT_KEY);
    verifyNoMoreInteractions(mTypedBundle);
  }

  @Test
  public void testPutChangesCacheBundleKey() {
    TypedBundle memoized = TypedBundles.memoize(mTypedBundle);

    memoized.put(TEST_OBJ_KEY, mTestObj);
    memoized.get(TEST_OBJ_KEY);
    memoized.get(TEST_OBJ_KEY);
    memoized.get(TEST_OBJ_KEY);

    verify(mTypedBundle).put(TEST_OBJ_KEY, mTestObj);
    verifyNoMoreInteractions(mTypedBundle);
  }

  @Test
  public void testPutChangesCacheReqBundleKey() {
    TypedBundle memoized = TypedBundles.memoize(mTypedBundle);

    memoized.put(TEST_OBJ_REQ_KEY, mTestObj);
    memoized.get(TEST_OBJ_REQ_KEY);
    memoized.get(TEST_OBJ_REQ_KEY);
    memoized.get(TEST_OBJ_REQ_KEY);

    verify(mTypedBundle).put(TEST_OBJ_REQ_KEY, mTestObj);
    verifyNoMoreInteractions(mTypedBundle);
  }

  @Test
  public void testPutChangesCacheOptBundleKey() {
    TypedBundle memoized = TypedBundles.memoize(mTypedBundle);

    memoized.put(TEST_OBJ_OPT_KEY, mTestObj);
    memoized.get(TEST_OBJ_OPT_KEY);
    memoized.get(TEST_OBJ_OPT_KEY);
    memoized.get(TEST_OBJ_OPT_KEY);

    verify(mTypedBundle).put(TEST_OBJ_OPT_KEY, mTestObj);
    verifyNoMoreInteractions(mTypedBundle);
  }

  @Test
  public void testRemoveAfterGetBundleKey() {
    TypedBundle memoized = TypedBundles.memoize(mTypedBundle);

    memoized.get(TEST_OBJ_KEY);
    memoized.remove(TEST_OBJ_KEY);
    memoized.get(TEST_OBJ_KEY);

    InOrder inOrder = inOrder(mTypedBundle);
    inOrder.verify(mTypedBundle).get(TEST_OBJ_KEY);
    inOrder.verify(mTypedBundle).remove(TEST_OBJ_KEY);
    inOrder.verify(mTypedBundle).get(TEST_OBJ_KEY);
    verifyNoMoreInteractions(mTypedBundle);
  }

  @Test
  public void testRemoveAfterGetReqBundleKey() {
    TypedBundle memoized = TypedBundles.memoize(mTypedBundle);

    memoized.get(TEST_OBJ_REQ_KEY);
    memoized.remove(TEST_OBJ_REQ_KEY);
    memoized.get(TEST_OBJ_REQ_KEY);

    InOrder inOrder = inOrder(mTypedBundle);
    inOrder.verify(mTypedBundle).get(TEST_OBJ_REQ_KEY);
    inOrder.verify(mTypedBundle).remove(TEST_OBJ_REQ_KEY);
    inOrder.verify(mTypedBundle).get(TEST_OBJ_REQ_KEY);
    verifyNoMoreInteractions(mTypedBundle);
  }

  @Test
  public void testRemoveAfterGetOptBundleKey() {
    TypedBundle memoized = TypedBundles.memoize(mTypedBundle);

    memoized.get(TEST_OBJ_OPT_KEY);
    memoized.remove(TEST_OBJ_OPT_KEY);
    memoized.get(TEST_OBJ_OPT_KEY);

    InOrder inOrder = inOrder(mTypedBundle);
    inOrder.verify(mTypedBundle).get(TEST_OBJ_OPT_KEY);
    inOrder.verify(mTypedBundle).remove(TEST_OBJ_OPT_KEY);
    inOrder.verify(mTypedBundle).get(TEST_OBJ_OPT_KEY);
    verifyNoMoreInteractions(mTypedBundle);
  }
}
