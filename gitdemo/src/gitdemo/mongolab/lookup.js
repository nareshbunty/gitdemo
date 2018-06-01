$lookup :
-------

db.getCollection('Customer').find({})  
  name   email               cityID
  sriram sriram@yahoo.com     1
  Raj    raj@gmail.com        2
  Krishna krish@gmail.com     1
  lalitha  lali@gmail.com     1

master collection: (CityMaster)
db.getCollection('CityMaster').find({}) 
  id      cityname
   1      Chennai
   2      Hyderabad

db.Customer.aggregate([      
    {$lookup: {
       from: "CityMaster", 
       localField: "CityId", 
       foreignField: "id", as: "CityDetail"}},  
       {$match: {details: {$ne: []}}}  
]); 
here 
Customer is table1 
From is     table2
localField:cityid means table1.CityId
foreignField:id means table2.id
as is column alias
-----------------------------------------------

Lab : One to Many join

use bankdatabase
db.banks.insertMany([
 {
   name:'HDFC',
   type:'National',
   email:'support@hdfc.com',
   countryId:1,
   phones:[2232323,2454443],
 },
 {
   name:'HDFC',
   type:'National',
   email:'support@hdfc.com',
   countryId:1,
   phones:[2232323,2454443],
 },
 {
   name:'ANZ',
   type:'International',
   email:'support@ANZ.com',
   countryId:3,
   phones:[242323,3454443],
 },
 {
   name:'ICICI',
   type:'National',
   email:'support@icici.com',
   countryId:1,
   phones:[2232323,2454443],
 },
 {
   name:'Singapore Bank',
   type:'National',
   email:'support@singapore.com',
   countryId:3,
   phones:[2232323,2454443],
 },
 {
   name:'Srilanka bank',
   type:'National',
   email:'support@srilanka.com',
   countryId:4,
   phones:[2232323,2454443],
 },
 {
   name:'SBI',
   type:'National',
   email:'support@sbi.com',
   countryId:1,
   phones:[2232323,2454443]
 },
 {
   name:'Corp Bank',
   type:'National',
   email:'support@corp.com',
   countryId:1,
   phones:[2232323,2454443],
 }
])

>db.banks.find()


// Master
db.bankarea.insertMany(
   [
   { _id:1, country:'India'},
   { _id:2, country:'USA'},
   { _id:3, country:'Singapore'},
   { _id:4, country:'Srilanka'}
   ])

db.bankarea.find()


db.banks.aggregate(     
    {$lookup: {
       from: "bankarea", 
       localField: "countryId", 
       foreignField: "_id", as: "CountryDetail"}} 

);

db.banks.aggregate(      
    {$lookup: {
       from: "bankarea", 
       localField: "countryId", 
       foreignField: "_id", as: "CountryDetail"}},  
      {$match: {type: {$ne: 'National'}}}  
);
----------------------------------------------------------

One to Many Join
----------------
db.countryCode.insert
  ([
    {code: 10}, 
    {code: 20},
    {code: 30}
  ])

db.getCollection('countryCode').find({})

//Next, create a lookup table pairing 
//the country codes to country names:
db.countryCodeLookup.insert(
   [{code: 10, name: "United States"},
    {code: 20, name: "Egypt"}, 
    {code: 30, name: "Greece"}
   ])

db.getCollection('countryCodeLookup').find({})

//Now we can query join the two collections 
//with the $lookup operator (no _id)

db.countryCode.aggregate([
{ $lookup: {
    from: "countryCodeLookup", 
     localField: "code", 
     foreignField: "code", 
     as: "countryName"} 
    },
  { 
   $project: 
     {"code":1,"countryName.name":1,"_id":0} }
 ])
---------------------------------------------
One to One Join
---------------
If we know that the relationship is 1:1, 
we can use the $unwind aggregator to 
deconstruct the array, flattening the return document:


db.countryCode.aggregate([
{ $lookup: {
   from: "countryCodeLookup", 
   localField: "code", 
   foreignField: "code", as: "countryName"}
   },
{ $unwind: "$countryName"},
{ $project: {"code":1, "countryName.name":1, "_id":0} }
])

This eliminates the inner array

Notice though that countryName is still in 
a sub-document.  

We can use a projection to eliminate that:
db.countryCode.aggregate([
{ $lookup: {
   from: "countryCodeLookup", 
   localField: "code", 
   foreignField: "code", as: "countryName"}
    },
{ $unwind: "$countryName"},
{ $project: {"code":1, 
          "name": "$countryName.name", "_id":0} }
])

------------------------------------------------------

Nested Lookups
==============
What if you have three collections,
 where collection A has a "foreign key"
  to collection B, and collection B has a 
  "foreign key" to collection C? 


collections and add a phone number 
which a separate country code, 
so we can then do a join like this:

phone number + country code + country name

It's somewhat artificial but it will clearly 
illustrate the "trick" to get this to work.  
First, create a couple phone records:


db.phone.insert([
   {number: "555-1212", countryCode: 1}, 
   {number: "851-1234", countryCode: 20}])

Note that we must unwind the each resulting 
document before proceeding with the next lookup:

db.phone.aggregate([
{ $lookup: {from: "countryCode", 
   localField: "countryCode",
    foreignField: "code", as: "countryCode"} },
{ $unwind: "$countryCode"},
{ $lookup: {from: "countryCodeLookup", 
   localField: "countryCode.code", 
   foreignField: "code", as: "country"} }
])

