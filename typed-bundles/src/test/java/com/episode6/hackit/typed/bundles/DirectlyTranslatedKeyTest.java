package com.episode6.hackit.typed.bundles;

import android.os.Build;
import android.os.Bundle;
import android.util.Size;
import android.util.SizeF;
import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.typed.core.TypedKey;
import com.episode6.hackit.typed.testing.Rules;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.MockPolicy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import java.util.Set;

/**
 * Tests keys created with {@link DirectBundleTranslators}
 */
@PrepareForTest({Build.VERSION.class})
@MockPolicy({TestResources.MockPolicy.class})
public class DirectlyTranslatedKeyTest {

  final TestResources t = new TestResources();
  @Rule public final Mockspresso.Rule mockspresso = Rules.mockspressoBuilder()
      .testResources(t)
      .buildRule();

  static final BundleNamespace NAMESPACE = BundleNamespace.fromClass(DirectlyTranslatedKeyTest.class);

  static final BundleKey<Boolean> BOOL_KEY = NAMESPACE.key(Boolean.class).named("boolKey").buildWithDefault(true);
  static final ReqBundleKey<Boolean> BOOL_REQ_KEY = NAMESPACE.key(Boolean.class).named("boolReqKey").buildRequired();
  static final OptBundleKey<Boolean> BOOL_OPT_KEY = NAMESPACE.key(Boolean.class).named("boolOptKey").buildOptional();

  static final BundleKey<Integer> INT_KEY = NAMESPACE.key(Integer.class).named("intKey").buildWithDefault(10);
  static final ReqBundleKey<Integer> INT_REQ_KEY = NAMESPACE.key(Integer.class).named("intReqKey").buildRequired();
  static final OptBundleKey<Integer> INT_OPT_KEY = NAMESPACE.key(Integer.class).named("intOptKey").buildOptional();

  static final BundleKey<String> STRING_KEY = NAMESPACE.key(String.class).named("stringKey").buildWithDefault("nice");
  static final ReqBundleKey<String> STRING_REQ_KEY = NAMESPACE.key(String.class).named("stringReqKey").buildRequired();
  static final OptBundleKey<String> STRING_OPT_KEY = NAMESPACE.key(String.class).named("stringOptKey").buildOptional();

  static final BundleKey<Float> FLOAT_KEY = NAMESPACE.key(Float.class).named("floatKey").buildWithDefault(10.3f);
  static final ReqBundleKey<Float> FLOAT_REQ_KEY = NAMESPACE.key(Float.class).named("floatReqKey").buildRequired();
  static final OptBundleKey<Float> FLOAT_OPT_KEY = NAMESPACE.key(Float.class).named("floatOptKey").buildOptional();

  static final BundleKey<Long> LONG_KEY = NAMESPACE.key(Long.class).named("longKey").buildWithDefault(10L);
  static final ReqBundleKey<Long> LONG_REQ_KEY = NAMESPACE.key(Long.class).named("longReqKey").buildRequired();
  static final OptBundleKey<Long> LONG_OPT_KEY = NAMESPACE.key(Long.class).named("longOptKey").buildOptional();

  static final BundleKey<Double> DBL_KEY = NAMESPACE.key(Double.class).named("dblKey").buildWithDefault(10.8d);
  static final ReqBundleKey<Double> DBL_REQ_KEY = NAMESPACE.key(Double.class).named("dblReqKey").buildRequired();
  static final OptBundleKey<Double> DBL_OPT_KEY = NAMESPACE.key(Double.class).named("dblOptKey").buildOptional();

  static final BundleKey<Short> SHORT_KEY = NAMESPACE.key(Short.class).named("shortKey").buildWithDefault((short)10);
  static final ReqBundleKey<Short> SHORT_REQ_KEY = NAMESPACE.key(Short.class).named("shortReqKey").buildRequired();
  static final OptBundleKey<Short> SHORT_OPT_KEY = NAMESPACE.key(Short.class).named("shortOptKey").buildOptional();

  static final BundleKey<Character> CHAR_KEY = NAMESPACE.key(Character.class).named("charKey").buildWithDefault('q');
  static final ReqBundleKey<Character> CHAR_REQ_KEY = NAMESPACE.key(Character.class).named("charReqKey").buildRequired();
  static final OptBundleKey<Character> CHAR_OPT_KEY = NAMESPACE.key(Character.class).named("charOptKey").buildOptional();

  static final BundleKey<Byte> BYTE_KEY = NAMESPACE.key(Byte.class).named("byteKey").buildWithDefault((byte)1);
  static final ReqBundleKey<Byte> BYTE_REQ_KEY = NAMESPACE.key(Byte.class).named("byteReqKey").buildRequired();
  static final OptBundleKey<Byte> BYTE_OPT_KEY = NAMESPACE.key(Byte.class).named("byteOptKey").buildOptional();

  static final BundleKey<Bundle> BUNDLE_KEY = NAMESPACE.key(Bundle.class).named("bundleKey").buildWithDefault(new Bundle());
  static final ReqBundleKey<Bundle> BUNDLE_REQ_KEY = NAMESPACE.key(Bundle.class).named("bundleReqKey").buildRequired();
  static final OptBundleKey<Bundle> BUNDLE_OPT_KEY = NAMESPACE.key(Bundle.class).named("bundleOptKey").buildOptional();

  static final BundleKey<CharSequence> CH_SQ_KEY = NAMESPACE.key(CharSequence.class).named("chSqKey").buildWithDefault("nice");
  static final ReqBundleKey<CharSequence> CH_SQ_REQ_KEY = NAMESPACE.key(CharSequence.class).named("chSqReqKey").buildRequired();;
  static final OptBundleKey<CharSequence> CH_SQ_OPT_KEY = NAMESPACE.key(CharSequence.class).named("chSqOptKey").buildOptional();

  static final Set<TypedKey> STATIC_KEYS = ImmutableSet.<TypedKey>of(
      BOOL_KEY,
      BOOL_REQ_KEY,
      BOOL_OPT_KEY,
      INT_KEY,
      INT_REQ_KEY,
      INT_OPT_KEY,
      STRING_KEY,
      STRING_REQ_KEY,
      STRING_OPT_KEY,
      FLOAT_KEY,
      FLOAT_REQ_KEY,
      FLOAT_OPT_KEY,
      LONG_KEY,
      LONG_REQ_KEY,
      LONG_OPT_KEY,
      DBL_KEY,
      DBL_REQ_KEY,
      DBL_OPT_KEY,
      SHORT_KEY,
      SHORT_REQ_KEY,
      SHORT_OPT_KEY,
      CHAR_KEY,
      CHAR_REQ_KEY,
      CHAR_OPT_KEY,
      BYTE_KEY,
      BYTE_REQ_KEY,
      BYTE_OPT_KEY,
      BUNDLE_KEY,
      BUNDLE_REQ_KEY,
      BUNDLE_OPT_KEY,
      CH_SQ_KEY,
      CH_SQ_REQ_KEY,
      CH_SQ_OPT_KEY);

  @Mock Size mSize;
  @Mock SizeF mSizeF;

  BundleKey<Size> sizeKey;
  ReqBundleKey<Size> sizeReqKey;
  OptBundleKey<Size> sizeOptKey;

  BundleKey<SizeF> sizeFKey;
  ReqBundleKey<SizeF> sizeFReqKey;
  OptBundleKey<SizeF> sizeFOptKey;

  Set<TypedKey> allKeys;

  @Before
  public void setup() {
    Whitebox.setInternalState(Build.VERSION.class, "SDK_INT", 21);

    // these keys require android v > 1, so we can't create the statically
    sizeKey = NAMESPACE.key(Size.class).named("sizeKey").buildWithDefault(mSize);
    sizeReqKey = NAMESPACE.key(Size.class).named("sizeReqKey").buildRequired();
    sizeOptKey = NAMESPACE.key(Size.class).named("sizeOptKey").buildOptional();
    sizeFKey = NAMESPACE.key(SizeF.class).named("sizeFKey").buildWithDefault(mSizeF);
    sizeFReqKey = NAMESPACE.key(SizeF.class).named("sizeFReqKey").buildRequired();
    sizeFOptKey = NAMESPACE.key(SizeF.class).named("sizeFOptKey").buildOptional();

    allKeys = ImmutableSet.<TypedKey>builder()
        .addAll(STATIC_KEYS)
        .add(sizeKey,
            sizeReqKey,
            sizeOptKey,
            sizeFKey,
            sizeFReqKey,
            sizeFOptKey)
        .build();
  }

  @Test
  public void testDoesntExist() {
    for (TypedKey key : allKeys) {
      t.testDoesntContain(key);
    }
  }

  @Test
  public void testGetDoesntExist() {
    for (TypedKey key : allKeys) {
      t.testGetDoesntExist(key);
    }
  }

  @Test
  public void testRemove() {
    for (TypedKey key : allKeys) {
      t.testRemove(key);
    }
  }

  @Test
  public void testGet() {
    for (TypedKey key : allKeys) {
      t.testGetDirectTranslated(key);
    }
  }

  @Test
  public void testPut() {
    for (TypedKey key : allKeys) {
      t.testPutDirectTranslated(key);
    }
  }
}
