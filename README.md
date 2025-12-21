# Fitness+ App

Приложение для трекинга тренировок и питания.

## Запуск

1. Открыть проект в Android Studio
2. Дождаться синхронизации Gradle
3. Запустить на эмуляторе (API 24+)

Если падает при запуске:
```bash
adb shell pm clear com.example.fitnessplusapp
```

## Структура

Приложение состоит из 4 модулей:

### Auth
Авторизация через SharedPreferences. Юзеры хранятся в памяти (Map), текущий пользователь в SharedPreferences.

### Workout
Список тренировок с возможностью добавления/удаления. Данные в Room БД:
- Название, категория, длительность
- Калории, интенсивность
- Заметки, подходы, повторения

### Nutrition  
Трекинг еды и калорий. Работает аналогично тренировкам через Room.

### Progress
Статистика с графиками (MPAndroidChart):
- LineChart - калории за неделю
- PieChart - распределение по категориям
- BarChart - средняя длительность

## Архитектура

MVVM с использованием:
- **ViewModel** - логика и состояние UI
- **Repository** - работа с данными (Room, SharedPreferences)
- **Hilt** - dependency injection
- **Coroutines** - асинхронные операции
- **LiveData** - реактивность UI

## База данных

Room с миграциями:
```kotlin
@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: String,
    val duration: Int,
    val caloriesBurned: Int,
    val date: Long,
    val notes: String = "",
    val intensity: String = "Medium",
    val sets: Int = 0,
    val reps: Int = 0,
    val completed: Boolean = true
)
```

## Основные зависимости

```gradle
// Room
implementation("androidx.room:room-runtime:2.8.4")
ksp("androidx.room:room-compiler:2.8.4")

// Hilt DI
implementation("com.google.dagger:hilt-android:2.53")
ksp("com.google.dagger:hilt-compiler:2.53")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

// Charts
implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

// Network
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.okhttp3:okhttp:4.12.0")
```

## Особенности

- Все данные хранятся локально (Room + SharedPreferences)
- Работает без интернета
- Navigation Component для навигации между модулями
- Material Design компоненты

