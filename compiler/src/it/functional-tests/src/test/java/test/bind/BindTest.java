/*
 * Copyright (C) 2016 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test.bind;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.google.common.truth.Truth.assertThat;

// TODO(ronshapiro): rename to BindsTest
@RunWith(JUnit4.class)
public class BindTest {

  private TestComponent component;

  @Before
  public void setUp() {
    component = DaggerTestComponent.create();
  }

  @Test
  public void bindDelegates() {
    assertThat(component.object()).isInstanceOf(FooOfStrings.class);
    assertThat(component.fooOfStrings()).isInstanceOf(FooOfStrings.class);
    assertThat(component.fooOfObjects()).isInstanceOf(FooOfObjects.class);
    assertThat(component.fooOfIntegers()).isNotNull();
  }

  @Test
  public void bindWithScope() {
    assertThat(component.qualifiedFooOfStrings())
        .isSameAs(component.qualifiedFooOfStrings());
  }

  @Test
  public void multibindings() {
    assertThat(component.foosOfNumbers()).hasSize(2);
    assertThat(component.objects()).hasSize(3);
    assertThat(component.charSequences()).hasSize(5);

    assertThat(component.integerObjectMap())
        .containsExactlyEntriesIn(
            ImmutableMap.of(123, "123-string", 456, "456-string", 789, "789-string"));
    assertThat(component.integerProviderOfObjectMap()).hasSize(3);
    assertThat(component.integerProviderOfObjectMap().get(123).get()).isEqualTo("123-string");
    assertThat(component.integerProviderOfObjectMap().get(456).get()).isEqualTo("456-string");
    assertThat(component.integerProviderOfObjectMap().get(789).get()).isEqualTo("789-string");

    assertThat(component.qualifiedIntegerObjectMap()).hasSize(1);
  }
}
