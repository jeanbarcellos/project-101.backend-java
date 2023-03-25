SET client_encoding TO utf8;

-- ----------------------------
-- Structure of role
-- ----------------------------

CREATE TABLE "public"."role" (
    id uuid NOT NULL,
    created_at timestamp NOT NULL,
    description text NOT NULL,
    name varchar(255) NOT NULL,
    updated_at timestamp NOT NULL,
    CONSTRAINT role_pk PRIMARY KEY (id)
);

ALTER TABLE "public"."role"
    ADD CONSTRAINT role_name_uk UNIQUE (name);


-- ----------------------------
-- Structure of role_hierarchy
-- ----------------------------

CREATE TABLE "public"."role_hierarchy" (
    child_role_id uuid NOT NULL,
    parent_role_id uuid NOT NULL,
    CONSTRAINT role_hierarchy_pk PRIMARY KEY (parent_role_id, child_role_id)
);

ALTER TABLE "public"."role_hierarchy"
    ADD CONSTRAINT role_hierarchy_parent_role_id_fk FOREIGN KEY (parent_role_id)
    REFERENCES "public"."role";

ALTER TABLE "public"."role_hierarchy"
    ADD CONSTRAINT role_hierarchy_child_role_id_fk FOREIGN KEY (child_role_id)
    REFERENCES "public"."role";


-- ----------------------------
-- Structure of user
-- ----------------------------

CREATE TABLE "public"."user" (
    id uuid NOT NULL,
    created_at timestamp NOT NULL,
    email varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    status int4 NOT NULL,
    updated_at timestamp NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (id)
);

ALTER TABLE "public"."user"
    ADD CONSTRAINT user_email_uk UNIQUE (email);


-- ----------------------------
-- Structure of user_role
-- ----------------------------

CREATE TABLE "public"."user_role" (
    user_id uuid NOT NULL,
    role_id uuid NOT NULL,
    CONSTRAINT user_role_pk PRIMARY KEY (user_id, role_id)
);

ALTER TABLE "public"."user_role"
    ADD CONSTRAINT user_role_role_id_fk FOREIGN KEY (role_id)
    REFERENCES "public"."role";

ALTER TABLE "public"."user_role"
    ADD CONSTRAINT user_role_user_id_fk FOREIGN KEY (user_id)
    REFERENCES "public"."user";
