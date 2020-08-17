delete from event;
delete from users;

insert into event(id, date_event, description, level_event, log_event, origin, quantity) values
(1, '2020-07-30', 'ID-1', 'WARNING', 'org.springframework' ,'ControllerAdvice beans', 1),
(2, '2020-06-30', 'ID-2', 'INFO', 'org.springframework', 'ControllerAdvice beans', 2),
(3, '2020-05-30', 'ID-3', 'ERROR', 'org.springframework', 'ControllerAdvice beans', 3);
