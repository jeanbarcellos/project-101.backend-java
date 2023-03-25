SET client_encoding TO utf8;

-- ----------------------------
-- Structure of category
-- ----------------------------

CREATE TABLE "public"."category" (
    id uuid NOT NULL,
    created_at timestamp NOT NULL,
    name varchar(255) NOT NULL,
    updated_at timestamp NOT NULL,
    CONSTRAINT category_pk PRIMARY KEY (id)
);


-- ----------------------------
-- Records of category
-- ----------------------------

INSERT INTO "public"."category" ("id", "name", "created_at", "updated_at") VALUES ('06867776-b52c-4a85-8815-14ee1552a559', 'Camisetas', '2022-02-14 08:05:56.281231', '2022-02-14 08:05:56.281231');
INSERT INTO "public"."category" ("id", "name", "created_at", "updated_at") VALUES ('18e5b82c-6c4c-42ef-ab9f-5f142ecd663e', 'Canecas', '2022-02-14 08:05:56.292126', '2022-02-14 08:05:56.292126');
INSERT INTO "public"."category" ("id", "name", "created_at", "updated_at") VALUES ('e55d2134-b9ae-4921-bf05-b084e29248e1', 'Adeviso', '2022-02-14 08:05:56.293571', '2022-02-14 08:05:56.293571');
