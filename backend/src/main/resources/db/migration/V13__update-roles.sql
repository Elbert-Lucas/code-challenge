INSERT INTO TB_ROLE(role, description) VALUES
('SUPER_ADMIN', 'Usuário com permissões de um admin e de mudança de role de usuarios'),
('OWNER', 'Usuário com todas as permissões');

UPDATE tb_role SET description='Usuário com permissão de criar usuarios e notificações'	WHERE tb_role.role='ADMIN';