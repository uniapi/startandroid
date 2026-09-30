DROP TABLE IF EXISTS position;
DROP TABLE IF EXISTS people;

-- 1. Создание таблицы должностей (position)
CREATE TABLE IF NOT EXISTS position (
    id INTEGER PRIMARY KEY,
    name TEXT,
    salary INTEGER
);

-- 2. Создание таблицы сотрудников (people)
CREATE TABLE IF NOT EXISTS people (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT,
    posid INTEGER
);

-- 3. Наполнение таблицы должностей
INSERT INTO position (id, name, salary) VALUES
(1, 'Директор', 15000),
(2, 'Программер', 13000),
(3, 'Бухгалтер', 10000),
(4, 'Охранник', 8000);

-- 4. Наполнение таблицы сотрудников
INSERT INTO people (name, posid) VALUES
('Иван', 2),
('Марья', 3),
('Петр', 2),
('Антон', 2),
('Даша', 3),
('Борис', 1),
('Костя', 2),
('Игорь', 4);

SELECT * FROM position;
SELECT * FROM people;

-- запрос (INNER JOIN из db.rawQuery, где salary > 12000)
SELECT PL.name as Name, PS.name as Position, salary as Salary
FROM people as PL
INNER JOIN position as PS
ON PL.posid = PS.id
WHERE salary > 12000;

-- запрос (INNER JOIN из db.query, где salary < 12000)
SELECT PL.name as Name, PS.name as Position, salary as Salary
FROM people as PL
INNER JOIN position as PS
ON PL.posid = PS.id
WHERE salary < 12000
