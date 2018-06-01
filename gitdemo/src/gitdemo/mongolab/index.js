Indexing allows to improve performance on query
but degrades on Insert/update/delete

//To find what are the indexes for current database
db.customers.getIndexes()

db.customers.find({ 'gender' : 'male' }).explain();
//explain() method provides information on the query plan. 
//The query plan is the plan the server uses to find 
//the matches for a query. 
//This information may be useful when optimizing a query.

db.customers.ensureIndex({ 'name' :  1 });
//Creates an index on the field specified, 
//if that index does not already exist.
// 1 specifies ascending and a -1 specifies descending.

We can also drop intex using dropIndex();


Indexes: on Ships collectoin
-------

Creating an index 
>db.ships.ensureIndex({name : 1})

Dropping an index 
>db.ships.dropIndex({name : 1})

Creating a compound index 
>db.ships.ensureIndex({name : 1, operator : 1, class : -1})

Dropping a compound index 
>db.ships.dropIndex({name : 1, operator : 1, class : -1})

Creating a unique compound index 
>db.ships.ensureIndex({name : 1, operator : 1, class : -1},
 {unique : true})