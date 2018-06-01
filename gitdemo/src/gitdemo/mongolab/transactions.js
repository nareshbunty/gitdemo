Transactions in Mongodb:
-----------------------

1. MongoDb does not support transactions on multiple documents?

But, in MongoDB you can perform atomic operations on a single document. 

2.  With lack of transactions on multiple documents, 
Can mongoDB achieve ACID transactions?

Using atomic operations on a single document we can meet all the  
requirements of ACID transactions in RDBMS.

Create separate STUDENT and PHONE collections. 
And documents in phone contain a reference to the student document.

So, If one transaction update STUDENT documents and other transaction 
updating PHONE documents might create some discrepancies.

 create and insert in STUDENT collection >
db.STUDENT.insert({
  "_id": 1,
  "FIRST_NAME": "Sriram"
})
db.STUDENT.insert({
  "_id": 2,
  "FIRST_NAME": "Samanvit"
})

create and insert in PHONE collection >
>db.PHONE.insert({
  "_id": 11,
  "PHONE_NUMBER": 1234,
  "STUDENT_ID":  {
      "$ref": "STUDENT",
      "$id": 1, 
      "$db": "mydb" 
   }
})
db.PHONE.insert({
  "_id": 12,
  "PHONE_NUMBER": 2345,
  "STUDENT_ID":  {
      "$ref": "STUDENT",
      "$id": 1, 
      "$db": "mydb" 
   }
})
db.PHONE.insert({
  "_id": 13,
  "PHONE_NUMBER": 3456,
  "STUDENT_ID":  {
      "$ref": "STUDENT",
      "$id": 2, 
      "$db": "mydb" 
   }
})
db.PHONE.insert({
  "_id": 14,
  "PHONE_NUMBER": 4567,
  "STUDENT_ID":  {
      "$ref": "STUDENT",
      "$id": 2, 
      "$db": "mydb" 
   }
})
Now transaction can not be performed on above multiple documents.

Let’s rephrase above example for better transaction management.

Create STUDENT collections. 
Embed the PHONE documents (completely) in the STUDENT document.

Now multiple transaction cannot create any discrepancies 
as STUDENT contains PHONE documents in embedded form and 
in MongoDB you can perform atomic operations on a single document.

>db.STUDENT.insert({
  "_id": 1,
  "FIRST_NAME": "Sriram",
  "PHONE": [
    {"PHONE_NUMBER": 1234},
    {"PHONE_NUMBER": 2345}
  ]
})
>db.STUDENT.insert({
  "_id": 2,
  "FIRST_NAME": "Samanvit",
  "PHONE": [
    {"PHONE_NUMBER": 3456},
    {"PHONE_NUMBER": 4567}
  ]
})

Summary:
-------
While designing your database and collections we must
 try and ensure that all the related data 
 which is needed to be updated atomically must be placed 
 in single document as embedded documents
  (in form of nest arrays OR nested documents) 
  so we can take advantage of MongoDB support 
  for performing atomic operations on a single document.


To implement atomic transactionin API

transferring cash between two bank accounts. 
To transfer $20, the steps are:

Subtract 20 from the sender’s balance
Add 20 to the receiver’s balance

// step 1: take $20 from Sender
Accounts.update({_id: "Sender"}, 
  {$inc: {balance: -20}}, function(err){
  if(err){ 
    // No harm done. No one's money is touched
    // Data remains consistent
    throw err; 
  }
  
  // Sender's account has been updated
   // step 2: add $20 to Reciever
  Accounts.update({_id: "Reciever"}, {$inc: {balance: 20}}, function(err){
    if(err) {
      // Sender's account has been updated but Reciever's account was not updated. 
      // Data is inconsistent.
       throw err;
    }
      // Both accounts have been updated. Wow!
  });
});

------------------------------------------------------
Using API in Node JS - Fawn with Mongoose:
-----------------------------------------
Fawn: implements transactions using this two phase commit system. 
With Fawn, we can save, update, and delete documents 
and files across collections in a transactional manner.

var Fawn = require("Fawn");
// intitialize Fawn
Fawn.init("mongodb://127.0.0.1:27017/testDB");

/**
optionally, you could initialize Fawn with mongoose
var mongoose = require("mongoose");
mongoose.connect("mongodb://127.0.0.1:27017/testDB");
Fawn.init(mongoose);
**/

// after initialization, create a task
var task = Fawn.Task();

// assuming "Accounts" is the Accounts collection
task.update("Accounts", {_id: "Sender"}, {$inc: {balance: -20}})
  .update("Accounts", {_id: "Reciever"}, {$inc: {balance: 20}})
  .run()
  .then(function(results){
    // task is complete 

    // result from first operation
    var firstUpdateResult = results[0];

    // result from second operation
    var secondUpdateResult = results[1];
  })
  .catch(function(err){
    // Everything has been rolled back.
    
    // log the error which caused the failure
    console.log(err);
  });

-------------------------------------