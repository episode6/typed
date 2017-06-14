package com.episode6.hackit.typed.bundles;

import android.os.Parcel;
import android.os.Parcelable;
import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.typed.testing.Rules;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.powermock.core.classloader.annotations.MockPolicy;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.eq;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 *
 */
@MockPolicy(TestResources.MockPolicy.class)
public class CustomTranslatorKeyTest {

  final TestResources t = new TestResources();
  @Rule public final Mockspresso.Rule mockspresso = Rules.mockspressoBuilder()
      .testResources(t)
      .buildRule();

  static final BundleNamespace NAMESPACE = BundleNamespace.fromClass(CustomTranslatorKeyTest.class);
  ReqBundleKey<TestParcelable> PARCELABLE_KEY = NAMESPACE.<TestParcelable>parcelableKey()
      .named("parcelableKey")
      .buildRequired();
  ReqBundleKey<ArrayList<TestParcelable>> PARCELABLE_ARRAY_KEY = NAMESPACE.<TestParcelable>parcelableArrayKey()
      .named("parcelableArrayListKey")
      .buildRequired();
  ReqBundleKey<ArrayList<TestParcelable>> PARCELABLE_ARRAY_LIST_KEY = NAMESPACE.<TestParcelable>parcelableArrayListKey()
      .named("parcelableArrayListKey")
      .buildRequired();

  @Test
  public void testGetParcelable() {
    t.testGetTranslated(PARCELABLE_KEY, new TestResources.Tester<TestParcelable>() {
      @Override
      public TestParcelable setup(String keyName) {
        TestParcelable val = new TestParcelable();
        when(t.bundle.getParcelable(keyName)).thenReturn(val);
        return val;
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).getParcelable(keyName);
      }
    });
  }

  @Test
  public void testPutParcelable() {
    t.testPutTranslated(PARCELABLE_KEY, new TestResources.Tester<TestParcelable>() {
      final TestParcelable tp = new TestParcelable();
      @Override
      public TestParcelable setup(String keyName) {
        return tp;
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).putParcelable(keyName, tp);
      }
    });
  }

  @Test
  public void testGetParcelableArray() {
    t.testGetTranslated(PARCELABLE_ARRAY_KEY, new TestResources.Tester<ArrayList<TestParcelable>>() {
      final TestParcelable[] array = new TestParcelable[] {new TestParcelable()};
      @Override
      public ArrayList<TestParcelable> setup(String keyName) {
        when(t.bundle.getParcelableArray(keyName)).thenReturn(array);
        return new ArrayList<TestParcelable>(Arrays.asList(array));
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).getParcelableArray(keyName);
      }
    });
  }

  @Test
  public void testPutParcelableArray() {
    t.testPutTranslated(PARCELABLE_ARRAY_KEY, new TestResources.Tester<ArrayList<TestParcelable>>() {
      final TestParcelable[] array = new TestParcelable[] {new TestParcelable()};
      @Override
      public ArrayList<TestParcelable> setup(String keyName) {
        return new ArrayList<TestParcelable>(Arrays.asList(array));
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).putParcelableArray(eq(keyName), eq(array));
      }
    });
  }

  @Test
  public void testGetParcelableArrayList() {
    t.testGetTranslated(PARCELABLE_ARRAY_LIST_KEY, new TestResources.Tester<ArrayList<TestParcelable>>() {

      @Override
      public ArrayList<TestParcelable> setup(String keyName) {
        final ArrayList<TestParcelable> list = new ArrayList<TestParcelable>();
        when(t.bundle.<TestParcelable>getParcelableArrayList(keyName)).thenReturn(list);
        return list;
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).getParcelableArrayList(keyName);
      }
    });
  }

  @Test
  public void testPutParcelableArrayList() {
    t.testPutTranslated(PARCELABLE_ARRAY_LIST_KEY, new TestResources.Tester<ArrayList<TestParcelable>>() {
      final ArrayList<TestParcelable> list = new ArrayList<TestParcelable>();
      @Override
      public ArrayList<TestParcelable> setup(String keyName) {
        return list;
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).putParcelableArrayList(keyName, list);
      }
    });
  }


  static class TestParcelable implements Parcelable {
    @Override
    public int describeContents() {
      return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static Creator<TestParcelable> CREATOR = new Creator<TestParcelable>() {
      @Override
      public TestParcelable createFromParcel(Parcel parcel) {
        return new TestParcelable();
      }

      @Override
      public TestParcelable[] newArray(int i) {
        return new TestParcelable[i];
      }
    };
  }
}
