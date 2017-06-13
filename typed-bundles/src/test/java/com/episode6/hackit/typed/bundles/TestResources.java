package com.episode6.hackit.typed.bundles;

import android.os.Bundle;
import com.episode6.hackit.mockspresso.annotation.RealObject;
import com.episode6.hackit.typed.core.TypedKey;
import com.google.gson.Gson;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.spi.PowerMockPolicy;
import org.powermock.mockpolicies.MockPolicyClassLoadingSettings;
import org.powermock.mockpolicies.MockPolicyInterceptionSettings;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 *
 */
public class TestResources {

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

  void testDoesntContain(BundleKey key) {
    assertThat(typedBundle.contains(key)).isFalse();
    verifyContainsCalled(key);
  }

  void testDoesntContain(OptBundleKey key) {
    assertThat(typedBundle.contains(key)).isFalse();
    verifyContainsCalled(key);
  }

  void verifyContainsCalled(TypedKey key) {
    InOrder inOrder = Mockito.inOrder(bundle, gson);
    inOrder.verify(bundle).containsKey(key.getKeyName().toString());
    verifyNoMoreInteractions(bundle, gson);
  }
}
