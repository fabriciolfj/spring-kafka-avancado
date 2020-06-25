create table orders (
    id bigint not null auto_increment,
    code varchar(50) not null,
    location varchar(50) not null,
    date_time datetime not null,
    credit_card_number varchar(50) not null,
    primary key (id)
);

create table order_items (
  id bigint not null primary key auto_increment,
  name varchar(50) not null,
  price decimal(15,2) not null,
  quantity int not null,
  order_id bigint not null,
  constraint fk_order foreign key (order_id) references orders (id)
)