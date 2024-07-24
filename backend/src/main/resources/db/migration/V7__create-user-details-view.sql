CREATE VIEW VW_USER_DETAILS AS
    SELECT  u.id, u.name, u.email, u.phone, u.status, u.birth_dt, dep.name as department, func.name as function, manager.name as manager
    FROM TB_USER u
        LEFT JOIN TB_DEPARTMENT dep ON u.department_id = dep.id
        LEFT JOIN TB_FUNCTION func ON u.function_id = func.id
        LEFT JOIN TB_USER manager ON u.manager_id = manager.id;
