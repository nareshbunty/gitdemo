$reduce (aggregation)
--------------------
Applies an expression to each element in an array 
and combines them into a single value.

$reduce has the following syntax:
{
    $reduce: {
        input: <array>,
        initialValue: <expression>,
        in: <expression>
    }
}
input	array	
Can be any valid expression that resolves to an array.

initialValue	expression	
The initial cumulative value set before in is applied to 
the first element of the input array.

in	expression	
A valid expression that $reduce applies to each element 
in the input array in left-to-right order. 

Wrap the input value with $reverseArray to yield the 
equivalent of applying the combining expression from right-to-left.

During evaulation of the in expression, 
two variables will be available:

value is the variable that represents the cumulative 
value of the expression.
This is the variable that refers to the element being processed.

If input resolves to an empty array, 
$reduce returns initialValue.

Example	
here $$value takes intial value & $$this uses input
values over iteration ($$this means current value)
1. 
{
   $reduce: {
      input: ["a", "b", "c"],
      initialValue: "",
      in: { $concat : ["$$value", "$$this"] }
    }
}
Output: "abc"

2. example
{
   $reduce: {
      input: [ 1, 2, 3, 4 ],
      initialValue: { sum: 5, product: 2 },
      in: {
         sum: { $add : ["$$value.sum", "$$this"] },
         product: { $multiply: [ "$$value.product", "$$this" ] }
      }
   }
}

Output: { "sum" : 15, "product" : 48 }

3. Example 
{
   $reduce: {
      input: [ [ 3, 4 ], [ 5, 6 ] ],
      initialValue: [ 1, 2 ],
      in: { $concatArrays : ["$$value", "$$this"] }
   }
}

Output: [ 1, 2, 3, 4, 5, 6 ]

Discounted Merchandise
A collection named clothes contains the following documents:

{ "_id" : 1, "productId" : "ts1", 
"description" : "T-Shirt", "color" : "black", 
"size" : "M", "price" : 20, "discounts" : [ 0.5, 0.1 ] }
{ "_id" : 2, "productId" : "j1", "description" : "Jeans", "color" : "blue", "size" : "36", "price" : 40, "discounts" : [ 0.25, 0.15, 0.05 ] }
{ "_id" : 3, "productId" : "s1", "description" : "Shorts", "color" : "beige", "size" : "32", "price" : 30, "discounts" : [ 0.15, 0.05 ] }
{ "_id" : 4, "productId" : "ts2", "description" : "Cool T-Shirt", "color" : "White", "size" : "L", "price" : 25, "discounts" : [ 0.3 ] }
{ "_id" : 5, "productId" : "j2", "description" : "Designer Jeans", "color" : "blue", "size" : "30", "price" : 80, "discounts" : [ 0.1, 0.25 ] }

Each document contains a discounts array containing 
the currently available percent-off coupons for each item. 
If each discount can be applied to the product once, 
we can calculate the lowest price by using $reduce to 
apply the following formula for each element in the 
discounts array: (1 - discount) * price.

db.clothes.aggregate(
  [
    {
      $project: {
        "discountedPrice": {
          $reduce: {
            input: "$discounts",
            initialValue: "$price",
            in: { 
              $multiply:
               [ "$$value", { $subtract: [ 1, "$$this" ] } ] }
          }
        }
      }
    }
  ]
)

The operation returns the following:

{ "_id" : ObjectId("57c893067054e6e47674ce01"),
     "discountedPrice" : 9 }
{ "_id" : ObjectId("57c9932b7054e6e47674ce12"), 
     "discountedPrice" : 24.224999999999998 }
{ "_id" : ObjectId("57c993457054e6e47674ce13"), 
    "discountedPrice" : 24.224999999999998 }
{ "_id" : ObjectId("57c993687054e6e47674ce14"),
    "discountedPrice" : 17.5 }
{ "_id" : ObjectId("57c993837054e6e47674ce15"), 
   "discountedPrice" : 54 }

String Concatenation
A collection named people contains the following documents:

{ "_id" : 1, "name" : "Melissa", "hobbies" : [ "softball", "drawing", "reading" ] }
{ "_id" : 2, "name" : "Brad", "hobbies" : [ "gaming", "skateboarding" ] }
{ "_id" : 3, "name" : "Scott", "hobbies" : [ "basketball", "music", "fishing" ] }
{ "_id" : 4, "name" : "Tracey", "hobbies" : [ "acting", "yoga" ] }
{ "_id" : 5, "name" : "Josh", "hobbies" : [ "programming" ] }
{ "_id" : 6, "name" : "Claire" }



Assignment:

Reduces the hobbies array of strings 
into a single string bio




Answer:
db.people.aggregate(
   [
     // Filter to return only non-empty arrays
     { $match: { "hobbies": { $gt: [ ] } } },
     {
       $project: {
         "name": 1,
         "bio": {
           $reduce: {
             input: "$hobbies",
             initialValue: "My hobbies include:",
             in: {
               $concat: [
                 "$$value",
                 {
                   $cond: {
                     if: { $eq: [ "$$value", "My hobbies include:" ] },
                     then: " ",
                     else: ", "
                   }
                 },
                 "$$this"
               ]
             }
           }
         }
       }
     }
   ]
)
The operation returns the following:

{ "_id" : 1, "name" : "Melissa", "bio" : "My hobbies include: softball, drawing, reading" }
{ "_id" : 2, "name" : "Brad", "bio" : "My hobbies include: gaming, skateboarding" }
{ "_id" : 3, "name" : "Scott", "bio" : "My hobbies include: basketball, music, fishing" }
{ "_id" : 4, "name" : "Tracey", "bio" : "My hobbies include: acting, yoga" }
{ "_id" : 5, "name" : "Josh", "bio" : "My hobbies include: programming" }

