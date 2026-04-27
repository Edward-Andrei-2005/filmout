-- insertar usuarios
INSERT INTO users (id, user_name, email, password) VALUES (1, 'Admin_Paco', 'paco@filmout.com', '1234');
INSERT INTO users (id, user_name, email, password) VALUES (2, 'Maria_Cinefila', 'maria@filmout.com', '1234');
INSERT INTO users (id, user_name, email, password) VALUES (3, 'Juan_Movies', 'juan@filmout.com', '1234');

-- insertar eventos
INSERT INTO events (id, description, date, max_attendees, location, movie_api_id, admin_id)
VALUES (1, 'Maratón de Terror Clásico', '2026-10-31 20:00:00', 20, 'Cine Capitol', 12345, 1);

INSERT INTO events (id, description, date, max_attendees, location, movie_api_id, admin_id)
VALUES (2, 'Estreno de Acción', '2026-11-15 18:30:00', 50, 'Cinesa Xanadú', 67890, 1);

-- insertar asistentes a traves de la tabla intermedia
INSERT INTO event_attendees (event_id, user_id) VALUES (1, 2);
INSERT INTO event_attendees (event_id, user_id) VALUES (1, 3);
INSERT INTO event_attendees (event_id, user_id) VALUES (2, 2);