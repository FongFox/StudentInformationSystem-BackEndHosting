-- -- 1. Thêm semester mẫu
-- INSERT INTO semester (code, year, short_description)
-- VALUES
--   (2431, 2024, 'Học kỳ 1 năm 2024');

-- -- 2. Thêm students mẫu
-- INSERT INTO students (code, full_name, user_name, password)
-- VALUES
--   (22002575, 'Trần Gia Nguyên Phong',   'phong.tgn02575', '123456'),
--   (22002581, 'Võ Thị Kim Ngân',          'ngan.vtkn02581','123456'),
--   (22002576, 'Nguyễn Văn An',            'an.nva02576',  '123456');

-- -- 3. Thêm 4 khóa course mẫu
-- INSERT INTO courses (code, name, credit, price)
-- VALUES
--   ('PHI 117DV01','Triết học trong Cuộc sống',      3, 4464000),
--   ('SW 210DE01','Công nghệ Phần mềm',             3, 6883000),
--   ('SW 306DV01','Phát triển Web Front-End',       3, 5653000),
--   ('SW 310DV01','Phát triển ứng dụng trên TB di động', 3, 6218000);

-- -- 4. Gán semester 2431 cho 4 khóa mẫu
-- UPDATE courses
-- SET semester_id = (SELECT id FROM semester WHERE code = 2431)
-- WHERE code IN (
--   'PHI 117DV01',
--   'SW 210DE01',
--   'SW 306DV01',
--   'SW 310DV01'
-- );

-- -- 5. Enroll: nhân bản 4 course mẫu cho mỗi sinh viên
-- INSERT INTO courses (code, name, credit, price, semester_id, student_id)
-- SELECT
--   c.code,
--   c.name,
--   c.credit,
--   c.price,
--   c.semester_id,
--   s.id
-- FROM courses c
-- CROSS JOIN (
--   SELECT id FROM students
--   WHERE code IN (22002575, 22002581, 22002576)
-- ) s
-- WHERE c.student_id IS NULL
--   AND c.code IN (
--     'PHI 117DV01',
--     'SW 210DE01',
--     'SW 306DV01',
--     'SW 310DV01'
--   );

-- -- 6. Thêm điểm ngẫu nhiên (0.0–10.0, làm tròn 1 chữ số) cho tất cả enrollment
-- UPDATE courses
-- SET grade = round(random() * 100)::int / 10.0
-- WHERE student_id IS NOT NULL;

-- BEGIN;
-- -- 1. Thêm semester 2431 nếu chưa có
-- INSERT INTO semester (code, year, short_description)
-- SELECT 2431, 2024, 'Học kỳ 1 năm 2024'
-- WHERE NOT EXISTS (SELECT 1 FROM semester WHERE code = 2431);

-- -- 2. Thêm 3 student mẫu nếu chưa có
-- INSERT INTO students (code, full_name, user_name, password)
-- SELECT *
-- FROM (VALUES
--   (22002575, 'Trần Gia Nguyên Phong', 'phong.tgn02575', '123456'),
--   (22002581, 'Võ Thị Kim Ngân',       'ngan.vtkn02581',   '123456'),
--   (22002576, 'Nguyễn Văn An',          'an.nva02576',      '123456')
-- ) AS v(code, full_name, user_name, password)
-- WHERE NOT EXISTS (SELECT 1 FROM students WHERE code = v.code);

-- -- 3. Thêm 12 course mẫu nếu chưa có
-- INSERT INTO courses (code, name, credit, price)
-- SELECT *
-- FROM (VALUES
--   ('KHTQ 105DV01', 'Toán Rời rạc',                                3, 4383000),
--   ('PSY 107DV01', 'Tâm lý học - Khái niệm và UD',                 3, 4464000),
--   ('DC 140DV01',  'Triết học Mác-Lênin',                          3, 3465000),
--   ('CN 103DV01',  'Mạng máy tính cơ sở',                         3, 5653000),
--   ('CN 104DV01',  'Hệ thống Máy tính',                           3, 5653000),
--   ('DC 144DV01',  'Lịch sử Đảng Cộng sản Việt Nam',               3, 2310000),
--   ('SW 103DV01',  'Lập trình Hướng đối tượng',                   3, 5653000),
--   ('SW 402DE01',  'Kiến trúc Phần mềm',                         3, 8260000),
--   ('PHI 117DV01', 'Triết học trong Cuộc sống',                   3, 4464000),
--   ('SW 210DE01',  'Công nghệ Phần mềm',                         3, 6883000),
--   ('SW 306DV01',  'Phát triển Web Front-End',                   3, 5653000),
--   ('SW 310DV01',  'Phát triển ứng dụng trên TB di động',        3, 6218000)
-- ) AS v(code, name, credit, price)
-- WHERE NOT EXISTS (
--   SELECT 1 FROM courses 
--   WHERE code = v.code AND student_id IS NULL
-- );

-- -- 4. Gán semester 2431 cho 12 course mẫu
-- UPDATE courses
-- SET semester_id = (SELECT id FROM semester WHERE code = 2431)
-- WHERE student_id IS NULL
--   AND code IN (
--     'KHTQ 105DV01','PSY 107DV01','DC 140DV01','CN 103DV01',
--     'CN 104DV01','DC 144DV01','SW 103DV01','SW 402DE01',
--     'PHI 117DV01','SW 210DE01','SW 306DV01','SW 310DV01'
--   );

-- -- 5. Enroll: với mỗi student, pick ngẫu nhiên 3–4 khóa template
-- WITH templates AS (
--   SELECT id, code, name, credit, price, semester_id
--   FROM courses
--   WHERE student_id IS NULL
--     AND semester_id = (SELECT id FROM semester WHERE code = 2431)
-- ), to_enroll AS (
--   SELECT 
--     s.id    AS student_id,
--     t.id    AS template_id
--   FROM students s
--   CROSS JOIN LATERAL (
--     SELECT id
--     FROM templates
--     ORDER BY random()
--     LIMIT (floor(random()*2)+3)::int   -- 3 hoặc 4
--   ) t
--   WHERE s.code IN (22002575, 22002581, 22002576)
-- )
-- INSERT INTO courses (code, name, credit, price, semester_id, student_id)
-- SELECT
--   temp.code,
--   temp.name,
--   temp.credit,
--   temp.price,
--   temp.semester_id,
--   e.student_id
-- FROM to_enroll e
-- JOIN templates temp ON temp.id = e.template_id;

-- -- 6. Thêm điểm random 1 chữ số thập phân cho tất cả enrollment
-- -- UPDATE courses
-- -- SET grade = round((random() * 10)::numeric, 1)
-- -- WHERE student_id IS NOT NULL
-- --   AND semester_id = (SELECT id FROM semester WHERE code = 2431);
-- UPDATE courses
-- SET grade = round((random() * 5 + 5)::numeric, 1)
-- WHERE student_id IS NOT NULL
--   AND semester_id = (
--     SELECT id FROM semester WHERE code = 2431
--   );

-- -- 7. Kiểm tra kết quả
-- SELECT
--   s.code        AS student_code,
--   s.full_name   AS student_name,
--   sem.code      AS semester_code,
--   c.code        AS course_code,
--   c.grade
-- FROM courses c
-- JOIN students s  ON c.student_id  = s.id
-- JOIN semester sem ON c.semester_id = sem.id
-- ORDER BY s.code, c.code;
-- COMMIT;

BEGIN;

-- 1. Thêm hai học kỳ nếu chưa có
INSERT INTO semester (code, year, short_description)
SELECT 2431, 2024, 'Học kỳ 1 năm 2024'
WHERE NOT EXISTS (SELECT 1 FROM semester WHERE code = 2431);

INSERT INTO semester (code, year, short_description)
SELECT 2333, 2023, 'Học kỳ 2 của 2023'
WHERE NOT EXISTS (SELECT 1 FROM semester WHERE code = 2333);

-- 2. Thêm 3 sinh viên mẫu nếu chưa có
INSERT INTO students (code, full_name, user_name, password)
SELECT *
FROM (VALUES
  (22002575, 'Trần Gia Nguyên Phong', 'phong.tgn02575', '123456'),
  (22002581, 'Võ Thị Kim Ngân',       'ngan.vtk02581',  '123456'),
  (22002576, 'Nguyễn Văn An',          'an.nv02576',     '123456')
) AS v(code, full_name, user_name, password)
WHERE NOT EXISTS (SELECT 1 FROM students WHERE code = v.code);

-- 3a. Chèn 12 khóa mẫu cho kỳ 2431
WITH base_courses(code, name, credit, price) AS (
  VALUES
    ('KHTQ 105DV01','Toán Rời rạc',                                3, 4383000),
    ('PSY 107DV01','Tâm lý học - Khái niệm và UD',                 3, 4464000),
    ('DC 140DV01', 'Triết học Mác-Lênin',                          3, 3465000),
    ('CN 103DV01', 'Mạng máy tính cơ sở',                         3, 5653000),
    ('CN 104DV01', 'Hệ thống Máy tính',                           3, 5653000),
    ('DC 144DV01', 'Lịch sử Đảng Cộng sản Việt Nam',               3, 2310000),
    ('SW 103DV01', 'Lập trình Hướng đối tượng',                   3, 5653000),
    ('SW 402DE01', 'Kiến trúc Phần mềm',                         3, 8260000),
    ('PHI 117DV01','Triết học trong Cuộc sống',                   3, 4464000),
    ('SW 210DE01', 'Công nghệ Phần mềm',                         3, 6883000),
    ('SW 306DV01', 'Phát triển Web Front-End',                   3, 5653000),
    ('SW 310DV01', 'Phát triển ứng dụng trên TB di động',        3, 6218000)
)
INSERT INTO courses (code, name, credit, price, semester_id)
SELECT
  bc.code,
  bc.name,
  bc.credit,
  bc.price,
  (SELECT id FROM semester WHERE code = 2431)
FROM base_courses bc
WHERE NOT EXISTS (
  SELECT 1 FROM courses c
  WHERE c.code = bc.code
    AND c.student_id IS NULL
    AND c.semester_id = (SELECT id FROM semester WHERE code = 2431)
);

-- 3b. Chèn 12 khóa mẫu cho kỳ 2333
WITH base_courses(code, name, credit, price) AS (
  VALUES
    ('KHTQ 105DV01','Toán Rời rạc',                                3, 4383000),
    ('PSY 107DV01','Tâm lý học - Khái niệm và UD',                 3, 4464000),
    ('DC 140DV01', 'Triết học Mác-Lênin',                          3, 3465000),
    ('CN 103DV01', 'Mạng máy tính cơ sở',                         3, 5653000),
    ('CN 104DV01', 'Hệ thống Máy tính',                           3, 5653000),
    ('DC 144DV01', 'Lịch sử Đảng Cộng sản Việt Nam',               3, 2310000),
    ('SW 103DV01', 'Lập trình Hướng đối tượng',                   3, 5653000),
    ('SW 402DE01', 'Kiến trúc Phần mềm',                         3, 8260000),
    ('PHI 117DV01','Triết học trong Cuộc sống',                   3, 4464000),
    ('SW 210DE01', 'Công nghệ Phần mềm',                         3, 6883000),
    ('SW 306DV01', 'Phát triển Web Front-End',                   3, 5653000),
    ('SW 310DV01', 'Phát triển ứng dụng trên TB di động',        3, 6218000)
)
INSERT INTO courses (code, name, credit, price, semester_id)
SELECT
  bc.code,
  bc.name,
  bc.credit,
  bc.price,
  (SELECT id FROM semester WHERE code = 2333)
FROM base_courses bc
WHERE NOT EXISTS (
  SELECT 1 FROM courses c
  WHERE c.code = bc.code
    AND c.student_id IS NULL
    AND c.semester_id = (SELECT id FROM semester WHERE code = 2333)
);

-- 4. Enroll ngẫu nhiên 3–4 khóa cho mỗi sinh viên ở kỳ 2431
WITH templates2431 AS (
  SELECT id, code, name, credit, price, semester_id
  FROM courses
  WHERE student_id IS NULL
    AND semester_id = (SELECT id FROM semester WHERE code = 2431)
), to_enroll2431 AS (
  SELECT
    s.id   AS student_id,
    t.id   AS template_id
  FROM students s
  CROSS JOIN LATERAL (
    SELECT id
    FROM templates2431
    ORDER BY random()
    LIMIT (floor(random()*2) + 3)::int
  ) t
  WHERE s.code IN (22002575, 22002581, 22002576)
)
INSERT INTO courses (code, name, credit, price, semester_id, student_id)
SELECT
  tpl.code,
  tpl.name,
  tpl.credit,
  tpl.price,
  tpl.semester_id,
  e.student_id
FROM to_enroll2431 e
JOIN templates2431 tpl ON tpl.id = e.template_id;

-- 5. Enroll ngẫu nhiên 3–4 khóa cho mỗi sinh viên ở kỳ 2333
WITH templates2333 AS (
  SELECT id, code, name, credit, price, semester_id
  FROM courses
  WHERE student_id IS NULL
    AND semester_id = (SELECT id FROM semester WHERE code = 2333)
), to_enroll2333 AS (
  SELECT
    s.id   AS student_id,
    t.id   AS template_id
  FROM students s
  CROSS JOIN LATERAL (
    SELECT id
    FROM templates2333
    ORDER BY random()
    LIMIT (floor(random()*2) + 3)::int
  ) t
  WHERE s.code IN (22002575, 22002581, 22002576)
)
INSERT INTO courses (code, name, credit, price, semester_id, student_id)
SELECT
  tpl.code,
  tpl.name,
  tpl.credit,
  tpl.price,
  tpl.semester_id,
  e.student_id
FROM to_enroll2333 e
JOIN templates2333 tpl ON tpl.id = e.template_id;

-- 6. Thêm điểm ngẫu nhiên từ 5.0 trở lên (1 chữ số thập phân) cho mọi enrollment ở cả hai kỳ
UPDATE courses
SET grade = round((random() * 5 + 5)::numeric, 1)
WHERE student_id IS NOT NULL
  AND semester_id IN (
    (SELECT id FROM semester WHERE code = 2431),
    (SELECT id FROM semester WHERE code = 2333)
  );

-- 7. Kiểm tra kết quả
SELECT
  s.code        AS student_code,
  s.full_name   AS student_name,
  sem.code      AS semester_code,
  c.code        AS course_code,
  c.grade
FROM courses c
JOIN students s  ON c.student_id  = s.id
JOIN semester sem ON c.semester_id = sem.id
ORDER BY s.code, c.code;
COMMIT;

