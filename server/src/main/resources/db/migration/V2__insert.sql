INSERT INTO artist (id_artist, art_type, "name", price_per_hour, id_coworker) VALUES (nextval('artist_id_artist_seq'), 2, 'Leonardo da Vinci', 3000, null);
INSERT INTO artist (id_artist, art_type, "name", price_per_hour, id_coworker) VALUES (nextval('artist_id_artist_seq'), 2, 'Pablo Picasso', 2500, 1);
INSERT INTO artist (id_artist, art_type, "name", price_per_hour, id_coworker) VALUES (nextval('artist_id_artist_seq'), 2, 'Vincent van Gogh', 500, 2);
INSERT INTO artist (id_artist, art_type, "name", price_per_hour, id_coworker) VALUES (nextval('artist_id_artist_seq'), 7, 'Michelangelo', 800, null);
INSERT INTO artist (id_artist, art_type, "name", price_per_hour, id_coworker) VALUES (nextval('artist_id_artist_seq'), 1, 'Salvador Dalí', 800, 1);
INSERT INTO artist (id_artist, art_type, "name", price_per_hour, id_coworker) VALUES (nextval('artist_id_artist_seq'), 1, 'Claude Monet', 900, 5);
INSERT INTO artist (id_artist, art_type, "name", price_per_hour, id_coworker) VALUES (nextval('artist_id_artist_seq'), 6, 'Tyler Joseph', 1000, null);
INSERT INTO artist (id_artist, art_type, "name", price_per_hour, id_coworker) VALUES (nextval('artist_id_artist_seq'), 6, 'Brendon Urie', 800, 7);
INSERT INTO artist (id_artist, art_type, "name", price_per_hour, id_coworker) VALUES (nextval('artist_id_artist_seq'), 4, 'Wolfgang Mozart', 2000, null);
INSERT INTO artist (id_artist, art_type, "name", price_per_hour, id_coworker) VALUES (nextval('artist_id_artist_seq'), 4, 'Ludwig van Beethoven', 2200, 9);
INSERT INTO artist (id_artist, art_type, "name", price_per_hour, id_coworker) VALUES (nextval('artist_id_artist_seq'), 3, 'Joshua Brooks', 700, null);
INSERT INTO artist (id_artist, art_type, "name", price_per_hour, id_coworker) VALUES (nextval('artist_id_artist_seq'), 2, 'Bob Ross', 800, null);
INSERT INTO artist (id_artist, art_type, "name", price_per_hour, id_coworker) VALUES (nextval('artist_id_artist_seq'), 0, 'Christopher Straver', 400, null);

INSERT INTO customer (id_customer, "name") VALUES (nextval('customer_id_customer_seq'), 'Alice');
INSERT INTO customer (id_customer, "name") VALUES (nextval('customer_id_customer_seq'), 'Bob');
INSERT INTO customer (id_customer, "name") VALUES (nextval('customer_id_customer_seq'), 'John');

INSERT INTO commission (id_commission, art_type, description, estimated_hours, issuing_date, estimated_end_date, price, id_customer) VALUES (nextval('commission_id_commission_seq'), 2, 'Mountaintops in mist', 8, '2022-12-30', '2022-12-31', 20000, 1);
INSERT INTO commission (id_commission, art_type, description, estimated_hours, issuing_date, estimated_end_date, price, id_customer) VALUES (nextval('commission_id_commission_seq'), 2, 'Winter landscape', 8, '2022-12-03', '2022-12-04', 44000, 2);
INSERT INTO commission (id_commission, art_type, description, estimated_hours, issuing_date, estimated_end_date, price, id_customer) VALUES (nextval('commission_id_commission_seq'), 6, 'A Christmas song', 24, '2022-12-03', '2022-12-06', 43200, 2);
INSERT INTO commission (id_commission, art_type, description, estimated_hours, issuing_date, estimated_end_date, price, id_customer) VALUES (nextval('commission_id_commission_seq'), 2, 'Sunflowers', 12, '2022-12-03', '2022-12-05', 6000, 1);

INSERT INTO commission_artist (id_artist, id_commission) VALUES (2, 1);
INSERT INTO commission_artist (id_artist, id_commission) VALUES (1, 2);
INSERT INTO commission_artist (id_artist, id_commission) VALUES (2, 2);
INSERT INTO commission_artist (id_artist, id_commission) VALUES (7, 3);
INSERT INTO commission_artist (id_artist, id_commission) VALUES (8, 3);
INSERT INTO commission_artist (id_artist, id_commission) VALUES (3, 4);