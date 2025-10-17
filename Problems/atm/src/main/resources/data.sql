-- To seed initial data during application startup.

INSERT INTO cash_inventory (denomination, count) VALUES (100, 100);
INSERT INTO cash_inventory (denomination, count) VALUES (200, 50);
INSERT INTO cash_inventory (denomination, count) VALUES (500, 15);
INSERT INTO cash_inventory (denomination, count) VALUES (2000, 25);


INSERT INTO cards (card_number, pin, active) VALUES ('1234-5678-9012-3456', '1234', true);
INSERT INTO cards (card_number, pin, active) VALUES ('1111-2222-3333-4444', '1111', true);