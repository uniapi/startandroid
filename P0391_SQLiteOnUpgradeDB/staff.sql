-- Начало транзакции (beginTransaction)
BEGIN TRANSACTION;

-- 1. Создание таблицы должностей и массовая вставка данных (одним запросом)
CREATE TABLE [position] (
    id INTEGER PRIMARY KEY,
    name TEXT,
    salary INTEGER
);

INSERT INTO [position] (id, name, salary) VALUES
(1, 'Директор', 15000),
(2, 'Программер', 13000),
(3, 'Бухгалтер', 10000),
(4, 'Охранник', 8000);

-- 2. Добавление колонки posid в существующую таблицу people
ALTER TABLE people ADD COLUMN posid INTEGER;

-- 3. Обновление id должностей на основе их текстовых названий (цикл it.update)
UPDATE people SET posid = 1 WHERE [position] = 'Директор';
UPDATE people SET posid = 2 WHERE [position] = 'Программер';
UPDATE people SET posid = 3 WHERE [position] = 'Бухгалтер';
UPDATE people SET posid = 4 WHERE [position] = 'Охранник';

-- 4. Создание временной таблицы и перенос всех данных
CREATE TEMPORARY TABLE people_tmp (
    id INTEGER, 
    name TEXT, 
    [position] TEXT, 
    posid INTEGER
);

INSERT INTO people_tmp SELECT id, name, [position], posid FROM people;

-- 5. Удаление старой таблицы people
DROP TABLE people;

-- 6. Создание новой таблицы people (уже без колонки [position])
CREATE TABLE people (
    id INTEGER PRIMARY KEY AUTOINCREMENT, 
    name TEXT, 
    posid INTEGER
);

-- 7. Возврат данных из временной таблицы в новую структуру
INSERT INTO people SELECT id, name, posid FROM people_tmp;

-- 8. Удаление временной таблицы
DROP TABLE people_tmp;

-- Фиксация транзакции (setTransactionSuccessful + endTransaction)
COMMIT;
