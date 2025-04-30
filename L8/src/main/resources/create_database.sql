CREATE TABLE continents (
    id INT PRIMARY KEY ,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE countries (
    id INT PRIMARY KEY ,
    name VARCHAR(100) NOT NULL ,
    code VARCHAR(10) NOT NULL ,
    continent_id INT NOT NULL ,
    FOREIGN KEY (continent_id) REFERENCES continents(id)
);

INSERT INTO continents(id,name) VALUES
    (1, 'Europe'),
    (2, 'North America'),
    (3, 'South America'),
    (4, 'Asia'),
    (5, 'Africa'),
    (6, 'Australia'),
    (7, 'Antarctica');

INSERT INTO countries(id, name, code, continent_id) VALUES
    (1, 'France', 'FR', 1),
    (2, 'United States of America', 'USA', 2),
    (3, 'Brazil', 'BR', 3),
    (4, 'Japan', 'JP', 4),
    (5, 'South Africa', 'ZA', 5),
    (6, 'Australia', 'AU', 6);