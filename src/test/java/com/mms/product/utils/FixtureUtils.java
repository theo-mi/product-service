package com.mms.product.utils;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BeanArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FailoverIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.plugin.SimpleValueJqwikPlugin;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import java.util.Arrays;

public class FixtureUtils {

  public static FixtureMonkey monkey = FixtureMonkey.builder()
      .objectIntrospector(new FailoverIntrospector(
          Arrays.asList(
//              JacksonObjectArbitraryIntrospector.INSTANCE,
              BuilderArbitraryIntrospector.INSTANCE,
              FieldReflectionArbitraryIntrospector.INSTANCE,
              BeanArbitraryIntrospector.INSTANCE
          )
      ))
      .defaultNotNull(true)
//            .plugin(new JacksonPlugin())
      .plugin(new JakartaValidationPlugin())
      .plugin(new SimpleValueJqwikPlugin())
      .build();
}
