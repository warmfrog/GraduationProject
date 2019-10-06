create table book
(
  id           int            not null,
  alt          varchar(50)    null,
  alt_title    varchar(100)   null,
  author       varchar(50)    null,
  author_intro varchar(255)   null,
  binding      varchar(255)   null,
  catalog      varchar(2000)  null,
  image        varchar(150)   null,
  isbn10       varchar(10)    null,
  isbn13       varchar(13)    null,
  origin_title varchar(100)   null,
  pages        int            null,
  price        decimal(19, 2) null,
  pubdate      datetime       null,
  publisher    varchar(50)    null,
  subtitle     varchar(50)    null,
  summary      varchar(500)   null,
  title        varchar(50)    null,
  translator   varchar(255)   null,
  url          varchar(150)   null,
  book_id      int            null,
  ubook_id     int            not null
    primary key
)
  engine = MyISAM;

create index FKtbuxfrmbsmf088aemmouo12u0
  on book (book_id);

create table book_images
(
  images_id int null,
  book_id   int not null
    primary key
)
  engine = MyISAM;

create index FKsdcjflp819jdk1hd9s1vnl0l4
  on book_images (images_id);

create table book_rating
(
  tag_id  int null,
  book_id int not null
    primary key
)
  engine = MyISAM;

create index FK4i42mk8gwrx5890mm3v9un4fq
  on book_rating (tag_id);

create table book_series
(
  series_id int null,
  book_id   int not null
    primary key
)
  engine = MyISAM;

create index FKfe8pbrjx4fmad2668r5k88744
  on book_series (series_id);

create table book_tag
(
  book_id int not null,
  tag_id  int not null
)
  engine = MyISAM;

create index FKdrc33u5ufw8rdvajeveowgx7g
  on book_tag (book_id);

create index FKrxw4xl05l6ns1763bq284e7m2
  on book_tag (tag_id);

create table hibernate_sequence
(
  next_val bigint null
)
  engine = MyISAM;

create table images
(
  id     int          not null
    primary key,
  large  varchar(255) null,
  medium varchar(255) null,
  small  varchar(255) null
)
  engine = MyISAM;

create table `order`
(
  id     int          not null
    primary key,
  status varchar(255) null
)
  engine = MyISAM;

create table order_customer
(
  customer_id int null,
  order_id    int not null
    primary key
)
  engine = MyISAM;

create index FK5xm4hq83d8niuj9rwutqt1a26
  on order_customer (customer_id);

create table order_ubook
(
  ubook_id int null,
  order_id int not null
    primary key
)
  engine = MyISAM;

create index FK4rs8omws5nhnblebt6ukp19pr
  on order_ubook (ubook_id);

create table payment
(
  id       int            not null
    primary key,
  amount   decimal(19, 2) null,
  pay_time datetime       null
)
  engine = MyISAM;

create table payment_order
(
  order_id   int null,
  payment_id int not null
    primary key
)
  engine = MyISAM;

create index FK3fjgxvncxjqk2fgdff6dm5asy
  on payment_order (order_id);

create table rating
(
  id         int          not null
    primary key,
  average    varchar(255) null,
  max        int          null,
  min        int          null,
  num_raters int          null
)
  engine = MyISAM;

create table `release`
(
  id           int      not null
    primary key,
  release_time datetime null
)
  engine = MyISAM;

create table release_ubook
(
  release_id int not null,
  ubook_id   int not null,
  constraint UK_6wt6t773fpa7mwgl32yqn3aoc
    unique (ubook_id)
)
  engine = MyISAM;

create index FKqila48mn4434nym4d8hn4aabl
  on release_ubook (release_id);

create table role
(
  id   int          not null
    primary key,
  name varchar(255) not null,
  constraint UK_8sewwnpamngi6b1dwaa88askk
    unique (name)
)
  engine = MyISAM;

create table series
(
  id    int         not null
    primary key,
  title varchar(50) null
)
  engine = MyISAM;

create table tag
(
  id    int         not null
    primary key,
  count bigint      not null,
  name  varchar(50) null,
  title varchar(50) null
)
  engine = MyISAM;

create table ubook
(
  id         int            not null
    primary key,
  rent_price decimal(19, 2) null,
  sell_price decimal(19, 2) null,
  type       varchar(255)   null
)
  engine = MyISAM;

create table user
(
  id          int          not null
    primary key,
  create_date datetime     null,
  email       varchar(255) null,
  enabled     bit          null,
  password    varchar(255) null,
  phone       varchar(255) null,
  username    varchar(255) null,
  role_id     int          null
)
  engine = MyISAM;

create index FKn82ha3ccdebhokx3a8fgdqeyy
  on user (role_id);

create table user_book
(
  user_id int not null,
  book_id int not null,
  constraint UK_953y2aqv7204gbduoa7tc5n9c
    unique (book_id)
)
  engine = MyISAM;

create index FKbc0bwdnndnxhct38sinbem0n0
  on user_book (user_id);

create table user_orders
(
  user_id  int not null,
  order_id int not null
)
  engine = MyISAM;

create index FKkuspr37yv513ga1okogyxrb7m
  on user_orders (user_id);

create index FKp2oo8w32cbx1yenu3u902xs9s
  on user_orders (order_id);

create table user_release
(
  user_id    int not null,
  release_id int not null,
  constraint UK_1bgyesf8c3rhe9kc7obpwper3
    unique (release_id)
)
  engine = MyISAM;

create index FKmqcoledyo7x2m6q3lsyw0gcwn
  on user_release (user_id);

create table user_role
(
  user_id int not null,
  role_id int not null
)
  engine = MyISAM;

create index FK859n2jvi8ivhui0rl0esws6o
  on user_role (user_id);

create index FKa68196081fvovjhkek5m97n3y
  on user_role (role_id);


