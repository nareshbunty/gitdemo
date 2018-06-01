Working with Cursors:

Iterate a Cursor in the mongo Shell:
-----------------------------------
call the cursor variable in the shell to iterate up to 20 times
 and print the matching documents

var myCursor = db.customers.find();
myCursor

use the cursor method next() to access the documents:

var myCursor = db.customers.find();
while (myCursor.hasNext()) {
   print(tojson(myCursor.next()));
}

OR

print operation, consider the printjson() helper method
to replace print(tojson()):

var myCursor = db.customers.find();
while (myCursor.hasNext()) {
   printjson(myCursor.next());
}

use the cursor method forEach() to iterate the cursor and 
access the documents

var myCursor =  db.customers.find( );
myCursor.forEach(printjson);


Get all customers who has more than 200 points
var myCursor = db.customers.find( { points : { $gt:200 }});
	while(myCursor.hasNext()){
		print(tojson(myCursor.next()));
	}


Iterator Index
--------------
Use the toArray() method to iterate the cursor and return
 the documents in an array

var myCursor = db.customers.find();
var documentArray = myCursor.toArray();
var myDocument = documentArray[3];

The toArray() method loads into RAM all documents returned by the
 cursor; the toArray() method exhausts the cursor.

Cursor Behaviors:
----------------
Closure of Inactive Cursors:

By default, the server will automatically close the cursor 
after 10 minutes of inactivity, 
or if client has exhausted the cursor. 

To override this behavior in the mongo shell, 
use the cursor.noCursorTimeout() method:

var myCursor = db.customers.find().noCursorTimeout();
--------------------------------------------------------------

