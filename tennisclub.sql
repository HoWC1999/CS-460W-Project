create table audit_logs
(
    log_id        int auto_increment
        primary key,
    event_time    datetime default CURRENT_TIMESTAMP null,
    event_details text                               null
);

create table courts
(
  court_number int                  not null
    primary key,
  is_available tinyint(1) default 1 null
);

create table criteria_filters
(
  criteria_id  int          not null,
  filter_value varchar(255) null,
  filter_key   varchar(255) not null,
  primary key (criteria_id, filter_key)
);

create table events
(
  event_id    int auto_increment
    primary key,
  title       varchar(100) not null,
  description text         null,
  event_date  date         not null,
  event_time  time         not null,
  location    varchar(255) null
);

create table financial_reports
(
  report_id    int auto_increment
    primary key,
  generated_on datetime default CURRENT_TIMESTAMP null,
  report_data  text                               null
);

create table reconciliations
(
  reconciliation_id int auto_increment
    primary key,
  discrepancies     text null
);

create table reconciliation_discrepancies
(
  reconciliation_id int          not null,
  discrepancy       varchar(255) null,
  constraint FKq0b0sadryk7n9wdsslumwiwcj
    foreign key (reconciliation_id) references reconciliations (reconciliation_id)
);

create table report_criteria
(
  criteria_id int auto_increment
    primary key,
  start_date  date null,
  end_date    date null,
  filters     text null,
  id          int  not null
);

create table users
(
  user_id       int auto_increment
    primary key,
  username      varchar(50)                  not null,
  password_hash varchar(255)                 not null,
  email         varchar(100)                 not null,
  role          varchar(20)                  not null,
  status        varchar(20) default 'Active' not null,
  phone_number  varchar(12)                  null,
  full_name     varchar(50)                  null,
  address       varchar(255)                 null,
  constraint email
    unique (email),
  constraint username
    unique (username),
  constraint users_pk
    unique (phone_number),
  constraint users_pk_2
    unique (full_name)
);

create table billing_transactions
(
  billing_id   int auto_increment
    primary key,
  user_id      int                                not null,
  amount       decimal(10, 2)                     not null,
  fee_type     varchar(50)                        not null,
  billing_date datetime default CURRENT_TIMESTAMP null,
  description  varchar(255)                       null,
  constraint fk_billing_user
    foreign key (user_id) references users (user_id)
      on delete cascade
);

create table court_reservations
(
  reservation_id   int auto_increment
    primary key,
  reservation_date date not null,
  start_time       time not null,
  end_time         time not null,
  user_id          int  not null,
  court_number     int  not null,
  constraint fk_reservation_court
    foreign key (court_number) references courts (court_number),
  constraint fk_reservation_user
    foreign key (user_id) references users (user_id)
      on delete cascade
);

create table financial_transactions
(
  transaction_id   int auto_increment
    primary key,
  amount           decimal(10, 2)                     not null,
  transaction_date datetime default CURRENT_TIMESTAMP null,
  transaction_type varchar(20)                        not null,
  status           varchar(20)                        not null,
  user_id          int                                not null,
  constraint fk_transaction_user
    foreign key (user_id) references users (user_id)
      on delete cascade
);

create table member_profiles
(
  profile_id             int auto_increment
    primary key,
  phone_number           varchar(20)   null,
  address                varchar(255)  null,
  guest_passes_remaining int default 0 null,
  user_id                int           not null,
  constraint fk_member_user
    foreign key (user_id) references users (user_id)
      on delete cascade
);

create table payment_disputes
(
  dispute_id     int auto_increment
    primary key,
  reason         varchar(255)               null,
  status         varchar(20) default 'OPEN' null,
  transaction_id int                        not null,
  constraint fk_dispute_transaction
    foreign key (transaction_id) references financial_transactions (transaction_id)
      on delete cascade
);