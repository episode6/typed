package com.episode6.hackit.typed.bundles;

import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.typed.core.TypedKey;
import com.episode6.hackit.typed.testing.Rules;
import com.google.common.collect.ImmutableSet;
import com.google.gson.reflect.TypeToken;
import org.junit.Rule;
import org.junit.Test;
import org.powermock.core.classloader.annotations.MockPolicy;

import java.util.HashMap;
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

  static final TypeToken<HashMap<String, TestObj>> MAP_TOKEN = new TypeToken<HashMap<String, TestObj>>() {};
  static final BundleKey<HashMap<String, TestObj>> MAP_KEY = NAMESPACE.key(MAP_TOKEN).named("mapKey").buildWithDefault(new HashMap<String, TestObj>());
  static final ReqBundleKey<HashMap<String, TestObj>> MAP_REQ_KEY = NAMESPACE.key(MAP_TOKEN).named("mapReqKey").buildRequired();
  static final OptBundleKey<HashMap<String, TestObj>> MAP_OPT_KEY = NAMESPACE.key(MAP_TOKEN).named("mapOptKey").buildOptional();

  static final Set<TypedKey> ALL_KEYS = ImmutableSet.<TypedKey>of(
      TEST_OBJ_KEY,
      TEST_OBJ_OPT_KEY,
      TEST_OBJ_REQ_KEY,
      MAP_KEY,
      MAP_OPT_KEY,
      MAP_REQ_KEY);

  @Test
  public void testContainAll() {
    for (TypedKey key : ALL_KEYS) {
      t.testDoesntContain(key);
    }
  }

  @Test
  public void testDoesntExistAll() {
    for (TypedKey key : ALL_KEYS) {
      t.testGetDoesntExist(key);
    }
  }

  @Test
  public void testRemoveAll() {
    for (TypedKey key : ALL_KEYS) {
      t.testRemove(key);
    }
  }

  @Test
  public void testGetTestObj() {
    t.testGetGsonTranslated(TEST_OBJ_KEY, new TestObj());
  }

  @Test
  public void testGetTestObjOpt() {
    t.testGetGsonTranslated(TEST_OBJ_OPT_KEY, new TestObj());
  }

  @Test
  public void testGetTestObjReq() {
    t.testGetGsonTranslated(TEST_OBJ_REQ_KEY, new TestObj());
  }

  @Test
  public void testPutTestObj() {
    t.testPutGsonTranslated(TEST_OBJ_KEY, new TestObj());
  }

  @Test
  public void testPutTestObjOpt() {
    t.testPutGsonTranslated(TEST_OBJ_OPT_KEY, new TestObj());
  }

  @Test
  public void testPutTestObjReq() {
    t.testPutGsonTranslated(TEST_OBJ_REQ_KEY, new TestObj());
  }

  @Test
  public void testGetGeneric() {
    t.testGetGsonTranslated(MAP_KEY, new HashMap<String, TestObj>());
  }

  @Test
  public void testGetGenericOpt() {
    t.testGetGsonTranslated(MAP_OPT_KEY, new HashMap<String, TestObj>());
  }

  @Test
  public void testGetGenericReq() {
    t.testGetGsonTranslated(MAP_REQ_KEY, new HashMap<String, TestObj>());
  }

  @Test
  public void testPutGeneric() {
    t.testPutGsonTranslated(MAP_KEY, new HashMap<String, TestObj>());
  }

  @Test
  public void testPutGenericOpt() {
    t.testPutGsonTranslated(MAP_OPT_KEY, new HashMap<String, TestObj>());
  }

  @Test
  public void testPutGenericReq() {
    t.testPutGsonTranslated(MAP_REQ_KEY, new HashMap<String, TestObj>());
  }
}
