ALTER SEQUENCE author_id_seq RESTART WITH 1;

-- Сброс последовательности для книг
ALTER SEQUENCE book_id_seq RESTART WITH 1;

-- Сброс последовательности для библиотек
ALTER SEQUENCE library_id_seq RESTART WITH 1;

-- Вставляем авторов
INSERT INTO Author (fio, age) VALUES
                                  ('John Doe', 45),
                                  ('Jane Smith', 30),
                                  ('William Brown', 50),
                                  ('Emily Davis', 35);

-- Вставляем библиотеки
INSERT INTO Library (open, close) VALUES
                                      ('08:00:00', '20:00:00'),
                                      ('09:00:00', '18:00:00'),
                                      ('10:00:00', '19:00:00');

-- Вставляем книги
INSERT INTO Book (title, publication_date, author_id, pages, description, circulation, price, rating) VALUES
                                                                                                          ('Book One', '2020-01-01', 1, 200, 'A great book', 5000, 19.99, 4.5),
                                                                                                          ('Book Two', '2019-05-15', 2, 150, 'Another great book', 3000, 9.99, 4.2),
                                                                                                          ('Book Three', '2021-09-10', 3, 220, 'Best book ever', 10000, 25.00, 4.8),
                                                                                                          ('Book Four', '2018-07-25', 4, 180, 'Interesting book', 7000, 12.99, 3.9);

-- Вставляем книги в библиотеки
INSERT INTO book_in_library (book_id, library_id, is_available) VALUES
                                                                    (1, 1, true),
                                                                    (1, 2, false),
                                                                    (2, 2, true),
                                                                    (3, 1, true),
                                                                    (3, 3, false),
                                                                    (4, 3, true);
