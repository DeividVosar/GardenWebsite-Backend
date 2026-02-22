create table maps (
  id bigserial primary key,
  name text not null,
  created_at timestamptz not null default now()
);

create table pins (
  id bigserial primary key,
  map_id bigint not null references maps(id) on delete cascade,
  x_percent double precision not null,
  y_percent double precision not null,
  name text,
  type text,
  last_watered timestamptz,
  watering_interval_days int,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now()
);

insert into maps(name) values ('Default garden');
