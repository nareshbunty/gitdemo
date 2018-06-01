Query Optimization  for Performance:
-----------------------------------

1. Create Indexes to Support Queries:
 For commonly issued queries, create indexes.
 If a query searches multiple fields, create a compound index. 
 Scanning an index is much faster than scanning a collection. 
 The indexes structures are smaller than the documents reference, 
 and store references in order.

EXAMPLE
If we have a posts collection containing blog posts, 
and  regularly issue a query that sorts on the author_name field, 
then optimize the query by creating an index on the author_name field:

db.posts.ensureIndex( { author_name : 1 } )

2. Indexes also improve efficiency on queries 
that routinely sort on a given field.

EXAMPLE

If we regularly issue a query that sorts on the timestamp field, 
then  optimize the query by creating an index 
on the timestamp field:

Creating this index:

db.posts.ensureIndex( { timestamp : 1 } )

Optimizes this query:
db.posts.find().sort( { timestamp : -1 } )


3. Index keys that are of the BinData type are more 
efficiently stored in the index if:

4. Limit the Number of Query Results to Reduce Network Demand

MongoDB cursors return results in groups of multiple documents. 
If you know the number of results you want, reduce the 
demand on network resources by issuing the limit() method.

This is typically used in conjunction with sort operations. 
For example, if you need only 10 results from query 
to the posts collection, issue the following command:

db.posts.find().sort( { timestamp : -1 } ).limit(10)
For more information on limiting results, see limit()

5. Use Projections to Return Only Necessary Data
When we need only a subset of fields from documents, 
return only the fields we need:

For example, if query to the posts collection, only the timestamp,
title, author, and abstract fields

db.posts.find( {}, {
 timestamp : 1 , title : 1 , author : 1 , abstract : 1} )
 .sort( { timestamp : -1 } )

6. Use $hint to Select a Particular Index
   In most cases the query optimizer selects the optimal index 
   for a specific operation; 
   we can force MongoDB to use a specific index 
   using the hint() method. 

   Use hint() to support performance testing, 
   or on some queries where you must select a field or field 
   included in several indexes.
----------------------------------------------------------------