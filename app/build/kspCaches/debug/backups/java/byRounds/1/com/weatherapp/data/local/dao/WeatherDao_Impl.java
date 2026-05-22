package com.weatherapp.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.weatherapp.data.local.entity.WeatherCacheEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WeatherDao_Impl implements WeatherDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WeatherCacheEntity> __insertionAdapterOfWeatherCacheEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteExpiredCache;

  public WeatherDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWeatherCacheEntity = new EntityInsertionAdapter<WeatherCacheEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `weather_cache` (`cityName`,`tempCurrent`,`tempMin`,`tempMax`,`feelsLike`,`description`,`iconCode`,`humidity`,`windSpeed`,`windDeg`,`pressure`,`visibility`,`sunrise`,`sunset`,`country`,`cachedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WeatherCacheEntity entity) {
        statement.bindString(1, entity.getCityName());
        statement.bindDouble(2, entity.getTempCurrent());
        statement.bindDouble(3, entity.getTempMin());
        statement.bindDouble(4, entity.getTempMax());
        statement.bindDouble(5, entity.getFeelsLike());
        statement.bindString(6, entity.getDescription());
        statement.bindString(7, entity.getIconCode());
        statement.bindLong(8, entity.getHumidity());
        statement.bindDouble(9, entity.getWindSpeed());
        statement.bindLong(10, entity.getWindDeg());
        statement.bindLong(11, entity.getPressure());
        statement.bindLong(12, entity.getVisibility());
        statement.bindLong(13, entity.getSunrise());
        statement.bindLong(14, entity.getSunset());
        statement.bindString(15, entity.getCountry());
        statement.bindLong(16, entity.getCachedAt());
      }
    };
    this.__preparedStmtOfDeleteExpiredCache = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM weather_cache WHERE cachedAt < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertWeather(final WeatherCacheEntity weather,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfWeatherCacheEntity.insert(weather);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteExpiredCache(final long expiry,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteExpiredCache.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, expiry);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteExpiredCache.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<WeatherCacheEntity> observeWeather(final String city) {
    final String _sql = "SELECT * FROM weather_cache WHERE cityName = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, city);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"weather_cache"}, new Callable<WeatherCacheEntity>() {
      @Override
      @Nullable
      public WeatherCacheEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCityName = CursorUtil.getColumnIndexOrThrow(_cursor, "cityName");
          final int _cursorIndexOfTempCurrent = CursorUtil.getColumnIndexOrThrow(_cursor, "tempCurrent");
          final int _cursorIndexOfTempMin = CursorUtil.getColumnIndexOrThrow(_cursor, "tempMin");
          final int _cursorIndexOfTempMax = CursorUtil.getColumnIndexOrThrow(_cursor, "tempMax");
          final int _cursorIndexOfFeelsLike = CursorUtil.getColumnIndexOrThrow(_cursor, "feelsLike");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIconCode = CursorUtil.getColumnIndexOrThrow(_cursor, "iconCode");
          final int _cursorIndexOfHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "humidity");
          final int _cursorIndexOfWindSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "windSpeed");
          final int _cursorIndexOfWindDeg = CursorUtil.getColumnIndexOrThrow(_cursor, "windDeg");
          final int _cursorIndexOfPressure = CursorUtil.getColumnIndexOrThrow(_cursor, "pressure");
          final int _cursorIndexOfVisibility = CursorUtil.getColumnIndexOrThrow(_cursor, "visibility");
          final int _cursorIndexOfSunrise = CursorUtil.getColumnIndexOrThrow(_cursor, "sunrise");
          final int _cursorIndexOfSunset = CursorUtil.getColumnIndexOrThrow(_cursor, "sunset");
          final int _cursorIndexOfCountry = CursorUtil.getColumnIndexOrThrow(_cursor, "country");
          final int _cursorIndexOfCachedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "cachedAt");
          final WeatherCacheEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpCityName;
            _tmpCityName = _cursor.getString(_cursorIndexOfCityName);
            final double _tmpTempCurrent;
            _tmpTempCurrent = _cursor.getDouble(_cursorIndexOfTempCurrent);
            final double _tmpTempMin;
            _tmpTempMin = _cursor.getDouble(_cursorIndexOfTempMin);
            final double _tmpTempMax;
            _tmpTempMax = _cursor.getDouble(_cursorIndexOfTempMax);
            final double _tmpFeelsLike;
            _tmpFeelsLike = _cursor.getDouble(_cursorIndexOfFeelsLike);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpIconCode;
            _tmpIconCode = _cursor.getString(_cursorIndexOfIconCode);
            final int _tmpHumidity;
            _tmpHumidity = _cursor.getInt(_cursorIndexOfHumidity);
            final double _tmpWindSpeed;
            _tmpWindSpeed = _cursor.getDouble(_cursorIndexOfWindSpeed);
            final int _tmpWindDeg;
            _tmpWindDeg = _cursor.getInt(_cursorIndexOfWindDeg);
            final int _tmpPressure;
            _tmpPressure = _cursor.getInt(_cursorIndexOfPressure);
            final int _tmpVisibility;
            _tmpVisibility = _cursor.getInt(_cursorIndexOfVisibility);
            final long _tmpSunrise;
            _tmpSunrise = _cursor.getLong(_cursorIndexOfSunrise);
            final long _tmpSunset;
            _tmpSunset = _cursor.getLong(_cursorIndexOfSunset);
            final String _tmpCountry;
            _tmpCountry = _cursor.getString(_cursorIndexOfCountry);
            final long _tmpCachedAt;
            _tmpCachedAt = _cursor.getLong(_cursorIndexOfCachedAt);
            _result = new WeatherCacheEntity(_tmpCityName,_tmpTempCurrent,_tmpTempMin,_tmpTempMax,_tmpFeelsLike,_tmpDescription,_tmpIconCode,_tmpHumidity,_tmpWindSpeed,_tmpWindDeg,_tmpPressure,_tmpVisibility,_tmpSunrise,_tmpSunset,_tmpCountry,_tmpCachedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getWeather(final String city,
      final Continuation<? super WeatherCacheEntity> $completion) {
    final String _sql = "SELECT * FROM weather_cache WHERE cityName = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, city);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<WeatherCacheEntity>() {
      @Override
      @Nullable
      public WeatherCacheEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCityName = CursorUtil.getColumnIndexOrThrow(_cursor, "cityName");
          final int _cursorIndexOfTempCurrent = CursorUtil.getColumnIndexOrThrow(_cursor, "tempCurrent");
          final int _cursorIndexOfTempMin = CursorUtil.getColumnIndexOrThrow(_cursor, "tempMin");
          final int _cursorIndexOfTempMax = CursorUtil.getColumnIndexOrThrow(_cursor, "tempMax");
          final int _cursorIndexOfFeelsLike = CursorUtil.getColumnIndexOrThrow(_cursor, "feelsLike");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIconCode = CursorUtil.getColumnIndexOrThrow(_cursor, "iconCode");
          final int _cursorIndexOfHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "humidity");
          final int _cursorIndexOfWindSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "windSpeed");
          final int _cursorIndexOfWindDeg = CursorUtil.getColumnIndexOrThrow(_cursor, "windDeg");
          final int _cursorIndexOfPressure = CursorUtil.getColumnIndexOrThrow(_cursor, "pressure");
          final int _cursorIndexOfVisibility = CursorUtil.getColumnIndexOrThrow(_cursor, "visibility");
          final int _cursorIndexOfSunrise = CursorUtil.getColumnIndexOrThrow(_cursor, "sunrise");
          final int _cursorIndexOfSunset = CursorUtil.getColumnIndexOrThrow(_cursor, "sunset");
          final int _cursorIndexOfCountry = CursorUtil.getColumnIndexOrThrow(_cursor, "country");
          final int _cursorIndexOfCachedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "cachedAt");
          final WeatherCacheEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpCityName;
            _tmpCityName = _cursor.getString(_cursorIndexOfCityName);
            final double _tmpTempCurrent;
            _tmpTempCurrent = _cursor.getDouble(_cursorIndexOfTempCurrent);
            final double _tmpTempMin;
            _tmpTempMin = _cursor.getDouble(_cursorIndexOfTempMin);
            final double _tmpTempMax;
            _tmpTempMax = _cursor.getDouble(_cursorIndexOfTempMax);
            final double _tmpFeelsLike;
            _tmpFeelsLike = _cursor.getDouble(_cursorIndexOfFeelsLike);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpIconCode;
            _tmpIconCode = _cursor.getString(_cursorIndexOfIconCode);
            final int _tmpHumidity;
            _tmpHumidity = _cursor.getInt(_cursorIndexOfHumidity);
            final double _tmpWindSpeed;
            _tmpWindSpeed = _cursor.getDouble(_cursorIndexOfWindSpeed);
            final int _tmpWindDeg;
            _tmpWindDeg = _cursor.getInt(_cursorIndexOfWindDeg);
            final int _tmpPressure;
            _tmpPressure = _cursor.getInt(_cursorIndexOfPressure);
            final int _tmpVisibility;
            _tmpVisibility = _cursor.getInt(_cursorIndexOfVisibility);
            final long _tmpSunrise;
            _tmpSunrise = _cursor.getLong(_cursorIndexOfSunrise);
            final long _tmpSunset;
            _tmpSunset = _cursor.getLong(_cursorIndexOfSunset);
            final String _tmpCountry;
            _tmpCountry = _cursor.getString(_cursorIndexOfCountry);
            final long _tmpCachedAt;
            _tmpCachedAt = _cursor.getLong(_cursorIndexOfCachedAt);
            _result = new WeatherCacheEntity(_tmpCityName,_tmpTempCurrent,_tmpTempMin,_tmpTempMax,_tmpFeelsLike,_tmpDescription,_tmpIconCode,_tmpHumidity,_tmpWindSpeed,_tmpWindDeg,_tmpPressure,_tmpVisibility,_tmpSunrise,_tmpSunset,_tmpCountry,_tmpCachedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<WeatherCacheEntity>> getAllCachedCities() {
    final String _sql = "SELECT * FROM weather_cache ORDER BY cachedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"weather_cache"}, new Callable<List<WeatherCacheEntity>>() {
      @Override
      @NonNull
      public List<WeatherCacheEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCityName = CursorUtil.getColumnIndexOrThrow(_cursor, "cityName");
          final int _cursorIndexOfTempCurrent = CursorUtil.getColumnIndexOrThrow(_cursor, "tempCurrent");
          final int _cursorIndexOfTempMin = CursorUtil.getColumnIndexOrThrow(_cursor, "tempMin");
          final int _cursorIndexOfTempMax = CursorUtil.getColumnIndexOrThrow(_cursor, "tempMax");
          final int _cursorIndexOfFeelsLike = CursorUtil.getColumnIndexOrThrow(_cursor, "feelsLike");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIconCode = CursorUtil.getColumnIndexOrThrow(_cursor, "iconCode");
          final int _cursorIndexOfHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "humidity");
          final int _cursorIndexOfWindSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "windSpeed");
          final int _cursorIndexOfWindDeg = CursorUtil.getColumnIndexOrThrow(_cursor, "windDeg");
          final int _cursorIndexOfPressure = CursorUtil.getColumnIndexOrThrow(_cursor, "pressure");
          final int _cursorIndexOfVisibility = CursorUtil.getColumnIndexOrThrow(_cursor, "visibility");
          final int _cursorIndexOfSunrise = CursorUtil.getColumnIndexOrThrow(_cursor, "sunrise");
          final int _cursorIndexOfSunset = CursorUtil.getColumnIndexOrThrow(_cursor, "sunset");
          final int _cursorIndexOfCountry = CursorUtil.getColumnIndexOrThrow(_cursor, "country");
          final int _cursorIndexOfCachedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "cachedAt");
          final List<WeatherCacheEntity> _result = new ArrayList<WeatherCacheEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WeatherCacheEntity _item;
            final String _tmpCityName;
            _tmpCityName = _cursor.getString(_cursorIndexOfCityName);
            final double _tmpTempCurrent;
            _tmpTempCurrent = _cursor.getDouble(_cursorIndexOfTempCurrent);
            final double _tmpTempMin;
            _tmpTempMin = _cursor.getDouble(_cursorIndexOfTempMin);
            final double _tmpTempMax;
            _tmpTempMax = _cursor.getDouble(_cursorIndexOfTempMax);
            final double _tmpFeelsLike;
            _tmpFeelsLike = _cursor.getDouble(_cursorIndexOfFeelsLike);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpIconCode;
            _tmpIconCode = _cursor.getString(_cursorIndexOfIconCode);
            final int _tmpHumidity;
            _tmpHumidity = _cursor.getInt(_cursorIndexOfHumidity);
            final double _tmpWindSpeed;
            _tmpWindSpeed = _cursor.getDouble(_cursorIndexOfWindSpeed);
            final int _tmpWindDeg;
            _tmpWindDeg = _cursor.getInt(_cursorIndexOfWindDeg);
            final int _tmpPressure;
            _tmpPressure = _cursor.getInt(_cursorIndexOfPressure);
            final int _tmpVisibility;
            _tmpVisibility = _cursor.getInt(_cursorIndexOfVisibility);
            final long _tmpSunrise;
            _tmpSunrise = _cursor.getLong(_cursorIndexOfSunrise);
            final long _tmpSunset;
            _tmpSunset = _cursor.getLong(_cursorIndexOfSunset);
            final String _tmpCountry;
            _tmpCountry = _cursor.getString(_cursorIndexOfCountry);
            final long _tmpCachedAt;
            _tmpCachedAt = _cursor.getLong(_cursorIndexOfCachedAt);
            _item = new WeatherCacheEntity(_tmpCityName,_tmpTempCurrent,_tmpTempMin,_tmpTempMax,_tmpFeelsLike,_tmpDescription,_tmpIconCode,_tmpHumidity,_tmpWindSpeed,_tmpWindDeg,_tmpPressure,_tmpVisibility,_tmpSunrise,_tmpSunset,_tmpCountry,_tmpCachedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
