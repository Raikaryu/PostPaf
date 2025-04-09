DROP TABLE IF EXISTS "Post";

DROP TABLE IF EXISTS "Users";

CREATE TABLE "Post"(
    "id" BIGINT NOT NULL,
    "id_user" INTEGER NOT NULL,
    "title" VARCHAR(255) NOT NULL,
    "contenu" TEXT NOT NULL,
    "creationdate" TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL
);
ALTER TABLE
    "Post" ADD PRIMARY KEY("id");
CREATE TABLE "Users"(
    "id" INTEGER NOT NULL,
    "pseudo" VARCHAR(255) NOT NULL,
    "email" VARCHAR(255) NOT NULL,
    "password" VARCHAR(255) NOT NULL,
    "bio" TEXT,
    "image" VARCHAR(255),
    "creationdate" TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL
);
ALTER TABLE
    "Users" ADD PRIMARY KEY("id");
ALTER TABLE
    "Users" ADD CONSTRAINT "user_pseudo_unique" UNIQUE("pseudo");
ALTER TABLE
    "Users" ADD CONSTRAINT "user_email_unique" UNIQUE("email");
ALTER TABLE
    "Post" ADD CONSTRAINT "post_id_user_foreign" FOREIGN KEY("id_user") REFERENCES "Users"("id") ON DELETE CASCADE;