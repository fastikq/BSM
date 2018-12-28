
CREATE TABLE `users` (
                       `id` int(11) NOT NULL AUTO_INCREMENT,
                       `username` varchar(255) NOT NULL,
                       `password` varchar(255) NOT NULL,
                       `name` varchar(255) NOT NULL,
                       `email` varchar(255) NOT NULL,
                       PRIMARY KEY (`id`),
                       UNIQUE KEY `UK_sx468g52bpetvlad2j9y0lptc` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Table: users
CREATE TABLE users(
  id        INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name      VARCHAR(255) NOT NULL,
  login     VARCHAR(255) NOT NULL,
  password  VARCHAR(255) NOT NULL
);

-- Table: containers
CREATE TABLE containers(
  id                 INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  info               VARCHAR(255) NOT NULL,
  date_registration  DATE         NOT NULL
);

-- Table: access_to_containers
CREATE TABLE access_to_containers(
  id           INT AUTO_INCREMENT PRIMARY KEY,
  user_id      INT NOT NULL,
  container_id INT NOT NULL,

  FOREIGN KEY (user_id)      REFERENCES users(id),
  FOREIGN KEY (container_id) REFERENCES containers(id),

  UNIQUE (user_id, container_id)
);

-- Table: history
CREATE TABLE history(
  event_id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  container_id   INT          NOT NULL,
  event_date_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  event_text           VARCHAR(255) NOT NULL,

  FOREIGN KEY (container_id) REFERENCES containers(id)
);

-- Table: roles
CREATE TABLE roles(
  id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);

-- Table: user_roles
CREATE TABLE user_roles(
    user_id      INT NOT NULL,
    role_id      INT NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id),

    UNIQUE (user_id, role_id)
);




