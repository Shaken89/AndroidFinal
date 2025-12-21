# Fitness+ App

Приложение для трекинга тренировок и питания.

## Запуск

1. Открыть в Android Studio
2. Запустить на эмуляторе

Если падает:
```bash
adb shell pm clear com.example.fitnessplusapp
```

## Что есть

- Workout - тренировки
- Nutrition - питание  
- Progress - статистика
- Auth - авторизация

## Технологии

- Room для БД
- MVVM + Hilt
- Coroutines
- MPAndroidChart для графиков
- Navigation Component

## Зависимости

```gradle
implementation("androidx.room:room-runtime:2.8.4")
implementation("com.google.dagger:hilt-android:2.53")
implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
```

