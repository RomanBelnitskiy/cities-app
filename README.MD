# Гра "Міста"

![Основний екран](https://github.com/RomanBelnitskiy/cities-app/blob/task-10/src/main/resources/images/main-window.png?raw=true "Основний екран")

> ### Мета
>
> Мета створення цього додатку - допомогти вивчити назви всіх українських міст та провести час весело і з користю.

> ### Правила
> 
> В грі дуже прості правила. Перший гравець називає будь-яке місто, а другий гравець повинен назвати місто який починається з тієї букви, яка була останньою в першому слові. Наприклад: Київ - Вінниця. Потім хід переходить знову до першого гравця. Гра закінчується, коли гравець не зможе назвати місто, значить він програв.

> ### Запуск додатку
> 
> Запуск додатка відбувається викликом методу main з класу AppLauncher.

> ### Створення виконуваного jar файлу
> 
> 1. Для створення виконуваного jar файлу в IntelliJ IDEA, потрібно зайти в меню "File" -> "Project Structure" та в розділі "Project Settings" вибрати "Artifacts". 
> 
>    ![Структура проекту](https://github.com/RomanBelnitskiy/cities-app/blob/task-10/src/main/resources/images/project-structure.jpg?raw=true "Структура проекту")
> 
> 2. Далі потрібно натиснути кнопку "+" та вибрати "JAR" і вибрати "From modules with dependencies".
> 
>    ![Створити артефакт](https://github.com/RomanBelnitskiy/cities-app/blob/task-10/src/main/resources/images/create-artifact.jpg?raw=true "Створити артефакт")
> 
> 3. Виберіть як "Main Class" клас AppLauncher та натисніть "OK".
> 
>    ![Створити jar](https://github.com/RomanBelnitskiy/cities-app/blob/task-10/src/main/resources/images/create-jar-from-modules.jpg?raw=true "Створити jar")
> 
> 4. З'явиться наступний екран. В списку бібліотек мають бути JavaFX та jsoup. Натисніть "Apply" та "OK".
> 
>    ![Список бібліотек](https://github.com/RomanBelnitskiy/cities-app/blob/task-10/src/main/resources/images/libs.jpg?raw=true "Список бібліотек")
> 
> 5. Створиться директорія "META_INF" в директорії "src/main/resources/", яка міститиме Manifest.
> 
> 6. Щоб створити виконуваний jar файл, перейдіть в меню "Build" та виберіть "Build Artifacts".
> 
>    ![Меню Build Artifacts](https://github.com/RomanBelnitskiy/cities-app/blob/task-10/src/main/resources/images/build-artifacts.jpg?raw=true "Меню Build Artifacts")
> 
> 7. Виберіть артефакт та натисніть "Build".
> 
>    ![Створити артефакт](https://github.com/RomanBelnitskiy/cities-app/blob/task-10/src/main/resources/images/build-artifact.jpg?raw=true "Створити артефакт")
> 
> 8. Буде створено jar файл в директорії out/artifacts/cities_app/cities-app.jar.
> 
>    ![Jar файл](https://github.com/RomanBelnitskiy/cities-app/blob/task-10/src/main/resources/images/cities-app-jar.jpg?raw=true "Jar файл")
> 
> 9. Цей файл можна запускати командою: `java —jar cities-app.jar`