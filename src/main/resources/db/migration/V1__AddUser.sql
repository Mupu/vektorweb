CREATE TABLE user (
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR (16) NOT NULL,
  email VARCHAR (21) NOT NULL,
  confirmation_token VARCHAR (63) NOT NULL,
  reset_password_token VARCHAR (63) NOT NULL,
  PRIMARY KEY (id)
)