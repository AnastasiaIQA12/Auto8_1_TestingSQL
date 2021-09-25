# Домашнее задание к занятию «3.2. SQL»

**Важно**: задачи этого занятия не предполагают подключения к CI.

## Задача №1 - Скоро deadline

Случилось то, что обычно случается ближе к дедлайну: никто ничего не успевает и винит во всём остальных других. Разработчикам не особо до вас ("им ведь нужно пилить новые фичи"), поэтому они подготовили для вас сборку, работающую с СУБД и даже приложили схему БД (см. файл [schema.sql](schema.sql)), но при этом сказали "остальное вам нужно сделать самим, там не сложно" 😈.

Что нужно сделать:
1. Внимательно изучить схему
1. Создать Docker Container на базе MySQL 8 (прописать создание БД, пользователя, пароля)
1. Запустить SUT ([app-deadline.jar](app-deadline.jar)): для указания параметров подключения к БД можно использовать:
- либо переменные окружения `DB_URL`, `DB_USER`, `DB_PASS`
- либо указать их через флаги командной строки при запуске: `-P:jdbc.url=...`, `-P:jdbc.user=...`, `-P:jdbc.password=...`. Данное приложение не использует файл `application.properties` в качестве конфигурации, конфигурационный файл находится внутри jar архива.
- либо можете схитрить и попробовать подобрать значения, зашитые в саму SUT

А дальше выясняется куча забавных вещей 😈. 

### Проблема первая: SUT не стартует
SUT не создаёт самостоятельно таблицы в БД. Поэтому нужно сходить на сайт-описание Docker Image MySQL и посмотреть, как при инициализации скармливать схему.

### Проблема вторая: SUT валится при повторном перезапуске

SUT вставляет в БД демо-данные, а поскольку там есть ограничение уникальности, это приводит к ошибкам. Поэтому нужно где-то настроить вычистку данных за SUT.

### Проблема третья (опциональна): пароли

Если вы решите вдруг генерировать пользователей, чтобы под ними тестировать "Вход в приложение", то не должны удивляться тому, что в базе данных пароль пользователя хранится в зашифрованном виде. Попытка его записать туда в открытом виде ни к чему хорошему не приведёт. Настойчивые требования к разработчикам "раскрыть" алгоритм генерации пароля - ни к чему не привели. Что же делать? Если внимательно присмотреться к демо-данным, то они очень похожи на те, что были в одной из предыдущих задач. Значит можно попробовать использовать уже готовые "зашифрованные пароли", зная то, какие они были в незашифрованном виде.

Если вы добрались до этого шага и всё-таки успешно запустили SUT, то вы уже герой!

Но теперь выяснилась следующая забавная информация: разработчики фронтенда поругались с разработчиками бэкенда и можно протестировать только "Вход в систему". Внимательно посмотрите, как и куда сохраняются коды генерации в СУБД и напишите тест, который взяв информацию из БД о сгенерированном коде позволит протестировать "Вход в систему" через веб-интерфейс.

P.S. Неплохо бы ещё проверить, что при трёхкратном неверном вводе пароля система блокируется.

Итого в результате должно получиться:
1. docker-compose.yml
1. app-deadline.jar
1. schema.sql
1. код ваших авто-тестов


