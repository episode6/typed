package com.episode6.hackit.typed.core;

import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.mockspresso.mockito.MockitoPlugin;

import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class Rules {

  public static Mockspresso.Rule mockspresso() {
    return Mockspresso.Builders.simple()
        .plugin(MockitoPlugin.getInstance())
        .buildRule();
  }

  public static MockitoRule mockito() {
    return MockitoJUnit.rule();
  }
}
