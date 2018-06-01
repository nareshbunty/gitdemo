Working with Embedded documents:
-------------------------------
Customer collection structure:

use custdb
db.customers.insertMany([
{   
	name:'Raju',
	email:"raj@gmail.com",
	member:true,
	gender:'male',
	points:400,
	address:{
		street:'Tarnaka',
		city:'Hyderabad'
	},
	contact:[4994994,94894949]
},
{   
  name:'Krishna',
  email:"krishna@gmail.com",
  member:false,
  gender:'male',
  points:800,
  address:{
    street:'B34,Shankermut',
    city:'Chennai'
  },
  contact:[3394994,9898949]
},
{   
  name:'Lalitha',
  email:"lalitha@gmail.com",
  member:true,
  gender:'female',
  points:700,
  address:{
    street:'rajbavan',
    city:'Pune'
  },
  contact:[4994994,94894949]
},
{   
  name:'Mallik',
  email:"mallik@gmail.com",
  member:true,
  gender:'female',
  points:1400,
  address:{
    street:'amberpet',
    city:'Hyderabad'
  },
  contact:[499494,954594949]
},
{   
  name:'Kamesh',
  email:"kamesh@gmail.com",
  member:false,
  gender:'male',
  points:600,
  address:{
    street:'market',
    city:'Hyderabad'
  },
  contact:[6994994,9994949]
}
])

Get all customers
>db.customers.find()

query selects all documents where 
     address is {street:'Tarnaka',city:'Hyderabad'}
>db.customers.find(
  { address:{street:'Tarnaka',city:'Hyderabad'}})

Query on Nested field:
>db.customers.find( { "address.city": "Hyderabad" } )

Query all male customers whose points < 500 and city is chennai
>db.customers.find( 
	{ "points": { $lt: 500 }, 
    "address.city": "Chennai",gender: "male" 
  } )

Query on Array: search for exactly two phones with same order
>db.customers.find( { contact: [4994994,94894949] } )

Query customeres in any order and also not exact 2 cells - $all
>db.customers.find( { contact: { $all: [4994994,94894949] } } )

Query array with operators
>db.customers.find( { contact: { $gt: 900000, $lt: 300000 } } )

Query on index position in array
>db.customers.find( { "contact.1": { $gt: 9000000 }})

Query on length: get me all customers who has 
only two contacts
>db.customers.find( { "contact": { $size: 2 } } )

Query an Array of Embedded Documents
------------------------------------
use inventorydb
db.inventory.insertMany( [
   { item: "journal", instock: [ { warehouse: "A", qty: 5 }, 
   { warehouse: "C", qty: 15 } ] },
   { item: "notebook", instock: [ { warehouse: "C", qty: 5 } ] },
   { item: "paper", instock: [ { warehouse: "A", qty: 60 }, { warehouse: "B", qty: 15 } ] },
   { item: "planner", instock: [ { warehouse: "A", qty: 40 }, { warehouse: "B", qty: 5 } ] },
   { item: "postcard", instock: [ { warehouse: "B", qty: 15 }, { warehouse: "C", qty: 35 } ] }
]);
>db.inventory.find()

Query for a Document Nested in an Array:
selects all documents where an element in the instock 
array matches the specified document:
>db.inventory.find( { "instock": { warehouse: "A", qty: 5 } } )

Query with condition:
selects all documents where the instock array has at least 
one embedded document that contains the field 
qty whose value is less than or equal to 20:
>db.inventory.find( { 'instock.qty': { $lte: 20 } } )

selects all documents where the instock array has as its 
first element a document that contains the field qty whose value
 is less than or equal to 20:
>db.inventory.find( { 'instock.0.qty': { $lte: 20 } } )

A Single Nested Document Meets Multiple Query Conditions 
on Nested Fields

Query for documents where the instock array has at least 
one embedded document that contains both the field qty 
equal to 5 and the field warehouse equal to A:

>db.inventory.find( { "instock": {
    $elemMatch: { qty: 5, warehouse: "A" }}})

----------------------------------------------------

Project Fields to Return from Query

To include a projection document to specify or 
restrict fields to return.
db.inventory.insertMany( [
  { item: "journal", status: "A", 
    size: { h: 14, w: 21, uom: "cm" }, 
    instock: [ { warehouse: "A", qty: 5 } ] },
  { item: "notebook", status: "A",  size: { h: 8.5, w: 11, uom: "in" }, instock: [ { warehouse: "C", qty: 5 } ] },
  { item: "paper", status: "D", size: { h: 8.5, w: 11, uom: "in" }, instock: [ { warehouse: "A", qty: 60 } ] },
  { item: "planner", status: "D", size: { h: 22.85, w: 30, uom: "cm" }, instock: [ { warehouse: "A", qty: 40 } ] },
  { item: "postcard", status: "A", size: { h: 10, w: 15.25, uom: "cm" }, instock: [ { warehouse: "B", qty: 15 }, { warehouse: "C", qty: 35 } ] }
]);

db.inventory.find( { "instock.qty": { $gt: 10,  $lte: 20 } } )

//In Sql : SELECT _id, item, status from inventory WHERE status = "A"
>db.inventory.find( { status: "A" }, { item: 1, status: 1 } )
>db.inventory.find( { status: "A" }, { status: 0, instock: 0 } )

Return Specific Fields in Embedded Documents
>db.inventory.find({ status: "A" },
      { item: 1, status: 1, "size.uom": 1 })
>db.inventory.find({ status: "A" },{ "size.uom": 0 })

>db.inventory.find( { status: "A" }, 
   { item: 1, status: 1, "instock.qty": 1 } )



Query for Null or Missing Fields
>db.inventory.find( { item: null } )

Existence check
>db.inventory.find( { item : { $exists: false } } )
====================================================

