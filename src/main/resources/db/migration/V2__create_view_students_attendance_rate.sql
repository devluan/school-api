CREATE VIEW vw_students_attendance_rate AS
    SELECT
        s.id, s.name, s.birth_date, s.classroom_id,
        COALESCE(
                COUNT(CASE WHEN a.status <> 'ABSENT' THEN 1 END) * 100.0
                    / NULLIF(COUNT(a.id), 0),
                0
        ) AS attendance_rate
    FROM tb_students s
             LEFT JOIN tb_attendances a
                       ON s.id = a.student_id
    GROUP BY s.id;