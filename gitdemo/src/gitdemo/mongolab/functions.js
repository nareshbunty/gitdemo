Custom Functions in Mongodb
---------------------------

In MongoDB databases we can create functions like in Sql Server. 

MongoDB provides us a collection named as System.js for this.

System.js collection contains two keys
	1. _id : _id is the function name.
	2. value :  value contains actual function defination.

For example, suppose we want to create a function which will accept 
two parameters named  firstname and lastname and will return full name.

db.system.js.save
(
   {
     _id: "FullName",
     value : function(FirstName,LastName) { 
      return FirstName + ' ' + LastName;
    }
   }
)

To call this function we need to load server scripts first, 
and than we can call this function as below

db.loadServerScripts();
FullName('Murthy','Srirama')
---------------------------------------------------------------

AutoIncrment Function:

Auto Increment on Primary key custom function:

Step 1: Create a collection( Identity in my case) w
hich will hold the counter

>db.createCollection("Identity") // Identity is my collection Name

Step 2: Insert a document in this collection with 
intial counter value

db.Identity.insert({_id:"incrementID",sequence_value:0}) 
db.Indentity.find()

Step 3: Create a function which will increment this 
        sequence_value and add that function in system.js as below

db.system.js.save
(
   {
     _id: "getNextIdentity",                                            
     value : function getNextIdentity(sequenceName)
     {
        var sequenceDocument = db.Identity.findAndModify
            ({
                query:{_id: sequenceName },
                update: {$inc:{sequence_value:1}},
                new:true
            });
        return sequenceDocument.sequence_value;
     }
   }
)

Step 4: While inserting a document we can call this function, 
which will return a incremented value as below

db.loadServerScripts();
db.Employee.insert({
   "_id":getNextIdentity("incrementID"),
   "EmpName":"Murthy",
   "Age":50
})
db.Employee.find()