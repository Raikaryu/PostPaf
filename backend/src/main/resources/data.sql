INSERT INTO "users" ("id", "pseudo", "email", "password", "bio", "image", "creationdate") VALUES
(1, 'pierre', 'pierre@example.com', 'securepassword1', 'Passionné de technologie et de développement.', 'https://raw.githubusercontent.com/Raikaryu/PostPaf/refs/heads/dev/frontend/public/assets/pierre.png', '2023-01-15 10:00:00'),
(2, 'floriane', 'floriane@example.com', 'securepassword2', 'Amoureuse de la nature et de la photographie.', 'https://raw.githubusercontent.com/Raikaryu/PostPaf/refs/heads/dev/frontend/public/assets/floriane.png', '2023-02-20 14:30:00'),
(3, 'sarra', 'sarra@example.com', 'securepassword3', 'Explorateur de nouvelles technologies.', 'https://raw.githubusercontent.com/Raikaryu/PostPaf/refs/heads/dev/frontend/public/assets/sarra.png', '2023-03-10 09:45:00');

INSERT INTO "post" ("id", "id_user", "title", "contenu", "creationdate") VALUES
(1, 1, 'Introduction à Spring Boot', 'Spring Boot est un framework puissant pour développer des applications Java.', '2023-01-16 11:00:00'),
(2, 2, 'Les Meilleures Pratiques en Photographie', 'Découvrez les astuces pour capturer des photos magnifiques.', '2023-02-21 15:30:00'),
(3, 3, 'Les Tendances Tech de 2023', 'Explorons les innovations technologiques de cette année.', '2023-03-11 10:45:00');