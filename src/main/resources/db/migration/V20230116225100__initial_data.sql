SET client_encoding TO utf8;

-- ----------------------------
-- Records of role
-- ----------------------------

INSERT INTO "public"."role"("id", "description", "name", "created_at", "updated_at") VALUES ('93a879ab-fa9c-475e-8bb3-86d0bc4244a9', 'Perfil do desenvolvedor/empresa. gerentia TUDO, configurações, roles e utilitários.', 'root', '2022-02-20 15:56:37.939407', '2022-02-20 15:56:37.939407');
INSERT INTO "public"."role"("id", "description", "name", "created_at", "updated_at") VALUES ('fce7f4b5-a5a7-4ac3-bcb2-c7a270e8698e', 'Inclui, exclui, atualiza e visualiza usuários, perfils e outras funções administrativas', 'admin', '2022-02-20 15:56:46.011322', '2022-02-20 15:56:46.011322');
INSERT INTO "public"."role"("id", "description", "name", "created_at", "updated_at") VALUES ('983e702d-51ed-41c4-8f05-ee3fba2cdc26', 'Usuário padrão utilizador do sistema', 'default', '2022-02-20 15:56:46.425343', '2022-02-20 15:56:46.425343');


-- ----------------------------
-- Records of role_hierarchy
-- ----------------------------

-- Herança
INSERT INTO "public"."role_hierarchy"("parent_role_id", "child_role_id") VALUES ('93a879ab-fa9c-475e-8bb3-86d0bc4244a9', 'fce7f4b5-a5a7-4ac3-bcb2-c7a270e8698e');
INSERT INTO "public"."role_hierarchy"("parent_role_id", "child_role_id") VALUES ('fce7f4b5-a5a7-4ac3-bcb2-c7a270e8698e', '983e702d-51ed-41c4-8f05-ee3fba2cdc26');
-- Definição em geral
-- INSERT INTO "public"."role_hierarchy"("parent_role_id", "child_role_id") VALUES ('93a879ab-fa9c-475e-8bb3-86d0bc4244a9', 'fce7f4b5-a5a7-4ac3-bcb2-c7a270e8698e');
-- INSERT INTO "public"."role_hierarchy"("parent_role_id", "child_role_id") VALUES ('93a879ab-fa9c-475e-8bb3-86d0bc4244a9', '983e702d-51ed-41c4-8f05-ee3fba2cdc26');


-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO "public"."user"("id", "email", "name", "password", "status", "created_at", "updated_at") VALUES ('824ca30a-a80b-4829-8af5-333b35c177b1', 'root@teste.com', 'Root/Programador', '$2a$10$rIw0H6cXzblKeHJuf9usfuNKDCIFrr2nFszv2uRI6QAhhKX6dyW5G', 1, '2022-02-20 15:57:47.840855', '2022-02-20 15:57:47.840855');
INSERT INTO "public"."user"("id", "email", "name", "password", "status", "created_at", "updated_at") VALUES ('d61ef95b-ff40-4c25-93d5-5caaae732494', 'admin001@teste.com', 'Administrador 001', '$2a$10$rIw0H6cXzblKeHJuf9usfuNKDCIFrr2nFszv2uRI6QAhhKX6dyW5G', 1, '2022-02-20 15:57:47.842465', '2022-02-20 15:57:47.842465');
INSERT INTO "public"."user"("id", "email", "name", "password", "status", "created_at", "updated_at") VALUES ('03324f77-322d-452a-8e84-5ad48c68a85f', 'admin002@teste.com', 'Administrador 002', '$2a$10$rIw0H6cXzblKeHJuf9usfuNKDCIFrr2nFszv2uRI6QAhhKX6dyW5G', 1, '2022-02-20 15:57:47.8437', '2022-02-20 15:57:47.8437');
INSERT INTO "public"."user"("id", "email", "name", "password", "status", "created_at", "updated_at") VALUES ('5770f2a0-0cce-48a9-8ccb-a97c2871f13f', 'admin003@teste.com', 'Administrador 003', '$2a$10$rIw0H6cXzblKeHJuf9usfuNKDCIFrr2nFszv2uRI6QAhhKX6dyW5G', 1, '2022-02-20 15:57:47.845014', '2022-02-20 15:57:47.845014');
INSERT INTO "public"."user"("id", "email", "name", "password", "status", "created_at", "updated_at") VALUES ('d29a680d-1e69-4ee7-a38f-ecb7717de755', 'usuario001@teste.com', 'Usuário 001', '$2a$10$rIw0H6cXzblKeHJuf9usfuNKDCIFrr2nFszv2uRI6QAhhKX6dyW5G', 1, '2022-02-20 15:57:47.848832', '2022-02-20 15:57:47.848832');
INSERT INTO "public"."user"("id", "email", "name", "password", "status", "created_at", "updated_at") VALUES ('4cad4f4f-12af-472e-ad7c-9be8ec083eb7', 'usuario002@teste.com', 'Usuário 002', '$2a$10$rIw0H6cXzblKeHJuf9usfuNKDCIFrr2nFszv2uRI6QAhhKX6dyW5G', 1, '2022-02-20 15:57:47.851461', '2022-02-20 15:57:47.851461');
INSERT INTO "public"."user"("id", "email", "name", "password", "status", "created_at", "updated_at") VALUES ('056abfa1-c6ff-419a-afc9-d59b6947209a', 'usuario003@teste.com', 'Usuário 003', '$2a$10$rIw0H6cXzblKeHJuf9usfuNKDCIFrr2nFszv2uRI6QAhhKX6dyW5G', 1, '2022-02-20 15:57:47.852933', '2022-02-20 15:57:47.852933');




-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO "public"."user_role" ("user_id", "role_id") VALUES ('824ca30a-a80b-4829-8af5-333b35c177b1', '93a879ab-fa9c-475e-8bb3-86d0bc4244a9');
INSERT INTO "public"."user_role" ("user_id", "role_id") VALUES ('d61ef95b-ff40-4c25-93d5-5caaae732494', 'fce7f4b5-a5a7-4ac3-bcb2-c7a270e8698e');
INSERT INTO "public"."user_role" ("user_id", "role_id") VALUES ('03324f77-322d-452a-8e84-5ad48c68a85f', 'fce7f4b5-a5a7-4ac3-bcb2-c7a270e8698e');
INSERT INTO "public"."user_role" ("user_id", "role_id") VALUES ('5770f2a0-0cce-48a9-8ccb-a97c2871f13f', 'fce7f4b5-a5a7-4ac3-bcb2-c7a270e8698e');
INSERT INTO "public"."user_role" ("user_id", "role_id") VALUES ('4cad4f4f-12af-472e-ad7c-9be8ec083eb7', '983e702d-51ed-41c4-8f05-ee3fba2cdc26');
INSERT INTO "public"."user_role" ("user_id", "role_id") VALUES ('056abfa1-c6ff-419a-afc9-d59b6947209a', '983e702d-51ed-41c4-8f05-ee3fba2cdc26');
INSERT INTO "public"."user_role" ("user_id", "role_id") VALUES ('d29a680d-1e69-4ee7-a38f-ecb7717de755', '983e702d-51ed-41c4-8f05-ee3fba2cdc26');
