create table if not exists likes
(
	id serial not null
		constraint likes_pk
			primary key,
	logged_user_id integer not null,
	liked_user_id integer not null,
	action boolean not null
);