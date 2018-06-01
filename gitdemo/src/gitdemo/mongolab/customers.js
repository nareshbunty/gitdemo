//Customers DB management

To generate fake json data:
https://www.json-generator.com/
click generate and download


//https://studio3t.com/  install Studio3T

To configure Sublime text with Mongo shell:
------------------------------------------

Start mongod intance 

Tools->Build System-> new Build System
Add Below Code:
{
	"selector": "source.js",
	"shell":true,
	"cmd":["mongo","<","$file"]
}
Press CTRL + S to save with name: mongo

next edit environment variables from control panel:
Click environment Variables Button,
select path variable and click edit
In Edit Environment Variable window,Click New Button
Browse to c:\programe files\Mongodb\server\3.4\bin
ok-> ok-> ok->

Now in Sublime text , select File -> new file 
name: source.js and save it.

type below code and press CTRL+B to see shell output:
--------------------------------------------------
var msg="hi"
print(msg)
--------------------------------------------------
Install Robot 3T and Connect to Mongodb localhost:27017
Start working.
--------------------------------------------------
Install Studio3T for windows (from Mongochef tool)

Start mongod  instance 

Open Studio3T GUI , click connect ->
CLick new connection, localhost, 27017, name:mongodb

Observe explorer left side with all databases loaded.

click intellishell, you can run script from here
enter  > db and click triangle icon to execute , 
below observe the test default database result.
-----------------------------------------------

Working with Database:

Database -> Collections -> Documents -> Fields {key:value}

Create database :
>use custdb   (press F6 to execute)   (switched to custdb)

Get current database:
>db

List of all databases:
>show dbs  (admin,local,config)

Insert collection to database
>db.customers.insert({name:'Sriram'})
>show dbs     (RC on mongodb root  and select Refresh ALL to see custdb)

>use dummydb    (Create database)
Drop database (dummydb):
>db.dropDatabase()   // drops current database in use

or you can RC on database in explorer and click drop database.
--------------------------------------------------------------

Working with Collections:

>use dummydb
>db.createCollection('dummy')        (observe :_id:... ok)
>show collections                    (observe dummy)
>db.dummy.insert({name:'xyz'})	     (WriteResult({"nInserted":1}))

Top drop collection:
>db.dummy.drop()					 (true)
>show collections                    (blank)
--------------------------------------------------------------

Inserting Documents:

>use custdb
>db.customers.insert({
	name:'Raju',
	email:"raj@gmail.com",
	member:true,
	gender:'male',
	address:{
		street:'Tarnaka',
		city:'Hyderabad'
	},
	contact:[4994994,94894949],
	
})

Right click on custdb->collections->customers->open Sql
>select * from customers

Inserting multiple records:

>db.customers.insertMany(
   [
	   {
		name:'Murthy',
		email:"murthy@gmail.com",
		member:true,
		gender:'male',
		address:{
			street:'Bilboard',
			city:'Newyork'
		},
		contact:[2324994,439339333]
	   },
	   {
		name:'Lalitha',
		email:"lalitha@gmail.com",
		member:false,
		gender:'female',
		address:{
			street:'Adyar',
			city:'Chennai'
		},
		contact:[3434994,94884949]
	   },
	   {
		name:'Kiran',
		email:"kiran@gmail.com",
		member:false,
		gender:'male',
		address:{
			street:'Thane',
			city:'Mumbai'
		},
		contact:[6994994,95594949]
	   },
	   {
		name:'Rama',
		email:"rama@gmail.com",
		member:true,
		gender:'female',
		address:{
			street:'shankarmut',
			city:'Hyderabad'
		},
		contact:[4444994,94894949]
	   },

	]
)


------------------------------------------------------------------

Querying database:  (Read)

in Studio , observe dropdown right side :
Tree view , Table view  , Json view

>db.customers.find()

Select JSON view and observe by giving below commands
>db.customres.find().pretty()

>db.students.findOne()

Find a customer with name Murthy
>db.customers.find({ name:'Murthy'})
-----------------------------------------------------

Update Document:

To update if exists or add new document based on _id
 (if _id does not exists then it create new document)
>db.customers.save({name:"xyz"})
>db.customers.find()

To update all documents:
>db.customers.update({},{$set:{points:100}})

To add new field called points to existing document:
>db.customers.update({name:"Murthy"},{$set:{points:450}})


To update existing field value
>db.customers.update({name:"Murthy"},{$set:{points:900}})


Update multiple documents :
>db.customers.update(
	{name:"Lalitha"},
	{$set:{name:"Lalitha"}},
	{multi:true}
	)

Update only one record:
>db.customers.findOneAndUpdate(
 {name:'Sriram'},{$set:{name:"Sri"}}
)

To increment ($inc) exsting field value:
>db.customers.update({name:"Murthy"},{$inc:{points:100}})

To remove a field ($unset)
>db.customers.update({name:"Murthy"},{$set:{age:45}})       //add field
>db.customers.find()                                        //check
>db.customers.update({name:"Murthy"},{$unset:{points:100}}) // remove field

Exercise:
1. How to remove single field
2. How to rename single field
3. How to update value of single field
4. How to update value of single field based on condition

-----------------------------------------------------------------

Delete  document:

To remove all documents: (Be careful)
>db.customers.remove()

To remove single record based on _id
>db.customers.remove({"_id":ObjectId("5ad96c22cdef60b5e4489463")})

To delete all documents with name is Sri
>db.customers.remove({name:'Sri'})   // delete all documents with name Sri

To delete only one document i.e. first matched one
>db.customers.remove({gender:'male'},{justOne:true})
or
>db.customers.remove({gender:'male'},1)
-----------------------------------------------------------------

Deleting collection:
>db.players.drop()

----------------------------------------------------------------

>use tempdb
for(i=0;i<5000,++i){
	db.posts.insert({'pid':i,name:'Sriram'});
}

===============================================
day 3:

Inserting Multiple documents:

var obj1={
	name:"Kiran",
	email:"kiran@gmail.com"
};
var obj2={
	name:"Mallika",
	email:"Mallika@gmail.com"
};

use customersdb
db.customers.insert([obj1,obj2]);
db.customers.find().pretty();


Querying database:
-----------------
Simple query
>db.customers.find().pretty()

Get only first document
>db.customers.findOne()

Printing in JSON format:
>db.customers.find().forEach(printjson)


Get all customers without any duplicates
>db.customers.distinct( "email" )

>db.customers.find( { gender: "male" } ).explain()

Conditions:
Get all documents whose gender is male
>db.customers.find({gender:male}).pretty()

>db.customers.updateMany({},{$set:{points:500}})

Update all customers who gender is female
and escalte points to 800
>db.customers.updateMany({gender:'female'},{$set:
  {
  	points:800
  }
})

get all customers whose gender is female
and points = 500
>db.customers.find({gender:'female',points:800})


Get all documents whose points more than 200 ($gt)
>db.customers.find({points:{$gt:200}})

Get all documents whose points greater than 
and equal to 450 ($gte)
>db.customers.find({points:{$gte:450}})

Get all documents whose points great than and equal 
to  300 ($lt)
>db.customers.find({points:{$lt:300}})

Get all documents whose points less than and equal to   300 ($lte)
>db.customers.find({points:{$lte:300}})

>db.customers.find({points:{$gt:200,$lt:900}})


Get all documenes who are not male ($ne) (not equal)
>db.customers.find({gender:{$ne:'male'}})

Show all customers whose gender is male and
not members but points greater than 200
Also get count of above query.

>db.customers.find({gender:'male',member:{$ne:false},
 points:{$gt:200}}).count()




Get all customers whose points  greater than and
 less than equals to 500
>db.customers.find(
   { points: { $gt: 250, $lte: 500 } }
)

in SQL:UPDATE customers SET member = true WHERE 
        points>550 
>db.customers.updateMany(
   { points: { $gt: 550 } },
   { $set: { member: true } }
)


in SQL : UPDATE customers SET points = points + 50 
         WHERE gender="female"
>db.customers.updateMany(
   { gender: "female" } ,
   { $inc: { points: 50 } }
)

Delete customers who has points <200
>db.people.deleteMany( $lt:{ points: 200 } )


And and Or condition:
Get documents whose name is Murthy AND gender is male
>db.customers.find({name:'Murthy',gender:'male'})


Get documents whose name is Murthy or mail is Murthy@gmail.com
>db.customers.find({
	$or:[{name:'Murthy'},{mail:"Murthy@gmail.com"}]
})

Get documents whose name is Murthy and gender 
is male or  mailid is Murthy@gmail.com



>db.customers.find(
   {
   	name:"Murthy",
   	$or: [{gender:"male",mail:"Murthy@gmail.com"}]
    }
)


Get female customers name and email who 
are male  but not id
>db.customers.find(
    { gender: "female" },
    { name: 1, email: 1, _id: 0 }
)
------------------------------------------------
$regex:

Get all customers with Name Murthy (i=case-insensitive)
db.users.find({name : /Murthy/i});

Get customers where mail matches gmail 
(like in sql - %gmail%)
>db.customers.find( { email: /gmail/ } )
-or-
>db.customers.find( { email: { $regex: /gmail/ } } )

Get customers whose mailid starts with ma
db.customers.find( { email: /^mu/ } )
-or-
db.customers.find( { email: { $regex: /^mu/ } } )


-----------------------------------------------------------------
Projection :
Selecting necessary data rather than whole document.

db.COLLECTION.find({},{KEY:1})
1 means show, 0 means do not show

Show the name of all customers with ID
>db.customers.find({},{name:1})

Shw the name of all customers without ID
>db.customers.find({},{name:1,"_id":0})

-----------------------------------------------------------------

Limit, Sort , Skip 

Get only top 2 customers and print in json
>db.customers.find().limit(10).forEach(printjson);

Show only 5 documents with name and gender without id 
>db.customers.find({},
	{name:1,gender:1,"_id":0}).limit(5)




get all customers from 3rd row
>db.customers.find({},{name:1,gender:1,"_id":0}).skip(3)

Get 5 customers from 3rd row
>db.customers.find({},
	{name:1,gender:1,"_id":0}).skip(3).limit(5)

Sort customers on gender 
(1 is ascending, -1 is descending)
>db.customers.find({},{name:1,gender:1,"_id":0})
 .sort({gender:1})

---------------------------------------------------------------

Aggregation:
How many customers are there 
>db.customers.count()
or
>db.customers.find().count()

How many customers who has points
>db.customers.count( { points: { $exists: true } } )
or
>db.customers.find( { points: { $exists: true } } ).count()

Get customers count whose points greater than 300
>db.customers.count( { points: { $gt: 300 } } )
or
>db.customers.find( { points: { $gt: 300 } } ).count()


========================================
Pipeline  Stages:
----------------
$project :  Change the set of documents by 
            modifying keys and values. 
            This is a 1:1 mapping

$match   : This is a filtering operation and 
            thus this can reduce the amount of 
            documents that are given 
           as input to the next stage. 
           This can be used for example if 
           aggregation should only happen on a 
           subset of the data.

$group   : This does the actual aggregation and 
           as we are grouping by one or more keys 
           this can have a reducing effect
	       on the amount of documents.

$sort 	 : Sorting the documents one way or the 
           other for the next stage. 
           It should be noted that this might 
           use a lot of memory. 
           Thus if possible one should always 
           try to reduce the amount of documents first.

$skip    : With this it is possible to skip forward 
           in the list of documents for a given 
           amount of documents. 
	        This allows for example starting 
	        only from the 10th document.
 	    
$limit   : This limits the amount of documents to 
            look at by the given number starting 
            from the current position.

$unwind  :  This is used to unwind document that 
             are using arrays.
             When using an array the data is kind 
             of pre-joined and this operation 
             will be undone with this to have
             individual documents again. 
             Thus with this stage we will 
             increase the amount of documents for 
             the next stage. 


Aggregations:
------------
db.ships.aggregate([
	{$group : {_id : "$operator", 
	num_ships : {$sum : 1}}}])

Counts the number of ships per operator, 
would be in SQL:
SELECT count(*) FROM ships GROUP BY operator;

db.ships.aggregate([{
	$project : {_id : 0, 
	operator : {$toLower : "$operator"}, 
	crew : {"$multiply" : ["$crew",10]}}}])



Simple $lookup
--------------
===============================================




























