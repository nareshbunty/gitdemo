In MongoDB, 

a stored procedure is just a JavaScript function, 
which must be stored in a special collection named 
db.system.js. 

A stored procedure used to perform Insert/Update/delete operations on database
and Function to write complex queries and invoke for reusability.

Write ATOMIC transactions here to perform Insert/Update/Delete 
as unit.

To convert that to a stored proc, just insert it into db.system.js:

> db.system.js.save(
	{_id:"showDataProc", 
	 value:function(msg){ print(msg) }
	});

Stored procs can be viewed, altered and deleted in the same way 
as any document in any collection, 

> db.system.js.find()
> db.loadServerScripts();
> showDataProc("I am Mongodb");

Inserting a record from Stored Procedure in Mongodb:
--------------------------------------------------
db.system.js.save(
  {
    _id:"addRecord",
    value:function(){
      db.customers.insert({name:'added from SP'})
    } 
  })

use custdb  
db.loadServerScripts();
addRecord()  
db.customers.find({name:'added from SP'})
---------------------------------------
We can also pass parameters





