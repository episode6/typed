package com.episode6.hackit.typed.preferences;

import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.mockspresso.api.DependencyProvider;
import com.episode6.hackit.mockspresso.api.SpecialObjectMaker;
import com.episode6.hackit.mockspresso.mockito.MockitoPlugin;
import com.episode6.hackit.mockspresso.reflect.DependencyKey;
import com.episode6.hackit.mockspresso.reflect.TypeToken;
import com.episode6.hackit.typed.core.util.Supplier;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *
 */
public class Rules {

  public static Mockspresso.Rule mockspresso() {
    return Mockspresso.Builders.simple()
        .plugin(MockitoPlugin.getInstance())
        .specialObjectMaker(supplierMaker())
        .buildRule();
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
