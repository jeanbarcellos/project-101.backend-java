SET client_encoding TO utf8;

-- ----------------------------
-- Structure of category
-- ----------------------------

CREATE TABLE "public"."product" (
  "id" uuid NOT NULL,
  "category_id" uuid NOT NULL,
  "name" varchar(128) NOT NULL,
  "description" varchar(255) NOT NULL,
  "active" bool NOT NULL,
  "value" numeric NOT NULL,
  "quantity" int4 NOT NULL,
  "image" text,
  "created_at" timestamp NOT NULL,
  "updated_at" timestamp NOT NULL
)
;

ALTER TABLE "public"."product"
    ADD CONSTRAINT "product_pk" PRIMARY KEY ("id");

ALTER TABLE "public"."product"
    ADD CONSTRAINT "product_category_id_fk" FOREIGN KEY ("category_id")
    REFERENCES "public"."category" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;


-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO "public"."product" VALUES ('55c0eb46-8eee-441d-afc6-a8ba3d2881df', '18e5b82c-6c4c-42ef-ab9f-5f142ecd663e', 'Caneca Start Bugs', 'Descrição', 't', 25, 100, 'caneca1.jpg', '2021-02-27 17:10:21.116423', '2021-02-27 17:10:21.116423');
INSERT INTO "public"."product" VALUES ('ac6deb27-e7c9-4169-99f9-787b28283573', '18e5b82c-6c4c-42ef-ab9f-5f142ecd663e', 'Caneca Programmer Code', 'Descrição', 't', 15, 100, 'caneca2.jpg', '2021-02-27 17:14:35.909087', '2021-02-27 17:14:35.909087');
INSERT INTO "public"."product" VALUES ('0a988a84-3a2e-41bb-b8d7-7fb16a48cad0', '18e5b82c-6c4c-42ef-ab9f-5f142ecd663e', 'Caneca Turn Coffe In Code', 'Descrição', 't', 15, 100, 'caneca3.jpg', '2021-02-27 17:10:21.116423', '2021-02-27 17:10:21.116423');
INSERT INTO "public"."product" VALUES ('399a4305-1172-433a-891b-e0ac3857cde0', '18e5b82c-6c4c-42ef-ab9f-5f142ecd663e', 'Caneca No Coffe No Code', 'Descrição', 't', 30, 100, 'caneca4.jpg', '2021-02-27 17:10:21.116423', '2021-02-27 17:10:21.116423');
INSERT INTO "public"."product" VALUES ('06867776-b52c-4a85-8815-14ee1552a559', '06867776-b52c-4a85-8815-14ee1552a559', 'Camiseta Sofware Developer', 'Descrição', 't', 100, 100, 'camiseta1.jpg', '2021-02-27 17:10:21.116423', '2021-02-27 17:10:21.116423');
INSERT INTO "public"."product" VALUES ('c498acc1-b622-4507-b3ff-64084325635e', '06867776-b52c-4a85-8815-14ee1552a559', 'Camiseta Code Life Preta', 'Descrição', 't', 90, 100, 'camiseta2.jpg', '2021-02-27 00:00:00', '2021-02-27 00:00:00');
INSERT INTO "public"."product" VALUES ('09c0e6b1-4763-4c77-bb92-ee3f68f8995e', '06867776-b52c-4a85-8815-14ee1552a559', 'Camiseta Code Life Cinza', 'Descrição', 't', 80, 100, 'camiseta3.jpg', '2021-02-27 17:08:52.913199', '2021-02-27 17:08:52.913199');
INSERT INTO "public"."product" VALUES ('82e966fe-806e-4395-97af-20bcf4f31733', '06867776-b52c-4a85-8815-14ee1552a559', 'Camiseta Debugar Preta', 'Descrição', 't', 110, 100, 'camiseta4.jpg', '2021-02-27 00:00:00', '2021-02-27 00:00:00');