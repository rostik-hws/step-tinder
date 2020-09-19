create table if not exists messages
(
	id serial not null
		constraint messages_pk
			primary key,
	from_id integer not null,
	to_id integer not null,
	content varchar not null,
	date date not null
);