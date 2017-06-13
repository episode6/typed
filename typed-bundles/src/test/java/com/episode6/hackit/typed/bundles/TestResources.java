package com.episode6.hackit.typed.bundles;

import android.os.Bundle;
import android.util.Size;
import android.util.SizeF;
import com.episode6.hackit.mockspresso.annotation.RealObject;
import com.episode6.hackit.typed.core.TypedKey;
import com.google.common.base.VerifyException;
import com.google.gson.Gson;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.spi.PowerMockPolicy;
import org.powermock.mockpolicies.MockPolicyClassLoadingSettings;
import org.powermock.mockpolicies.MockPolicyInterceptionSettings;

import java.lang.reflect.Type;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 *
 */
public class TestResources {

  public interface Tester<T> {
    T setup(String keyName);
    void verify(String keyName, InOrder inOrder);
  }

  static class MockPolicy implements PowerMockPolicy {

    @Override
    public void applyClassLoadingPolicy(MockPolicyClassLoadingSettings settings) {
      settings.addFullyQualifiedNamesOfClassesToLoadByMockClassloader(Gson.class.getName());
    }

    @Override
    public void applyInterceptionPolicy(MockPolicyInterceptionSettings settings) {

    }
  }

  @Mock Bundle bundle;
  @Mock Gson gson;

  @RealObject(implementation = TypedBundleImpl.class) TypedBundle typedBundle;

  void testDoesntContain(TypedKey key) {
    if (key instanceof BundleKey) {
      assertThat(typedBundle.contains((BundleKey<?>) key)).isFalse();
    } else if (key instanceof ReqBundleKey) {
      assertThat(typedBundle.contains((ReqBundleKey<?>) key)).isFalse();
    } else if (key instanceof OptBundleKey) {
      assertThat(typedBundle.contains((OptBundleKey<?>) key)).isFalse();
    } else {
      throw new VerifyException();
    }

    verifyContainsCalled(key);
  }

  void verifyContainsCalled(TypedKey key) {
    InOrder inOrder = Mockito.inOrder(bundle, gson);
    inOrder.verify(bundle).containsKey(key.getKeyName().toString());
    verifyNoMoreInteractions(bundle, gson);
  }

  void testGetDoesntExist(TypedKey key) {
    if (key instanceof BundleKey) {
      assertThat(typedBundle.get((BundleKey)key)).isEqualTo(((BundleKey) key).getDefaultValue());
    } else if (key instanceof ReqBundleKey) {
      try {
        typedBundle.get((ReqBundleKey)key);
      } catch (MissingPropertyException e) {
        verifyContainsCalled(key);
        return;
      }
      fail("expected exception to be thrown: " + key.getKeyName());
    } else if (key instanceof OptBundleKey) {
      assertThat(typedBundle.get((OptBundleKey)key)).isNull();
    } else {
      throw new VerifyException();
    }
    verifyContainsCalled(key);
  }

  void testRemove(TypedKey key) {
    if (key instanceof BundleKey) {
      typedBundle.remove((BundleKey)key);
    } else if (key instanceof ReqBundleKey) {
      typedBundle.remove((ReqBundleKey)key);
    } else if (key instanceof OptBundleKey) {
      typedBundle.remove((OptBundleKey)key);
    } else {
      throw new VerifyException();
    }

    verifyRemoveCalled(key);
  }

  void verifyRemoveCalled(TypedKey key) {
    InOrder inOrder = Mockito.inOrder(bundle, gson);
    inOrder.verify(bundle).remove(key.getKeyName().toString());
    verifyNoMoreInteractions(bundle, gson);
  }

  <T> void testGetDirectTranslated(TypedKey<T> key) {
    Tester<T> tester = getGetTester(key.getObjectType());
    final String keyName = key.getKeyName().toString();
    when(bundle.containsKey(keyName)).thenReturn(true);
    T expected = tester.setup(keyName);

    T result = get(key);

    assertThat(result).isEqualTo(expected);
    InOrder inOrder = Mockito.inOrder(bundle, gson);
    inOrder.verify(bundle).containsKey(keyName);
    tester.verify(keyName, inOrder);
    verifyNoMoreInteractions(bundle, gson);
  }

  <T> void testPutDirectTranslated(TypedKey<T> key) {
    Tester<T> tester = getPutTester(key.getObjectType());
    final String keyName = key.getKeyName().toString();
    T objToSet = tester.setup(keyName);

    put(key, objToSet);

    InOrder inOrder = Mockito.inOrder(bundle, gson);
    tester.verify(keyName, inOrder);
    verifyNoMoreInteractions(bundle, gson);
  }

  @SuppressWarnings("unchecked")
  <T> Tester<T> getGetTester(Type clazz) {
    if (clazz == Boolean.class) {
      return (Tester<T>) new Tester<Boolean>() {
        @Override
        public Boolean setup(String keyName) {
          when(bundle.getBoolean(keyName)).thenReturn(true);
          return true;
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).getBoolean(keyName);
        }
      };
    }

    if (clazz == Integer.class) {
      return (Tester<T>) new Tester<Integer>() {
        @Override
        public Integer setup(String keyName) {
          when(bundle.getInt(keyName)).thenReturn(4);
          return 4;
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).getInt(keyName);
        }
      };
    }

    if (clazz == String.class) {
      return (Tester<T>) new Tester<String>() {
        @Override
        public String setup(String keyName) {
          when(bundle.getString(keyName)).thenReturn("hello there");
          return "hello there";
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).getString(keyName);
        }
      };
    }

    if (clazz == Float.class) {
      return (Tester<T>) new Tester<Float>() {
        @Override
        public Float setup(String keyName) {
          when(bundle.getFloat(keyName)).thenReturn(2.5f);
          return 2.5f;
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).getFloat(keyName);
        }
      };
    }

    if (clazz == Long.class) {
      return (Tester<T>) new Tester<Long>() {
        @Override
        public Long setup(String keyName) {
          when(bundle.getLong(keyName)).thenReturn(92L);
          return 92L;
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).getLong(keyName);
        }
      };
    }

    if (clazz == Double.class) {
      return (Tester<T>) new Tester<Double>() {
        @Override
        public Double setup(String keyName) {
          when(bundle.getDouble(keyName)).thenReturn(92.8d);
          return 92.8d;
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).getDouble(keyName);
        }
      };
    }

    if (clazz == Short.class) {
      return (Tester<T>) new Tester<Short>() {
        @Override
        public Short setup(String keyName) {
          when(bundle.getShort(keyName)).thenReturn((short)2);
          return (short)2;
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).getShort(keyName);
        }
      };
    }

    if (clazz == Character.class) {
      return (Tester<T>) new Tester<Character>() {
        @Override
        public Character setup(String keyName) {
          when(bundle.getChar(keyName)).thenReturn('q');
          return 'q';
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).getChar(keyName);
        }
      };
    }

    if (clazz == Byte.class) {
      return (Tester<T>) new Tester<Byte>() {
        @Override
        public Byte setup(String keyName) {
          when(bundle.getByte(keyName)).thenReturn((byte)1);
          return (byte)1;
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).getByte(keyName);
        }
      };
    }

    if (clazz == Bundle.class) {
      return (Tester<T>) new Tester<Bundle>() {
        @Override
        public Bundle setup(String keyName) {
          Bundle b = mock(Bundle.class);
          when(bundle.getBundle(keyName)).thenReturn(b);
          return b;
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).getBundle(keyName);
        }
      };
    }

    if (clazz == TypedBundle.class) {
      return (Tester<T>) new Tester<TypedBundle>() {
        @Override
        public TypedBundle setup(String keyName) {
          Bundle b = mock(Bundle.class);
          when(bundle.getBundle(keyName)).thenReturn(b);
          return TypedBundles.fromBundle(b);
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).getBundle(keyName);
        }
      };
    }

    if (clazz == CharSequence.class) {
      return (Tester<T>) new Tester<CharSequence>() {
        @Override
        public CharSequence setup(String keyName) {
          when(bundle.getCharSequence(keyName)).thenReturn("hello there");
          return "hello there";
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).getCharSequence(keyName);
        }
      };
    }

    if (clazz == Size.class) {
      return (Tester<T>) new Tester<Size>() {
        @Override
        public Size setup(String keyName) {
          Size b = mock(Size.class);
          when(bundle.getSize(keyName)).thenReturn(b);
          return b;
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).getSize(keyName);
        }
      };
    }

    if (clazz == SizeF.class) {
      return (Tester<T>) new Tester<SizeF>() {
        @Override
        public SizeF setup(String keyName) {
          SizeF b = mock(SizeF.class);
          when(bundle.getSizeF(keyName)).thenReturn(b);
          return b;
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).getSizeF(keyName);
        }
      };
    }
    throw new VerifyException(clazz.toString());
  }

  @SuppressWarnings("unchecked")
  <T> Tester<T> getPutTester(Type clazz) {
    if (clazz == Boolean.class) {
      return (Tester<T>) new Tester<Boolean>() {
        @Override
        public Boolean setup(String keyName) {
          return true;
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).putBoolean(keyName, true);
        }
      };
    }

    if (clazz == Integer.class) {
      return (Tester<T>) new Tester<Integer>() {
        @Override
        public Integer setup(String keyName) {
          return 4;
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).putInt(keyName, 4);
        }
      };
    }

    if (clazz == String.class) {
      return (Tester<T>) new Tester<String>() {
        @Override
        public String setup(String keyName) {
          return "hello there";
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).putString(keyName, "hello there");
        }
      };
    }

    if (clazz == Float.class) {
      return (Tester<T>) new Tester<Float>() {
        @Override
        public Float setup(String keyName) {
          return 2.5f;
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).putFloat(keyName, 2.5f);
        }
      };
    }

    if (clazz == Long.class) {
      return (Tester<T>) new Tester<Long>() {
        @Override
        public Long setup(String keyName) {
          return 92L;
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).putLong(keyName, 92L);
        }
      };
    }

    if (clazz == Double.class) {
      return (Tester<T>) new Tester<Double>() {
        @Override
        public Double setup(String keyName) {
          return 92.8d;
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).putDouble(keyName, 92.8d);
        }
      };
    }

    if (clazz == Short.class) {
      return (Tester<T>) new Tester<Short>() {
        @Override
        public Short setup(String keyName) {
          return (short)2;
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).putShort(keyName, (short)2);
        }
      };
    }

    if (clazz == Character.class) {
      return (Tester<T>) new Tester<Character>() {
        @Override
        public Character setup(String keyName) {
          return 'q';
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).putChar(keyName, 'q');
        }
      };
    }

    if (clazz == Byte.class) {
      return (Tester<T>) new Tester<Byte>() {
        @Override
        public Byte setup(String keyName) {
          return (byte)1;
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).putByte(keyName, (byte)1);
        }
      };
    }

    if (clazz == Bundle.class) {
      return (Tester<T>) new Tester<Bundle>() {
        final Bundle b = mock(Bundle.class);

        @Override
        public Bundle setup(String keyName) {
          return b;
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).putBundle(keyName, b);
        }
      };
    }

    if (clazz == TypedBundle.class) {
      return (Tester<T>) new Tester<TypedBundle>() {
        final Bundle b = mock(Bundle.class);
        final TypedBundle tb = mock(TypedBundle.class);

        @Override
        public TypedBundle setup(String keyName) {
          when(tb.asBundle()).thenReturn(b);
          return tb;
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          Mockito.verify(tb).asBundle();
          inOrder.verify(bundle).putBundle(keyName, b);
        }
      };
    }

    if (clazz == CharSequence.class) {
      return (Tester<T>) new Tester<CharSequence>() {
        @Override
        public CharSequence setup(String keyName) {
          return "cool beans!";
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).putCharSequence(keyName, "cool beans!");
        }
      };
    }

    if (clazz == Size.class) {
      return (Tester<T>) new Tester<Size>() {
        final Size b = mock(Size.class);
        @Override
        public Size setup(String keyName) {
          return b;
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).putSize(keyName, b);
        }
      };
    }

    if (clazz == SizeF.class) {
      return (Tester<T>) new Tester<SizeF>() {
        final SizeF b = mock(SizeF.class);
        @Override
        public SizeF setup(String keyName) {
          return b;
        }

        @Override
        public void verify(String keyName, InOrder inOrder) {
          inOrder.verify(bundle).putSizeF(keyName, b);
        }
      };
    }
    throw new VerifyException(clazz.toString());
  }

  private <T> T get(TypedKey<T> key) {
    if (key instanceof BundleKey) {
      return typedBundle.get((BundleKey<T>)key);
    } else if (key instanceof ReqBundleKey) {
      return typedBundle.get((ReqBundleKey<T>)key);
    } else if (key instanceof OptBundleKey) {
      return typedBundle.get((OptBundleKey<T>)key);
    } else {
      throw new VerifyException(key.getKeyName().toString());
    }
  }

  private <T> void put(TypedKey<T> key, T instance) {
    if (key instanceof BundleKey) {
      typedBundle.put((BundleKey<T>)key, instance);
    } else if (key instanceof ReqBundleKey) {
      typedBundle.put((ReqBundleKey<T>)key, instance);
    } else if (key instanceof OptBundleKey) {
      typedBundle.put((OptBundleKey<T>)key, instance);
    } else {
      throw new VerifyException(key.getKeyName().toString());
    }
  }
}
