Key points of MongoDB:
---------------------
1. MongoDB Stores Data in Json Format
   (We call it BSON(Binary JSON))

2. JSON documents store data in Key Value Pair like 
   {“X”:1,”Y”:2,”Z”:3}

3. There are two basic structure inside JSON is
      a. Array : List of things is represented in List of 
      Items [……..]
      b. Dictionaries : Associate Maps {key:Value}

4. Mongo Db is Schema less

5. MongoDB does not support Joins but achieved with $lookup

Operation	  In SQL	   In MongoDB
------------------------------
Create		  Insert	   Insert
Read		    Select	   Find
Update		  Update	   Update
Delete		  Delete	   Remove
Table		    Table	     Collection
Row			    Row		     Document
column		  column	   field
Logic       Function   Function scripts
IUD         SP         Procedure scripts
Notify      Trigger    Oplog Trigger
Readonly    view       View
Foreignkey  Foreignkey $ref
----------------------------------------------------------------



Aggregations:
------------
Aggregation operation process data records and 
return computed results. 

It group values from multiple documents together, 
and can perform operations on the grouped data to 
return a single result.

Syntax : >db.COLLECTION_NAME.aggregate(AGGREGATE_OPERATION)

>db.blog.insertMany([
{
   title: 'MongoDB', 
   description: 'MongoDB is no sql database',
   user: 'Murthy',
   tags: ['mongodb', 'database', 'Casandra'],
   likes: 1000
},
{
   title: 'PostGre', 
   description: 'Post-gre database is very fast',
   user: 'Murthy',
   tags: ['mongodb', 'database', 'NoSQL'],
   likes: 100
},
{
   title: 'Neo4j Overview', 
   description: 'Neo4j is no sql database',
   user: 'Neo4j',
   tags: ['neo4j', 'database', 'NoSQL'],
   likes: 750
}
])

//select user, count(*) from mycol group by by_user.
> db.blog.aggregate(
	[{$group : {id : "$user", count : {$sum : 1}}}])

>db.blog.aggregate(
	[{$group : {_id : "$user", count : {$sum : "$likes"}}}])

>db.blog.aggregate(
	[{$group : {_id : "$user", count : {$avg : "$likes"}}}])

>db.blog.aggregate([
	{$group : {_id : "$user", count : {$min : "$likes"}}}])

>db.blog.aggregate([
	{$group : {_id : "$user", count : {$max : "$likes"}}}])

//Update the value to an array in the resulting document.
>db.blog.aggregate(
	[{$group : {_id : "$user", url : {$push: "$description"}}}])

//get first document from grouped result ($first,$last)
>db.blog.aggregate(
	[{$group : {_id : "$user", first : {$first : "$description"}}}])

Pipeline stages:

$project − Used to select some specific fields 
           from a collection.

$match − This is a filtering operation and thus this 
        can reduce the amount of documents that are given 
        as input to the next stage.

$group − This does the actual aggregation on specified 
         columns

$sort − Sorts the documents.

$skip − skip forward in the list of documents for a 
        given amount of documents.

$limit − This limits the amount of documents to look at, 
         by the given number starting from the current positions.

$unwind − This is used to unwind document that are using arrays. 
          Takes an array field with n elements from a document 
         and returns n documents with each element added to each 
         document as a field replacing that array
---------------------------------------------------------------------

Importing json document into mongodb:

> mongoimport -d testdb -c website --file website.json

//SELECT hosting, SUM(hosting) AS total
//       FROM website
//       GROUP BY hosting
> db.website.aggregate(
    { 
	$group : {_id : "$hosting", total : { $sum : 1 }}
    }
  );

//Sorting
>  db.website.aggregate(
     { 
	$group : {_id : "$hosting", total : { $sum : 1 }}
     },
     {
	$sort : {total : -1}
     }
  );

//Matching
> db.website.aggregate(
    { 
	$match : {hosting : "aws.amazon.com"}
    },
    { 
	$group : { _id : "$hosting", total : { $sum : 1 } }
    }
  );


 Set the group results in a variable. 
 In this case, the variable name is “groupdata”.

> var groupdata = db.website.aggregate(
    { 
	$group : {_id : "$hosting", total : { $sum : 1 }}
    },
    {
	$sort : {total : -1}
    }
  );
//now add this group 
> db.websitegroup.insert(groupdata.toArray());

> db.websitegroup.find().pretty()
{ "_id" : "aws.amazon.com", "total" : 4 }
{ "_id" : "hostgator.com", "total" : 3 }
{ "_id" : "cloud.google.com", "total" : 2 }
{ "_id" : "godaddy.com", "total" : 1 }

Exports the collection “websitegroup” to a csv file.
c:\> mongoexport -d testdb -c websitegroup -f _id,total -o group.csv --csv

Export to JSON file
c:\> mongoexport -d testdb -c websitegroup -o group.json

Aggregation Expressions
-----------------------
$match:

{
  "id": "1",
  "firstName": "Sriram",
  "lastName": "Murthy",
  "phoneNumber": "900455534",
  "city": "Hyderbad",
  "state": "AP",   "zip": 50013, 
  "email":  "Murthy@gmail.com"
}

To find all users who live in hyderabad ,

>db.customers.aggregate([ 
  { $match: { "zip": 50013 }}
]);

This will return the array of customers that 
live in the 90210 zip code. Using the match
stage in this way is no different 
from using the find method on a collection

$match with $group will give performance but not find()

> db.customers.aggregate([ 
  { $match: {"zip": "90210"}}, 
  { 
    $group: {
      _id: null, 
      count: {
        $sum: 1
      }
    }
  }
]);


{
  "id": "1",
  "productId": "1",
  "customerId": "1",
  "amount": 20.00,
  "transactionDate": ISODate("2017-02-23T15:25:56.314Z")
}

calculating the total amount of sales made 
for the month of January. We will start by 
 matching only transactions that occurred 
between January 1 and January 31.

{
   $match: {
    transactionDate: {
        $gte: ISODate("2017-01-01T00:00:00.000Z"),
        $lt: ISODate("2017-02-01T00:00:00.000Z")
    }
  }
}

> db.transactions.aggregate([
  { 
    $match: {
      transactionDate: {
        $gte: ISODate("2017-01-01T00:00:00.000Z"),
        $lt: ISODate("2017-01-31T23:59:59.000Z")
      }    
    }
  }, {
    $group: {
      _id: null,
      total: {
        $sum: "$amount"
      }
    }
  }
]);

> db.transactions.aggregate([
  { 
    $match: {
      transactionDate: {
        $gte: ISODate("2017-01-01T00:00:00.000Z"),
        $lt: ISODate("2017-01-31T23:59:59.000Z")
      }    
    }
  }, {
    $group: {
      _id: null,
      total: {
        $sum: "$amount"
      },
      average_transaction_amount: {
        $avg: "$amount"
      },
      min_transaction_amount: {
        $min: "$amount"
      },
      max_transaction_amount: {
        $max: "$amount"
      }
    }
  }
]);

=====================

$push : Pushing values to a result array

  >db.ships.aggregate([{$group : 
    {_id : "$operator", classes : {$push: "$class"}}}])

$addToSet : Pushing values to a result array without duplicates
  > db.ships.aggregate(
    [{$group : 
    {_id : "$operator", classes :{$addToSet :"$class"}}}])

$first / $last : Getting the first / lastdocument
  
  > db.ships.aggregate([{$group :
   {_id : "$operator", last_class : 
   {$last :"$class"}}}])

$unwind
Deconstructs an array field from the
input documents to output a document for each element.
 Each output document is the input document with the 
value of the array field replaced by the element.

{ "_id" : 1, "item" : "ABC1", sizes: [ "S", "M", "L"] }

The following aggregation uses the $unwind 
stage to output a document for each element 
in the sizes array:

db.inventory.aggregate( [ { $unwind : "$sizes" } ] )
The operation returns the following results:

{ "_id" : 1, "item" : "ABC1", "sizes" : "S" }
{ "_id" : 1, "item" : "ABC1", "sizes" : "M" }
{ "_id" : 1, "item" : "ABC1", "sizes" : "L" }

