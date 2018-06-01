Starting in version 3.4, 
MongoDB adds support for creating 
read-only views from existing collections or other views.

Create View:
-----------
To create view:

db.createView(<view>, <source>, <pipeline> )

Behavior
Views exhibit the following behavior:

Read Only
Views are read-only; write operations on views will error.

The following read operations can support views:

db.collection.find()
db.collection.findOne()
db.collection.aggregate()
db.collection.count()
db.collection.distinct()

Index Use and Sort Operations
Views use the indexes of the underlying collection.

As the indexes are on the underlying collection, you cannot create,
 drop or re-build indexes on the view directly 
 nor get a list of indexes on the view.

Projection Restrictions

find() operations on views do not support the 
following projection operators:

$
$elemMatch
$slice
$meta

Immutable Name   :You cannot rename views.

View Creation

Views are computed on demand during read operations, 
and MongoDB executes read operations on views as part 
of the underlying aggregation pipeline. 

Drop a View
To remove a view, use the db.collection.drop() method on the view.

> use team
> db.players.find().pretty()
{
        "_id" : ObjectId("596b44d46879c5e83035f228"),
        "firstName" : "Joe",
        "lastName" : "Johnson",
        "position" : "forward",
        "number" : 9,
        "isMarried" : true
}
{
        "_id" : ObjectId("596b44d46879c5e83035f229"),
        "firstName" : "Alex",
        "lastName" : "Jones",
        "position" : "goalkeeper",
        "number" : 16,
        "isMarried" : false
}
{
        "_id" : ObjectId("596b44d46879c5e83035f22a"),
        "firstName" : "Ethan",
        "lastName" : "Hawk",
        "position" : "defender",
        "number" : 4,
        "isMarried" : false
}
{
        "_id" : ObjectId("596b44d46879c5e83035f22b"),
        "firstName" : "Richard",
        "lastName" : "Dawkins",
        "position" : "midfielder",
        "number" : 8,
        "isMarried" : true
}

db.createView:

It takes 3 arguments: 
  1. view’s name, 
  2. source collection and the 
  3. an array containing  aggregation pipeline. 

This is where you define how your view will 
manipulate the source collection.

> db.createView(
	"playersInfos", 
	"players", 
	 [ {$project : 
	 	{ "firstName": 1, "lastName": 1, "position": 1} } ] )

> show collections
players
playersInfos
system.views

> db.system.views.find()

> db.playersInfos.find()


Now, I forgot one player in my team. 
I will add a new document to my players collection 
and query my view again. 
Remember that you can’t write to a read-only view.

> db.players.insertOne({"firstName": "Rigoberto", "lastName": "Song", "position": "wingback", "number": 2, "isMarried": false})
{
        "acknowledged" : true,
        "insertedId" : ObjectId("596b47506879c5e83035f22c")
}

> db.playersInfos.find()

The document has been inserted in the source collection, 
and my view is able to query it right away.

To drop view : 
> db.playersInfos.drop()

> db.createView(
	"playersInfos", 
	"players", 
	[{$project : 
	 { "fullName": 
	     {$concat: ["$firstName", " ", "$lastName"]},
	      "position": 1}}])

> db.playersInfos.find()

Why View:
Security
You can allow an application to query a view without 
exposing certain fields. 
If you store sensitive information in collection, 
those informations can’t be accessed from a view 
if it is defined correctly, 
even if those informations are in the source collection.

Simplicity
-------------------------------------------------------