INSERT INTO "users" ("id", "pseudo", "email", "password", "bio", "image", "creationdate") VALUES
(1, 'Seli', 'seli@example.com', 'securepassword1', 'Epouse de Léodagan.', 'https://raw.githubusercontent.com/Raikaryu/PostPaf/refs/heads/dev/frontend/public/assets/seli.jpg', '2023-01-15 10:00:00'),
(2, 'Arthur', 'arthour@example.com', 'securepassword2', 'Roi de Bretagne, ancien romain devenu chef des rois fédérés de Bretagne. Choisi par les dieux pour mener la quête du Graal.', 'https://raw.githubusercontent.com/Raikaryu/PostPaf/refs/heads/dev/frontend/public/assets/arthur.jpg', '2023-02-20 14:30:00'),
(3, 'Léodagan', 'dagan@example.com', 'securepassword3', 'Roi de Carmélide, beau-père d''Arthur et spécialiste de la fortification. Amateur de justice expéditive.', 'https://raw.githubusercontent.com/Raikaryu/PostPaf/refs/heads/dev/frontend/public/assets/leodagan.jpg', '2023-03-10 09:45:00');

INSERT INTO "post" ("id", "id_user", "title", "contenu", "creationdate") VALUES
(1, 1, 'Les tartes', 'Non mais je vais varier les fruits, vous inquiétez pas.', '2023-01-16 11:00:00'),
(2, 2, 'Les tartes', 'Et vous allez varier la pâte aussi ?.', '2023-01-16 11:02:00'),
(3, 1, 'Les tartes', 'Non mais n''exagérez pas non plus, je vous demande quand même pas de manger des briques.', '2023-01-16 11:03:00');