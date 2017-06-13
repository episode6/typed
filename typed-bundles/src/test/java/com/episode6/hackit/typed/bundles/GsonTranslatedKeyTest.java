package com.episode6.hackit.typed.bundles;

import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.typed.core.TypedKey;
import com.episode6.hackit.typed.testing.Rules;
import com.google.common.collect.ImmutableSet;
import org.junit.Rule;
import org.junit.Test;
import org.powermock.core.classloader.annotations.MockPolicy;

import java.util.Set;

/**
 *
 */
@MockPolicy({TestResources.MockPolicy.class})
public class GsonTranslatedKeyTest {

  final TestResources t = new TestResources();
  @Rule public final Mockspresso.Rule mockspresso = Rules.mockspressoBuilder()
      .testResources(t)
      .buildRule();

  static class TestObj {}

  static final BundleNamespace NAMESPACE = BundleNamespace.fromClass(GsonTranslatedKeyTest.class);

  static final BundleKey<TestObj> TEST_OBJ_KEY = NAMESPACE.key(TestObj.class).named("testObjKey").buildWithDefault(new TestObj());
  static final ReqBundleKey<TestObj> TEST_OBJ_REQ_KEY = NAMESPACE.key(TestObj.class).named("testObjReqKey").buildRequired();
  static final OptBundleKey<TestObj> TEST_OBJ_OPT_KEY = NAMESPACE.key(TestObj.class).named("testObjOptKey").buildOptional();

  static final Set<TypedKey> ALL_KEYS = ImmutableSet.<TypedKey>of(
      TEST_OBJ_KEY,
      TEST_OBJ_OPT_KEY,
      TEST_OBJ_REQ_KEY);

  @Test
  public void testTestObjDoesntExist() {
    for (TypedKey key : ALL_KEYS) {
      t.testDoesntContain(key);
    }
  }

  @Test
  public void testGetTestObjDoesntExist() {
    for (TypedKey key : ALL_KEYS) {
      t.testGetDoesntExist(key);
    }
  }

  @Test
  public void testRemoveTestObj() {
    for (TypedKey key : ALL_KEYS) {
      t.testRemove(key);
    }
  }

  @Test
  public void testGetTestObj() {
    t.testGetGsonTranslated(TEST_OBJ_KEY, new TestObj());
    t.testGetGsonTranslated(TEST_OBJ_OPT_KEY, new TestObj());
    t.testGetGsonTranslated(TEST_OBJ_REQ_KEY, new TestObj());
  }

  @Test
  public void testPutTestObj() {
    t.testPutGsonTranslated(TEST_OBJ_KEY, new TestObj());
    t.testPutGsonTranslated(TEST_OBJ_OPT_KEY, new TestObj());
    t.testPutGsonTranslated(TEST_OBJ_REQ_KEY, new TestObj());
  }
}
