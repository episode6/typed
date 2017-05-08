package com.episode6.hackit.typed.testing;

import com.episode6.hackit.mockspresso.BuildMockspresso;
import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.mockspresso.api.DependencyProvider;
import com.episode6.hackit.mockspresso.api.SpecialObjectMaker;
import com.episode6.hackit.mockspresso.reflect.DependencyKey;
import com.episode6.hackit.mockspresso.reflect.TypeToken;
import com.episode6.hackit.typed.core.util.Supplier;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *
 */
public class Rules {

  public static Mockspresso.Builder mockspressoBuilder() {
    return BuildMockspresso.with()
        .injector().simple()
        .mocker().mockito()
        .specialObjectMaker(supplierMaker());
  }

  public static Mockspresso.Rule mockspresso() {
    return mockspressoBuilder().buildRule();
  }

  public static MockitoRule mockito() {
    return MockitoJUnit.rule();
  }

  private static SpecialObjectMaker supplierMaker() {
    return new SpecialObjectMaker() {
      @Override
      public boolean canMakeObject(DependencyKey<?> key) {
        return key.typeToken.getRawType() == Supplier.class;
      }

      @Override
      public <T> T makeObject(final DependencyProvider dependencyProvider, DependencyKey<T> key) {
        Type paramType = ((ParameterizedType)key.typeToken.getType()).getActualTypeArguments()[0];
        final DependencyKey<?> subKey = DependencyKey.of(TypeToken.of(paramType), key.identityAnnotation);
        return (T) new Supplier() {

          @Override
          public Object get() {
            return dependencyProvider.get(subKey);
          }
        };
      }
    };
  }
}
