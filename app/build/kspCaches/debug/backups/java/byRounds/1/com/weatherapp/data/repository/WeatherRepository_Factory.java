package com.weatherapp.data.repository;

import com.weatherapp.data.api.WeatherApiService;
import com.weatherapp.data.local.dao.WeatherDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class WeatherRepository_Factory implements Factory<WeatherRepository> {
  private final Provider<WeatherApiService> apiProvider;

  private final Provider<WeatherDao> daoProvider;

  public WeatherRepository_Factory(Provider<WeatherApiService> apiProvider,
      Provider<WeatherDao> daoProvider) {
    this.apiProvider = apiProvider;
    this.daoProvider = daoProvider;
  }

  @Override
  public WeatherRepository get() {
    return newInstance(apiProvider.get(), daoProvider.get());
  }

  public static WeatherRepository_Factory create(Provider<WeatherApiService> apiProvider,
      Provider<WeatherDao> daoProvider) {
    return new WeatherRepository_Factory(apiProvider, daoProvider);
  }

  public static WeatherRepository newInstance(WeatherApiService api, WeatherDao dao) {
    return new WeatherRepository(api, dao);
  }
}
