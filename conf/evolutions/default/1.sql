# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table copy_test (
  id                        bigint not null,
  notification_id           bigint,
  message_id                bigint,
  test_id                   integer,
  send_limit                integer,
  start_date                timestamp,
  end_date                  timestamp,
  enabled                   boolean default false,
  last_sent                 timestamp,
  constraint pk_copy_test primary key (id))
;

create table message (
  id                        bigint not null,
  key                       varchar(255),
  url                       varchar(255),
  constraint pk_message primary key (id))
;

create table notification (
  id                        bigint not null,
  name                      varchar(255),
  description               varchar(255),
  segment_name              varchar(255),
  constraint pk_notification primary key (id))
;

create sequence copy_test_seq;

create sequence message_seq;

create sequence notification_seq;

alter table copy_test add constraint fk_copy_test_notification_1 foreign key (notification_id) references notification (id) on delete restrict on update restrict;
create index ix_copy_test_notification_1 on copy_test (notification_id);
alter table copy_test add constraint fk_copy_test_message_2 foreign key (message_id) references message (id) on delete restrict on update restrict;
create index ix_copy_test_message_2 on copy_test (message_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists copy_test;

drop table if exists message;

drop table if exists notification;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists copy_test_seq;

drop sequence if exists message_seq;

drop sequence if exists notification_seq;

