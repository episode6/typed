package com.episode6.hackit.typed.bundles;

import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.typed.testing.Rules;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.powermock.core.classloader.annotations.MockPolicy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 *
 */
@PrepareForTest({Build.VERSION.class})
@MockPolicy(TestResources.MockPolicy.class)
public class CustomTranslatorKeyTest {

  final TestResources t = new TestResources();
  @Rule public final Mockspresso.Rule mockspresso = Rules.mockspressoBuilder()
      .testResources(t)
      .buildRule();

  static final BundleNamespace NAMESPACE = BundleNamespace.fromClass(CustomTranslatorKeyTest.class);

  static ReqBundleKey<TestParcelable> PARCELABLE_KEY = NAMESPACE.parcelableKey(TestParcelable.class)
      .named("parcelableKey")
      .buildRequired();
  static ReqBundleKey<ArrayList<TestParcelable>> PARCELABLE_ARRAY_KEY = NAMESPACE.parcelableArrayKey(TestParcelable.class)
      .named("parcelableArrayKey")
      .buildRequired();
  static ReqBundleKey<ArrayList<TestParcelable>> PARCELABLE_ARRAY_LIST_KEY = NAMESPACE.parcelableArrayListKey(TestParcelable.class)
      .named("parcelableArrayListKey")
      .buildRequired();
  static ReqBundleKey<SparseArray<TestParcelable>> SPARSE_PARCELABLE_ARRAY_KEY = NAMESPACE.sparseParcelableArrayKey(TestParcelable.class)
      .named("sparceParcelableArrayKey")
      .buildRequired();

  static ReqBundleKey<TestSerializable> SERIALIZABLE_KEY = NAMESPACE.serializableKey(TestSerializable.class)
      .named("serializableKey")
      .buildRequired();

  static ReqBundleKey<TestBinder> binderKey() {
    return NAMESPACE.binderKey(TestBinder.class)
        .named("binderKey")
        .buildRequired();
  }

  static ReqBundleKey<ArrayList<Boolean>> BOOL_ARRAY_KEY = NAMESPACE.boolArrayKey()
      .named("boolArrayKey")
      .buildRequired();

  static ReqBundleKey<ArrayList<Integer>> INT_ARRAY_KEY = NAMESPACE.intArrayKey()
      .named("intArrayKey")
      .buildRequired();
  static ReqBundleKey<ArrayList<Integer>> INT_ARRAY_LIST_KEY = NAMESPACE.intArrayListKey()
      .named("intArrayListKey")
      .buildRequired();

  static ReqBundleKey<ArrayList<String>> STRING_ARRAY_KEY = NAMESPACE.stringArrayKey()
      .named("stringArrayKey")
      .buildRequired();
  static ReqBundleKey<ArrayList<String>> STRING_ARRAY_LIST_KEY = NAMESPACE.stringArrayListKey()
      .named("stringArrayListKey")
      .buildRequired();

  static ReqBundleKey<ArrayList<Float>> FLOAT_ARRAY = NAMESPACE.floatArrayKey()
      .named("floatArrayKey")
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

  @Test
  public void testGetSparseParcelableArray() {
    t.testGetTranslated(SPARSE_PARCELABLE_ARRAY_KEY, new TestResources.Tester<SparseArray<TestParcelable>>() {
      final SparseArray<TestParcelable> sparseArray = mock(SparseArray.class);
      @Override
      public SparseArray<TestParcelable> setup(String keyName) {
        when(t.bundle.<TestParcelable>getSparseParcelableArray(keyName)).thenReturn(sparseArray);
        return sparseArray;
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).getSparseParcelableArray(keyName);
      }
    });
  }

  @Test
  public void testPutSparseParcelableArray() {
    t.testPutTranslated(SPARSE_PARCELABLE_ARRAY_KEY, new TestResources.Tester<SparseArray<TestParcelable>>() {
      final SparseArray<TestParcelable> sparseArray = mock(SparseArray.class);
      @Override
      public SparseArray<TestParcelable> setup(String keyName) {
        return sparseArray;
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).putSparseParcelableArray(keyName, sparseArray);
      }
    });
  }

  @Test
  public void testGetSerializable() {
    t.testGetTranslated(SERIALIZABLE_KEY, new TestResources.Tester<TestSerializable>() {
      @Override
      public TestSerializable setup(String keyName) {
        TestSerializable s = new TestSerializable();
        when(t.bundle.getSerializable(keyName)).thenReturn(s);
        return s;
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).getSerializable(keyName);
      }
    });
  }

  @Test
  public void testPutSerializable() {
    t.testPutTranslated(SERIALIZABLE_KEY, new TestResources.Tester<TestSerializable>() {
      final TestSerializable s = new TestSerializable();
      @Override
      public TestSerializable setup(String keyName) {
        return s;
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).putSerializable(keyName, s);
      }
    });
  }

  @Test(expected = NoSuchMethodError.class)
  public void testBinderKeyOnOldApi() {
    setSdkVersion(17);
    binderKey();
  }

  @Test
  public void testGetBinder() {
    setSdkVersion(18);
    t.testGetTranslated(binderKey(), new TestResources.Tester<TestBinder>() {

      @Override
      public TestBinder setup(String keyName) {
        TestBinder tb = mock(TestBinder.class);
        when(t.bundle.getBinder(keyName)).thenReturn(tb);
        return tb;
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).getBinder(keyName);
      }
    });
  }

  @Test
  public void testPutBinder() {
    setSdkVersion(18);
    t.testPutTranslated(binderKey(), new TestResources.Tester<TestBinder>() {
      final TestBinder tb = mock(TestBinder.class);
      @Override
      public TestBinder setup(String keyName) {
        return tb;
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).putBinder(keyName, tb);
      }
    });
  }

  @Test
  public void testGetBoolArray() {
    t.testGetTranslated(BOOL_ARRAY_KEY, new TestResources.Tester<ArrayList<Boolean>>() {

      @Override
      public ArrayList<Boolean> setup(String keyName) {
        when(t.bundle.getBooleanArray(keyName)).thenReturn(new boolean[] {true, false, true});
        return new ArrayList<>(Arrays.asList(true, false, true));
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).getBooleanArray(keyName);
      }
    });
  }

  @Test
  public void testPutBoolArray() {
    t.testPutTranslated(BOOL_ARRAY_KEY, new TestResources.Tester<ArrayList<Boolean>>() {

      @Override
      public ArrayList<Boolean> setup(String keyName) {
        return new ArrayList<>(Arrays.asList(true, false, true));
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).putBooleanArray(keyName, new boolean[] {true, false, true});
      }
    });
  }

  @Test
  public void testGetIntArray() {
    t.testGetTranslated(INT_ARRAY_KEY, new TestResources.Tester<ArrayList<Integer>>() {

      @Override
      public ArrayList<Integer> setup(String keyName) {
        when(t.bundle.getIntArray(keyName)).thenReturn(new int[] {10, 20, 30});
        return new ArrayList<>(Arrays.asList(10, 20, 30));
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).getIntArray(keyName);
      }
    });
  }

  @Test
  public void testPutIntArray() {
    t.testPutTranslated(INT_ARRAY_KEY, new TestResources.Tester<ArrayList<Integer>>() {

      @Override
      public ArrayList<Integer> setup(String keyName) {
        return new ArrayList<>(Arrays.asList(30, 40, 50));
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).putIntArray(keyName, new int[] {30, 40, 50});
      }
    });
  }

  @Test
  public void testGetIntArrayList() {
    t.testGetTranslated(INT_ARRAY_LIST_KEY, new TestResources.Tester<ArrayList<Integer>>() {
      @Override
      public ArrayList<Integer> setup(String keyName) {
        ArrayList<Integer> list = mock(ArrayList.class);
        when(t.bundle.getIntegerArrayList(keyName)).thenReturn(list);
        return list;
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).getIntegerArrayList(keyName);
      }
    });
  }

  @Test
  public void testPutIntArrayList() {
    t.testPutTranslated(INT_ARRAY_LIST_KEY, new TestResources.Tester<ArrayList<Integer>>() {
      final ArrayList<Integer> list = mock(ArrayList.class);
      @Override
      public ArrayList<Integer> setup(String keyName) {
        return list;
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).putIntegerArrayList(keyName, list);
      }
    });
  }

  @Test
  public void testGetStringArray() {
    t.testGetTranslated(STRING_ARRAY_KEY, new TestResources.Tester<ArrayList<String>>() {
      @Override
      public ArrayList<String> setup(String keyName) {
        String[] array = new String[] {"test", "ing"};
        when(t.bundle.getStringArray(keyName)).thenReturn(array);
        return new ArrayList<>(Arrays.asList(array));
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).getStringArray(keyName);
      }
    });
  }

  @Test
  public void testPutStringArray() {
    t.testPutTranslated(STRING_ARRAY_KEY, new TestResources.Tester<ArrayList<String>>() {
      final String[] array = new String[] {"test", "ing"};
      @Override
      public ArrayList<String> setup(String keyName) {
        return new ArrayList<>(Arrays.asList(array));
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).putStringArray(keyName, array);
      }
    });
  }

  @Test
  public void testGetStringArrayList() {
    t.testGetTranslated(STRING_ARRAY_LIST_KEY, new TestResources.Tester<ArrayList<String>>() {
      @Override
      public ArrayList<String> setup(String keyName) {
        ArrayList<String> list = mock(ArrayList.class);
        when(t.bundle.getStringArrayList(keyName)).thenReturn(list);
        return list;
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).getStringArrayList(keyName);
      }
    });
  }

  @Test
  public void testPutStringArrayList() {
    t.testPutTranslated(STRING_ARRAY_LIST_KEY, new TestResources.Tester<ArrayList<String>>() {
      final ArrayList<String> list = mock(ArrayList.class);
      @Override
      public ArrayList<String> setup(String keyName) {
        return list;
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).putStringArrayList(keyName, list);
      }
    });
  }

  @Test
  public void testGetFloatArray() {
    t.testGetTranslated(FLOAT_ARRAY, new TestResources.Tester<ArrayList<Float>>() {

      @Override
      public ArrayList<Float> setup(String keyName) {
        when(t.bundle.getFloatArray(keyName)).thenReturn(new float[] {10f, 20f, 30f});
        return new ArrayList<>(Arrays.asList(10f, 20f, 30f));
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).getFloatArray(keyName);
      }
    });
  }

  @Test
  public void testPutFloatArray() {
    t.testPutTranslated(FLOAT_ARRAY, new TestResources.Tester<ArrayList<Float>>() {

      @Override
      public ArrayList<Float> setup(String keyName) {
        return new ArrayList<>(Arrays.asList(30f, 40f, 50f));
      }

      @Override
      public void verify(String keyName, InOrder inOrder) {
        inOrder.verify(t.bundle).putFloatArray(keyName, new float[] {30f, 40f, 50f});
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

  static class TestSerializable implements Serializable {}

  public interface TestBinder extends IBinder {}

  private static void setSdkVersion(int version) {
    Whitebox.setInternalState(Build.VERSION.class, "SDK_INT", version);
  }
}
