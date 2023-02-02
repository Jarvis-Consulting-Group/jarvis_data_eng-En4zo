# Introduction

# SQL Queries

###### Table Setup (DDL)

```sql
--create schema cd
create schema if not exists cd;
create table if not exists cd.facilities
  (
     facid integer not null,
     name character varying(100) not null,
     membercost numeric not null,
     guestcost numeric not null,
     initialoutlay numeric not null,
     monthlymaintenance numeric not null,
     constraint facilities_pk primary key (facid)
   );
   
create table if not exists cd.members
  (
  	 memid integer not null,
  	 surname character varying(200),
  	 firstname character varying(200),
  	 address character varying(300),
  	 zipcode integer,
  	 telephone character varying(20),
  	 recommendedby integer,
  	 joindate timestamp,
  	 constraint members_pk primary key (memid),
  	 constraint members_recommendedby_fk foreign key (memid)
  	 references cd.members(memid) on delete set null
  );
 
create table if not exists cd.bookings
  (
     facid integer not null,
     memid integer not null,
     starttime timestamp not null,
     slots integer not null,
     constraint bookings_pk primary key (facid),
     constraint bookings_memid_fk foreign key (memid) references cd.members(memid),
     constraint booking_facid_fk foreign key (facid) references cd.facilities(facid)
)
```

###### Question 1: Show all members

```sql
SELECT *
FROM cd.members
```

###### Question 2: Lorem ipsum...

```sql
SELECT blah blah
```

