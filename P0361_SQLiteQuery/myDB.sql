
DROP TABLE IF EXISTS mytable;

-- 1. Создание таблицы (если она еще не создана)
CREATE TABLE IF NOT EXISTS mytable (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	name TEXT,
	people INTEGER,
	region TEXT
);

-- 2. Заполнение таблицы данными
INSERT INTO mytable (name, people, region) VALUES
('Китай', 1400, 'Азия'),
('США', 311, 'Америка'),
('Бразилия', 195, 'Америка'),
('Россия', 142, 'Европа'),
('Япония', 128, 'Азия'),
('Германия', 82, 'Европа'),
('Египет', 80, 'Африка'),
('Италия', 60, 'Европа'),
('Франция', 66, 'Европа'),
('Канада', 35, 'Америка');

--- Все записи ---
SELECT * FROM mytable;

--- Функция count(*) as Count ---
SELECT count(*) as Count FROM mytable;

--- Население больше 100 ---
SELECT * FROM mytable WHERE people > 100;

--- Население по региону ---
SELECT region, sum(people) as people FROM mytable GROUP BY region;

--- Регионы с населением больше 500 ---
SELECT region, sum(people) as people FROM mytable GROUP BY region HAVING sum(people) > 500;

--- Сортировка по населению ---
SELECT * FROM mytable ORDER BY people;
