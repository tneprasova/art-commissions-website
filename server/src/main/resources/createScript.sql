-- This script serves only for manual reinitialization of the database
-- The artist table was designed using the code from the following website
-- https://www.codeproject.com/Tips/5255964/How-to-Create-and-Use-a-Self-referencing-Hierarchi

-- Remove conflicting tables
DROP TABLE IF EXISTS commission_artist;
DROP TABLE IF EXISTS commission;
DROP TABLE IF EXISTS artist;
DROP TABLE IF EXISTS customer;
-- End of removing

CREATE TABLE artist (
                        id_artist BIGINT IDENTITY(1,1) NOT NULL,
                        id_coworker BIGINT NULL,
                        name VARCHAR(256) NOT NULL,
                        price_per_hour INTEGER NOT NULL,
                        art_type INTEGER NOT NULL,

                        CONSTRAINT [pk_artist] PRIMARY KEY CLUSTERED
                            (
                            [id_artist] ASC
                            )WITH (PAD_INDEX = OFF,
                            STATISTICS_NORECOMPUTE = OFF,
                            IGNORE_DUP_KEY = OFF,
                            ALLOW_ROW_LOCKS = ON,
                            ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
    GO

CREATE NONCLUSTERED INDEX [IX_artist_id_coworker] ON artist
    (
        [id_coworker] ASC
    )WITH (PAD_INDEX = OFF,
        STATISTICS_NORECOMPUTE = OFF,
        SORT_IN_TEMPDB = OFF,
        DROP_EXISTING = OFF,
        ONLINE = OFF,
        ALLOW_ROW_LOCKS = ON,
        ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO

CREATE TABLE commission (
                            id_commission BIGINT IDENTITY(1,1) NOT NULL,
                            id_customer BIGINT NOT NULL,
                            art_type INTEGER NOT NULL,
                            description VARCHAR(1024) NOT NULL,
                            estimated_hours INTEGER NOT NULL,
                            issuing_date DATE NOT NULL,
                            estimated_end_date DATE NOT NULL,
                            price INTEGER NOT NULL
);
ALTER TABLE commission ADD CONSTRAINT pk_commission PRIMARY KEY (id_commission);

CREATE TABLE customer (
                          id_customer BIGINT IDENTITY(1,1) NOT NULL,
                          name VARCHAR(256) NOT NULL
);
ALTER TABLE customer ADD CONSTRAINT pk_customer PRIMARY KEY (id_customer);

CREATE TABLE commission_artist (
                                   id_commission BIGINT NOT NULL,
                                   id_artist BIGINT NOT NULL
);
ALTER TABLE commission_artist ADD CONSTRAINT pk_commission_artist PRIMARY KEY (id_commission, id_artist);

ALTER TABLE commission ADD CONSTRAINT fk_commission_customer FOREIGN KEY (id_customer) REFERENCES customer (id_customer) ON DELETE CASCADE;

ALTER TABLE commission_artist ADD CONSTRAINT fk_commission_artist_commission FOREIGN KEY (id_commission) REFERENCES commission (id_commission) ON DELETE CASCADE;
ALTER TABLE commission_artist ADD CONSTRAINT fk_commission_artist_artist FOREIGN KEY (id_artist) REFERENCES artist (id_artist) ON DELETE CASCADE;