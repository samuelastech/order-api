CREATE TABLE orders (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    date_hour datetime NOT NULL,
    status varchar(255) NOT NULL,
    PRIMARY KEY (id)
);