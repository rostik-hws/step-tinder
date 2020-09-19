create table if not exists users
(
	id serial not null
		constraint users_pk
			primary key,
	username varchar not null,
	password varchar not null,
	photo_url varchar,
	profession varchar not null,
	last_login date
);