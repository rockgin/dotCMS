update inode set type = 'communication' where inode in (select inode from communication);