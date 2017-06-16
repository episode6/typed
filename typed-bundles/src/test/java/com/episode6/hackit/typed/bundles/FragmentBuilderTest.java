package com.episode6.hackit.typed.bundles;

import android.app.Fragment;
import android.os.Bundle;
import com.episode6.hackit.typed.testing.Rules;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoRule;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Tests {@link FragmentBuilder}
 */
public class FragmentBuilderTest {

  @Rule public final MockitoRule mockito = Rules.mockito();

  public static class TestObj {}

  static final BundleNamespace NAMESPACE = BundleNamespace.fromClass(FragmentBuilderTest.class);

  static final BundleKey<TestObj> TEST_OBJ_KEY = NAMESPACE.key(TestObj.class).named("testObjKey").buildWithDefault(new TestObj());
  static final ReqBundleKey<TestObj> TEST_OBJ_REQ_KEY = NAMESPACE.key(TestObj.class).named("testObjReqKey").buildRequired();
  static final OptBundleKey<TestObj> TEST_OBJ_OPT_KEY = NAMESPACE.key(TestObj.class).named("testObjOptKey").buildOptional();

  @Mock TypedBundle mTypedBundle;
  @Mock Bundle mBundle;
  @Mock TestObj mTestObj;

  @Test
  public void testBuildArgs() {
    when(mTypedBundle.asBundle()).thenReturn(mBundle);

    TestFragment fragment = new FragmentBuilder<>(mTypedBundle, TestFragment.class)
        .arg(TEST_OBJ_KEY, mTestObj)
        .arg(TEST_OBJ_REQ_KEY, mTestObj)
        .arg(TEST_OBJ_OPT_KEY, mTestObj)
        .build();

    assertThat(fragment.args).isEqualTo(mBundle);
    InOrder inOrder = Mockito.inOrder(mTypedBundle, mBundle, mTestObj);
    inOrder.verify(mTypedBundle).put(TEST_OBJ_KEY, mTestObj);
    inOrder.verify(mTypedBundle).put(TEST_OBJ_REQ_KEY, mTestObj);
    inOrder.verify(mTypedBundle).put(TEST_OBJ_OPT_KEY, mTestObj);
    inOrder.verify(mTypedBundle).asBundle();
    verifyNoMoreInteractions(mTypedBundle, mBundle, mTestObj);
  }

  public static class TestFragment extends Fragment {
    Bundle args;

    @Override
    public void setArguments(Bundle args) {
      this.args = args;
    }
  }
}
